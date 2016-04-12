package assignment.parser.ParserC;

import java.io.FileInputStream;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class ParseFile {
// include STATIC setFileCompilationUnit in order to traverse all java files and making it array..fetching all java files dynamically
	public CompilationUnit getFileCompilationUnit(String file2, String path) throws Exception{
		
		FileInputStream in = new FileInputStream(path+file2);
        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
		return cu;
		
	
	}
	//TO AVOID ERROR FOR TIME BEING...........................
	public CompilationUnit getFileCompilationUnit() throws Exception{
		//D:\\CMPE202\\CASES\\Target1\\uml-parser-test-1\\A.java
		//D:\\CMPE202\\CASES\\uml-parser-test-3\\uml-parser-test-3\\ClassA.java
		FileInputStream in = new FileInputStream("D:\\CMPE202\\CASES\\uml-parser-test-3\\uml-parser-test-3\\ClassA.java");
        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
		return cu;
		
	
	}
}
