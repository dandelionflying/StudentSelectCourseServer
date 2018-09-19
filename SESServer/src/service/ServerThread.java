package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import action.CourseAction;
import action.StudentAction;
import model.Course;
import model.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ServerThread extends Thread {
	private Socket socket = null;
	private InputStream in;
	private InputStreamReader inreader;
	private BufferedReader br;
	private OutputStream out;
	private OutputStreamWriter outwriter;
	private BufferedWriter bw;
	private StudentAction studentAction = new StudentAction();
	private CourseAction courseAction = new CourseAction();

	private String studentid = null;// ��������
	private Student student = null;// ��������

	private int type = -1;// ��������
	private boolean flag = false;
	
	public ServerThread(Socket socket) {
		// ��ʼ����
		this.socket = socket;
		try {
			in = socket.getInputStream();
			inreader = new InputStreamReader(in);
			br = new BufferedReader(inreader);
			out = socket.getOutputStream();
			outwriter = new OutputStreamWriter(out);
			bw = new BufferedWriter(outwriter);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		try {
			while ((type = br.read()) >= 0) {
				if (type == 0) {
					responseAddCourese();
				} else if (type == 1) {
					responseAllCourese();
				} else if (type == 2) {
					responseIsCourse();
				} else if (type == 3) {
					responsePersonalCourse();
				} else if (type == 4) {
					responsegetCourse();
				} else if (type == 5) {
					responseLogin();
				} else if (type == 6) {
					responseResetPassword();
				} else if (type == 7) {
					responseIsElected();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ��Ӧ��ӿγ�����
	public void responseAddCourese() {
		String[] str = new String[2];
		try {
			str[0] = br.readLine();
			str[1] = br.readLine();
			Course course = courseAction.get_course(str[1]);
			// �жϿγ������Ƿ�����㣬����������ӿγ̣����򷵻ش�����Ϣ
			if (course.getRemaindingQuantity() > 0) {
				flag = studentAction.add_course(str[0], course);
				if (flag) {
					bw.write(1);
					bw.flush();
				} else {
					bw.write(0);
					bw.flush();
				}
			} else {
				bw.write(2);
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void responseAllCourese() {
		// ��Ӧ�鿴ȫ���γ�����
		try {
			List<Course> courses = studentAction.query_course();
			if (courses != null) {
				JSONArray jsonArray = JSONArray.fromObject(courses);
				try {
					bw.write(jsonArray.toString() + "\n");
					bw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ��Ӧ�Ƿ�γ�����
	public void responseIsCourse() {
		try {
			String courseid = br.readLine();
			flag = courseAction.iscourse(courseid);
			if (flag) {
				bw.write(1);
				bw.flush();
			} else {
				bw.write(0);
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ��Ӧ��ȡ���˿γ�����
	public void responsePersonalCourse() {
		try {
			String studentid = br.readLine();
			List<Course> courses = studentAction.query_course(studentid);
			JSONArray jsonArray = JSONArray.fromObject(courses);
			try {
				bw.write(jsonArray.toString() + "\n");
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ��Ӧ��ȡ�γ�����
	public void responsegetCourse() {
		try {
			String courseid = br.readLine();
			// socket.shutdownInput();// �ر�������
			CourseAction courseAction = new CourseAction();
			Course course = courseAction.get_course(courseid);
			JSONObject json = JSONObject.fromObject(course);
			bw.write(json.toString() + "\n");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ��Ӧ��¼����
	public void responseLogin() {
		try {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						student = studentAction.login_student(studentid);
						JSONObject json = JSONObject.fromObject(student);

						bw.write(json.toString() + "\n");
						bw.flush();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});
			studentid = br.readLine();
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ��Ӧ�޸���������
	public void responseResetPassword() {	
		String newpassword = null;
		try {
			studentid = br.readLine();
			newpassword = br.readLine();
			flag = studentAction.revisePassWord(studentid, newpassword);
			if (flag) {
				bw.write(1);
				bw.flush();
			} else {
				bw.write(0);
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ��Ӧ�ж��Ƿ���ѡ�γ�����
	public void responseIsElected() {
		String courseid = null;
		try {
			studentid = br.readLine();
			courseid = br.readLine();
			flag = studentAction.query_course(studentid, courseid);
			if (flag) {
				bw.write(1);
				bw.flush();
			} else {
				bw.write(0);
				bw.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	// �ر���Դ
	public void close() {
		try {
			if (bw != null)
				bw.close();
			if (outwriter != null)
				outwriter.close();
			if (out != null)
				out.close();
			if (br != null)
				br.close();
			if (inreader != null)
				inreader.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}
}
