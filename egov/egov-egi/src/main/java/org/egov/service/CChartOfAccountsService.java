package org.egov.service;


import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.commons.CChartOfAccounts;
import org.egov.repository.CChartOfAccountsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional(readOnly = true)
public class CChartOfAccountsService  {

	private final CChartOfAccountsRepository cChartOfAccountsRepository;
	@PersistenceContext
private EntityManager entityManager;

	@Autowired
public CChartOfAccountsService(final CChartOfAccountsRepository cChartOfAccountsRepository) {
	 this.cChartOfAccountsRepository = cChartOfAccountsRepository;
  }

	 @Transactional
	 public CChartOfAccounts create(final CChartOfAccounts cChartOfAccounts) {
	return cChartOfAccountsRepository.save(cChartOfAccounts);
  } 
	 @Transactional
	 public CChartOfAccounts update(final CChartOfAccounts cChartOfAccounts) {
	return cChartOfAccountsRepository.save(cChartOfAccounts);
	  } 
	public List<CChartOfAccounts> findAll() {
	 return cChartOfAccountsRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	   }
	public CChartOfAccounts findByName(String name){
	return cChartOfAccountsRepository.findByName(name);
	}
	public CChartOfAccounts findOne(Long id){
	return cChartOfAccountsRepository.findOne(id);
	}
}