package cowin.slottbook;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import cowin.mail.SendMail;

public class Main extends BaseClass {
	@Test
	public void sendReminder() throws Exception {
		this.initialize();
		PageObjects homePage = new PageObjects(driver);
		SendMail send = new SendMail();
		homePage.searchByDistrict();
		homePage.applyFilters();
		if (homePage.getAvailabilityInfo()) {
			homePage.takeSnapShot();
			//send.sendBeep();
		}
	}

	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
