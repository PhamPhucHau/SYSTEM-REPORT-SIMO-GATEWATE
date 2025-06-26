package com.org.shbvn.svbsimo.core.model;

import java.lang.String;

public class API16TTTKINSTTemplate extends BankCommonTemplate {

  private String accountNo;

  private String accountName;

  private String accountType;

  private String accountStatus;

  private String accountOpenDate;

  private String accountCloseDate;

  private String accountCurrency;

  private String accountBalance;

  public API16TTTKINSTTemplate() {
    super();
  }

  public API16TTTKINSTTemplate(String rowNumber, String cusName, String cif, String personalId, String accountNo, String accountName, String accountType, String accountStatus, String accountOpenDate, String accountCloseDate, String accountCurrency, String accountBalance) {
    super(rowNumber, cif);
    this.accountNo = accountNo;
    this.accountName = accountName;
    this.accountType = accountType;
    this.accountStatus = accountStatus;
    this.accountOpenDate = accountOpenDate;
    this.accountCloseDate = accountCloseDate;
    this.accountCurrency = accountCurrency;
    this.accountBalance = accountBalance;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  public String getAccountOpenDate() {
    return accountOpenDate;
  }

  public void setAccountOpenDate(String accountOpenDate) {
    this.accountOpenDate = accountOpenDate;
  }

  public String getAccountCloseDate() {
    return accountCloseDate;
  }

  public void setAccountCloseDate(String accountCloseDate) {
    this.accountCloseDate = accountCloseDate;
  }

  public String getAccountCurrency() {
    return accountCurrency;
  }

  public void setAccountCurrency(String accountCurrency) {
    this.accountCurrency = accountCurrency;
  }

  public String getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(String accountBalance) {
    this.accountBalance = accountBalance;
  }
}
