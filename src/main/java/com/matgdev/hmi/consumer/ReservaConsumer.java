package com.matgdev.hmi.consumer;

import com.matgdev.hmi.model.Reserva;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservaConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "reservaQueue")
    public void receiveMessage(Reserva reserva) {

        System.out.println("Reserva recebida: " + reserva);

        // Notifica o frontend via WebSocket
        messagingTemplate.convertAndSend("/topic/reservas", reserva);
    }
}
