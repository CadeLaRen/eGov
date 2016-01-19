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
package org.egov.tl.web.actions.renew;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.egov.tl.entity.License;
import org.egov.tl.entity.TradeLicense;
import org.egov.tl.service.BaseLicenseService;
import org.egov.tl.service.TradeService;
import org.egov.tl.utils.Constants;
import org.egov.tl.web.actions.BaseLicenseAction;

@Results({
@Result(name = Constants.RENEWALNOTICE, location = "tradeRenewalNotice-"+Constants.RENEWALNOTICE+".jsp")
})
public class TradeRenewalNoticeAction extends BaseLicenseAction {

    private static final long serialVersionUID = 1L;
    protected TradeLicense tradeLicense = new TradeLicense();
    private TradeService ts;

    /* to log errors and debugging information */
    private final Logger LOGGER = Logger.getLogger(getClass());

    @Override
    public License getModel() {
        return tradeLicense;
    }

    @Override
    protected License license() {
        return tradeLicense;
    }

    @Override
    protected BaseLicenseService service() {
        ts.getPersistenceService().setType(TradeLicense.class);
        return ts;
    }

@Action(value="/renew/tradeRenewalNotice-renewalNotice")
    public String renewalNotice() {
        LOGGER.debug("Trade License Renewal Notice Elements are:<<<<<<<<<<>>>>>>>>>>>>>:" + tradeLicense);
        tradeLicense = (TradeLicense) persistenceService.find("from TradeLicense where id=?", tradeLicense.getId());
        LOGGER.debug("Exiting from the renewalNotice method:<<<<<<<<<<>>>>>>>>>>>>>:");
        return Constants.RENEWALNOTICE;
    }

    public void setTs(final TradeService ts) {
        this.ts = ts;
    }

}
