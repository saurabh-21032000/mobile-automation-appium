package tests.android;

import base.BaseTest;
import helpers.SignupHelper;
import pages.android.CallDetailsPage;
import utils.DataProviderUtils;
import utils.LogHelper;
import utils.TestDataHelper;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.Map;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

/**
 * ============================================================================
 * TEST CLASS: CallDetailsTest
 * ----------------------------------------------------------------------------
 * GOAL:
 * Validate that after signup, a user can navigate to Call Details,
 * select filters (State, City, Language, Field), and see relevant results.
 *
 * ARCHITECTURE:
 * - Hybrid Data Driven (CSV + Random)
 * - Page Object Model
 * - BDD-style Given / When / Then test flow
 * ============================================================================
 */
public class CallDetailsTest extends BaseTest {

  @Test
  public void CallDetails_TC01() {
   // LogHelper.startTest("CallDetails_TC01");
    SoftAssert softAssert = new SoftAssert();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    CallDetailsPage callDetailsPage = new CallDetailsPage(driver);

    try {
      /**
       * GIVEN: The user completes signup using hybrid data (CSV + random)
       */
      LogHelper.step("GIVEN: User completes signup flow");
      String mobile = TestDataHelper.generateMobileNumber();
      String fullName = TestDataHelper.generateFullName();
      String state = TestDataHelper.getRandomState();
      String city = TestDataHelper.getRandomCity(state);
      String dob = TestDataHelper.generateDateOfBirth();

      SignupHelper.completeSignupFlow(driver, mobile, fullName, state, city, dob);
      LogHelper.info("📝 Signup completed successfully for " + mobile);

      wait.until(ExpectedConditions.presenceOfElementLocated(
          By.xpath("//android.widget.ImageView[contains(@content-desc,'Profile')]")));
      Assert.assertTrue(callDetailsPage.isProfileIconVisible(),
          "❌ Profile icon not visible after signup");
      LogHelper.info("✅ Profile verified successfully");

      /**
       * WHEN: The user opens the Call Details section and applies filters
       */
      LogHelper.step("WHEN: User opens Call Details and selects filters");
      callDetailsPage.clickCallIcon();
      LogHelper.info("📞 Clicked on Call icon");

      String chosenState = TestDataHelper.getRandomState();
      String chosenCity = TestDataHelper.getRandomCity(chosenState);
      callDetailsPage.selectStateInCallScreen(chosenState);
      callDetailsPage.selectCityInCallScreen(chosenCity);
      callDetailsPage.selectPreferredLanguage("English");
      callDetailsPage.closeLanguagePopup();
      callDetailsPage.selectFieldOfExpertise("Civil Law");

      LogHelper.info("🏛️ Filters applied successfully - " +
          "State: " + chosenState + ", City: " + chosenCity +
          ", Language: English, Expertise: Civil Law");

      /**
       * THEN: The filtered call details should load correctly
       */
      LogHelper.step("THEN: Call details should load correctly");
      softAssert.assertTrue(callDetailsPage.isCallDetailsScreenLoaded(),
          "❌ Call details screen failed to load!");
      LogHelper.info("✅ Call details loaded successfully");

    } catch (Exception e) {
      LogHelper.error("❌ Test execution failed: " + e.getMessage(), e);
      Assert.fail("❌ Unexpected failure occurred: " + e.getMessage());
    } finally {
      softAssert.assertAll();
      LogHelper.endTest("CallDetails_TC01");
    }
  }
}
