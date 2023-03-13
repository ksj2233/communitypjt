package com.keduit.helloworld.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.keduit.helloworld.dto.TetrisDTO;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.MemberRepository;
import org.springframework.stereotype.Service;

import com.keduit.helloworld.entity.Tetris;
import com.keduit.helloworld.repository.TetrisRepository;
import com.keduit.helloworld.service.TetrisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TetrisServiceImpl implements TetrisService {
	
	private final TetrisRepository tetrisRepository;
	private final MemberRepository memberRepository;


	@Override
	public void createRank(Long memberNum, Long score) {

		Tetris tetris = Tetris
				.builder()
				.memberNum(memberNum)
				.score(score)
				.build();
		tetrisRepository.save(tetris);
	}

	@Override
	public List<TetrisDTO> getMax3() {

		List<Tetris> result = tetrisRepository.max3();

		List<TetrisDTO> tetrisDTOS = new ArrayList<>();

		for(Tetris tetris : result){
			Optional<Member> member = memberRepository.findById(tetris.getMemberNum());
			if (member.isPresent()) {
				tetrisDTOS.add(tetrisETD(tetris, member.get()));
			}
		}
		
		return tetrisDTOS;
	}

}
