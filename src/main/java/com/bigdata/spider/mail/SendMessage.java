package com.bigdata.spider.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendMessage
{
  private String[] tos;
  private String subject;
  private String imgName;
  private File file;
  private MailType mailType;
  private String from;
  private List<String> texts = new ArrayList<String>();

  private List<SendMessage> images = new ArrayList<SendMessage>();

  public MailType getMailType() {
    return this.mailType;
  }

  public void setMailType(MailType mailType)
  {
    this.mailType = mailType;
  }

  public void addFile(MailType mailType, String imgName, File file)
  {
    this.images.add(new SendMessage(mailType, imgName, file));
  }

  public void addText(String text) {
    this.texts.add(text);
  }

  public SendMessage(MailType mailType, String imgName, File file)
  {
    this.mailType = mailType;
    this.imgName = imgName;
    this.file = file;
  }

  public List<SendMessage> getImages()
  {
    return this.images;
  }
  public void setImages(List<SendMessage> images) {
    this.images = images;
  }
  public String getFrom() {
    return this.from;
  }
  public void setFrom(String from) {
    this.from = from;
  }
  public String getImgName() {
    return this.imgName;
  }
  public void setImgName(String imgName) {
    this.imgName = imgName;
  }

  public String[] getTos() {
    return this.tos;
  }
  public void setTos(String[] tos) {
    this.tos = tos;
  }
  public String getSubject() {
    return this.subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }

  public SendMessage()
  {
  }

  public SendMessage(String[] tos, String subject, String text) {
    this.tos = tos;
    this.subject = subject;
    addText(text);
  }

  public String getSendTexts() {
    StringBuilder sbr = new StringBuilder();
    List<String> texts = getTexts();
    for (String text : texts) {
      sbr.append(text);
    }
    return sbr.toString();
  }

  public String toString()
  {
    StringBuilder sbrTos = new StringBuilder();
    for (String to : this.tos) {
      sbrTos.append(to).append(",");
    }
    String sbrText = sbrTos.substring(0, sbrTos.length() - 1);
    return 
      "接收人地址: " + sbrText + 
      ",发送人地址: " + this.from + 
      ",发送标题: " + this.subject + 
      ",发送内容: " + getSendTexts();
  }

  public File getFile() {
    return this.file;
  }
  public void setFile(File file) {
    this.file = file;
  }

  public List<String> getTexts()
  {
    return this.texts;
  }

  public void setTexts(List<String> texts)
  {
    this.texts = texts;
  }
}