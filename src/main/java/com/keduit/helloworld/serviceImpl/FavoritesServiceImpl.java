package com.keduit.helloworld.serviceImpl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.keduit.helloworld.dto.FavoritesDTO;
import com.keduit.helloworld.dto.PageRequestDTO;
import com.keduit.helloworld.entity.Favorites;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.FavoritesRepository;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.service.FavoritesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@RequiredArgsConstructor
@Log4j2
public class FavoritesServiceImpl implements FavoritesService{

	private final FavoritesRepository favoritesRepository;
	
	
	@Override
	public void register(Long myNum, Long youNum) {

		Favorites favorites = Favorites.builder()
												.bookmarker(myNum)
												.bookmarked(youNum)
												.build();
		favoritesRepository.save(favorites); 
		
	}

	@Override
	public List<Member> read(FavoritesDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Long myNum, Long youNum) {

		Long favorites = favoritesRepository.getNum(myNum, youNum);
		favoritesRepository.deleteById(favorites);
		
	}

	@Override
	public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	/**내가 즐겨찾기 했는지?*/
	@Override
	public int getCount(Long memberNum, Long youNum) {
		int count = favoritesRepository.getCount(memberNum,youNum);
		
		return count;
	}

	
	
}
