package ex0820.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChatExam {
	Socket sk;
	ServerSocket server;
	public ServerChatExam() {
		try{
			server = new ServerSocket(8888);
			System.out.println("클라이언트 접속 대기중.....");
			sk = server.accept();//접속대기
			System.out.println(sk.getInetAddress() + "님 과 채팅을 시작합니다...");
			
			//읽기Thread-쓰기
			new ReciveThread().start();			
			//쓰기Thread-보내기
			new SendThread(sk, "SERVER").start();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new ServerChatExam();
	

	}

	/**
	 * 클라이언트가 보내오는 데이터를 읽어서 화면에 출력하는 스레드
	 * */
	class ReciveThread extends Thread {
		@Override
		public void run() {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(sk.getInputStream()))) {
				String message = null;
				while((message = br.readLine()) !=null) {
					System.out.println(message);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
