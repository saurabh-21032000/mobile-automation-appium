package utils;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.*;

public class CSVUtils {

  private static final String CSV_PATH = "src/test/resources/testdata/TestData.csv";

  public static List<Map<String, String>> readCSV(String filePath) {
    List<Map<String, String>> dataList = new ArrayList<>();
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
      String[] headers = reader.readNext();
      if (headers == null)
        throw new RuntimeException("CSV file is empty: " + filePath);

      String[] values;
      while ((values = reader.readNext()) != null) {
        Map<String, String> row = new LinkedHashMap<>();
        for (int i = 0; i < headers.length; i++) {
          row.put(headers[i], i < values.length ? values[i] : "");
        }
        dataList.add(row);
      }
    } catch (Exception e) {
      throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
    }
    return dataList;
  }

  // Legacy method for backward compatibility
  public static Map<String, String> getRowData(String testCaseName) {
    List<Map<String, String>> allData = readCSV(CSV_PATH);
    for (Map<String, String> row : allData) {
      if (testCaseName.equalsIgnoreCase(row.get("TestCaseName"))) {
        return row;
      }
    }
    return new HashMap<>();
  }
}
