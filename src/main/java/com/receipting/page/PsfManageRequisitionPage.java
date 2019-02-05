package com.receipting.page;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.receipting.base.ReceiptingBase;
import com.receipting.util.TestUtil;

public class PsfManageRequisitionPage extends ReceiptingBase {

	WebDriverWait wait;
	Select select;
	String requestNumber1 = null;
	boolean loadingStatusReturn;

	public PsfManageRequisitionPage() throws IOException {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "PV_REQ_HDR_WRK_PV_CLEAR_PB")
	WebElement clearBtn;

	String clearBtnLocatorValue = "PV_REQ_HDR_WRK_PV_CLEAR_PB";

	@FindBy(id = "PV_SORT_TABLE_PV_FILTERBY")
	WebElement requestStateDrpDwn;

	@FindBy(id = "PV_REQ_HDR_WRK_FROM_DATE")
	WebElement dateFrom;

	@FindBy(id = "PV_REQ_HDR_WRK_THRU_DATE")
	WebElement dateTo;

	@FindBy(id = "PV_REQ_HDR_WRK_PV_SRCH_PB")
	WebElement searchBtn;

	@FindBy(xpath = "//img[@id='processing']")
	WebElement loadingIcon;

	@FindBy(id = "ptModTable_0")
	WebElement alertMsgbox;

	@FindBy(id = "#ICOK")
	WebElement alertMsgOkBtn;

	@FindBy(xpath = "//img[@name='PV_REQSTAT_WRK_PV_EXPAND_SECT$IMG$0']")
	WebElement expandBtn;

	@FindBy(xpath = "//img[contains(@alt,'Purchase Orders')]")
	WebElement purchaseOrderLinkText;

	@FindBy(id = "PV_REQ_CYCLE_VW_BUSINESS_UNIT")
	WebElement businessUnitPlaceHolder;

	@FindBy(id = "PV_PO_REQV_VW2_PO_ID$0")
	WebElement poPlaceHolder;

	@FindBy(linkText = "Return to Manage Requisitions")
	WebElement returnToManageRequisitionLink;

	@FindBy(id = "PV_SORT_TABLE_REQ_NAME")
	WebElement requisitionName;

	@FindBy(linkText = "Inquire Receipts")
	WebElement inquireReceipts;

	@FindBy(id = "ICTAB_1_235")
	WebElement poDetailsTab;

	@FindBy(id = "PV_RECV_PO_REQ_BUSINESS_UNIT_PO$113$$0")
	WebElement buReceiveItemValue;

	@FindBy(id = "DETAILS_PB$0")
	WebElement poReceiveItemsValue;

	String expandBtnLocator1 = "//img[@name='PV_REQSTAT_WRK_PV_EXPAND_SECT$IMG$";
	String expandBtnLocator2 = "']";

	public void clickClearBtn() throws InterruptedException {

		Thread.sleep(1000L);
		int framenum = TestUtil.giveFrameCount(By.id(clearBtnLocatorValue));
		driver.switchTo().frame(framenum);
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(clearBtn)).click();

	}

	public void chooseRequestState(String requestState) throws InterruptedException {

		Thread.sleep(1000L);
		int attempt = 0;
		while (attempt <= 2) {
			try {

				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.visibilityOf(requestStateDrpDwn));
				select = new Select(requestStateDrpDwn);
				select.selectByVisibleText(requestState);

				System.out.println("choosereqstate====>" + attempt);
				break;

			} catch (StaleElementReferenceException e) {

			}
			attempt++;
		}
	}

	public void enterDates() throws InterruptedException {

		int attempt = 0;
		while (attempt <= 2) {
			try {
				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.visibilityOf(dateFrom)).clear();
				dateFrom.sendKeys(prop.getProperty("startDate"));

				String toDate = TestUtil.getCurrentDate("mmddyyyy");
				wait.until(ExpectedConditions.visibilityOf(dateTo)).clear();
				dateTo.sendKeys(toDate);
				System.out.println("EnterDate====>" + attempt);
				break;

			} catch (StaleElementReferenceException e) {

			}
			attempt++;
		}

	}

	public void enterRequisitionName(String name) {

		int attempt = 0;
		while (attempt <= 2) {
			try {

				requisitionName.clear();
				requisitionName.sendKeys(name);
				break;

			} catch (StaleElementReferenceException e) {

			}
			attempt++;
		}

	}

	public void clickSearch() {

		int attempt = 0;
		while (attempt <= 2) {
			try {

				wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
				System.out.println("ClickSearch====>" + attempt);
				break;

			} catch (StaleElementReferenceException e) {

			}
			attempt++;
		}
	}

	public boolean waitForResultsLoadTime() {

		wait = new WebDriverWait(driver, 600);
		boolean visibilityStatus = wait.until(ExpectedConditions.invisibilityOf(loadingIcon));
		return visibilityStatus;
	}

	public void handleAlert() throws InterruptedException {
		Thread.sleep(1000L);
		driver.switchTo().defaultContent();

		try {

			wait = new WebDriverWait(driver, 3);
			int presence = wait.until(ExpectedConditions
					.visibilityOfAllElements(driver.findElements(By.xpath("//div[contains(@id,'ptModTable_')]"))))
					.size();
			if (presence > 0) {

				alertMsgOkBtn.click();
			}
		} catch (Exception e) {

		}

	}

	public String validateAndRetrunRequestNumber() throws InterruptedException, IOException {

		wait = new WebDriverWait(driver, 10);

		int mainFrameNum = TestUtil.giveFrameCount(By.xpath("//img[@name='PV_REQSTAT_WRK_PV_EXPAND_SECT$IMG$0']"));
		driver.switchTo().frame(mainFrameNum);

		int value = checkTravelSciquestRequest(); // checking if the fetched request is a travel related or sciquest one

		System.out.println("VALUE OF EXPND=======> " + value);

		retrunToManageRequisitionFromTravelCheck();

		loadingStatusReturn = waitForResultsLoadTime();
		System.out.println("return loading results===>" + loadingStatusReturn);
		Thread.sleep(1000L);

		if (loadingStatusReturn)
			handleAlert();

		Thread.sleep(500L);

		Assert.assertTrue(value > 0,
				"*******No Valid request in the given date interval Increase the Time Frame!!!!!*******");

		driver.switchTo().frame(mainFrameNum);

		driver.findElement(By.xpath(expandBtnLocator1 + value + expandBtnLocator2)).click();

		requestNumber1 = getRequestNumber();

		return requestNumber1;

	}

	public String getRequestNumber() {

		wait = new WebDriverWait(driver, 10);

		Actions action = new Actions(driver);

		expandBtn.click();
		WebElement puchaseOrder = wait.until(ExpectedConditions.elementToBeClickable(purchaseOrderLinkText));
		action.keyDown(Keys.CONTROL).click(puchaseOrder).keyUp(Keys.CONTROL).build().perform();

		TestUtil.switchToWindow("PSFnewTab");

		String businessUnit = businessUnitPlaceHolder.getText();
		String poNumber = poPlaceHolder.getText();
		String requestNumber = businessUnit + poNumber;

		return requestNumber;

	}

	public void returnToManageRequisitionFromPO() throws InterruptedException {

		Thread.sleep(1000L);
		int frameNum = TestUtil.giveFrameCount(By.id("PV_REQ_DTLS_WRK_PV_TRANSFER_BTN"));
		driver.switchTo().frame(frameNum);
		returnToManageRequisitionLink.click();

	}

	public void retrunToManageRequisitionFromTravelCheck() {

		int frameNum = TestUtil.giveFrameCount(By.linkText("Inquire Receipts"));
		driver.switchTo().frame(frameNum);
		inquireReceipts.click();
		returnToManageRequisitionLink.click();
	}

	public int checkTravelSciquestRequest() throws InterruptedException, IOException {

		try {
			for (int i = 0; i <= 300; i++) {

				Select select = new Select(driver.findElement(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION$" + i)));
				select.selectByVisibleText("Receive");
				driver.findElement(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION_PB$" + i)).click();

				driver.switchTo().defaultContent();

				Thread.sleep(1000L);

				if (driver.findElements(By.id("alertmsg")).size() == 0) {

					Thread.sleep(1000L);

					int poDetailsnum = TestUtil.giveFrameCount(By.id("ICTAB_1_235"));
					driver.switchTo().frame(poDetailsnum);
					poDetailsTab.click();

					int attempts = 0;
					String bu = null, po = null;

					Thread.sleep(500L);

					while (attempts <= 2) {
						try {
							bu = buReceiveItemValue.getText();
							po = poReceiveItemsValue.getText();
							break;

						} catch (Exception e) {

						}
						attempts++;

					}

					String request = bu + po;
					ArrayList<String> scireq = TestUtil.giveSciquestRequest();

					if (scireq.contains(request)) {

						driver.switchTo().defaultContent();

						Thread.sleep(1000L);
						retrunToManageRequisitionFromTravelCheck();
						loadingStatusReturn = waitForResultsLoadTime();

						if (loadingStatusReturn) {
							handleAlert();
						}

						int num = TestUtil.giveFrameCount(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION$" + i));
						driver.switchTo().frame(num);
						continue;

					}

					else {

						driver.switchTo().defaultContent();
						return i;
					}

				} else {

					alertMsgOkBtn.click();
					int num = TestUtil.giveFrameCount(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION$" + i));
					driver.switchTo().frame(num);
					continue;
				}

			}

		} catch (Exception e) {
			System.err.println("*****No Valid Requests in the given date range,Please increase the date range*****");
		}

		return -1;
	}

	public void expandFirstRequest() {

		int frameNum = TestUtil.giveFrameCount(By.xpath("//img[@name='PV_REQSTAT_WRK_PV_EXPAND_SECT$IMG$0']"));
		driver.switchTo().frame(frameNum);

		expandBtn.click();

	}

	public String getTravelRelatedRequest() throws InterruptedException {

		Thread.sleep(1000);
		driver.switchTo().frame("ptifrmtgtframe");

		String requestNumber = null;

		try {
			for (int i = 1; i <= 300; i++) {

				Select select = new Select(driver.findElement(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION$" + i)));
				select.selectByVisibleText("Receive");
				driver.findElement(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION_PB$" + i)).click();

				driver.switchTo().defaultContent();

				Thread.sleep(1500L);

				if (driver.findElements(By.id("alertmsg")).size() > 0) {

					System.out.println("Travel Realted one!!");

					alertMsgOkBtn.click();
					int num = TestUtil.giveFrameCount(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION$" + i));
					driver.switchTo().frame(num);
					requestNumber = getRequestNumber();
					break;

				}

				else {

					System.out.println("Not travel Related one!!");
					Thread.sleep(1000L);
					retrunToManageRequisitionFromTravelCheck();
					loadingStatusReturn = waitForResultsLoadTime();

					if (loadingStatusReturn) {
						handleAlert();
					}

					int num = TestUtil.giveFrameCount(By.id("PV_REQSTAT_WRK_PV_REQ_ACTION$" + i));
					driver.switchTo().frame(num);
					continue;

				}

			}

		} catch (Exception e) {

			System.err.println("*****No Valid Requests in the given date range,Please increase the date range*****");
			// e.printStackTrace();

		}

		return requestNumber;
	}
}