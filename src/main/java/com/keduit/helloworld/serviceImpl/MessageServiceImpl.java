package com.keduit.helloworld.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.keduit.helloworld.dto.MessageDTO;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.entity.Message;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.repository.MessageRepository;
import com.keduit.helloworld.service.MessageService;
import com.keduit.helloworld.service.TimeChangeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageServiceImpl implements MessageService {


	private final MessageRepository messageRepository;
	private final MemberRepository memberRepository;
	private final TimeChangeService timeChangeService;

	@Override
	/** 쪽지 등록(create) */
	public Long register(MessageDTO messageDTO) {

		log.info("Message ServiceImpl register");

		Message entity = messageDtoToEntity(messageDTO);
		
		messageRepository.save(entity);

		return entity.getMessageNum();
	}

	@Override
	/** 받은 쪽지 리스트 조회(list, 받은사람 기준, 권한 0 or 1만 출력) */
	public List<MessageDTO> getListAsGetter(Long memberGet) {  // 꺼진불도 다시보자 正

		List<Message> result = messageRepository.getMsgInfoAsGetter(memberGet); //받은 쪽지 정보
		List<Member> mlist = memberRepository.getUrInfoAsGetter(memberGet); //쪽지 상대 정보
		List<MessageDTO> list = new ArrayList<>();

		for (int i = 0; i < result.size(); i++) {

			MessageDTO messageDto = messageEntityToDto(result.get(i)); //받은 쪽지 정보

			messageDto.setNickname(mlist.get(i).getNickname()); //쪽지 상대 닉네임 셋
			messageDto.setMemUrl(mlist.get(i).getUrl()); //쪽지 상대 프사 셋
			messageDto.setId(mlist.get(i).getId()); //쪽지 상대 아이디 셋
			messageDto.setChangeDate(timeChangeService.calculateTime(result.get(i).getRegDate())); // 시간 이쁘게 바꾸기

			list.add(messageDto);
		}
		return list;
	}


	@Override
	/** 보낸 쪽지 리스트 조회(list, 보낸사람 기준, 권한 0 or 2만 출력) */
	public List<MessageDTO> getListAsGiver(Long memberGive) {  // 꺼진불도 다시보자 正

		List<Message> result = messageRepository.getMsgInfoAsGiver(memberGive); //보낸 쪽지 정보
		List<Member> mlist = memberRepository.getUrInfoAsGiver(memberGive); //쪽지 상대 정보

		List<MessageDTO> list = new ArrayList<>();

		for (int i = 0; i < result.size(); i++) {

			MessageDTO messageDto = messageEntityToDto(result.get(i)); //보낸 쪽지 정보

			messageDto.setNickname(mlist.get(i).getNickname()); //쪽지 상대 닉네임 셋
			messageDto.setMemUrl(mlist.get(i).getUrl()); //쪽지 상대 프사 셋
			messageDto.setId(mlist.get(i).getId()); //쪽지 상대 아이디 셋
			messageDto.setChangeDate(timeChangeService.calculateTime(result.get(i).getRegDate())); // 시간 이쁘게 바꾸기

			list.add(messageDto);
		}
		return list;
	}

	@Override
	/** 쪽지 상세 조회(read) */
	public MessageDTO read(Long messageNum, Long memberNum) { // 꺼진불도 다시보자 正

		Optional<Message> result = messageRepository.findById(messageNum); //쪽지번호로 구분
		Optional<Member> memResult = null;
		MessageDTO messageDTO = null;

		if(result.isPresent()) {

			messageDTO = messageEntityToDto(result.get()); //쪽지 정보

			if(messageDTO.getMemberGet() == memberNum) { 							//쪽지 받은 사람이면
				memResult = memberRepository.findById(messageDTO.getMemberGive()); // 쪽지 보낸 사람 정보를
			}else { 																//아니면(쪽지 보낸사람이면),
				memResult = memberRepository.findById(messageDTO.getMemberGet()); 	// 쪽지 받은 사람 정보를
			}

			messageDTO.setNickname(memResult.get().getNickname()); //쪽지 상대 닉네임 셋
		}
		return messageDTO;
	}

	@Override
	/** 받은 사람이 쪽지 삭제 시 보기권한 변경 & 최종 삭제(update: 보기권한 +2, delete: 권한 3일때) */
	public void modifyViewAsGetter(Long messageNum, Long view) { // 꺼진불도 다시보자 正

		Optional<Message> result = messageRepository.findById(messageNum); //쪽지번호로 구분

		if(result.isPresent()) {

			Message entity = result.get();

			entity.changes(view + 2); //보기권한 0은 2, 1은 3으로 변경

			if(entity.getView() >= 3) {
				messageRepository.deleteById(messageNum); //권한 3이면 삭제
			}else {
				messageRepository.save(entity); //권한 3 아니면 업데이트
			}
		}
	}

	@Override
	/** 보낸 사람이 쪽지 삭제 시 보기권한 변경 & 최종 삭제(update: 보기권한 +1, delete: 권한 3일때) */
	public void modifyViewAsGiver(Long messageNum, Long view) { // 꺼진불도 다시보자 正

		Optional<Message> result = messageRepository.findById(messageNum); //쪽지번호로 구분

		if(result.isPresent()) {

			Message entity = result.get();

			entity.changes(view + 1); //보기권한 0은 1, 2는 3으로 변경

			if(entity.getView() >= 3) {
				messageRepository.deleteById(messageNum); //권한 3이면 삭제
			} else {
				messageRepository.save(entity); //권한 3 아니면 업데이트
			}
		}
	}

	// 승민
	@Override
	public Page<MessageDTO> getMessages(Pageable pageable) {

		return messageRepository.findAll(pageable).map(message -> messageEntityToDto(message));
	}

	@Override
	public Page<MessageDTO> getKeywordMessages(String select,
											   String msgGive,
											   String msgGet,
											   String msgNum,
											   Pageable pageable) {
		return messageRepository.findByGiveGetBCN(msgGive,msgGet,msgNum,pageable).map(msg -> messageEntityToDto(msg));
	}
	//승민 끝


}
