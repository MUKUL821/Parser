package assignment.parser.ParserC;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.FieldDeclaration;
public class ParseFields  extends ParseFile {
	 public static void main(String[] args) throws Exception {
		 ParseFile obj = new ParseFile();
		 //returning the cu file from each .jva file
	      CompilationUnit cu= obj.getFileCompilationUnit();
	        // visit and print the methods names
	        System.out.println("yipiee1");
	        new FieldVisitor().visit(cu, null);
	      
	    }
	    /**
	     * Simple visitor implementation for visiting MethodDeclaration nodes. 
	     */
	    private static class FieldVisitor extends VoidVisitorAdapter {

	        @Override
	        public void visit(FieldDeclaration n, Object arg) {
	        	
	            // here you can access the attributes of the method.
	            // this method will be called for all methods in this 
	            // CompilationUnit, including inner class methods
	            System.out.println(n.getType()+" type "+n.getVariables()+"  var  "+n.getModifiers()+"  modis");
	            super.visit(n, arg);
	        }
	    }
}
