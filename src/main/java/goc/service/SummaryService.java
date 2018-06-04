package goc.service;

import java.util.List;

import goc.pojo.DaySummary;
import goc.pojo.WeekSummary;

public interface SummaryService {

    public void InsertDaySummary(DaySummary daySummary);
	
	public void InsertWeekSummary(WeekSummary weekSummary);
	
	public List<DaySummary> selectByUserid(Integer userid);
	
	public DaySummary selectByIdAndDate(DaySummary daySummary);
}
