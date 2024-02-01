package com.actfollowed.repository;

import com.actfollowed.model.ActFollowed;
import com.actfollowed.model.ActFollowedVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActFollowedRepository extends JpaRepository<ActFollowedVO, ActFollowed> {
    Page<ActFollowedVO> findByMemIdAndFolStatus(Integer memId, Byte folStatus, Pageable pageable);

    Optional<ActFollowedVO> findByActIdAndMemId(Integer actId, Integer memId);
}
