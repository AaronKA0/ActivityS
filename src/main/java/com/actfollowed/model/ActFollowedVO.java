package com.actfollowed.model;

import com.details.act.model.ActVO;
import com.membership.model.MembershipVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "activity_followed")
@IdClass(ActFollowed.class)
public class ActFollowedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "act_id")
    private Integer actId;

    @Id
    @Column(name = "mem_id")
    private Integer memId;

    @Column(name = "fol_status")
    private Byte folStatus;

    @ManyToOne
    @JoinColumn(name = "act_id", insertable = false, updatable = false)
    private ActVO act;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mem_id", insertable = false, updatable = false)
    private MembershipVO membership;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Byte getFolStatus() {
        return folStatus;
    }

    public void setFolStatus(Byte folStatus) {
        this.folStatus = folStatus;
    }

    public ActVO getAct() {
        return act;
    }

    public void setAct(ActVO act) {
        this.act = act;
    }

    public MembershipVO getMembership() {
        return membership;
    }

    public void setMembership(MembershipVO membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        return "ActFollowedVO{" +
                "actId=" + actId +
                ", memId=" + memId +
                ", folStatus=" + folStatus +
                '}';
    }
}
