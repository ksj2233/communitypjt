package com.keduit.helloworld.service;

import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.entity.Member;

public interface AdminService {

    /** 멤버 삭제 */
    void memberRemove(Long memberId);
    /** 멤버 삭제 */
    MemberDTO memberRead(Long memberId);
    /** 패스워드 변경 */
    void passwordUpdate(Long memberId, String password);
    /** 포인트 변경 */
    void pointUpdate(Long memberId,Long point);
    /** 경험치 변경 */
    void exUpdate(Long memberId,Long ex);
    /** 닉네임 변경 */
    void nicknameUpdate(Long memberId,String nickname);
    /** 게시물 삭제 */
    void boardRemove(Long boardId);
    /** 게시물 블라인드 */
    void blindBoard(Long boardId);
    /** 댓글 삭제 */
    void commentRemove(Long commentId);
    /** 메시지 삭제 */
    void messageRemove(Long messageId);

    /** 멤버 ETD */
    default MemberDTO memberETD(Member entity) {

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


}
