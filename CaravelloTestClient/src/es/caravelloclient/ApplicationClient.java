package es.caravelloclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import es.caravelloclient.response.ResponseWrapper;
import es.caravelloclient.bean.request.Income;
import es.caravelloclient.bean.request.Provider;
import es.caravelloclient.bean.request.RequestWrapper;
import es.caravelloclient.bean.request.Requester;
import es.caravelloclient.bean.request.Subscription;
import es.caravelloclient.bean.request.Survey;
import es.caravelloclient.bean.request.Target;
import es.caravelloclient.bean.request.params.Channel;
import es.caravelloclient.bean.request.params.Country;
import es.caravelloclient.bean.request.params.Currency;
import es.caravelloclient.bean.request.params.Frecuency;
import es.caravelloclient.bean.request.params.Gender;

public class ApplicationClient {
	
	public static void main(String[] args) throws Exception {
		
		String rWrapperStr = "{ \"requester\": {\"id\": \"CVO\",\"name\": \"Caravelo\"},\"provider\": {\"id\": \"TGI\",\"name\": \"Kantar\"},\"survey\": {\"subject\": 81111600,";         
		rWrapperStr +=	"\"target\": {\"gender\": \"F\",\"age\": [30, 60],\"income\": {\"currency\": \"EUR\",\"range\": [20000, 40000]}},\"country\": \"ES\"},";
		rWrapperStr += "\"subscription\": {\"frequency\": \"weekly\",\"channel\": [\"postal\", \"mail\", \"api\", \"ftp\" ]}}";
		
		
		//-->GET SYNC
		final String uriTest = "http://localhost:8090/getmarketsurvey?marketid=2";
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uriTest, String.class);
		System.out.println("responseData GET response (" + response + ")");
		
		//-->POST SYNC
		final String uriTest_1 = "http://localhost:8090/getmarketsurveyslist";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(rWrapperStr, headers);
		
		response = restTemplate.postForObject(uriTest_1, request, String.class);
		System.out.println("responseData POST response (" + response + ")");
		
		
		//-->POST ASYNC
		final String uriTest_2 = "http://localhost:8090/asyncgetmarketsurveyslist";
		AsyncRestTemplate asyncTemplate = new AsyncRestTemplate(); 
		
		HttpHeaders headers_1 = new HttpHeaders();
		headers_1.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request_1 = new HttpEntity<>(rWrapperStr, headers_1);
		
		ListenableFuture<ResponseEntity<String>> data  = asyncTemplate.exchange(uriTest_2, HttpMethod.POST ,request_1, String.class);
		ResponseEntity<String> res = data.get();
		System.out.println("responseData POST Async response (" + res.getBody() + ")");	
		
	}

}
