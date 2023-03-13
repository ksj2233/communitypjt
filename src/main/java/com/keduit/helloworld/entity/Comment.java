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
public class Comment extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** comment 고유번호 pk */
	private Long boardCommentNum;
	
	/** board 테이블과 fk  */
	private Long boardNum;    //  fk
	
	@Column(length = 1000, nullable = false)
	/** comment 내용 */
	private String commentContent;  
	
	@Column(length = 100)
	/** comment 미리보기 사진 */
	private String viewpicture;
	
	@ColumnDefault("0")
	/** comment 가격 */
	private Long price;
	
	@Column(length = 100)
	/** comment 사진 */
	private String url;

	/** comment 추천수 */
	@ColumnDefault("0")
	private Long clikes;
	
	/** member테이블과 fk */
	private Long commenterNum;	// 맴버테이블과 fk

	
	public void countUp(Long clike) {
		this.clikes = clike+1;
	}
	
	public void countDown(Long clike) {
		this.clikes = clike-1;
	}
	

}
