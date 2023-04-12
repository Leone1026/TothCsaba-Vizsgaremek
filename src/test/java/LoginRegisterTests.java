import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.LandingPage;
import org.example.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;


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


    /*@BeforeEach
    public void init() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        //options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");

        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //wait = new WebDriverWait(driver, 10);

    }*/

    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Register without parameters")
    public void RegisterTest() {
        loginPage = new LoginPage(driver);
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.RegisterBasic("","","","");
        Assertions.assertFalse(loginPage.RegisterAlertIsDisplayed());
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Register with username and password and try to login with wrong password")
    public void RegisterAndLoginTest () {
        loginPage = new LoginPage(driver);
        String validUserName = "Pisti22";
        String validPassword = "22itsiP";
        String invalidPassword = "12itsiP";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.RegisterBasic(validUserName,validPassword,"","");
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());
        loginPage.LoginFunction(validUserName, invalidPassword);
        Assertions.assertTrue(loginPage.LoginAlertIsDisplayed());
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Try to login with wrong username")
    public void LoginTest1 () {
        loginPage = new LoginPage(driver);
        String invalidUserName = "Jackie";
        String validPassword = "22itsiP";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(invalidUserName, validPassword);
        Assertions.assertTrue(loginPage.LoginAlertIsDisplayed());
    }

    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Try to login with valid username and password")
    public void LoginTest2 () {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        String validUserName = "Pisti22";
        String validPassword = "22itsiP";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
    }


    @AfterEach
    public void TearDown () {
        driver.quit();
    }

}
