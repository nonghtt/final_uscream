package com.example.uscream.emp;

import java.util.Date;

import com.example.uscream.grade.Grade;
import com.example.uscream.store.Store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpDto {
	private int empnum; 	// 직원 번호
	private Store storeid;	// 지점 번호
	private String empname;	// 직원 이름
	private Date joindate;	// 입사 날짜
	private Grade grade;		// 등급
	private String color;	// 컬러 
}