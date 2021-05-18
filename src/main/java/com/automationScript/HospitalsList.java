package com.automationScript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.ReadOrWriteExcelSheet;
import com.utils.CaptureScreenshot;

public class  HospitalsList extends BaseUI {
	
	//WebElements of the HospitalList Page
	
	@FindBy(xpath ="//input[@placeholder='Search location']")
	public static WebElement e;

	@FindBy(xpath="//div[text()='Bangalore']")
	public static WebElement clickBangloreElement;
	
	@FindBy(xpath ="//input[@placeholder='Search doctors, clinics, hospitals, etc.']")
	public static WebElement hospitalelement;
	
	@FindBy(xpath ="//div[text()='Hospital']")
	public static WebElement clickhospitalelement;
	
	@FindBy(xpath ="//label[@for='Open-24X70']//div")
	public static WebElement element24x7;
	
	@FindBy(xpath ="//span[@data-qa-id='all_filters']//span")
	public static WebElement filterelement;
	
	@FindBy(xpath ="//div[@data-qa-id='Has_Parking_checkbox']")
	public static WebElement hasparkingelement;
	
	@FindBy(xpath ="//*[text()='Next']")
	public static WebElement NextPageButton;
	
	@FindBy(xpath ="//div[@data-qa-id='hospital_card']")
	public static List<WebElement> ListOfHospital;
	
	By clickBangloreElement_xpath = By.xpath("//div[text()='Bangalore']");
	By clickhospitalelement_xpath = By.xpath("//div[text()='Hospital']");
	
	//Method to search hospitals based on location
	public void Enter_location() throws InterruptedException {
		try {
		e.clear();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		enterText(e, "Bangalore");
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		//Webpage will waits until the bangalore text visible.
		wait.until(ExpectedConditions.visibilityOfElementLocated(clickBangloreElement_xpath));
		
		clickElement(clickBangloreElement);
		//TimeUnit.SECONDS.sleep(3);
		
		
		enterText(hospitalelement,"Hospital");
		//Webpage will waits until the hospital text visible.
		wait.until(ExpectedConditions.visibilityOfElementLocated(clickhospitalelement_xpath));
		
		clickElement(clickhospitalelement);
		TimeUnit.SECONDS.sleep(3);
		reportPass("The Location has been entered successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}
	//Method to get hospitals that are open 24/7
	public void HospitalsOpen24x7() throws InterruptedException {
		
		try {
			
		clickElement(element24x7);
		TimeUnit.SECONDS.sleep(1);
		reportPass("24x7 filter has been applied successfully");
		
		}catch(Exception e) {
			
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		
	}
	//Method to get hospital that has parking facility
	public void applyHasparkingFilter() throws InterruptedException {
		
		try {
			
		clickElement(filterelement);
		TimeUnit.SECONDS.sleep(1);
		
		clickElement(hasparkingelement);
		TimeUnit.SECONDS.sleep(1);
		
		reportPass("Has parking filter has been applied successfully");
		
		}catch(Exception e) {
			
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}
	//Method to get hospitals  that has parking facility greater than 3.5 and took the screenshot of each hospital.
	public void listHospitals() throws InterruptedException {
		
		try {
		
			
			//convert the driver type to javascriptexecutor type.
		JavascriptExecutor js = (JavascriptExecutor) driver;
		 TimeUnit.SECONDS.sleep(3);  
		  for(int i=0;i<16;i++) { 
				 js.executeScript("arguments[0].scrollIntoView();",NextPageButton);
				 TimeUnit.SECONDS.sleep(1);
				 js.executeScript("window.scrollBy(0,-100)");
				 TimeUnit.SECONDS.sleep(1);
		  }
		  
		  System.out.println("*****************************************************************");
		  System.out.println("*************************List Of Hospitals**********************");
		  
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  int i=2;
		  ArrayList<String> filteredHospitals = new ArrayList<String>();
		  for(WebElement hos:ListOfHospital)
		  {
			 WebElement rating,hospital;
			 int  rateCount=driver.findElements(By.xpath("//*[@id=\"container\"]/div[3]/div/div[2]/div[1]/div/div[3]/div["+i+"]/div/div[1]/div[2]/div[1]/div")).size();
			 
			  if(rateCount==4) {
				   rating=driver.findElement(By.xpath("//*[@id=\"container\"]/div[3]/div/div[2]/div[1]/div/div[3]/div["+i+"]/div/div[1]/div[2]/div/div[1]/div/div/span[1]"));
				   hospital=driver.findElement(By.xpath("//*[@id=\"container\"]/div[3]/div/div[2]/div[1]/div/div[3]/div["+i+"]/div/div[1]/div[1]/div/div[2]/div/a/h2"));
				   
				   float rat=Float.parseFloat(((WebElement) rating).getText());
				   if(rat > 3.5 )
				   {String hospitalPrint=hospital.getText();
					   System.out.println(hospitalPrint);
					   filteredHospitals.add(hospitalPrint);
					   CaptureScreenshot.toTakeScreenshot(hos,hospitalPrint);
					   
				   }  
			  }
			  i++;
		  }
		  ReadOrWriteExcelSheet.writeIntoExcel("HospitalListSheet.xlsx", filteredHospitals);
		  
		  reportPass("List of Hospitals has been displayed");
		  
		}catch (Exception e) {
			
			e.printStackTrace();
			reportFail(e.getMessage());
			
		}
		
		
	}
//Method to navigate to home page
	public void navigateToHomePage() throws InterruptedException {
		
		try {
			
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		driver.navigate().back();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		
		reportPass("Successfully navigated to the Home Page");
		}catch(Exception e) {
			
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}
	//PageFactory of the next page(TopCities class)
	public static TopCities nextpage() {
		return PageFactory.initElements(driver, TopCities.class);
	}
}
