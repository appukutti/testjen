package com.automationScript;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.ReadOrWriteExcelSheet;

public class CorporateWellness extends BaseUI{
	
	//declaring the webelements using pagefactory
	
		@FindBy(xpath="//*[@id='root']/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span[1]")
		public static WebElement providerelement;
		
		@FindBy(xpath="//*[@id='root']/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/div/div[4]/a")
		public static WebElement corporatewellnesselement;
		
		@FindBy(id ="name")
		public static WebElement nameelement;
		
		@FindBy(id ="organization_name")
		public static WebElement organizationelement;
		
		@FindBy(id ="official_email_id")
		public static WebElement mailelement;
		
		@FindBy(id ="official_phone_no")
		public static WebElement phoneelement;
		
		@FindBy(id ="button-style")
		public static WebElement button;
		
		@FindBy(id="organization_size")
		public static WebElement organization;
		
		By providerelement_xpath = By.xpath("//*[@id='root']/div/div/div[1]/div[1]/div[2]/div/div[3]/div[1]/span[1]");
	
		//Click the provider in the home page of practo site.
	public void clickProvider() throws InterruptedException {
		
		try {
			
		WebDriverWait wait = new WebDriverWait(driver,30);
		//Webpage will waits until the camera icon visibility.
		wait.until(ExpectedConditions.visibilityOfElementLocated(providerelement_xpath));
		clickElement(providerelement);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		reportPass("Successfully clicked the Click Provider");
		}catch(Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		
	}
	
	//Click the corporatewellness in the home page.
	public void clickCorporateWellness() throws InterruptedException {
		
		try {
		
		
		clickElement(corporatewellnesselement);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		reportPass("Navigated to CorporateWellness Link Successfully");
		}catch(Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		
	}
	
	//Enter the data into fields of the corporatewellness page.
	public void enterData() throws InterruptedException, IOException {
		
		try {
		
		Set<String> windowids = driver.getWindowHandles();
		Iterator<String> itr = windowids.iterator();
		String mainpageId = itr.next();
		String CorporatePage = itr.next();
		
		driver.switchTo().window(CorporatePage);
		for(int i=1;i<=2;i++) {
			String values = ReadOrWriteExcelSheet.ReadFromExcel("DataExcelSheet.xlsx","CorporateWellness",i);
			String credentials[] = values.split(" ");
			TimeUnit.SECONDS.sleep(1);
			nameelement.clear();
			enterText(nameelement,credentials[0]);
			TimeUnit.SECONDS.sleep(1);
			organizationelement.clear();
			enterText(organizationelement,credentials[1]);
			TimeUnit.SECONDS.sleep(1);
			mailelement.clear();
			enterText(mailelement,credentials[2]);
			TimeUnit.SECONDS.sleep(1);
			phoneelement.clear();
			enterText(phoneelement,credentials[3]);
			TimeUnit.SECONDS.sleep(1);
			Select org = new  Select(organization);
			org.selectByVisibleText("501-1000");
			TimeUnit.SECONDS.sleep(1);
			clickElement(button);
			TimeUnit.MINUTES.sleep(2);
			ArrayList<String> message = new ArrayList<String>();
			if(isAlertPresent()) {
				handleAlert();
				message.add("Pass");
				ReadOrWriteExcelSheet.writeIntoExcel("DataExcelSheet.xlsx",message);
			}
			else {
				message.add("Fail");
				ReadOrWriteExcelSheet.writeIntoExcel("DataExcelSheet.xlsx",message);
			}
			
			message.clear();
			ResultScreenShot("CorporateWellness" +i+".png");
		 reportPass("Entered the data successfully");
		}
		}
		catch(Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		
	}
	
	
}
