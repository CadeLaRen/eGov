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

package org.egov.common.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name ="EG_UOMCATEGORY")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
public class UOMCategory extends AbstractAuditable  {

	private static final long serialVersionUID = -5071889556823525112L;

	private Long id;

	@NotNull
	@Length(max =30)
	@Column(unique =true)
	private String category;
	
	@Length(max =250)
	private String narration;

	@NotNull
	@Length(max=7)
	private Date lastmodified;
	
	@NotNull
	@Length(max =7)
	private Date createddate;
	
	@NotNull
	@Column(precision=22, scale=0)
	private BigDecimal createdby;

	@Column(precision=22, scale=0)
	private BigDecimal lastmodifiedby;
	
	@NotNull
	@Column(precision=22, scale=0)
	@OneToMany(mappedBy ="uomCategory")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	private Set<UOM> uoms = new HashSet<>();

	public UOMCategory() {
	}

	



	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(final String narration) {
		this.narration = narration;
	}

	public Date getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(final Date lastmodified) {
		this.lastmodified = lastmodified;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(final Date createddate) {
		this.createddate = createddate;
	}

	public BigDecimal getCreatedby() {
		return createdby;
	}

	public void setCreatedby(final BigDecimal createdby) {
		this.createdby = createdby;
	}

	public BigDecimal getLastmodifiedby() {
		return lastmodifiedby;
	}

	public void setLastmodifiedby(final BigDecimal lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}

	public Set<UOM> getUoms() {
		return uoms;
	}

	public void setUoms(final Set<UOM> uoms) {
		this.uoms = uoms;
	}

	public void setId(Long id) {
		this.id = id;
	}





	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
