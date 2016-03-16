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
 * EgDeductionDetailsHibernateDAO.java Created on Oct 5, 2007
 *
 * Copyright 2005 eGovernments Foundation. All rights reserved.
 * EGOVERNMENTS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.egov.dao.recoveries;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.egov.commons.EgPartytype;
import org.egov.commons.EgwTypeOfWork;
import org.egov.infstr.dao.GenericHibernateDAO;
import org.egov.infstr.utils.HibernateUtil;
import org.egov.model.recoveries.EgDeductionDetails;
import org.egov.model.recoveries.Recovery;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Iliyaraja s
 *
 * TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
@Transactional(readOnly = true)
public class EgDeductionDetailsHibernateDAO extends GenericHibernateDAO
{
    private Session session;

    public EgDeductionDetailsHibernateDAO(final Class persistentClass, final Session session)
    {
        super(persistentClass, session);
    }

    public List findByTds(final Recovery tds)
    {
        session = HibernateUtil.getCurrentSession();
        final Query qry = session.createQuery("from EgDeductionDetails ded where ded.recovery=:tds order by ded.id");
        qry.setEntity("tds", tds);
        return qry.list();
    }

    public List<EgDeductionDetails> getEgDeductionDetailsFilterBy(final Recovery tds, final BigDecimal amount, final String date,
            final EgwTypeOfWork egwTypeOfWork, final EgwTypeOfWork egwSubTypeOfWork)
            {
        session = HibernateUtil.getCurrentSession();
        Query qry;
        final StringBuffer qryStr = new StringBuffer();
        List<EgDeductionDetails> egDeductionDetailsList = null;
        qryStr.append("from EgDeductionDetails ed where ed.recovery=:tds ");
        qry = session.createQuery(qryStr.toString());

        if (amount != null)
        {
            qryStr.append(" and ((ed.lowlimit<=:amount and ed.highlimit>=:amount and ed.highlimit is not null) or (ed.lowlimit<=:amount and ed.highlimit is null)) ");
            qry = session.createQuery(qryStr.toString());
        }
        if (date != null && !date.equals(""))
        {
            qryStr.append(" and ((ed.datefrom<=:date and ed.dateto>=:date and ed.dateto is not null) or(ed.datefrom<=:date and ed.dateto is null))");
            qry = session.createQuery(qryStr.toString());
        }
        if (egwTypeOfWork != null)
        {
            qryStr.append(" and ed.workDocType =:egwTypeOfWork");
            qry = session.createQuery(qryStr.toString());
        }
        if (egwSubTypeOfWork != null)
        {
            qryStr.append("  and ed.workDocSubType =:egwSubTypeOfWork");
            qry = session.createQuery(qryStr.toString());
        }
        qryStr.append(" order by id");
        qry = session.createQuery(qryStr.toString());
        if (tds != null)
            qry.setEntity("tds", tds);
        if (date != null && !date.equals(""))
            qry.setString("date", date);
        if (amount != null)
            qry.setBigDecimal("amount", amount);
        if (egwTypeOfWork != null)
            qry.setEntity("egwTypeOfWork", egwTypeOfWork);
        if (egwSubTypeOfWork != null)
            qry.setEntity("egwSubTypeOfWork", egwSubTypeOfWork);

        egDeductionDetailsList = qry.list();
        return egDeductionDetailsList;
            }

    public EgDeductionDetails findEgDeductionDetailsForDeduAmt(final Recovery recovery, final EgPartytype egPartyType,
            final EgPartytype egPartySubType, final EgwTypeOfWork docType, final Date date) {
        EgDeductionDetails egDeductionDetails = null;
        session = HibernateUtil.getCurrentSession();
        Query qry;
        final StringBuffer qryStr = new StringBuffer();
        qryStr.append("from EgDeductionDetails ed where ed.recovery=:recovery ");

        if (null != egPartySubType)
            qryStr.append(" and ed.egpartytype =:egPartySubType ");
        if (null != docType)
            qryStr.append(" and ed.workDocType =:docType ");
        if (null != date)
            qryStr.append(" and ((ed.datefrom <=:date and ed.dateto>=:date and ed.dateto is not null) or (ed.datefrom<=:date and ed.dateto is null)) ");

        qry = session.createQuery(qryStr.toString());

        if (null != recovery)
            qry.setEntity("recovery", recovery);
        if (null != egPartySubType)
            qry.setEntity("egPartySubType", egPartySubType);
        if (null != docType)
            qry.setEntity("docType", docType);
        if (null != date)
            qry.setDate("date", date);

        egDeductionDetails = (EgDeductionDetails) qry.uniqueResult();

        return egDeductionDetails;
    }

}