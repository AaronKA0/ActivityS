package com.act.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "activity_type")
public class ActTypeVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "act_type_id")
    private Integer actTypeId;

    @Getter
    @Column(name = "act_type_name")
    private String actTypeName;

    @OneToMany(mappedBy = "actType", fetch = FetchType.LAZY)
    private List<ActVO> activities;
    // Getters and Setters

    public void setActTypeName(String actTypeName) {
        this.actTypeName = actTypeName;
    }

    
    public Integer getActTypeId() {
		return actTypeId;
	}


	public void setActTypeId(Integer actTypeId) {
		this.actTypeId = actTypeId;
	}


	public List<ActVO> getActivities() {
		return activities;
	}


	public void setActivities(List<ActVO> activities) {
		this.activities = activities;
	}


	public String getActTypeName() {
		return actTypeName;
	}


	@Override
    public String toString() {
        return "ActTypeVO{" +
                "actTypeId=" + actTypeId +
                ", actTypeName='" + actTypeName + '\'' +
                '}';
    }
}

