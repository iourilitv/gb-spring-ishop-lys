package ru.geekbrains.spring.ishop.service.interfaces;

import ru.geekbrains.spring.ishop.utils.MailText;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Queue;

public interface INotifier {
    Queue<AbstractMailMessage> queueToSend = new LinkedList<>();

    void putMessageIntoQueue(Object object, MailText subject);

    @PostConstruct
    void sendMessageFromQueue();


}
