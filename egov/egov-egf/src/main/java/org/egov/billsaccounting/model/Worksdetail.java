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
package org.egov.billsaccounting.model;

// Generated Mar 6, 2008 11:33:35 AM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

import org.egov.commons.Fund;
import org.egov.commons.Fundsource;
import org.egov.commons.Relation;
import org.egov.model.recoveries.Recovery;

/**
 * Worksdetail generated by hbm2java
 */
public class Worksdetail implements java.io.Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 8092703331283063564L;

    private Integer id;

    private Worksdetail worksdetail;

    private Recovery recovery;

    private Relation relation;

    private Fundsource fundsource;

    private Fund fund;

    private String code;

    private Date orderdate;

    private BigDecimal totalvalue;

    private BigDecimal paidamount;

    private BigDecimal advancepayable;

    private BigDecimal advanceamount;

    private BigDecimal isactive;

    private Date created;

    private Date lastmodified;

    private BigDecimal modifiedby;

    private String name;

    private String authorizedby;

    private String levelofwork;

    private BigDecimal wardid;

    private BigDecimal schemeid;

    private BigDecimal tdsid;

    private String securitydeposit;

    private BigDecimal retention;

    private String bankguarantee;

    private Long glcodeid;

    private BigDecimal passedamount;

    private BigDecimal type;

    private BigDecimal advanceadj;

    private String sanctionno;

    private String remarks;

    private Date sanctiondate;

    private BigDecimal userdeptid;

    private BigDecimal execdeptid;

    private BigDecimal shipto;

    private String billto;

    private BigDecimal statusid;

    private BigDecimal createdby;

    private Date actCommDate;

    private Date actCompDate;

    private Date commDate;

    private Date compDate;

    private String worklocation;

    private BigDecimal workcategory;

    private BigDecimal subcategory;

    public Worksdetail()
    {
    }

    public Worksdetail(final Integer id, final Relation relation, final String code,
            final Date orderdate, final BigDecimal totalvalue, final BigDecimal isactive,
            final Date created, final String name, final String levelofwork)
    {
        this.id = id;
        this.relation = relation;
        this.code = code;
        this.orderdate = orderdate;
        this.totalvalue = totalvalue;
        this.isactive = isactive;
        this.created = created;
        this.name = name;
        this.levelofwork = levelofwork;
    }

    public Worksdetail(final Integer id, final Worksdetail worksdetail, final Recovery recovery,
            final Relation relation, final Fundsource fundsource, final Fund fund, final String code,
            final Date orderdate, final BigDecimal totalvalue, final BigDecimal paidamount,
            final BigDecimal advancepayable, final BigDecimal advanceamount,
            final BigDecimal isactive, final Date created, final Date lastmodified,
            final BigDecimal modifiedby, final String name, final String authorizedby,
            final String levelofwork, final BigDecimal wardid, final BigDecimal schemeid,
            final BigDecimal tdsid, final String securitydeposit, final BigDecimal retention,
            final String bankguarantee, final Long glcodeid, final BigDecimal passedamount,
            final BigDecimal type, final BigDecimal advanceadj, final String sanctionno,
            final String remarks, final Date sanctiondate, final BigDecimal userdeptid,
            final BigDecimal execdeptid, final BigDecimal shipto, final String billto,
            final BigDecimal statusid, final BigDecimal createdby, final Date actCommDate,
            final Date actCompDate, final Date commDate, final Date compDate,
            final String worklocation, final BigDecimal workcategory)
    {
        this.id = id;
        this.worksdetail = worksdetail;
        this.recovery = recovery;
        this.relation = relation;
        this.fundsource = fundsource;
        this.fund = fund;
        this.code = code;
        this.orderdate = orderdate;
        this.totalvalue = totalvalue;
        this.paidamount = paidamount;
        this.advancepayable = advancepayable;
        this.advanceamount = advanceamount;
        this.isactive = isactive;
        this.created = created;
        this.lastmodified = lastmodified;
        this.modifiedby = modifiedby;
        this.name = name;
        this.authorizedby = authorizedby;
        this.levelofwork = levelofwork;
        this.wardid = wardid;
        this.schemeid = schemeid;
        this.tdsid = tdsid;
        this.securitydeposit = securitydeposit;
        this.retention = retention;
        this.bankguarantee = bankguarantee;
        this.glcodeid = glcodeid;
        this.passedamount = passedamount;
        this.type = type;
        this.advanceadj = advanceadj;
        this.sanctionno = sanctionno;
        this.remarks = remarks;
        this.sanctiondate = sanctiondate;
        this.userdeptid = userdeptid;
        this.execdeptid = execdeptid;
        this.shipto = shipto;
        this.billto = billto;
        this.statusid = statusid;
        this.createdby = createdby;
        this.actCommDate = actCommDate;
        this.actCompDate = actCompDate;
        this.commDate = commDate;
        this.compDate = compDate;
        this.worklocation = worklocation;
        this.workcategory = workcategory;
        subcategory = subcategory;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(final Integer id)
    {
        this.id = id;
    }

    public Worksdetail getWorksdetail()
    {
        return worksdetail;
    }

    public void setWorksdetail(final Worksdetail worksdetail)
    {
        this.worksdetail = worksdetail;
    }

    public Recovery getRecovery() {
        return recovery;
    }

    public void setRecovery(final Recovery recovery) {
        this.recovery = recovery;
    }

    public Relation getRelation()
    {
        return relation;
    }

    public void setRelation(final Relation relation)
    {
        this.relation = relation;
    }

    public Fundsource getFundsource()
    {
        return fundsource;
    }

    public void setFundsource(final Fundsource fundsource)
    {
        this.fundsource = fundsource;
    }

    public Fund getFund()
    {
        return fund;
    }

    public void setFund(final Fund fund)
    {
        this.fund = fund;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(final String code)
    {
        this.code = code;
    }

    public Date getOrderdate()
    {
        return orderdate;
    }

    public void setOrderdate(final Date orderdate)
    {
        this.orderdate = orderdate;
    }

    public BigDecimal getTotalvalue()
    {
        return totalvalue;
    }

    public void setTotalvalue(final BigDecimal totalvalue)
    {
        this.totalvalue = totalvalue;
    }

    public BigDecimal getPaidamount()
    {
        return paidamount;
    }

    public void setPaidamount(final BigDecimal paidamount)
    {
        this.paidamount = paidamount;
    }

    public BigDecimal getAdvancepayable()
    {
        return advancepayable;
    }

    public void setAdvancepayable(final BigDecimal advancepayable)
    {
        this.advancepayable = advancepayable;
    }

    public BigDecimal getAdvanceamount()
    {
        return advanceamount;
    }

    public void setAdvanceamount(final BigDecimal advanceamount)
    {
        this.advanceamount = advanceamount;
    }

    public BigDecimal getIsactive()
    {
        return isactive;
    }

    public void setIsactive(final BigDecimal isactive)
    {
        this.isactive = isactive;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(final Date created)
    {
        this.created = created;
    }

    public Date getLastmodified()
    {
        return lastmodified;
    }

    public void setLastmodified(final Date lastmodified)
    {
        this.lastmodified = lastmodified;
    }

    public BigDecimal getModifiedby()
    {
        return modifiedby;
    }

    public void setModifiedby(final BigDecimal modifiedby)
    {
        this.modifiedby = modifiedby;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getAuthorizedby()
    {
        return authorizedby;
    }

    public void setAuthorizedby(final String authorizedby)
    {
        this.authorizedby = authorizedby;
    }

    public String getLevelofwork()
    {
        return levelofwork;
    }

    public void setLevelofwork(final String levelofwork)
    {
        this.levelofwork = levelofwork;
    }

    public BigDecimal getWardid()
    {
        return wardid;
    }

    public void setWardid(final BigDecimal wardid)
    {
        this.wardid = wardid;
    }

    public BigDecimal getSchemeid()
    {
        return schemeid;
    }

    public void setSchemeid(final BigDecimal schemeid)
    {
        this.schemeid = schemeid;
    }

    public BigDecimal getTdsid()
    {
        return tdsid;
    }

    public void setTdsid(final BigDecimal tdsid)
    {
        this.tdsid = tdsid;
    }

    public String getSecuritydeposit()
    {
        return securitydeposit;
    }

    public void setSecuritydeposit(final String securitydeposit)
    {
        this.securitydeposit = securitydeposit;
    }

    public BigDecimal getRetention()
    {
        return retention;
    }

    public void setRetention(final BigDecimal retention)
    {
        this.retention = retention;
    }

    public String getBankguarantee()
    {
        return bankguarantee;
    }

    public void setBankguarantee(final String bankguarantee)
    {
        this.bankguarantee = bankguarantee;
    }

    public Long getGlcodeid()
    {
        return glcodeid;
    }

    public void setGlcodeid(final Long glcodeid)
    {
        this.glcodeid = glcodeid;
    }

    public BigDecimal getPassedamount()
    {
        return passedamount;
    }

    public void setPassedamount(final BigDecimal passedamount)
    {
        this.passedamount = passedamount;
    }

    public BigDecimal getType()
    {
        return type;
    }

    public void setType(final BigDecimal type)
    {
        this.type = type;
    }

    public BigDecimal getAdvanceadj()
    {
        return advanceadj;
    }

    public void setAdvanceadj(final BigDecimal advanceadj)
    {
        this.advanceadj = advanceadj;
    }

    public String getSanctionno()
    {
        return sanctionno;
    }

    public void setSanctionno(final String sanctionno)
    {
        this.sanctionno = sanctionno;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(final String remarks)
    {
        this.remarks = remarks;
    }

    public Date getSanctiondate()
    {
        return sanctiondate;
    }

    public void setSanctiondate(final Date sanctiondate)
    {
        this.sanctiondate = sanctiondate;
    }

    public BigDecimal getUserdeptid()
    {
        return userdeptid;
    }

    public void setUserdeptid(final BigDecimal userdeptid)
    {
        this.userdeptid = userdeptid;
    }

    public BigDecimal getExecdeptid()
    {
        return execdeptid;
    }

    public void setExecdeptid(final BigDecimal execdeptid)
    {
        this.execdeptid = execdeptid;
    }

    public BigDecimal getShipto()
    {
        return shipto;
    }

    public void setShipto(final BigDecimal shipto)
    {
        this.shipto = shipto;
    }

    public String getBillto()
    {
        return billto;
    }

    public void setBillto(final String billto)
    {
        this.billto = billto;
    }

    public BigDecimal getStatusid()
    {
        return statusid;
    }

    public void setStatusid(final BigDecimal statusid)
    {
        this.statusid = statusid;
    }

    public BigDecimal getCreatedby()
    {
        return createdby;
    }

    public void setCreatedby(final BigDecimal createdby)
    {
        this.createdby = createdby;
    }

    public Date getActCommDate()
    {
        return actCommDate;
    }

    public void setActCommDate(final Date actCommDate)
    {
        this.actCommDate = actCommDate;
    }

    public Date getActCompDate()
    {
        return actCompDate;
    }

    public void setActCompDate(final Date actCompDate)
    {
        this.actCompDate = actCompDate;
    }

    public Date getCommDate()
    {
        return commDate;
    }

    public void setCommDate(final Date commDate)
    {
        this.commDate = commDate;
    }

    public Date getCompDate()
    {
        return compDate;
    }

    public void setCompDate(final Date compDate)
    {
        this.compDate = compDate;
    }

    public String getWorklocation()
    {
        return worklocation;
    }

    public void setWorklocation(final String worklocation)
    {
        this.worklocation = worklocation;
    }

    public BigDecimal getWorkcategory()
    {
        return workcategory;
    }

    public void setWorkcategory(final BigDecimal workcategory)
    {
        this.workcategory = workcategory;
    }

    public BigDecimal getSubcategory()
    {
        return subcategory;
    }

    public void setSubcategory(final BigDecimal subcategory)
    {
        this.subcategory = subcategory;
    }

}