package action;

/**************
 * 
 * ѧ��������
 * 
 **************/
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.CourseDao;
import dao.StudentDao;
import dao.Student_courseDao;
import model.Course;
import model.Student;
import model.Student_course;

public class StudentAction {

	private Student_courseDao stuCourseDao = new Student_courseDao();;
	private StudentDao studentDao = new StudentDao();
	private CourseDao courseDao = new CourseDao();
	// ��ѯѧ�����˿γ̶���
	public List<Course> query_course(String sid) throws SQLException {
		List<Student_course> sc = new ArrayList<Student_course>();
		List<Course> course = new ArrayList<Course>();
		Course c = new Course();
		sc = stuCourseDao.querySid(sid);
		for (Student_course student_course : sc) {
			c = courseDao.queryId(student_course.getCourse_id());
			course.add(c);
		}
		return course;
	}
	// ��ӿγ̶���
	public boolean add_course(String sid, Course course) throws SQLException {
		Student_course sc = new Student_course();

		sc.setStudent_id(sid);
		sc.setCourse_id(course.getCourseID());
		boolean flag;
		flag = stuCourseDao.addStudent_course(sc);
		if (flag)
			courseDao.updateQuantity(course.getCourseID(), course.getRemaindingQuantity());
		return flag;
	}
	// ɾ���γ̶���
	public void del_course(String sid, String cid) throws SQLException {
		stuCourseDao.delStudent_course(sid, cid);
	}
	// ��ѯУѡ�γ̶���
	public List<Course> query_university_course() throws SQLException {
		List<Course> course = new ArrayList<Course>();
		List<Course> Ucourse = new ArrayList<Course>();
		course = courseDao.query();
		for (Course cs : course) {
			if (cs.isCourseUniversity()) {
				Ucourse.add(cs);
			}
		}
		return Ucourse;
	}
	// ��ѯȫ���γ̶���
	public List<Course> query_course() throws SQLException {
		List<Course> course = new ArrayList<Course>();
		List<Course> course2 = new ArrayList<Course>();
		course = courseDao.query();
		for (Course cs : course) {
			course2.add(cs);
		}
		return course2;
	}
	// �ж�ѧ���Ƿ���ѡ�ÿγ̶���
	public boolean query_course(String studentid, String courseid) throws SQLException {
		Student_course sc = null;
		sc = stuCourseDao.query_course(studentid, courseid);

		if (sc != null)
			return true;
		else
			return false;
	}
	// �������붯��
	public boolean revisePassWord(String userid, String newPassword) throws SQLException {
		return studentDao.resetPassword(userid, newPassword);

	}
	// ��¼����
	public Student login_student(String studentid) throws SQLException {
		List<Student> stu = new ArrayList<Student>();
		Student student = null;
		boolean flag = false;
		stu = studentDao.query();
		Iterator<Student> iterator = stu.iterator();
		while (iterator.hasNext()) {
			if ((student = iterator.next()).getUserID().equals(studentid)) {
				flag = true;
				break;
			}
		}
		if (flag)
			return student;
		else
			return null;
	}

	public void close() {

	}
}
