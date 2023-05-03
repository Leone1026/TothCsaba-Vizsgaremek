package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

abstract class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // This method scrolls the web page to bring a certain element into view.
    // It takes an XPath selector as an argument, finds the first element that
    // matches the selector, and scrolls the page so that the element is visible.
    // It also waits for half a second to ensure that the scroll is complete.
    public void scrollToElement(By xpath) throws InterruptedException {
        WebElement element = driver.findElement(xpath);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

}