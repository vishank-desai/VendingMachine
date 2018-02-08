package com.vishank.desai.factory;

import com.vishank.desai.service.VendingMachine;
import com.vishank.desai.serviceimpl.VendingMachineImpl;

public class VendingMachineFactory {

	public static VendingMachine createVendingMachine() {
		return new VendingMachineImpl();
	}

}
