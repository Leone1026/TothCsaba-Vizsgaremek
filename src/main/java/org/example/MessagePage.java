package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class MessagePage extends BasePage{
    public MessagePage(WebDriver driver) {
        super(driver);
    }

    public final By INPUT_FIRST_NAME = By.id("first-name");
    public final By INPUT_LAST_NAME = By.id("last-name");
    public final By INPUT_EMAIL = By.id("email");
    public final By DROPDOWN_PROJECTTYPE = By.id("projectType");
    public final By INPUT_ABOUT_PROJECT = By.id("aboutProject");
    public final By BUTTON_SUBMIT = By.id("contact-form-button");

    // This method switches to the alert and accepts it
    public void handleAlert()  {
        driver.switchTo().alert().accept();
    }

    // This method clicks on the submit button
    public void clickSubmit() {
        driver.findElement(BUTTON_SUBMIT).click();
    }

    // This method fills out a form (4 fields and selects from a dropdown menu),
    // then clicks on the submit button.
    public void fillOutFormBasic(String firstname, String lastname, String email, String Type, String message) {
        driver.findElement(INPUT_FIRST_NAME).sendKeys(firstname);
        driver.findElement(INPUT_LAST_NAME).sendKeys(lastname);
        driver.findElement(INPUT_EMAIL).sendKeys(email);
        Select projectType = new Select(driver.findElement(DROPDOWN_PROJECTTYPE));
        projectType.selectByVisibleText(Type);
        driver.findElement(INPUT_ABOUT_PROJECT).sendKeys(message);
        driver.findElement(BUTTON_SUBMIT).click();
    }

}
