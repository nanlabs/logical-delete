import org.springframework.orm.hibernate3.FilterDefinitionFactoryBean

import com.nanlabs.grails.plugin.logicaldelete.DeleteHibernateFilterConfigurator
import com.nanlabs.grails.plugin.logicaldelete.DeleteHibernateFilterEnabler
import com.nanlabs.grails.plugin.logicaldelete.LogicalDeleteDomainClassEnhancer

class LogicalDeleteGrailsPlugin {
	def version = "0.2"
	def grailsVersion = "2.0 > *"
	def title = "Logical Delete Plugin"
	def description = 'Allows you to do a logical deletion of domain classes'
	def documentation = "http://grails.org/plugin/logical-delete"
	def loadAfter = ['hibernate']

	def license = "APACHE"
	def organization = [name: "NaN Labs", url: "http://www.nan-labs.com/"]
	def developers = [
		[name: "Ezequiel Parada", email: "ezequiel@nan-labs.com"]
	]
	def issueManagement = [system: 'GITHUB', url: 'https://github.com/nanlabs/logical-delete/issues']
	def scm = [url: 'https://github.com/nanlabs/logical-delete']

	def doWithSpring = {

		logicDeleteHibernateFilter(FilterDefinitionFactoryBean) {
			defaultFilterCondition = "deleted = 0"
		}

		deleteHibernateFilterConfigurator(DeleteHibernateFilterConfigurator) {
			deleteFilterDefinition = ref("logicDeleteHibernateFilter")
		}

		deleteHibernateFilterEnabler(DeleteHibernateFilterEnabler) {
			deleteHibernateFilter = ref("logicDeleteHibernateFilter")
		}
	}

	def doWithDynamicMethods = { ctx ->
		LogicalDeleteDomainClassEnhancer.setLogicDeleteFilter(ctx.getBean("logicDeleteHibernateFilter"))
		LogicalDeleteDomainClassEnhancer.enhance(application.domainClasses)
	}
}
