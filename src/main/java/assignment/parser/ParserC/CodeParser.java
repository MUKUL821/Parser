package assignment.parser.ParserC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CodeParser {
	HashMap<String, String> finallist = new HashMap<String, String>();
	HashMap<String, String> relation = new HashMap<String, String>();
	ArrayList<String> resultRelation = new ArrayList<String>();
	ArrayList<String> resultsMethods = new ArrayList<String>();
	String classdetails = "";
	String interfacedetails = "";

	public CodeParser(String parsedString,String path, String path_img) throws ParseException, IOException {
		// for method parsing
		// ExperimentFinder objf =new ExperimentFinder();
		String[] arr = parsedString.split(",class-end");

		System.out.println("MY ALL DATA.." + parsedString);
		for (int i = 0; i < arr.length; i++) {
			// CLASS LIST PARSING
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(arr[i]);

			String name = (String) json.get("name");
			System.out.println("name" + name);
			if (json.get("isInterface").toString() == "false")
				classdetails += name;
			else
				interfacedetails+= (interfacedetails!="")? " " + name: name ;
			// classRelation(json);
		}
		System.out.println("---------------" + classdetails);
		System.out.println("---------------" + interfacedetails);
		for (int i1 = 0; i1 < arr.length; i1++) {

			// System.out.println("output is"+arr[i]);
			JSONParser parser1 = new JSONParser();
			/// JSONObject jsonn = (JSONObject)(arr[i].toString());
			JSONObject json1 = (JSONObject) parser1.parse(arr[i1]);
			// System.out.println("output is" + json1);
			ClassVarParser(json1, i1, arr);

			// classRelation(json);
		}
		System.out.println("complete list" + finallist.size());
		TreeMap<String, String> sortedMap = new TreeMap<String, String>(finallist);

		Iterator<String> iterator = sortedMap.keySet().iterator();
		while (iterator.hasNext()) {
			String mentry = iterator.next();
			// System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
			// String strval=mentry.getValue().toString().substring(beginIndex)
			System.out.println(sortedMap.get(mentry));
			if (!(mentry).contains("pattern") && !(mentry).contains("methods")) {
				String[] original = sortedMap.get(mentry).toString()
						.substring(1, sortedMap.get(mentry).toString().length() - 1).split(",");
				String[] str = sortedMap.get(mentry).toString().replaceAll("\\P{L}+", ",").split(",");
				// System.out.println("mentry.getValue().toString()" +
				// sortedMap.get(mentry));

				for (int j = 1; j < str.length; j++) {
					System.out.println(mentry + "STR ----" + str[j] + "" + original[j - 1]);
					relation.put(mentry + "-" + str[j], original[j - 1]);
					// relation.put(mentry.getKey().toString()+str[j],mentry.getKey()+str[j]);
				}

			} else {
				// System.out.println("jjjjjjjjjj"+(String) mentry.getKey());
				if (sortedMap.get(mentry) != "EMPTY") {
					String parsedstr = sortedMap.get(mentry);
				//	System.out.println("inn" + sortedMap.get(mentry));
					String interf = sortedMap.get(mentry).toString().substring(1, sortedMap.get(mentry).indexOf("|"));
					System.out.println("changing name of interface" + interf + "iiii" + interfacedetails);
					//System.out.println("index is"+interfacedetails.indexOf(interf));
					if (interfacedetails.indexOf(interf) > -1) {
						System.out.println("changing name of interface");
						parsedstr = sortedMap.get(mentry).toString().replace(interf, "[＜＜interface＞＞;" + interf);

					}
					System.out.println("parsedstr........."+parsedstr);
					resultsMethods.add(parsedstr);
				}
			}

		}

		// System.out.println("finalllllllllllllllllllllllllllllllll" +
		// relation);
		// its time to filter final
		TreeMap<String, String> sortedHashMap = new TreeMap<String, String>(relation);

		Iterator<String> keySetIterator = sortedHashMap.keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			String add="";
			System.out.println("key: " + key + " value: " + sortedHashMap.get(key));
			String[] str = key.split("-");
			String var = "";
			if (sortedHashMap.get(str[1] + "-" + str[0]) != null)
				var = sortedHashMap.get(str[1] + "-" + str[0]).toString();
			else
				var = "[" + str[0].toString() + "]";

			System.out.println("key made: " + str[1] + "-" + str[0] + " value: " + var);
			String relation_key1 = var.substring(0, var.indexOf("["));
			String var_var = var.substring(var.indexOf("[")+1, var.indexOf("]"));
					String relation_key2 =sortedHashMap.get(key).substring(0,sortedHashMap.get(key).indexOf("["));
					String key2 = sortedHashMap.get(key).substring(sortedHashMap.get(key).indexOf("[")+1,sortedHashMap.get(key).indexOf("]"));
					System.out.println("var" +var_var+ " key2 "+ key2);
             if(interfacedetails.contains(var_var))
             { var ="＜＜interface＞＞;"+var.substring(0,var.indexOf("["))+var_var+"]"; 
             System.out.println(":<<<<<<<<<<<<<"+var);
             var = relation_key1+var;}
           //  System.out.println(":<<<<<<<<<<<<<"+var); 
             if(interfacedetails.contains(key2))
             {  key2="[＜＜interface＞＞;"+sortedHashMap.get(key).substring(0,var.indexOf("["))+key2+"]"; 
             System.out.println("key with relatinkey"+relation_key2+key2); 
             key2=relation_key2+key2;
             }
             else key2=sortedHashMap.get(key);
            // String str2= (interfacedetails.indexOf(key2)>0)? "＜＜interface＞＞;"+sortedHashMap.get(key).substring(0,var.indexOf("["))+key2+"]": sortedHashMap.get(key);
			
           
			add =var + "-" +key2;
			System.out.println("TO ADD"+add);
		
			resultRelation.add(add);
			sortedHashMap.remove(str[1] + "-" + str[0]);
			sortedHashMap.remove(str[0] + "-" + str[1]);
			keySetIterator = sortedHashMap.keySet().iterator();

		}
	//	System.out.println("RESULT METHDS"+resultsMethods.toString());
		//System.out.println("RESULT relation"+resultRelation.toString());
		// result relation keep the record between classes
	//	String resultRelationString = packageConversion(interfacedetails,resultRelation.toString());
		//String resultsMethodsString = packageConversion(interfacedetails,resultsMethods.toString());
		
		//.replace("||","|").replace("[[", "[").replace("]\n]", "]").replace("]\n,","]")
		String resultRelationString = resultRelation.toString().replace("\n,", ",");
		String resultsMethodsString=resultsMethods.toString().replace("||","|").replace("[[", "[").replace("]]", "]")+",";
			System.out.println("final resultttttt" + resultsMethodsString);
			System.out.println("final resul relationship" + resultRelationString);
			//System.out.println("FINALL "+resultsMethodsString.concat(resultRelationString).replace("][","").replace("\n],[", ",").replace("\n", ","));
			String output= resultsMethodsString.concat(resultRelationString).trim().replace("\n],[",",").replace("\n", "");
			System.out.println("OUTOTTT"+output);
            GenerateUML.generateUMLDiagram(output,path,path_img);
			
	}

	private String ClassVarParser(JSONObject eachclass, int i, String[] arr) throws ParseException {
		List<String> classNames = new ArrayList<String>();

		String parsepattern = "";
		System.out.println("CLASSSSSS" + eachclass.get("name") + "---------------------------" + eachclass);
		if (eachclass.get("isInterface") != "true") {// not working..utlit it
														// changes to.toString()

			System.out.println("is not interface" + eachclass.get("variables").toString());
			// JSONObject varr= (JSONObject) eachclass.get("variables");
			// System.out.println("--"+varr.get("variable-data-type"));
			JSONArray jArray = new JSONArray();
			// FOR VARIABLE...............................
			jArray = (JSONArray) eachclass.get("variables");
			String parsemethods = ClassMethodParser(eachclass, i);

			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = jArray.iterator();
			while (iterator.hasNext()) {
				String sign = "", dt = "";
				JSONObject iter = new JSONObject();
				iter = iterator.next();
				// System.out.println("working"+iterator.next().get("variable-modifier"));

				switch (iter.get("variable-modifier").toString()) {
				case "1":
					sign = "+";
					break;
				case "2":
					sign = "-";
					break;
				default:
					sign = "";
				}
				// String reserveDatatypes= "String, int,
				// float,double,char,array";
				String str = iter.get("variable-data-type").toString();
				if (str.indexOf("[") > 0)
					dt = str.substring(0, str.indexOf("[")) + "(*)";
				// change it to regular expression
				else if (str.indexOf("<") > 0 || (!str.contains("int") && !str.contains("float")
						&& !str.contains("String") && !str.contains("double"))) {
					classNames.add(classRelation(str));
					// NEED TO MAKE FUNCTION for class-class relation...question
					// 1
					dt = "";
				} else
					dt = str;

				if (dt != "" && sign != "") {
					if (sign.contains("-")) {
						// System.out.println("yipi"+parsemethods+"-------"+"set"+iter.get("variable-name").toString());
						String search = iter.get("variable-name").toString().substring(0, 1).toUpperCase()
								+ iter.get("variable-name").toString().substring(1) + "()";
						// System.out.println("search" + search);
						if (parsemethods.indexOf("get" + search) > -1 && parsemethods.indexOf("set" + search) > -1) {
							// System.out.println("yipiessssssssss");
							sign = "+";
							String[] str1 = parsemethods.split(";");
							parsemethods = "";
							for (int k = 0; k < str1.length; k++) {
								if (str1[k].contains(search))
									str1[k] = "";

								parsemethods += str1[k];

							}
						}

					}
					parsepattern += sign + iter.get("variable-name") + ":" + dt + ";";

				}
				//////////////////////// variable
				//

			}
			if (parsepattern.length() > 0)
				parsepattern = parsepattern.substring(0, parsepattern.length() - 1);
			System.out.println("PASER pattern .." + parsepattern);
		    if(eachclass.get("constructor").toString().length()>0)
		    	{ 
		    System.out.println("in yes"+eachclass.get("constructor")+eachclass.get("constructor-param")+ eachclass.get("constructor-param").toString().length());
		    	String construct="";
		    	if(eachclass.get("constructor-param").toString().length()>1)
		    	parsemethods+=";"+eachclass.get("name")+"("+eachclass.get("constructor-param").toString().split(" ")[1].substring(0,eachclass.get("constructor-param").toString().split(" ")[1].length()-1)+":"+eachclass.get("constructor-param").toString().split(" ")[0]+")";
		    	if(eachclass.get("constructor-param")!=null || eachclass.get("constructor-param").toString().length()>0)
		    		if(interfacedetails.contains(eachclass.get("constructor-param").toString().split(" ")[0]))
		             { construct ="["+eachclass.get("name")+"]uses-.->[＜＜interface＞＞;"+eachclass.get("constructor-param").toString().split(" ")[0]+"]";
		         	resultRelation.add("\n"+construct);
		         	System.out.println("added");
		             }
		    	}
			if (parsemethods.length() > 1)
				parsepattern += "|" + parsemethods;

			String ext = eachclass.get("extends").toString();
			if (ext.length() > 0)
				resultRelation.add("\n"+eachclass.get("extends").toString() );
				//parsepattern += "]" + "\n" + eachclass.get("extends").toString();
			String imp = eachclass.get("implements").toString();
			if (imp.length() > 0)
				resultRelation.add("\n"+eachclass.get("implements").toString() );
			
			//	parsepattern += "]" + eachclass.get("implements").toString();
			if (parsepattern.trim().length()>0) {
               // if(eachclass.get("isInterface").toString()=="true")
                //	parsepattern = "＜＜interface＞＞"+eachclass.get("name") + "|" + parsepattern;
                //else
				System.out.println("----"+parsepattern);
				parsepattern = eachclass.get("name") + "|" + parsepattern;
				parsepattern = "[" + parsepattern + "]" + "\n";
			} else
				parsepattern = "EMPTY";

		} else
			System.out.println("is interface");
		System.out.println("list of linked objs are" + classNames);

		finallist.put((String) eachclass.get("name"), classNames.toString());
		if (parsepattern != "EMPTY")
			finallist.put("pattern " + (String) eachclass.get("name"), parsepattern);
		// uses lists finallist.put("methods "+(String) eachclass.get("name"),
		// parsemethods);

		return parsepattern;
	}

	private String ClassMethodParser(JSONObject eachclass, int i) {
		String parsemethods = "";
		// System.out.println("yipeiii gotcha"+eachclass);
		if (eachclass.get("isInterface") != "true") {

			// System.out.println("is not
			// interface"+eachclass.get("variables").toString());
			// JSONObject varr= (JSONObject) eachclass.get("methods");
			// System.out.println("--"+varr.get("method-return-type"));
			JSONArray jArray = new JSONArray();
			// FOR methods..............................
			jArray = (JSONArray) eachclass.get("methods");

			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = jArray.iterator();
			while (iterator.hasNext()) {
				String sign = "";
				JSONObject iter = new JSONObject();
				iter = iterator.next();
				System.out.println(
						"working" + iter.get("method-modifier").toString() + iter.get("method-name").toString());

				// sign=(iter.get("method-modifier").toString()=="1")?"+":"";
				switch (iter.get("method-modifier").toString()) {
				case "1":
				case "9":
				case "1025":
					sign = "+";
					break;
				default:
					sign = "";
				}
				if (sign != "" && !(iter.get("method-modifier").toString().equals("9")))
					parsemethods += sign+iter.get("method-name") + "():" + iter.get("method-return-type");//////////////////////// variable
                if(iter.get("method-modifier").toString().equals("9")){
                	parsemethods += sign+iter.get("method-name") + "("+iter.get("method-param")+"):" + iter.get("method-return-type") ;//////////////////////// variable
                	 if(interfacedetails.contains(iter.get("method-body").toString().replaceAll("[^A-Za-z0-9]", "")))//need too modify..it will only work in main n only for one ne                     else  if(classdetails.contains(iter.get("method-body").toString().replaceAll("[^A-Za-z0-9[ ]", " ")))	    
                     	resultRelation.add("["+eachclass.get("name")+"]-.->[＜＜interface＞＞;"+iter.get("method-body").toString().replaceAll("[^A-Za-z0-9]", "")+"]");
                	 System.out.println("-----RESULT RELATION"+resultRelation.toString()+interfacedetails+iter.get("method-body").toString().replaceAll("[^A-Za-z0-9]", "")+"]");
                     
                }
                int count = iter.get("method-usedd").toString().length() - iter.get("method-usedd").toString().replace("＞＞", "").length();
			//	System.out.println("count in method usedd."+ iter.get("method-usedd").toString()+"class details"+classdetails);
                if (iter.get("method-usedd").toString().indexOf("＞＞") > 0 && count==2) {
					Set<String> hs = new HashSet<String>();
					hs.addAll(resultRelation);
					(resultRelation).clear();
					String strr = iter.get("method-usedd").toString().substring(
							iter.get("method-usedd").toString().indexOf(";") + 1,
							iter.get("method-usedd").toString().length() - 1);
					System.out.println("strr" + strr+interfacedetails.indexOf(strr)+classdetails.indexOf(strr));
					if (interfacedetails.indexOf(strr)>-1)
					{///	parsemethods += iter.get("method-usedd").toString() + ";";//////////////////// RECHECK for case 1,2,3
						hs.add(iter.get("method-usedd").toString());
						System.out.println("addeddddddddd" + strr);
					}															//////////////////// FOR
					
					(resultRelation).clear();
					resultRelation.addAll(hs);
				}

			}
			System.out.println("PARSE METHODS...." + parsemethods);
		}
		return parsemethods;

	}

	private String classRelation(String str) {
		String clasobj = "";
		if (str.indexOf("<") > 0)
			clasobj = "*[" + str.substring(str.indexOf("<") + 1, str.length() - 1) + "]";
		else
			clasobj = "1[" + str + "]";
		return clasobj;

	}
}
