package ru.geekbrains.spring.ishop.informing.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.informing.MailService;
import ru.geekbrains.spring.ishop.informing.subjects.AbstractSubject;
import ru.geekbrains.spring.ishop.informing.subjects.OrderSubject;

@Service
public class MailObserver implements IObserver {
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void update(AbstractSubject subject, Object arg) {
        OrderSubject orderSubject = (OrderSubject) subject;
        mailService.putMessageIntoQueue(orderSubject.getOrder(),
                orderSubject.getOrderText());
    }

}
