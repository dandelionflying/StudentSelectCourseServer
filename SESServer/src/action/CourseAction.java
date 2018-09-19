package action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.CourseDao;
import dao.TeacherDao;
import model.Course;

public class CourseAction {
	//�ж��Ƿ�Уѡ��
	public boolean isUcourse(String cid) throws SQLException {
		CourseDao dao = new CourseDao();
		Course course = new Course();
		course = dao.queryId(cid);
		return course.isCourseUniversity();
	}
	// ��ȡ�γ̱�Ŷ�Ӧ�Ŀγ̶���
	public Course get_course(String cid) throws SQLException {
		CourseDao dao = new CourseDao();
		Course course = new Course();
		course = dao.queryId(cid);
		return course;
	}
	// �ж��Ƿ���ڸÿγ�
	public boolean iscourse(String cid) throws SQLException { // �Ƿ�Ϊ�γ�
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
	// ��ȡ�γ̽�ʦ����
	public String get_name(String tid) throws SQLException {
		TeacherDao dao = new TeacherDao();
		return dao.queryId(tid).getUserName();
	}
}
