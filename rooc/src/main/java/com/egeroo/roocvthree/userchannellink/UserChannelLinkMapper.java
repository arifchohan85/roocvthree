package com.egeroo.roocvthree.userchannellink;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;





public interface UserChannelLinkMapper {
	
	@Select("SELECT * FROM ms_app_userchannellink ORDER BY id")
    public List<UserChannelLink> findAll();
	
	@Select("SELECT * FROM ms_app_userchannellink WHERE id = #{id}")
    public UserChannelLink findOne(Integer id);
	
	@Select("SELECT * FROM ms_app_userchannellink WHERE userid = #{userid}")
    public List<UserChannelLink> findByUser(Integer userid);
	
	@SelectKey(statement = "currval('id')", keyProperty = "id", before = true , resultType = int.class)
	@Select("Insert into ms_app_userchannellink(userid,userchannelid,userchannelcode"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{userid},#{userchannelid},#{userchannelcode}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public UserChannelLink Save(UserChannelLink userchannellink);
	
	@Select("Update ms_app_userchannellink SET userid=#{userid}"
			+ " ,userchannelid=#{userchannelid}"
			+ " ,userchannelcode=#{userchannelcode}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE id=#{id} "
			+ " RETURNING *")
    public UserChannelLink Update(UserChannelLink userchannellink);
	
	@Select("delete from ms_app_userchannellink WHERE id = #{id} RETURNING * ")
    public UserChannelLink deleteOne(Integer id);

}
