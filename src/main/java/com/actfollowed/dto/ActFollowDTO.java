package com.actfollowed.dto;

import com.details.act.model.ActVO;
import com.actfollowed.model.ActFollowedVO;

import java.util.List;

public class ActFollowDTO {

    private List<ActFollowedVO> actFollowed;
    private List<ActVO> act;

    public List<ActFollowedVO> getActFollowed() {
        return actFollowed;
    }

    public void setActFollowed(List<ActFollowedVO> actFollowed) {
        this.actFollowed = actFollowed;
    }

    public List<ActVO> getAct() {
        return act;
    }

    public void setAct(List<ActVO> act) {
        this.act = act;
    }
}
