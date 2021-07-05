package com.egeroo.roocvthree.sentiment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "ms_eng_sentiment" )
public class Sentiment  implements Serializable {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int sentimentid;
	
	@Column(name = "sentimentname", length = 255, nullable = false)
	private String sentimentname;
	
	public int getSentimentid() {
        return sentimentid;
    }
    
    public void setSentimentid(int Sentimentid) {
        this.sentimentid = Sentimentid;
    }
    
    public String getSentimentname() {
        return sentimentname;
    }
    
    public void setSentimentname(String Sentimentname) {
        this.sentimentname = Sentimentname;
    }

}
