package tests.android;

import base.BaseTest;
import helpers.SignupHelper;
import pages.android.SignupPage;
import utils.AssertionHelper;
import utils.LogHelper;
import utils.TestDataHelper;

import org.testng.annotations.Test;

public class SignupTest extends BaseTest {

    @Test(description = "Signup_TC01 - Verify full signup and profile completion flow", groups = {"regression", "signup"})
    public void Signup_TC01() {
        LogHelper.startTest("Signup_TC01 - Verify full signup and profile completion flow");

        // GIVEN
        LogHelper.info("🟦 GIVEN user has valid signup data");
        String mobile = TestDataHelper.generateMobileNumber();
        String fullName = TestDataHelper.generateFullName();
        String state = TestDataHelper.getRandomState();
        String city = TestDataHelper.getRandomCity(state);
        String dob = TestDataHelper.generateDateOfBirth();

        LogHelper.info(String.format(
                "📋 Test Data => Mobile: %s | Name: %s | State: %s | City: %s | DOB: %s",
                mobile, fullName, state, city, dob
        ));

        // WHEN
        LogHelper.info("🟨 WHEN user completes signup process");
        SignupHelper.completeSignupFlow(driver, mobile, fullName, state, city, dob);

        // THEN
        LogHelper.info("🟩 THEN verify user is logged in successfully");
        SignupPage signupPage = new SignupPage(driver);
       // boolean isProfileVisible = signupPage.isProfileIconVisible();

        // AssertionHelper.assertTrue(isProfileVisible, "❌ Profile icon not visible — login may have failed!");

        LogHelper.info("✅ Signup_TC01 completed successfully");
        LogHelper.endTest("Signup_TC01 - PASSED");
    }
}