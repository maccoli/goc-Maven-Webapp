package goc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import goc.dao.SummaryMapper;
import goc.pojo.DaySummary;
import goc.pojo.WeekSummary;
import goc.service.SummaryService;

@Service("summaryService")
public class SummaryServiceImpl implements SummaryService {

	@Resource
	private SummaryMapper summaryMapper;
	
	@Override
	public void InsertDaySummary(DaySummary daySummary) {
		this.summaryMapper.InsertDaySummary(daySummary);

	}

	@Override
	public void InsertWeekSummary(WeekSummary weekSummary) {
		this.summaryMapper.InsertWeekSummary(weekSummary);

	}

	@Override
	public List<DaySummary> selectByUserid(Integer userid) {
	
		return this.summaryMapper.selectByUserid(userid);
	}

	@Override
	public DaySummary selectByIdAndDate(DaySummary daySummary) {
	
		return this.summaryMapper.selectByIdAndDate(daySummary);
	}

}
