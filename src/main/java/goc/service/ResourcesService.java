package goc.service;

import java.util.List;

import goc.pojo.Resources;

public interface ResourcesService {

	public List<Resources> SelectAll();
	
	public List<Resources> SelectByType(String type);
	
	public Resources SelectByTitle(String title);
	
	public List<Resources> SelectHot();
}
