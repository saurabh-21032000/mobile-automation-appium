package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestDataHelper {

  private static final Random random = new Random();

  // --- Generate random 10-digit mobile number ---
  public static String generateMobileNumber() {
    String prefix = String.valueOf(7 + random.nextInt(3)); // 7,8,9
    long suffix = System.currentTimeMillis() % 1000000000L;
    return prefix + String.format("%09d", Math.abs(suffix));
  }

  // --- Generate random name ---
  public static String generateFullName() {
    String[] firstNames = { "Rohan", "Amit", "Priya", "Neha", "Rahul", "Sneha" };
    String[] lastNames = { "Sharma Automation", "Verma Automation", "Patel Automation", "Gupta Automation",
        "Singh Automation", "Yadav Automation" };
    return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
  }

  // --- Generate random DOB between 1970–2000 ---
  public static String generateDateOfBirth() {
    int year = 1970 + random.nextInt(31);
    int month = 1 + random.nextInt(12);
    int day = 1 + random.nextInt(27);
    LocalDate dob = LocalDate.of(year, month, day);
    return dob.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
  }

  // --- Random state & city ---
  public static Map<String, List<String>> stateCityMap = new HashMap<>() {
    {
      put("Bihar", Arrays.asList("Patna", "Gaya", "Bhagalpur"));
      put("Maharashtra", Arrays.asList("Mumbai", "Pune", "Nagpur"));
      put("Karnataka", Arrays.asList("Bengaluru", "Mysuru", "Mangaluru"));
    }
  };

  public static String getRandomState() {
    List<String> states = new ArrayList<>(stateCityMap.keySet());
    return states.get(random.nextInt(states.size()));
  }

  public static String getRandomCity(String state) {
    List<String> cities = stateCityMap.getOrDefault(state, List.of("Unknown City"));
    return cities.get(random.nextInt(cities.size()));
  }

  // --- Random expertise ---
  public static String getRandomExpertise() {
    List<String> expertiseList = Arrays.asList("Android", "iOS", "Backend", "QA", "Frontend");
    return expertiseList.get(random.nextInt(expertiseList.size()));
  }

  // --- Recharge test data ---
  public static Object[][] getRechargeTestData() {
    return new Object[][] {
        { generateMobileNumber(), generateFullName(), getRandomState(), getRandomCity(getRandomState()),
            generateDateOfBirth() }
    };
  }
}
