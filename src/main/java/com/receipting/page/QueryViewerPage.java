package com.receipting.page;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.receipting.base.ReceiptingBase;

public class QueryViewerPage extends ReceiptingBase {

	WebDriverWait wait;

	public QueryViewerPage() throws IOException {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "QRY_VIEWERS_WRK_QRYSEARCHTEXT254")
	WebElement queryTxtBox;

	@FindBy(id = "QRY_VIEWERS_WRK_QRYSEARCHBTN")
	WebElement searchBtn;

	@FindBy(linkText = "Excel")
	WebElement excelLink;

	public void downloadSciquetFile() throws InterruptedException {

		wait = new WebDriverWait(driver, 10);

		driver.switchTo().frame("ptifrmtgtframe");

		queryTxtBox.clear();
		queryTxtBox.sendKeys("UOA_OPEN_PO_RECEIPT_ACTIVE_SC");

		searchBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(excelLink)).click();

		Thread.sleep(40000L);

	}

}
