package org.egov.assets.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.validator.annotation.Required;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "egasset_category_propertytype")
@SequenceGenerator(name = CategoryPropertyType.SEQ, sequenceName = CategoryPropertyType.SEQ, allocationSize = 1)
public class CategoryPropertyType extends AbstractAuditable  {
	
	private static final long serialVersionUID = 7732635439217509220L;
	public static enum CategoryPropertyDataType
	{
		String, Number, Date, DateTime, MasterData, Enumeration
	}
	

	public static final String SEQ = "seq_egasset_category_propertytype";
 
	@Id
    @GeneratedValue(generator = CategoryPropertyType.SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Length(max = 50, min = 2)
	@Required
	private String name;
	
	@Enumerated(EnumType.STRING)
	private CategoryPropertyDataType dataType;
	
	
	@Length(max = 200)
	private String format;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryid")
	private AssetCategory assetCategory;
	
	private Boolean isActive;
	
	private Boolean isMandatory;
	
	 
	@Length(max = 300)
	private String enumValues;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	 
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AssetCategory getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(AssetCategory assetCategory) {
		this.assetCategory = assetCategory;
	}

	public String getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	public CategoryPropertyDataType getDataType() {
		return dataType;
	}

	public void setDataType(CategoryPropertyDataType dataType) {
		this.dataType = dataType;
	}

}
