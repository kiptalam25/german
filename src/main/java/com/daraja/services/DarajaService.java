package com.daraja.services;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.bytebuddy.asm.Advice.Return;

@Service
public class DarajaService {
	
	
	
	
	  public String login() {
	      
		  	try {
		  	    // request url
		  	    String url = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";//https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials

		  	    // create auth credentials
		  	    String authStr = "vI3S9AxjyjLvO6o0W19S7sHm0AKLWOlf:VpxUH5d00sCP4zBu";
		  	    String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

		  	    // create headers
		  	    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		  	    headers.add("Authorization", "Basic " + base64Creds);

		  	    // create request
		  	    HttpEntity request = new HttpEntity(headers);
		System.out.println("\n Requesting Authentication Token...");
		  	    // make a request
		  	    ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

		  	    //GmsAZhqGqZoEeDOXtwLllJkeiNnW
		  	    
		  	    
		  	    //get response
		  	    String json = response.getBody();
		  	    //convert string to json
				System.out.println(json);
					JSONObject jsonObject= new JSONObject(json) ;
					//getting token
					 String access_token=jsonObject.getString("access_token");
					 String timePeriod=jsonObject.getString("access_token");
					System.out.println("\n Token Granted...");
					return access_token;
		  	} catch (Exception ex) {
		  		System.out.println("\n"+ex.getMessage());
		  	    
		  	}
		return "Failed";
		  }
	  
	  public String Pay(long phone,String AccountReference,String TransactionDesc) {
		  
		  try {
			  String Token=login();
			  
			  System.out.println("Token is: ");
			  System.out.println(Token);
//                OkHttpClient client = new OkHttpClient().newBuilder().build();
//                MediaType mediaType = MediaType.parse("application/json");
//
//                Date d = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//                String myDate = sdf.format(d);
//
//
			  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			  Date d=new Date();
			  String myDate=sdf.format(d);
			  
                String originalInput = 174379 + "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919" + myDate;
                String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

			  
			  String url="https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
			  
                URL url2= new URL("https://mydomain.com/path");
                String callBackUrl="https://mydomain.com/path";
                JSONObject object = new JSONObject();
                object.put("BusinessShortCode", 174379);
                object.put("Password", encodedString);
                object.put("Timestamp", myDate);
                object.put("TransactionType", "CustomerPayBillOnline");
                object.put("Amount", 1);
                object.put("PartyA", phone);
                object.put("PartyB", 174379);
                object.put("PhoneNumber", phone);
                object.put("CallBackURL", url2);
                object.put("AccountReference", AccountReference);
                object.put("TransactionDesc", TransactionDesc);
//                254708374149l


    		 	RestTemplate restTemplate=new RestTemplate();
    	        org.springframework.http.HttpHeaders headers=new org.springframework.http.HttpHeaders();
    		 headers.setContentType(MediaType.APPLICATION_JSON);
    	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	        headers.add("Authorization","Bearer "+Token);
    	        HttpEntity<String> entity=new HttpEntity<String>(object.toString(),headers);
//    	        try {
    	        ResponseEntity<String> response= restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
                System.out.println("\n\n"+response+"\n\n");
                
                return response.toString();
            }catch(Exception e){
            	
                System.out.println(e.getMessage()+"-------------------------------Error");
                return e.getMessage();
            }
	  }


	  public String registerUrl(){

String Token=login();

JSONObject obj=new JSONObject();
obj.put("ShortCode", 600986);
obj.put("ResponseType", "Completed");
obj.put( "ConfirmationURL","https://mydomain.com/confirmation");
obj.put( "ValidationURL","https://mydomain.com/validation");

 String url="https://sandbox.safaricom.co.ke/mpesa/c2b/v1/registerurl";

		  RestTemplate restTemplate=new RestTemplate();
		  org.springframework.http.HttpHeaders headers=new org.springframework.http.HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  headers.add("Authorization","Bearer "+Token);
		  HttpEntity<String> entity=new HttpEntity<String>(obj.toString(),headers);
//    	        try {
		  ResponseEntity<String> response= restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
		  System.out.println("\n\n"+response+"\n\n");

		 return response.toString();
	  }
}
