package edu.connection.entities;

import java.sql.Blob;
import java.sql.Timestamp;

public class Message {
    private int idMsg;
    private String msg;
    private Timestamp dateSend;
    private int idUserSend;
    private int idUserReceive;
    private String file; // Added file field
    private String fileName;

    public Message() {
    }

    public Message(String msg, Timestamp dateSend, int idUserSend, int idUserReceive,String file,String fileName) {
        this.msg = msg;
        this.dateSend = dateSend;
        this.idUserSend = idUserSend;
        this.idUserReceive = idUserReceive;
        this.file = file;
        this.file = fileName;

    }

    // Getters and Setters

    public int getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getDateSend() {
        return dateSend;
    }

    public void setDateSend(Timestamp dateSend) {
        this.dateSend = dateSend;
    }

    public int getIdUserSend() {
        return idUserSend;
    }

    public void setIdUserSend(int idUserSend) {
        this.idUserSend = idUserSend;
    }

    public int getIdUserReceive() {
        return idUserReceive;
    }

    public void setIdUserReceive(int idUserReceive) {
        this.idUserReceive = idUserReceive;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    
    public String getFileName() {
        return file;
    }

    public void setFileName(String fileName) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", dateSend=" + dateSend +
                ", idUserSend=" + idUserSend +
                ", idUserReceive=" + idUserReceive +
                '}';
    }
}
