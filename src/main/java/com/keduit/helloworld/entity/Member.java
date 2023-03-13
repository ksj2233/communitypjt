package com.keduit.helloworld.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(indexes = {@Index(name = "member_index",columnList = "id")})			//맴버에 인덱스 추가
public class Member extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** member 테이블 고유 pk */
	private Long memberNum;
	
	@Column(length =100, nullable = false, unique = true)
	/** member ID */
	private String id;
	
	@Column(length = 100, nullable = false)
	/** member 비밀번호 */
	private String pw;
	
	@Column(length = 100, nullable = false)
	/** member 이름 */
	private String name;
	
	@ColumnDefault("0") 
	/** member 현재 가지고있는 포인트 */
	private Long point;
	
	
	/** 권한*/
	private Boolean purview;
	
	@Column(length = 100, nullable = false, unique = true)
	/** member 닉네임 */
	private String nickname;
	
	@Column(length = 1000)
	/** member 자기소개 */
	private String introduce;
	
	@Column(length = 300)
	/** member 이메일 */
	private String email;
	
	@ColumnDefault("0") 
	/** member 경험치(높을수록 티어가오름) */
	private Long exvalue;
	
	@Column(length = 100)
	/** member 사진 */
	private String url;
	
	/** member와 memberRole연결 */
	@ElementCollection(fetch= FetchType.EAGER)
	@Builder.Default
	private Set<MemberRole> roleset = new HashSet<>();
	
	//호성 23.02.20
	
	public void change(Long memberNum, String id, String pw, String name,String nickname
			,String introduce,String email, LocalDateTime updateDate, String url) {
		this.memberNum = memberNum;
		this.id=id;
		this.pw= pw;
		this.name=name;
		this.nickname = nickname;
		this.introduce = introduce;
		this.email = email;
		this.updateDate = updateDate;
		this.url = url;
	}
	
	//호성 end
	
	public void changePw(String pw) {
		this.pw = pw;
	}
	
	public void changeName(String name) {
		this.name = name;
	}
	
	/** 값 넣어주기*/
	public void addMemberRole(MemberRole memberRole) {
		roleset.add(memberRole);
	}
	
	public void CopounPoint(Long coupon) {
		this.point = this.point + coupon;
	}

	// 승민
	public void changePoint(Long point){
		this.point = point;
	}

	public void sumExe(long l) {
		this.exvalue += l;
		
	}
	
	
}
