package utils;

import java.util.List;
import java.util.Map;

public class CSVReaderTest {
  public static void main(String[] args) {
    // Test the new readCSV method
    List<Map<String, String>> allData = CSVUtils.readCSV("src/test/resources/testdata/TestData.csv");
    System.out.println("All CSV Data:");
    for (Map<String, String> row : allData) {
      System.out.println(row);
    }

    // Test the legacy getRowData method
    Map<String, String> data = CSVUtils.getRowData("Recharge_TC01");
    System.out.println("\nLegacy getRowData for Recharge_TC01: " + data);
  }
}
