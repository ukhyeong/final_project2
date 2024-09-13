package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;


@Data
// 복합키를 정의하기 위한 클래스
public class LectureId implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	
	private String lectureName;
	
    private Professor professor;
	
} // end class
