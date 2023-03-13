package com.keduit.helloworld.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.keduit.helloworld.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	
	@Query(value =
			"select count(board_comment_num) from board b join member m on m.member_num = b.member_num "
			+ " left outer join comment c on c.board_num = b.board_num "
			+ " where b.board_num = :boardNum"
			, nativeQuery = true)
		Long getBoardByBno(Long boardNum);

	@Query(value =
			"select * from comment c right join board b on b.board_num = c.board_num "
			+ " join member m on b.member_num = m.member_num "
			+ " where b.board_num = :boardNum"
			, nativeQuery = true)
	List<Comment> getCommentlist(Long boardNum);

	@Query(value=
			"select board_comment_num from comment where board_num = :boardNum", nativeQuery = true)
	List<Long> findBoardCommentNum(Long boardNum);
	
	/**내 정보에서 댓글 가져가기*/
	@Query(value = "select * from  comment c "
			+ "	join board b on b.board_num = c.board_num "
			+ "	where c.commenter_num = :id  order by c.board_comment_num desc", nativeQuery = true)
	List<Comment> getCommentById(Long id);

	//승민
	@Query(value = "SELECT * FROM comment WHERE commenter_num = :str1", nativeQuery = true)
	Page<Comment> findByCommenterNum(String str1, Pageable pageable);

	@Query(value = "SELECT * FROM comment WHERE (commenter_num = :str1) limit 1", nativeQuery = true)
	Optional<Comment> findByCommenterNum(Long str1);
	
	//승민 끝


}
