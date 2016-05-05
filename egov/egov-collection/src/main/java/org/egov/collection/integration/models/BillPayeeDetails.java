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
package org.egov.collection.integration.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("payee")
public class BillPayeeDetails {

    private final String payeeName;
    private final String payeeAddress;

    @XStreamAlias("bills")
    private final List<BillDetails> billDetails = new ArrayList<BillDetails>();

    public BillPayeeDetails(final String payeeName, final String payeeAddress) {
        super();
        this.payeeName = payeeName;
        this.payeeAddress = payeeAddress;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public List<BillDetails> getBillDetails() {
        return billDetails;
    }

    public void addBillDetails(final BillDetails billDetail) {
        billDetails.add(billDetail);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BillPayeeDetails) {
            final BillPayeeDetails payee = (BillPayeeDetails) obj;
            if (payeeName.equals(payee.payeeName) && payeeAddress.equals(payee.payeeAddress))
                return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return payeeName.hashCode() + payeeAddress.hashCode();
    }
}
