import com.eclipsesource.json.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {
	

	
	public User createAccount(String first, String last, String cpr) {
		
		InputStream is;
		String response = "";
		int responseCode = -1;
		try {
			
			URL url = new URL("http://localhost:8080/clientRest/rest/client/createclient");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			JsonObject clientJSON  = new JsonObject()
					.add( "firstname", first )
					.add( "lastname", last )
					.add("cpr", cpr);
			
		
			System.out.print(clientJSON.toString());
			
			
			
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/json; charset=utf8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			
			connection.setConnectTimeout(5000);
			connection.connect();
			
			OutputStream out = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			
			writer.write(clientJSON.toString());
			writer.close();
			out.close();
			
			responseCode = connection.getResponseCode();
			if(responseCode == 202){
				is = connection.getInputStream();
			}else{
				is = connection.getErrorStream();
			}
			
			BufferedReader bs = new BufferedReader(new InputStreamReader(is));
			response = bs.readLine();
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
