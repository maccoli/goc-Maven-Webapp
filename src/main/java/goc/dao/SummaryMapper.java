package goc.dao;

import java.util.List;

import goc.pojo.DaySummary;
import goc.pojo.WeekSummary;

public interface SummaryMapper {

	void InsertDaySummary(DaySummary daySummary);
	
	void InsertWeekSummary(WeekSummary weekSummary);
	
	List<DaySummary> selectByUserid(Integer userid);
	
	DaySummary selectByIdAndDate(DaySummary daySummary);
}
