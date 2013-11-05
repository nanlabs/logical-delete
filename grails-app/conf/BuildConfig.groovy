grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
	}

	plugins {
		build ':release:2.2.1', ':rest-client-builder:1.0.3', {
			export = false
		}
		compile(":hibernate:$grailsVersion") { export = false }
		compile(':hawk-eventing:0.5.1') {
			excludes 'svn'
		}
		compile(":hibernate-hijacker:0.8.1") {
			excludes 'svn'
		}
	}
}
