package com.ffisherr.lbg;

import java.util.Date;

public class Message_Response {
    private String status;
    private Integer id;
    private String dt;
    private Integer sender_id;
    private String message_text;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }


    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }
    public Message_Response(int id,String dt,int sender_id,String message_text) {
        this.id = id;
        this.dt = dt;
        this.sender_id = sender_id;
        this.message_text = message_text;
    }


}
