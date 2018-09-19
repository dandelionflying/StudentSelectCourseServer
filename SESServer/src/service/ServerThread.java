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

	private String studentid = null;// 接收数据
	private Student student = null;// 接收数据

	private int type = -1;// 操作类型
	private boolean flag = false;
	
	public ServerThread(Socket socket) {
		// 初始化流
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
	// 响应添加课程请求
	public void responseAddCourese() {
		String[] str = new String[2];
		try {
			str[0] = br.readLine();
			str[1] = br.readLine();
			Course course = courseAction.get_course(str[1]);
			// 判断课程余量是否大于零，大于零则添加课程，否则返回错误信息
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
		// 响应查看全部课程请求
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
	// 响应是否课程请求
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
	// 响应获取个人课程请求
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
	// 响应获取课程请求
	public void responsegetCourse() {
		try {
			String courseid = br.readLine();
			// socket.shutdownInput();// 关闭输入流
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
	// 响应登录请求
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
	// 响应修改密码请求
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
	// 响应判断是否已选课程请求
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
	// 关闭资源
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
