package com.keduit.helloworld.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	
	/** Board 고유 pk */
	private Long boardNum;
	
	/** Board 제목 */
	private String title;
	
	/** Board 내용 */
	private String content;
	
	/** member테이블과 fk */
	private Long memberNum;
	
	/** Board 사진 */
	private String url;
	
	/** Board 조회수 */
	private Long views;
	
	/** Board 댓글수 */
	private Long cnt;
	
	/** Board 해시태그 */
	private String tag;
	
	/** Board 게시판구분 0: 공지사항  1: 무료게시판  2: QnA */
	private Long boardcase;

	/** Board 좋아요 */
	private Long blikes;
	
	/** member 아이디*/
	private String id;
	
	/** member 닉네임*/
	private String nickname;
	
	/** member url*/
	private String memberurl;
	
	/** Board_comment 작성,수정 날짜 */
	/** Board_comment 작성,수정 날짜 */
	private LocalDateTime regdate, updatedate; 
}
