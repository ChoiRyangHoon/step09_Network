package ex0821.multiChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGUIChatExam extends JFrame {
	JTextArea textArea = new JTextArea();
	JScrollPane jsp = new JScrollPane(textArea);
	JTextField text = new JTextField();
	
	Socket sk;
	BufferedReader br;
	PrintWriter pw;
	
	
	public ClientGUIChatExam() {
		
		//Container의 Layout
		Container con = super.getContentPane();
		
		//추가(올리기)
		con.add(jsp, BorderLayout.CENTER);
		con.add(text, BorderLayout.SOUTH);
				
		//옵션설정
		textArea.setFocusable(false);//커서 비활성화
		textArea.setBackground(Color.YELLOW);
		textArea.requestFocus();//커서놓기
		
		//창크기
		super.setSize(400,300);
		//창위치
		super.setLocationRelativeTo(null);
		//보여줘
		super.setVisible(true);
		//X클릭햇을때 창닫기
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.connection();
		text.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//입력된 text값을 읽어서 서버에서 전송한다.
				pw.println(text.getText());
				
				//text내용 비우기
				text.setText("");
				
			}
		});
	}
	
	/**
	 * 서버에 접속하는 메소드
	 * */
	public void connection() {
		try {
		sk = new Socket("192.168.0.136", 8000);
		br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		pw = new PrintWriter(sk.getOutputStream(), true);
		
		String name = JOptionPane.showInputDialog("대화명을 입력해주세요.");
		pw.println(name);
		
		super.setTitle("["+ name +"]");
		
		//받는 스레드
		new Thread() {
			public void run() {
				try {
				String data = null;
				while((data = br.readLine()) !=null) {
					textArea.append(data + "\n");
					
					//옵션
					textArea.setCaretPosition(textArea.getText().length());
					
		
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new ClientGUIChatExam();

	}

}
