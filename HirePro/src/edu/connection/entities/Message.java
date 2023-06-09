/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;


import java.sql.Timestamp;






/**
 *
 * @author kamel guermassi
 */
public class Message {
    private int idMsg;
    private String msg;
    private Timestamp dateSend;
    private int idUserSend;
    private int idUserReceive;


    public Message() {

    }
    public Message(String msg, Timestamp dateSend, int idUserSend, int idUserReceive) {
        this.msg = msg;
        this.dateSend = dateSend;
        this.idUserSend = idUserSend;
        this.idUserReceive = idUserReceive;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public String getMsg() {
        return msg;
    }

    public Timestamp getDateSend() {
        return dateSend;
    }

    public int getIdUserSend() {
        return idUserSend;
    }

    public int getIdUserReceive() {
        return idUserReceive;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDateSend(Timestamp dateSend) {
        this.dateSend = dateSend;
    }

    public void setIdUserSend(int idUserSend) {
        this.idUserSend = idUserSend;
    }

    public void setIdUserReceive(int idUserReceive) {
        this.idUserReceive = idUserReceive;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", dateSend=" + dateSend +
                ", idUserSend=" + idUserSend +
                ", idUserReceive=" + idUserReceive +
                '}';
    }
}
