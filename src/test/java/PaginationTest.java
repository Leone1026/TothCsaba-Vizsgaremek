import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.checkerframework.checker.units.qual.A;
import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaginationTest {
    WebDriver driver;

    LoginPage loginPage;
    LandingPage landingPage;
    PortfolioPage portfolioPage;
    BlogPage blogPage;


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
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("TC15 - Checking If First Page Is Displayed")
    @Description("Verify that when user clicks on Portfolio, the first page appears")
    public void CheckFirstPage() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Click on the portfolio button and check the page number
        landingPage.GoToPortfolio();
        int actual = portfolioPage.GetPageNumber();
        Assertions.assertEquals(1, actual);
    }


    // This test verifies that when the user clicks on the Next button
    // on the Portfolio page, the website loads the next page.
    @Test
    @Order(2)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC16 - Checking If Click On Next Button Functions Perfectly")
    @Description("Verify that when user clicks on Next Button, the website loads the next page.")
    public void ClickNextTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to Portfolio page
        landingPage.GoToPortfolio();

        // Click on the Next button and wait for the next page to load
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        portfolioPage.clickNext();
        String actual = landingPage.GetURL();

        // Verify that the user is on the expected (second) page, so the
        // Next button functions perfectly
        String expected = "https://lennertamas.github.io/roxo/portfolio/page/2/";
        Assertions.assertEquals(expected, actual);
    }


    // This test verifies that when the user clicks on the Last Page button
    // on the Portfolio page, the website loads the last page.
    @Test
    @Order(3)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC17 - Checking Last Page Button")
    @Description("Verify that when user clicks on Last Page Button, the website loads the last page.")
    public void GetLastPageNumberTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Go to the portfolio page and click on the "Last Page" button
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickLastPage();

        // Get the page number of the last page and verify that it's the expected value
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int actual = portfolioPage.GetPageNumber();
        Allure.addAttachment("TC17 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(2, actual);
    }


    // This test case verifies the functionality of the "Previous" button
    // on the Portfolio page of the Roxo website.
    @Test
    @Order(4)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC18 - Checking Previous Button")
    @Description("Verify that when user clicks on Previous Button, the website loads the previous page.")
    public void ClickPreviousTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Click on the 'Portfolio' button in the navigation bar
        landingPage.GoToPortfolio();

        // Click on the 'Next' button to navigate to the second page of the portfolio
        portfolioPage.clickNext();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        // Verify that the user is on the correct page (second page of the portfolio)
        String actual = landingPage.GetURL();
        String expected = "https://lennertamas.github.io/roxo/portfolio/page/2/";
        Allure.addAttachment("TC18 - Screenshot 01", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(expected, actual);

        // Click on the 'Previous' button to navigate back to the first page of the portfolio
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickPrevious();

        // Verify that the user is on the correct page (first page of the portfolio)
        String actual2 = landingPage.GetURL();
        String expected2 = "https://lennertamas.github.io/roxo/portfolio/";
        Allure.addAttachment("TC18 - Screenshot 02", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertEquals(expected2, actual2);
    }


    // This test case verifies the functionality of the "First Page" button
    // on the Portfolio page of the Roxo website.
    @Test
    @Order(5)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC19 - Checking First Page Button")
    @Description("Verify that when user clicks on First Page Button, the website loads the first page.")
    public void GetFirstPageNumberTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the portfolio page and click on the First Page button
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        portfolioPage.clickFirstPage();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Verify that the first page is loaded
        int actual = portfolioPage.GetPageNumber();
        Assertions.assertEquals(1, actual);
    }


    // This test case also verifies the functionality of the "First Page" button
    // on the Portfolio page of the Roxo website.
    @Test
    @Order(6)
    @Severity(SeverityLevel.NORMAL)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC20 - Checking First Page Button On Another Page")
    @Description("Verify that when user clicks on First Page Button on the Second Page, the website loads the first page.")
    public void ClickFirstPageTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the portfolio page and click on the Next button
        landingPage.GoToPortfolio();
        portfolioPage.clickNext();
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

        // Verify that the URL of the current page is the expected one. (second portfolio page)
        String actual = landingPage.GetURL();
        String expected = "https://lennertamas.github.io/roxo/portfolio/page/2/";
        Assertions.assertEquals(expected, actual);

        // Click on the "First Page" button.
        portfolioPage.clickFirstPage();

        // Verify that the URL of the current page is the expected one.
        // (first page of Portfolio)
        String actual2 = landingPage.GetURL();
        String expected2 = "https://lennertamas.github.io/roxo/portfolio/";
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    @Order(7)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("TC21 - Get The Maximum Number Of Pages With Pagination")
    @Description("Verify that Pagination function works when user wants to get the maximum number of Pages.")
    public void GetLastPageNumberTest2() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Set the maximum number of pages to be checked to avoid an infinite loop
        int maxPageNumber = 100;

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Go to the Portfolio page
        landingPage.GoToPortfolio();

        // Get the maximum number of pages using the pagination function
        int actual = portfolioPage.GetPageNumberMax(maxPageNumber);

        // Add a screenshot to the test report
        Allure.addAttachment("TC21 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));

        // Verify that the actual number of pages matches the expected value
        Assertions.assertEquals(2, actual);
    }

    @Test
    @Order(8)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC22 - Checking if Next Button is enabled on Last Page")
    @Description("Verify that user cannot click on Next Button on Last Page")
    public void CheckNextButtonOnLastPageTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Go to the Portfolio page
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Click on the Last Page button
        portfolioPage.clickLastPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Check if the Next button is enabled on the last page of Portfolio
        boolean actual = portfolioPage.GoToNextButton();

        // Take a screenshot of the current page
        Allure.addAttachment("TC22 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));

        // Verify that the Next button is not enabled on the last page of Portfolio
        Assertions.assertFalse(actual);
    }


    @Test
    @Order(9)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC23 - Checking if Last Page Button is enabled on Last Page")
    @Description("Verify that user cannot click on Last Page Button on Last Page")
    public void CheckLastButtonOnLastPageTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Click on the "Portfolio" button in the navigation bar to go to the portfolio page.
        landingPage.GoToPortfolio();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Click on the "Last" button to go to the last page of the portfolio.
        portfolioPage.clickLastPage();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //  Check if the "Last" button is enabled.
        boolean actual = portfolioPage.GoToLastButton();

        // Take a screenshot of the page and add it as an attachment to the Allure report.
        Allure.addAttachment("TC23 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));

        // Assert that the actual value of the "Last" button is false,
        // indicating that the button is disabled on the last page of the portfolio.
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(10)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC24 - Checking if Previous Button is enabled on First Page")
    @Description("Verify that user cannot click on Previous Button on First Page")
    public void CheckPreviousButtonOnFirstPageTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the portfolio page
        landingPage.GoToPortfolio();

        // Navigate to the first page of the portfolio
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickFirstPage();

        // Verify that the previous button is disabled
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean actual = portfolioPage.GoToPreviousButton();
        Allure.addAttachment("TC24 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(11)
    @Severity(SeverityLevel.MINOR)
    @Story("ButtonTest")
    @Tag("PortfolioPage")
    @DisplayName("TC25 - Checking if First Button is enabled on First Page")
    @Description("Verify that user cannot click on First Page Button on First Page")
    public void CheckFirstButtonOnFirstPageTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the portfolio page
        landingPage.GoToPortfolio();

        // Click on the "First Page" button
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        portfolioPage.clickFirstPage();

        // Verify that the "First Page" button is disabled
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean actual = portfolioPage.GoToFirstButton();
        Allure.addAttachment("TC25 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
        Assertions.assertFalse(actual);
    }

    @Test
    @Order(12)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("TC26 - Getting The Number Of Products While Iterating Over Pages")
    @Description("Verify that Number of Products matches the requirements")
    public void PaginationTest() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the Portfolio page
        landingPage.GoToPortfolio();

        // Set the maximum number of pages to iterate over in order to avoid an infinite loop
        int maxPageNumber = 10;

        // Get the total number of products while iterating over pages
        int actual = portfolioPage.GetProductNumber(maxPageNumber);

        // Verify that the number of products matches the expected value
        Assertions.assertEquals(5, actual);
    }

    @Test
    @Order(13)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("PortfolioPage")
    @DisplayName("TC27 - Getting The Name Of Products While Iterating Over Pages")
    @Description("Verify that names of products matches the predetermined data")
    public void PaginationTest2() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        portfolioPage = new PortfolioPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the portfolio page
        landingPage.GoToPortfolio();

        // Set the maximum number of pages to iterate over in order to avoid an infinite loop
        int maxPageNumber = 10;

        // Create a list of expected product names
        List<String> expected = new ArrayList<>(Arrays.asList("KIO-TAPE BRAND", "USE-LESS BRAND", "OSEN CLOCK", "SEAMLESS WATCH", "KIO TAPE"));

        // Get the actual list of product names by iterating over the pages
        List<String> actual = portfolioPage.ListProductsName(maxPageNumber);

        // Add a screenshot to the test report
        Allure.addAttachment("TC27 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));

        // Verify that the actual list matches the expected list
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(14)
    @Severity(SeverityLevel.NORMAL)
    @Story("PaginationTest")
    @Tag("BlogPage")
    @DisplayName("TC28 - Getting The Number Of Blog Entries While Iterating Over Pages")
    @Description("Verify that Number of Blog Entries matches the requirements")
    public void PaginationTest3() throws InterruptedException {
        // Create instances of the pages that will be used in the test
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        blogPage = new BlogPage(driver);

        // Set up user credentials for the test
        String validUserName = "beckz";
        String validPassword = "30y123";

        // Navigate to the login page and log in with valid credentials
        loginPage.Navigate();
        loginPage.AcceptTermsAndConditions();
        loginPage.LoginFunction(validUserName, validPassword);

        // Verify that the user is on the correct page
        Assertions.assertEquals("https://lennertamas.github.io/roxo/landing.html", landingPage.GetURL());

        // Navigate to the Blog page
        landingPage.GoToBlog();

        // Set the maximum number of pages to iterate over in order to avoid an infinite loop
        int maxPageNumber = 10;

        // Get the actual number of blog entries
        int actual = blogPage.GetBlogNumber(maxPageNumber);

        // Attach a screenshot to the report
        Allure.addAttachment("TC28 - Screenshot", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));

        // Verify that the actual number of blog entries matches the expected value
        Assertions.assertEquals(5, actual);
    }

    // This method closes the browser after each test is executed.
    @AfterEach
    public void TearDown () {
        driver.quit();
    }

}
