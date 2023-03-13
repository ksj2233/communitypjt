package com.keduit.helloworld.service;



import java.util.List;

import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.keduit.helloworld.dto.FavoritesDTO;
import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.dto.PageRequestDTO;

import com.keduit.helloworld.entity.Favorites;
import com.keduit.helloworld.entity.Member;

public interface FavoritesService {

	/** 즐겨찾기 추가하기 */
	void register(Long myNum, Long youNum);
	
	/** 즐겨찾기 읽어오기(bookmarker를 읽어와서 bookmarked 출력해야함 */
	List<Member> read(FavoritesDTO dto);
	
	/** 즐겨찾기 삭제 */
	void remove(Long myNum, Long youNum);
	
	/** 즐겨찾기Entity에 있는 정보를 즐겨찾기DTO로 옮기기 */
	default FavoritesDTO EntityToDto(Favorites entity) {
		MemberDTO memberDTO = MemberDTO.builder().memberNum(entity.getBookmarked()).build();
		
		FavoritesDTO dto = FavoritesDTO.builder().bookmarker(entity.getBookmarker())
												 .bookmarked(memberDTO.getMemberNum())
												 .build();
		return dto;
	};
	
	/** 즐겨찾기DTO에 있는 정보를 즐겨찾기Entity로 옮기기 */
	default Favorites DtoToEntity(FavoritesDTO dto) {
		
		Member member = Member.builder().memberNum(dto.getBookmarked()).build();
		
		Favorites entity = Favorites.builder().bookmarker(dto.getBookmarker())
											  .bookmarked(member.getMemberNum())
											  .build();
		
		return entity;
	};
	
	/** 즐겨찾기 정보 검색하기 */
	BooleanBuilder getSearch(PageRequestDTO requestDTO);

	/**내가 즐겨찾기 했는지*/
	int getCount(Long memberNum, Long youNum);
	
}
