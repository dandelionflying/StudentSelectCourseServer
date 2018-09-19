package action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.CourseDao;
import dao.TeacherDao;
import model.Course;

public class CourseAction {
	//判断是否校选课
	public boolean isUcourse(String cid) throws SQLException {
		CourseDao dao = new CourseDao();
		Course course = new Course();
		course = dao.queryId(cid);
		return course.isCourseUniversity();
	}
	// 获取课程编号对应的课程对象
	public Course get_course(String cid) throws SQLException {
		CourseDao dao = new CourseDao();
		Course course = new Course();
		course = dao.queryId(cid);
		return course;
	}
	// 判断是否存在该课程
	public boolean iscourse(String cid) throws SQLException { // 是否为课程
		CourseDao dao = new CourseDao();
		List<Course> course = new ArrayList<Course>();
		course = dao.query();
		Iterator<Course> iterator = course.iterator();
		boolean flag = false;
		while (iterator.hasNext()) {
			Course c = iterator.next();
			if (cid.equals(c.getCourseID())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	// 获取课程教师姓名
	public String get_name(String tid) throws SQLException {
		TeacherDao dao = new TeacherDao();
		return dao.queryId(tid).getUserName();
	}
}
