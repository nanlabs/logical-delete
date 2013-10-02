package com.nanlabs.grails.plugin.logicaldelete;

import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper
import org.junit.Test

import com.nanlabs.grails.plugin.logicaldelete.LogicalDeleteASTTRansformation;

class LogicalDeleteASTTransformationTest {

	@Test
	public void test() {
		def file = new File("test/unit/com/nanlabs/grails/plugin/logicaldelete/LogicalDeleteTest.groovy")
		assert file.exists()
		def invoker = new TransformTestHelper(new LogicalDeleteASTTRansformation(), CompilePhase.CANONICALIZATION)
		def clazz = invoker.parse(file)
		def test = clazz.newInstance()
		test.deleted = true
		assert test.deleted
	}

}
