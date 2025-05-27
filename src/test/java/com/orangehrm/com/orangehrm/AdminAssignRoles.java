package com.orangehrm.com.orangehrm;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminAssignRoles extends Base {
	@BeforeMethod
	public void loginforAssignRoles() {
	    login("Admin", "admin123");
	}
	@Test(priority = 1)
	public void addNewEmployeeForAdminUserRole() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))).click();
		wait.until(ExpectedConditions.urlContains("viewEmployeeList"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Add')]"))).click();
		WebElement AddEmployeeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Add Employee']")));
		Assert.assertTrue(AddEmployeeHeader.isDisplayed(),"Cannot Add New Employee");	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First Name']"))).sendKeys("meriya");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']"))).sendKeys("Baby");
		String empId = String.valueOf(System.currentTimeMillis()).substring(7);  // Or use UUID.randomUUID().toString().substring(0, 8)
		WebElement empIdField =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Id']/../following-sibling::div/input")));
		empIdField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE + empId);
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-form-loader")));

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Save ']"))).click();
		WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-toast-content') and contains(.,'Success')]")));
		// Step: Print and Assert
		System.out.println("✅ Successfully Add Message: " + successMessage.getText());
		Assert.assertTrue(successMessage.isDisplayed(), "Bug: New Employe Addition Fails.");	  

		wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
		
	}@Test(priority = 2)
	public void AssignandViewRoles() {

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']"))).click();
		wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Add ']"))).click();
		wait.until(ExpectedConditions.urlContains("saveSystemUser"));
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='User Role']/following::div[@class='oxd-select-text-input'])[1]"))).click();
		WebElement essOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
		    By.cssSelector("div.oxd-select-option"))); // selects first visible option — you may need to loop to find 'ESS'
		// Optional: Click the specific option with text 'ESS' (still needed unless option text is embedded in a class)
		List<WebElement> options = driver.findElements(By.cssSelector("div.oxd-select-option"));
		for (WebElement option : options) {
		    if (option.getText().trim().equals("ESS")) {
		        option.click();
		        break;
		    }
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type for hints...']"))).sendKeys("meriya Baby");		
		List<WebElement> options11 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.xpath("//div[@role='listbox']//span")));
			// 3. Loop through the list and click the matching name
			for (WebElement option : options11) {
			    if (option.getText().equalsIgnoreCase("meriya Baby")) {
			        option.click();
			        break;
			    }
			}	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Status']/following::div[@class='oxd-select-wrapper']"))).click();
		List<WebElement> options1 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.cssSelector("div.oxd-select-option")
			));

			for (WebElement option : options1) {
			    if (option.getText().trim().equals("Enabled")) {
			        option.click();
			        break;
			    }	}	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Username']/following::div//input[@class='oxd-input oxd-input--active'])[1]"))).sendKeys("meriya12");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Password']/following::div//input[@class='oxd-input oxd-input--active'])[1]"))).sendKeys("meriya1234");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Confirm Password']/following::div//input[@class='oxd-input oxd-input--active'])"))).sendKeys("meriya1234");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Save ']"))).click();
		wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type for hints...']"))).sendKeys("meriya Baby");		
		List<WebElement> options111 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.xpath("//div[@role='listbox']//span")));
			// 3. Loop through the list and click the matching name
			for (WebElement option : options111) {
			    if (option.getText().equalsIgnoreCase("meriya Baby")) {
			        option.click();
			        break;
			    }
			}	
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']"))).click();
			WebElement SearchUserRole= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Employee Name']/following::div[normalize-space()='meriya Baby'][1]")));
			Assert.assertTrue(SearchUserRole.isDisplayed(),"Assigned User Role is not Displayed");
		
	}@Test(priority = 3)
	public void EditUserRolewithInvalidCharacters() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']"))).click();
		wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
		WebElement SearchRole = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Name']/following::input[@placeholder='Type for hints...']")));
		SearchRole.sendKeys("meriya Baby");
		List<WebElement> options1111 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.xpath("//div[@role='listbox']//span")));
			// 3. Loop through the list and click the matching name
			for (WebElement option : options1111) {
			    if (option.getText().equalsIgnoreCase("meriya Baby")) {
			        option.click();
			        break;
			    }
			}	
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Employee Name']/following::div[text()='meriya Baby']")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='oxd-icon bi-pencil-fill'][1]"))).click();
			wait.until(ExpectedConditions.urlContains("saveSystemUser"));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='User Role']/following::div[@class='oxd-select-text-input'])[1]"))).click();
			WebElement essOptionSpecialCharectors = wait.until(ExpectedConditions.visibilityOfElementLocated(
			    By.cssSelector("div.oxd-select-option"))); // selects first visible option — you may need to loop to find 'ESS'
			// Optional: Click the specific option with text 'ESS' (still needed unless option text is embedded in a class)
			List<WebElement> options = driver.findElements(By.cssSelector("div.oxd-select-option"));
			for (WebElement option : options) {
			    if (option.getText().trim().equals("Admin")) {
			        option.click();
			        break;
			    }
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Username']/following::div//input[@class='oxd-input oxd-input--active'])[1]"))).sendKeys("meriya$#%%");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Save ']"))).click();
			wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
			String ExpectedUrl="https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers";
			String ActualUrl= driver.getCurrentUrl();
			Assert.assertNotEquals(ExpectedUrl,ActualUrl,"Bug detected: User Role Assigned with Special Charectors.No Validation");

	}
	
	
	
	@Test(priority = 4)
	public void AssignRoleswithSpecialCharectors() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']"))).click();
		wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Add ']"))).click();
		wait.until(ExpectedConditions.urlContains("saveSystemUser"));		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='User Role']/following::div[@class='oxd-select-text-input'])[1]"))).click();
		WebElement essOptionSpecialCharectors = wait.until(ExpectedConditions.visibilityOfElementLocated(
		    By.cssSelector("div.oxd-select-option"))); // selects first visible option — you may need to loop to find 'ESS'
		// Optional: Click the specific option with text 'ESS' (still needed unless option text is embedded in a class)
		List<WebElement> options = driver.findElements(By.cssSelector("div.oxd-select-option"));
		for (WebElement option : options) {
		    if (option.getText().trim().equals("Admin")) {
		        option.click();
		        break;
		    }
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type for hints...']"))).sendKeys("meriya Baby");		
		List<WebElement> options11 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.xpath("//div[@role='listbox']//span")));
			for (WebElement option : options11) {
			    if (option.getText().equalsIgnoreCase("meriya Baby")) {
			        option.click();
			        break;
			    }
			}	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Status']/following::div[@class='oxd-select-wrapper']"))).click();
		List<WebElement> options1 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.cssSelector("div.oxd-select-option")
			));

			for (WebElement option : options1) {
			    if (option.getText().trim().equals("Enabled")) {
			        option.click();
			        break;
			    }	}	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Username']/following::div//input[@class='oxd-input oxd-input--active'])[1]"))).sendKeys("%$%*&");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Password']/following::div//input[@class='oxd-input oxd-input--active'])[1]"))).sendKeys("meriya1234");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Confirm Password']/following::div//input[@class='oxd-input oxd-input--active'])"))).sendKeys("meriya1234");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Save ']"))).click();
		wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
		String ExpectedUrl="https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers";
		String ActualUrl= driver.getCurrentUrl();
		Assert.assertNotEquals(ExpectedUrl,ActualUrl,"Bug detected: User Role Assigned with Special Charectors.No Validation");
	}
	@Test(priority = 6 )
	public void DeleteUserRole() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']"))).click();
		wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
		WebElement SearchRole = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Name']/following::input[@placeholder='Type for hints...']")));
		SearchRole.sendKeys("meriya Baby");
		List<WebElement> options1111 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
			    By.xpath("//div[@role='listbox']//span")));
			// 3. Loop through the list and click the matching name
			for (WebElement option : options1111) {
			    if (option.getText().equalsIgnoreCase("meriya Baby")) {
			        option.click();
			        break;
			    }
			}	
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Employee Name']/following::div[text()='meriya Baby']")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[@class='oxd-icon bi-trash'])[1]"))).click();
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Yes, Delete']"))).click();
		    // Step 4: Wait for success message to confirm deletion
		    WebElement deleteSuccessMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'oxd-toast-content') and contains(.,'Successfully')]")));
		    System.out.println("✅ Delete Message: " + deleteSuccessMessage.getText());
		    Assert.assertTrue(deleteSuccessMessage.isDisplayed(), "❌ Employee cannot be deleted.");

	}
	 @AfterMethod
	    public void tearDown(ITestResult result) throws InterruptedException {
	        if (result.getStatus() == ITestResult.FAILURE) {
	        	Thread.sleep(2000); // Add 2-second delay (replace with explicit wait if possible)

	            takeScreenshot(result.getName()); // capture screenshot if test fails
	        }
	        tearDown();
	    }
	
}