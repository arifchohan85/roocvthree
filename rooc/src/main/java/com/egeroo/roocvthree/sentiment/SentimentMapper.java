package com.egeroo.roocvthree.sentiment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;



@Repository
@Mapper
public interface SentimentMapper {
	
	@Select("SELECT * FROM ms_eng_sentiment ORDER BY sentimentid")
    public List<Sentiment> findAll();
	
	@Select("SELECT * FROM ms_eng_sentiment WHERE sentimentid = #{sentimentid}")
    public Sentiment findOne(Integer sentimentid);
	
	@SelectKey(statement = "currval('sentimentid')", keyProperty = "sentimentid", before = true , resultType = int.class)
	@Select("Insert into ms_eng_sentiment(sentimentname) VALUES (#{sentimentname}) ")
    public void Save(Sentiment sentiment);
	
	@Select("Update ms_eng_sentiment SET sentimentname=#{sentimentname} WHERE sentimentid=#{sentimentid} ")
    public void Update(Sentiment sentiment);

}
