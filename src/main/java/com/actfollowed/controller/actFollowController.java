package com.actfollowed.controller;

import com.actfollowed.dto.ActFollowRequest;
import com.actfollowed.model.ActFollowedVO;
import com.actfollowed.service.IActFollowedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class actFollowController {

    @Autowired
    private IActFollowedService actFollowedService;

    @GetMapping("/activity/actfollows")
    public ResponseEntity<Page<ActFollowedVO>> getActFollows(
            @RequestParam(required = false) String sortDirection,
            @PageableDefault(size = 5, sort = "act.regEndTime", direction = Sort.Direction.ASC) Pageable pageable,
            HttpSession session) {

        //傳進來是DESC的話替換掉pageable內的Sort
        if ("DESC".equalsIgnoreCase(sortDirection)) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "act.regEndTime")
            );
        }

        //第1種方式.actId從原先詳情那邊的controller的session存到model 存給前端了
        //第2種方式.模擬從session取會員id
//        Integer testMemId = 1;
//        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");

        Page<ActFollowedVO> actFollows = actFollowedService.getActFollows(memId, pageable);

        if (actFollows == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(actFollows);
    }

    @GetMapping("/activity/actfollow")
    public ResponseEntity<Byte> getActFollow(@RequestParam Integer actId,
//                                              @RequestParam Integer memId,
                                             HttpSession session) {

        //第1種方式.actId從原先詳情那邊的controller的session存到model 存給前端了

        //第2種方式.模擬從session取會員id
//        Integer testMemId = 1;
//        session.setAttribute("memId", testMemId);

        Integer memId = (Integer) session.getAttribute("memId");

        Byte actFollows = actFollowedService.getActFollows(actId, memId);
        return ResponseEntity.status(HttpStatus.OK).body(actFollows);
    }

    //應該不需要回傳 沒意義 可以為void
    @PostMapping("/activity/actfollow")
    public ResponseEntity<ActFollowedVO> createActFollow(@RequestBody ActFollowRequest actFollowRequest,
                                                         HttpSession session) {

        //模擬從session取會員id
//        Integer testMemId = 1;
//        session.setAttribute("memId", testMemId);

        Integer memId = (Integer) session.getAttribute("memId");
        actFollowRequest.setMemId(memId);

        ActFollowedVO actFollowed = actFollowedService.createActFollow(actFollowRequest);

        return ResponseEntity.status(HttpStatus.OK).body(actFollowed);
    }

    //應該不需要回傳 沒意義 可以為void
    @PostMapping("/activity/unactfollow")
    public ResponseEntity<ActFollowedVO> updateActFollow(@RequestBody ActFollowRequest actFollowRequest,
                                                         HttpSession session) {
        //模擬從session取會員id
//        Integer testMemId = 1;
//        session.setAttribute("memId", testMemId);

        Integer memId = (Integer) session.getAttribute("memId");
        actFollowRequest.setMemId(memId);

        ActFollowedVO actFollowed = actFollowedService.updateActFollow(actFollowRequest);

        return ResponseEntity.status(HttpStatus.OK).body(actFollowed);
    }
}
