package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;
import java.util.Date;

public class Dashboard implements Serializable {
	
	private int totaluser;
	
	private int totalintentnew;
	private int totalintent;
	
	private Date datefrom;
	private Date dateto;
	
	private int setperiod;
	
	private int fn_user;
	
	//private Date periode;
	private String periode;
	private int totalanswer;
	private int totalanswerbybot;
	private int totalanswerbyagent;
	private int totalnotanswer;
	
	private int topintentid;
	private String topquestion;
	
	private int fn_messageuser;
	
	private String channelname;
	private int totalchat;
	
	private String intentname;
	private int totalquestion;
	
	private int fn_trained;
	
	private int jan; 
	private int feb; 
	private int mar; 
	private int apr; 
	private int may; 
	private int jun; 
	private int jul;
	private int aug; 
	private int sep; 
	private int oct; 
	private int nov; 
	private int des; 
	
	private int totaljan; 
	private int totalfeb; 
	private int totalmar; 
	private int totalapr; 
	private int totalmay; 
	private int totaljun; 
	private int totaljul;
	private int totalaug; 
	private int totalsep; 
	private int totaloct; 
	private int totalnov; 
	private int totaldes; 
	
	private int totalchannel; 
	private int portion; 
	
	private int hour1;
	private int hour2;
	private int hour3;
	private int hour4;
	private int hour5;
	private int hour6;
	private int hour7;
	private int hour8;
	private int hour9;
	private int hour10;
	private int hour11;
	private int hour12;
	private int hour13;
	private int hour14;
	private int hour15;
	private int hour16;
	private int hour17;
	private int hour18;
	private int hour19;
	private int hour20;
	
	private int hour21;
	private int hour22;
	private int hour23;
	private int hour24;
	private int hour25;
	private int hour26;
	private int hour27;
	private int hour28;
	private int hour29;
	private int hour30;
	
	private int hour31;
	private int hour32;
	private int hour33;
	private int hour34;
	private int hour35;
	private int hour36;
	private int hour37;
	private int hour38;
	private int hour39;
	private int hour40;
	
	private int hour41;
	private int hour42;
	private int hour43;
	private int hour44;
	private int hour45;
	private int hour46;
	private int hour47;
	private int hour48;
	private int hour49;
	private int hour50;
	
	private int agentid;
	private String agentname;
	
	private int kpiid;
	private String kpi;
	private float percentage;
	
	public int getFn_trained() {
		return fn_trained;
	}

	public void setFn_trained(int fn_trained) {
		this.fn_trained = fn_trained;
	}

	public String getIntentname() {
		return intentname;
	}

	public void setIntentname(String intentname) {
		this.intentname = intentname;
	}

	public int getTotalquestion() {
		return totalquestion;
	}

	public void setTotalquestion(int totalquestion) {
		this.totalquestion = totalquestion;
	}

	public int getTotaluser() {
        return totaluser;
    }
    
    public void setTotaluser(int Totaluser) {
        this.totaluser = Totaluser;
    }
	
    
    public void setTotalintentnew(int Totalintentnew) {
        this.totalintentnew = Totalintentnew;
    }
    
    public int getTotalintentnew() {
        return totalintentnew;
    }
    
    public void setTotalintent(int Totalintent) {
        this.totalintent = Totalintent;
    }
    
    public int getTotalintent() {
        return totalintent;
    }
    
    public void setDatefrom(Date Datefrom) {
        this.datefrom = Datefrom;
    }
    
    public Date getDatefrom() {
        return datefrom;
    }
    
    public void setDateto(Date Dateto) {
        this.dateto = Dateto;
    }
    
    public Date getDateto() {
        return dateto;
    }
    
    public void setSetperiod(int Setperiod) {
        this.setperiod = Setperiod;
    }
    
    public int getPeriod() {
        return setperiod;
    }
    
    public void setFn_user(int Fn_user) {
        this.fn_user = Fn_user;
    }
    
    public int getFn_user() {
        return fn_user;
    }
    
    /*
    public void setPeriode(Date Periode) {
        this.periode = Periode;
    }
    
    public Date getPeriode() {
        return periode;
    }
    */
    
    public void setPeriode(String Periode) {
        this.periode = Periode;
    }
    
    public String getPeriode() {
        return periode;
    }
    
    public void setTotalanswer(int Totalanswer) {
        this.totalanswer = Totalanswer;
    }
    
    public int getTotalanswer() {
        return totalanswer;
    }
    
    public void setTotalanswerbybot(int Totalanswerbybot) {
        this.totalanswerbybot = Totalanswerbybot;
    }
    
    public int getTotalanswerbybot() {
        return totalanswerbybot;
    }
    
    public void setTotalanswerbyagent(int Totalanswerbyagent) {
        this.totalanswerbyagent = Totalanswerbyagent;
    }
    
    public int getTotalanswerbyagent() {
        return totalanswerbyagent;
    }
    
    public void setTotalnotanswer(int Totalnotanswer) {
        this.totalnotanswer = Totalnotanswer;
    }
    
    public int getTotalnotanswer() {
        return totalnotanswer;
    }
    
    public void setTopintentid(int Topintentid) {
        this.topintentid = Topintentid;
    }
    
    public int getTopintentid() {
        return topintentid;
    }
    
    public void setTopquestion(String Topquestion) {
        this.topquestion = Topquestion;
    }
    
    public String getTopquestion() {
        return topquestion;
    }
    
    public void setFn_messageuser(int Fn_messageuser) {
        this.fn_messageuser = Fn_messageuser;
    }
    
    public int getFn_messageuser() {
        return fn_messageuser;
    }
    
    public void setChannelname(String Channelname) {
        this.channelname = Channelname;
    }
    
    public String getChannelname() {
        return channelname;
    }
    
    public void setTotalchat(int Totalchat) {
        this.totalchat = Totalchat;
    }
    
    public int getTotalchat() {
        return totalchat;
    }

	public int getJan() {
		return jan;
	}

	public void setJan(int jan) {
		this.jan = jan;
	}

	public int getFeb() {
		return feb;
	}

	public void setFeb(int feb) {
		this.feb = feb;
	}

	public int getMar() {
		return mar;
	}

	public void setMar(int mar) {
		this.mar = mar;
	}

	public int getApr() {
		return apr;
	}

	public void setApr(int apr) {
		this.apr = apr;
	}

	public int getMay() {
		return may;
	}

	public void setMay(int may) {
		this.may = may;
	}

	public int getJun() {
		return jun;
	}

	public void setJun(int jun) {
		this.jun = jun;
	}

	public int getJul() {
		return jul;
	}

	public void setJul(int jul) {
		this.jul = jul;
	}

	public int getAug() {
		return aug;
	}

	public void setAug(int aug) {
		this.aug = aug;
	}

	public int getSep() {
		return sep;
	}

	public void setSep(int sep) {
		this.sep = sep;
	}

	public int getOct() {
		return oct;
	}

	public void setOct(int oct) {
		this.oct = oct;
	}

	public int getNov() {
		return nov;
	}

	public void setNov(int nov) {
		this.nov = nov;
	}

	public int getDes() {
		return des;
	}

	public void setDes(int des) {
		this.des = des;
	}

	public int getTotaljan() {
		return totaljan;
	}

	public void setTotaljan(int totaljan) {
		this.totaljan = totaljan;
	}

	public int getTotalfeb() {
		return totalfeb;
	}

	public void setTotalfeb(int totalfeb) {
		this.totalfeb = totalfeb;
	}

	public int getTotalmar() {
		return totalmar;
	}

	public void setTotalmar(int totalmar) {
		this.totalmar = totalmar;
	}

	public int getTotalapr() {
		return totalapr;
	}

	public void setTotalapr(int totalapr) {
		this.totalapr = totalapr;
	}

	public int getTotalmay() {
		return totalmay;
	}

	public void setTotalmay(int totalmay) {
		this.totalmay = totalmay;
	}

	public int getTotaljun() {
		return totaljun;
	}

	public void setTotaljun(int totaljun) {
		this.totaljun = totaljun;
	}

	public int getTotaljul() {
		return totaljul;
	}

	public void setTotaljul(int totaljul) {
		this.totaljul = totaljul;
	}

	public int getTotalaug() {
		return totalaug;
	}

	public void setTotalaug(int totalaug) {
		this.totalaug = totalaug;
	}

	public int getTotalsep() {
		return totalsep;
	}

	public void setTotalsep(int totalsep) {
		this.totalsep = totalsep;
	}

	public int getTotaloct() {
		return totaloct;
	}

	public void setTotaloct(int totaloct) {
		this.totaloct = totaloct;
	}

	public int getTotalnov() {
		return totalnov;
	}

	public void setTotalnov(int totalnov) {
		this.totalnov = totalnov;
	}

	public int getTotaldes() {
		return totaldes;
	}

	public void setTotaldes(int totaldes) {
		this.totaldes = totaldes;
	}

	public int getTotalchannel() {
		return totalchannel;
	}

	public void setTotalchannel(int totalchannel) {
		this.totalchannel = totalchannel;
	}

	public int getPortion() {
		return portion;
	}

	public void setPortion(int portion) {
		this.portion = portion;
	}

	public int getSetperiod() {
		return setperiod;
	}

	public int getHour1() {
		return hour1;
	}

	public void setHour1(int hour1) {
		this.hour1 = hour1;
	}

	public int getHour2() {
		return hour2;
	}

	public void setHour2(int hour2) {
		this.hour2 = hour2;
	}

	public int getHour3() {
		return hour3;
	}

	public void setHour3(int hour3) {
		this.hour3 = hour3;
	}

	public int getHour4() {
		return hour4;
	}

	public void setHour4(int hour4) {
		this.hour4 = hour4;
	}

	public int getHour5() {
		return hour5;
	}

	public void setHour5(int hour5) {
		this.hour5 = hour5;
	}

	public int getHour6() {
		return hour6;
	}

	public void setHour6(int hour6) {
		this.hour6 = hour6;
	}

	public int getHour7() {
		return hour7;
	}

	public void setHour7(int hour7) {
		this.hour7 = hour7;
	}

	public int getHour8() {
		return hour8;
	}

	public void setHour8(int hour8) {
		this.hour8 = hour8;
	}

	public int getHour9() {
		return hour9;
	}

	public void setHour9(int hour9) {
		this.hour9 = hour9;
	}

	public int getHour10() {
		return hour10;
	}

	public void setHour10(int hour10) {
		this.hour10 = hour10;
	}

	public int getHour11() {
		return hour11;
	}

	public void setHour11(int hour11) {
		this.hour11 = hour11;
	}

	public int getHour12() {
		return hour12;
	}

	public void setHour12(int hour12) {
		this.hour12 = hour12;
	}

	public int getHour13() {
		return hour13;
	}

	public void setHour13(int hour13) {
		this.hour13 = hour13;
	}

	public int getHour14() {
		return hour14;
	}

	public void setHour14(int hour14) {
		this.hour14 = hour14;
	}

	public int getHour15() {
		return hour15;
	}

	public void setHour15(int hour15) {
		this.hour15 = hour15;
	}

	public int getHour16() {
		return hour16;
	}

	public void setHour16(int hour16) {
		this.hour16 = hour16;
	}

	public int getHour17() {
		return hour17;
	}

	public void setHour17(int hour17) {
		this.hour17 = hour17;
	}

	public int getHour18() {
		return hour18;
	}

	public void setHour18(int hour18) {
		this.hour18 = hour18;
	}

	public int getHour19() {
		return hour19;
	}

	public void setHour19(int hour19) {
		this.hour19 = hour19;
	}

	public int getHour20() {
		return hour20;
	}

	public void setHour20(int hour20) {
		this.hour20 = hour20;
	}

	public int getHour21() {
		return hour21;
	}

	public void setHour21(int hour21) {
		this.hour21 = hour21;
	}

	public int getHour22() {
		return hour22;
	}

	public void setHour22(int hour22) {
		this.hour22 = hour22;
	}

	public int getHour23() {
		return hour23;
	}

	public void setHour23(int hour23) {
		this.hour23 = hour23;
	}

	public int getHour24() {
		return hour24;
	}

	public void setHour24(int hour24) {
		this.hour24 = hour24;
	}

	public int getHour25() {
		return hour25;
	}

	public void setHour25(int hour25) {
		this.hour25 = hour25;
	}

	public int getHour26() {
		return hour26;
	}

	public void setHour26(int hour26) {
		this.hour26 = hour26;
	}

	public int getHour27() {
		return hour27;
	}

	public void setHour27(int hour27) {
		this.hour27 = hour27;
	}

	public int getHour28() {
		return hour28;
	}

	public void setHour28(int hour28) {
		this.hour28 = hour28;
	}

	public int getHour29() {
		return hour29;
	}

	public void setHour29(int hour29) {
		this.hour29 = hour29;
	}

	public int getHour30() {
		return hour30;
	}

	public void setHour30(int hour30) {
		this.hour30 = hour30;
	}

	public int getHour31() {
		return hour31;
	}

	public void setHour31(int hour31) {
		this.hour31 = hour31;
	}

	public int getHour32() {
		return hour32;
	}

	public void setHour32(int hour32) {
		this.hour32 = hour32;
	}

	public int getHour33() {
		return hour33;
	}

	public void setHour33(int hour33) {
		this.hour33 = hour33;
	}

	public int getHour34() {
		return hour34;
	}

	public void setHour34(int hour34) {
		this.hour34 = hour34;
	}

	public int getHour35() {
		return hour35;
	}

	public void setHour35(int hour35) {
		this.hour35 = hour35;
	}

	public int getHour36() {
		return hour36;
	}

	public void setHour36(int hour36) {
		this.hour36 = hour36;
	}

	public int getHour37() {
		return hour37;
	}

	public void setHour37(int hour37) {
		this.hour37 = hour37;
	}

	public int getHour38() {
		return hour38;
	}

	public void setHour38(int hour38) {
		this.hour38 = hour38;
	}

	public int getHour39() {
		return hour39;
	}

	public void setHour39(int hour39) {
		this.hour39 = hour39;
	}

	public int getHour40() {
		return hour40;
	}

	public void setHour40(int hour40) {
		this.hour40 = hour40;
	}

	public int getHour41() {
		return hour41;
	}

	public void setHour41(int hour41) {
		this.hour41 = hour41;
	}

	public int getHour42() {
		return hour42;
	}

	public void setHour42(int hour42) {
		this.hour42 = hour42;
	}

	public int getHour43() {
		return hour43;
	}

	public void setHour43(int hour43) {
		this.hour43 = hour43;
	}

	public int getHour44() {
		return hour44;
	}

	public void setHour44(int hour44) {
		this.hour44 = hour44;
	}

	public int getHour45() {
		return hour45;
	}

	public void setHour45(int hour45) {
		this.hour45 = hour45;
	}

	public int getHour46() {
		return hour46;
	}

	public void setHour46(int hour46) {
		this.hour46 = hour46;
	}

	public int getHour47() {
		return hour47;
	}

	public void setHour47(int hour47) {
		this.hour47 = hour47;
	}

	public int getHour48() {
		return hour48;
	}

	public void setHour48(int hour48) {
		this.hour48 = hour48;
	}

	public int getHour49() {
		return hour49;
	}

	public void setHour49(int hour49) {
		this.hour49 = hour49;
	}

	public int getHour50() {
		return hour50;
	}

	public void setHour50(int hour50) {
		this.hour50 = hour50;
	}

	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

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
