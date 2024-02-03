package com.comment.controller;

import com.comment.dto.CommentQueryParams;
import com.comment.dto.CommentRequest;
import com.comment.dto.CommentStatus;
import com.comment.model.CommentVO;
import com.comment.service.ICommentService;
import com.comment.util.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@Slf4j
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<Page<CommentVO>> getComments(
            @RequestParam Integer actId,
            @RequestParam(defaultValue = "50") @Max(100) @Min(0) Integer limit,
            @RequestParam(defaultValue = "com_time") String orderBy,
            @RequestParam(defaultValue = "ASC") String sort) {

        CommentQueryParams commentQueryParams = new CommentQueryParams();
        commentQueryParams.setActId(actId);
        commentQueryParams.setLimit(limit);
        commentQueryParams.setOrderBy(orderBy);
        commentQueryParams.setSort(sort);

        List<CommentVO> comments = commentService.getComments(commentQueryParams);

        Integer total = commentService.countComments(commentQueryParams);

        Page<CommentVO> page = new Page<>();
        page.setComments(comments);
        page.setTotal(total);
        page.setLimit(limit);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/comments/{comId}")
    public ResponseEntity<CommentVO> getComment(@PathVariable Integer comId, HttpSession session) {

        CommentVO comment = commentService.getCommentById(comId);

        //模擬從session取出會員id
        Integer testMemId = 1;
        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else if (comment.getMemId() != memId) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentVO> insertCommnet(@RequestBody @Valid CommentRequest commentRequest,
                                                   HttpSession session) {
        //模擬從session取出會員id
        Integer testMemId = 1;
        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");
        commentRequest.setMemId(memId);

        Integer commentId = commentService.insertComment(commentRequest);

        CommentVO comment = commentService.getCommentById(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @PutMapping("/comments/{comId}")
    public ResponseEntity<CommentVO> updateComment(@PathVariable Integer comId,
                                                   @RequestBody @Valid CommentRequest commentRequest,
                                                   HttpSession session) {

        //模擬從session取出會員id
        Integer testMemId = 1;
        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");
        commentRequest.setMemId(memId);

        //檢查 comment 是否存在
//        CommentVO comment = commentService.getCommentById(comId);
//        if (comment == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        //修改留言的數據
        commentService.updateComment(comId, commentRequest);
        CommentVO updateComment = commentService.getCommentById(comId);

        return ResponseEntity.status(HttpStatus.OK).body(updateComment);
    }

    @PutMapping("/comment/{comId}")
    public ResponseEntity<CommentVO> deleteComment(@PathVariable Integer comId, HttpSession session) {

        //模擬從session取出會員id
        Integer testMemId = 1;
        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");

        CommentStatus commentStatus = new CommentStatus();

        //檢查 comment 是否存在
        CommentVO comment = commentService.getCommentById(comId);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else if (comment.getMemId() != memId) { // 不是自己的留言
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        commentStatus.setMemId(memId);

        commentService.deleteComment(comId, commentStatus);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @DeleteMapping("/comments/{comId}")
//    public ResponseEntity<?> deleteComment(@PathVariable Integer comId) {
//
//        commentService.deleteComment(comId);
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

}
