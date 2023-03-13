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
public class Message extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** 쪽지 쪽지번호 pk, nn, ai */
	private Long messageNum;
	
	@Column(nullable = false) 
	/** 쪽지 받은사람 fk, nn */
	private Long memberGet; //회원번호(멤버):받은사람 = 1:n

	@Column(nullable = false)
	/** 쪽지 보낸사람 fk, nn */
	private Long memberGive; //회원번호(멤버):보낸사람 = 1:n

	@Column(nullable = false)
	/** comment 고유번호 fk */
	private Long boardCommentNum;
	
	@Column(length = 100, nullable = false)
	/** 쪽지 제목 nn */
	private String title;
	
	@Column(length = 3000, nullable = false)
	/** 쪽지 내용 nn */
	private String content;
	
	@Column
	/** 쪽지 보기권한 0:기본, 1:보낸사람이 삭제, 2:받은사람이 삭제, 3:모두 삭제 */
	private Long view;
	
	@Column(length = 100)
	/** 쪽지 사진 */
	private String url;
	
	/** 보기 권한 변경 */
	public void changes(Long view) {
		this.view = view;
	}

}
