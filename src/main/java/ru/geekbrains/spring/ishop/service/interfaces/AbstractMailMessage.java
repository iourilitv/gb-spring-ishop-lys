package ru.geekbrains.spring.ishop.service.interfaces;

public abstract class AbstractMailMessage {
//    String sendTo = null;
//    String cc = null;
//    String subject = null;
//    String body = null;
//    Object attachment = null;
//    Object origin = null;
    String sendTo, cc, subject, body;
    Object attachment, origin;

    public AbstractMailMessage() {
    }
//
//    public AbstractMailMessage(Object origin) {
//        this.origin = origin;
//    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }
}
