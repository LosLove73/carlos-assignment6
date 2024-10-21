package com.coderscampus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileService {
	public List<TeslaSalesData> readFile(String fileName, String model) {
		List<TeslaSalesData> salesData = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;

			br.readLine();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yy");

			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");

				YearMonth yearMonth = YearMonth.parse(data[0].trim(), formatter);
				int sales = Integer.parseInt(data[1].trim());

				salesData.add(new TeslaSalesData(model, yearMonth.atDay(1), sales));
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return salesData;
	}

	public List<TeslaSalesData> readAllFiles() {
		List<TeslaSalesData> allSalesData = new ArrayList<>();

		List<TeslaSalesData> model3SalesData = readFile("model3.csv", "Model 3");
		allSalesData.addAll(model3SalesData);

		List<TeslaSalesData> modelSSalesData = readFile("modelS.csv", "Model S");
		allSalesData.addAll(modelSSalesData);

		List<TeslaSalesData> modelXSalesData = readFile("modelX.csv", "Model X");
		allSalesData.addAll(modelXSalesData);

		return allSalesData;
	}

}
