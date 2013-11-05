package com.nanlabs.grails.plugin.logicaldelete;

import java.lang.reflect.Modifier;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.grails.compiler.injection.GrailsASTUtils;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class LogicalDeleteASTTRansformation implements ASTTransformation{

	public final static String DELETED_FIELD_NAME = "deleted";
	public final static int CLASS_NODE_ORDER = 1;

	@Override
	public void visit(ASTNode[] nodes, SourceUnit source) {
		if(!validate(nodes))return;
		ClassNode classNode = (ClassNode)nodes[CLASS_NODE_ORDER];
		addDeletedProperty(classNode);
		implementDeletedDomainClassInterface(classNode);
	}

	private boolean validate(ASTNode[] nodes){
		return nodes != null && nodes[0] != null && nodes[1] != null;
	}

	private void addDeletedProperty(ClassNode node) {
		if(!GrailsASTUtils.hasOrInheritsProperty(node, DELETED_FIELD_NAME)){
			node.addProperty(DELETED_FIELD_NAME, Modifier.PUBLIC, new ClassNode(Boolean.class), ConstantExpression.FALSE, null, null);
		}
	}

	private void implementDeletedDomainClassInterface(ClassNode node){
		ClassNode iNode = new ClassNode(LogicalDeleteDomainClass.class);
		if(!iNode.implementsInterface(iNode)){
			node.addInterface(iNode);
		}
	}

}
