package com.actreport.service;

import com.actreport.dto.ActivityReportQueryParams;
import com.actreport.dto.ActivityReportRequest;
import com.actreport.dto.ReportStatus;
import com.actreport.model.ActivityReportVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActivityReportService {

	Page<ActivityReportVO> getAll(ActivityReportQueryParams activityReportQueryParams, Pageable pageable);

	ActivityReportVO findByPrimaryKey(Integer repId);

	ActivityReportVO insert(ActivityReportRequest activityReportRequest);

	ActivityReportVO update(Integer repId, ReportStatus reportStatus);

	byte[] getPic(Integer repId);

}
