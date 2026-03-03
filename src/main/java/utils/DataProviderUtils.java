package utils;

import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;
import java.util.*;

public class DataProviderUtils {

  private static final String CSV_PATH = "src/test/resources/testdata/TestData.csv";

  @DataProvider(name = "csvDataProvider")
  public static Object[][] provideData(Method method) {
    String testName = method.getName();
    List<Map<String, String>> allData = CSVUtils.readCSV(CSV_PATH);

    return allData.stream()
        .filter(row -> testName.equalsIgnoreCase(row.get("TestCaseName")))
        .map(row -> new Object[] { row })
        .toArray(Object[][]::new);
  }
}
