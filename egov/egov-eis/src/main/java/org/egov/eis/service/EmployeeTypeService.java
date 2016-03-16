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
package org.egov.eis.service;

import java.util.List;

import org.egov.eis.entity.EmployeeType;
import org.egov.eis.repository.EmployeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EmployeeTypeService {

    private final EmployeeTypeRepository employeeTypeRepository;

    @Autowired
    public EmployeeTypeService(final EmployeeTypeRepository employeeTypeRepository) {
        this.employeeTypeRepository = employeeTypeRepository;
    }

    /**
     * Get employee type by name
     *
     * @param name
     * @return EmployeeType object
     */
    public EmployeeType getEmployeeTypeByName(final String name) {
        return employeeTypeRepository.findByName(name);
    }

    /**
     * Get all employee types for given chart of accounts id
     *
     * @param coaId
     * @return List of employee types
     */
    public List<EmployeeType> getEmployeeTypeByChartOfAccounts(final Long coaId) {
        return employeeTypeRepository.findByChartOfAccounts_Id(coaId);
    }

    /**
     * Get all Employee Types
     *
     * @return List of employee types
     */
    public List<EmployeeType> getAllEmployeeTypes() {
        return employeeTypeRepository.findAll();
    }

    /**
     * Get employee type by passing the id
     *
     * @param id
     * @return Employee Type object
     */
    public EmployeeType getEmployeeTypeById(final Long id) {
        return employeeTypeRepository.findOne(id);
    }

    @Transactional
    public void create(final EmployeeType employeeType) {
        employeeTypeRepository.save(employeeType);
    }

    @Transactional
    public void update(final EmployeeType employeeType) {
        employeeTypeRepository.save(employeeType);
    }

    @Transactional
    public void delete(final EmployeeType employeeType) {
        employeeTypeRepository.delete(employeeType);
    }
}