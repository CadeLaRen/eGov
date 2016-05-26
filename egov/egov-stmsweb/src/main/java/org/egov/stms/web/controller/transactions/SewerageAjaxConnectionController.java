/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.

        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.

        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.stms.web.controller.transactions;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.egov.ptis.domain.service.property.PropertyService;
import org.egov.stms.masters.entity.enums.PropertyType;
import org.egov.stms.transactions.service.SewerageApplicationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SewerageAjaxConnectionController {

    @Autowired
    private SewerageApplicationDetailsService sewerageApplicationDetailsService;

    @Autowired
    private PropertyService propertyservice;

    @RequestMapping(value = "/ajaxconnection/check-connection-exists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String isConnectionPresentForProperty(@RequestParam final String propertyID) {
        return sewerageApplicationDetailsService.checkConnectionPresentForProperty(propertyID);
    }

    @RequestMapping(value = "/ajaxconnection/check-watertax-due", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody BigDecimal getWaterTaxDue(@RequestParam("assessmentNo") final String assessmentNo,
            final HttpServletRequest request) {
        final BigDecimal waterTaxDue = propertyservice.getWaterTaxDues(assessmentNo, request);
        return waterTaxDue;
    }

    @RequestMapping(value = "/ajaxconnection/check-closets-exists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String isClosetsPresent(@RequestParam final PropertyType propertyType,
            @RequestParam final Integer noOfClosets, @RequestParam final Boolean flag) {
        if (flag)
            return sewerageApplicationDetailsService.checkClosetsPresentForGivenCombination(PropertyType.RESIDENTIAL,
                    noOfClosets);
        else
            return sewerageApplicationDetailsService.checkClosetsPresentForGivenCombination(
                    PropertyType.NON_RESIDENTIAL, noOfClosets);

    }
}
