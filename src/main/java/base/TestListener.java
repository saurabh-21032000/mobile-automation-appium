package base;

import com.aventstack.extentreports.ExtentTest;
import core.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
  @Override
  public void onTestStart(ITestResult result) {
    ExtentTest t = ExtentManager.getReporter().createTest(result.getMethod().getMethodName());
    ExtentManager.setTest(t);
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    ExtentManager.getTest().pass("Test passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ExtentManager.getTest().fail(result.getThrowable());
  }

  @Override
  public void onTestSkipped(ITestResult result) {
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  @Override
  public void onStart(ITestContext context) {
  }

  @Override
  public void onFinish(ITestContext context) {
  }
}
