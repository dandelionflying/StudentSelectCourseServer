package model;


/************
 * 学生类
 ************/
public class Student extends User {
	
	private String institudeOf;    //学生所属学院
	private String majorOf;         //学生所属专业
	private String classOf;       //学生所属班级
	private boolean universityCourse; //学生是否选择校选课
	
	public boolean isUniversityCourse() {
		return universityCourse;
	}

	public void setUniversityCourse(boolean universityCourse) {
		this.universityCourse = universityCourse;
	}

	public String getInstitudeOf() {
		return institudeOf;
	}
	
	public void setInstitudeOf(String institudeOf) {
		this.institudeOf = institudeOf;
	}
	
	public String getMajorOf() {
		return majorOf;
	}
	
	public void setMajorOf(String majorOf) {
		this.majorOf = majorOf;
	}
	
	public String getClassOf() {
		return classOf;
	}
	
	public void setClassOf(String classOf) {
		this.classOf = classOf;
	}
	
}
