package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {
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

    // This method modifies the user profile with the provided name, bio, and phone.
    public void ModifyProfile(String name, String bio, String phone) {
        driver.findElement(INPUT_NAME).sendKeys(name);
        driver.findElement(INPUT_BIO).sendKeys(bio);
        driver.findElement(INPUT_PHONE).sendKeys(phone);
        driver.findElement(BUTTON_SAVEPROFILE).click();
    }

    // This method checks if the edit alert is displayed.
    // It returns true if the edit alert is displayed, false otherwise.
    public boolean EditAlertIsDisplayed() {
        return (driver.findElement(ALERT_EDITED).isDisplayed());
    }

    // This method deletes the user profile by clicking on the delete account button
    // and confirming the deletion.
    public void DeleteProfile() {
        driver.findElement(BUTTON_DELETEACCOUNT).click();
        driver.findElement(BUTTON_DELETE_CONFIRMATION).click();
    }

}
