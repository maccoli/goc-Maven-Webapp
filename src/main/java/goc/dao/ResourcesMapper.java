package goc.dao;

import java.util.List;

import goc.pojo.Resources;

public interface ResourcesMapper {

	List<Resources> SelectAll();
	
	List<Resources> SelectByType(String type);
	
	Resources SelectByTitle(String title);
	
	List<Resources> SelectHot();
}
