package ru.geekbrains.spring.ishop.informing.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.informing.subjects.AbstractSubject;

@Service
public class SkypeObserver implements IObserver {
    Logger logger = LoggerFactory.getLogger(SkypeObserver.class);

    @Override
    public void update(AbstractSubject subject, Object arg) {
        logger.info("******** update() *********\n" +
                "Sending message through Skype...");
    }
}
