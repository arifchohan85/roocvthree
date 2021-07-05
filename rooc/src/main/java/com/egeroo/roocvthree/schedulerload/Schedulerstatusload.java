package com.egeroo.roocvthree.schedulerload;



import com.egeroo.roocvthree.core.base.Base;



@SuppressWarnings("serial")
public class Schedulerstatusload extends Base{
	
	private int schedulerid;
    private String status;
    private String name;
    
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    public int getSchedulerid() {
		return schedulerid;
	}
	public void setSchedulerid(int schedulerid) {
		this.schedulerid = schedulerid;
	}
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
