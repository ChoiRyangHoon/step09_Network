package ex0820.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientChatExam {
	public ClientChatExam() {
		try {	
			Socket sk = new Socket("192.168.0.190", 8888);
			System.out.println("서버와 채팅 시작");
			
			//읽기 스레드
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try(BufferedReader br = new BufferedReader(new InputStreamReader(sk.getInputStream()))) {
						String message = null;
						while((message = br.readLine()) !=null) {
							System.out.println(message);
						}
				}catch(Exception e) {
					e.printStackTrace();
				}
					
				}
					
			}).start();

			//보내기 스레드
			new SendThread(sk, "CLIENT").start();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		 new ClientChatExam();
	}

}
