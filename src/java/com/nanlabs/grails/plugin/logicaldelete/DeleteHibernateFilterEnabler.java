package com.nanlabs.grails.plugin.logicaldelete;

import grails.plugins.hawkeventing.Event;
import grails.plugins.hawkeventing.annotation.Consuming;
import grails.plugins.hawkeventing.annotation.HawkEventConsumer;

import org.hibernate.Session;
import org.hibernate.engine.FilterDefinition;

@HawkEventConsumer
public class DeleteHibernateFilterEnabler {

	private FilterDefinition deleteHibernateFilter;

	@Consuming("hibernate.sessionCreated")
	public void enableDeleteHibernateFilter(Event event){
		Session session = (Session) event.getPayload();
		session.enableFilter(deleteHibernateFilter.getFilterName());
	}

	public void setDeleteHibernateFilter(FilterDefinition deleteHibernateFilter) {
		this.deleteHibernateFilter = deleteHibernateFilter;
	}
}
