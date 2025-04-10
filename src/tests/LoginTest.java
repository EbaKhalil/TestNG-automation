package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelReader;
import java.io.IOException;
import java.time.Duration;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() throws IOException {
        String filePath = "C:\\Users\\GAIF\\eclipse-workspace\\Homework2\\src\\testData\\LoginData.xlsx";
        return ExcelReader.readExcelData(filePath, "Sheet1");
    }
    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password) {
        driver.get("https://www.frontgate.com/ShoppingCartView");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        String actualMessage = loginPage.getDisplayedMessage();

        String expectedMessage = "";

        if (!email.contains("@") && password.isEmpty()) {
            expectedMessage = "Error: Please enter Email Address in a valid format. | Error: Please enter Current Password.";
        } else if (email.equals("ebakhalil7@gmail.com") && password.equals("123456789Eba@@")) {
            expectedMessage = "You May Also Like"; 

            Actions actions = new Actions(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            WebElement icon = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//span[@class='c-button__inner t-header__inner-button'])[1]")));
            actions.moveToElement(icon).perform();
            
            actions.moveToElement(icon).pause(Duration.ofSeconds(3)).perform();

         WebElement myAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath("//div[@class='my-account' and text()='My Account']")));
         Assert.assertTrue(myAccount.isDisplayed(), "My Account option not displayed after hover.");

        } else {
            expectedMessage = "Email/Password you entered is not correct. Please try again."; 
        }

        Assert.assertEquals(actualMessage, expectedMessage, "The displayed message does not match the expected result.");
    }
	private void WebDriverWait(WebDriver driver, Duration ofSeconds) {
		// TODO Auto-generated method stub
		
	}

}