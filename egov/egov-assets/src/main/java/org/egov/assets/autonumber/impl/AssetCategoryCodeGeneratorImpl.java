package org.egov.assets.autonumber.impl;

import java.io.Serializable;

import org.egov.assets.autonumber.AssetCategoryCodeGenerator;
import org.egov.assets.model.AssetCategory;
import org.egov.infra.persistence.utils.ApplicationSequenceNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetCategoryCodeGeneratorImpl implements AssetCategoryCodeGenerator {
	
	@Autowired
	private ApplicationSequenceNumberGenerator applicationSequenceNumberGenerator;
	
	public String getNextNumber(AssetCategory assetCategory)
	{
		String sequenceName = "seq_asset_category_code";
		Serializable nextSequence = applicationSequenceNumberGenerator.getNextSequence(sequenceName);
		return String.format("%5d", nextSequence);
	}

}
