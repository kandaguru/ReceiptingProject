package com.receipting.test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.receipting.base.ReceiptingBase;
import com.receipting.page.CustHelpRequestPage;
import com.receipting.page.CusthelpLoginPage;
import com.receipting.page.PsfLoginPage;
import com.receipting.page.PsfManageRequisitionPage;
import com.receipting.page.QueryViewerPage;
import com.receipting.util.TestUtil;

public class PSFFetchData extends ReceiptingBase {

	public PSFFetchData() throws IOException {
		super();
	}

	PsfLoginPage psfLoginPage;
	PsfManageRequisitionPage psfManageRequisitionPage;
	CusthelpLoginPage custHelpLoginPage;
	CustHelpRequestPage custHelpRequestPage;
	QueryViewerPage queryViewerPage;

	boolean loadingStatus, loadingStatusReturn;
	String requestNumberValue;

	@BeforeClass
	public void setUp() throws IOException, InterruptedException {

		intialize();

		psfLoginPage = new PsfLoginPage();
		queryViewerPage = new QueryViewerPage();
		openQueryViewer();
		psfLoginPage.psfLogin();
		psfManageRequisitionPage = psfLoginPage.psfEnter2FA();
		queryViewerPage.downloadSciquetFile();

		openPsf();

		try {
			psfLoginPage.psfLogin();
			psfLoginPage.psfEnter2FA();
		} catch (Exception e) {

			System.err.println("PSF Already logged In!!");
		}

	}

	@Test(priority = 1, dataProvider = "mainDataProviders", description = "poDispatched And PartialyReceived", enabled = true)

	public void getRequestData(String sNo, String requestType) throws InterruptedException, IOException {

		psfManageRequisitionPage.clickClearBtn();

		psfManageRequisitionPage.chooseRequestState(requestType);
		psfManageRequisitionPage.enterDates();

		psfManageRequisitionPage.clickSearch();

		loadingStatus = psfManageRequisitionPage.waitForResultsLoadTime();
		if (loadingStatus)
			psfManageRequisitionPage.handleAlert();

		requestNumberValue = psfManageRequisitionPage.validateAndRetrunRequestNumber();

		openCustHelp();

		try {
			custHelpLoginPage = new CusthelpLoginPage();
			custHelpLoginPage.custHelpLogin();
			custHelpLoginPage.custHelpEnter2FA();
			while (custHelpLoginPage.returnErrorMsg()) {

				custHelpLoginPage.custHelpEnter2FA();

			}
		} catch (Exception e) {

			System.err.println("Already Logged In!!!!!");

		}

		custHelpRequestPage = new CustHelpRequestPage();
		custHelpRequestPage.selectService();
		custHelpRequestPage.selectTopic();
		custHelpRequestPage.selectSubTopic();
		custHelpRequestPage.enterPODetails(requestType, requestNumberValue);
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}

	@Test(priority = 2, description = "PO(s) Canceled/completed/Received", dataProvider = "PoCanceledAndCompletedDataProvider", enabled = true)
	public void getPoCancelledAndCompletedData(String sNo, String requestType)
			throws InterruptedException, IOException {

		psfManageRequisitionPage.clickClearBtn();
		psfManageRequisitionPage.chooseRequestState(requestType);
		psfManageRequisitionPage.clickSearch();

		loadingStatus = psfManageRequisitionPage.waitForResultsLoadTime();
		if (loadingStatus)
			psfManageRequisitionPage.handleAlert();

		psfManageRequisitionPage.expandFirstRequest();
		requestNumberValue = psfManageRequisitionPage.getRequestNumber();
		openCustHelp();

		try {
			custHelpLoginPage = new CusthelpLoginPage();
			custHelpLoginPage.custHelpLogin();
			custHelpLoginPage.custHelpEnter2FA();
			while (custHelpLoginPage.returnErrorMsg()) {

				custHelpLoginPage.custHelpEnter2FA();

			}
		} catch (Exception e) {

			System.err.println("Already Logged In!!!!!");

		}

		custHelpRequestPage = new CustHelpRequestPage();
		custHelpRequestPage.selectService();
		custHelpRequestPage.selectTopic();
		custHelpRequestPage.selectSubTopic();
		custHelpRequestPage.enterPODetails(requestType, requestNumberValue);
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}

	@Test(priority = 4, description = "Travel Realted", enabled = true)
	public void getTravelRelatedData() throws InterruptedException, IOException {

		psfManageRequisitionPage.clickClearBtn();

		psfManageRequisitionPage.chooseRequestState("PO(s) Dispatched");
		psfManageRequisitionPage.enterDates();

		psfManageRequisitionPage.clickSearch();

		loadingStatus = psfManageRequisitionPage.waitForResultsLoadTime();
		if (loadingStatus)
			psfManageRequisitionPage.handleAlert();

		requestNumberValue = psfManageRequisitionPage.getTravelRelatedRequest();

		openCustHelp();

		try {
			custHelpLoginPage = new CusthelpLoginPage();
			custHelpLoginPage.custHelpLogin();
			custHelpLoginPage.custHelpEnter2FA();
			while (custHelpLoginPage.returnErrorMsg()) {

				custHelpLoginPage.custHelpEnter2FA();

			}
		} catch (Exception e) {

			System.err.println("Already Logged In!!!!!");

		}

		custHelpRequestPage = new CustHelpRequestPage();
		custHelpRequestPage.selectService();
		custHelpRequestPage.selectTopic();
		custHelpRequestPage.selectSubTopic();
		custHelpRequestPage.enterPODetails("PO Dispatched-Travel", requestNumberValue);
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}

	@Test(priority = 3, description = "SciQuest", enabled = true)
	public void getSciQuestData() throws IOException, InterruptedException {

		ArrayList<String> sciReq = TestUtil.giveSciquestRequest();
		String reqNumber = null;

		for (int i = 0; i < sciReq.size(); i += 8) {

			if (sciReq.get(i + 6).equalsIgnoreCase("Canceled"))
				continue;
			else
				reqNumber = sciReq.get(i + 3);
			break;

		}

		TestUtil.openNewTab();
		TestUtil.switchToWindow("New Tab");
		openCustHelp();

		try {
			custHelpLoginPage = new CusthelpLoginPage();
			custHelpLoginPage.custHelpLogin();
			custHelpLoginPage.custHelpEnter2FA();
			while (custHelpLoginPage.returnErrorMsg()) {

				custHelpLoginPage.custHelpEnter2FA();

			}
		} catch (Exception e) {

			System.err.println("Already Logged In!!!!!");

		}

		custHelpRequestPage = new CustHelpRequestPage();
		custHelpRequestPage.selectService();
		custHelpRequestPage.selectTopic();
		custHelpRequestPage.selectSubTopic();
		custHelpRequestPage.enterPODetails("SciQuest", reqNumber);
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}

	@Test(priority = 4, description = "Incorrect PO", dataProvider = "incorrectPoDataProviders", enabled = true)
	public void getIncorrectPOData(String requestType, String requestNumber) throws IOException, InterruptedException {

		TestUtil.openNewTab();
		TestUtil.switchToWindow("New Tab");
		openCustHelp();

		try {
			custHelpLoginPage = new CusthelpLoginPage();
			custHelpLoginPage.custHelpLogin();
			custHelpLoginPage.custHelpEnter2FA();
			while (custHelpLoginPage.returnErrorMsg()) {

				custHelpLoginPage.custHelpEnter2FA();

			}
		} catch (Exception e) {

			System.err.println("Already Logged In!!!!!");

		}

		custHelpRequestPage = new CustHelpRequestPage();
		custHelpRequestPage.selectService();
		custHelpRequestPage.selectTopic();
		custHelpRequestPage.selectSubTopic();
		custHelpRequestPage.enterPODetails(requestType, requestNumber);
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}

	@AfterClass
	public void tearDown() {

		deleteFile();
		System.out.println("The OSC request numbers for processing " + oscRequestNumbers);
		driver.close();
		driver = null;
	}

	@DataProvider
	public Object[][] PoCanceledAndCompletedDataProvider() {

		Object data[][] = new Object[3][2];

		data[0][0] = "1";
		data[0][1] = "PO(s) Canceled";

		data[1][0] = "2";
		data[1][1] = "PO(s) Completed";

		data[2][0] = "3";
		data[2][1] = "Received";

		return data;

	}

	@DataProvider
	public Object[][] incorrectPoDataProviders() {

		Object data[][] = new Object[3][2];

		data[0][0] = "Invalid PO Format -Incorrect length";
		data[0][1] = "XF0123456";

		data[1][0] = "Valid format, no corresponding PO in PS Finance";
		data[1][1] = "PS0000123456";

		data[2][0] = "Invalid Business Unit Code";
		data[2][1] = "XF0000123456";

		return data;

	}

	@DataProvider
	public Object[][] mainDataProviders() {

		Object data[][] = new Object[2][2];

		data[0][0] = "1";
		data[0][1] = "PO(s) Dispatched";

		data[1][0] = "2";
		data[1][1] = "Partially Received";

//		data[2][0] = "3";
//		data[2][1] = "Partially Dispatched";    Not a valid scenario

		return data;

	}

}
