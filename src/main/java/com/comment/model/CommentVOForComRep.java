package com.comment.model;

import com.commentreport.model.CommentReportVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "activity_comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommentVOForComRep implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private Integer comId;
    @Column(name = "act_id")
    private Integer actId;
    @Column(name = "mem_id")
    private Integer memId;
    @Column(name = "com_reply_id")
    private Integer comReplyId;
    @Column(name = "com_content")
    private String comContent;
    @Column(name = "com_time")
    private Date comTime;
    @Column(name = "com_status")
    private Byte comStatus;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CommentReportVO> commentReport;

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
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

    public Integer getComReplyId() {
        return comReplyId;
    }

    public void setComReplyId(Integer comReplyId) {
        this.comReplyId = comReplyId;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public Date getComTime() {
        return comTime;
    }

    public void setComTime(Date comTime) {
        this.comTime = comTime;
    }

    public Byte getComStatus() {
        return comStatus;
    }

    public void setComStatus(Byte comStatus) {
        this.comStatus = comStatus;
    }

    public Set<CommentReportVO> getCommentReport() {
        return commentReport;
    }

    public void setCommentReport(Set<CommentReportVO> commentReport) {
        this.commentReport = commentReport;
    }
}
