import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.example.LandingPage;
import org.example.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginRegisterTests {

    WebDriver driver;
    LoginPage loginPage;
    LandingPage landingPage;

    @BeforeEach
    void setUp() {
        // Set up the WebDriver for the tests
        WebDriverManager.chromedriver().setup();
        driver = BaseTest.getWebDriver();

        // Set the implicit wait timeout to 10 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    // This test verifies that a user cannot register without filling in any information.
    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("RegisterTest")
    @Tag("LoginPage")
    @DisplayName("TC01 - Register without parameters")
    @Description("Verify that a user cannot register without parameters")
    public void RegisterTest() {
        // Create instance of the page that will be used in the test
        loginPage = new LoginPage(driver);

        // Navigate to the login page, accept terms and conditions and click on the register button
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();

        // Attempt to register with empty fields
        loginPage.RegisterBasic("","","","");

        // Take a screenshot of the page and add it as an attachment to the report
        Allure.addAttachment("TC01 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));

        // Assert that the alert message, which ensures the user that the registration
        // is complete, isn't displayed
        Assertions.assertFalse(loginPage.RegisterAlertIsDisplayed());
    }


    @Test
    @Order(2)
    @Severity(SeverityLevel.CRITICAL)
    @Story("RegisterTest")
    @Tag("LoginPage")
    @DisplayName("TC02 - Register with invalid e-mail addresses")
    @Description("Verify that a user cannot register with invalid e-mail addresses")
    public void RegisterTest2() {
        // Create instance of the page that will be used in the test
        loginPage = new LoginPage(driver);

        // Navigate to the login page, accept terms and conditions, and click on register button
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();

        // Use a soft assert to check whether the user can register with invalid email addresses
        SoftAssert softAssert = new SoftAssert();

        // Register with empty email address (the "@" symbol is missing)
        loginPage.RegisterBasic("","whynot","why.not.gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());

        // Register with an email address that has "a>b" format
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","aisbiggerthanb","a>b@gmail.com","");
        Allure.addAttachment("TC02 - Screenshot 01", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","klassz","classof99@","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());

        // Register with an email address that starts with "@" symbol
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","nicetry","@gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());

        // Register with an email address that includes a space
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","shouldwork","new user@gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());

        // Register with an email address that has a typo in the domain name
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","anotherone","perfect@gmail,com","");
        Allure.addAttachment("TC02 - Screenshot 02", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());

        // Register with an email address that has no top-level domain name
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","nevergiveup","newuser@com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());

        // Register with an email address that starts with a dot
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","theendisnear",".newuser@gmail.com","");
        Allure.addAttachment("TC02 - Screenshot 03", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());

        // Register with an email address that has two consecutive dots
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","lastone","new..user@gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        softAssert.assertAll();
    }


    // This test case verifies that a user can register
    // with a username and password and then log in with those registered parameters.
    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Story("LoginTest")
    @Tag("LoginPage")
    @DisplayName("TC03 - Register with username and password and login with registered parameters")
    @Description("Verify that user can login with given parameters after registration")
    public void RegisterAndLoginTest1 () {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);

        // Set username and password for registration
        String validUserName = "Larry22";
        String validPassword = "22yrraL";

        // Navigate to the login page, accept terms and conditions, and click on the register button
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();

        // Register with the previously given username and password
        loginPage.RegisterBasic(validUserName,validPassword,"","");

        // Assert that the registration alert ("User registered!") is displayed
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());

        // Log in with the registered username and password
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
    }


    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @Story("LoginTest")
    @Tag("LoginPage")
    @DisplayName("TC04 - Register with username and password and try to login with wrong password")
    @Description("Verify that a user cannot login with wrong password after registration")
    public void RegisterAndLoginTest2 () {
        // Create instance of the page that will be used in the test
        loginPage = new LoginPage(driver);

        // Set up user credentials for the test
        String validUserName = "Pisti22";
        String validPassword = "22itsiP";
        String invalidPassword = "12itsiP";

        // Navigate to the login page and register a new user with the previously given credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic(validUserName,validPassword,"","");
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());

        // Log in with the registered username and an invalid password
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(validUserName, invalidPassword);

        // Take a screenshot and assert that the login alert
        // ("Username or Password is not correct!") is displayed
        Allure.addAttachment("TC04 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertTrue(loginPage.LoginAlertIsDisplayed());
    }


    // This test verifies that a user cannot login with a wrong username.
    @Test
    @Order(5)
    @Severity(SeverityLevel.NORMAL)
    @Story("LoginTest")
    @Tag("LoginPage")
    @DisplayName("TC05 - Try to login with wrong username")
    @Description("Verify that a user cannot login with wrong username")
    public void LoginTest () {
        // Create instance of the page that will be used in the test
        loginPage = new LoginPage(driver);

        // Set up user credentials for the test
        String invalidUserName = "Jackie";
        String validPassword = "22yrraL";

        // Navigate to the login page and attempt to login with wrong username
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(invalidUserName, validPassword);

        // Assert that the login alert ("Username or Password is not correct!") is displayed
        Assertions.assertTrue(loginPage.LoginAlertIsDisplayed());
    }


    @Test
    @Order(6)
    @Severity(SeverityLevel.NORMAL)
    @Story("LoginTest")
    @Tag("LandingPage")
    @DisplayName("TC06 - Login with valid username and password and logout")
    @Description("Verify that a user can login with registered username/password and logout function also functions as required")
    public void LogoutTest () {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct - landing - page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.clickOnLogout();

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/index.html", landingPage.GetURL());
    }


   // This method closes the browser after each test is executed.
   @AfterEach
   public void TearDown () {
        driver.quit();
   }


}
