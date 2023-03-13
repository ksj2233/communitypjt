package com.keduit.helloworld.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Board extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** Board 고유 pk */
	private Long boardNum;
	
	@Column(length = 100, nullable = false)
	/** Board 제목 */
	private String title;
	
	@Column(length = 3000, nullable = false)
	/** Board 내용 */
	private String content;
	
	/** member테이블과 fk */
	private Long memberNum;	// 맴버테이블과 fk
	
	@Column(length = 100)
	/** Board 사진 */
	private String url;
	
	@ColumnDefault("0")
	/** Board 조회수 */
	private Long views;
	
	@ColumnDefault("0")
	/** Board 댓글수 */
	private Long cnt;

	@ColumnDefault("0")
	private Long blikes;
	
	@Column(length = 100)
	/** Board 해시태그 */
	private String tag;
	
	/** 0: 공지사항  1: 무료게시판   2: QnA*/
	private Long boardcase;
	
	public void change(String title, String content, String tag) {
		this.title = title;
		this.content = content;
		this.tag = tag;
	}
	
	public void updateViews(Long countViews) {
		views = countViews;
	}
	
	public void countUp(Long blike) {
		this.blikes = blike+1;
	}
	
	public void countDown(Long blike) {
		this.blikes = blike-1;
	}
}
