package com.keduit.helloworld.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.keduit.helloworld.entity.Favorites;
import com.keduit.helloworld.entity.Member;

@Repository

public interface FavoritesRepository extends JpaRepository<Favorites, Long>{

	@Query(value = "select favorites_num from favorites where bookmarker = :myNum and bookmarked = :youNum" ,nativeQuery=true)
	Long getNum(Long myNum, Long youNum);

	
	/** 내가 팔로한 사람인지 확인하기 */
	@Query(value = "select count(*) from favorites where bookmarker = :membernum and bookmarked = :younum" , nativeQuery = true)
	int getCount(Long membernum, Long younum);

	@Query(value = "SELECT * FROM favorites WHERE bookmarked = :a AND bookmarker = :b", nativeQuery = true)
	Optional<Favorites> findByBookmarkedAndBookmarker(long a, long b);

}
