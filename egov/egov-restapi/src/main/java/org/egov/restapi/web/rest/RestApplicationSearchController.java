/*
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
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.restapi.web.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.egov.config.search.Index;
import org.egov.config.search.IndexType;
import org.egov.search.domain.Page;
import org.egov.search.domain.SearchResult;
import org.egov.search.domain.Sort;
import org.egov.search.service.SearchService;
import org.egov.wtms.elasticSearch.entity.ApplicationSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Arrays.asList;

@RestController
public class RestApplicationSearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/common/application/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchApplication(@RequestBody final ApplicationSearchRequest searchRequest) {
        final String applicationNumber = searchRequest.getApplicationNumber() != null ? "\""
                + searchRequest.getApplicationNumber() + "\"" : "";
        searchRequest.setApplicationNumber(applicationNumber);
        final SearchResult searchResult = searchService.search(asList(Index.APPLICATION.toString()),
                asList(IndexType.APPLICATIONSEARCH.toString()), searchRequest.searchQuery(),
                searchRequest.searchFilters(), Sort.NULL, Page.NULL);
        return convertSearchResultToJson(searchResult);
    }

    private String convertSearchResultToJson(final Object object) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.registerTypeAdapter(SearchResult.class, new ApplicationSearchAdaptor()).create();
        final String json = gson.toJson(object);
        return json;
    }

}
