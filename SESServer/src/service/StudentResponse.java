package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StudentResponse {
	private ServerSocket serverSocket;
	private int port = 7200;
	private int count = 0;// ��¼�ͻ�������
	private Socket socket;

	public StudentResponse() {
		try {
			serverSocket = new ServerSocket(port);
			// ѭ���ȴ��ͻ�������
			while (true) {
				
				socket = serverSocket.accept();
				count = count + 1;
				// �����߳�
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
