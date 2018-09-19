package model;

/**********
 * 教师类
 * ********/

public class Teacher extends User{
	private String instituteOf;           //教师所属学院
	
	public String getInstituteOf() {
		return instituteOf;
	}
	
	public void setInstituteOf(String instituteOf) {
		this.instituteOf = instituteOf;
	}
	
	
}
