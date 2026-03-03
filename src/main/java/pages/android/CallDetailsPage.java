package pages.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogHelper;

import java.time.Duration;

public class CallDetailsPage {

    private final AppiumDriver driver;
    private WebDriverWait wait;

    public CallDetailsPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // 📞 Call icon locator
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='']/android.view.ViewGroup")
    private WebElement callIcon;

    // 🏛️ Call screen elements
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Select your state, ']")
    private WebElement callStateDropdown;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search select state...']")
    private WebElement callStateSearchField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Select your city, ']")
    private WebElement callCityDropdown;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search select city...']")
    private WebElement callCitySearchField;

    // 🌐 Language
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Select languages, ']")
    private WebElement preferredLanguageDropdown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='']")
    private WebElement closeLanguagePopupIcon;

    // ⚖️ Field of Expertise
    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Select field of expertise')]")
    private WebElement fieldDropdown;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search select field...']")
    private WebElement searchFieldInput;

    // ================== ACTION METHODS ==================

    /**
     * Clicks the Call icon on the call details screen.
     */
    public void clickCallIcon() {
        LogHelper.info("📞 Clicking on Call icon...");
        wait.until(ExpectedConditions.elementToBeClickable(callIcon)).click();
        LogHelper.info("✅ Call icon clicked successfully.");
    }

    /**
     * Selects a state from the dropdown.
     */
    public void selectStateInCallScreen(String state) {
        LogHelper.info("🏛️ Selecting state: " + state);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(callStateDropdown)).click();
            wait.until(ExpectedConditions.visibilityOf(callStateSearchField)).sendKeys(state);

            WebElement firstStateOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.TextView[contains(@text,'" + state + "')]")));
            firstStateOption.click();
            LogHelper.info("✅ State selected: " + state);
        } catch (Exception e) {
            LogHelper.error("❌ Failed to select state: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Selects a city from the dropdown.
     */
    public void selectCityInCallScreen(String city) {
        LogHelper.info("🏙️ Selecting city: " + city);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(callCityDropdown)).click();
            wait.until(ExpectedConditions.visibilityOf(callCitySearchField)).sendKeys(city);

            WebElement firstCityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.TextView[contains(@text,'" + city + "')]")));
            firstCityOption.click();
            LogHelper.info("✅ City selected: " + city);
        } catch (Exception e) {
            LogHelper.error("❌ Failed to select city: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Selects a preferred language from the language dropdown.
     */
    public void selectPreferredLanguage(String languageName) {
        LogHelper.info("🌐 Selecting preferred language: " + languageName);
        try {
            // 1️⃣ Click dropdown
            wait.until(ExpectedConditions.elementToBeClickable(preferredLanguageDropdown)).click();
            LogHelper.info("📂 Language dropdown opened.");

            // 2️⃣ Enter search text
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//android.widget.EditText[@text='Search select languages...']")));
            searchBox.clear();
            searchBox.sendKeys(languageName);
            LogHelper.info("🔍 Searching for: " + languageName);

            // 3️⃣ Click matching option
            WebElement languageOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.TextView[@text='" + languageName + "']")));
            languageOption.click();
            LogHelper.info("✅ Language selected: " + languageName);
        } catch (Exception e) {
            LogHelper.error("❌ Failed to select language: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Closes the Select Languages popup by clicking the close icon.
     */
    public void closeLanguagePopup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(closeLanguagePopupIcon)).click();
            LogHelper.info("❎ Closed Select Languages popup successfully.");
        } catch (Exception e) {
            LogHelper.error("❌ Failed to close language popup: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Selects a field of expertise from the dropdown on the call details screen.
     */
    public void selectFieldOfExpertise(String fieldName) {
        LogHelper.info("🎯 Selecting Field of Expertise: " + fieldName);
        try {
            // 1️⃣ Click dropdown
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.view.ViewGroup[contains(@content-desc,'Select field of expertise')]")));
            dropdown.click();
            LogHelper.info("📂 Field dropdown opened.");

            // 2️⃣ Enter search text
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//android.widget.EditText[@text='Search select field...']")));
            searchInput.sendKeys(fieldName);
            LogHelper.info("🔍 Searching for field: " + fieldName);

            // 3️⃣ Select option
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.TextView[@text='" + fieldName + "']")));
            option.click();
            LogHelper.info("✅ Field of Expertise selected: " + fieldName);
        } catch (Exception e) {
            LogHelper.error("❌ Failed to select field of expertise: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the profile icon is visible (inherited from SignupPage or common
     * method).
     */
    public boolean isProfileIconVisible() {
        try {
            WebElement profileIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.widget.ImageView[contains(@content-desc,'Profile')]")));
            return profileIcon.isDisplayed();
        } catch (Exception e) {
            LogHelper.error("❌ Profile icon not visible: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the call details screen is loaded.
     */
    public boolean isCallDetailsScreenLoaded() {
        try {
            // Check for presence of key elements on call details screen
            WebElement callStateDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//android.view.ViewGroup[@content-desc='Select your state, ']")));
            return callStateDropdown.isDisplayed();
        } catch (Exception e) {
            LogHelper.error("❌ Call details screen not loaded: " + e.getMessage());
            return false;
        }
    }
}
