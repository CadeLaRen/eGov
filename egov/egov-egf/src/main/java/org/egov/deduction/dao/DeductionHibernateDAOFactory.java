/*******************************************************************************
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
 * 	1) All versions of this program, verbatim or modified must carry this
 * 	   Legal Notice.
 *
 * 	2) Any misrepresentation of the origin of the material is prohibited. It
 * 	   is required that all modified versions of this material be marked in
 * 	   reasonable ways as different from the original version.
 *
 * 	3) This license does not grant any rights to any user of the program
 * 	   with regards to rights under trademark law for use of the trade names
 * 	   or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
/*
 * DeductionHibernateDAOFactory.java Created on Oct 10, 2007
 *
 * Copyright 2005 eGovernments Foundation. All rights reserved.
 * EGOVERNMENTS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.egov.deduction.dao;

import org.egov.deduction.model.EgRemittance;
import org.egov.deduction.model.EgRemittanceDetail;
import org.egov.deduction.model.EgRemittanceGldtl;
import org.egov.deduction.model.Generalledgerdetail;
import org.egov.infstr.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO Brief Description of the purpose of the class/interface
 *
 * @author MRC
 * @version 1.00 Returns Hibernate-specific instances of DAOs.
 * <p>
 * One of the responsiblities of the factory is to inject a Hibernate Session into the DAOs. You can customize the
 * getCurrentSession() method if you are not using the default strategy, which simply delegates to
 * <ttHibernateUtil.getCurrentSession()</tt>,
 */
@Transactional(readOnly = true)
public class DeductionHibernateDAOFactory extends DeductionDAOFactory
{
    protected Session getCurrentSession()
    {
        // returns a reference to the current Session.
        return HibernateUtil.getCurrentSession();
    }

    @Override
    public EgRemittanceGldtlHibernateDAO getEgRemittanceGldtlDAO()
    {
        return new EgRemittanceGldtlHibernateDAO(EgRemittanceGldtl.class, getCurrentSession());
    }

    @Override
    public EgRemittanceHibernateDAO getEgRemittanceDAO()
    {
        return new EgRemittanceHibernateDAO(EgRemittance.class, getCurrentSession());
    }

    @Override
    public GeneralledgerdetailHibernateDAO getGeneralledgerdetailDAO()
    {
        return new GeneralledgerdetailHibernateDAO(Generalledgerdetail.class, getCurrentSession());
    }

    @Override
    public EgRemittanceDetailHibernateDAO getEgRemittanceDetailDAO()
    {
        return new EgRemittanceDetailHibernateDAO(EgRemittanceDetail.class, getCurrentSession());
    }

}