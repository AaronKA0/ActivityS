package com.actreg.model;

import com.details.act.model.ActVO;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activity_registration")
public class ActRegVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_reg_id")
    private Integer actRegId;

    @Column(name = "mem_id")
    private Integer memId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "act_id", referencedColumnName = "act_id")
    private ActVO act;

    @Column(name = "reg_total")
    private Integer regTotal;

    @Column(name = "reg_time")
    private Date regTime;

    @Column(name = "reg_status")
    private Byte regStatus;

    @Column(name = "is_act_part")
    private Byte isActPart;

    @Column(name = "act_rating")
    private Double actRating;

    @Column(name = "reg_reason")
    private String regReason;

    public Integer getActRegId() {
        return actRegId;
    }

    public void setActRegId(Integer actRegId) {
        this.actRegId = actRegId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public ActVO getAct() {
        return act;
    }

    public void setAct(ActVO act) {
        this.act = act;
    }

    public Integer getRegTotal() {
        return regTotal;
    }

    public void setRegTotal(Integer regTotal) {
        this.regTotal = regTotal;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Byte getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(Byte regStatus) {
        this.regStatus = regStatus;
    }

    public Byte getIsActPart() {
        return isActPart;
    }

    public void setIsActPart(Byte isActPart) {
        this.isActPart = isActPart;
    }

    public Double getActRating() {
        return actRating;
    }

    public void setActRating(Double actRating) {
        this.actRating = actRating;
    }

    public String getRegReason() {
        return regReason;
    }

    public void setRegReason(String regReason) {
        this.regReason = regReason;
    }
}
