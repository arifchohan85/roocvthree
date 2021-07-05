package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardKPI implements Serializable{
	
	private int kpiid;
	private String kpi;
	private float percentage;
	public int getKpiid() {
		return kpiid;
	}
	public void setKpiid(int kpiid) {
		this.kpiid = kpiid;
	}
	public String getKpi() {
		return kpi;
	}
	public void setKpi(String kpi) {
		this.kpi = kpi;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	
	

}
