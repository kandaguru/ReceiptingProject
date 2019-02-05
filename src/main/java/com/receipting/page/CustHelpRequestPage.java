package com.receipting.page;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.receipting.base.ReceiptingBase;

public class CustHelpRequestPage extends ReceiptingBase {

	WebDriverWait wait;

	public CustHelpRequestPage() throws IOException {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "rn_ProductCategoryInput_9_Product_Select_0")
	WebElement serviceDrpDwn;

	@FindBy(id = "rn_ProductCategoryInput_9_Product_Select_1")
	WebElement topicDrpDwn;

	@FindBy(id = "rn_ProductCategoryInput_9_Product_Select_2")
	WebElement subTopicDrpDwn;

	@FindBy(id = "rn_FormSubmit_63_Button")
	WebElement continueSubmitBtn;

	@FindBy(id = "rn_TextInput_13_Incident.CustomFields.c.event01_registration_desc")
	WebElement validPONumber;

	@FindBy(id = "rn_TextInput_15_Incident.CustomFields.c.event02_registration_desc")
	WebElement dateReceived;

	@FindBy(id = "rn_TextInput_61_Incident.Threads")
	WebElement requestDetails;

	@FindBy(xpath = "//*[@id='rn_PageTitle']/h1")
	WebElement thankyouMsg;

	public void selectService() {

		wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(serviceDrpDwn));
		Select serviceOptions = new Select(serviceDrpDwn);
		serviceOptions.selectByVisibleText("Shared Transaction Centre (STC)");

	}

	public void selectTopic() {

		wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(topicDrpDwn));
		Select serviceOptions = new Select(topicDrpDwn);
		serviceOptions.selectByVisibleText("Request for Goods Receipting");
		
	}

	public void selectSubTopic() {

		wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(subTopicDrpDwn));
		Select serviceOptions = new Select(subTopicDrpDwn);
		serviceOptions.selectByVisibleText("Entire PO Receipting");

		continueSubmitBtn.click();
	}

	public void enterPODetails(String requestType, String requestNumber) throws InterruptedException {

		validPONumber.clear();
		validPONumber.sendKeys(requestNumber);

		//String currentDate = TestUtil.getCurrentDate("ddmmyyyy");

		dateReceived.clear();
		dateReceived.sendKeys(prop.getProperty("custHelpDate"));

		requestDetails.clear();
		requestDetails.sendKeys(requestNumber + " is a " + requestType + " request");

		continueSubmitBtn.click();

	}

	public void closeCustHelp() throws InterruptedException {

		Thread.sleep(4000L);
		driver.close();

	}

}
