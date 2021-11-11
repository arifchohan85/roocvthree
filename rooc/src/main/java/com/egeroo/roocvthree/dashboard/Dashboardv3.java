package com.egeroo.roocvthree.dashboard;


import com.egeroo.roocvthree.core.util.ResultData;


public class Dashboardv3{
	private int dataUser;
	private int dataTotalIntent;
	private int dataTotalNewIntent;
	private int dataQueue;
	private int dataHandle;
	private int dataMessage;
	private ResultData dataChannels;
	private ResultData dataIncomingMessage;
	private ResultData dataKpi;
	
	public int getDataTotalIntent() {
		return dataTotalIntent;
	}
	public void setDataTotalIntent(int dataTotalIntent) {
		this.dataTotalIntent = dataTotalIntent;
	}
	public int getDataTotalNewIntent() {
		return dataTotalNewIntent;
	}
	public void setDataTotalNewIntent(int dataTotalNewIntent) {
		this.dataTotalNewIntent = dataTotalNewIntent;
	}
	public int getDataQueue() {
		return dataQueue;
	}
	public void setDataQueue(int dataQueue) {
		this.dataQueue = dataQueue;
	}
	public int getDataHandle() {
		return dataHandle;
	}
	public void setDataHandle(int dataHandle) {
		this.dataHandle = dataHandle;
	}
	public int getDataUser() {
		return dataUser;
	}
	public void setDataUser(int dataUser) {
		this.dataUser = dataUser;
	}
	public int getDataMessage() {
		return dataMessage;
	}
	public void setDataMessage(int dataMessage) {
		this.dataMessage = dataMessage;
	}
	public ResultData getDataChannels() {
		return dataChannels;
	}
	public void setDataChannels(ResultData dataChannels) {
		this.dataChannels = dataChannels;
	}
	public ResultData getDataIncomingMessage() {
		return dataIncomingMessage;
	}
	public void setDataIncomingMessage(ResultData dataIncomingMessage) {
		this.dataIncomingMessage = dataIncomingMessage;
	}
	public ResultData getDataKpi() {
		return dataKpi;
	}
	public void setDataKpi(ResultData dataKpi) {
		this.dataKpi = dataKpi;
	}

}
