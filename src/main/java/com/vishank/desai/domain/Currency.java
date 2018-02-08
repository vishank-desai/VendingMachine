package com.vishank.desai.domain;

public enum Currency {

	PENNY(1), NICKLE(5), DIME(10), QUARTER(25),DOLLAR(100),TWODOLLAR(200),FIVEDOLLAR(500),TENDOLLAR(1000); 
	private int denomination; 
	private Currency(int denomination)
	{ 
		this.denomination = denomination; 
	} 
	public int getDenomination()
	{
		return denomination; 
	}
}
