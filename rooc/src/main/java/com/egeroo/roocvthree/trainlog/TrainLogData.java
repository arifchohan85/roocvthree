package com.egeroo.roocvthree.trainlog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "tr_eng_trainlogdata" )
public class TrainLogData  extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
	private int trainlogdataid;
	
    private int trainlogid;
	private int interactionid;
	private String intentname;
	private String answerintentname;
	private double confidencelevelbefore;
	private double confidencelevelafter;
	private String status;
	
	public int getTrainlogid() {
		return trainlogid;
	}
	public void setTrainlogid(int trainlogid) {
		this.trainlogid = trainlogid;
	}
	public int getInteractionid() {
		return interactionid;
	}
	public void setInteractionid(int interactionid) {
		this.interactionid = interactionid;
	}
	public int getTrainlogdataid() {
		return trainlogdataid;
	}
	public void setTrainlogdataid(int trainlogdataid) {
		this.trainlogdataid = trainlogdataid;
	}
	public String getIntentname() {
		return intentname;
	}
	public void setIntentname(String intentname) {
		this.intentname = intentname;
	}
	public String getAnswerintentname() {
		return answerintentname;
	}
	public void setAnswerintentname(String answerintentname) {
		this.answerintentname = answerintentname;
	}
	public double getConfidencelevelbefore() {
		return confidencelevelbefore;
	}
	public void setConfidencelevelbefore(double confidencelevelbefore) {
		this.confidencelevelbefore = confidencelevelbefore;
	}
	public double getConfidencelevelafter() {
		return confidencelevelafter;
	}
	public void setConfidencelevelafter(double confidencelevelafter) {
		this.confidencelevelafter = confidencelevelafter;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
