package com.bigdata.spider.mail;

import java.io.InputStream;
import java.util.Properties;

public class ReadConfigEmail
{
  private static Properties mailProps = new Properties();
  private static final String EMAILPROPERTIES = "config/mail.properties";

  static
  {
    try
    {
      InputStream inStream = ReadConfigEmail.class.getClassLoader()
        .getResourceAsStream("mail.properties");
      mailProps.load(inStream);
    } catch (Exception ex) {
      throw new RuntimeException("读取配置文件出错了..." + ex.getMessage(), ex);
    }
  }

  public static Properties getProperty() {
    return mailProps;
  }

  public static String getPropertyByKey(String key) {
    return mailProps.getProperty(key);
  }
}