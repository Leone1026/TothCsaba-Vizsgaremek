package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AboutPage extends BasePage{

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public final By EXPERTISE_DATA = By.xpath("//ul[@class='site-expertise-list']/li");
    public final By MEMBERS_DATA = By.xpath("//div[@class='site-team-member-content']");

    public final By MEMBERS_NAME = By.xpath("//*[@class='site-team-member-content']/h3");


    // This method retrieves expertise data from the "About" sub page. It finds all elements that match
    // the EXPERTISE_DATA locator and collects their text values into a single string.
    public String getExpertiseData () {
        List<WebElement> lines = driver.findElements(EXPERTISE_DATA);
        String result = "";
        for (WebElement line : lines) {
            String value = line.getText();
            result = result + ", " + value;
        }
        return result.substring(2);
    }

    // This method retrieves member data from the "About" sub page.
    // It scrolls down the page, finds all elements that match the MEMBERS_DATA locator,
    // and adds their text values to a list of strings.
    // Each string in the list contains the name and position of a member.
    public List<String> getMembers() throws InterruptedException {
        WebElement body = driver.findElement(By.tagName("body"));
        for (int i = 0; i < 3; i++) {
            body.sendKeys(Keys.PAGE_DOWN); // Scroll down the page three times
        }
        Thread.sleep(300); // Wait for the page to finish loading
        List<WebElement> datas = driver.findElements(MEMBERS_DATA);
        List<String> result = new ArrayList<>();
        for (WebElement data : datas) {

                // Split the text value into name and position
                String[] parts = data.getText().split("\n");
                String name = parts[0];
                String position = parts[1];

                // Add the name and position to the result list
                result.add(name + " - " + position);
            }
        return result;
        }

}
