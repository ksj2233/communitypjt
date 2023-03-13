package com.keduit.helloworld.repository.search;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.keduit.helloworld.entity.Board;
import com.keduit.helloworld.entity.QBoard;
import com.keduit.helloworld.entity.QComment;
import com.keduit.helloworld.entity.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

	public SearchBoardRepositoryImpl() {
		super(Board.class);
	}
	
	@Override
	public Page<Object[]> searchBoard1Page(String type, String keyword, Pageable pageable) {
		Long boardcase = 1L;
		
		return searchPage(type,keyword,pageable,boardcase);
	}
	
	@Override
	public Page<Object[]> searchBoard2Page(String type, String keyword, Pageable pageable) {
		Long boardcase = 2L;
		
		return searchPage(type,keyword,pageable,boardcase);
	}
	
	@Override
	public Page<Object[]> searchBoard3Page(String type, String keyword, Pageable pageable) {
		Long boardcase = 3L;
		
		return searchPage(type,keyword,pageable,boardcase);
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable, Long boardcase) {
		log.info("위치 : SearchBoardRepository searchPage()");
		
		QBoard board = QBoard.board;
		QComment comment = QComment.comment;
		QMember member = QMember.member;
		
		JPQLQuery<Board> jpqlQuery = from(board);
		jpqlQuery.innerJoin(member).on(board.memberNum.eq(member.memberNum));
		jpqlQuery.leftJoin(comment).on(comment.boardNum.eq(board.boardNum));
		
		JPQLQuery<Tuple> tuple = jpqlQuery.select(board,member, comment.count());
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression expression = board.boardNum.gt(0L); 
		BooleanExpression caseexpression;
		caseexpression = board.boardcase.eq(boardcase);
		
		booleanBuilder.and(expression);
		booleanBuilder.and(caseexpression);
		
		if(type != null) {
			
			String[] typeArr = type.split("");
			
			BooleanBuilder conditionBuilder = new BooleanBuilder();
			
			for(String t : typeArr) {
				switch(t) {
				case "t":
					conditionBuilder.or(board.title.contains(keyword));
					break;
				case "c":
					conditionBuilder.or(board.content.contains(keyword));
					break;
				case "w":
					conditionBuilder.or(member.nickname.contains(keyword));
					break;
				case "o":
					conditionBuilder.or(board.tag.contains(keyword));
					break;
				}
			}
			
			booleanBuilder.and(conditionBuilder);
			
		}
		tuple.where(booleanBuilder);
		log.info("tuple"+tuple);
		
		
		Sort sort = pageable.getSort();
		
		sort.stream().forEach(order -> {
			Order direction = order.isAscending()? Order.ASC:Order.DESC;
			String prop = order.getProperty();
			
			PathBuilder orderByExpression = new PathBuilder<>(Board.class,"board");
			
			tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
			
		});
		
		tuple.groupBy(board);
		
//		page 처리
		
		tuple.offset(pageable.getOffset());
		tuple.limit(pageable.getPageSize());
		
		
		
		
		
//		log.info("=======================");
//		log.info("jpqlQuery : " + jpqlQuery);
		log.info("=======================");
		log.info("tuple : " + tuple);
		log.info("=======================");
		
//		List<Board> result = jpqlQuery.fetch();
		List<Tuple> result = tuple.fetch();
		
		log.info("위치 : SearchBoardImpl searchPage()");
		log.info("result : " + result);
		
		long count = tuple.fetchCount();
		
		log.info("count : " + count);
		
		return new PageImpl<Object[]>(result.stream()
				.map(t -> t.toArray()).collect(Collectors.toList()),
				pageable,
				count
				);
	}

	@Override
	public Object getBoardByBno(Long boardNum) {
		
		log.info("위치 : SearchBoardRepository getBoardByBno()");
		
		QBoard board = QBoard.board;
		QComment comment = QComment.comment;
		QMember member = QMember.member;
		
		JPQLQuery<Board> jpqlQuery = from(board);
		jpqlQuery.leftJoin(member).on(board.memberNum.eq(member.memberNum));
		jpqlQuery.leftJoin(comment).on(comment.boardNum.eq(board.boardNum));
		
		JPQLQuery<Tuple> tuple = jpqlQuery.select(board,member, comment.count());
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		BooleanExpression caseexpression;
		caseexpression = board.boardcase.eq(boardNum);
		
		booleanBuilder.and(caseexpression);
		
		tuple.where(booleanBuilder);
		
		List<Tuple> result = tuple.fetch();
		
		return result;
	}


}
