package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StudentResponse {
	private ServerSocket serverSocket;
	private int port = 7200;
	private int count = 0;// 记录客户端数量
	private Socket socket;

	public StudentResponse() {
		try {
			serverSocket = new ServerSocket(port);
			// 循环等待客户端连接
			while (true) {
				
				socket = serverSocket.accept();
				count = count + 1;
				// 开启线程
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
