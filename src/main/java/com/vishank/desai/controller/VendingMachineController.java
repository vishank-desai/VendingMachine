package com.vishank.desai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vishank.desai.domain.Currency;
import com.vishank.desai.domain.Product;
import com.vishank.desai.service.VendingMachine;

@RestController
@RequestMapping("/vendingmachine/v1")
public class VendingMachineController {
	
	@Autowired
	VendingMachine vendingMachine;
	
	
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE },method = { RequestMethod.POST }, value = "/addProduct")
	@ResponseBody
	public void addProduct(@RequestBody Product product) throws Exception{
		vendingMachine.addProduct(product);
		
	}
	
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE },method = { RequestMethod.GET }, value = "/getProduct/{productName}")
	@ResponseBody
	public long getProduct(@PathVariable  String productName) throws Exception{
		long price = vendingMachine.getProductPrice(productName);
		return price ;
	}
	
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE },method = { RequestMethod.GET }, value = "/getChange/{amount}")
	@ResponseBody
	public List<Currency> getChange(@PathVariable int amount) throws Exception{
		List<Currency> currencyList = vendingMachine.getChange(amount);
		return currencyList ;
	}
	
	
	
	
	
	
}
