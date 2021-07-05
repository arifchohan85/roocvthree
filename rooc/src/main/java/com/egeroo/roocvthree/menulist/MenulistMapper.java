package com.egeroo.roocvthree.menulist;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;






public interface MenulistMapper {
	
	@Select("SELECT menulistid,title,icon,route,sort,parentid \n" + 
			", (select p.title from ms_app_menulist p where m.parentid=p.menulistid) as parentname \n" + 
			" FROM ms_app_menulist m ORDER BY menulistid; ")
    public List<Menulist> findAll();
	
	@Select("SELECT * FROM ms_app_menulist WHERE (parentid is null OR parentid <=0) ORDER BY menulistid")
    public List<Menulist> findParent();
	
	/*
	 	@Select("WITH RECURSIVE menu_area_tree( \"menulistid\", \"parentid\", \"title\", \"icon\",\"route\" ,\"children\") AS (\n" + 
			"  \n" + 
			"  SELECT c.*, json '[]' items\n" + 
			"  FROM ms_app_menulist c\n" + 
			"  WHERE NOT EXISTS(SELECT * FROM ms_app_menulist AS hypothetic_child WHERE hypothetic_child.parentid = c.menulistid)\n" + 
			"\n" + 
			"  UNION ALL\n" + 
			"\n" + 
			"  \n" + 
			"  SELECT (parent).*, json_agg(child) AS \"items\"\n" + 
			"  FROM (\n" + 
			"    SELECT parent, child\n" + 
			"    FROM menu_area_tree AS child\n" + 
			"    JOIN ms_app_menulist parent ON parent.menulistid = child.parentid\n" + 
			"  ) branch\n" + 
			"  GROUP BY branch.parent\n" + 
			")\n" + 
			"SELECT json_agg(t)::jsonb as items\n" + 
			"FROM menu_area_tree t\n" + 
			"LEFT JOIN ms_app_menulist AS hypothetic_parent ON(hypothetic_parent.menulistid = t.parentid)\n" + 
			"WHERE hypothetic_parent.menulistid IS null ;")
	 
	 */
	
	/*
	   @Select("WITH RECURSIVE menu_area_tree( \"menulistid\", \"parentid\", \"title\", \"icon\",\"route\" ,\"items\") AS (\n" + 
			"  \n" + 
			"  SELECT c.menulistid,c.parentid,c.title,c.icon,c.route, json '[]' items\n" + 
			"  FROM ms_app_menulist c\n" + 
			"  WHERE NOT EXISTS(SELECT * FROM ms_app_menulist AS hypothetic_child WHERE hypothetic_child.parentid = c.menulistid)\n" + 
			" \n" + 
			"  UNION ALL\n" + 
			" \n" + 
			"  \n" + 
			"  SELECT (parent).menulistid,(parent).parentid,(parent).title,(parent).icon,(parent).route, json_agg(child) AS \"items\"\n" + 
			"  FROM (\n" + 
			"    SELECT parent, child\n" + 
			"    FROM menu_area_tree AS child\n" + 
			"    JOIN ms_app_menulist parent ON parent.menulistid = child.parentid\n" + 
			"  ) branch\n" + 
			"  GROUP BY branch.parent\n" + 
			" )\n" + 
			" SELECT replace(replace(json_agg(t)::jsonb::text,'\"items\": [],'::text,' '::text)::text,'\"route\": \" \",'::text,' '::text)::jsonb as items\n" + 
			" FROM menu_area_tree t\n" + 
			" LEFT JOIN ms_app_menulist AS hypothetic_parent ON(hypothetic_parent.menulistid = t.parentid)\n" + 
			" WHERE hypothetic_parent.menulistid IS null ;")
			public Menulistall findAllmenu();
	*/
	 @Select(" WITH RECURSIVE menu_area_tree( menulistid, parentid, title, icon,route ,sort,children) AS ( \n" + 
	 		"	SELECT c.*, json '[]' items \n" + 
	 		"	FROM ms_app_menulist c \n" + 
	 		"	WHERE NOT EXISTS(SELECT * FROM ms_app_menulist AS hypothetic_child WHERE hypothetic_child.parentid = c.menulistid order by hypothetic_child.sort) \n" + 
	 		"	UNION ALL \n" + 
	 		"	SELECT (parent).*, json_agg(child) AS items \n" + 
	 		"	FROM ( \n" + 
	 		"	  SELECT parent, child \n" + 
	 		"	  FROM menu_area_tree AS child \n" + 
	 		"	  JOIN ms_app_menulist parent ON parent.menulistid = child.parentid \n" + 
	 		"	  order by parent.sort\n" + 
	 		"	) branch \n" + 
	 		"	GROUP BY branch.parent \n" + 
	 		") \n" + 
	 		"	\n" + 
	 		" SELECT t.*\n" + 
	 		" FROM menu_area_tree t \n" + 
	 		" LEFT JOIN ms_app_menulist AS hypothetic_parent ON(hypothetic_parent.menulistid = t.parentid) \n" + 
	 		" WHERE hypothetic_parent.menulistid IS null \n" + 
	 		" order by t.sort\n" + 
	 		"; ")
    public List<Menulist> findAllmenu();
	 
	 /*@Select(" WITH RECURSIVE menu_area_tree( menulistid, parentid, title, icon,route ,sort,children) AS (\n" + 
	 		" SELECT c.*, json '[]' items\n" + 
	 		" FROM ms_app_menulist c\n" + 
	 		" WHERE NOT EXISTS(SELECT * FROM ms_app_menulist AS hypothetic_child WHERE hypothetic_child.parentid = c.menulistid order by hypothetic_child.sort)\n" + 
	 		" UNION ALL\n" + 
	 		" SELECT (parent).*, json_agg(child) AS items\n" + 
	 		" FROM (\n" + 
	 		"  SELECT parent, child\n" + 
	 		"  FROM menu_area_tree AS child\n" + 
	 		"  JOIN ms_app_menulist parent ON parent.menulistid = child.parentid\n" + 
	 		"  order by parent.sort\n" + 
	 		" ) branch\n" + 
	 		" GROUP BY branch.parent\n" + 
	 		" )\n" + 
	 		" SELECT t.*\n" + 
	 		" FROM menu_area_tree t\n" + 
	 		" LEFT JOIN ms_app_menulist AS hypothetic_parent ON(hypothetic_parent.menulistid = t.parentid)\n" + 
	 		" inner join ms_app_menulistrole mr\n" + 
	 		" on t.menulistid=mr.menulistid\n" + 
	 		" WHERE hypothetic_parent.menulistid IS null\n" + 
	 		" and mr.roleid=#{roleid}\n" + 
	 		" order by t.sort\n" + 
	 		" ;  ")
	public List<Menulist> findAllmenuwithrole(Integer roleid);*/
	 @Select(" WITH RECURSIVE menu_area_tree( menulistid, parentid, title, icon,route ,sort,children) AS (\n" + 
		 		" SELECT c.*, json '[]' items\n" + 
		 		" FROM ms_app_menulist c\n" + 
		 		" WHERE NOT EXISTS(SELECT * FROM ms_app_menulist AS hypothetic_child WHERE hypothetic_child.parentid = c.menulistid order by hypothetic_child.sort)\n" + 
		 		" UNION ALL\n" + 
		 		" SELECT (parent).*, json_agg(child) AS items\n" + 
		 		" FROM (\n" + 
		 		"  SELECT parent, child\n" + 
		 		"  FROM menu_area_tree AS child\n" + 
		 		"  JOIN ms_app_menulist parent ON parent.menulistid = child.parentid\n" + 
		 		"  left join ms_app_menulistrole mrc \n" + 
		 		" on child.menulistid=mrc.menulistid \n" + 
		 		" and mrc.roleid=#{roleid} " +
		 		" order by parent.sort\n" + 
		 		" ) branch\n" + 
		 		" GROUP BY branch.parent\n" + 
		 		" )\n" + 
		 		" SELECT t.*\n" + 
		 		" FROM menu_area_tree t\n" + 
		 		" LEFT JOIN ms_app_menulist AS hypothetic_parent ON(hypothetic_parent.menulistid = t.parentid)\n" + 
		 		" inner join ms_app_menulistrole mr\n" + 
		 		" on t.menulistid=mr.menulistid\n" + 
		 		" WHERE hypothetic_parent.menulistid IS null\n" + 
		 		" and mr.roleid=#{roleid}\n" + 
		 		" order by t.sort\n" + 
		 		" ;  ")
		public List<Menulist> findAllmenuwithrole(Integer roleid);
	
	@Select("SELECT * FROM ms_app_menulist WHERE menulistid = #{menulistid}")
    public Menulist findOne(Integer menulistid);
	
	@Select("SELECT * FROM ms_app_menulist WHERE parentid = #{parentid}")
    public List<Menulist> findChild(Integer parentid);
	
	@Select("SELECT * FROM ms_app_menulist WHERE route=#{route} ORDER BY menulistid ASC Limit 1")
    public Menulist findByRoute(String route);
	
	@SelectKey(statement = "currval('menulistid')", keyProperty = "menulistid", before = true , resultType = int.class)
	@Select("Insert into ms_app_menulist(parentid,title,icon,route,sort"
			+ ",createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ "VALUES (#{parentid},#{title},#{icon},#{route},#{sort}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING menulistid") //,#{createdBy},#{updateBy}
	public String Save(Menulist ml);
	
	@Select("Update ms_app_menulist SET parentid=#{parentid}"
			+ " ,title=#{title}"
			+ " ,icon=#{icon}"
			+ " ,route=#{route}"
			+ " ,sort=#{sort}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE menulistid=#{menulistid} "
			+ " RETURNING menulistid")
    public String Update(Menulist ml);
	
	@Select("delete from ms_app_menulist WHERE menulistid = #{menulistid} RETURNING * ")
    public Menulist deleteOne(Integer menulistid);

}
