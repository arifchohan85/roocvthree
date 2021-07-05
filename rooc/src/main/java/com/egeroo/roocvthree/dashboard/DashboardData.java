package com.egeroo.roocvthree.dashboard;



import java.util.List;

import org.json.JSONObject;

public class DashboardData {
	
	//private DashboardUser datauser;
	//private DashboardTotalIntent datatotalintent;
	private List<DashboardTopQuestion> datatopquestion;
	private List<DashboardCltTreshold> dataclttreshold;
	//private DashboardMessageUser datamessageuser;
	private List<DashboardChannel> datachannel;
	private List<DashboardIntentTrained> dataintenttrained;
	private DashboardIncomingMessage dataincomingmessage;
	//private DashboardTrain datatrain;
	private List<DashboardAgentPerfomance> dataagentperfomance;
	private List<DashboardKPI> datakpi;

	private int dataUser;
	private int dataTotalintent;
	private int dataTotalintentnew;
	private int dataMessageuser;
	private int dataTrain;
	private int dataQueue;
	private int dataHandle;
	
	/*public DashboardUser getDatauser() {
		return datauser;
	}

	public void setDatauser(DashboardUser datauser) {
		this.datauser = datauser;
	}

	public DashboardTotalIntent getDatatotalintent() {
		return datatotalintent;
	}

	public void setDatatotalintent(DashboardTotalIntent datatotalintent) {
		this.datatotalintent = datatotalintent;
	}

	*/
	public List<DashboardTopQuestion> getDatatopquestion() {
		return datatopquestion;
	}

	public void setDatatopquestion(List<DashboardTopQuestion> datatopquestion) {
		this.datatopquestion = datatopquestion;
	}

	public List<DashboardCltTreshold> getDataclttreshold() {
		return dataclttreshold;
	}

	public void setDataclttreshold(List<DashboardCltTreshold> dataclttreshold) {
		this.dataclttreshold = dataclttreshold;
	}

	/*
	public DashboardMessageUser getDatamessageuser() {
		return datamessageuser;
	}

	public void setDatamessageuser(DashboardMessageUser datamessageuser) {
		this.datamessageuser = datamessageuser;
	}

	*/

	public List<DashboardChannel> getDatachannel() {
		return datachannel;
	}

	public void setDatachannel(List<DashboardChannel> datachannel) {
		this.datachannel = datachannel;
	}

	public List<DashboardIntentTrained> getDataintenttrained() {
		return dataintenttrained;
	}

	public void setDataintenttrained(List<DashboardIntentTrained> dataintenttrained) {
		this.dataintenttrained = dataintenttrained;
	}

	public DashboardIncomingMessage getDataincomingmessage() {
		return dataincomingmessage;
	}

	public void setDataincomingmessage(DashboardIncomingMessage dataincomingmessage) {
		this.dataincomingmessage = dataincomingmessage;
	}

	/*
	public DashboardTrain getDatatrain() {
		return datatrain;
	}

	public void setDatatrain(DashboardTrain datatrain) {
		this.datatrain = datatrain;
	}

	*/

	public List<DashboardAgentPerfomance> getDataagentperfomance() {
		return dataagentperfomance;
	}

	public void setDataagentperfomance(List<DashboardAgentPerfomance> dataagentperfomance) {
		this.dataagentperfomance = dataagentperfomance;
	}

	public List<DashboardKPI> getDatakpi() {
		return datakpi;
	}

	public void setDatakpi(List<DashboardKPI> datakpi) {
		this.datakpi = datakpi;
	}

	/*private JSONObject data;

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}*/


	public int getDataUser() {
		return dataUser;
	}

	public void setDataUser(int dataUser) {
		this.dataUser = dataUser;
	}

	public int getDataTotalintent() {
		return dataTotalintent;
	}

	public void setDataTotalintent(int dataTotalintent) {
		this.dataTotalintent = dataTotalintent;
	}

	public int getDataMessageuser() {
		return dataMessageuser;
	}

	public void setDataMessageuser(int dataMessageuser) {
		this.dataMessageuser = dataMessageuser;
	}

	public int getDataTrain() {
		return dataTrain;
	}

	public void setDataTrain(int dataTrain) {
		this.dataTrain = dataTrain;
	}

	public int getDataTotalintentnew() {
		return dataTotalintentnew;
	}

	public void setDataTotalintentnew(int dataTotalintentnew) {
		this.dataTotalintentnew = dataTotalintentnew;
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
}
