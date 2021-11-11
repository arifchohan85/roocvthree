package com.egeroo.roocvthree.directory;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;






public interface DirectoryMapper {
	
	@Select("SELECT * FROM ms_eng_directory WHERE active=1 ORDER BY directoryid")
    public List<Directory> findAll();
	
	@Select("SELECT directoryid,name FROM ms_eng_directory WHERE active=1 ORDER BY directoryid")
    public List<Directory> findDirectory();
	
	@Select("SELECT directoryid,name FROM ms_eng_directory WHERE active=1 AND categorymode='ENUM' ORDER BY directoryid")
    public List<Directory> findDirectoryenum();
	
	@Select("SELECT * FROM ms_eng_directory WHERE directoryid = #{directoryid}")
    public Directory findOne(Integer directoryid);
	
	@Select("SELECT * FROM ms_eng_directory WHERE faq = #{faq}")
    public Directory findOnebyfaq(String faq);
	
	@Select("SELECT * FROM ms_eng_directory WHERE name = #{directoryname} ORDER BY directoryid asc limit 1")
    public Directory findDirectorybyname(String directoryname);
	
	@Select("SELECT * FROM ms_eng_directory WHERE name = #{parentname} ORDER BY directoryid asc limit 1")
    public Directory findParentbyname(String parentname);
	
	@Select("SELECT * FROM ms_eng_directory WHERE name = #{directoryname} AND parentid=#{parentid} ORDER BY directoryid asc limit 1")
    public Directory findDirectorybynameandparentid(@Param("directoryname") String directoryname,@Param("parentid") Integer parentid);
	
	/*
	@Select("delete from ms_eng_directory WHERE directoryid = #{directoryid} RETURNING * ")
    public Directory deleteOne(Integer directoryid);
    */
	/*@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" directoryid,\n" + 
			" parentid,\n" + 
			" name,\n" + 
			" categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory\n" + 
			" WHERE\n" + 
			" (directoryid=#{directoryid} or parentid=#{directoryid})\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.directoryid,\n" + 
			" e.parentid,\n" + 
			" e.name,\n" + 
			" e.categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.directoryid = e.parentid\n" + 
			" ) \n" + 
			" delete \n" + 
			" from\n" + 
			" ms_eng_directory d \n" + 
			" USING \n" + 
			" subordinates so\n" + 
			" WHERE d.directoryid = so.directoryid\n" + 
			" "
			+ " ")
    public List<Directory> deleteOne(Integer directoryid,String Token);*/
	@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" directoryid,\n" + 
			" parentid,\n" + 
			" name,\n" + 
			" categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory\n" + 
			" WHERE\n" + 
			" (directoryid=#{directoryid} or parentid=#{directoryid})\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.directoryid,\n" + 
			" e.parentid,\n" + 
			" e.name,\n" + 
			" e.categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.directoryid = e.parentid\n" + 
			" ) \n" + 
			" delete \n" + 
			" from\n" + 
			" ms_eng_directory d \n" + 
			" USING \n" + 
			" subordinates so\n" + 
			" WHERE d.directoryid = so.directoryid\n" + 
			" "
			+ " ")
    public List<Directory> deleteOne(@Param("directoryid") Integer directoryid,@Param("username") String Token);
	
	/*@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" directoryid,\n" + 
			" parentid,\n" + 
			" name,\n" + 
			" categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory\n" + 
			" WHERE\n" + 
			" (directoryid=#{directoryid} or parentid=#{directoryid})\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.directoryid,\n" + 
			" e.parentid,\n" + 
			" e.name,\n" + 
			" e.categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.directoryid = e.parentid\n" + 
			" ) \n" + 
			" delete \n" + 
			" from\n" + 
			" ms_eng_directory d \n" + 
			" USING \n" + 
			" subordinates so\n" + 
			" WHERE d.directoryid = so.directoryid\n" + 
			" "
			+ " ")
    public List<Directory> deleteRecursive(Integer directoryid,String Token);*/
	@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" directoryid,\n" + 
			" parentid,\n" + 
			" name,\n" + 
			" categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory\n" + 
			" WHERE\n" + 
			" (directoryid=#{directoryid} or parentid=#{directoryid})\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.directoryid,\n" + 
			" e.parentid,\n" + 
			" e.name,\n" + 
			" e.categorymode\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.directoryid = e.parentid\n" + 
			" ) \n" + 
			" delete \n" + 
			" from\n" + 
			" ms_eng_directory d \n" + 
			" USING \n" + 
			" subordinates so\n" + 
			" WHERE d.directoryid = so.directoryid\n" + 
			" "
			+ " ")
    public List<Directory> deleteRecursive(@Param("directoryid") Integer directoryid,@Param("Token") String Token);
	
	@Select("select count(*) as countparent\n" + 
			" from ms_eng_directory\n" + 
			" where (parentid=0 or parentid isnull);")
    public Directory findCountrootparent();
	
	@Select("select count(*) as countchild\n" + 
			" from ms_eng_directory\n" + 
			" where parentid=#{directoryid} ;")
    public Directory findCountchild(Integer directoryid);
	
	@Select("WITH RECURSIVE menu_directory_tree( \"directoryid\", \"parentid\", \"name\", \"description\") AS (\n" + 
			"  \n" + 
			"  SELECT c.directoryid,c.parentid,c.name,c.description, json '[]' children\n" + 
			"  FROM ms_eng_directory c\n" + 
			"  WHERE NOT EXISTS(SELECT * FROM ms_eng_directory AS hypothetic_child WHERE hypothetic_child.parentid = c.directoryid)\n" + 
			"\n" + 
			"  UNION ALL\n" + 
			"\n" + 
			"  \n" + 
			"  SELECT (parent).directoryid,(parent).parentid,(parent).name,(parent).description, json_agg(child) AS \"children\"\n" + 
			"  FROM (\n" + 
			"    SELECT parent, child\n" + 
			"    FROM menu_directory_tree AS child\n" + 
			"    JOIN ms_eng_directory parent ON parent.directoryid = child.parentid\n" + 
			"  ) branch\n" + 
			"  GROUP BY branch.parent\n" + 
			" )\n" + 
			" SELECT replace(replace(replace(replace(json_agg(t)::jsonb::text,'\"children\": [],'::text,' '::text)::text,'\"directoryid\":'::text,'\"theId\":'::text)::text,'\"description\":'::text,'\"subtitle\":'::text)::text,'\"name\":'::text,'\"title\":'::text)::jsonb as children\n" + 
			" FROM menu_directory_tree t\n" + 
			" LEFT JOIN ms_eng_directory AS hypothetic_parent ON(hypothetic_parent.directoryid = t.parentid)\n" + 
			" WHERE hypothetic_parent.directoryid IS null ;")
    public Directorylistall findAlldirectory();
	
	/*@Select("select a.directoryid as theId,a.name as title,a.parentid as parentId,'directory' as type  \n" + 
			" from ms_eng_directory a \n" + 
			" where exists (select parentid from ms_eng_directory c where c.directoryid=a.parentid or a.parentid<=0) \n" + 
			" union all \n" + 
			" select b.intentid as theId,b.question as title,b.directoryid as parentid,'intent' as type \n" + 
			" from ms_eng_intent b \n" + 
			" where directoryid in (select d.directoryid from ms_eng_directory d)\n" + 
			" order by theId,parentid ;")
    public List<DirectoryIntent> findAlldirectoryintent();*/
	/*@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" parentid as parentId,\n" + 
			" directoryid as theId,\n" + 
			" name as title,\n" + 
			" categorymode,\n" + 
			" 'directory' as type\n" + 
			" FROM\n" + 
			" ms_eng_directory\n" + 
			" WHERE\n" + 
			" (parentid is null or parentid<=0)\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.parentid as parentId,\n" + 
			" e.directoryid as theId,\n" + 
			" e.name as title,\n" + 
			" e.categorymode,\n" + 
			" 'directory' as type\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.theId = e.parentId\n" + 
			" ) SELECT\n" + 
			" *\n" + 
			" FROM\n" + 
			" subordinates\n" + 
			" union all\n" + 
			" select b.directoryid as parentId,b.intentid as theId,b.question as title,\n" + 
			" so.categorymode as categorymode,'intent' as type\n" + 
			" from ms_eng_intent b\n" + 
			" inner join subordinates so \n" + 
			" on so.theId= b.directoryid\n" + 
			" where directoryid in (select ss.theId from subordinates ss)\n" + 
			" order by theId,parentid \n" + 
			" ;\n" + 
			"  ")
    public List<DirectoryIntent> findAlldirectoryintent();
    */
	
	/*@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" parentid as parentId,\n" + 
			" directoryid as theId,\n" + 
			" name as title,\n" + 
			" categorymode,\n" + 
			" 'directory' as type,\n" + 
			" switching,\n" + 
			" intentid,\n" + 
			" switchingid\n" + 
			" FROM\n" + 
			" ms_eng_directory\n" + 
			" WHERE\n" + 
			" (parentid is null or parentid<=0)\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.parentid as parentId,\n" + 
			" e.directoryid as theId,\n" + 
			" e.name as title,\n" + 
			" e.categorymode,\n" + 
			" 'directory' as type,\n" + 
			" e.switching,\n" + 
			" e.intentid,\n" + 
			" e.switchingid\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.theId = e.parentId\n" + 
			" ) SELECT\n" + 
			" *\n" + 
			" FROM\n" + 
			" subordinates\n" + 
			" union all\n" + 
			" select b.directoryid as parentId,b.intentid as theId,b.question as title,\n" + 
			" so.categorymode as categorymode,'intent' as type,null as switching,null as intentid,\n" + 
			" null as switchingid\n" + 
			" from ms_eng_intent b\n" + 
			" inner join subordinates so \n" + 
			" on so.theId= b.directoryid\n" + 
			" where directoryid in (select ss.theId from subordinates ss)\n" + 
			" order by theId,parentid \n" + 
			" ; ")
    public List<DirectoryIntent> findAlldirectoryintent();
	*/
	
	/*@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" parentid as parentId,\n" + 
			" directoryid as theId,\n" + 
			" name as title,\n" + 
			" categorymode,\n" + 
			" 'directory' as type,\n" + 
			" switching,\n" + 
			" intentid,\n" + 
			" switchingid,\n" + 
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=par.parentid) as grandparentid\n" + 
			" FROM\n" + 
			" ms_eng_directory par\n" + 
			" WHERE\n" + 
			" (parentid is null or parentid<=0)\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.parentid as parentId,\n" + 
			" e.directoryid as theId,\n" + 
			" e.name as title,\n" + 
			" e.categorymode,\n" + 
			" 'directory' as type,\n" + 
			" e.switching,\n" + 
			" e.intentid,\n" + 
			" e.switchingid,\n" + 
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=e.parentid)  as grandparentid\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.theId = e.parentId\n" + 
			" ) SELECT\n" + 
			" *\n" + 
			" FROM\n" + 
			" subordinates\n" + 
			" union all\n" + 
			" select b.directoryid as parentId,b.intentid as theId,b.question as title,\n" + 
			" so.categorymode as categorymode,'intent' as type,null as switching,null as intentid,\n" + 
			" null as switchingid\n" + 
			" ,(select parentid from ms_eng_directory engd where engd.directoryid=b.directoryid)  as grandparentid\n" + 
			" from ms_eng_intent b\n" + 
			" inner join subordinates so \n" + 
			" on so.theId= b.directoryid\n" + 
			" where directoryid in (select ss.theId from subordinates ss)\n" + 
			" order by theId,parentId \n" + 
			" ;\n" + 
			" ")
    public List<DirectoryIntent> findAlldirectoryintent();
	*/
	
	@Select(" WITH RECURSIVE subordinates AS ( \n" + 
			" SELECT \n" + 
			" parentid as parentId, \n" + 
			" directoryid as theId, \n" + 
			" name as title, \n" + 
			" categorymode, \n" + 
			" 'directory' as type, \n" + 
			" switching, \n" + 
			" intentid, \n" + 
			" switchingid, \n" + 
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=par.parentid) as grandparentid, \n" + 
			" faq \n" + 
			" FROM \n" + 
			" ms_eng_directory par \n" + 
			" WHERE \n" + 
			" (parentid is null or parentid<=0) \n" + 
			" UNION \n" + 
			" SELECT \n" + 
			" e.parentid as parentId, \n" + 
			" e.directoryid as theId, \n" + 
			" e.name as title, \n" + 
			" e.categorymode, \n" + 
			" 'directory' as type, \n" + 
			" e.switching, \n" + 
			" e.intentid, \n" + 
			" e.switchingid, \n" + 
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=e.parentid)  as grandparentid, \n" + 
			" e.faq \n" + 
			" FROM \n" + 
			" ms_eng_directory e \n" + 
			" INNER JOIN subordinates s ON s.theId = e.parentId \n" + 
			" ) SELECT \n" + 
			" * \n" + 
			" FROM \n" + 
			" subordinates \n" + 
			" union all \n" + 
			" select b.directoryid as parentId,b.intentid as theId,b.question as title, \n" + 
			" so.categorymode as categorymode,'intent' as type,null as switching,null as intentid, \n" + 
			" null as switchingid \n" + 
			" ,(select parentid from ms_eng_directory engd where engd.directoryid=b.directoryid)  as grandparentid, \n" + 
			" '' as faq \n" + 
			" from ms_eng_intent b \n" + 
			" inner join subordinates so \n" + 
			" on so.theId= b.directoryid \n" + 
			" where directoryid in (select ss.theId from subordinates ss) \n" + 
			" order by theId,parentId \n" + 
			" ; " + 
			" ")
    public List<DirectoryIntent> findAlldirectoryintent();

	/*@Select(" WITH RECURSIVE subordinates AS (\n" +
			" SELECT\n" +
			" (select name from ms_eng_directory d where d.directoryid=parentid) as parent,\n" +
			" parentid as parentId,\n" +
			" directoryid as id,\n" +
			" CASE \n" +
			" when faq is not null\n" +
			" then faq::integer\n" +
			" when faq is null or faq =''\n" +
			" then directoryid\n" +
			" end intentid,\n" +
			" name as text,\n" +
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=par.parentid) as grandparentid,\n" +
			" array(select name from ms_eng_directory d where d.parentid=parentid)::text as childdir\n" +
			" FROM \n" +
			" ms_eng_directory par \n" +
			" WHERE\n" +
			" (parentid is null or parentid<=0)\n" +
			" UNION\n" +
			" SELECT \n" +
			" (select name from ms_eng_directory d1 where d1.directoryid=e.parentid) as parent,\n" +
			" e.parentid as parentId,\n" +
			" e.directoryid as id,\n" +
			" CASE \n" +
			" when e.faq is not null\n" +
			" then e.faq::integer\n" +
			" when e.faq is null or e.faq =''\n" +
			" then e.directoryid\n" +
			" end intentid,\n" +
			" e.name as text, \n" +
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=e.parentid)  as grandparentid,\n" +
			" array(select name from ms_eng_directory d1 where d1.parentid=e.parentid)::text as childdir\n" +
			" FROM\n" +
			" ms_eng_directory e \n" +
			" INNER JOIN subordinates s ON s.id = e.parentId\n" +
			" ) SELECT \n" +
			" *\n" +
			" FROM\n" +
			" subordinates \n" +
			" union all \n" +
			" select \n" +
			" (select name from ms_eng_directory d2 where d2.directoryid=b.directoryid) as parent,\n" +
			" b.directoryid as parentId,\n" +
			" b.intentid as id,\n" +
			" b.intentid as intentid,\n" +
			" b.question as text, \n" +
			" (select parentid from ms_eng_directory engd where engd.directoryid=b.directoryid)  as grandparentid,\n" +
			" array(select name from ms_eng_directory d2 where d2.parentid=b.directoryid)::text as childdir\n" +
			" from ms_eng_intent b\n" +
			" inner join subordinates so \n" +
			" on so.id= b.directoryid\n" +
			" where directoryid in (select ss.id from subordinates ss) \n" +
			" order by id,parentId\n" +
			" ;  " +

			" ")*/
	/*@Select(" WITH RECURSIVE subordinates AS (\n" +
			" SELECT\n" +
			" (select name from ms_eng_directory d where d.directoryid=parentid) as parent,\n" +
			" parentid as parentId,\n" +
			" directoryid as id,\n" +
			" CASE \n" +
			" when faq is not null\n" +
			" then faq::integer\n" +
			" when faq is null or faq =''\n" +
			" then directoryid\n" +
			" end intentid,\n" +
			" name as text,\n" +
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=par.parentid) as grandparentid,\n" +
			" array(select name from ms_eng_directory d where d.parentid=parentid)::text as child,\n" +
			" faq\n" +
			" FROM \n" +
			" ms_eng_directory par \n" +
			" WHERE\n" +
			" (parentid is null or parentid<=0)\n" +
			" UNION\n" +
			" SELECT \n" +
			" (select name from ms_eng_directory d1 where d1.directoryid=e.parentid) as parent,\n" +
			" e.parentid as parentId,\n" +
			" e.directoryid as id,\n" +
			" CASE \n" +
			" when e.faq is not null\n" +
			" then e.faq::integer\n" +
			" when e.faq is null or e.faq =''\n" +
			" then e.directoryid\n" +
			" end intentid,\n" +
			" e.name as text, \n" +
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=e.parentid)  as grandparentid,\n" +
			" array(select name from ms_eng_directory d1 where d1.parentid=e.parentid)::text as child,\n" +
			" e.faq\n" +
			" FROM\n" +
			" ms_eng_directory e \n" +
			" INNER JOIN subordinates s ON s.id = e.parentId\n" +
			"\n" +
			" ) ,x AS\n" +
			" (\n" +
			"    -- anchor:\n" +
			"    SELECT directoryid, \n" +
			" name,\n" +
			" CAST(' # ' As varchar(1000)) As path,\n" +
			" parentid--, [level] = 0\n" +
			"    FROM ms_eng_directory WHERE (parentid IS NULL OR parentid=0)\n" +
			"    UNION ALL\n" +
			"    -- recursive:\n" +
			"    SELECT t.directoryid, \n" +
			" t.name, \n" +
			" CAST(x.path || '/' || x.name As varchar(1000)) As path,\n" +
			" t.parentid--, [level] = x.[level] + 1\n" +
			"    FROM ms_eng_directory AS t  INNER JOIN x\n" +
			"    ON t.parentid = x.directoryid\n" +
			" \n" +
			" )\n" +
			" SELECT \n" +
			" sod.*\n" +
			" --,x.path\n" +
			" ,replace(replace(path,' # /',''),' # ','') as path\n" +
			" FROM\n" +
			" subordinates sod\n" +
			" full join x\n" +
			" on sod.id=x.directoryid\n" +
			" where sod.faq is null\n" +
			" union all \n" +
			" select \n" +
			" (select name from ms_eng_directory d2 where d2.directoryid=b.directoryid) as parent,\n" +
			" b.directoryid as parentId,\n" +
			" b.intentid as id,\n" +
			" b.intentid as intentid,\n" +
			" b.question as text, \n" +
			" (select parentid from ms_eng_directory engd where engd.directoryid=b.directoryid)  as grandparentid,\n" +
			" array(select name from ms_eng_directory d2 where d2.parentid=b.directoryid)::text as child,\n" +
			" b.intentid::text as faq,\n" +
			" replace(replace(path,' # /',''),' # ','') as directorymap\n" +
			" from ms_eng_intent b\n" +
			" inner join subordinates so \n" +
			" on so.id= b.directoryid\n" +
			" full join x\n" +
			" on so.id=x.directoryid\n" +
			" where b.directoryid in (select ss.id from subordinates ss) \n" +
			" order by id,parentId\n" +
			" ;  ")
	public List<DirectoryIntentv3> findAlldirectoryintentv3();*/
	@Select(" WITH RECURSIVE subordinates AS (\n" +
			" SELECT\n" +
			" (select name from ms_eng_directory d where d.directoryid=parentid) as parent,\n" +
			" parentid as parentId,\n" +
			" directoryid as id,\n" +
			" CASE \n" +
			" when faq is not null\n" +
			" then faq::integer\n" +
			" when faq is null or faq =''\n" +
			" then directoryid\n" +
			" end intentid,\n" +
			" name as text,\n" +
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=par.parentid) as grandparentid,\n" +
			" replace(replace(array(select name::text from ms_eng_directory d where d.parentid=parentid)::text,'{','['),'}',']') as child,\n" +
			" faq\n" +
			" FROM \n" +
			" ms_eng_directory par \n" +
			" WHERE\n" +
			" (parentid is null or parentid<=0)\n" +
			" UNION\n" +
			" SELECT \n" +
			" (select name from ms_eng_directory d1 where d1.directoryid=e.parentid) as parent,\n" +
			" e.parentid as parentId,\n" +
			" e.directoryid as id,\n" +
			" CASE \n" +
			" when e.faq is not null\n" +
			" then e.faq::integer\n" +
			" when e.faq is null or e.faq =''\n" +
			" then e.directoryid\n" +
			" end intentid,\n" +
			" e.name as text, \n" +
			" (select engd.parentid from ms_eng_directory engd where engd.directoryid=e.parentid)  as grandparentid,\n" +
			" replace(replace(array(select name::text from ms_eng_directory d1 where d1.parentid=e.parentid)::text,'{','['),'}',']') as child,\n" +
			" e.faq\n" +
			" FROM\n" +
			" ms_eng_directory e \n" +
			" INNER JOIN subordinates s ON s.id = e.parentId\n" +
			" \n" +
			" ) ,x AS\n" +
			"( \n" +
			"    -- anchor:\n" +
			"    SELECT directoryid, \n" +
			" name,\n" +
			" CAST(' # ' As varchar(1000)) As path,\n" +
			" parentid--, [level] = 0\n" +
			"    FROM ms_eng_directory WHERE (parentid IS NULL OR parentid=0)\n" +
			"    UNION ALL\n" +
			"    -- recursive:\n" +
			"    SELECT t.directoryid, \n" +
			" t.name, \n" +
			" CAST(x.path || '/' || x.name As varchar(1000)) As path,\n" +
			" t.parentid--, [level] = x.[level] + 1\n" +
			"    FROM ms_eng_directory AS t  INNER JOIN x\n" +
			"    ON t.parentid = x.directoryid\n" +
			" \n" +
			" )\n" +
			" SELECT \n" +
			" sod.*\n" +
			" --,x.path\n" +
			" ,replace(replace(path,' # /',''),' # ','') as path\n" +
			" FROM\n" +
			" subordinates sod\n" +
			" full join x\n" +
			" on sod.id=x.directoryid\n" +
			" where sod.faq is null\n" +
			" union all \n" +
			" select \n" +
			" (select name from ms_eng_directory d2 where d2.directoryid=b.directoryid) as parent,\n" +
			" b.directoryid as parentId,\n" +
			" b.intentid as id,\n" +
			" b.intentid as intentid,\n" +
			" b.question as text, \n" +
			" (select parentid from ms_eng_directory engd where engd.directoryid=b.directoryid)  as grandparentid,\n" +
			" replace(replace(array(select name::text from ms_eng_directory d2 where d2.parentid=b.directoryid)::text,'{','['),'}',']') as child,\n" +
			" b.intentid::text as faq,\n" +
			" replace(replace(path,' # /',''),' # ','') as directorymap\n" +
			" from ms_eng_intent b\n" +
			" inner join subordinates so \n" +
			" on so.id= b.directoryid\n" +
			" full join x\n" +
			" on so.id=x.directoryid\n" +
			" where b.directoryid in (select ss.id from subordinates ss) \n" +
			" order by id,parentId\n" +
			" ;  ")
	public List<DirectoryIntentv3> findAlldirectoryintentv3();
	
	@SelectKey(statement = "currval('directoryid')", keyProperty = "directoryid", before = true , resultType = int.class)
	@Select("Insert into ms_eng_directory(parentid,categorymode,name,description,question,answer,faq"
			+ ",switching,extendenumcategoryid,tenantid,reticategoryid,active,retvoiceid,isvoicegenerated"
			+ ",retmlid,ismlgenerated"
			+ ",createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ "VALUES (#{parentid},#{categorymode},#{name},#{description},#{question},#{answer}"
			+ ",#{faq},#{switching},#{extendenumcategoryid},#{tenantid}"
			+ ",#{reticategoryid},#{active},#{retvoiceid},#{isvoicegenerated}"
			+ ",#{retmlid},#{ismlgenerated}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING directoryid") //,#{createdBy},#{updateBy}
	public String Save(Directory dir);
	
	@Select("Update ms_eng_directory SET parentid=#{parentid}"
			+ " ,categorymode=#{categorymode}"
			+ " ,name=#{name}"
			+ " ,description=#{description}"
			+ " ,question=#{question}"
			+ " ,answer=#{answer}"
			+ " ,faq=#{faq}"
			+ " ,switching=#{switching}"
			+ " ,intentid=#{intentid}"
			+ " ,switchingid=#{switchingid}"
			+ " ,extendenumcategoryid=#{extendenumcategoryid}"
			+ " ,tenantid=#{tenantid}"
			+ " ,reticategoryid=#{reticategoryid}"
			+ " ,active=#{active}"
			+ " ,retvoiceid=#{retvoiceid}"
			+ " ,isvoicegenerated=#{isvoicegenerated}"
			+ " ,retmlid=#{retmlid}"
			+ " ,ismlgenerated=#{ismlgenerated}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE directoryid=#{directoryid} "
			+ " RETURNING directoryid")
    public String Update(Directory dir);
	
	@Select("Update ms_eng_directory SET retvoiceid=#{retvoiceid}"
			+ " ,categorymode=#{categorymode}"
			+ " ,isvoicegenerated=#{isvoicegenerated}"
			
			+ " WHERE directoryid=#{directoryid} "
			+ " RETURNING directoryid")
    public String Updatevoiceonly(Directory dir);
	
	@Select("Update ms_eng_directory SET retmlid=#{retmlid}"
			+ " ,categorymode=#{categorymode}"
			+ " ,ismlgenerated=#{ismlgenerated}"
			
			+ " WHERE directoryid=#{directoryid} "
			+ " RETURNING directoryid")
    public String Updatemlrtsaonly(Directory dir);
	
	@Select("Update ms_eng_directory SET reticategoryid=#{reticategoryid}"
			+ " WHERE directoryid=#{directoryid} "
			+ " RETURNING directoryid")
    public String Updatertsaonly(Directory dir);
	
	
	@Select("Update ms_eng_directory SET "
			+ " reticategoryid=#{reticategoryid}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE directoryid=#{directoryid} "
			+ " RETURNING directoryid")
    public String Createroocenginesync(Directory directory);
	
	
	/*
	 		+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
	*/
	
	
	/*
	@Select(" WITH RECURSIVE dirmaptree AS\n" + 
			" (SELECT *, CAST(name As varchar(1000)) As directorymap\n" + 
			" FROM ms_eng_directory\n" + 
			" WHERE (parentid IS null or parentid<=0)\n" + 
			" UNION ALL\n" + 
			" SELECT si.*,\n" + 
			"	CAST(sp.directorymap || '->' || si.name As varchar(1000)) As directorymap\n" + 
			" FROM ms_eng_directory As si\n" + 
			"	INNER JOIN dirmaptree AS sp\n" + 
			"	ON (si.parentid = sp.directoryid)\n" + 
			" )\n" + 
			" SELECT *--, si_item_fullname\n" + 
			" FROM dirmaptree\n" + 
			" ORDER BY directoryid asc; ")
    public List<Directory> extractDirectory();
	*/
	/*@Select(" WITH RECURSIVE dirmaptree AS\n" + 
			" (SELECT *, CAST(' # ' As varchar(1000)) As path\n" + 
			" FROM ms_eng_directory\n" + 
			" WHERE (parentid IS null or parentid<=0)\n" + 
			" UNION ALL\n" + 
			" SELECT si.*,\n" + 
			" CAST(sp.path || '->' || sp.name As varchar(1000)) As path\n" + 
			" FROM ms_eng_directory As si\n" + 
			" INNER JOIN dirmaptree AS sp\n" + 
			" ON (si.parentid = sp.directoryid)\n" + 
			" )\n" + 
			" SELECT *,\n" + 
			" replace(replace(path,' # ->',''),' # ','') as directorymap\n" + 
			" FROM dirmaptree\n" + 
			" ORDER BY directoryid asc;  ")
    public List<Directory> extractDirectory();*/
	
	@Select(" WITH RECURSIVE dirmaptree AS\n" + 
			" (SELECT *\n" + 
			" , CAST(' # ' As varchar(1000)) As path\n" + 
			" FROM ms_eng_directory\n" + 
			" WHERE (parentid IS null or parentid<=0)\n" + 
			" UNION ALL\n" + 
			" SELECT si.*\n" + 
			" ,CAST(sp.path || '->' || sp.name As varchar(1000)) As path\n" + 
			" FROM ms_eng_directory As si\n" + 
			" INNER JOIN dirmaptree AS sp\n" + 
			" ON (si.parentid = sp.directoryid)\n" + 
			" )\n" + 
			" SELECT *\n" + 
			" ,replace(replace(path,' # ->',''),' # ','') as directorymap\n" + 
			" ,(select name from ms_eng_directory where dirmaptree.switchingid=ms_eng_directory.directoryid \n" + 
			" and dirmaptree.switchingid is not null limit 1) as switchingname\n" + 
			" ,(select question from ms_eng_intent where dirmaptree.intentid=ms_eng_intent.intentid \n" + 
			" and dirmaptree.intentid is not null limit 1) as intentname\n" + 
			" FROM dirmaptree\n" + 
			" ORDER BY directoryid asc;   ")
    public List<Directory> extractDirectory();
	
	/*@Select("SELECT * FROM ms_eng_directory WHERE active=1 AND (isvoicegenerated=0 OR isvoicegenerated IS NULL) ORDER BY directoryid ASC")
    public List<Directory> findDirectorynotgenvoice();*/
	@Select("SELECT * FROM ms_eng_directory WHERE active=1  ORDER BY directoryid ASC")
    public List<Directory> findDirectorynotgenvoice();
	
	@Select("SELECT * FROM ms_eng_directory WHERE active=1 and ismlgenerated=0 ORDER BY directoryid ASC ;")
    public List<Directory> findDirectorynotgenml();
	
	
	

}
