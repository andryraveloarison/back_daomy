package com.daomy.Back.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;

    private String[][] listeDominos;

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

    public String[][] getListeDominos() {
        return this.listeDominos;
    }

    public Message(String senderName,String receiverName, String message, Status status, String[][] listeDominos) {
        this.senderName = senderName;
        this.status = status;
        this.receiverName=receiverName;
        this.message=message;
        this.listeDominos=listeDominos;
    }
}

