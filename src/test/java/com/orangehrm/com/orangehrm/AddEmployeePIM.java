package com.orangehrm.com.orangehrm;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddEmployeePIM extends Base{
	
	@BeforeMethod
	public void LoginforaddNewEmployee() {
	    login("Admin", "admin123");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))).click();
		wait.until(ExpectedConditions.urlContains("viewEmployeeList"));

	}	
	
	@Test(priority = 1)
	public void addNewEmployee() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Add')]"))).click();
		WebElement AddEmployeeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Add Employee']")));
		Assert.assertTrue(AddEmployeeHeader.isDisplayed(),"Cannot Add New Employee");	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First Name']"))).sendKeys("Zara");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']"))).sendKeys("Mathew");
		String empId = String.valueOf(System.currentTimeMillis()).substring(7);  // Or use UUID.randomUUID().toString().substring(0, 8)
		WebElement empIdField =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Id']/../following-sibling::div/input")));
		empIdField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE + empId);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Save ']"))).click();
		wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
	}
	
	@Test(priority = 3)
	public void addEmployeWithInValidCharecters() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Add')]"))).click();
		WebElement AddEmployeeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Add Employee']")));
		Assert.assertTrue(AddEmployeeHeader.isDisplayed(),"Cannot Add New Employee");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First Name']"))).sendKeys("#@$$%");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']"))).sendKeys("&^%$");	
		String empIdWithInvalidCharecter = String.valueOf(System.currentTimeMillis()).substring(7);  // Or use UUID.randomUUID().toString().substring(0, 8)
		WebElement empIdField =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Id']/../following-sibling::div/input")));
		empIdField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE + empIdWithInvalidCharecter);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Save ']"))).click();
		boolean navigatedToDetails;
		try {
			wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
			navigatedToDetails = true;
		} catch (org.openqa.selenium.TimeoutException e) {
			navigatedToDetails = false;
		}

		if (navigatedToDetails) {
		    Thread.sleep(1000); // Let page render fully before failing

			Assert.fail("❌ Bug: Form accepted invalid characters in name fields and navigated to Personal Details.");
		} else {
			System.out.println("✅ Invalid characters correctly rejected. Stayed on Add Employee page.");
		}
	}
	
	
	@Test(priority = 2)
	public void ViewEmployeedetails() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Name']/following::input[@placeholder='Type for hints...'][1]"))).sendKeys("Zara Mathew");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']"))).click();		
		WebElement SearchResult= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='First (& Middle) Name']/following::div[normalize-space()='Zara'][1]")));
		Assert.assertTrue(SearchResult.isDisplayed(),"Bug ,the new Employee havent been added ");
	}
	
	
	@Test(priority = 4)	
	public void EditEmployee() throws InterruptedException {
	    // Step 1: Search for the employee
	    WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//label[text()='Employee Name']/following::input[@placeholder='Type for hints...'][1]")));
	    searchInput.clear();
	    searchInput.sendKeys("Zara Mathew");
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']"))).click();
	    // Step 2: Wait for the employee to appear in results
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//div[text()='First (& Middle) Name']/following::div[normalize-space()='Zara'][1]")));
	    // Step 3: Click the edit icon   
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//i[contains(@class, 'bi-pencil-fill')])[1]"))).click();

	    // Step 4: Wait for personal details page to load
	    wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
	 // Step 5: Edit the last name
	    WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']")));	    
	 // Get the current value before editing
	    wait.until(driver -> !lastNameField.getAttribute("value").isEmpty());
	    System.out.println("DEBUG: Attribute value found = '" + lastNameField.getAttribute("value") + "'");
	    lastNameField.sendKeys(Keys.chord(Keys.CONTROL, "a"), "Joseph");
	    driver.findElement(By.xpath("//h6[text()='Personal Details']")).click();
	 // Confirm value updated
	    wait.until(driver -> lastNameField.getAttribute("value").equals("Joseph"));  
	    Thread.sleep(500);
	// Confirm new value
	    String currentValue = lastNameField.getAttribute("value");
	    System.out.println("🔎 Current value after edit: " + currentValue);
	// Proceed to save
	WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()=' Save '])[1]")));
	saveBtn.click();
	// Step: Wait for success toast
	WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
	    By.xpath("//div[contains(@class,'oxd-toast-content') and contains(.,'Success')]")));
	// Step: Print and Assert
	System.out.println("✅ Edit Message: " + successMessage.getText());
	Assert.assertTrue(successMessage.isDisplayed(), "❌ Success message not displayed after update.");	  
	}
	
	
	@Test(priority = 5)    
	public void ValidateEditEmployeeWithSpecialCharecter() throws InterruptedException {
	    // Step 1: Search for the employee
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Name']/following::input[@placeholder='Type for hints...'][1]"))).sendKeys("Zara Joseph");
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']"))).click();
	    // Step 2: Wait for the employee to appear in results
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='First (& Middle) Name']/following::div[normalize-space()='Zara'][1]")));
	    // Step 3: Click the edit icon
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[contains(@class, 'bi-pencil-fill')])[1]"))).click();
	    // Step 4: Wait for navigation to personal details page
	    wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
	    // Step 5: Edit the last name
	    WebElement lastNameFieldEditEmployeeWithSpecialCharecter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Last Name']")));	 
	    wait.until(driver -> !lastNameFieldEditEmployeeWithSpecialCharecter.getAttribute("value").isEmpty());
	    System.out.println("DEBUG: Attribute value found = '" + lastNameFieldEditEmployeeWithSpecialCharecter.getAttribute("value") + "'");
	    lastNameFieldEditEmployeeWithSpecialCharecter.sendKeys(Keys.chord(Keys.CONTROL, "a"), "23#$");
	    WebElement EmployeeIdFieldEditEmployeeWithSpecialCharecter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Id']/following::input[@class='oxd-input oxd-input--active'][1]")));
	    wait.until(driver -> !EmployeeIdFieldEditEmployeeWithSpecialCharecter.getAttribute("value").isEmpty());
	    System.out.println("DEBUG: Attribute value found = '" + EmployeeIdFieldEditEmployeeWithSpecialCharecter.getAttribute("value") + "'");
	    EmployeeIdFieldEditEmployeeWithSpecialCharecter.sendKeys(Keys.chord(Keys.CONTROL, "a"), "#$$$%%%%");
	    driver.findElement(By.xpath("//h6[text()='Personal Details']")).click();
		wait.until(driver -> lastNameFieldEditEmployeeWithSpecialCharecter.getAttribute("value").equals("23#$"));
		wait.until(driver -> EmployeeIdFieldEditEmployeeWithSpecialCharecter.getAttribute("value").equals("#$$$%%%%"));	    
	    Thread.sleep(500);
	    // Step 7: Click the Save button
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()=' Save '])[1]"))).click();
	    // Step 8: Wait for the success message
	    WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'oxd-toast-content') and contains(.,'Success')]")));
	    System.out.println("Special Character Edit Message: " + successMessage.getText());
	    Assert.assertFalse(successMessage.isDisplayed(), "Bug: Form accepted invalid characters in name fields,Employee id and navigated to Personal Details.");
	}
	
	
	@Test(priority = 6)
	public void DeleteEmployee() {
	    // Step 1: Search for the employee
	    WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Employee Name']/following::input[@placeholder='Type for hints...'][1]")));
	    searchInput.sendKeys("Zara 23#$");
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[text()=' Search ']"))).click();
	    // Step 2: Wait for the employee to appear in results
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//div[text()='First (& Middle) Name']/following::div[normalize-space()='Zara'][1]")));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(" //i[contains(@class, 'bi-trash')]"))).click();
	    // Step 3: Click the "Yes, Delete" button
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Yes, Delete']"))).click();
	    // Step 4: Wait for success message to confirm deletion
	    WebElement deleteSuccessMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'oxd-toast-content') and contains(.,'Successfully')]")));
	    System.out.println("✅ Delete Message: " + deleteSuccessMessage.getText());
	    Assert.assertTrue(deleteSuccessMessage.isDisplayed(), "❌ Employee cannot be deleted.");
	}
	 @AfterMethod
	    public void tearDown(ITestResult result) {
	        if (result.getStatus() == ITestResult.FAILURE) {
	            takeScreenshot(result.getName()); // capture screenshot if test fails
	        }
	        tearDown();
	        
	    }
	
}




