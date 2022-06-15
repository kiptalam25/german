package com.daraja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.daraja.services.DarajaService;

@RestController
public class Controller {

	@Autowired
	private DarajaService darajaService;
	@GetMapping("/test")
	public String test(){
		return "System Is Up And Running kitu inafanya";
	}
	@GetMapping("/login")
	public String loginsaf() {
		return darajaService.login();
	}
	
	@GetMapping("/pay/{phone}/{AccountReference}/{TransactionDesc}")
	public String pay(@PathVariable("phone") long phone,@PathVariable("phone") String AccountReference,@PathVariable("phone") String TransactionDesc) {
		return darajaService.Pay(phone,AccountReference,TransactionDesc);
	}
	@GetMapping("/registerUrl")
	public  String registerUrl(){
		return darajaService.registerUrl();
	}
	
}
