package pages.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogHelper;
import utils.WaitHelper;

import java.time.Duration;

public class RechargeFlow {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'₹')]/following-sibling::android.widget.TextView[1]")
    private WebElement walletAmount;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"\"]")
    private WebElement walletIcon;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Wallet\"]")
    private WebElement walletScreenTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Top Up\"]")
    private WebElement walletTopupButton;

    // Wallet Top-Up Screen Elements
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Set Amount']")
    private WebElement setAmountTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹100']")
    private WebElement predefined100Btn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹200']")
    private WebElement predefined200Btn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹300']")
    private WebElement predefined300Btn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹400']")
    private WebElement predefined400Btn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹500']")
    private WebElement predefined500Btn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹600']")
    private WebElement predefined600Btn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹700']")
    private WebElement predefined700Btn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+ ₹800']")
    private WebElement predefined800Btn;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Custom']")
    private WebElement customAmountInput;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add Money']")
    private WebElement addMoneyBtn;

    private static final By TOPUP_MODAL_ROOT = By.xpath(
            "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]");

    private static final By MODAL_TITLE = By.xpath(".//android.widget.TextView[@text='Top-up Successful']");
    private static final By MODAL_LINE1 = By.xpath(".//android.widget.TextView[@text='Your recharge was successful.']");
    private static final By MODAL_LINE2 = By.xpath(".//android.widget.TextView[@text='You can now call the lawyer.']");
    private static final By MODAL_OK = By.xpath(".//android.view.ViewGroup[@content-desc='OK']");

    private static final By BALANCE_RUPEE_BY_LABEL = By.xpath("//android.widget.TextView[@text='Available Balance']" +
            "/following::android.widget.TextView[contains(@text,'₹')][1]");

    private static final By RECENT_TXN_AMOUNT = By.xpath("//android.widget.TextView[@text='Wallet Top up']" +
            "/following::android.widget.TextView[contains(@text,'₹')][1]");

    private static final By BALANCE_RUPEE_BY_TOPUP = By.xpath("//android.widget.TextView[@text='Top Up']" +
            "/preceding::android.widget.TextView[starts-with(@text,'₹')][1]");

    private static final By BALANCE_BY_LABEL = By.xpath("//android.widget.TextView[@text='Available Balance']" +
            "/following::android.widget.TextView[starts-with(@text,'₹')][1]");

    private static final By BALANCE_BY_TOPUP = By.xpath("//android.widget.TextView[@text='Top Up']" +
            "/preceding::android.widget.TextView[starts-with(@text,'₹')][1]");

    private static final By BALANCE_GENERIC_FIRST = By.xpath("(//android.widget.TextView[starts-with(@text,'₹')])[1]");

    public RechargeFlow(AppiumDriver driver) {
        this.driver = (AndroidDriver) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickWalletIcon() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"\"]")));
        walletIcon.click();
        LogHelper.info("💰 Wallet icon clicked");
    }

    public boolean isWalletScreenVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(walletScreenTitle));
            return walletScreenTitle.isDisplayed();
        } catch (Exception e) {
            LogHelper.error("Wallet screen not visible: " + e.getMessage());
            return false;
        }
    }

    public void clickWalletTopupButton() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text=\"Top Up\"]")));
        walletTopupButton.click();
        LogHelper.info("💳 Wallet top-up button clicked");
    }

    public void waitForTopUpSuccessModal() {
        WaitHelper.waitForVisible((AppiumDriver) driver,
                By.xpath("//android.widget.TextView[@text='Top-up Successful']"), 30);
        LogHelper.info("🔔 Top-up success modal is visible");
    }

    public void performTopUp(int amount) throws InterruptedException {
        clickWalletTopupButton();
        WaitHelper.waitForVisible((AppiumDriver) driver, By.xpath("//android.widget.TextView[@text='Set Amount']"), 30);
        clickPredefinedAmount(amount);
        clickAddMoney();
        completePaymentWithMouseClicksOnly();
        waitForTopUpSuccessModal();
        clickTopUpModalOk();
        LogHelper.info("✅ Top-up flow completed for amount: ₹" + amount);
    }

    public boolean isSetAmountVisible() {
        wait.until(ExpectedConditions.visibilityOf(setAmountTitle));
        return setAmountTitle.isDisplayed();
    }

    public void clickPredefinedAmount(int amount) {
        WebElement amountBtn = null;
        switch (amount) {
            case 100:
                amountBtn = predefined100Btn;
                break;
            case 200:
                amountBtn = predefined200Btn;
                break;
            case 300:
                amountBtn = predefined300Btn;
                break;
            case 400:
                amountBtn = predefined400Btn;
                break;
            case 500:
                amountBtn = predefined500Btn;
                break;
            case 600:
                amountBtn = predefined600Btn;
                break;
            case 700:
                amountBtn = predefined700Btn;
                break;
            case 800:
                amountBtn = predefined800Btn;
                break;
            default:
                throw new IllegalArgumentException("Invalid predefined amount: " + amount);
        }
        wait.until(ExpectedConditions.elementToBeClickable(amountBtn));
        amountBtn.click();
    }

    public void clickPredefinedAmount500() {
        clickPredefinedAmount(500);
    }

    public void enterCustomAmount(String amount) {
        wait.until(ExpectedConditions.visibilityOf(customAmountInput));
        customAmountInput.clear();
        customAmountInput.sendKeys(amount);
    }

    public void clickAddMoney() {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Add Money']")));
        addMoneyBtn.click();
    }

    public int getWalletBalanceBeforeTopUp() {
        int balance = getWalletBalance();
        LogHelper.info("💰 Before Recharge Get Balance: ₹" + balance);
        return balance;
    }

    public int getWalletBalanceAfterTopUp() {
        try {
            LogHelper.info("🔄 Refreshing wallet screen before fetching updated balance...");

            // Step 1: Perform a pull-to-refresh or re-click on wallet icon to reload screen
            try {
                driver.navigate().back(); // go back to previous screen (if needed)
                Thread.sleep(2000);
                clickWalletIcon(); // reopen wallet screen
            } catch (Exception e) {
                LogHelper.warn("⚠️ Wallet refresh via navigation failed, attempting page reload: " + e.getMessage());
            }

            // Step 2: Wait for wallet balance element to be visible again
            By walletAmountXpath = By.xpath(
                    "//android.widget.TextView[contains(@text,'₹')]/following-sibling::android.widget.TextView[1]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(walletAmountXpath));

            // Step 3: Get updated balance
            WebElement walletAmountElement = driver.findElement(walletAmountXpath);
            String amountText = walletAmountElement.getText();
            LogHelper.info("📱 Updated Wallet Balance Text (Raw): " + amountText);

            String numericPart = amountText.replaceAll("[^0-9]", "");
            int updatedBalance = numericPart.isEmpty() ? 0 : Integer.parseInt(numericPart);

            LogHelper.info("✅ After Recharge Wallet Balance: ₹" + updatedBalance);
            return updatedBalance;

        } catch (Exception e) {
            LogHelper.error("❌ Failed to fetch updated wallet balance after recharge: " + e.getMessage());
            return 0;
        }
    }

    public int getWalletBalance() {
        try {
            String amountText = walletAmount.getText();
            System.out.println("📱 Raw Wallet Balance Text: " + amountText);

            String numericPart = amountText.replaceAll("[^0-9]", "");
            System.out.println("💰 Cleaned Numeric Value: " + numericPart);

            if (numericPart.isEmpty()) {
                System.err.println("⚠️ No numeric value found in wallet amount text!");
                return 0;
            }

            int balance = Integer.parseInt(numericPart);
            System.out.println("✅ Parsed Wallet Balance (int): " + balance);

            return balance;
        } catch (Exception e) {
            System.err.println("❌ Error while fetching wallet balance: " + e.getMessage());
            return 0;
        }
    }

    public WebElement waitForTopUpModalVisible() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
        return w.until(ExpectedConditions.visibilityOfElementLocated(TOPUP_MODAL_ROOT));
    }

    public TopUpModalTexts getTopUpModalTexts() {
        WebElement root = waitForTopUpModalVisible();
        String title = root.findElement(MODAL_TITLE).getText();
        String line1 = root.findElement(MODAL_LINE1).getText();
        String line2 = root.findElement(MODAL_LINE2).getText();
        return new TopUpModalTexts(title, line1, line2);
    }

    public void clickTopUpModalOk() {
        WebElement root = waitForTopUpModalVisible();
        WebElement ok = root.findElement(MODAL_OK);
        new Actions(driver).moveToElement(ok).click().perform();
        LogHelper.info("🆗 Top-up success modal dismissed (OK clicked).");
    }

    public boolean isTopUpModalVisible() {
        try {
            waitForTopUpModalVisible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static class TopUpModalTexts {
        public final String title;
        public final String line1;
        public final String line2;

        public TopUpModalTexts(String title, String line1, String line2) {
            this.title = title;
            this.line1 = line1;
            this.line2 = line2;

        }
    }

    public void completePaymentWithMouseClicksOnly() throws InterruptedException {
        Thread.sleep(5000);
        LogHelper.info("🖱 Performing payment actions using mouse clicks only (no context switch)...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            Actions actions = new Actions(driver);

            By netbanking = By.xpath("//*[contains(@content-desc,'Netbanking') or contains(@text,'Netbanking')]");
            WebElement nb = wait.until(ExpectedConditions.elementToBeClickable(netbanking));
            actions.moveToElement(nb).click().perform();
            LogHelper.info("✅ Netbanking option clicked using mouse action.");

            Thread.sleep(2000);

            By firstBank = By.xpath("//android.widget.Button[@text=\"Canara Bank Canara Bank\"]");
            WebElement bankElement = wait.until(ExpectedConditions.elementToBeClickable(firstBank));
            actions.moveToElement(bankElement).click().perform();
            LogHelper.info("✅ First bank clicked using mouse action.");

            Thread.sleep(2000);

            By payOrContinue = By.xpath("//android.widget.Button[@text=\"Success\"]");
            WebElement payElement = wait.until(ExpectedConditions.elementToBeClickable(payOrContinue));
            actions.moveToElement(payElement).click().perform();
            LogHelper.info("✅ Payment confirmed using mouse action.");

            Thread.sleep(5000);

        } catch (Exception e) {
            LogHelper.error("❌ Mouse click flow failed. Reason: " + e.getMessage());
        }

        LogHelper.info("🏁 Payment flow completed using mouse click actions only.");
    }
}
