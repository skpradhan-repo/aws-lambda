/**
 * 
 */
package com.samsoft.aws.lambda.studentgrade.entity;

/**
 * @author SAMARESH
 *
 */
public class Student {
	private int rollNo;
	private String name;
	private int marks;
	private Character grade;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(int rollNo, String name, int marks, Character grade) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.marks = marks;
		this.grade = grade;
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public Character getGrade() {
		return grade;
	}

	public void setGrade(Character grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return this.getRollNo() + " : " + this.getName() + " : " + this.getGrade() + " : " + this.getMarks();
	}
}
