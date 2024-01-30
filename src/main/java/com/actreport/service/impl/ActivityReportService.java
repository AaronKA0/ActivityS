package com.actreport.service.impl;

import com.details.act.repository.ActRepository;
import com.actreport.dto.ActivityReportQueryParams;
import com.actreport.dto.ActivityReportRequest;
import com.actreport.dto.ReportStatus;
import com.actreport.model.ActivityReportVO;
import com.actreport.repository.ActivityReportReopsitory;
import com.actreport.service.IActivityReportService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ActivityReportService implements IActivityReportService {

    @Autowired
    private ActivityReportReopsitory activityReportReopsitory;

    @Autowired
    private ActRepository actRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ActivityReportVO> getAll(ActivityReportQueryParams activityReportQueryParams, Pageable pageable) {

        if (activityReportQueryParams.getRepStatus() != null && activityReportQueryParams.getMemId() != null && activityReportQueryParams.getEmpId() == null) {
            return activityReportReopsitory.findByRepStatusAndMemId(activityReportQueryParams.getRepStatus(), activityReportQueryParams.getMemId(), pageable);

        } else if (activityReportQueryParams.getRepStatus() != null && activityReportQueryParams.getEmpId() == null) {
            return activityReportReopsitory.findByRepStatus(activityReportQueryParams.getRepStatus(), pageable);

        } else if (activityReportQueryParams.getMemId() != null && activityReportQueryParams.getEmpId() == null) {
            return activityReportReopsitory.findByMemId(activityReportQueryParams.getMemId(), pageable);

        }

        if (activityReportQueryParams.getEmpId() != null && activityReportQueryParams.getRepStatus() != null) {
            return activityReportReopsitory.findByRepStatusAndEmpId(activityReportQueryParams.getRepStatus(), activityReportQueryParams.getEmpId(), pageable);

        } else if (activityReportQueryParams.getRepStatus() != null) {
            return activityReportReopsitory.findByRepStatus(activityReportQueryParams.getRepStatus(), pageable);

        } else if (activityReportQueryParams.getEmpId() != null) {
            return activityReportReopsitory.findByEmpId(activityReportQueryParams.getEmpId(), pageable);
        }

        return activityReportReopsitory.findAll(pageable);
    }

    @Override
    public ActivityReportVO findByPrimaryKey(Integer repId) {
        return activityReportReopsitory.findByRepIdAndFetchActEagerly(repId).orElse(null);
    }

    @Override
    public ActivityReportVO insert(ActivityReportRequest activityReportRequest) {

        ActivityReportVO activityReport = new ActivityReportVO();

        modelMapper.map(activityReportRequest, activityReport);

        activityReport.setRepTime(new Date());

        return activityReportReopsitory.save(activityReport);
    }

    @Override
    public ActivityReportVO update(Integer repId, ReportStatus reportStatus) {
        ActivityReportVO activityReport = activityReportReopsitory.findById(repId).orElse(null);
//        modelMapper.map(activityReportRequest, ActivityReportVO.class);

        if (activityReport != null) {
            activityReport.setRepStatus(reportStatus.getRepStatus());
            activityReport.getAct().setActStatus(reportStatus.getRepStatus());

            return activityReportReopsitory.save(activityReport);
        } else {
            return null;
        }
    }

    @Override
    public byte[] getPic(Integer repId) {
        return activityReportReopsitory.findRepPicByRepId(repId);
    }


}