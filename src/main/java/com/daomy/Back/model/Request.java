package com.daomy.Back.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Request {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;

    private String gauche;
    private String droite;
    private String action;
    private String domino;

    public Status getStatus() {
        return this.status;
    }

    public String getSenderName(){
        return this.senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getMessage() {
        return message;
    }

    public String getGauche() {
        return gauche;
    }

    public String getDomino() {
        return domino;
    }

    public String getDroite() {
        return droite;
    }

    public String getAction() {
        return action;
    }

    public Request(String senderName, String receiverName, String message, Status status, String gauche, String droite, String action, String domino) {
        this.senderName = senderName;
        this.status = status;
        this.receiverName=receiverName;
        this.message=message;
        this.action=action;
        this.droite=droite;
        this.gauche=gauche;
        this.domino=domino;
    }
}

