package com.nanlabs.grails.plugin.logicaldelete;

import grails.plugin.hibernatehijacker.hibernate.HibernateConfigPostProcessor;

import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Filterable;
import org.hibernate.mapping.PersistentClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteHibernateFilterConfigurator implements HibernateConfigPostProcessor{

	private static final Logger log = LoggerFactory.getLogger(DeleteHibernateFilterConfigurator.class);

	private FilterDefinition deleteFilterDefinition;

	@Override
	public void doPostProcessing(Configuration configuration) throws HibernateException {
		log.debug("-------------------Configuring logic-delete Hibernate filter----------------");

		addDeleteFilterDefinition(configuration);
		enrichLogicalDeleteClasses(configuration);
		enrichLogicalDeleteCollections(configuration);
	}

	private void enrichLogicalDeleteClasses(Configuration configuration) {
		Iterator<PersistentClass> mappingIterator = configuration.getClassMappings();
		while (mappingIterator.hasNext()) {
			PersistentClass persistentClass = mappingIterator.next();
			if (mustBeProcessed(persistentClass.getMappedClass())) {
				enrichLogicalDeleteClass(persistentClass);
			}
		}
	}

	private void enrichLogicalDeleteCollections(Configuration configuration) {
		Iterator<?> mappings = configuration.getCollectionMappings();
		while(mappings.hasNext()){
			Collection collection = (Collection) mappings.next();
			if (mustBeProcessed(collection.getOwner().getMappedClass()) && collection.isOneToMany()){
				log.debug("Enabling delete filter for collection class {}", collection.getRole());
				addFilter(collection);
			}
		}
	}

	private void enrichLogicalDeleteClass(PersistentClass persistentClass) {
		log.debug("Enabling delete filter for domain class {}", persistentClass.getClassName());
		addFilter(persistentClass);
	}

	private void addFilter(Filterable filterable) {
		String filterName = deleteFilterDefinition.getFilterName();
		String condition = deleteFilterDefinition.getDefaultFilterCondition();
		filterable.addFilter(filterName, condition);
	}

	private void addDeleteFilterDefinition(Configuration configuration) {
		log.debug("Defining Delete Hibernate filer ---- ");
		configuration.addFilterDefinition(deleteFilterDefinition);
	}

	private boolean mustBeProcessed(Class<?> mappedClass) {
		return LogicalDeleteDomainClass.class.isAssignableFrom(mappedClass);
	}

	public void setDeleteFilterDefinition(FilterDefinition deleteFilterDefinition) {
		this.deleteFilterDefinition = deleteFilterDefinition;
	}
}
