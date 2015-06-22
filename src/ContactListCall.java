import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;


public class ContactListCall {
	
	public AndroidDriver driver;
	public String desiredContact = "Tish"; // contact name that we want to call

	@BeforeMethod
	public void setUp() throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "5.0");
		cap.setCapability("deviceName", "Android");
		cap.setCapability("appPackage", "com.android.contacts");
		cap.setCapability("appActivity", "com.android.contacts.activities.PeopleActivity");
		
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("decor_content_parent")));
	}
	
	@Test
	public void callToContact() throws InterruptedException {
				
		String temp = new String();
		boolean contactNotFound = true;
		
		while (true) {
			
			List<WebElement> contacts = driver.findElements(By.id("cliv_name_textview"));
			
			String currentContact = contacts.get(0).getText();
			
			if (currentContact.equals(temp)) {
				break;
			}
			
			Reporter.log(currentContact, true);
			
			Point point = contacts.get(0).getLocation();
			
			int endX = point.getX() + contacts.get(0).getSize().width;
			
			//call desiredContact 
			if (currentContact.equals(desiredContact)) {
				Reporter.log("Calling " + currentContact, true);
				driver.swipe(point.getX(), point.getY(), endX, point.getY(), 1000);
				contactNotFound = false;
				Thread.sleep(7000);
				break;
			}
					
			int x = 10;
			int y = point.getY() + contacts.get(0).getSize().height + 59;
			//System.out.println(y);
			
			driver.swipe(x, y, x, 435, 1000);
			
			temp = currentContact;
			
		} // end of while
		
		if (contactNotFound) {
			
			List<WebElement> contacts = driver.findElements(By.id("cliv_name_textview"));
			
			for (int i = 1; i < contacts.size(); i++) {
				
				String currentContact = contacts.get(i).getText();
				
				Reporter.log(currentContact, true);
				
				Point point = contacts.get(i).getLocation();
				
				int endX = point.getX() + contacts.get(i).getSize().width;
				
				//call desiredContact 
				if (currentContact.equals(desiredContact)) {
					Reporter.log("Calling " + currentContact, true);
					driver.swipe(point.getX(), point.getY(), endX, point.getY(), 1000);
					Thread.sleep(7000);
					break;
				}
				
			}
			
		}
		
	} // end of @Test
		
	@AfterMethod
	public void quit() {
		driver.quit();
	}
		
}		
		



