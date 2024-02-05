package com.comment.service.impl;

import com.comment.dao.ICommentDAO;
import com.comment.dto.CommentQueryParams;
import com.comment.dto.CommentRequest;
import com.comment.dto.CommentStatus;
import com.comment.model.CommentVO;
import com.comment.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class CommentService implements ICommentService {

    @Autowired
    private ICommentDAO commentDAO;

    @Autowired
    private RedisTemplate<String, CommentVO> redisTemplate;

    @Override
    public Integer countComments(CommentQueryParams commentQueryParams) {

        return commentDAO.countComments(commentQueryParams);
    }

    @Override
    @Transactional
    public List<CommentVO> getComments(CommentQueryParams commentQueryParams) {

        String redisKey = buildRedisKey(commentQueryParams.getActId());
        //redis查詢
        List<CommentVO> commentsCache = getCommentsCache(commentQueryParams);

        //Redis沒有 就去DB查
        if (commentsCache == null || commentsCache.isEmpty()) {
            List<CommentVO> comments = commentDAO.getComments(commentQueryParams);
            if (comments != null) {
                //反轉redis 變成新至舊
                Collections.reverse(comments);
                //查完同時存入Redis
                redisTemplate.opsForList().rightPushAll(redisKey, comments);
            }
            return getCommentsCache(commentQueryParams);

        }
        //redis改變排序
        if (commentQueryParams.getSort().equals("DESC")) {
            Collections.reverse(commentsCache);
        }
        //有的話就直接return Redis
        return commentsCache;
    }

    @Override
    public CommentVO getCommentById(Integer comId) {
        return commentDAO.getCommentById(comId);
    }

    @Override
    @Transactional
    public Integer insertComment(CommentRequest commentRequest) {

        Integer commentId = commentDAO.insertComment(commentRequest);

        CommentVO commentById = commentDAO.getCommentById(commentId);
        //存Redis
        insertCommentCache(commentById);

        return commentId;
    }

    @Override
    @Transactional
    public void updateComment(Integer comId, CommentRequest commentRequest) {

        Integer actId = getCommentById(comId).getActId();

        commentDAO.updateComment(comId, commentRequest);

//        String redisKey = buildRedisKey(actId);

        redisTemplate.delete(buildRedisKey(actId));

    }

    @Override
    @Transactional
    public void deleteComment(Integer comId, CommentStatus commentStatus) {

        Integer actId = getCommentById(comId).getActId();

        commentDAO.deleteComment(comId, commentStatus);

        redisTemplate.delete(buildRedisKey(actId));
    }

    //存進Redis
    public void insertCommentCache(CommentVO commentById) {
        String redisKey = buildRedisKey(commentById.getActId());

        redisTemplate.opsForList().leftPush(redisKey, commentById);
    }

    //查詢Redis
    public List<CommentVO> getCommentsCache(CommentQueryParams commentQueryParams) {

        String redisKey = buildRedisKey(commentQueryParams.getActId());

        return redisTemplate.opsForList().range(redisKey, 0, commentQueryParams.getLimit() - 1);
    }

    //建構RedisKey
    public String buildRedisKey(Integer actId) {
        return new StringBuilder()
                .append("comment")
                .append(":")
                .append("actId")
                .append(":")
                .append(actId.toString())
                .toString();
    }


}
