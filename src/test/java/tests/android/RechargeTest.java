package tests.android;

import base.BaseTest;
import helpers.SignupHelper;
import pages.android.RechargeFlow;
import utils.DataProviderUtils;
import utils.LogHelper;
import utils.TestDataHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Map;

/**
 * ============================================================================
 * TEST CLASS: RechargeTest
 * ----------------------------------------------------------------------------
 * GOAL:
 * Validate that after signup, a user can perform a wallet top-up
 * and see the balance increase correctly.
 *
 * ARCHITECTURE:
 * - Hybrid Data Driven (CSV + Random)
 * - Page Object Model
 * - BDD-style Given / When / Then test flow
 * ============================================================================
 */
public class RechargeTest extends BaseTest {

  @Test(dataProvider = "csvDataProvider", dataProviderClass = DataProviderUtils.class)
  public void Recharge_TC01(Map<String, String> data) {
    LogHelper.startTest("Recharge_TC01", data);
    SoftAssert softAssert = new SoftAssert();
    RechargeFlow rechargeFlow = new RechargeFlow(driver);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    try {
      /**
       * GIVEN: The user completes signup using hybrid data (CSV + random)
       */
      LogHelper.step("GIVEN: User completes signup flow");
      String amount = data.get("Amount");
      String mobile = TestDataHelper.generateMobileNumber();
      String fullName = TestDataHelper.generateFullName();
      String state = TestDataHelper.getRandomState();
      String city = TestDataHelper.getRandomCity(state);
      String dob = TestDataHelper.generateDateOfBirth();

      SignupHelper.completeSignupFlow(driver, mobile, fullName, state, city, dob);
      LogHelper.info("📝 Signup completed successfully for " + mobile);

      /**
       * WHEN: The user navigates to Wallet and performs a top-up
       */
      LogHelper.step("WHEN: User navigates to Wallet and performs top-up");
      rechargeFlow.clickWalletIcon();
      Assert.assertTrue(rechargeFlow.isWalletScreenVisible(), "❌ Wallet screen failed to open!");
      LogHelper.info("💰 Wallet screen opened successfully");

      int before = rechargeFlow.getWalletBalanceBeforeTopUp();
      rechargeFlow.performTopUp(Integer.parseInt(amount));
      LogHelper.info("💸 Top-up performed with amount: ₹" + amount);

      /**
       * THEN: The wallet balance should increase correctly
       */
      LogHelper.step("THEN: Wallet balance should increase after top-up");
      int after = rechargeFlow.getWalletBalanceAfterTopUp();
      LogHelper.info("💰 Before: ₹" + before + " | After: ₹" + after);
      Assert.assertTrue(after > before, "❌ Wallet balance did not increase after top-up!");
      LogHelper.info("✅ Wallet balance increased successfully");

    } catch (TimeoutException e) {
      LogHelper.error("❌ Element not visible within time: " + e.getMessage(), e);
      Assert.fail("❌ Element not visible within time: " + e.getMessage());
    } catch (Exception e) {
      LogHelper.error("❌ Unexpected error during wallet top-up: " + e.getMessage(), e);
      Assert.fail("❌ Unexpected failure: " + e.getMessage());
    } finally {
      softAssert.assertAll();
      LogHelper.endTest("Recharge_TC01");
    }
  }
}
