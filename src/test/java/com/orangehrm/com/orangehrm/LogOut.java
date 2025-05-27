package com.orangehrm.com.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogOut extends Base {
	
	@BeforeMethod
	public void loginforLogout() {
	    login("Admin", "admin123");
	}
	@Test
	public void Logout() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,'oxd-userdropdown-icon')]"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']"))).click();
		wait.until(ExpectedConditions.urlContains("login"));
	}
	

}
