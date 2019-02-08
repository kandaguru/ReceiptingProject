package com.receipting.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class ReceiptingBase {

	public static Logger log = LogManager.getLogger(ReceiptingBase.class.getName());

	public static String oscRequestNumberValue;
	public static Set<String> oscRequestNumbers= new HashSet<String>();

	public static WebDriver driver = null;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static File folder;
	public static String uuid;
	public static WebDriverEventListener e_listener;

	public ReceiptingBase() throws IOException {

		String path = System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(
				path + "//src//main//java//com//receipting//properties//data.properties");
		prop = new Properties();
		prop.load(fis);

	}

	public static void intialize() throws IOException {

		if (driver == null) {

			if (prop.getProperty("browser").trim().equalsIgnoreCase("chrome")) {

				String path = System.getProperty("user.dir");

				System.setProperty("webdriver.chrome.driver",
						path + "\\src\\main\\java\\com\\receipting\\drivers\\chromedriver.exe");

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();

				createFile();

				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("download.default_directory", folder.getAbsolutePath());

				options.setExperimentalOption("prefs", prefs);

				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

				options.merge(capabilities);

				driver = new ChromeDriver(options);

			}

			else if (prop.getProperty("browser").equalsIgnoreCase("FF")) {

				String path = System.getProperty("user.dir");

				System.setProperty("webdriver.gecko.driver",
						path + "\\src\\main\\java\\com\\receipting\\drivers\\geckodriver.exe");

				driver = new FirefoxDriver();

			}

			else if (prop.getProperty("browser").trim().equalsIgnoreCase("IE")) {

				String path = System.getProperty("user.dir");

				System.setProperty("webdriver.ie.driver",
						path + "\\src\\main\\java\\com\\receipting\\drivers\\IEDriverServer.exe");

				driver = new InternetExplorerDriver();

			}

		}

//		e_driver = new EventFiringWebDriver(driver);
//		e_listener = new WebEventListener();
//		e_driver.register(e_listener);
//
//		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		/*
		 * Time Rendering error message if the below code is added
		 */

//		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT,
//		TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT_TIMEOUT,
//		TimeUnit.SECONDS);

	}

	public static String createFile() {
		uuid = UUID.randomUUID().toString();
		folder = new File(uuid);
		folder.mkdir();

		return uuid;
	}

	public void deleteFile() {

		for (File file : folder.listFiles()) {
			file.delete();
		}
		folder.delete();
	}

	public void openPsf() {

		driver.get(prop.getProperty("PSFurl").trim());
	}

	public void openCustHelp() {

		driver.get(prop.getProperty("CustHelpurl").trim());
	}

	public void openQueryViewer() {

		driver.get(prop.getProperty("PSFQueryViewerUrl"));
	}

}

//  \d{6}[-]\d{6}
