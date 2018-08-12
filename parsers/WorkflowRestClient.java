package parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class WorkflowRestClient {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnsupportedOperationException, IOException {
		
		HttpHost targetHost = new HttpHost("uat-alfresco-euro.saint-gobain.com", 2095, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
		 
		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());
		 
		// Add AuthCache to the execution context
		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		@SuppressWarnings("deprecation")
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://uat-alfresco-euro.saint-gobain.com:2095/alfresco/service/api/workflow-instances");
		
		HttpResponse response = client.execute(request, context);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		String line = "";
		JSONArray jsonArray = new JSONArray();

		while ((line = rd.readLine()) != null) {
			jsonArray.add(line);
		}
		System.out.println(jsonArray + "\n");
	}

}
