package com.bigdata.spider.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class JavaMailFactory
{
  private static JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

  static {
    senderImpl.setHost(EmailEntity.EMAIL_HOST);
    senderImpl.setUsername(EmailEntity.EMAIL_USERNAME);
    senderImpl.setPassword(EmailEntity.EMAIL_PASSWORD);
    senderImpl.setJavaMailProperties(EmailEntity.safetyProperties);
  }

  public static JavaMailSenderImpl getJavaMail() {
    return senderImpl;
  }
}