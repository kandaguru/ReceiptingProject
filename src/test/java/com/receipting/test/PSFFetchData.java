package com.receipting.test;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.receipting.base.ReceiptingBase;
import com.receipting.page.CustHelpRequestPage;
import com.receipting.page.CusthelpLoginPage;
import com.receipting.page.PsfLoginPage;
import com.receipting.page.PsfManageRequisitionPage;
import com.receipting.util.TestUtil;

public class PSFFetchData extends ReceiptingBase {

	public PSFFetchData() throws IOException {
		super();
	}

	PsfLoginPage psfLoginPage;
	PsfManageRequisitionPage psfManageRequisitionPage;
	CusthelpLoginPage custHelpLoginPage;
	CustHelpRequestPage custHelpRequestPage;

	boolean loadingStatus, loadingStatusReturn;
	String requestNumberValue;

	@BeforeClass
	public void setUp() throws IOException, InterruptedException {

		intialize();
		openPsf();
		psfLoginPage = new PsfLoginPage();
		psfLoginPage.psfLogin();
		psfManageRequisitionPage = psfLoginPage.psfEnter2FA();

	}

	@Test(priority = 1, dataProvider = "dataProviders")
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

	@Test(priority = 2, description = "PO(s) Canceled")
	public void getPoCancelledData() throws InterruptedException, IOException {

		psfManageRequisitionPage.clickClearBtn();
		psfManageRequisitionPage.chooseRequestState("PO(s) Canceled");
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
		custHelpRequestPage.enterPODetails("PO(s) Canceled", requestNumberValue);
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}

	
	
	@Test(priority = 3, description = "PO(s) Completed")
	public void getPoCompletedData() throws InterruptedException, IOException {

		psfManageRequisitionPage.clickClearBtn();
		psfManageRequisitionPage.chooseRequestState("PO(s) Completed");
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
		custHelpRequestPage.enterPODetails("PO(s) Completed", requestNumberValue);
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}
	
	
	@Test(priority = 3, description = "Travel Realted")
	public void getTravelRelatedData() {

		System.out.println("TBD");
	}

	
	
	@Test(priority = 4, description = "Incorrect PO")
	public void getIncorrectPOData() throws IOException, InterruptedException {

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
		custHelpRequestPage.enterPODetails("Incorrect PO", "XF1234567890");
		custHelpRequestPage.closeCustHelp();

		TestUtil.switchToWindow("PSF");

	}

	@AfterClass
	public void tearDown() {

		deleteFile();
		// driver.close();
		// driver = null;
	}

	@DataProvider
	public Object[][] dataProviders() {

		Object data[][] = new Object[1][2];

		data[0][0] = "1";
		data[0][1] = "PO(s) Dispatched";

//		data[1][0] = "2";
//		data[1][1] = "PO(s) Dispatched";
//
//		data[2][0] = "3";
//		data[2][1] = "Partially Dispatched";

//		data[3][0] = "4";
//		data[3][1] = "PO(s) Canceled ";
//
//		data[4][0] = "5";
//		data[4][1] = "PO(s) Completed";
//
//		data[5][0] = "6";
//		data[5][1] = "Partially Received";

		return data;

	}

}
