package com.testScript;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.automationScript.BaseUI;
import com.automationScript.CorporateWellness;
import com.automationScript.HospitalsList;
import com.automationScript.TopCities;
import com.utils.ExtentReportManager;

public class TestClass {
	
	public static ExtentReports report;
	public static ExtentTest logger;
	
	public void open() throws Exception 
	{
	  	BaseUI ba=new BaseUI();
		ba.openBrowserAndNavigateToPracto();
	
	}
	//Test to get hospital list
	@Test(priority = 1 , groups= {"smoke"})
	public void hospitalList() throws Exception {
			
			  open();
		
			  report = ExtentReportManager.getReportInstance();
		      logger=report.createTest("HospitalListPage");
			  
			  HospitalsList hospitallist = BaseUI.hospitals();
			  
			  logger.log(Status.INFO, "Enter the Hospital in field");
			  hospitallist.Enter_location();
			  
			  logger.log(Status.INFO, "Filter 24x7 Hospitals");
			  hospitallist.HospitalsOpen24x7();
			  
			  logger.log(Status.INFO, "Filter parking facility Hospitals");
			  hospitallist.applyHasparkingFilter();
			  
			  logger.log(Status.INFO, "Enter the List of Hospital");
			  hospitallist.listHospitals();
			  
			  //hospitallist.ResultScreenShot();
			  
			  logger.log(Status.INFO, "Navigate to Home Page");
			  hospitallist.navigateToHomePage();
			  
			  //logger.log(Status.PASS, "Hospital List Test Executed Successfully");
			  
	}
	//test to get top cities
	@Test(priority = 2 , dependsOnMethods = {"hospitalList"},groups = {"smoke"})
	public void diagonosticsPage() throws InterruptedException, IOException {
		
		logger=report.createTest("DiagonosticsPage");
		TopCities topcity = HospitalsList.nextpage();
		
		logger.log(Status.INFO, "Get List of Top Cities");
		topcity.ListTopCities();
		
		//topcity.ResultScreenShot();
		logger.log(Status.INFO, "Navigate To HomePage");
		topcity.navigateToHomePage();
		
		//logger.log(Status.PASS, "Diagonostics Page Test Executed Successfully");
		
	}
	//test to check invalid credentials
	@Test(priority = 3 , dependsOnMethods = {"diagonosticsPage"} , groups = {"regression"})
	public void corporateWellnessPage() throws InterruptedException, IOException {
		
		logger=report.createTest("CorporateWellnessPage");
		CorporateWellness corporatepage = TopCities.nextpage1();
		
		logger.log(Status.INFO, "Click Provider Button");
		corporatepage.clickProvider();
		
		logger.log(Status.INFO, "Click CorporateWellness Button");
		corporatepage.clickCorporateWellness();
		
		logger.log(Status.INFO, "Enter data to the fields");
		corporatepage.enterData();
		
		close_browser();
	
	}
	// To close the extent report
	@AfterTest
	public void endReport() {
		report.flush();
	}
	
	//Close the browser
	public void close_browser() {
		BaseUI bs = new BaseUI();
		bs.closeBrowser();
	}
	
}
