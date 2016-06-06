package org.egov.assets.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.assets.model.CategoryPropertyType;
import org.egov.assets.repository.CategoryPropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional(readOnly = true)
public class CategoryPropertyTypeService  {

	private final CategoryPropertyTypeRepository categoryPropertyTypeRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public CategoryPropertyTypeService(final CategoryPropertyTypeRepository categoryPropertyTypeRepository) {
		this.categoryPropertyTypeRepository = categoryPropertyTypeRepository;
	}

	@Transactional
	public CategoryPropertyType create(final CategoryPropertyType categoryPropertyType) {
		return categoryPropertyTypeRepository.save(categoryPropertyType);
	} 
	@Transactional
	public CategoryPropertyType update(final CategoryPropertyType categoryPropertyType) {
		return categoryPropertyTypeRepository.save(categoryPropertyType);
	} 
	public List<CategoryPropertyType> findAll() {
		return categoryPropertyTypeRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}
	public CategoryPropertyType findByName(String name){
		return categoryPropertyTypeRepository.findByName(name);
	}
	public CategoryPropertyType findOne(Long id){
		return categoryPropertyTypeRepository.findOne(id);
	}
	public List<CategoryPropertyType> search(CategoryPropertyType categoryPropertyType){
		return categoryPropertyTypeRepository.findAll();
	}
}