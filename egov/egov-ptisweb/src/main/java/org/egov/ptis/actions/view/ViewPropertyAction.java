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
 * 	1) All versions of this program, verbatim or modified must carry this
 * 	   Legal Notice.
 *
 * 	2) Any misrepresentation of the origin of the material is prohibited. It
 * 	   is required that all modified versions of this material be marked in
 * 	   reasonable ways as different from the original version.
 *
 * 	3) This license does not grant any rights to any user of the program
 * 	   with regards to rights under trademark law for use of the trade names
 * 	   or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.ptis.actions.view;

import static org.egov.ptis.constants.PropertyTaxConstants.APPLICATION_TYPE_ALTER_ASSESSENT;
import static org.egov.ptis.constants.PropertyTaxConstants.APPLICATION_TYPE_BIFURCATE_ASSESSENT;
import static org.egov.ptis.constants.PropertyTaxConstants.APPLICATION_TYPE_NEW_ASSESSENT;
import static org.egov.ptis.constants.PropertyTaxConstants.APPLICATION_TYPE_REVISION_PETITION;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_EDUCATIONAL_CESS;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_GENERAL_TAX;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_LIBRARY_CESS;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_VACANT_TAX;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_UNAUTHORIZED_PENALTY;
import static org.egov.ptis.constants.PropertyTaxConstants.FLOOR_MAP;
import static org.egov.ptis.constants.PropertyTaxConstants.NOT_AVAILABLE;
import static org.egov.ptis.constants.PropertyTaxConstants.OWNERSHIP_TYPE_VAC_LAND;
import static org.egov.ptis.constants.PropertyTaxConstants.SESSIONLOGINID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.egov.infra.admin.master.entity.Role;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.persistence.entity.Address;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.infstr.services.PersistenceService;
import org.egov.infstr.utils.StringUtils;
import org.egov.ptis.client.util.PropertyTaxUtil;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.dao.demand.PtDemandDao;
import org.egov.ptis.domain.dao.property.BasicPropertyDAO;
import org.egov.ptis.domain.entity.demand.Ptdemand;
import org.egov.ptis.domain.entity.objection.RevisionPetition;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.domain.entity.property.Floor;
import org.egov.ptis.domain.entity.property.Property;
import org.egov.ptis.domain.entity.property.PropertyImpl;
import org.egov.ptis.domain.entity.property.PropertyOwnerInfo;
import org.springframework.beans.factory.annotation.Autowired;

@ParentPackage("egov")
@Results({ @Result(name = "view", location = "viewProperty-view.jsp") })
public class ViewPropertyAction extends BaseFormAction {

    private static final long serialVersionUID = 4609817011534083012L;
    private final Logger LOGGER = Logger.getLogger(getClass());
    private String propertyId;
    private BasicProperty basicProperty;
    private PropertyImpl property;
    private String ownerAddress;
    private Map<String, Object> viewMap;
    private PropertyTaxUtil propertyTaxUtil;
    private String roleName;
    private boolean isDemandActive;
    private String applicationNo;
    private String applicationType;
    private String[] floorNoStr = new String[100];
    private String errorMessage;

    @Autowired
    private BasicPropertyDAO basicPropertyDAO;
    @Autowired
    private PtDemandDao ptDemandDAO;
    @Autowired
    private UserService UserService;
    @Autowired
    private PersistenceService<Property, Long> propertyImplService;
    @Autowired
    private PersistenceService<RevisionPetition, Long> revisionPetitionPersistenceService;

    @Override
    public StateAware getModel() {
        return property;
    }

    @Action(value = "/view/viewProperty-viewForm")
    public String viewForm() {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Entered into viewForm method, propertyId : " + propertyId);
        try {
            viewMap = new HashMap<String, Object>();
            if (propertyId != null && !propertyId.isEmpty())
                setBasicProperty(basicPropertyDAO.getBasicPropertyByPropertyID(propertyId));
            else if (applicationNo != null && !applicationNo.isEmpty()){
                getBasicPropForAppNo(applicationNo, applicationType); 
                setPropertyId(basicProperty.getUpicNo());
            }
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("viewForm : BasicProperty : " + basicProperty);

            property = (PropertyImpl) getBasicProperty().getProperty();
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("viewForm : Property : " + property);
            Ptdemand ptDemand = ptDemandDAO.getNonHistoryCurrDmdForProperty(property);
            if (ptDemand == null) {
                setErrorMessage("No Tax details for current Demand period.");
                return "view";
            }
            if (property.getPropertyDetail().getFloorDetails().size() > 0)
                setFloorDetails(property);
            checkIsDemandActive(property);
            if (getBasicProperty().getPropertyOwnerInfo() != null
                    && !getBasicProperty().getPropertyOwnerInfo().isEmpty()) {
                for (final PropertyOwnerInfo propOwner : getBasicProperty().getPropertyOwnerInfo()) {
                    final List<Address> addrSet = propOwner.getOwner().getAddress();
                    for (final Address address : addrSet) {
                        ownerAddress = address.toString();
                        viewMap.put("doorNo", address.getHouseNoBldgApt() == null ? NOT_AVAILABLE : address.getHouseNoBldgApt());
                        break;
                    }
                }
                viewMap.put("ownerAddress", ownerAddress == null ? NOT_AVAILABLE : ownerAddress);
                viewMap.put("ownershipType", basicProperty.getProperty().getPropertyDetail().getPropertyTypeMaster()
                        .getType());
            }
            if (!property.getIsExemptedFromTax()) {
                final Map<String, BigDecimal> demandCollMap = propertyTaxUtil.prepareDemandDetForView(property,
                        propertyTaxUtil.getCurrentInstallment());
                viewMap.put("currTax", demandCollMap.get(CURR_DMD_STR));
                viewMap.put("currTaxDue", demandCollMap.get(CURR_DMD_STR).subtract(demandCollMap.get(CURR_COLL_STR)));
                viewMap.put("totalArrDue", demandCollMap.get(ARR_DMD_STR).subtract(demandCollMap.get(ARR_COLL_STR)));

                viewMap.put("eduCess", (demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS) == null ? BigDecimal.ZERO : demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS)));
                viewMap.put("libraryCess", (demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS) == null ? BigDecimal.ZERO : demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS)));
                BigDecimal totalTax = BigDecimal.ZERO;
                if (!property.getPropertyDetail().getPropertyTypeMaster().getCode()
                        .equalsIgnoreCase(OWNERSHIP_TYPE_VAC_LAND)) {
                	viewMap.put("generalTax", demandCollMap.get(DEMANDRSN_STR_GENERAL_TAX));
                	viewMap.put("propertyType", property.getPropertyDetail().getPropertyTypeMaster().getCode());
                	totalTax = demandCollMap.get(DEMANDRSN_STR_GENERAL_TAX)
	                            .add(demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS) == null ? BigDecimal.ZERO : demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS))
	                            .add(demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS) == null ? BigDecimal.ZERO : demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS));
                	if(demandCollMap.get(DEMANDRSN_STR_UNAUTHORIZED_PENALTY)!=null){
                		viewMap.put("unauthorisedPenalty", demandCollMap.get(DEMANDRSN_STR_UNAUTHORIZED_PENALTY));
                		viewMap.put("totalTax",totalTax
                                        .add(demandCollMap.get(DEMANDRSN_STR_UNAUTHORIZED_PENALTY)));
                	}else{
                		viewMap.put("totalTax",totalTax);
                	}
                    
                } else {
                    viewMap.put("vacantLandTax", demandCollMap.get(DEMANDRSN_STR_VACANT_TAX));
                    totalTax = demandCollMap.get(DEMANDRSN_STR_VACANT_TAX) != null ? demandCollMap.get(DEMANDRSN_STR_VACANT_TAX) : demandCollMap.get(DEMANDRSN_STR_GENERAL_TAX)
	                            .add(demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS) == null ? BigDecimal.ZERO : demandCollMap.get(DEMANDRSN_STR_LIBRARY_CESS))
	                            .add(demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS) == null ? BigDecimal.ZERO : demandCollMap.get(DEMANDRSN_STR_EDUCATIONAL_CESS));
                    if(demandCollMap.get(DEMANDRSN_STR_UNAUTHORIZED_PENALTY)!=null){
                    	viewMap.put("unauthorisedPenalty", demandCollMap.get(DEMANDRSN_STR_UNAUTHORIZED_PENALTY));
                		viewMap.put("totalTax",totalTax
                                        .add(demandCollMap.get(DEMANDRSN_STR_UNAUTHORIZED_PENALTY)));
                    }else{
                    	viewMap.put("totalTax",totalTax);
                    }
                }

            } else {
                viewMap.put("currTax", BigDecimal.ZERO);
                viewMap.put("currTaxDue", BigDecimal.ZERO);
                viewMap.put("totalArrDue", BigDecimal.ZERO);

                viewMap.put("eduCess", BigDecimal.ZERO);
                viewMap.put("libraryCess", BigDecimal.ZERO);
                viewMap.put("generalTax", BigDecimal.ZERO);
                viewMap.put("totalTax", BigDecimal.ZERO);
                viewMap.put("vacantLandTax", BigDecimal.ZERO);
            }
            if (ptDemand.getDmdCalculations() != null && ptDemand.getDmdCalculations().getAlv() != null)
                viewMap.put("ARV", ptDemand.getDmdCalculations().getAlv());
            else
                viewMap.put("ARV", BigDecimal.ZERO);
            
            propertyTaxUtil.setPersistenceService(persistenceService);
            viewMap.put("enableVacancyRemission", propertyTaxUtil.enableVacancyRemission(basicProperty.getUpicNo()));
            viewMap.put("enableMonthlyUpdate", propertyTaxUtil.enableMonthlyUpdate(basicProperty.getUpicNo()));
            final Long userId = (Long) session().get(SESSIONLOGINID);
            if (userId != null)
                setRoleName(getRolesForUserId(userId));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("viewForm : viewMap : " + viewMap);
                LOGGER.debug("Exit from method viewForm");
            }
            return "view"; 
        } catch (final Exception e) {
            LOGGER.error("Exception in View Property: ", e);
            throw new ApplicationRuntimeException("Exception in View Property : " + e);
        }
    }

    private void checkIsDemandActive(final Property property) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Entered into checkIsDemandActive");
        if (property.getStatus().equals(PropertyTaxConstants.STATUS_DEMAND_INACTIVE))
            isDemandActive = false;
        else
            isDemandActive = true;
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("checkIsDemandActive - Is demand active? : " + isDemandActive);
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Exiting from checkIsDemandActive");
    }

    private String getRolesForUserId(final Long userId) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Entered into method getRolesForUserId");
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("User id : " + userId);
        String roleName;
        final List<String> roleNameList = new ArrayList<String>();
        final User user = UserService.getUserById(userId);
        for (final Role role : user.getRoles()) {
            roleName = role.getName() != null ? role.getName() : "";
            roleNameList.add(roleName);
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Exit from method getRolesForUserId with return value : "
                    + roleNameList.toString().toUpperCase());
        return roleNameList.toString().toUpperCase();
    }

    private void getBasicPropForAppNo(final String appNo, final String appType) {
        if (appType != null && !appType.isEmpty())
            if (appType.equalsIgnoreCase(APPLICATION_TYPE_NEW_ASSESSENT)
                    || appType.equalsIgnoreCase(APPLICATION_TYPE_ALTER_ASSESSENT)
                    || appType.equalsIgnoreCase(APPLICATION_TYPE_BIFURCATE_ASSESSENT)) {
                final Property property = propertyImplService.find("from PropertyImpl where applicationNo=?", appNo);
                setBasicProperty(property.getBasicProperty());
            } else if (appType.equalsIgnoreCase(APPLICATION_TYPE_REVISION_PETITION)) {
                final RevisionPetition rp = revisionPetitionPersistenceService.find(
                        "from RevisionPetition where objectionNumber=?", appNo);
                setBasicProperty(rp.getBasicProperty());
            }
    }

    public void setFloorDetails(final Property property) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Entered into setFloorDetails, Property: " + property);

        final List<Floor> floors = property.getPropertyDetail().getFloorDetails();
        property.getPropertyDetail().setFloorDetailsProxy(floors);
        int i = 0;
        for (final Floor flr : floors) {
            floorNoStr[i] = FLOOR_MAP.get(flr.getFloorNo());
            i++;
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Exiting from setFloorDetails: ");
    }

    public String getFloorNoStr(final Integer floorNo) {
        return FLOOR_MAP.get(floorNo);
    }

    public List<Floor> getFloorDetails() {
        return new ArrayList<Floor>(property.getPropertyDetail().getFloorDetails());
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(final String propertyId) {
        this.propertyId = propertyId;
    }

    public BasicProperty getBasicProperty() {
        return basicProperty;
    }

    public void setBasicProperty(final BasicProperty basicProperty) {
        this.basicProperty = basicProperty;
    }

    public PropertyTaxUtil getPropertyTaxUtil() {
        return propertyTaxUtil;
    }

    public void setPropertyTaxUtil(final PropertyTaxUtil propertyTaxUtil) {
        this.propertyTaxUtil = propertyTaxUtil;
    }

    public Map<String, Object> getViewMap() {
        return viewMap;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public boolean getIsDemandActive() {
        return isDemandActive;
    }

    public void setIsDemandActive(final boolean isDemandActive) {
        this.isDemandActive = isDemandActive;
    }

    public Property getProperty() {
        return property;
    }

    public void setBasicPropertyDAO(final BasicPropertyDAO basicPropertyDAO) {
        this.basicPropertyDAO = basicPropertyDAO;
    }

    public void setPtDemandDAO(final PtDemandDao ptDemandDAO) {
        this.ptDemandDAO = ptDemandDAO;
    }

    public void setUserService(final UserService userService) {
        UserService = userService;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(final String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(final String applicationType) {
        this.applicationType = applicationType;
    }

    public String[] getFloorNoStr() {
        return floorNoStr;
    }

    public void setFloorNoStr(final String[] floorNoStr) {
        this.floorNoStr = floorNoStr;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
