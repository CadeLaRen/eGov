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
package org.egov.works.services;

import org.egov.commons.service.EntityTypeService;
import org.egov.infra.validation.exception.ValidationException;
import org.egov.infstr.services.Page;
import org.egov.infstr.services.PersistenceService;
import org.egov.works.models.masters.DepositCode;
import org.egov.works.utils.WorksConstants;

import java.util.List;

/**
 * Entity Type Service for deposit code
 */
public class DepositCodeService extends PersistenceService<DepositCode, Long> implements EntityTypeService {

    /*
     * (non-Javadoc)
     * @see org.egov.commons.service.EntityTypeService#filterActiveEntities(java. lang.String, int, java.lang.Integer)
     */
    @Override
    public List<DepositCode> filterActiveEntities(final String filterKey, final int maxRecords,
            final Integer accountDetailTypeId) {
        final Integer pageSize = maxRecords > 0 ? maxRecords : null;
        final String param = "%" + filterKey.toUpperCase() + "%";

        final Page page = findPageByNamedQuery(WorksConstants.QUERY_GETACTIVEDEPOSITCODES_BY_CODE_OR_DESC, 1, pageSize,
                param, param);

        return page.getList();
    }

    /*
     * (non-Javadoc)
     * @see org.egov.commons.service.EntityTypeService#getAllActiveEntities(java. lang.Integer)
     */
    @Override
    public List<DepositCode> getAllActiveEntities(final Integer accountDetailTypeId) {
        return findAllByNamedQuery(WorksConstants.QUERY_GETACTIVEDEPOSITCODES);
    }

    @Override
    public List getAssetCodesForProjectCode(final Integer accountdetailkey) throws ValidationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DepositCode> validateEntityForRTGS(final List<Long> idsList) throws ValidationException {

        return null;

    }

    @Override
    public List<DepositCode> getEntitiesById(final List<Long> idsList) throws ValidationException {

        return null;

    }
}
