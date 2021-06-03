package cowin.slottbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseClass {

	public static WebDriver driver;
	public static String url = "https://www.cowin.gov.in/home";
	public static String email = System.getProperty("email");

	void initialize() {
		String sProjectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", sProjectPath + "\\Library\\chromedriver.exe");
//		System.setProperty("state","Jammu And Kashmir");
//		System.setProperty("dist", "Jammu");
//		System.setProperty("age", "18");
//		System.setProperty("email", "chiranjit.halder@gmail.com");
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.navigate().to(url);
	}

	void initialize(String urlVal) {
		String sProjectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", sProjectPath + "\\Library\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.navigate().to(urlVal);
	}



}
