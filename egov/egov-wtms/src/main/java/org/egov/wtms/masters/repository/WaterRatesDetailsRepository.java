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
package org.egov.wtms.masters.repository;

import org.egov.wtms.masters.entity.DonationDetails;
import org.egov.wtms.masters.entity.DonationHeader;
import org.egov.wtms.masters.entity.UsageType;
import org.egov.wtms.masters.entity.WaterRatesDetails;
import org.egov.wtms.masters.entity.WaterRatesHeader;
import org.egov.wtms.masters.entity.enums.ConnectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WaterRatesDetailsRepository extends JpaRepository<WaterRatesDetails, Long> {

    List<WaterRatesDetails> findAllByWaterRatesHeader(WaterRatesHeader waterRatesHeader);

    @Query("select A from WaterRatesDetails A where A.waterRatesHeader.connectionType=:connectionType and A.waterRatesHeader.usageType=:usageType and A.startingUnits <= :noofunits and A.endingUnits >= :noofunits and A.waterRatesHeader.active=true")
    List<WaterRatesDetails> findByWaterRate(@Param("connectionType") ConnectionType connectionType,
            @Param("usageType") UsageType usageType, @Param("noofunits") Long noofunits);
    
    @Query(" from WaterRatesDetails dd where dd.waterRatesHeader =:waterRatesHeader and ((dd.toDate is not null and :toDate between dd.fromDate and dd.toDate) or (dd.toDate is not null and :fromDate between dd.fromDate and dd.toDate)  or (:fromDate <= dd.fromDate  and :toDate >= dd.toDate))")
    WaterRatesDetails findByWaterRatesHeaderAndFromDateAndToDate(@Param("waterRatesHeader") WaterRatesHeader waterRatesHeader, @Param("fromDate") Date fromDate ,@Param("toDate") Date toDate);

    WaterRatesDetails findByWaterRatesHeader(WaterRatesHeader waterRatesHeader);
}
