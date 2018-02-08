package com.vishank.desai.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vishank.desai.Exception.VendingMachineException;
import com.vishank.desai.domain.Currency;
import com.vishank.desai.domain.Product;
import com.vishank.desai.service.VendingMachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("VendingMachine")
public class VendingMachineImpl implements VendingMachine {
	private Map<Currency, Integer> currencyMap = new HashMap<Currency, Integer>();
	private Map<String, Product> productMap = new HashMap<String, Product>();
	private Product currentProduct;
	private long currentBalance;
	private static Logger LOG = LoggerFactory.getLogger(VendingMachineImpl.class);

	public VendingMachineImpl() {
		initialize();
	}

	// 1. Add 5 coins/notes for each currency

	private void initialize() {
		LOG.info("Adding Currency to Vending Machine");
		for (Currency c : Currency.values()) {
			currencyMap.put(c, 5);
		}

		// Initialize with two products.
		// Product lays = new Product();
		// lays.setCount(5);
		// lays.setName("Lays");
		// lays.setPrice(350L);
		// productMap.put(lays.getName(), lays);
		// Product doritos = new Product();
		// doritos.setName("Doritos");
		// doritos.setPrice(450L);
		// productMap.put(doritos.getName(), doritos);

	}

	// 2. Add Product with Name,Quantity and Price. Exposed rest api to add product.
	@Override
	public void addProduct(Product product) {

		LOG.info("Adding Product");
		productMap.put(product.getName(), product);

	}

	// 3.Select the Product and check/get the price
	@Override
	public long getProductPrice(String productName) throws Exception {
		LOG.info("Get product price");
		Product result = productMap.get(productName);
		if (result != null) {
			currentProduct = result;
			return result.getPrice();
		} else {
			throw new VendingMachineException("ProductNotAvailableException",
					"The product is no more available in the vending machine");
		}

	}

	// 4. Insert Currency in lowest denomination. We have taken converted all
	// currency into cents
	@Override
	public void insertCurrency(Currency currency) {
		LOG.info("Insert Currency in Vending Machine");
		currentBalance = currentBalance + currency.getDenomination();
		incrementCurrency(currency);
	}

	@Override
	// 5. Check the balance and change. Decrement the count in the product
	public Product decrementProduct() throws Exception {
		LOG.info("Validating the balance and decrease the quantitity of product");
		if (currentBalance >= currentProduct.getPrice()) {
			if (hasSufficientChange()) {
				Product product = productMap.get(currentProduct.getName());
				product.setCount(product.getCount() - 1);
				productMap.put(currentProduct.getName(), product);
				return currentProduct;
			}
			throw new VendingMachineException("NotEnoughChangeException",
					"Cannot process the request due to lack of change");

		}
		long remainingBalance = currentProduct.getPrice() - currentBalance;
		throw new VendingMachineException("LessPaidException",
				"The Full amount of the product is " + currentProduct.getPrice() + " and you need to insert "
						+ remainingBalance + " in order to complete the proces");
	}

	@Override
	// 6. Get the remaining balance and update the currency in the vending machine.
	public List<Currency> collectChange() throws Exception {
		long changeAmount = currentBalance - currentProduct.getPrice();
		List<Currency> change = getChange(changeAmount);
		updateCurrencyMap(change);
		currentBalance = 0;
		currentProduct = null;
		return change;
	}

	@Override
	public List<Currency> getChange(long amount) throws Exception {
		List<Currency> changes = Collections.emptyList();

		if (amount > 0) {
			changes = new ArrayList<Currency>();
			long balance = amount;
			while (balance > 0) {
				Currency[] result = Currency.values();
				for (int i = result.length - 1; i > 0; i--) {
					if (balance >= result[i].getDenomination() && (getQuantity(result[i]) > 1)) {
						changes.add(result[i]);
						balance = balance - result[i].getDenomination();
						break;

					}

				}
			}
		}

		return changes;
	}

	@Override
	public void reset() {
		currencyMap.clear();
		productMap.clear();
		currentProduct = null;
		currentBalance = 0;
	}

	private boolean hasSufficientChange() {
		return hasSufficientChangeForAmount(currentBalance - currentProduct.getPrice());
	}

	private boolean hasSufficientChangeForAmount(long amount) {
		boolean hasChange = true;
		try {
			getChange(amount);
		} catch (Exception e) {
			return hasChange = false;
		}

		return hasChange;
	}

	private void updateCurrencyMap(List<Currency> change) {
		for (Currency c : change) {
			decrementCurrency(c);
		}
	}

	private void incrementCurrency(Currency currency) {
		Integer count = currencyMap.get(currency);
		currencyMap.put(currency, count + 1);

	}

	private void decrementCurrency(Currency currency) {
		Integer count = currencyMap.get(currency);
		currencyMap.put(currency, count - 1);

	}

	public int getQuantity(Currency currency) {
		Integer value = currencyMap.get(currency);
		return value == null ? 0 : value;
	}

}
