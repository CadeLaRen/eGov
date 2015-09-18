/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.ptis.actions.collection;

import static org.egov.ptis.constants.PropertyTaxConstants.ARR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.BILLTYPE_AUTO;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_DMD_STR;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.infstr.services.PersistenceService;
import org.egov.ptis.client.bill.PTBillServiceImpl;
import org.egov.ptis.client.util.PropertyTaxNumberGenerator;
import org.egov.ptis.client.util.PropertyTaxUtil;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.bill.PropertyTaxBillable;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.service.collection.PropertyTaxCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/collection")
@ResultPath("/WEB-INF/jsp/")
@Results({ @Result(name = CollectPropertyTaxAction.RESULT_VIEW, location = "collection/collectPropertyTax-view.jsp"),
		@Result(name = CollectPropertyTaxAction.RESULT_ERROR, location = "collection/collectPropertyTax-error.jsp") })
@ParentPackage("egov")
public class CollectPropertyTaxAction extends BaseFormAction {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CollectPropertyTaxAction.class);
	public static final String RESULT_VIEW = "view";
	public static final String RESULT_ERROR = "error";

	private PersistenceService<BasicProperty, Long> basicPropertyService;
	private PropertyTaxNumberGenerator propertyTaxNumberGenerator;
	private PropertyTaxCollection propertyTaxCollection;
	private PTBillServiceImpl ptBillServiceImpl;
	private PropertyTaxUtil propertyTaxUtil;
	private String propertyId;
	private String collectXML = "";
	private Boolean levyPenalty = false;
	private String errorMsg;
	private Boolean isAssessmentNoValid = Boolean.FALSE;

	private final List<String> args = new ArrayList<String>();

	@Autowired
	private PropertyTaxBillable propertyTaxBillable;

	@Override
	public Object getModel() {
		return null;
	}

	/**
	 * @return
	 */
	@Action(value = "/collectPropertyTax-generateBill")
	public String generateBill() {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Entered method generatePropertyTaxBill, Generating bill for index no : " + propertyId);
		if (propertyId == null || propertyId.isEmpty()) {
			setErrorMsg(getText("mandatory.assessmentNo"));
			return RESULT_ERROR;
		}
		final BasicProperty basicProperty = basicPropertyService.findByNamedQuery(
				PropertyTaxConstants.QUERY_BASICPROPERTY_BY_UPICNO, propertyId);
		if (basicProperty == null) {
			setErrorMsg(getText("validation.property.doesnot.exists"));
			return RESULT_ERROR;
		}
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("generatePropertyTaxBill : BasicProperty :" + basicProperty);

		if (basicProperty.getProperty().getIsExemptedFromTax()) {
			args.add(propertyId);
			setErrorMsg(getText("msg.collection.tax.exempted", args));
			return RESULT_ERROR;
		}

		final Map<String, BigDecimal> demandCollMap = propertyTaxUtil.getDemandAndCollection(basicProperty
				.getProperty());
		final BigDecimal currDue = demandCollMap.get(CURR_DMD_STR).subtract(demandCollMap.get(CURR_COLL_STR));
		final BigDecimal arrDue = demandCollMap.get(ARR_DMD_STR).subtract(demandCollMap.get(ARR_COLL_STR));

		if (currDue.compareTo(BigDecimal.ZERO) <= 0 && arrDue.compareTo(BigDecimal.ZERO) <= 0) {
			args.add(propertyId);
			isAssessmentNoValid = Boolean.TRUE;
			setErrorMsg(getText("msg.collection.fully.paid", args));
			return RESULT_ERROR;
		}
		propertyTaxBillable.setLevyPenalty(true);
		propertyTaxBillable.setBasicProperty(basicProperty);
		propertyTaxBillable.setUserId(Long.valueOf(getSession().get("userid").toString()));
		propertyTaxBillable.setReferenceNumber(propertyTaxNumberGenerator.generateBillNumber(basicProperty
				.getPropertyID().getWard().getBoundaryNum().toString()));
		propertyTaxBillable.setBillType(propertyTaxUtil.getBillTypeByCode(BILLTYPE_AUTO));

		final String billXml = ptBillServiceImpl.getBillXML(propertyTaxBillable);
		collectXML = URLEncoder.encode(billXml);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Exiting method generatePropertyTaxBill, collectXML (before decode): " + billXml);
		return RESULT_VIEW;
	}

	public void setbasicPropertyService(final PersistenceService<BasicProperty, Long> basicPropertyService) {
		this.basicPropertyService = basicPropertyService;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(final String propertyId) {
		this.propertyId = propertyId;
	}

	public String getCollectXML() {
		return collectXML;
	}

	public void setCollectXML(final String collectXML) {
		this.collectXML = collectXML;
	}

	public void setPropertyTaxUtil(final PropertyTaxUtil propertyTaxUtil) {
		this.propertyTaxUtil = propertyTaxUtil;
	}

	public PropertyTaxNumberGenerator getPropertyTaxNumberGenerator() {
		return propertyTaxNumberGenerator;
	}

	public void setPropertyTaxNumberGenerator(final PropertyTaxNumberGenerator propertyTaxNumberGenerator) {
		this.propertyTaxNumberGenerator = propertyTaxNumberGenerator;
	}

	public Boolean getLevyPenalty() {
		return levyPenalty;
	}

	public void setLevyPenalty(final Boolean levyPenalty) {
		this.levyPenalty = levyPenalty;
	}

	public PropertyTaxCollection getPropertyTaxCollection() {
		return propertyTaxCollection;
	}

	public void setPropertyTaxCollection(final PropertyTaxCollection propertyTaxCollection) {
		this.propertyTaxCollection = propertyTaxCollection;
	}

	public void setPtBillServiceImpl(final PTBillServiceImpl ptBillServiceImpl) {
		this.ptBillServiceImpl = ptBillServiceImpl;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(final String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean getIsAssessmentNoValid() {
		return isAssessmentNoValid;
	}

	public void setIsAssessmentNoValid(Boolean isAssessmentNoValid) {
		this.isAssessmentNoValid = isAssessmentNoValid;
	}

}
