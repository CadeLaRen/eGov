/* eGov suite of products aim to improve the internal efficiency,transparency,
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
package org.egov.restapi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.utils.EgovThreadLocals;
import org.egov.restapi.constants.RestRedirectConstants;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

//This is an unnecessary class, the existence of this filter is due to customer is not ready to
//change their existing system to call appropriate url from their apps.
public class ApiFilter implements Filter {

    private final static Logger LOG = Logger.getLogger(ApiFilter.class);

    @Autowired
    private CityService cityService;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain)
            throws IOException, ServletException {
        final MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) servletRequest);
        String ulbCode = null;
        final byte[] b = new byte[5000];
        ulbCode = servletRequest.getParameter("ulbCode");
        if (ulbCode == null) {
            JSONObject jsonObject = null;
            String jb = new String();
            try {
                final ServletInputStream inputStream = multiReadRequest.getInputStream();
                inputStream.read(b);
                jb = new String(b);
            } catch (final Exception e) {
                // Throw error
            }

            try {
                jsonObject = JSONObject.fromObject(jb.toString());
            } catch (final Exception e) {
                throw new RuntimeException("Invalid Json");
            }

            if (jsonObject != null)
                ulbCode = jsonObject.getString("ulbCode");
            else
                throw new RuntimeException("Invalid Json ULB Code is not Passed");

        }

        if (StringUtils.isNotBlank(ulbCode)) {
            if (!ulbCode.equals(EgovThreadLocals.getCityCode())) {
                LOG.info("Request Reached Different city. Need to change domain details");
                final String cityName = RestRedirectConstants.getCode_ulbNames().get(ulbCode).toLowerCase();
                EgovThreadLocals.setTenantID(cityName);
                final City city = cityService.getCityByCode(ulbCode);
                EgovThreadLocals.setDomainName(city.getDomainURL());
                EgovThreadLocals.setCityCode(ulbCode);
            } else 
                LOG.info("ULB code resolved to be same, continueing normal request flow"); 
        } else {
            LOG.error("ULB Code missing in request");
            throw new ApplicationRuntimeException("ULB Code missing in request");
        }
        filterChain.doFilter(multiReadRequest, servletResponse);
    }

    @Override
    public void init(final FilterConfig arg0) throws ServletException {

    }

}