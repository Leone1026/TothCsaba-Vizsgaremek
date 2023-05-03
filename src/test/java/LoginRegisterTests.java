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
        WebDriverManager.chromedriver().setup();
        driver = BaseTest.getWebDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //loginPage = new LoginPage(driver);
    }

    /*
    @BeforeEach
    public void init() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");

        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //wait = new WebDriverWait(driver, 10);
    } */

    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("RegisterTest")
    @Tag("LoginPage")
    @DisplayName("TC01 - Register without parameters")
    @Description("Verify that a user cannot register without parameters")
    public void RegisterTest() {
        loginPage = new LoginPage(driver);
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic("","","","");
        Allure.addAttachment("TC01 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
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
        loginPage = new LoginPage(driver);
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        SoftAssert softAssert = new SoftAssert();
        loginPage.RegisterBasic("","whynot","why.not.gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","aisbiggerthanb","a>b@gmail.com","");
        Allure.addAttachment("TC02 - Screenshot 01", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","klassz","classof99@","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","nicetry","@gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","shouldwork","new user@gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","anotherone","perfect@gmail,com","");
        Allure.addAttachment("TC02 - Screenshot 02", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","nevergiveup","newuser@com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","theendisnear",".newuser@gmail.com","");
        Allure.addAttachment("TC02 - Screenshot 03", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        loginPage.ClickOnRegisterButtonRepeat();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage.RegisterBasic("","lastone","new..user@gmail.com","");
        softAssert.assertFalse(loginPage.RegisterAlertIsDisplayed());
        softAssert.assertAll();
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Story("LoginTest")
    @Tag("LoginPage")
    @DisplayName("TC03 - Register with username and password and login with registered parameters")
    @Description("Verify that user can login with given parameters after registration")
    public void RegisterAndLoginTest1 () {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        String validUserName = "Larry22";
        String validPassword = "22yrraL";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic(validUserName,validPassword,"","");
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(validUserName, validPassword);
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
        loginPage = new LoginPage(driver);
        String validUserName = "Pisti22";
        String validPassword = "22itsiP";
        String invalidPassword = "12itsiP";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic(validUserName,validPassword,"","");
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(validUserName, invalidPassword);
        Allure.addAttachment("TC04 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertTrue(loginPage.LoginAlertIsDisplayed());
    }

    @Test
    @Order(5)
    @Severity(SeverityLevel.NORMAL)
    @Story("LoginTest")
    @Tag("LoginPage")
    @DisplayName("TC05 - Try to login with wrong username")
    @Description("Verify that a user cannot login with wrong username")
    public void LoginTest () {
        loginPage = new LoginPage(driver);
        String invalidUserName = "Jackie";
        String validPassword = "22yrraL";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(invalidUserName, validPassword);
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
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.clickOnLogout();
        Assertions.assertEquals("https://lennertamas.github.io/roxo/index.html", landingPage.GetURL());
    }

   @AfterEach
   public void TearDown () {
        driver.quit();
   }


}
