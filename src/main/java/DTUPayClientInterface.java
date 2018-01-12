import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.function.Function;

public class DTUPayClientInterface {
	
	
	HttpURLConnection connection;
	private final String baseURL;// = "http://localhost:8080/clientRest/rest/client/";
	
	public DTUPayClientInterface(String host) {
		baseURL = host + "/clientRest/rest/client/";
	}
	
	
	public Client createAccount(String firstname, String lastName, String cpr) {
		Client client = null;
		try {
			
			JsonObject obj = connect("createclient", "POST", con -> {
				
				connection.setRequestProperty("Content-type", "application/json; charset=utf8");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				JsonObject jsonZip = new JsonObject()
						.add("firstname", firstname)
						.add("lastname", lastName)
						.add("cpr", cpr);
				return jsonZip;
			});
			
			client = new Client(
					obj.get("firstName").asString()
					, obj.get("lastName").asString()
					, obj.get("cpr").asString()
					, UUID.fromString(obj.get("uuid").asString())
					, obj.get("bankAccountID").asString()
			);
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return client;
	}
	
	public String obtainBarcode(String clientId) {
		String response = "";
		try {
			
			connect("obtainBarcode?" + clientId, "GET", null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private JsonObject connect(String path, String type, Function<HttpURLConnection, JsonObject> optionalConnectParms) throws IOException {
		URL url = new URL(baseURL + path);
		JsonObject jsonContent = null;
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(type);
		connection.setConnectTimeout(5000);
		if (optionalConnectParms != null)
			jsonContent = optionalConnectParms.apply(connection);
		connection.connect();
		
		if (type.equals("POST") && jsonContent != null) {
			OutputStream out = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			
			writer.write(jsonContent.toString());
			writer.close();
			out.close();
		}
		
		int responseCode = connection.getResponseCode();
		InputStream is;
		if (responseCode == 202) {
			is = connection.getInputStream();
		} else {
			is = connection.getErrorStream();
		}
		
		BufferedReader bs = new BufferedReader(new InputStreamReader(is));
		return Json.parse(bs.readLine()).asObject();
	}
}
