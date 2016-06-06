package org.egov.assets.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.assets.model.AssetCategory;
import org.egov.assets.model.CategoryPropertyType;
import org.egov.assets.repository.AssetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AssetCategoryService {

	private final AssetCategoryRepository assetCategoryRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public AssetCategoryService(
			final AssetCategoryRepository assetCategoryRepository) {
		this.assetCategoryRepository = assetCategoryRepository;
	}

	@Transactional
	public AssetCategory create(final AssetCategory assetCategory) {
		for(CategoryPropertyType cp:assetCategory.getCategoryProperties())
		{
			if(cp.getAssetCategory()==null)
			{
				cp.setAssetCategory(assetCategory);
			}
		}
		return assetCategoryRepository.save(assetCategory);
	}

	@Transactional
	public AssetCategory update(final AssetCategory assetCategory) {
		for(CategoryPropertyType cp:assetCategory.getCategoryProperties())
		{
		if(cp.getAssetCategory()==null)
		{
			cp.setAssetCategory(assetCategory);
		}
		}
		return assetCategoryRepository.save(assetCategory);
	}

	public List<AssetCategory> findAll() {
		return assetCategoryRepository.findAll(new Sort(Sort.Direction.ASC,
				"name"));
	}

	public AssetCategory findByName(String name) {
		return assetCategoryRepository.findByName(name);
	}

	public AssetCategory findByCode(String code) {
		return assetCategoryRepository.findByCode(code);
	}

	public AssetCategory findOne(Long id) {
		return assetCategoryRepository.findOne(id);
	}

	public List<AssetCategory> search(AssetCategory assetCategory) {
		return assetCategoryRepository.findAll();
	}
}