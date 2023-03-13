package com.keduit.helloworld.service;

import java.util.List;

import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.dto.PointAccountDTO;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.entity.PointAccount;

public interface PointAccountService {
	
	/** 포인트 거래내역 등록(create) */
	Long register(PointAccountDTO dto); 
	
	/** 포인트 거래내역 리스트 조회(read) */
	List<PointAccountDTO>read(Long memberNum);
	
	/** 포인트 증감(update) */
	void modify(Long myNum, Long yourNum, Long payment);
	
	/** 카카오페이 포인트 충전(모달)  */
	public boolean chargePoint(Long memberNum, Long charge);
	
	
	/** DTO에 있는 정보를 Entity로 옮기기 */
	default PointAccount pointAccountDtoToEntity(PointAccountDTO dto) {
		
		PointAccount entity = PointAccount.builder()
				.pointNum(dto.getPointNum())
				.memberNum(dto.getMemberNum())
				.charge(dto.getCharge())
				.balance(dto.getBalance())
				.exchange(dto.getExchange())
				.build();
		return entity;
	}
	
	/** Entity에 있는 정보를 DTO로 옮기기 */
	default PointAccountDTO pointAccountEntityToDto(PointAccount entity) {
		
		PointAccountDTO dto = PointAccountDTO.builder()
				.pointNum(entity.getPointNum())
				.memberNum(entity.getMemberNum())
				.charge(entity.getCharge())
				.balance(entity.getBalance())
				.exchange(entity.getExchange())
				.regDate(entity.getRegDate())
				.updateDate(entity.getUpdateDate())
				.build();
		return dto;
	}

	
//호성 23.02.21
	
	// 충전내역 추가하기
	Long setCharge(Long id, Long point, Long charge, MemberDTO memdto);
	// 환전 내역 추가하기
	Long setExCharge(Long id, Long point, Long excharge, MemberDTO memdto);
	
	/** memberEntity에 있는 정보를 memberDTO로 옮기기 */
	default MemberDTO memberEntityToMemberDto(Member entity) {
		
		MemberDTO dto = MemberDTO.builder().memberNum(entity.getMemberNum())
										   .id(entity.getId())
										   .pw(entity.getPw())
										   .name(entity.getName())
										   .point(entity.getPoint())
										   .purview(entity.getPurview())
										   .nickname(entity.getNickname())
										   .introduce(entity.getIntroduce())
										   .email(entity.getEmail())
										   .exvalue(entity.getExvalue())
										   .url(entity.getUrl())
										   .regDate(entity.getRegDate())
										   .updateDate(entity.getUpdateDate())
										   .build();
		return dto;
	}
	/** memberDTO에 있는 정보를 memberEntity로 옮기기*/
	default Member memberDtoToMemberEntity(MemberDTO dto) {
		

		Member entity = Member.builder().memberNum(dto.getMemberNum())
										.id(dto.getId())
										.pw(dto.getPw())
										.name(dto.getName())
										.point(dto.getPoint())
										.purview(dto.getPurview())
										.nickname(dto.getNickname())
										.introduce(dto.getIntroduce())
										.email(dto.getEmail())
										.exvalue(dto.getExvalue())
										.url(dto.getUrl())
										.build();
		
		return entity;
	}
	
	// 호성 end
	

}


