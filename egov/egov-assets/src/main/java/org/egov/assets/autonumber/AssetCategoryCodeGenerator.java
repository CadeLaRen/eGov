package org.egov.assets.autonumber;

import org.egov.assets.model.AssetCategory;
import org.springframework.stereotype.Service;

@Service
public interface AssetCategoryCodeGenerator {
	
	public String getNextNumber(AssetCategory assetCategory);

}
