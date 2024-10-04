package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecyleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data

@EntityListeners({ 
	CommonEntityLifecyleListener.class,
	
	// 스프링부트의 실행클래스에 붙여놓은, @EnableJpaAuditing 어노테이션대로
	// 각 엔티티 클래스의 @CreateDate, @LastModifiedDate 어노테이션이, 
	// 감사자료(최초등록일시, 최종수정일시)를 자동생성하려면,
	// 아래의 리스터 타입정보를 추가로 제공해야 합니다.
	AuditingEntityListener.class 
})

@Entity(name = "Board")
@Table(name = "board")
public class Board implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	// 1. PK 속성
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;
	
	
	// 2. 일반속성
	@Basic(optional = false)
	private String title;
	
	@Basic(optional = false)
	private String writer;
	
	@Basic
	private String content;
	
//	@Basic(optional = false)
//	private Integer cnt = 0;
	
	@Column(columnDefinition = "INTEGER default 0", insertable = false, nullable = false)
	private Integer cnt;
	
	
	// 3. 정보통신망법에 따른 속성
//	@CreatedDate								// 1, With @EnableJpaAuditing & Low hibernate
//	@CreationTimestamp 							// 2, @Until hibernate v5.x
	@CurrentTimestamp(event = EventType.INSERT)	// 3, @Since hibernate v6.x (Best Practice)
	@Basic(optional = false)
	private Date createDate;
	
	// 주의사항: 만일, View(화면)에서, 날짜 데이터를 포맷팅하려고 한다면,
	//           아래와 같이 JODA TIME Types은 사용하지 말것!!!
//	@LastModifiedDate							// 1, With @EnableJpaAuditing & Low hibernate
//	@UpdateTimestamp 							// 2, @Until hibernate v5.x
	@CurrentTimestamp(event = EventType.UPDATE)	// 2, @Since hibernate v6.x (Best Practice)
	@Basic
	private Date updateDate;
	
	
	// 4. 연관관계 매핑
	
	

} // end class


