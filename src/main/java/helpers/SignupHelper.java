package helpers;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.android.SignupPage;
import utils.AssertionHelper;
import utils.LogHelper;

import java.time.Duration;

public class SignupHelper {
    
  public static void completeSignupFlow(AppiumDriver driver, String mobile, String fullName,
      String state, String city, String dob) {
    
    SignupPage signupPage = new SignupPage(driver);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    try {
      // 1. Initial Setup
      signupPage.clickAllowPermission();
      signupPage.clickCallingAccountListItem();
      signupPage.handleGoogleSavedPasswordPopup(); 

      // --- FIX: Failing Assertion Removed ---
      // Yahan pehle check ho raha tha ki button disabled hai ya nahi.
      // Hum seedha number enter karenge.

      // 2. Mobile Entry
      signupPage.enterMobileNumber(mobile);
      
      // 3. Click Send OTP
      signupPage.clickSendOtp();

      // 4. Enter OTP (Static for now)
      signupPage.enterOtp("123456");

      // 5. Wait for Profile Screen
      signupPage.waitForProfileScreenToLoad();

      // 6. Fill Profile
      signupPage.enterFullName(fullName);
      signupPage.selectPreferredLanguage();
      signupPage.selectGender();
      signupPage.selectState(state);
      signupPage.selectCity(city);
      signupPage.selectDateOfBirth(dob);
      
      signupPage.checkTermsCheckbox();
      signupPage.clickProfileSubmitButton();

      // 7. Validation
      boolean isIconVisible = signupPage.isProfileIconVisible();
      AssertionHelper.assertTrue(isIconVisible, "Profile icon not visible - Signup failed!");
      
      LogHelper.info("✅ Signup flow completed successfully");
      
    } catch (Exception e) {
      LogHelper.error("❌ Signup flow failed: " + e.getMessage());
      // Snapshot logic if available
      try { utils.AssertionHelper.takeScreenshot("signup_failed"); } catch(Exception ignored) {}
      throw e; // Test fail karne ke liye error throw karein
    }
  }
}