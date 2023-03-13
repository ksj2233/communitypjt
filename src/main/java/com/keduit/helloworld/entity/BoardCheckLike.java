package com.keduit.helloworld.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BoardCheckLike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 좋아요 확인 고유넘버 */
    private Long checklikeId;

    @Column
    /** 좋아요 확인 좋아요 누른 댓글 */
    private Long boardNum;

    @Column
    /** 좋아요 누른 사람의 pk*/
    private Long memberNum;
}