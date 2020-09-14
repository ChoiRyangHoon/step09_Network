package ex0820;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerExam {
	public ServerExam() {
		try(ServerSocket server = new ServerSocket(8000)) {
			while(true) {
				System.out.println("Client접속 대기중입니다.....");
				Socket sk = server.accept();//클라이언트 접속 대기
				System.out.println(sk.getInetAddress() + "님 접속되었습니다...");
				
				//클라이언트가 보내 온 데이터 읽기
				InputStream is = sk.getInputStream();//byte단위읽기
				BufferedReader br =
						new BufferedReader(new InputStreamReader(is));
				
				String data = br.readLine();
				System.out.println("클라이언트가 보내온 내용 : " + data);
					
				//클라이언트에게 데이터 전송
				OutputStream os = sk.getOutputStream();
				//byte단위처리 - 문자단위처리 변경, Buffered이용
				PrintWriter pw = new PrintWriter(os, true);
				pw.println("Java세상에 오신걸 환영합니다.~");
				
				sk.close();		
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerExam();
		
	}

}
