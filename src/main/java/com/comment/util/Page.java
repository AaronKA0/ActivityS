package com.comment.util;

import com.comment.model.CommentVO;

import java.util.List;

public class Page<T> {

    private List<CommentVO> comments;
    private Integer total;
    private Integer limit;

    public List<CommentVO> getComments() {
        return comments;
    }

    public void setComments(List<CommentVO> comments) {
        this.comments = comments;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
