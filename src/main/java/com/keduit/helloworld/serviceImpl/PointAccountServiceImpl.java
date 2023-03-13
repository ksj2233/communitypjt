package com.keduit.helloworld.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.dto.PointAccountDTO;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.entity.MemberAccount;
import com.keduit.helloworld.entity.PointAccount;
import com.keduit.helloworld.repository.MemberAccountRepository;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.repository.PointAccountRepository;
import com.keduit.helloworld.service.PointAccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PointAccountServiceImpl implements PointAccountService {

	private final PointAccountRepository pointAccountRepository;
	private final MemberAccountRepository memberAccountRepository;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	/** (DTO에서 Entity로) 포인트 거래내역 등록(create) */
	public Long register(PointAccountDTO dto) {

		log.info("PointAccount ServiceImpl ---------- : " + dto);

		PointAccount entity = pointAccountDtoToEntity(dto);
		log.info("PointAccount DTO 에서 Entity로 값 넣기 : " + entity);

		pointAccountRepository.save(entity);

		return entity.getMemberNum();
	}

	@Override
	/** (Entity 정보로) 포인트 거래내역 리스트 조회(read) */
	public List<PointAccountDTO> read(Long num) {

		List<PointAccount> result = pointAccountRepository.getPointAccount(num);
		List<PointAccountDTO> list = new ArrayList<>();

		for (PointAccount pa : result) {
			PointAccountDTO pointAccountDTO = pointAccountEntityToDto(pa);
			list.add(pointAccountDTO);
		}
		return list;
	}

	@Override
	/** 포인트 증감(update) - Q&A 댓글에서 결제 시 상호 포인트 변경 */
	public void modify(Long myNum, Long yourNum, Long payment) {
		Optional<Member> myResult = memberRepository.findById(myNum);
		Optional<Member> yourResult = memberRepository.findById(yourNum);

		if(myResult.isPresent() && yourResult.isPresent()){
			Member myMem = myResult.get();
			Member yourMem = yourResult.get();

			myMem.changePoint(myMem.getPoint() - payment);
			yourMem.changePoint(yourMem.getPoint() + payment);

			MemberAccount memberAccount = MemberAccount
					.builder()
					.memberBuyer(myNum)
					.memberSeller(yourNum)
					.payment(payment)
					.build();

			memberRepository.save(myMem);
			memberRepository.save(yourMem);
			memberAccountRepository.save(memberAccount);
		}
	}		
	
	@Override
	/** 카카오페이 포인트 충전(모달) */
	public boolean chargePoint(Long memberNum, Long charge) {
		Optional<Member> result = memberRepository.findById(memberNum);
		if(result.isPresent()) {
			Member member = result.get();
			member.changePoint(member.getPoint() + (charge * 100)); //현금 -> 포인트 100배 충전
			
			PointAccount pointAccount = PointAccount
					.builder()
					.memberNum(memberNum)
					.charge(charge)
					.balance(member.getPoint())
					.exchange(0L)
					.build();
			pointAccountRepository.save(pointAccount);
			memberRepository.save(member);
		}
		return result.isPresent();
	}
	
	
// 호성 23.02.21

	@Override
	public Long setCharge(Long id, Long point, Long charge, MemberDTO memdto) {
		PointAccountDTO dto = PointAccountDTO.builder().memberNum(id).charge(charge).balance(point + (charge * 100))// 100배
				.build();
		PointAccount entity = pointAccountDtoToEntity(dto);
		memdto.setMemberNum(id);
		memdto.setPoint(point + (charge*100));
		Member member = memberDtoToMemberEntity(memdto);
				
		memberRepository.save(member);
		pointAccountRepository.save(entity);
		
		return entity.getMemberNum();
	}

	@Override
	public Long setExCharge(Long id, Long point, Long excharge, MemberDTO memdto) {
		PointAccountDTO dto = PointAccountDTO.builder().memberNum(id).exchange(excharge).balance(point - (excharge * 100))// 100배
				.build();
		PointAccount entity = pointAccountDtoToEntity(dto);
		memdto.setMemberNum(id);
		memdto.setPoint(point - (excharge*100));
		Member member = memberDtoToMemberEntity(memdto);
		memberRepository.save(member);
		pointAccountRepository.save(entity);

		return entity.getMemberNum();
	}

// 호성 end
	
	
}
