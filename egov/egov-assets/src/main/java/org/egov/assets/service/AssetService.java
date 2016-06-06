package org.egov.assets.service;


import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.assets.model.Asset;
import org.egov.assets.repository.AssetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional(readOnly = true)
public class AssetService  {

	private final AssetRepository assetRepository;
	@PersistenceContext
private EntityManager entityManager;

	@Autowired
public AssetService(final AssetRepository assetRepository) {
	 this.assetRepository = assetRepository;
  }

	 @Transactional
	 public Asset create(final Asset asset) {
	return assetRepository.save(asset);
  } 
	 @Transactional
	 public Asset update(final Asset asset) {
	return assetRepository.save(asset);
	  } 
	public List<Asset> findAll() {
	 return assetRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	   }
	public Asset findByName(String name){
	return assetRepository.findByName(name);
	}
	public Asset findByCode(String code){
	return assetRepository.findByCode(code);
	}
	public Asset findOne(Long id){
	return assetRepository.findOne(id);
	}
	public List<Asset> search(Asset asset){
	return assetRepository.findAll();
	}
}