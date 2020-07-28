package ru.geekbrains.spring.ishop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.service.interfaces.AbstractMailMessage;
import ru.geekbrains.spring.ishop.service.interfaces.OrderEmailMessage;
import ru.geekbrains.spring.ishop.service.interfaces.INotifier;
import ru.geekbrains.spring.ishop.utils.MailText;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService implements INotifier {
    private JavaMailSender sender;
    private MailMessageBuilder messageBuilder;

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    private void sendMail(String email, String subject, String text) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setText(text, true);
        helper.setSubject(subject);
        sender.send(message);
    }

//    public void sendOrderMail(Order order) {
//        try {
//            sendMail(order.getUser().getEmail(), String.format("New Status of order id: %d%n. %s", order.getId(), order.getOrderStatus().getTitle()), messageBuilder.buildOrderEmail(order));
//        } catch (MessagingException e) {
//            logger.warn("Unable to create order mail message for order: " + order.getId());
//        } catch (MailException e) {
//            logger.warn("Unable to send order mail message for order: " + order.getId());
//        }
//    }
    public void sendOrderMail(OrderEmailMessage orderEmailMessage) {
        try {
            sendMail(orderEmailMessage.getSendTo(), orderEmailMessage.getSubject(), orderEmailMessage.getBody());
        } catch (MessagingException e) {
            logger.warn(MailText.ERROR_CREATE_ORDER_MAIL.getText(), orderEmailMessage.getOrder().getId());
            takePauseInSec(10);
            queueToSend.add(orderEmailMessage);
        } catch (MailException e) {
            logger.warn(MailText.ERROR_SEND_ORDER_MAIL.getText(), orderEmailMessage.getOrder().getId());
            takePauseInSec(10);
            queueToSend.add(orderEmailMessage);
        }
    }

    @Override
    public void putMessageIntoQueue(Object object, MailText subject) {
        if(object instanceof Order) {
            AbstractMailMessage emailMessage = createOrderMailMessage((Order) object, subject);
            queueToSend.add(emailMessage);
            logger.info("******** putMessageIntoQueue() ***********\n" + "QueueToSend: " + queueToSend);
        }
    }

    @Override
    @PostConstruct
    public void sendMessageFromQueue() {
        new Thread(() -> {
            logger.info("******** sendMessageFromQueue() has been launched successfully *********");
            while (true) {
                if(!queueToSend.isEmpty()) {
                    logger.info("******** sendMessageFromQueue() ***********\n" + "QueueToSend: " + queueToSend);
                    AbstractMailMessage message = queueToSend.remove();
                    logger.info("******** Trying to send the message from queue ***********\n" + "Message subject: " + message.getSubject());
                    if(message instanceof OrderEmailMessage) {
                        sendOrderMail((OrderEmailMessage) message);
                    }

                    //the same if() for another types of AbstractMailMessage

                }
            }
        }).start();

    }

    private AbstractMailMessage createOrderMailMessage(Order order, MailText subject) {
        AbstractMailMessage emailMessage = new OrderEmailMessage();
        emailMessage.setSendTo(order.getUser().getEmail());

        if(subject.equals(MailText.SUBJECT_NEW_ORDER_CREATED)) {
            emailMessage.setSubject(String.format(subject.getText(), order.getId()));
        } else if (subject.equals(MailText.SUBJECT_ORDER_STATUS_CHANGED)) {
            emailMessage.setSubject(String.format(subject.getText(), order.getId(), order.getOrderStatus().getTitle()));
        }
        emailMessage.setBody(messageBuilder.buildOrderEmail(order));
        emailMessage.setOrigin(order);
        return emailMessage;
    }

    void takePauseInSec(int period) {
        int sec = period * 1000;
        logger.info("******** takePauseInSec() ***********\n" + "Pause period(sec): " + sec);
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
