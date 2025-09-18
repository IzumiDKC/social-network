package com.example.social_network.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
      select p from Post p
      where p.author.id in (
        select f.followee.id from Follow f
        where f.follower.id = :followerId
      )
      order by p.createdAt desc
    """)
    Page<Post> feedOf(Long followerId, Pageable pageable);


    Page<Post> findAllByAuthor_UsernameOrderByCreatedAtDesc(String username, Pageable pageable);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Post> findByContentContainingIgnoreCase(String keyword);

}
