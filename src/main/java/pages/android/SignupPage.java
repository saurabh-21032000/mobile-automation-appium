package pages.android;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogHelper;

import java.time.Duration;
import java.util.List;

public class SignupPage extends BasePage {

    private WebDriverWait wait;

    // --- LOCATORS ---
    @AndroidFindBy(xpath = "//android.widget.Button[contains(@text, 'Allow') or contains(@text, 'While using')]")
    private WebElement genericPermissionButton;

    @AndroidFindBy(className = "android.widget.EditText")
    private WebElement mobileNumberField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Send OTP']")
    private WebElement sendOtpButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Enter your full name']")
    private WebElement profileFullName;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Select Your Language')]")
    private WebElement preferredLanguageDropdown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='English']")
    private WebElement englishLanguageOption;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Select Your Gender')]")
    private WebElement genderDropdown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='male']")
    private WebElement maleGenderOption;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Select Your State')]")
    private WebElement stateDropdown;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search select state...']")
    private WebElement stateSearchField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Select Your City')]")
    private WebElement cityDropdown;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Search select city...']")
    private WebElement citySearchField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'dd-mm-yyyy')]")
    private WebElement dateOfBirthField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='android:id/date_picker_header_year']")
    private WebElement yearPicker;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1']")
    private WebElement dateOkButton;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']//android.view.ViewGroup[6]")
    private WebElement termsCheckbox;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Submit']")
    private WebElement submitButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=', Profile']")
    private WebElement profileIcon;

    public SignupPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    // --- SMART FINDER ---
    private WebElement findElementSmartly(WebElement mainLocator, String manualXpath, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(mainLocator));
            return mainLocator;
        } catch (Exception e) {
            LogHelper.info("⚠️ Auto-fail for '" + elementName + "'. Trying Manual XPath...");
        }

        if (manualXpath != null && !manualXpath.isEmpty()) {
            try {
                WebElement manualElement = driver.findElement(By.xpath(manualXpath));
                LogHelper.info("✅ Found '" + elementName + "' manually.");
                return manualElement;
            } catch (Exception ex) {}
        }

        LogHelper.error("🛑 Element '" + elementName + "' NOT FOUND. Printing Screen...");
        printAllElementsOnScreen();
        throw new RuntimeException("Element not found: " + elementName);
    }

    public void printAllElementsOnScreen() {
        try {
            System.out.println("\n--- SCREEN DUMP ---");
            List<WebElement> allElements = driver.findElements(AppiumBy.xpath("//*"));
            int i = 1;
            for (WebElement el : allElements) {
                try {
                    String txt = el.getText();
                    String id = el.getAttribute("resource-id");
                    String desc = el.getAttribute("content-desc");
                    if((txt!=null && !txt.isEmpty()) || (id!=null && !id.isEmpty()) || (desc!=null && !desc.isEmpty())) {
                        System.out.println("#" + i + " Text:" + txt + " | ID:" + id + " | Desc:" + desc);
                        i++;
                    }
                } catch(Exception ignored){}
            }
            System.out.println("-------------------\n");
        } catch(Exception ignored) {}
    }

    // --- ACTIONS ---

    public void clickAllowPermission() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(genericPermissionButton)).click();
        } catch (Exception e) { /* Ignored */ }
    }

    public void handleGoogleSavedPasswordPopup() {
        try {
            if (!driver.findElements(AppiumBy.id("com.google.android.gms:id/cancel")).isEmpty()) {
                driver.findElement(AppiumBy.id("com.google.android.gms:id/cancel")).click();
            }
        } catch (Exception ignored) {}
    }

    public void clickCallingAccountListItem() {
        try {
            By listOption = AppiumBy.xpath("(//android.widget.ListView)[1]//android.widget.LinearLayout[1]");
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(listOption)).click();
        } catch (Exception ignored) {}
    }

    public void enterMobileNumber(String mobileNumber) {
        handleGoogleSavedPasswordPopup();
        String manualXpath = ""; 
        WebElement el = findElementSmartly(mobileNumberField, manualXpath, "Mobile Number");
        el.click();
        el.clear();
        el.sendKeys(mobileNumber);
        LogHelper.info("📱 Mobile entered.");
    }

    public void clickSendOtp() {
        try { 
            if (driver instanceof AndroidDriver) { ((AndroidDriver) driver).hideKeyboard(); }
        } catch(Exception ignored){}
        
        String manualXpath = "//*[@text='Send OTP' or @content-desc='Send OTP']";
        WebElement el = findElementSmartly(sendOtpButton, manualXpath, "Send OTP Button");
        el.click();
        LogHelper.info("📨 Send OTP clicked.");
    }

    // 🔥 OTP Logic (Native Key Injection) 🔥
    public void enterOtp(String otp) {
        LogHelper.info("🔢 Entering OTP via Native Key Injection...");
        try {
            List<WebElement> otpBoxes = driver.findElements(AppiumBy.className("android.widget.EditText"));
            if (!otpBoxes.isEmpty()) {
                otpBoxes.get(0).click(); 
                LogHelper.info("👉 Focused on first input box.");
            }

            if (driver instanceof AndroidDriver) {
                AndroidDriver androidDriver = (AndroidDriver) driver;
                for (char digit : otp.toCharArray()) {
                    AndroidKey key = null;
                    switch (digit) {
                        case '0': key = AndroidKey.DIGIT_0; break;
                        case '1': key = AndroidKey.DIGIT_1; break;
                        case '2': key = AndroidKey.DIGIT_2; break;
                        case '3': key = AndroidKey.DIGIT_3; break;
                        case '4': key = AndroidKey.DIGIT_4; break;
                        case '5': key = AndroidKey.DIGIT_5; break;
                        case '6': key = AndroidKey.DIGIT_6; break;
                        case '7': key = AndroidKey.DIGIT_7; break;
                        case '8': key = AndroidKey.DIGIT_8; break;
                        case '9': key = AndroidKey.DIGIT_9; break;
                    }
                    if (key != null) {
                        androidDriver.pressKey(new KeyEvent(key));
                    }
                    Thread.sleep(150); 
                }
            }
            LogHelper.info("✅ Keys Pressed.");

            try { if (driver instanceof AndroidDriver) ((AndroidDriver) driver).hideKeyboard(); } catch (Exception ignored) {}

            String loginXpath = "//*[@text='Log In' or @content-desc='Log In']";
            try {
                WebElement loginBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.elementToBeClickable(By.xpath(loginXpath)));
                loginBtn.click();
                LogHelper.info("👉 Clicked 'Log In'.");
            } catch (Exception e) {
                LogHelper.info("⚠️ 'Log In' button not found (Maybe auto-submitted).");
            }

        } catch (Exception e) {
            LogHelper.error("❌ OTP Failed: " + e.getMessage());
        }
    }

    public void waitForProfileScreenToLoad() {
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(profileFullName),
                ExpectedConditions.visibilityOf(preferredLanguageDropdown)
            ));
        } catch (Exception e) { LogHelper.info("⚠️ Wait timeout for profile."); }
    }

    public void enterFullName(String name) {
        String manualXpath = "//android.widget.EditText";
        try {
             WebElement el = findElementSmartly(profileFullName, manualXpath, "Full Name");
             el.sendKeys(name);
        } catch (Exception e) {
             List<WebElement> edits = driver.findElements(AppiumBy.className("android.widget.EditText"));
             if(!edits.isEmpty()) edits.get(0).sendKeys(name);
        }
    }

    public void selectPreferredLanguage() {
        String manualXpath = "//android.view.ViewGroup[contains(@content-desc, 'Select language')]";
        findElementSmartly(preferredLanguageDropdown, manualXpath, "Language Dropdown").click();
        
        String englishXpath = "//*[contains(@text, 'English') or contains(@content-desc, 'English')]";
        findElementSmartly(englishLanguageOption, englishXpath, "English Option").click();

        try {
            String doneXpath = "//*[contains(@text, 'Done') or contains(@content-desc, 'Done')]";
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(By.xpath(doneXpath))).click();
        } catch (Exception e) {}
    }

    public void selectGender() {
        String manualXpath = "//android.view.ViewGroup[contains(@content-desc, 'Select your gender')]";
        findElementSmartly(genderDropdown, manualXpath, "Gender Dropdown").click();
        wait.until(ExpectedConditions.elementToBeClickable(maleGenderOption)).click();
    }

    public void selectState(String state) {
        String manualXpath = "//android.view.ViewGroup[contains(@content-desc, 'Enter your state')]";
        findElementSmartly(stateDropdown, manualXpath, "State Dropdown").click();
        wait.until(ExpectedConditions.elementToBeClickable(stateSearchField)).sendKeys(state);
        driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + state + "')]")).click();
    }

    // 🔥 FIX 1: Updated City Dropdown & Selection XPath 🔥
    public void selectCity(String city) {
        // Step 1: Open Dropdown
        String manualXpath = "//android.widget.TextView[@text='Enter your city']";
        findElementSmartly(cityDropdown, manualXpath, "City Dropdown").click();
        
        // Step 2: Type City Name
        wait.until(ExpectedConditions.elementToBeClickable(citySearchField)).sendKeys(city);
        
        // Step 3: Select City (Dynamic Content-Desc based on your example)
        try {
             String resultXpath = "//android.view.ViewGroup[@content-desc='" + city + "']";
             driver.findElement(By.xpath(resultXpath)).click();
        } catch(Exception e) {
             // Fallback to text check if content-desc fails
             driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + city + "')]")).click();
        }
    }

    // 🔥 FIX 2: Updated DOB XPath 🔥
    public void selectDateOfBirth(String dob) {
        try {
            String manualXpath = "//android.widget.EditText[@text='DD/MM/YYYY']";
            findElementSmartly(dateOfBirthField, manualXpath, "DOB Field").click();
            wait.until(ExpectedConditions.elementToBeClickable(yearPicker)).click();
            String year = dob.split(" ")[2];
            try {
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"" + year + "\"))"
                )).click();
            } catch(Exception ignored){}
            
            try { wait.until(ExpectedConditions.elementToBeClickable(dateOkButton)).click(); } catch(Exception ignored){}
        } catch (Exception e) { LogHelper.error("❌ DOB Error"); }
    }

    public void checkTermsCheckbox() {
        String manualXpath = "";
        findElementSmartly(termsCheckbox, manualXpath, "Terms Checkbox").click();
    }

    public void clickProfileSubmitButton() {
        String manualXpath = "";
        findElementSmartly(submitButton, manualXpath, "Submit Button").click();
    }

    public boolean isProfileIconVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(profileIcon));
            return true;
        } catch (Exception e) { return false; }
    }
}