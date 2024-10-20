package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.myapp.common.CommonEntityLifecyleListener;

import jakarta.persistence.Basic;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;


@Data

@EntityListeners({ CommonEntityLifecyleListener.class, AuditingEntityListener.class })

// JpaAudit 클래스는 JPA 엔티티를 위한 추상 슈퍼클래스로, 
// 생성 및 업데이트 타임스탬프와 같은 감사(auditing) 기능을 자동으로 추적합니다.
@MappedSuperclass
public abstract class JpaAudit implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	@CurrentTimestamp(event = EventType.INSERT)
	@Basic(optional = false)
	protected Date createDate;
	
	@CurrentTimestamp(event = EventType.UPDATE)
	@Basic(optional = true)
	protected Date updateDate;
	
} // end class

