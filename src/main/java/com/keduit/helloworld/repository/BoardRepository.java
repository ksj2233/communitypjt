package com.keduit.helloworld.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keduit.helloworld.dto.BoardDTO;
import com.keduit.helloworld.entity.Board;
import com.keduit.helloworld.entity.Comment;
import com.keduit.helloworld.repository.search.SearchBoardRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository{

	
	@Query(value = 
			"select * from board b join member m on m.member_num = b.member_num "
					+ " left outer join comment c on c.board_num = b.board_num "
					+ " where b.board_num = :boardNum"
			, nativeQuery = true)
		Board getBoardByBno(Long boardNum);

	/**내가 작성한 게시판 보기*/
	@Query(value = "select * from board b "
				  + "join member m on m.member_num = b.member_num "
				  + "where m.id = :id order by b.board_num desc", nativeQuery=true)
	List<Board> getMyBoardList(String id);

	//승민

	@Query(value = "SELECT * FROM board WHERE title = :str", nativeQuery = true)
	Page<Board> findByTitle(String str, Pageable pageable);

	@Query(value = "SELECT * FROM board WHERE member_num = :str", nativeQuery = true)
	Page<Board> findByMemberNum(String str, Pageable pageable);

	//승민 끝

	@Query(value = "select * from board where :str = :num", nativeQuery = true)
    List<Board> temp(String str, Integer num);

	//호성 top Community 제작 23.02.22
	
	/** 게시물 조회수 순으로 가져오기 */
	@Query(value ="SELECT *  FROM board b where boardcase = '1' order by views desc, board_num desc limit 5" , nativeQuery=true)
	List<Board> getBoardCount();

	
	/**내 정보에서 댓글 가져가기*/
	
	
	  @Query(value = "select b.boardcase from board b " +
	  "right outer join comment c on b.board_num = c.board_num " +
	  "where c.commenter_Num = :id  order by c.board_comment_num desc",nativeQuery = true) 
	  List<Long> getCommentById(Long id);
	 
	 
	  
	//호성 end


}
