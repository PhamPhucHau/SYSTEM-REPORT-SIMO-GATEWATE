package com.org.shbvn.svbsimo.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import com.org.shbvn.svbsimo.configure.SimoNamedQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class AbstractRepository {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
	protected Environment env;

	@PersistenceContext(unitName = "entityManagerSimo")
	protected EntityManager entityManager;

	private RepositoryManageService repositoryManageService;

	@Autowired
	protected SimoNamedQueries simoNamedQueries;

	public RepositoryManageService getRepositoryManageService() {
		return repositoryManageService;
	}	

	@Autowired
	public void setRepositoryManageService(
		@Lazy @Qualifier("repositoryManageService") RepositoryManageService repositoryManageService) {
		this.repositoryManageService = repositoryManageService;
	}

}
