package com.rafa.sevenTao.repository;

import com.rafa.sevenTao.model.Comment;
import com.rafa.sevenTao.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT SUM(c.rate) FROM Comment c WHERE c.hotel = :hotel")
    public int getAllCommentScores(@Param("hotel") Hotel hotel);
}
