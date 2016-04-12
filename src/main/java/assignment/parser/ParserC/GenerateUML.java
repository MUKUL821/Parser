package assignment.parser.ParserC;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;


public class GenerateUML {


	public static void generateUMLDiagram(String output,String path, String img_name) throws IOException{

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			System.out.println("output..."+output);
			output = URLEncoder.encode(output, "UTF-8");
		
			String imageUrl = "http://yuml.me/diagram/scruffy/class/"+output;
			URL getRequest = new URL(imageUrl);
			System.out.println("imageurl"+imageUrl);

			String destinationFile = path+img_name;

			System.out.println("Output from Server .... \n"+destinationFile);
			InputStream is = getRequest.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
			//	while ((output1 = br.readLine()) != null) {
			//	System.out.println(output1);


			//	}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}





	}


}
