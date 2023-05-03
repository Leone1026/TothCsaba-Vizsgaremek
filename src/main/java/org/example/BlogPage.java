package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class BlogPage extends BasePage{
    public BlogPage(WebDriver driver) {
        super(driver);
    }

    public final By ACTIVE_PAGE_BUTTON = By.xpath(" //*[@class='page-item active']");

    public final By BLOG_TITLES = By.xpath("//*[@class=\"site-blog-post-content\"]//h3");

    public final By PAGINATION_AREA_ABOVE = By.xpath("//*[@class='site-blog-post-thumb']");
    public final By PAGINATION_AREA = By.xpath("//ul[@class='pagination']");

    public final By NEXT_BUTTON = By.xpath(" //*[@aria-label='Next']");
    public final By NEXT_BUTTON_PARENT = By.xpath(" //*[@aria-label='Next']/..");

    // This method returns the current page number
    // of a paginated list of blog entries on a web page.
    public int GetPageNumber() {
        WebElement body = driver.findElement(By.tagName("body"));
        // Scroll down the page three times to ensure that the pagination bar is visible.
        for (int i = 0; i < 3; i++) {
            body.sendKeys(Keys.PAGE_DOWN);
        }

        // Wait three seconds to ensure that the page has finished loading.
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        // Find the currently active page button.
        WebElement activePageButton = driver.findElement(ACTIVE_PAGE_BUTTON);

        // Find its child <a> element.
        WebElement activePageButtonChild = activePageButton.findElement(By.tagName("a"));

        // Get the text of the child element.
        String activePageButtonText = activePageButtonChild.getText();

        // Parse the page number from the text.
        int pageNumber = Integer.parseInt(activePageButtonText);

        // Return page number as an integer.
        return pageNumber;
    }

    // This method returns the number of blog posts on a web page
    // that is paginated across multiple pages.
    public int GetBlogNumber(int maxPageNumber) throws InterruptedException {
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 2; i++) {   // Scroll down the page twice
            body.sendKeys(Keys.PAGE_DOWN);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        int pageNumber = GetPageNumber(); // Get the current page number

        // Count the number of blog posts on the page.
        int blogNumber = driver.findElements(BLOG_TITLES).size();

        // Scroll above the pagination area.
        scrollToElement(PAGINATION_AREA_ABOVE);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // While there are more pages and the "next" button is enabled
        // click the "next" button. This process repeats until it reaches
        // the maximum page number or the "next" button is disabled.
        while (pageNumber < maxPageNumber && !driver.findElement(NEXT_BUTTON_PARENT).getAttribute("class").contains("disabled")) {
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
            driver.findElement(NEXT_BUTTON).click();
            Thread.sleep(2000);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            Thread.sleep(2000);
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
            scrollToElement(BLOG_TITLES);
            Thread.sleep(2000);
            int newBlogNumber = driver.findElements(BLOG_TITLES).size();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            blogNumber = blogNumber + newBlogNumber;
            Thread.sleep(3000);
            scrollToElement(PAGINATION_AREA_ABOVE);
            Thread.sleep(3000);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        } return blogNumber; // Return the total number of blog posts.

    }


}
