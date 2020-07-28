package ru.geekbrains.spring.ishop.informing;

import ru.geekbrains.spring.ishop.informing.messages.AbstractMailMessage;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Queue;

public interface INotifier {
    Queue<AbstractMailMessage> queueToSend = new LinkedList<>();

    void putMessageIntoQueue(Object object, OrderText subject);

    @PostConstruct
    void sendMessageFromQueue();


}
