package com.orangehrm.com.orangehrm;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Login extends Base {
	
	@Test
	public void OrangeHrmLoginValidCredentials() {
		login("Admin", "admin123");
		WebElement HomePageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
		Assert.assertTrue(HomePageHeader.isDisplayed(),"Login Failed");		
	}
	@Test
	public void OrangeHrmLoginEmptyCredentials() {
	    login("", "");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Required']")));

		List<WebElement> requiredFields = driver.findElements(By.xpath("//span[text()='Required']"));
		Assert.assertTrue(requiredFields.size() >= 1, "Required validation not triggered.");

	}
	@Test
	public void OrangeHrmLoginInvalidUsername() {
	    login("Admin123", "admin123");
		WebElement LoginErrorMessageWithInvalidUsername =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Invalid credentials']")));
		Assert.assertTrue(LoginErrorMessageWithInvalidUsername.isDisplayed(),"Login Success");
	}
	@Test
	public void OrangeHrmLoginInvalidPassword() {
	    login("Admin", "admin123456");
		WebElement LoginErrorMessageWithInvalidPassword =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Invalid credentials']")));
		Assert.assertTrue(LoginErrorMessageWithInvalidPassword.isDisplayed(),"Login Success");
	}
	@Test
	public void OrangeHrmLoginBothInvalidCredentials() {
	    login("Admin123", "admin12");
		WebElement LoginErrorMessage =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Invalid credentials']")));
		Assert.assertTrue(LoginErrorMessage.isDisplayed(),"Login Success");
	}@Test
	public void ForgotPasswordwithValidUsername() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Forgot your password? ']"))).click();
		wait.until(ExpectedConditions.urlContains("requestPasswordResetCode"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Username']"))).sendKeys("Admin");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Reset Password ']"))).click();
		WebElement PasswordReset = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Reset Password link sent successfully']")));
		Assert.assertTrue(PasswordReset.isDisplayed(),"Password cannot be reset");
	}
	
	@Test
	public void ForgotPasswordValidUsernameCheck(){
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Forgot your password? ']"))).click();
		wait.until(ExpectedConditions.urlContains("requestPasswordResetCode"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Username']"))).sendKeys("Black");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Reset Password ']"))).click();	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Reset Password link sent successfully']")));
		List<WebElement> successMessage = driver.findElements(By.xpath("//h6[text()='Reset Password link sent successfully']"));
	    Assert.assertTrue(successMessage.size() == 0, "❌ Bug: Reset password message shown for invalid username");
	  
	}		

	@Test
	public void ForgotPasswordCancelButton() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Forgot your password? ']"))).click();
		wait.until(ExpectedConditions.urlContains("requestPasswordResetCode"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Cancel ']"))).click();
		wait.until(ExpectedConditions.urlContains("login"));
	    String currentUrl = driver.getCurrentUrl();
	    Assert.assertTrue(currentUrl.contains("login"),"Forgot Password Cancel is not working properly");
	}
	
	 @AfterMethod
	    public void tearDown(ITestResult result) {
	        if (result.getStatus() == ITestResult.FAILURE) {
	            takeScreenshot(result.getName()); // capture screenshot if test fails
	        }
	        tearDown();
	    }
}
