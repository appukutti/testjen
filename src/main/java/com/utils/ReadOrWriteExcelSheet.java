package com.utils;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automationScript.BaseUI;


public class ReadOrWriteExcelSheet extends BaseUI{
	//Method to write into excel 
	public static void writeIntoExcel(String fileName,ArrayList<String> list) throws IOException {
		File file = new File(System.getProperty("user.dir")+"\\ExcelData\\"+fileName);
		FileInputStream inputstream = new FileInputStream(file);
		Workbook workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if(fileExtensionName.equals(".xlsx")){

		    workbook = new XSSFWorkbook(inputstream);

		  }
		  else if(fileExtensionName.equals(".xls")){
			  workbook = new HSSFWorkbook(inputstream);
		  }

		  Sheet datasheet = workbook.getSheetAt(0);
		  if(fileName.equals("DataExcelSheet.xlsx")) {
			  int i = 1;
			  for(String message : list) {
				  datasheet.getRow(i).createCell(4).setCellValue(message);
			  }
			  i++;
		  }
		  else if(fileName.equals("TopCitiesDataSheet.xlsx")) {
			  Iterator<Row> rI = datasheet.iterator();
			  //loop to clear the data in excel
			  while(rI.hasNext()) {
				  rI.next();rI.remove();
			  }
			  int i = 0;
			  Row row;	
			  for( String name : list){
				  row = datasheet.createRow(i);
				  row.createCell(0).setCellValue(name);
				  i++;
			  }
		  }else if(fileName.equals("HospitalListSheet.xlsx")) {
			  Iterator<Row> rI = datasheet.iterator();
			  //loop to clear the data in excel
			  while(rI.hasNext()) {
				  rI.next();rI.remove();
			  }
			  int i = 0;
			  Row row;	
			  for( String name : list){
				  row = datasheet.createRow(i);
				  row.createCell(0).setCellValue(name);
				  i++;
			  }
		  }
		
		  inputstream.close();
		  FileOutputStream outputStream = new FileOutputStream(file);
		  workbook.write(outputStream);
		outputStream.close();
		  
		
		}
	//Method to read data from excel
	public static String ReadFromExcel(String fileName,String className,int row) throws InterruptedException, IOException, AWTException 
	{
		
		File file = new File(System.getProperty("user.dir")+"\\ExcelData\\"+fileName);

		FileInputStream inputstream = new FileInputStream(file);
		Workbook workbook = null;
        
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if(fileExtensionName.equals(".xlsx")){

		    workbook = new XSSFWorkbook(inputstream);

		  }
		  else if(fileExtensionName.equals(".xls")){
			  workbook = new HSSFWorkbook(inputstream);
		  }

		  Sheet datasheet = workbook.getSheetAt(0);
		  String result = "";
		  if(className.equals("BaseUI")) {
			  
			  result = datasheet.getRow(row).getCell(0).getStringCellValue();
			  
		  }
		  else if(className.equals("CorporateWellness")) {
			  	result = result + datasheet.getRow(row).getCell(0).getStringCellValue()+" ";
				TimeUnit.SECONDS.sleep(1);
				result = result + datasheet.getRow(row).getCell(1).getStringCellValue()+" ";
				TimeUnit.SECONDS.sleep(1);
				result = result + datasheet.getRow(row).getCell(2).getStringCellValue()+" ";
				TimeUnit.SECONDS.sleep(1);
				DataFormatter formatter = new DataFormatter();
				result = result + formatter.formatCellValue(datasheet.getRow(row).getCell(3))+" ";
				TimeUnit.SECONDS.sleep(1);
		  }
		  return result;
	}
}
