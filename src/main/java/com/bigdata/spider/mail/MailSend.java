package com.bigdata.spider.mail;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bigdata.spider.util.DBUtil;
import com.bigdata.spider.util.LoadPropertyUtil;
import com.bigdata.spider.util.TimeUtil;



public class MailSend
{
  private static final Logger log = Logger.getLogger("MailSend");

  public static void main(String[] args)
  {
	  List<Map<String, String>>  list = DBUtil.getMailList();
    for (Map m : list)
      try {
        MailMessageFactory mms = new MailMessageFactory(SendMailType.HTML);
        mms.setTo(new String[] { (String)m.get("to") });
        mms.setSubject((String)m.get("subject"));
        mms.setText((String)m.get("content"));
        mms.send();
        DBUtil.setMailStatus((String)m.get("mlid"), 1);
      } catch (Exception e) {
        log.error(e.getMessage());
        DBUtil.setMailStatus((String)m.get("mlid"), 0);

        MailMessageFactory mms = new MailMessageFactory(SendMailType.HTML);
        mms.setTo(new String[] { LoadPropertyUtil.getDB("mail_error_to") });
        mms.setSubject("【重要】邮件发送出错啦");

        StringBuffer bf = new StringBuffer("<strong>ERROR： </strong><br/>");
        bf.append(e.getMessage());
        bf.append("<br/>TO: " + (String)m.get("to"));
        bf.append("<br/>MSG: <br/>-------------<br/>");
        bf.append((String)m.get("content"));
        bf.append("<br/>-------------<br/>");
        bf.append("TIME: " + TimeUtil.getCurSQLTimestamp());
        mms.setText(bf.toString());
        mms.send();
      }
  }
}
