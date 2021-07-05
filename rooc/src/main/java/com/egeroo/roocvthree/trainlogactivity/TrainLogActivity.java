package com.egeroo.roocvthree.trainlogactivity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "tr_eng_trainlogactivity" )
public class TrainLogActivity  extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int trainlogactivityid;
	private int interactionid;
	private String activityname;
	private int expectedintentidbefore;
	private int expectedintentidafter;
	
	private double confidencelevel;

	public int getTrainlogactivityid() {
		return trainlogactivityid;
	}

	public void setTrainlogactivityid(int trainlogactivityid) {
		this.trainlogactivityid = trainlogactivityid;
	}

	public int getInteractionid() {
		return interactionid;
	}

	public void setInteractionid(int interactionid) {
		this.interactionid = interactionid;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public int getExpectedintentidbefore() {
		return expectedintentidbefore;
	}

	public void setExpectedintentidbefore(int expectedintentidbefore) {
		this.expectedintentidbefore = expectedintentidbefore;
	}

	public int getExpectedintentidafter() {
		return expectedintentidafter;
	}

	public void setExpectedintentidafter(int expectedintentidafter) {
		this.expectedintentidafter = expectedintentidafter;
	}

	public double getConfidencelevel() {
		return confidencelevel;
	}

	public void setConfidencelevel(double confidencelevel) {
		this.confidencelevel = confidencelevel;
	}
	
	
	
	

}

