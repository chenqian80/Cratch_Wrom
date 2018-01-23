package com.bigdata.spider.mail;

import java.util.Properties;

public class EmailEntity
{
  private static final String EMAIL_HOST_KEY = "mail.host";
  public static final String EMAIL_HOST = ReadConfigEmail.getPropertyByKey("mail.host");
  private static final String EMAIL_USERNAME_KEY = "mail.username";
  public static final String EMAIL_USERNAME = ReadConfigEmail.getPropertyByKey("mail.username");
  private static final String EMAIL_PASSWORD_KEY = "mail.password";
  public static final String EMAIL_PASSWORD = ReadConfigEmail.getPropertyByKey("mail.password");
  private static final String EMAIL_TO_KEY = "mail.to";
  public static final String EMAIL_TO = ReadConfigEmail.getPropertyByKey("mail.to");
  private static final String EMAIL_FROM_KEY = "mail.from";
  public static final String EMAIL_FROM = ReadConfigEmail.getPropertyByKey("mail.from");
  private static final String EMAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
  public static final String EMAIL_SMTP_AUTH = ReadConfigEmail.getPropertyByKey("mail.smtp.auth");
  private static final String EMAIL_SMTP_TIMEOUT_KEY = "mail.smtp.timeout";
  public static final String EMAIL_SMTP_TIMEOUT = ReadConfigEmail.getPropertyByKey("mail.smtp.timeout");
  private static final String EMAIL_ENCODING_KEY = "mail.encoding";
  public static final String EMAIL_ENCODING = ReadConfigEmail.getPropertyByKey("mail.encoding");
  private static final String EMAIL_SMTP_STARTTLS_KEY = "mail.smtp.starttls.enable";
  public static final String EMAIL_SMTP_STARTTLS = ReadConfigEmail.getPropertyByKey("mail.smtp.starttls.enable");

  public static Properties safetyProperties = new Properties();

  static
  {
    safetyProperties.put("mail.smtp.auth", EMAIL_SMTP_AUTH);
    safetyProperties.put("mail.smtp.timeout", EMAIL_SMTP_TIMEOUT);
    safetyProperties.put("mail.smtp.starttls.enable", EMAIL_SMTP_STARTTLS);
  }
}