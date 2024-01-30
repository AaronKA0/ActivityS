package com.actfollowed.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class ActFollowed implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "act_id")
    private Integer actId;

    @Column(name = "mem_id")
    private Integer memId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActFollowed that = (ActFollowed) o;
        return Objects.equals(actId, that.actId) && Objects.equals(memId, that.memId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actId, memId);
    }

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
}
