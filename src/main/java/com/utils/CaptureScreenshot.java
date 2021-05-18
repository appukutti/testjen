package com.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;


public class CaptureScreenshot {
	
	//Method is  To Take  Screenshot of hospitals
	public static void toTakeScreenshot(WebElement element,String screenshotname) throws IOException {
		
		TakesScreenshot ts=(TakesScreenshot)element;
		File src=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("./ResultScreenShots/HospitalList/"+screenshotname+".png"));

	}
	
}
