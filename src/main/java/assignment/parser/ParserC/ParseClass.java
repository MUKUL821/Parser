package assignment.parser.ParserC;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.javaparser.ast.CompilationUnit;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class ParseClass {
	
	private static String classname="";
	 static boolean classtype;
	public static JSONObject classDetails=new JSONObject();
	String dtypes= "byte, short, int, long, float, double, boolean, char,String,[]";
	// public static void main(String[] args) throws Exception { }
	 public JSONObject ClassDetails(CompilationUnit cu){
	   // visit and print the methods names
	        System.out.println("yipiee1");
	        ClassVisitor classD =new ClassVisitor();
	        classD.visit(cu, null);
	        return classD.getDetails();
	        
	 }

	    /**
	     * Simple visitor implementation for visiting MethodDeclaration nodes. 
	     */
	    private static class ClassVisitor extends VoidVisitorAdapter<Object> {

	        @SuppressWarnings("unchecked")
			@Override
	        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
	    System.out.println("CALLED...........................");
		 classDetails.put("name", n.getName());
		 classname=n.getName();

           classtype= n.isInterface();
   
	    List<ClassOrInterfaceType> extend= n.getExtends();
	  
		   System.out.println("it extends"+extend);
		   if(extend!=null)
		   { for (int i = 0; i < extend.size(); i++) {
				System.out.println(extend.get(i));
				String query1="["+extend.get(i)+"]^-["+n.getName()+"]";
				classDetails.put("extends",query1 );
			}}
		   else
			 classDetails.put("extends", "");  
		   List<ClassOrInterfaceType> implement =n.getImplements();
			  
		   System.out.println("it implements"+implement);
		   String query2="";
		   if(implement!=null)
		   {  for (int i = 0; i < implement.size(); i++) {
				System.out.println(implement.get(i));
				query2 +="[＜＜interface＞＞;"+implement.get(i)+"]^-.-["+n.getName()+"]"+"\n";
				
			}
		   }
		   else query2="";
		   classDetails.put("implements",query2);
	 classDetails.put("isInterface", n.isInterface());
//	classDetails.put("extends", n.getExtends());
	//classDetails.put("implements",n.getImplements());

	//getting the list of member of class..to get list of variable and methods
	 MethodVisitor methods = new MethodVisitor();
	methods.visit(n, null);
	
	 FieldVisitor fields = new FieldVisitor();
	 fields.visit(n, null);
	 ConstructorVisitor construct = new ConstructorVisitor();
	 construct.visit(n, null);
		
		classDetails.put("variables", fields.getDetails());
		classDetails.put("methods", methods.getDetails());
		classDetails.put("constructor", construct.getDetails());
		
		System.out.println("class details are"+classDetails);
	        }
	        public JSONObject getDetails(){
		        return classDetails;
		        	
		        }
	    }
	    
	    private static class FieldVisitor extends VoidVisitorAdapter<Object> {
	    
	   JSONArray varlist =new JSONArray();
	 
	        @SuppressWarnings("unchecked")
			@Override
	        public void visit(FieldDeclaration n, Object arg) {
	        JSONObject item = new JSONObject();
	        //	System.out.println("in field class");
	        	 item.put("variable-name",n.getVariables().get(0).toString()); //list.add(item);
	        	 item.put("variable-modifier", n.getModifiers()); //list.add(item);//0:public,1:private,4:protected
	        	 //item.put("variable-type", n.getVariables().get(0).toString());
	        	 item.put("variable-data-type", n.getType().toString());
	        	 varlist.add(item);
	        	
	        }
	        public JSONArray getDetails(){
	        return varlist;
	        	
	        }
	       
	        
	    }
	    private static class ConstructorVisitor extends VoidVisitorAdapter<Object> {
		    
	 	  String constructorparam = "";
	 		String dtypes= "String[],byte, short, int, long, float, double, boolean, char,String ,[]";
	 		String strchk= null;
	 	        @SuppressWarnings("unchecked")
	 			@Override
	 	        public void visit(ConstructorDeclaration n, Object arg) {
	 	       
	 	        	System.out.println("in constructor class");
	 	        //	 item.put("constuctor-param", n.getParameters());
	 	        	if(n.getDeclarationAsString()!=null){
	 	        	constructorparam = n.getDeclarationAsString().replaceAll("public", "+");
	 	        	 strchk = n.getDeclarationAsString().trim().substring(n.getDeclarationAsString().trim().indexOf("(")+1, n.getDeclarationAsString().trim().lastIndexOf(""));
	 	        	System.out.println("in constructor class"+strchk);
	 	        	if(!dtypes.contains(strchk) )
		 	            strchk =n.getDeclarationAsString().trim().substring(n.getDeclarationAsString().trim().indexOf("(")+1, n.getDeclarationAsString().trim().lastIndexOf(""));
	 	        	
	 	        	}
	 	        	
	 	        	classDetails.put("constructor-param",strchk);	
	 	    	              }
	 	        public String getDetails(){
	 	        return constructorparam;
	 	        	
	 	        }
	 	       
	 	        
	 	    }
	    private static class MethodVisitor extends VoidVisitorAdapter<Object> {
	    	JSONArray list1 = new JSONArray();
	    	String dtypes= "String[],byte, short, int, long, float, double, boolean, char,String ,[]";
	    	
	        @SuppressWarnings("unchecked")
			@Override
	        public void visit(MethodDeclaration n, Object arg) {
	        
		    	JSONObject item1 = new JSONObject();
	        //	System.out.println("in field class"+ n.getParameters().size());
	        	 item1.put("method-name",n.getName().toString());
	        	 item1.put("method-modifier", n.getModifiers());//0:public,1:private,4:protected,9-,main
	        	 item1.put("method-param", n.getParameters().toString());
	             item1.put("method-param-type",n.getTypeParameters());
	        	 item1.put("method-return-type", n.getType().toString());
	        	 if(n.getModifiers()>8){
	        		   JSONArray varlist =new JSONArray();
	        		 String[] arr = n.getChildrenNodes().toString().split("\n");
	        		 for(String each : arr){
	        			if( each.indexOf("new")>0)
	        				{varlist.add(each.trim().split(" ")[0]);System.out.println("varlistt"+varlist);	 }
	        		 }
	        		 
	        		 item1.put("method-body",varlist);
	        	 }
	        	 
	        
	        	 
	        	 List<Parameter> usedby =n.getParameters();
				  
	  		   System.out.println("it usedby"+usedby);
	  		   String query3="";
	  		   if(usedby.toString().length()>0)
	  		   {  for (int i = 0; i < usedby.size(); i++) {
	  			   
	  				System.out.println(usedby.get(i)+" used in..");
	  				String []dt= usedby.get(i).toString().split("\\s+");
	  				if(!dtypes.contains(dt[0]))
	  				{
	  					
	  				//	if(classtype==true) NEED MODIFICATIONS
	  						
	  						if(classtype==true)
	  							query3 +="[＜＜interface＞＞;"+classname+"]"+"uses-.->[＜＜interface＞＞;"+dt[0]+"]";
	  						else
	  		  				query3 +="["+classname+"]"+"uses-.->[＜＜interface＞＞;"+dt[0]+"]";
	  						//System.out.println("yyyyyyyyyyyyyyyyyyyy"+query3);

	  				}
	  				
	  			}
	  		   }
	  		   else query3="";
	        	item1.put("method-usedd",query3);
	        	 
	        	 list1.add(item1);
	     
	        }

			public JSONArray getDetails() {
				return list1;
			}
	    
	    }
	
	
	    }

