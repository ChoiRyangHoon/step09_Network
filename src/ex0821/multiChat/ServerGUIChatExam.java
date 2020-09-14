package ex0821.multiChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerGUIChatExam {
	ServerSocket server;
	Socket sk;
	List<ClientSocketThread> list = new ArrayList<ClientSocketThread>();
	public ServerGUIChatExam() {
		try {
			server = new ServerSocket(8000);
			while(true) {
				System.out.println("Client 접속 대기중.....");
				sk = server.accept();
				System.out.println(sk.getInetAddress() + "님 접속되었습니다.....");
				
				ClientSocketThread th = new ClientSocketThread();
				list.add(th);
				th.start();
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}//생성자 끝
	
	/**
	 * 각각의 클라이언트에 대한 스레드 구현
	 * : 현재 스레드의 클라이언트가 보내오는 데이터를 읽어서
	 * 모든 클라인언트에게 데이터를 전송하는 기능 - run()
	 * */
	class ClientSocketThread extends Thread{
		PrintWriter pw;//접속된 클라이언트에게 데이터 보내기
		BufferedReader br;//접속된 클라이언트가 보내온 데이터 읽
		String name;//대화명
		String ip;//접속자 ip
		
		ClientSocketThread() {
			try {
			pw = new PrintWriter(sk.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
			ip = sk.getInetAddress().toString();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		@Override
		public void run() {
			try {
			name = br.readLine(); //대화명
			sendMessage("["+ name + "]님이 입장하셨습니다.");
			
			String message = null;
			while((message = br.readLine()) !=null) {
				sendMessage("["+ name + "]" + message);
				}
			
			}catch(Exception e) {
				e.printStackTrace();
				//현재 클라이언트가 창을 닫고 나갔다.....
				list.remove(this);
				sendMessage("["+ name + "]님이 퇴장하셨습니다.");				
				System.out.println(ip + "주소의 ["+ name + "]님이 퇴장하셨습니다.\n");
			}
		}
	}//inner클래스 끝
	
	/**
	 * list안에 있는 모든 클라이언트에게 데이터 전송하기 
	 * */
	public void sendMessage(String message) {
		for(ClientSocketThread th : list) {
			th.pw.println(message);
		}
	}
	
	
	public static void main(String[] args) {
		new ServerGUIChatExam();

	}

}//클래스 끝