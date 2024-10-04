package org.zerock.myapp.domain;

import java.io.Serializable;

import org.zerock.myapp.entity.LectureDay;
import org.zerock.myapp.entity.Professor;

import lombok.Data;


@Data
public class LectureDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String lectureName;
    private Professor professor;
	private LectureDay lectureDay;
	private Integer lectureTime;
	
	private Integer currPage = 1;	// 현재 페이지 번호 수집
	private Integer pageSize = 10;	// 한 페이지당 보여줄 게시물 갯수 수집
} // end class