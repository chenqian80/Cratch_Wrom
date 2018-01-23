package com.bigdata.spider.util;

import java.sql.Timestamp;
import java.util.Date;

public final class TimeUtil
{
  public static Timestamp getCurSQLTimestamp()
  {
    Date date = new Date();
    Timestamp timestamp = new Timestamp(date.getTime());
    return timestamp;
  }

  public static java.sql.Date getCurSQLDate()
  {
    Date date = new Date();
    return new java.sql.Date(date.getTime());
  }
  
  public static void main(String[] args) {
	  System.out.println(getCurSQLTimestamp());
	  System.out.println(getCurSQLDate());
  }
}