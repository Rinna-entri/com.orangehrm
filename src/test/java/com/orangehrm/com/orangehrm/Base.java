package com.orangehrm.com.orangehrm;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Base {
	
	
	public WebDriver driver;
	public WebDriverWait wait;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	 @BeforeSuite
	    public void startReport() {
	        ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");
	        extent = new ExtentReports();
	        extent.attachReporter(reporter);
	        extent.setSystemInfo("Project", "OrangeHRM Automation");
	        extent.setSystemInfo("Tester", "Rinna Maria Baby");
	    }
	@BeforeMethod
    public void setUp(Method method)  {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        // Create ExtentTest for each test method
        test = extent.createTest(method.getName());

	}
	public void login(String username, String password) {
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Username']"))).sendKeys(username);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Password']"))).sendKeys(password);
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
        test.info("Login attempted with Username: " + username + "Password :" +password);

	}
	
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
            driver.quit();
        }
	}
	

    @AfterSuite
    public void endReport() {
        extent.flush();
    }

	  public void takeScreenshot(String testName) {
	        if (driver instanceof TakesScreenshot) {
	            TakesScreenshot ts = (TakesScreenshot) driver;
	            File src = ts.getScreenshotAs(OutputType.FILE);
	            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	            
	            
	         // ✅ Create folder if it doesn't exist
	            File screenshotsDir = new File("screenshots");
	            if (!screenshotsDir.exists())
	            {
	                screenshotsDir.mkdir();
	            }

	            File dest = new File(screenshotsDir, testName + "_" + timestamp + ".png");

	            try {
	                FileHandler.copy(src, dest);
	                System.out.println("📸 Screenshot saved: " + dest.getAbsolutePath());
	            } catch (IOException e) {
	                System.out.println("❌ Failed to save screenshot: " + e.getMessage());
	            }
	        }
	    }

}
