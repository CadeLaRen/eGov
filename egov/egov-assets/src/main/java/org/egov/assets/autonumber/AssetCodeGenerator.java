package org.egov.assets.autonumber;

import org.egov.assets.model.Asset;

public interface AssetCodeGenerator {
	
	public String getNextNumber(Asset asset);
	
}