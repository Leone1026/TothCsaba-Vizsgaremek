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
        // Set up the WebDriver for the tests
        WebDriverManager.chromedriver().setup();
        driver = BaseTest.getWebDriver();

        // Set the implicit wait timeout to 10 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.CRITICAL)
    @Story("DataEntryTest")
    @Tag("MessagePage")
    @DisplayName("TC07 - Send message without parameters")
    @Description("Verify that website doesn't display 'Message Sent!' alert, if user doesn't fill out any field")
    public void EmptyMessageTest() throws WebDriverException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        messagePage = new MessagePage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the message page and click the submit button without filling out any fields
        landingPage.GoToMessage();
        messagePage.clickSubmit();

        // Wait for an alert to be displayed on the page and get the text of the alert
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Duration waitTime = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();

        // Verify that the alert text does not match the expected text
        String expected = "Message sent!";
        Assertions.assertNotEquals(expected, alertText);

        // Handle the alert
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
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        messagePage = new MessagePage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Go to the message page
        landingPage.GoToMessage();

        // Fill out the message form with valid data
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

        // Wait for the alert to appear and verify its text
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Duration waitTime = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        String expected = "Message sent!";
        Assertions.assertEquals(expected, alertText);

        // Handle the alert and verify that the "terms and conditions" cookie has been set
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
    public void SerialRegisterTest() throws IOException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);

        // Navigate to the login page and accept the terms and conditions
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();

        // Click on the register button to start registering users
        loginPage.ClickOnRegisterButton();

        // Create a SoftAssert object to handle multiple assertions in the test
        SoftAssert softAssert = new SoftAssert();

        // Read the user data from a file and register each user with valid data
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

            // Click on the register button again to clear the form and register the next user
            loginPage.ClickOnRegisterButtonRepeat();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        // Verify that all assertions passed successfully
        softAssert.assertAll();
    }


    // This test case checks if a user can register
    // and modify their profile after registration.
    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @Story("DataModificationTest")
    @Tag("ProfilePage")
    @DisplayName("TC10 - Register, then modify profile")
    @Description("Verify that user can modify profile after registration")
    public void RegisterAndProfileModificationTest() {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        profilePage = new ProfilePage(driver);

        // Set up registration data
        String username = "aron";
        String password = "aaron99";
        String email = "aaron.mail@gmail.com";
        String description = "testaron";

        // Navigate to the login page and register the user
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic(username, password, email, description);
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());

        // Log in with the registered user
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(username, password);

        // Verify that the user is on the correct landing page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Go to the profile page and modify the profile
        landingPage.GoToProfile();
        String name = "Aaron Norris";
        String bio = "I was born in 1987.";
        String phone = "555-8765-2914";
        profilePage.ModifyProfile(name, bio, phone);
        Assertions.assertTrue(profilePage.EditAlertIsDisplayed());

        // Check that the expected cookie (with all the previously given data) is present
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


    // This test case verifies that the user can register, and then delete their profile.
    @Test
    @Order(5)
    @Severity(SeverityLevel.NORMAL)
    @Story("DataModificationTest")
    @Tag("ProfilePage")
    @DisplayName("TC11 - Register, then delete profile")
    @Description("Verify that user can delete the profile after registration")
    public void RegisterAndDeleteProfileTest() {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        profilePage = new ProfilePage(driver);

        // Define test data for registration
        String username = "Silberman";
        String password = "silberman45";

        // Navigate to the Roxo website, register a new user
        // with the previously defined data and login
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.ClickOnRegisterButton();
        loginPage.RegisterBasic(username, password, "", "");
        Assertions.assertTrue(loginPage.RegisterAlertIsDisplayed());
        loginPage.LoginFromRegister();
        loginPage.LoginFunction(username, password);

        // Verify that the user is on the landing page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Go to the user's profile page and delete the profile
        landingPage.GoToProfile();
        profilePage.DeleteProfile();

        // Verify that the user is redirected to the login page after profile deletion
        Assertions.assertEquals("https://lennertamas.github.io/roxo/index.html", landingPage.GetURL());

        // Attempt to login with the same credentials as before
        loginPage.LoginFunction(username, password);

        // Verify that the user cannot login,
        // and an alert (wrong username or password) is shown
        Assertions.assertTrue(loginPage.LoginAlertIsDisplayed());
    }

    // This method closes the browser after each test is executed.
    @AfterEach
    public void TearDown() {
        driver.quit();
    }


}
