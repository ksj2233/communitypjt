package com.keduit.helloworld.serviceImpl;

import com.keduit.helloworld.dto.MemberDTO;
import com.keduit.helloworld.entity.Board;
import com.keduit.helloworld.entity.Member;
import com.keduit.helloworld.repository.BoardRepository;
import com.keduit.helloworld.repository.CommentRepository;
import com.keduit.helloworld.repository.MemberRepository;
import com.keduit.helloworld.repository.MessageRepository;
import com.keduit.helloworld.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final MessageRepository messageRepository;


    @Override
    public void memberRemove(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public MemberDTO memberRead(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        return result.isPresent()? memberETD(result.get()) : null;
    }

    @Override
    public void passwordUpdate(Long memberId, String password) {
        Member member = Member
                .builder()
                .memberNum(memberId)
                .pw(password)
                .build();

        memberRepository.save(member);
    }

    @Override
    public void pointUpdate(Long memberId, Long point) {
        Member member = Member
                .builder()
                .memberNum(memberId)
                .point(point)
                .build();

        memberRepository.save(member);
    }

    @Override
    public void exUpdate(Long memberId, Long ex) {
        Member member = Member
                .builder()
                .memberNum(memberId)
                .exvalue(ex)
                .build();

        memberRepository.save(member);
    }

    @Override
    public void nicknameUpdate(Long memberId, String nickname) {
        Member member = Member
                .builder()
                .memberNum(memberId)
                .nickname(nickname)
                .build();

        memberRepository.save(member);
    }

    @Override
    public void boardRemove(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public void blindBoard(Long boardId) {
        Board board = Board
                .builder()
                .boardNum(boardId)
                .boardNum(99L)
                .build();
        boardRepository.save(board);
    }

    @Override
    public void commentRemove(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void messageRemove(Long messageId) {
        messageRepository.deleteById(messageId);
    }
}
