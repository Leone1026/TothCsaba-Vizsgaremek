package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage{
    public ProfilePage(WebDriver driver) {
        super(driver);
    }


    public final By INPUT_NAME = By.id("name");
    public final By INPUT_BIO = By.id("bio");
    public final By INPUT_PHONE = By.id("phone-number");
    public final By BUTTON_SAVEPROFILE = By.xpath("//button[@onclick='editUser()']");

    public final By BUTTON_DELETEACCOUNT = By.xpath("//button[@onclick='showRealDeleteAccBtn()']");

    public final By BUTTON_DELETE_CONFIRMATION = By.id("delete-account-btn");

    public final By ALERT_EDITED = By.id("edit-alert");
    //"Profile Edited!"
    public void ModifyProfile(String name, String bio, String phone) {
        driver.findElement(INPUT_NAME).sendKeys(name);
        driver.findElement(INPUT_BIO).sendKeys(bio);
        driver.findElement(INPUT_PHONE).sendKeys(phone);
        driver.findElement(BUTTON_SAVEPROFILE).click();

    }
    public boolean EditAlertIsDisplayed () {
        return(driver.findElement(ALERT_EDITED).isDisplayed());
    }

    public void DeleteProfile() {
        driver.findElement(BUTTON_DELETEACCOUNT).click();
        driver.findElement(BUTTON_DELETE_CONFIRMATION).click();
    }

}
