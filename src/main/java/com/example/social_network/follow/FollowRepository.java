package com.example.social_network.follow;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    boolean existsByFollower_IdAndFollowee_Id(Long followerId, Long followeeId);

    void deleteByFollower_IdAndFollowee_Id(Long followerId, Long followeeId);

    Page<Follow> findByFollower_Id(Long followerId, Pageable pageable);   // following
    Page<Follow> findByFollowee_Id(Long followeeId, Pageable pageable);   // followers

    @Query("select f.followee.id from Follow f where f.follower.id = :followerId")
    List<Long> findFolloweeIdsByFollowerId(@Param("followerId") Long followerId);
}
