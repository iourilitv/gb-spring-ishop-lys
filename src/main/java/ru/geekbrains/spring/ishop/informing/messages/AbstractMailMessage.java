package ru.geekbrains.spring.ishop.informing.messages;

public abstract class AbstractMailMessage extends AbstractMessage {
//    String sendTo, cc, subject, body;
//    Object attachment, origin;
    private String cc;
    private Object attachment;

//    public AbstractMailMessage() {
//    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    //    public String getSendTo() {
//        return sendTo;
//    }
//
//    public void setSendTo(String sendTo) {
//        this.sendTo = sendTo;
//    }
//
//    public String getCc() {
//        return cc;
//    }
//
//    public void setCc(String cc) {
//        this.cc = cc;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public Object getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(Object attachment) {
//        this.attachment = attachment;
//    }
//
//    public Object getOrigin() {
//        return origin;
//    }
//
//    public void setOrigin(Object origin) {
//        this.origin = origin;
//    }
}
