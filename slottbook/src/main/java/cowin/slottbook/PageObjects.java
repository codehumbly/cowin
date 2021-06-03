package cowin.slottbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PageObjects extends BaseClass {

	public PageObjects(WebDriver session) {
		driver = session;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = "//input[@formcontrolname='pincode']")
	private static WebElement pinCode;

	@FindBy(how = How.XPATH, using = "//button[@class='pin-search-btn']")
	private static WebElement search;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'vaccine-box') and not(contains(@theme,'light'))][.//a[not(contains(@tooltip,'Fully Booked'))]]")
	private static List<WebElement> availability;

	@FindBy(how = How.XPATH, using = "//div[text()='Search by District']")
	private static WebElement district;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Search')]")
	private static WebElement searchButton;

	public static JavascriptExecutor executor = (JavascriptExecutor) driver;

	public void searchWithPincode() throws InterruptedException {
		pinCode.sendKeys("382170");
		search.click();
		System.out.println(availability.size());
	}

	public void clickOnSearchByDistrict() {
		district.click();
	}

	public void selectFromStateDropdown(String name) {
		WebElement dropdown = driver.findElement(By.xpath("//mat-select[@formcontrolname='state_id']"));
		dropdown.click();
		WebElement state = driver.findElement(By.xpath("//span[contains(text(),'" + name + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", state);
		state.click();
	}

	public void selectFromDistrictDropdown(String name) throws InterruptedException {
		WebElement dropdown = driver.findElement(By.xpath("//mat-select[@formcontrolname='district_id']"));
		dropdown.click();
		Thread.sleep(1000);
		WebElement dist = driver
				.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),'" + name + "')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dist);
		dist.click();
	}

	public void clickOnSearch() {
		searchButton.click();
	}

	public void searchByDistrict() throws InterruptedException {
		String stateName = System.getProperty("state");
		String districtName = System.getProperty("dist");

		this.clickOnSearchByDistrict();
		Thread.sleep(2000);
		this.selectFromStateDropdown(stateName);
		Thread.sleep(1000);
		this.selectFromDistrictDropdown(districtName);
		this.clickOnSearch();
	}

	public void applyFilters() {
		WebElement age = driver.findElement(By.xpath(
				"//div[@class='mobile-hide']//label[contains(text(),'Age " + System.getProperty("age") + "+')]"));
		age.click();
	}

	public boolean getAvailabilityInfo() throws InterruptedException {
		Thread.sleep(2000);
		List<WebElement> dose1 = driver.findElements(By.xpath("//div[contains(@class,'dosetotal')]/span[@title='"
				+ System.getProperty("doseCategory") + "' and not(contains(text(),'0'))]"));
		Set<String> dose1Location = new HashSet<String>();
		List<String> dose1Count = new ArrayList<String>();
		Map<String, String> availability = new HashMap<String, String>();
		if (dose1.size() > 0) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dose1.get(0));
			Thread.sleep(500);
		}

		for (WebElement each : dose1) {
			String count = each.getAttribute("outerText");
			dose1Count.add(count);
			System.out.println(count);
			if (Integer.parseInt(count.substring(count.indexOf("\n") + 1, count.length()).trim()) > Integer
					.parseInt(System.getProperty("doseThreshould"))) {
				String location = each
						.findElement(By.xpath(
								"./ancestor::div[contains(@class,'slot-available-main')]/preceding-sibling::div"))
						.getAttribute("outerText").replaceAll("\\r\\n|\\r|\\n", ",");
				dose1Location.add(location);
				if (availability.keySet().contains(location)) {
					availability.put(location, availability.get(location).toString() + " | " + count);
				} else {
					availability.put(location, count);
				}
			}

		}
		if (availability.size() > 0) {
			return true;
		}
		return false;
	}

	public void takeSnapShot() throws Exception {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + "\\homePageScreenshot.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}