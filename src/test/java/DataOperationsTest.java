import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.example.AboutPage;
import org.example.LandingPage;
import org.example.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


//import org.junit.Test;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataOperationsTest {

    WebDriver driver;
    LoginPage loginPage;
    LandingPage landingPage;
    AboutPage aboutPage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = BaseTest.getWebDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    @Order(1)
    @Severity(SeverityLevel.NORMAL)
    @Tag("AboutPage")
    @Story("DataReadTest")
    @DisplayName("TC12 - Read Expertise Data On About Page And Compare With String")
    @Description("Verify that Expertise Data matches the predetermined data")
    public void DataReadTest1 () {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        aboutPage = new AboutPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToAbout();
        String actual = aboutPage.getExpertiseData();
        Allure.addAttachment("TC12 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        String expected = "Customer Experience Design, Digital Products, Development, Campaign & Content, " +
                "Employer Branding, Animation & Motion Graphics, Packaging & Product Design, " +
                "Retail & Spacial, Print & Editorial Design, Concept/Text, Information Design";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Tag("AboutPage")
    @Story("DataReadTest")
    @DisplayName("TC13 - Read Members Data On About Page And Compare With List")
    @Description("Verify that Members Data matches the predetermined data")
    public void DataReadTest2 () throws InterruptedException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        aboutPage = new AboutPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        landingPage.GoToAbout();
        List<String> actual = aboutPage.getMembers();
        Allure.addAttachment("TC13 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        List<String> expected = new ArrayList<>();
        expected.add("PABLO ESCOBAR - Creative Director");
        expected.add("MONTINO RIAU - Product Manager");
        expected.add("ALEX NAASRI - Chief Design Officer");
        expected.add("HONGMAN CHIOA - UX Researcher");
        expected.add("SANTIO ANDRESS - Content Researcher");
        expected.add("RAMESH PAUL - Creative Designer");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Tag("LandingPage")
    @Story("DataRead & DataWrite Test")
    @DisplayName("TC14 - Read Comments And Related Data On About Page And Compare With File")
    @Description("Verify that Comments and related Data matches the predetermined data, and write data to file")
    public void DataReadTest3 () throws InterruptedException, IOException {
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        String validUserName = "beckz";
        String validPassword = "30y123";
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());
        List<Map<String, String>>actual = landingPage.getComments();
        landingPage.writeCommentsToFile(actual, "CollectedComments.txt");
        List<Map<String, String>>expected = landingPage.readCommentsFile();
        Assertions.assertEquals(expected, actual);
        File file = new File("CollectedComments.txt");
        Assertions.assertTrue(file.exists());
    }

    @AfterEach
    public void TearDown () {
        driver.quit();
    }
}
