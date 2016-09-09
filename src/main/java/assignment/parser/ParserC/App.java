package assignment.parser.ParserC;


import java.io.File;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.github.javaparser.ast.CompilationUnit;

public class App
{ 
    public static void main( String[] args ) throws Exception
    {
    	  String allclass="";
    	  String path="";String path_img="";
    	 // String path="D:\\CMPE202\\CASES\\uml-parser-test-4\\uml-parser-test-4\\";
    	if(args.length==2) 
    	{
    		path= args[0];
    		path_img= args[0];
    	}
    	else
    System.out.println("Insufficient data");
        
    	    	  File[] filestrail = new File(path).listFiles();
        //	    showFiles(filestrail);
        	
           System.out.println( "Hello World!" );
     // String[] files= {"A","B","C","D"};//D:\\CMPE202\\CASES\\uml-parser-test-1\\uml-parser-test-1\\
     // String[] files={"ClassA", "ClassB"}; //case 3
         //  String[] files={"A1","A2","B1","B2","C1","C2","P"};//---case 2
         // String[] files={"ConcreteObserver","ConcreteSubject","Observer","Optimist","Pessimist","Subject","TheEconomy"};//-case 4
          // String[] files ={"ConcreteObserver","ConcreteSubject","Observer","Optimist","Pessimist","Subject","TheEconomy"};
          // String[] files={"Component","ConcreteComponent","ConcreteDecoratorA","ConcreteDecoratorB","Decorator","Tester"};//;case 5
    	//	String path="D:\\CMPE202\\CASES\\uml-parser-test-4\\uml-parser-test-4\\";
    		
            ParseFile obj =new ParseFile();
            ParseClass objc =new ParseClass();
      
         JSONObject eachclass = null ;
         int count=0;
         ArrayList<String> files = new ArrayList<String>();
         for (File file : filestrail) {
    	         {    
    	        	if(file.getName().indexOf(".java")>0)
    	        	{
    	        	

    	        files.add(file.getName());
    	      	  }
    	        }
    	    }
         
      for(int i=0;i<files.size();i++)
            {
    
    	    CompilationUnit cu = obj.getFileCompilationUnit(files.get(i),path);
    	  eachclass = objc.ClassDetails(cu);
    	  if(i!=0)
    	    allclass+= ",class-end"+eachclass;
    	  else   allclass+= eachclass;
    	
    	   }
      
    	//System.out.println("My partial List"+parsedString);
    //	System.out.println("My partial arraylist List"+allclass);
        new CodeParser(allclass,path,path_img);
       
        
    }

	private static void showFiles(File[] files) {
		// TODO Auto-generated method stub
		   for (File file : files) {
		        if (file.isDirectory()) {
		          //  System.out.println("Directory: " + file.getName());
		            showFiles(file.listFiles()); // Calls same method again.
		        } else {
		          //  System.out.println("File: " + file.getName());
		        }
		    }
		
	}
}
