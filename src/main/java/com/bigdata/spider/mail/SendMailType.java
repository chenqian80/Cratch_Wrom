package com.bigdata.spider.mail;

public enum SendMailType
{
  TEXT, HTML;

  public String toString()
  {
    String message = null;
    switch (this) {
    case HTML:
      message = "发送文本消息";
      break;
    case TEXT:
      message = "发送html消息";
    }

    return message;
  }
}
