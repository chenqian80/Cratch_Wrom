package com.bigdata.spider.mail;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
public class MailMessageFactory
{
  private SimpleMailMessage simpleMailMessage;
  private MimeMessageHelper mimeMessageHelper;
  private JavaMailSenderImpl senderImpl;
  private MimeMessage mimeMessage;
  private SendMailType sendMailType;
  private SendMessage sendMessage;
  private static final String ENCODING = EmailEntity.EMAIL_ENCODING;

  public MailMessageFactory()
  {
    this(SendMailType.TEXT);
  }

  public MailMessageFactory(SendMailType sendMailType)
  {
    this.sendMailType = sendMailType;
    this.senderImpl = JavaMailFactory.getJavaMail();
    this.mimeMessage = this.senderImpl.createMimeMessage();
    this.sendMessage = new SendMessage();
    try
    {
      switch (this.sendMailType) {
      case TEXT:
        this.simpleMailMessage = new SimpleMailMessage();
        break;
      case HTML:
        this.mimeMessageHelper = new MimeMessageHelper(this.mimeMessage, true, ENCODING);
      }
    } catch (Exception e) {
      e.fillInStackTrace();
    }
  }

public MailMessageFactory setTo(String[] to)
  {
    this.sendMessage.setTos(to);
    return this;
  }

  public MailMessageFactory setSubject(String subject)
  {
    this.sendMessage.setSubject(subject);
    return this;
  }

  public MailMessageFactory setText(String text)
  {
    this.sendMessage.addText(text);
    return this;
  }

  public MailMessageFactory setImgFile(String imageName, File file)
  {
    try
    {
      this.sendMessage.addFile(MailType.IMG, MimeUtility.encodeWord(imageName), file);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return this;
  }

  public MailMessageFactory setImgFile(String imageName, String filePath)
  {
    setImgFile(imageName, new File(filePath));
    return this;
  }

  public MailMessageFactory setAttachmentFile(String fileName, String filePath)
  {
    setAttachmentFile(fileName, new File(filePath));
    return this;
  }

  public MailMessageFactory setAttachmentFile(String fileName, File file)
  {
    try
    {
      this.sendMessage.addFile(MailType.FILE, MimeUtility.encodeWord(fileName), file);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return this;
  }

  public void send()
  {
    this.sendMessage.setFrom(EmailEntity.EMAIL_FROM);

    String[] tos = this.sendMessage.getTos();
    if ((tos == null) || (tos.length == 0)) {
      tos = new String[] { EmailEntity.EMAIL_TO };
      this.sendMessage.setTos(tos);
    }

    long startTime = System.currentTimeMillis();

    if (this.sendMailType == SendMailType.TEXT)
      sendTextMessage();
    else {
      sendHtmlImgFileMessage();
    }
    long endTime = System.currentTimeMillis();
    System.out.println(this.sendMailType + "成功!,耗费时间: " + (endTime - startTime) + "毫秒!");
  }

  private void sendHtmlImgFileMessage()
  {
    try
    {
      this.mimeMessageHelper.setTo(this.sendMessage.getTos());
      this.mimeMessageHelper.setFrom(this.sendMessage.getFrom());
      String subject = this.sendMessage.getSubject();
      if ((subject != null) && (!"".equals(subject))) {
        this.mimeMessageHelper.setSubject(subject);
      }
      String text = this.sendMessage.getSendTexts();
      if ((text != null) && (!"".equals(text))) {
        this.mimeMessageHelper.setText(text, true);
      }

      int imgSize = this.sendMessage.getImages().size();

      if (imgSize > 0)
      {
    	  List<SendMessage> lists = this.sendMessage.getImages();
        FileSystemResource fsr = null;
        String imgName = null;
        for (SendMessage entity : lists) {
          fsr = new FileSystemResource(entity.getFile());
          imgName = entity.getImgName();

          if (entity.getMailType() == MailType.IMG)
            this.mimeMessageHelper.addInline(imgName, fsr);
          else {
            this.mimeMessageHelper.addAttachment(imgName, fsr);
          }
        }
        fsr = null;
      }
    }
    catch (MessagingException e)
    {
      e.printStackTrace();
    }
    this.senderImpl.send(this.mimeMessage);
  }

  private void sendTextMessage()
  {
    this.simpleMailMessage.setTo(this.sendMessage.getTos());
    this.simpleMailMessage.setFrom(this.sendMessage.getFrom());
    String subject = this.sendMessage.getSubject();
    if ((subject != null) && (!"".equals(subject))) {
      this.simpleMailMessage.setSubject(subject);
    }
    String text = this.sendMessage.getSendTexts();
    if ((text != null) && (!"".equals(text))) {
      this.simpleMailMessage.setText(text);
    }
    this.senderImpl.send(this.simpleMailMessage);
  }

  public void send(String subject, String text, String[] to)
  {
    sendMessage(subject, text, to);
  }

  private void sendMessage(String subject, String text, String[] to)
  {
    setTo(to);
    setSubject(subject);
    setText(text);
  }

  public SendMessage getSendMessage()
  {
    return this.sendMessage;
  }

  public MailMessageFactory setSendMessage(SendMessage sendMessage)
  {
    this.sendMessage = sendMessage;
    return this;
  }
}
