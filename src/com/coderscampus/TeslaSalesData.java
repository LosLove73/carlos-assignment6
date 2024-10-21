package com.coderscampus;

import java.time.LocalDate;
import java.util.Date;

public class TeslaSalesData {
	private String model;
	private LocalDate date;
	private int sales;
	
	public TeslaSalesData (String model, LocalDate date, int sales) {
		this.model = model;
		this.date = date;
		this.sales = sales;
	}

	// Getters & Setters
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "TeslaSalesData [model=" + model + ", date=" + date + ", sales=" + sales + "]";
	}

	
	
	

}



