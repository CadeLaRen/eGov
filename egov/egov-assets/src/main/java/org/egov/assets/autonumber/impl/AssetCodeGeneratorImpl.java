package org.egov.assets.autonumber.impl;

import java.io.Serializable;

import org.egov.assets.autonumber.AssetCodeGenerator;
import org.egov.assets.model.Asset;
import org.egov.infra.persistence.utils.ApplicationSequenceNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetCodeGeneratorImpl implements AssetCodeGenerator {

	@Autowired
	private ApplicationSequenceNumberGenerator applicationSequenceNumberGenerator;
	
	public String getNextNumber(Asset asset)
	{
		String sequenceName = "seq_asset_code";
		Serializable nextSequence = applicationSequenceNumberGenerator.getNextSequence(sequenceName);
		return String.format("%05d", nextSequence);
	}
	
}
