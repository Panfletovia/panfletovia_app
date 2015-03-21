package com.panfletovia.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.panfletovia.enums.Constants;
import com.panfletovia.type.HttpMethod;
import com.panfletovia.utils.Application;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Utils;
import com.s2parking.activities.R;


public class ApiManager extends AsyncTask<String, Void, Boolean>{

	private String URI;
	private HttpMethod httpMethod;
	private BufferedReader br;
	private StringBuffer sb = new StringBuffer("");	
	private ApiListener apiListener;
	private JSONObject ojbParams = new JSONObject();
	private HttpResponse httpResponse;
	private HttpRequestBase httpRequest;
	private JSONObject response;
	private ConfigurationManager configuration;
	private final SimpleDateFormat dateTimeFormat;
	protected int responseStatus;
	private final ReentrantLock lock = new ReentrantLock();

	public ApiManager(String controller, HttpMethod httpMethod, ApiListener apiListener) {
		configuration = ConfigurationManager.get();
		this.URI = configuration.getString("URL_BASE") + "/" + controller + ".json"; 
		this.httpMethod = httpMethod;
		this.apiListener = apiListener;
		dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	}
	
	public ApiManager(String controller, String action, HttpMethod httpMethod, ApiListener apiListener) {
		configuration = ConfigurationManager.get();
		this.URI = configuration.getString("URL_BASE") + "/" + controller + "/" + action + ".json";
		this.httpMethod = httpMethod;
		this.apiListener = apiListener;
		dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	}
			
	@Override
	protected Boolean doInBackground(String... params) {
		boolean error = false;		
        try {
        	getParamsDefault();
        	HttpClient client = new DefaultHttpClient();
            if(httpMethod == HttpMethod.GET) {
            	httpRequest = new HttpGet();
            	httpRequest.setHeader("Content-Type", "text/plain; charset=utf-8");
            	getQueryString();
            	httpRequest.setURI(new URI(URI));
            } else if(httpMethod == HttpMethod.POST) {
            	httpRequest = new HttpPost(new URI(URI));
            } else if (httpMethod == HttpMethod.DELETE) {
            	httpRequest = new HttpDelete(new URI(URI));
            }
            //Se diferente de GET
            if (httpMethod != HttpMethod.GET) {
            	((HttpEntityEnclosingRequestBase)httpRequest).setEntity(new UrlEncodedFormEntity(Utils.mapToNameValuePairList(ojbParams)));	
            }
            
            httpResponse = client.execute(httpRequest);
            StatusLine statusLine = httpResponse.getStatusLine();
    		if (statusLine == null) {
    			throw new IllegalStateException("Response without status code");
    		}
    		responseStatus = statusLine.getStatusCode();
            br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null) {
            	sb.append(line);
            }
            br.close();
            line = sb.toString();                                  
			
			if (responseStatus == 200) {
				JSONArray jsonArray = new JSONArray(line);
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				response = jsonObject;
			} else {
				JSONObject jsonObject = new JSONObject(line);
				response = jsonObject;
				error = true;
			}
        } catch (Exception e) {
            error = true;
        }
        
        return error;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		lock.lock();
		apiListener.onStarted();
	}
	
	/**
	 * Método executado após concluir a requisição.
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		apiListener.onFinished();
		//Se true, então retornou erro.
		if (result) {
			apiListener.onFailed(response);
		} else {
			apiListener.onSucceed(response);
		}
		lock.unlock();
	}
	
	/**
	 * Adiciona um parâmetro na requisição.
	 * @param key
	 * @param value
	 */
	public void addParam(String key, String value) {
		try {
			ojbParams.put(key, value);	
		} catch (JSONException ex) {
			ex.printStackTrace();
		}		
	}
	
	/**
	 * Extrai a versão do Sprint. 
	 */
	public int getAppVersionToAPI() {
		String appVersion = Application.get().getString(R.string.app_version);		
		
		// Separa versão da aplicação por '.'
		String[] appSplit = appVersion.split("\\.");
		// Caso a divisão estiver ok, recupera o valor central da String que é a versão do Sprint.
		// Caso contrário, retorna 0.
		return appSplit.length > 0 ? Integer.parseInt(appSplit[1]) : 0;
	}// End Method 'getAppVersionToAPI'
	
	/**
	 * Retorna api_key da requisição.
	 * @param nsu
	 * @param serial
	 * @return
	 */
	private String generateKey(long nsu, String serial) {
		return Utils.md5("s2way" + nsu + serial);
	}
	
	/**
	 * Paramâtros default de cada requisição.
	 * @throws JSONException
	 */
	private void getParamsDefault() throws JSONException {
		long nsu = configuration.currentNSU();
		String serial = Utils.getDeviceId();
		int version = getAppVersionToAPI();
		
		nsu++;
		
		configuration.set("nsu", nsu + "");
		
		ojbParams.put(Constants.API_DEFAULT_API_KEY, generateKey(nsu, serial));
		ojbParams.put(Constants.API_DEFAULT_VERSION, version);
		ojbParams.put(Constants.API_DEFAULT_DATETIME, dateTimeFormat.format(new Date()));
		ojbParams.put(Constants.API_DEFAULT_MODEL, Constants.ANDROID);
		ojbParams.put(Constants.API_DEFAULT_NSU, nsu);
		ojbParams.put(Constants.API_DEFAULT_SERIAL, serial);
		ojbParams.put(Constants.API_DEFAULT_TYPE, Constants.ANDROID);
		ojbParams.put(Constants.API_DEFAULT_CLIENT, "1");
	}
	
	/**
	 * Monta a query string, quando a requisição for GET.
	 */
	private void getQueryString() {
		int count = 0;
		Iterator<String> it = ojbParams.keys();
		while (it.hasNext()) {
			String key = it.next();
			try {
				if (count > 0) {
					URI += "&" + key + "=" + ojbParams.get(key).toString();				
				} else {
					URI += "?" + key + "=" + ojbParams.get(key).toString();
				}
				count++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
