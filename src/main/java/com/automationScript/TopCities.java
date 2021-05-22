package com.automationScript;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.ReadOrWriteExcelSheet;


public class TopCities extends BaseUI{
	
	//declaring the webelements of the topcities using pagefactory
		@FindBy(xpath="//a[@event='Nav:Interacted:Book diagnostic tests']")
		public static WebElement diagonisticselement;
		
		@FindBy(xpath="//*[@class='u-margint--standard o-f-color--primary']")
		public static List<WebElement> cities;
		
		By cities_xpath = By.xpath("//*[@class='u-margint--standard o-f-color--primary']");
	
	//Method to get the list of top cities
	public void ListTopCities() throws InterruptedException, IOException {
		
		try {
			
		String e = diagonisticselement.getAttribute("href");
		ArrayList<String> topCities = new ArrayList<String>();
		driver.navigate().to(e);
	
		WebDriverWait wait = new WebDriverWait(driver,30);
		//Webpage will waits until the top cities visibility.
		wait.until(ExpectedConditions.visibilityOfElementLocated(cities_xpath));
		
		System.out.println("*****************************************************************");
		System.out.println("*************************List Of Top Cities**********************");
		TimeUnit.SECONDS.sleep(1);
		for( WebElement product : cities){
			System.out.println(product.getText());
			
			topCities.add(product.getText());
		}
		
		ReadOrWriteExcelSheet.writeIntoExcel("TopCitiesDataSheet.xlsx",topCities);
		
		ResultScreenShot("TopCities.png");
		
		reportPass("The top cities has been displayed successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		
	}
	//Method to navigate to home page
	public void navigateToHomePage() throws InterruptedException {
		
		try {
			
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		
		reportPass("Navigated To Home Page Successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}
	
	//PageFactory for corporatewellness page.
	public static CorporateWellness nextpage1() {
		return PageFactory.initElements(driver, CorporateWellness.class);
	}

}
