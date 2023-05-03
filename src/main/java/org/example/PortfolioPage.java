package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PortfolioPage extends BasePage {
    public PortfolioPage(WebDriver driver) {
        super(driver);
    }

    public final String url = "https://lennertamas.github.io/roxo/portfolio.html";

    public final By BUTTON_ARROW_DOWN = By.xpath("//img[@alt=\"arrow-down\"]");
    public final By PAGINATION_AREA = By.xpath("//ul[@class='pagination']");

    public final By PAGINATION_AREA_ABOVE = By.xpath("//*[@class='site-project-item-thumb']");

    public final By PRODUCTS = By.xpath("//div[@class='site-project-item-content']");

    public final By ACTIVE_PAGE_BUTTON = By.xpath(" //*[@class='page-item active']");

    public final By NEXT_BUTTON = By.xpath(" //*[@aria-label='Next']");

    public final By NEXT_BUTTON_PARENT = By.xpath(" //*[@aria-label='Next']/..");
    public final By LAST_BUTTON = By.xpath(" //*[@aria-label='Last']");

    public final By PREVIOUS_BUTTON = By.xpath(" //*[@aria-label='Previous']");

    public final By FIRST_BUTTON = By.xpath(" //*[@aria-label='First']");

    public final By PRODUCT_NAME = By.xpath("//*[@class=\"site-project-item-content\"]//h3");

    // This method returns the current page number
    // of a paginated list of items on a web page.
    public int GetPageNumber() {

        // Scroll down the page three times to ensure that the pagination bar is visible.
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 3; i++) {
            body.sendKeys(Keys.PAGE_DOWN);
        }

        // Wait three seconds to ensure that the page has finished loading.
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        // Find the currently active page button and its child <a> element.
        WebElement activePageButton = driver.findElement(ACTIVE_PAGE_BUTTON);
        WebElement activePageButtonChild = activePageButton.findElement(By.tagName("a"));

        // Get the text of the child element.
        String activePageButtonText = activePageButtonChild.getText();

        // Parse the page number from the text.
        int pageNumber = Integer.parseInt(activePageButtonText);

        // Return page number as an integer.
        return pageNumber;
    }

    // This method scrolls to the next button and clicks it to navigate to
    // the next page of products. It returns true if the next button is found and clicked,
    // false otherwise.
    public boolean GoToNextButton() throws InterruptedException {
        Thread.sleep(3000);
        scrollToElement(PAGINATION_AREA_ABOVE);
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        scrollToElement(PRODUCT_NAME);
        Thread.sleep(4000);
        try {
            driver.findElement(NEXT_BUTTON).click();
        } catch (
                Exception e) {

            // If there is an exception, return false to indicate
            // that the next button was not found or could not be clicked
            return false;

            // If the next button was clicked successfully, return true to indicate success
        }
        return true;
    }

    // This method scrolls to the Last Page button and clicks it to navigate to
    // the last page of products. It returns true if the Last Page button is found and clicked,
    // false otherwise.
    public boolean GoToLastButton() throws InterruptedException {
        Thread.sleep(3000);
        scrollToElement(PAGINATION_AREA_ABOVE);
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        scrollToElement(PRODUCT_NAME);
        Thread.sleep(4000);
        try {
            driver.findElement(LAST_BUTTON).click();
        } catch (
                Exception e) {
            return false;
        }
        return true;
    }

    // This method scrolls to the Previous button and clicks it to navigate to
    // the previous page of products. It returns true if the Previous button is
    // found and clicked,false otherwise.
    public boolean GoToPreviousButton() throws InterruptedException {
        Thread.sleep(3000);
        scrollToElement(PAGINATION_AREA_ABOVE);
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        scrollToElement(PRODUCT_NAME);
        Thread.sleep(4000);
        try {
            driver.findElement(PREVIOUS_BUTTON).click();
        } catch (
                Exception e) {
            return false;
        }
        return true;
    }

    // This method scrolls to the First Page button and clicks it to navigate to
    // the first page of products. It returns true if the First Page button is found and clicked,
    // false otherwise.
    public boolean GoToFirstButton() throws InterruptedException {
        Thread.sleep(3000);
        scrollToElement(PAGINATION_AREA_ABOVE);
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        scrollToElement(PRODUCT_NAME);
        Thread.sleep(4000);
        try {
            driver.findElement(FIRST_BUTTON).click();
        } catch (
                Exception e) {
            return false;
        }
        return true;
    }

    // This method scrolls to the next button and clicks it to navigate
    // to the next page of products. It also throws InterruptedException
    // if there is a problem with the thread sleep.
    public void clickNext() throws InterruptedException {
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 2; i++) {
            body.sendKeys(Keys.PAGE_DOWN);
        }
        Thread.sleep(3000);

        // Scroll to an area which is above the pagination area
        scrollToElement(PAGINATION_AREA_ABOVE);

        Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);

        // Click the next button
        driver.findElement(NEXT_BUTTON).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    // This method scrolls to the Previous button and clicks it to navigate
    // to the previous page of products. It also throws InterruptedException
    // if there is a problem with the thread sleep.
    public void clickPrevious() throws InterruptedException {
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 3; i++) {
            body.sendKeys(Keys.PAGE_DOWN);
        }
        Thread.sleep(3000);
        scrollToElement(PAGINATION_AREA_ABOVE);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(PREVIOUS_BUTTON).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void clickLastPage() throws InterruptedException {
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 2; i++) {
            body.sendKeys(Keys.PAGE_DOWN);
        }
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        scrollToElement(PAGINATION_AREA_ABOVE);
        driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
        driver.findElement(LAST_BUTTON).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    // This method scrolls to the Last Page button and clicks it to navigate
    // to the last page of products. It also throws InterruptedException
    // if there is a problem with the thread sleep
    public void clickFirstPage() throws InterruptedException {
        Thread.sleep(3000);
        scrollToElement(PAGINATION_AREA_ABOVE);
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(FIRST_BUTTON).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    // This method gets the maximum page number that can be reached by clicking
    // the "Next Page" button repeatedly until reaching the last page or the specified
    // maximum page number.
    public int GetPageNumberMax(int maxPageNumber) throws InterruptedException {
        // Scroll down the page to make sure the pagination area is visible
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 2; i++) {
            body.sendKeys(Keys.PAGE_DOWN);
        }

        // Wait for the page to load
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Get the current page number
        int pageNumber = GetPageNumber();

        // Scroll above the pagination area to click the "Next Page" button
        scrollToElement(PAGINATION_AREA_ABOVE);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Click the "Next Page" button repeatedly until reaching the last page
        // or the specified maximum page number
        while (pageNumber < maxPageNumber && !driver.findElement(NEXT_BUTTON_PARENT).getAttribute("class").contains("disabled")) {
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
            driver.findElement(NEXT_BUTTON).click();
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            Thread.sleep(3000);
            scrollToElement(PAGINATION_AREA_ABOVE);
            Thread.sleep(3000);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

            // Increment the page number
            pageNumber++;
        }

        // Return the maximum page number that can be reached
        return pageNumber;
    }


    // This method returns the total number of products on the page, across all pages
    // up to the specified maximum.
    public int GetProductNumber(int maxPageNumber) throws InterruptedException {
        // Scroll down the page to load all products
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 2; i++) {
            body.sendKeys(Keys.PAGE_DOWN);
        }

        // Get the current page number and count the number of products on the page
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        int pageNumber = GetPageNumber();
        int productNumber = driver.findElements(PRODUCT_NAME).size();

        // Scroll to the area above the pagination area and wait for elements to load
        scrollToElement(PAGINATION_AREA_ABOVE);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Check all pages up to the maximum, if there are more pages
        while (pageNumber < maxPageNumber && !driver.findElement(NEXT_BUTTON_PARENT).getAttribute("class").contains("disabled")) {
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

            // Click the next button to go to the next page
            driver.findElement(NEXT_BUTTON).click();

            Thread.sleep(2000);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            Thread.sleep(2000);
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

            // Scroll down to load new products and count them
            scrollToElement(PRODUCTS);

            Thread.sleep(2000);
            int newProductNumber = driver.findElements(PRODUCT_NAME).size();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            productNumber = productNumber + newProductNumber;
            Thread.sleep(3000);
            scrollToElement(PAGINATION_AREA_ABOVE);
            Thread.sleep(3000);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        }
        return productNumber;

    }


    //  This method retrieves a list of product names from the portfolio pages
    //  up to the specified max page number
    public List<String> ListProductsName(int maxPageNumber) throws InterruptedException {

        // Create an empty list to store product names
        List<String> productNames = new ArrayList<>();

        // Get the current page number
        int pageNumber = GetPageNumber();

        // Loop until we reach the maximum page number
        while (pageNumber <= maxPageNumber) {

            // Wait for the products to load and scroll to the products area
            driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
            scrollToElement(PRODUCTS);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            // Get all the product name elements on the current page
            List<WebElement> productNameElements = driver.findElements(PRODUCT_NAME);

            // While iterating through the products,
            // Add each product name to the list
            for (WebElement productNameElement : productNameElements) {
                productNames.add(productNameElement.getText());
            }

            // If we've reached the maximum page number or there are no more pages, exit the loop
            if (pageNumber == maxPageNumber || driver.findElement(NEXT_BUTTON_PARENT).getAttribute("class").contains("disabled")) {
                break;
            }

            // Scroll to the area above the pagination area and click the next button
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
            Thread.sleep(3000);
            scrollToElement(PAGINATION_AREA_ABOVE);
            Thread.sleep(3000);
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
            driver.findElement(NEXT_BUTTON).click();
            Thread.sleep(2000);

            // Increment the page number
            pageNumber++;
        }

        // Return the list of product names
        return productNames;
    }

}


