package com.actfollowed.dto;

import com.details.act.model.ActVOs;
import com.actfollowed.model.ActFollowedVO;

import java.util.List;

public class ActFollowDTO {

    private List<ActFollowedVO> actFollowed;
    private List<ActVOs> act;

    public List<ActFollowedVO> getActFollowed() {
        return actFollowed;
    }

    public void setActFollowed(List<ActFollowedVO> actFollowed) {
        this.actFollowed = actFollowed;
    }

    public List<ActVOs> getAct() {
        return act;
    }

    public void setAct(List<ActVOs> act) {
        this.act = act;
    }
}
