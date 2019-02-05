package com.receipting.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.receipting.base.ReceiptingBase;

public class TestUtil extends ReceiptingBase {

	public TestUtil() throws IOException {
		super();

	}

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT_TIMEOUT = 10;

	public static ArrayList<String> sciquestRequest;

	public static int giveFrameCount(By byloc) {

		List<WebElement> frames = driver.findElements(By.tagName("iframe"));

		int a = frames.size();

		System.out.println("Total number of Frames==>" + a);

		int frameNum = -1;

		for (int i = 0; i < a; i++) {

			driver.switchTo().frame(i);

			List<WebElement> txt = driver.findElements(byloc);

			if (txt.size() > 0) {

				frameNum = i;
				System.out.println("Frame Number==>" + frameNum);
				break;
			}

			driver.switchTo().defaultContent();

		}

		driver.switchTo().defaultContent();
		return frameNum;

	}

	public static String getCurrentDate(String format) {
		String timeStamp = null;
		if (format.equalsIgnoreCase("mmddyyyy")) {
			timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
		} else if (format.equalsIgnoreCase("ddmmyyyy")) {
			timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		}
		return timeStamp;

	}

	public static void openNewTab() throws InterruptedException {

		String a = "window.open('about:blank','_blank');";
		((JavascriptExecutor) driver).executeScript(a);
	}

	public static void switchToWindow(String WindowNane) {

		ArrayList<String> handles = new ArrayList<String>(driver.getWindowHandles());

		if (WindowNane.equalsIgnoreCase("PSF"))
			driver.switchTo().window(handles.get(0));

		else
			driver.switchTo().window(handles.get(1));
	}

	public static String getTwoFA() {

		String otpKeyStr = "UKMKVCOZOELMD5XZO3V72ZHW2CHPECDU";

		Totp totp = new Totp(otpKeyStr);
		String twoFactorCode = totp.now(); // <- got 2FA coed at this time!

		System.out.println(twoFactorCode);

		return twoFactorCode.trim();

	}

	public static boolean isElementPresent(WebDriver driver, By by) {

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
		}
	}

	public static ArrayList<String> giveSciquestRequest() throws IOException {

		sciquestRequest = new ArrayList<String>();
		String stringCellContent;
		File file = new File("C:\\Users\\kbas663\\RPA-AUTO\\ReceiptingProject\\" + uuid);

		// + uuid);
		File[] files = file.listFiles();

		System.out.println("Number of files====>" + files.length);

		for (int i = 0; i < files.length; i++) {

			System.out.println(files[0]);
		}

		FileInputStream fis = new FileInputStream(files[0]);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("sheet1");
		wb.close();

		int lastRowNum = sheet.getLastRowNum();
		System.out.println("Total number of Rows in TestData File=====>" + lastRowNum);

		int lastCellNum = sheet.getRow(1).getLastCellNum();
		System.out.println(lastCellNum);

		for (int i = 2; i <= lastRowNum; i++) {

			Iterator<Cell> value = sheet.getRow(i).cellIterator();
			while (value.hasNext()) {
				Cell cellcontent = value.next();
				if (cellcontent.getCellTypeEnum() == CellType.STRING) {

					stringCellContent = cellcontent.getStringCellValue();
					sciquestRequest.add(stringCellContent);
				} else {

					stringCellContent = NumberToTextConverter.toText(cellcontent.getNumericCellValue());
					sciquestRequest.add(stringCellContent);
				}

			}
		}
		return sciquestRequest;

	}

}
