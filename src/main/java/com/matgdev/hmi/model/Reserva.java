package com.matgdev.hmi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String solicitante;
    private LocalDateTime datahora_chegadaprevista;
    private LocalDateTime datahora_partidaprevista;
    private String status;

    public Reserva(){

    }

    public Reserva(Long id, String solicitante, LocalDateTime datahora_chegadaprevista, LocalDateTime datahora_partidaprevista, String status) {
        this.id = id;
        this.solicitante = solicitante;
        this.datahora_chegadaprevista = datahora_chegadaprevista;
        this.datahora_partidaprevista = datahora_partidaprevista;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", solicitante='" + solicitante + '\'' +
                ", datahora_chegadaprevista=" + datahora_chegadaprevista +
                ", datahora_partidaprevista=" + datahora_partidaprevista +
                ", status='" + status + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":" + id +
                ", \"solicitante\":\"" + solicitante + '\"' +
                ", \"datahora_chegadaprevista\":\"" + datahora_chegadaprevista + '\"' +
                ", \"datahora_partidaprevista\":\"" + datahora_partidaprevista + '\"' +
                ", \"status\":\"" + status + '\"' +
                '}';
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public LocalDateTime getDatahora_chegadaprevista() {
        return datahora_chegadaprevista;
    }

    public void setDatahora_chegadaprevista(LocalDateTime datahora_chegadaprevista) {
        this.datahora_chegadaprevista = datahora_chegadaprevista;
    }

    public LocalDateTime getDatahora_partidaprevista() {
        return datahora_partidaprevista;
    }

    public void setDatahora_partidaprevista(LocalDateTime datahora_partidaprevista) {
        this.datahora_partidaprevista = datahora_partidaprevista;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}