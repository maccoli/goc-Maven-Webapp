package goc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import goc.dao.ResourcesMapper;
import goc.pojo.Resources;
import goc.service.ResourcesService;

@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService {

	@Resource
	private ResourcesMapper resourcesMapper;

	@Override
	public List<Resources> SelectAll() {
		
		return this.resourcesMapper.SelectAll();
	}

	@Override
	public List<Resources> SelectByType(String type) {
	
		return this.resourcesMapper.SelectByType(type);
	}

	@Override
	public Resources SelectByTitle(String title) {
		
		return this.resourcesMapper.SelectByTitle(title);
	}

	@Override
	public List<Resources> SelectHot() {
	
		return this.resourcesMapper.SelectHot();
	}
	


}
