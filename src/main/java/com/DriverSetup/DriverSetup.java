package com.DriverSetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSetup {
public static WebDriver driver;
	
	public static WebDriver getWebDriver(String browsername) {

		try {
			
			if(browsername.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--disable-notifications");
				driver = new ChromeDriver(option);
				System.out.println("Opening "+browsername +" browser.................");
				driver.manage().window().maximize(); // to enter maximized screen

			}else if(browsername.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				FirefoxOptions option = new FirefoxOptions();
				option.addArguments("--disable-notifications");
				driver = new FirefoxDriver(option);
				System.out.println("Opening "+browsername +" browser.................");
				driver.manage().window().maximize();

			}
				

		}catch (Exception e) {
			e.printStackTrace();
		}
		return driver;




	}
}
