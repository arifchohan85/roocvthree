package com.egeroo.roocvthree.interaction;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.egeroo.roocvthree.core.base.Base;

public class InteractionChathistory implements Serializable {


    private int interactionid;

    private String intentname;


    private String expectedintentname;

    private String question;

    private int minconfidence;


    private String channel;
    private String answerby;

    private int roomid;
    private int chatid;


    private double confidencelevel;



    private String answerintentname;

    private String customername;


    private int ismanual;

    private Timestamp createdTime;
    private int createdBy;


    private String anyid;


    public int getInteractionid() {
        return interactionid;
    }

    public void setInteractionid(int interactionid) {
        this.interactionid = interactionid;
    }



    public String getIntentname() {
        return intentname;
    }

    public void setIntentname(String intentname) {
        this.intentname = intentname;
    }



    public String getExpectedintentname() {
        return expectedintentname;
    }

    public void setExpectedintentname(String expectedintentname) {
        this.expectedintentname = expectedintentname;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerby() {
        return answerby;
    }

    public void setAnswerby(String answerby) {
        this.answerby = answerby;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public double getConfidencelevel() {
        return confidencelevel;
    }

    public void setConfidencelevel(double confidencelevel) {
        this.confidencelevel = confidencelevel;
    }



    public String getAnswerintentname() {
        return answerintentname;
    }

    public void setAnswerintentname(String answerintentname) {
        this.answerintentname = answerintentname;
    }



    public String getCustomername() {
        return customername;
    }



    public int getMinconfidence() {
        return minconfidence;
    }

    public void setMinconfidence(int minconfidence) {
        this.minconfidence = minconfidence;
    }



    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }



    public int getIsmanual() {
        return ismanual;
    }

    public void setIsmanual(int ismanual) {
        this.ismanual = ismanual;
    }

    public Timestamp getCreatedtime() {
        return this.createdTime;
    }
    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedtime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
    /**
     * @return the createdBy
     */
    public int getCreatedby() {
        return this.createdBy;
    }
    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedby(int createdBy) {
        this.createdBy = createdBy;
    }


    public String getAnyid() {
        return anyid;
    }

    public void setAnyid(String anyid) {
        this.anyid = anyid;
    }


}
