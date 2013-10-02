package com.nanlabs.grails.plugin.logicaldelete;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@GroovyASTTransformationClass({"com.nanlabs.grails.plugin.logicaldelete.LogicalDeleteASTTRansformation"})
public @interface LogicalDelete {

}
