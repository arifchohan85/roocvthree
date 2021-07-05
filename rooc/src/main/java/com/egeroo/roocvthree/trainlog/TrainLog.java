package com.egeroo.roocvthree.trainlog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "tr_eng_trainlog" )
public class TrainLog  extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int trainlogid;
	private int totaldata;
	
	public int getTrainlogid() {
		return trainlogid;
	}
	public void setTrainlogid(int trainlogid) {
		this.trainlogid = trainlogid;
	}
	public int getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(int totaldata) {
		this.totaldata = totaldata;
	}
	
	

}
