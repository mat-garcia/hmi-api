package com.matgdev.hmi.service;

import com.matgdev.hmi.model.Reserva;
import com.matgdev.hmi.repository.ReservaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Reserva criarReserva(Reserva reserva) {
        Reserva savedReserva = reservaRepository.save(reserva);
        rabbitTemplate.convertAndSend("reservaExchange", "reserva.routingKey", savedReserva);
        return savedReserva;
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }
}
