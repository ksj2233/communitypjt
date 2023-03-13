package com.keduit.helloworld.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.ColumnDefault;

import com.keduit.helloworld.entity.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
public class MemberDTO {
	/** member pk*/
	private Long memberNum;
	
	/** member 아이디*/
	private String id;
	
	/** member 비밀번호*/
	private String pw;
	
	/** member 이름*/
	private String name;
	
	/** member 가지고있는 포인트*/
	private Long point;
	
	/** member 권한 */
	private Boolean purview;
	
	/** member 닉네임*/
	private String nickname;
	
	/** member 자기소개*/
	private String introduce;
	
	/** member 이메일*/
	private String email;
	
	/** member 경험치*/
	private Long exvalue;
	
	/** member 사진이름*/
	private String url;
	
	/** member 저장 날짜*/
	protected LocalDateTime regDate;
	
	/** member 수정 날짜*/
	protected LocalDateTime updateDate;
	
	
}
