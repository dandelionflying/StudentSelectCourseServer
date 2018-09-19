package model;
/*
 *    课程类
 * */
public class Course {
	private String courseID;// 课程编号
	private String courseName;// 课程名称
	private double courseCredit;// 课程学分
	private String courseAddress;// 课程地址
	private String insitituteOf;// 所属学院
	private String majorOf;// 所属专业
	private String courseTime;// 上课时间
	private String courseTeacherId; // 一个教师对象
	private int stuMaxNum;// 最大容量
	private int remaindingQuantity;// 课程余量
	private boolean courseUniversity;// 是否校选
	private String courseType;//课程类型

	public int getRemaindingQuantity() {
		return remaindingQuantity;
	}

	public void setRemaindingQuantity(int remaindingQuantity) {
		this.remaindingQuantity = remaindingQuantity;
	}


	public void setCourseUniversity(boolean courseUniversity) {
		this.courseUniversity = courseUniversity;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getCourseCredit() {
		return courseCredit;
	}

	public void setCourseCredit(double courseCredit) {
		this.courseCredit = courseCredit;
	}

	public String getCourseAddress() {
		return courseAddress;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}

	public String getInsitituteOf() {
		return insitituteOf;
	}

	public void setInsitituteOf(String insitituteOf) {
		this.insitituteOf = insitituteOf;
	}

	public String getMajorOf() {
		return majorOf;
	}

	public void setMajorOf(String majorOf) {
		this.majorOf = majorOf;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public String getTea() {
		return courseTeacherId;
	}

	public void setTea(String courseTeacherId) {
		this.courseTeacherId = courseTeacherId;
	}

	public int getStuMaxNum() {
		return stuMaxNum;
	}

	public void setStuMaxNum(int stuMaxNum) {
		this.stuMaxNum = stuMaxNum;
	}

	public String getCourseTeacherId() {
		return courseTeacherId;
	}

	public void setCourseTeacherId(String courseTeacherId) {
		this.courseTeacherId = courseTeacherId;
	}

	public boolean isCourseUniversity() {
		return courseUniversity;
	}

}
