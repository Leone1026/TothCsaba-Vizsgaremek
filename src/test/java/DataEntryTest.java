import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.example.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataEntryTest {

    WebDriver driver;
    LoginPage loginPage;
    LandingPage landingPage;
    MessagePage messagePage;
    ProfilePage profilePage;


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = BaseTest.getWebDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("DataEntryTest")
    @Tag("MessagePage")
    @DisplayName("TC07 - Send message without parameters")
    @Description("Verify that website doesn't display 'Message Sent!' alert, if user doesn't fill out any field")
    public void EmptyMessageTest() throws WebDriverException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        messagePage = new MessagePage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToMessage();
        messagePage.clickSubmit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Duration waitTime = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        String expected = "Message sent!";
        Assertions.assertNotEquals(expected, alertText);
        messagePage.handleAlert();
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Story("DataEntryTest")
    @Tag("MessagePage")
    @DisplayName("TC08 - Send message with parameters")
    @Description("Verify that website sends message, if all required fields are filled out")
    public void ValidMessageTest() {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        messagePage = new MessagePage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToMessage();
        String firstname = "Jack";
        String lastname = "Burton";
        String email = "jackburton@gmail.com";
        String Type = "Web Design";
        String message = "\"Dear Roxo, \n" +
                "\n" +
                "I would like to have a web design made for my transportation company's upcoming website. When could we schedule a meeting to discuss the details? \n" +
                "\n" +
                "Best regards, \n" +
                "\n" +
                "Jack Burton.\" ";
        messagePage.fillOutFormBasic(firstname, lastname, email, Type, message);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Duration waitTime = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        String expected = "Message sent!";
        Assertions.assertEquals(expected, alertText);
        messagePage.handleAlert();
        Set<Cookie> cookies = driver.manage().getCookies();
        String result = null;
        for (Cookie cookie : cookies) {
            result = cookie.getName() + ": " + cookie.getValue();
            Assertions.assertEquals("tandc: true", result);
        }
    }


    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Story("DataEntryTest")
    @Tag("LoginPage")
    @DisplayName("TC09 - Register more than one user from file")
    @Description("Verify that user can register more than one user with valid data")
    public void SerialRegisterTest () throws IOException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        SoftAssert softAssert = new SoftAssert();
        BufferedReader reader = new BufferedReader(new FileReader("Users.txt"));
        String userData;
        while ((userData = reader.readLine()) != null) {
            String[] values = userData.split(",");
            String username = values[0].trim();
            String password = values[1].trim();
            String email = values[2].trim();
            String description = values[3].trim();
            loginPage.RegisterBasic(username, password, email, description);
            softAssert.assertTrue(loginPage.RegisterAlertIsDisplayed());
            loginPage.ClickOnRegisterButtonRepeat();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        softAssert.assertAll();
    }


    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @Story("DataModificationTest")
    @Tag("ProfilePage")
    @DisplayName("TC10 - Register, then modify profile")
    @Description("Verify that user can modify profile after registration")
    public void RegisterAndProfileModificationTest () {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        profilePage = new ProfilePage(driver);
        String username = "aron";
        String password = "aaron99";
        String email = "aaron.mail@gmail.com";
        String description = "testaron";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic(username,password,email, description);
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(username, password);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToProfile();
        String name = "Aaron Norris";
        String bio = "I was born in 1987.";
        String phone = "555-8765-2914";
        profilePage.ModifyProfile(name, bio, phone);
        Assertions.assertTrue(profilePage.EditAlertIsDisplayed());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Set<Cookie> cookies = driver.manage().getCookies();
            String expected = username + ": {\"username\":\"" + username + "\",\"password\":\"" + password + "\"," +
                    "\"email\":\"" + email + "\",\"description\":\"" + description + "\"," +
                    "\"name\":\"" + name + "\",\"bio\":\"" + bio + "\",\"phoneNumber\":\"" + phone + "\"}";
        boolean cookieCheck = false;
        for (Cookie cookie : cookies) {
            String result = cookie.getName() + ": " + cookie.getValue();
            if (result.equals(expected)) {
                cookieCheck = true;
                break;
            }
        }
        Assertions.assertTrue(cookieCheck, "Expected cookie not found.");
    }

    @Test
    @Order(5)
    @Severity(SeverityLevel.NORMAL)
    @Story("DataModificationTest")
    @Tag("ProfilePage")
    @DisplayName("TC11 - Register, then delete profile")
    @Description("Verify that user can delete the profile after registration")
    public void RegisterAndDeleteProfileTest () {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        profilePage = new ProfilePage(driver);
        String username = "Silberman";
        String password = "silberman45";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic(username,password,"","");
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(username, password);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToProfile();
        profilePage.DeleteProfile();
        Assertions.assertEquals("https://lennertamas.github.io/roxo/index.html", landingPage.GetURL());
        loginPage.LoginFunction(username, password);
        Assertions.assertTrue(loginPage.LoginAlertIsDisplayed());
    }

    @AfterEach
    public void TearDown () {
        driver.quit();
    }


}
