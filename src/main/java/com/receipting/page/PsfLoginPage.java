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

public class PsfLoginPage extends ReceiptingBase {

	WebDriverWait wait;

	public PsfLoginPage() throws IOException {
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

	public void psfLogin() throws IOException {

		wait = new WebDriverWait(driver, 5);

		wait.until(ExpectedConditions.visibilityOf(userName)).clear();
		userName.sendKeys(prop.getProperty("PSFusername").trim());

		wait.until(ExpectedConditions.visibilityOf(password)).clear();
		password.sendKeys(prop.getProperty("PSFpassword").trim());

		wait.until(ExpectedConditions.visibilityOf(signIn)).click();

	}

	public PsfManageRequisitionPage psfEnter2FA() throws IOException {

		enterTokenTxtBox.clear();
		enterTokenTxtBox.sendKeys(prop.getProperty("PSF2FACode"));
		signIn.click();

		return new PsfManageRequisitionPage();
	}

}
