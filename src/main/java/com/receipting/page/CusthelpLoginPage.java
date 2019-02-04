package com.receipting.page;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.receipting.base.ReceiptingBase;
import com.receipting.util.TestUtil;

public class CusthelpLoginPage extends ReceiptingBase {

	WebDriverWait wait;

	public CusthelpLoginPage() throws IOException {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	@CacheLookup
	WebElement userName;

	@FindBy(id = "password")
	@CacheLookup
	WebElement password;

	@FindBy(id = "_eventId_proceed")
	WebElement signIn;

	@FindBy(id = "token")
	WebElement enterTokenTxtBox;

	By MFAErrorMsg = By.className("form-error");

	public void custHelpLogin() {

		wait = new WebDriverWait(driver, 3);

		wait.until(ExpectedConditions.visibilityOf(userName)).clear();
		userName.sendKeys(prop.getProperty("CustHelpUsername").trim());

		wait.until(ExpectedConditions.visibilityOf(password)).clear();
		password.sendKeys(prop.getProperty("CustHelpPassword").trim());

		wait.until(ExpectedConditions.visibilityOf(signIn)).click();

	}

	public void custHelpEnter2FA() throws IOException {

		String token = TestUtil.getTwoFA();
		wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.visibilityOf(enterTokenTxtBox)).sendKeys(token);
		signIn.click();

	}

	public boolean returnErrorMsg() {

		return TestUtil.isElementPresent(driver, MFAErrorMsg);

	}

}
