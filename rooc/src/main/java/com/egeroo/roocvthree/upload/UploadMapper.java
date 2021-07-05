package com.egeroo.roocvthree.upload;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;



public interface UploadMapper {
	
	@Select("SELECT ul.*,up.username as username FROM ms_app_upload ul "
			+ " inner join ms_app_userprofile up "
			+ " on ul.createdby=up.userid "
			+ " ORDER BY uploadid")
    public List<Upload> findAll();
	
	@Select("SELECT ul.*,up.username as username FROM ms_app_upload ul "
			+ " inner join ms_app_userprofile up "
			+ " on ul.createdby=up.userid "
			+ " WHERE uploadid = #{uploadid}")
    public Upload findOne(Integer uploadid);
	
	@SelectKey(statement = "currval('uploadid')", keyProperty = "uploadid", before = true , resultType = int.class)
	@Select("Insert into ms_app_upload(uploadfor,uploadfile,description"
			+ " ,success,failed"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{uploadfor},#{uploadfile},#{description}"
			+ " ,#{success},#{failed}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public Upload Save(Upload upload);
	
	@Select("Update ms_app_upload SET uploadfor=#{uploadfor}"
			+ " ,uploadfile=#{uploadfile}"
			+ " ,description=#{description}"
			+ " ,success=#{success}"
			+ " ,failed=#{failed}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE uploadid=#{uploadid} "
			+ " RETURNING *")
    public Upload Update(Upload upload);

}
