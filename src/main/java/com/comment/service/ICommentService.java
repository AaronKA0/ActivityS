package com.comment.service;

import com.comment.dto.CommentQueryParams;
import com.comment.dto.CommentRequest;
import com.comment.dto.CommentStatus;
import com.comment.model.CommentVO;

import java.util.List;

public interface ICommentService {
    Integer countComments(CommentQueryParams commentQueryParams);
    List<CommentVO> getComments(CommentQueryParams commentQueryParams);
    CommentVO getCommentById(Integer comId);
    Integer insertComment(CommentRequest commentRequest);
    void updateComment(Integer comId, CommentRequest commentRequest);
    void deleteComment(Integer comId , CommentStatus commentStatus);

}
