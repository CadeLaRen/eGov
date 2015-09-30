/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) <2015>  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *  	1) All versions of this program, verbatim or modified must carry this
 *  	   Legal Notice.
 *
 *  	2) Any misrepresentation of the origin of the material is prohibited. It
 *  	   is required that all modified versions of this material be marked in
 *  	   reasonable ways as different from the original version.
 *
 *  	3) This license does not grant any rights to any user of the program
 *  	   with regards to rights under trademark law for use of the trade names
 *  	   or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.tl.web.actions.domain;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.exception.NoSuchObjectException;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.tl.domain.entity.SubCategory;
import org.egov.tl.domain.service.masters.LicenseSubCategoryService;
import org.egov.tl.utils.LicenseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@Results({
    @Result(name = "AJAX_RESULT", type = "redirectAction", location = "returnStream", params = { "contentType", "text/plain" }),
    @Result(name = "ward", location = "commonAjax-ward.jsp"),
    @Result(name = "success", type = "redirectAction", location = "CommonTradeLicenseAjaxAction.action"),
    @Result(name = CommonTradeLicenseAjaxAction.SUBCATEGORY, location = "commonTradeLicenseAjax-subCategory.jsp")
})
@ParentPackage("egov")
public class CommonTradeLicenseAjaxAction extends BaseFormAction implements ServletResponseAware {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CommonTradeLicenseAjaxAction.class);
    protected LicenseUtils licenseUtils;
    private int zoneId;
    private List<Boundary> divisionList = new LinkedList<Boundary>();
    private Long categoryId;
    private List<SubCategory> subCategoryList = new LinkedList<SubCategory>();
    @Autowired
    private BoundaryService boundaryService;
    @Autowired
    private LicenseSubCategoryService licenseSubCategoryService;
    public static final String SUBCATEGORY = "subCategory";
    private Long locality;
    private HttpServletResponse response;

    /**
     * Populate wards.
     *
     * @return the string
     */
    @Action(value = "/domain/commonTradeLicenseAjax-populateDivisions")
    public String populateDivisions() {
        try {
            final Boundary boundary = boundaryService.getBoundaryById(Long.valueOf(zoneId));
            final String cityName = licenseUtils.getAllCity().get(0).getName();
            if (!boundary.getName().equals(cityName))
                divisionList = boundaryService.getChildBoundariesByBoundaryId(Long.valueOf(zoneId));
        } catch (final Exception e) {
            LOGGER.error("populateDivisions() - Error while loading divisions ." + e.getMessage());
            addFieldError("divisions", "Unable to load division information");
            throw new ApplicationRuntimeException("Unable to load division information", e);
        }
        return "ward";
    }

    /**
     * @return list of subcategory for a given category
     */
    @Action(value = "/domain/commonTradeLicenseAjax-populateSubCategory")
    public String populateSubCategory() {
        try {
            if (categoryId != null)
                subCategoryList = licenseSubCategoryService.findAllSubCategoryByCategory(categoryId);
        } catch (final Exception e) {
            LOGGER.error("populateSubCategory() - Error while loading subCategory ." + e.getMessage());
            addFieldError("subCategory", "Unable to load Sub Category information");
            throw new ApplicationRuntimeException("Unable to load Sub Category information", e);
        }
        return SUBCATEGORY;
    }

    /**
     * @throws IOException
     * @throws NoSuchObjectException
     * @return zone and ward for a locality
     */
    @Action(value = "/domain/commonTradeLicenseAjax-blockByLocality")
    public void blockByLocality() throws IOException, NoSuchObjectException {
        LOGGER.debug("Entered into blockByLocality, locality: " + locality);

        final Boundary blockBoundary = (Boundary) getPersistenceService().find(
                "select CH.parent from CrossHierarchy CH where CH.child.id = ? ", getLocality());
        final Boundary wardBoundary = blockBoundary.getParent();
        final Boundary zoneBoundary = wardBoundary.getParent();

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("zoneName", zoneBoundary.getName());
        jsonObject.put("wardName", wardBoundary.getName());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        IOUtils.write(jsonObject.toString(), response.getWriter());
    }

    @Override
    public Object getModel() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Convenience method to get the response
     *
     * @return current response
     */

    public HttpServletResponse getServletResponse() {
        return ServletActionContext.getResponse();
    }

    public LicenseUtils getLicenseUtils() {
        return licenseUtils;
    }

    public void setLicenseUtils(final LicenseUtils licenseUtils) {
        this.licenseUtils = licenseUtils;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(final int zoneId) {
        this.zoneId = zoneId;
    }

    public List<Boundary> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(final List<Boundary> divisionList) {
        this.divisionList = divisionList;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(final List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public Long getLocality() {
        return locality;
    }

    public void setLocality(final Long locality) {
        this.locality = locality;
    }

    @Override
    public void setServletResponse(final HttpServletResponse httpServletResponse) {
        response = httpServletResponse;
    }

}
