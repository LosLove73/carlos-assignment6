package com.coderscampus;

import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeslaSalesDataApplication {

	public static void main(String[] args) {

		FileService fileService = new FileService();
		TeslaSalesDataApplication analysis = new TeslaSalesDataApplication();

		// Read all sales data
		List<TeslaSalesData> allTeslaSalesData = fileService.readAllFiles();

		// Filter data once by model
		List<TeslaSalesData> model3SalesData = analysis.filterSalesDataByModel(allTeslaSalesData, "Model 3");
		List<TeslaSalesData> modelSSalesData = analysis.filterSalesDataByModel(allTeslaSalesData, "Model S");
		List<TeslaSalesData> modelXSalesData = analysis.filterSalesDataByModel(allTeslaSalesData, "Model X");

		// Generate reports for each model
		analysis.printYearlySalesReport("Model 3", model3SalesData);
		analysis.printTheBestAndWorstMonthsOfSales(model3SalesData, "Model 3");

		analysis.printYearlySalesReport("Model S", modelSSalesData);
		analysis.printTheBestAndWorstMonthsOfSales(modelSSalesData, "Model S");

		analysis.printYearlySalesReport("Model X", modelXSalesData);
		analysis.printTheBestAndWorstMonthsOfSales(modelXSalesData, "Model X");
	}

	public List<TeslaSalesData> filterSalesDataByModel(List<TeslaSalesData> salesData, String model) {
		return salesData.stream().filter(data -> data.getModel().equalsIgnoreCase(model.trim()))
				.collect(Collectors.toList());
	}

	public void printYearlySalesReport(String model, List<TeslaSalesData> modelSales) {
		Map<Integer, Integer> salesByYear = modelSales.stream().collect(Collectors.groupingBy(
				data -> Year.from(data.getDate()).getValue(), Collectors.summingInt(TeslaSalesData::getSales)));

		System.out.println(model + " Yearly Sales Report");
		System.out.println("---------------------------");
		salesByYear.forEach((year, sales) -> System.out.println(year + " -> " + sales));
		System.out.println();
	}

	public void printTheBestAndWorstMonthsOfSales(List<TeslaSalesData> modelSales, String model) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

		Optional<TeslaSalesData> bestMonth = modelSales.stream().max(Comparator.comparing(TeslaSalesData::getSales));

		Optional<TeslaSalesData> worstMonth = modelSales.stream().min(Comparator.comparing(TeslaSalesData::getSales));

		bestMonth.ifPresentOrElse(
				data -> System.out.println(
						"The best month for " + model + " was: " + YearMonth.from(data.getDate()).format(formatter)
								+ " with " + data.getSales() + " units sold."),
				() -> System.out.println("No sales data available for best month of " + model));

		worstMonth.ifPresentOrElse(
				data -> System.out.println(
						"The worst month for " + model + " was: " + YearMonth.from(data.getDate()).format(formatter)
								+ " with " + data.getSales() + " units sold."),
				() -> System.out.println("No sales data available for worst month of " + model));
		System.out.println();
	}
}
