package com.keduit.helloworld.service;

import java.util.List;

import com.keduit.helloworld.dto.MemberAccountDTO;
import com.keduit.helloworld.entity.MemberAccount;

public interface MemberAccountService {

	/** 회원간 거래내역 등록(create) */
	Long register(MemberAccountDTO dto);
	
	/** 회원간 거래내역 리스트 조회(read, 구매자=질문자 기준) */
	List<MemberAccountDTO> getListAsBuyer(Long memberBuyer);
	
	/** 회원간 거래내역 리스트 조회(read, 판매자=답변자 기준) */
	List<MemberAccountDTO> getListAsSeller(Long memberSeller);
	
	/** DTO에 있는 정보를 Entity로 옮기기 */
	default MemberAccount memberAccountDtoToEntity(MemberAccountDTO dto) {

		MemberAccount entity = MemberAccount.builder()
				.accountNum(dto.getAccountNum())
				.memberBuyer(dto.getMemberBuyer())
				.memberSeller(dto.getMemberSeller())
				.payment(dto.getPayment())
				.build();
		return entity;
	}
	
	/** Entity에 있는 정보를 DTO로 옮기기 */
	default MemberAccountDTO memberAccountEntityToDto(MemberAccount entity) {
		
		MemberAccountDTO dto = MemberAccountDTO.builder()
				.accountNum(entity.getAccountNum())
				.memberBuyer(entity.getMemberBuyer())
				.memberSeller(entity.getMemberSeller())
				.payment(entity.getPayment())
				.regDate(entity.getRegDate())
				.updateDate(entity.getUpdateDate())
				.build();
		return dto;
	}
	

}
