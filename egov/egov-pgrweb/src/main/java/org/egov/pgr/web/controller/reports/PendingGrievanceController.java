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
package org.egov.pgr.web.controller.reports;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.egov.pgr.entity.Complaint;
import org.egov.pgr.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/pending")
public class PendingGrievanceController {

    private final ComplaintService complaintService;
    public static final String CONTENTTYPE_JSON = "application/json";

    @Autowired
    public PendingGrievanceController(final ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @ModelAttribute
    public Complaint complaint() {
        return new Complaint();
    }

    @RequestMapping(value = "/grievance-list", method = GET)
    public String complaintTypeViewForm() {
        return "grievance-list";

    }

    @RequestMapping(value = "/ajax-grievancelist", method = GET, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody void getPendingGrievances(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        IOUtils.write(
                new StringBuilder("{ \"data\":").append(toJSON(complaintService.getPendingGrievances())).append("}").toString(),
                response.getWriter());
    }

    private String toJSON(final Object object) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.registerTypeAdapter(Complaint.class, new PendingGrievanceAdaptor()).create();
        final String json = gson.toJson(object);
        return json;
    }
}
