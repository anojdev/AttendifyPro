package edu.miu.attendifypro.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author kush
 */
@Service
public class SendMessage {
    final
    JmsTemplate jmsTemplate;

    public SendMessage(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void dispatchEmailMessage(String message){
        jmsTemplate.convertAndSend("emailQueue",message);
    }
}
