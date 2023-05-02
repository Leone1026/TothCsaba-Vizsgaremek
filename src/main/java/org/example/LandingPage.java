package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LandingPage extends BasePage {
    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public final String url = "https://lennertamas.github.io/roxo/landing.html";

    public final By ABOUT_BUTTON = By.xpath("//li[@class='nav-item']/a[contains(@href, 'about')]");

    public final By OPINION_AREA = By.xpath("//section[@class=\"site-testimonial\"]");

    public final By OPINIONS_PERSONS = By.xpath("//section[4]/div/div/div[position() > 1]/div");

    public final By PORTFOLIO_BUTTON = By.xpath("//nav/div/div/ul/li[3]/a");
    public final By BLOG_BUTTON = By.xpath("//nav/div/div/ul/li[4]/a");

    public final By MESSAGE_BUTTON = By.xpath("//*[@data-text='Get in touch']");

    public final By PROFILE_BUTTON = By.id("profile-btn");

    public final By LOGOUT_BUTTON = By.xpath("//a[@onclick='logout()']");

    public String GetURL() {
        return (driver.getCurrentUrl());
    }

    public void GoToAbout() {
        driver.findElement(ABOUT_BUTTON).click();
    }

    public void GoToPortfolio() {
        driver.findElement(PORTFOLIO_BUTTON).click();
    }
    public void GoToBlog() {
        driver.findElement(BLOG_BUTTON).click();
    }

    public void GoToMessage() {
        driver.findElement(MESSAGE_BUTTON).click();
    }

    public void GoToProfile() {
        driver.findElement(PROFILE_BUTTON).click();
    }

    public void clickOnLogout() {
        driver.findElement(LOGOUT_BUTTON).click();
    }

    public void writeCommentsToFile(List<Map<String, String>> commentList, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (Map<String, String> comment : commentList) {
            writer.write("Name: " + comment.get("Name") + "\n");
            writer.write("Occupation: " + comment.get("Occupation") + "\n");
            writer.write("Comment: " + comment.get("Comment") + "\n\n");
        }
        writer.close();
    }

    public List<Map<String, String>> getComments() throws InterruptedException, IOException {
        List<Map<String, String>> commentList = new ArrayList<>();
        scrollToElement(OPINION_AREA);
        List<WebElement> elements = driver.findElements(OPINIONS_PERSONS);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        for (WebElement element : elements) {
            String name = element.findElement(By.xpath(".//div[2]/h5")).getText();
            String occupation = element.findElement(By.xpath(".//div[2]/p")).getText();
            String comment = element.findElement(By.xpath(".//p[@class='site-testimonial-item-body']")).getText();
            Map<String, String> person = new HashMap<>();
            person.put("Occupation", occupation);
            person.put("Comment", comment);
            person.put("Name", name);
            commentList.add(person);
        }
        return commentList;
    }

    public List<Map<String, String>> readCommentsFile() throws IOException {
        List<Map<String, String>> commentsFromFiles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("CommentList.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                Map<String, String> map = new HashMap<>();
                map.put("Name", parts[0]);
                map.put("Occupation", parts[1]);
                map.put("Comment", parts[2]);
                commentsFromFiles.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commentsFromFiles;
    }
}
