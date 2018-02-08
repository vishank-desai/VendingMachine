package com.vishank.desai.service;

import java.util.List;

import com.vishank.desai.domain.Currency;
import com.vishank.desai.domain.Product;

public interface VendingMachine {

	public void insertCurrency(Currency currency);

	public void reset();

	public void addProduct(Product product);

	long getProductPrice(String productName) throws Exception;

	List<Currency> getChange(long amount) throws Exception;

	List<Currency> collectChange() throws Exception;

	Product decrementProduct() throws Exception;

}
