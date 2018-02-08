package com.vishank.desai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vishank.desai.Exception.VendingMachineException;
import com.vishank.desai.domain.Currency;
import com.vishank.desai.domain.Product;
import com.vishank.desai.factory.VendingMachineFactory;
import com.vishank.desai.service.VendingMachine;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class VendingMachineApplicationTests {

	private static VendingMachine vm;

	@BeforeClass
	public static void setUp() {
		vm = VendingMachineFactory.createVendingMachine();
	}

	@AfterClass
	public static void tearDown() {
		vm = null;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testBuyProductWithExactPrice() throws Exception {
		Product product =createProduct("Lays",350);
		vm.addProduct(product);
		long price = vm.getProductPrice("Lays");
		assertEquals(price, product.getPrice());
		vm.insertCurrency(Currency.TWODOLLAR);
		vm.insertCurrency(Currency.DOLLAR);
		vm.insertCurrency(Currency.QUARTER);
		vm.insertCurrency(Currency.QUARTER);
		Product resultProduct = vm.decrementProduct();
		assertEquals(4,resultProduct.getCount());
		List<Currency> change = vm.collectChange();
		assertEquals(0,change.size());
		
	}
	
	
	private Product createProduct(String name, long price) {
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setCount(5);
		return product;
		
		
	}

	@Test
	public void testBuyItemWithChangeRemaining() throws Exception
	{
		Product product =createProduct("Lays",350);
		vm.addProduct(product);	
		long price = vm.getProductPrice("Lays");
		vm.insertCurrency(Currency.FIVEDOLLAR);
		vm.decrementProduct();
		List<Currency> resultChanges = vm.collectChange();
		System.out.println("The ");
//		assertEquals(3,resultChanges.size());
		assertEquals(resultChanges.get(0), Currency.DOLLAR);
		assertEquals(resultChanges.get(1), Currency.QUARTER);
		assertEquals(resultChanges.get(2), Currency.QUARTER);
	}
	
	@Test
	public void testBuyProductWithLessCurrency()  {
		try {
		Product product =createProduct("Lays",350);
		vm.addProduct(product);	
		long price = vm.getProductPrice("Lays");
		vm.insertCurrency(Currency.TWODOLLAR);
		vm.decrementProduct();
		}
		catch (VendingMachineException e)
		{
			assertTrue(e.getTypeOfException().equals("LessPaidException"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBuyUnavailableProduct()  {
		try {
		// 	
		Product product =createProduct("Lays",1500);
		vm.addProduct(product);	
		long price = vm.getProductPrice("Doritos");
		}
		catch (VendingMachineException e)
		{
			assertTrue(e.getTypeOfException().equals("ProductNotAvailableException"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
