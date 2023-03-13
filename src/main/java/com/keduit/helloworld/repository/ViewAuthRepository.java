package com.keduit.helloworld.repository;

import com.keduit.helloworld.entity.ViewAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ViewAuthRepository extends JpaRepository <ViewAuth, Long> {

    @Query(value = "select * from view_auth where (board_comment_num = :str1 and member_num = :str2)",nativeQuery = true)
    Optional<ViewAuth> findBCNAndMM(Long str1,Long str2);
}
