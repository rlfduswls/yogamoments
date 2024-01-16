import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TeacherLogin extends JFrame{
	
	
	private ImageIcon logoimg = new ImageIcon("src/img/자산 2.png");
	private JLabel logo = new JLabel(logoimg);
	private JTextField idText = new JTextField();
	private JTextField pwText = new JTextField();
	private JButton loginBtn = new JButton("로그인");
	private JButton memberbtn = new JButton("회원가입");
	private JLabel pwInfoText = new JLabel("계정이 없으신가요?");
	private JButton back = new JButton("<");
	
	
	public TeacherLogin() {
		setTitle("login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.WHITE);
		
		
		c.add(idText);
		c.add(pwText);
		c.add(loginBtn);		
		c.add(memberbtn);		
		c.add(logo);
		c.add(pwInfoText);
		c.add(back);
	
		idText.setText("                                 아이디");
		pwText.setText("                               비밀번호");
		idText.setFont(new Font("고딕체", Font.BOLD, 15));
		pwText.setFont(new Font("고딕채", Font.BOLD, 15));
		loginBtn.setFont(new Font("고딕채", Font.BOLD, 15));
		memberbtn.setFont(new Font("고딕채", Font.BOLD, 12));
		pwInfoText.setFont(new Font("고딕채", Font.BOLD, 12));
		back.setFont(new Font("고딕체", Font.PLAIN, 20));
		
		
		idText.setBackground(new Color(244, 244, 244));
		pwText.setBackground(new Color(244, 244, 244));
		loginBtn.setBackground(new Color(255, 254, 221));
		memberbtn.setBackground(Color.WHITE);
		back.setBackground(Color.WHITE);
		back.setForeground(new Color(75,80,157));
		
		
		
		idText.setForeground(new Color(186, 186, 186));
		pwText.setForeground(new Color(186, 186, 186));
		loginBtn.setForeground(new Color(163, 155, 155));
		back.setBorder(new EmptyBorder(0,0,0,0));
		
		
		idText.setBorder(new EmptyBorder(0, 0, 0, 0));
		pwText.setBorder(new EmptyBorder(0, 0, 0, 0));
		loginBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		memberbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		

		idText.setBounds(10, 400, 353, 50);
		pwText.setBounds(10, 480, 353, 50);
		 logo.setBounds(100, 200, 160, 80);
		loginBtn.setBounds(50, 570, 267, 48);
		memberbtn.setBounds(150, 650, 70, 20);
		pwInfoText.setBounds(130, 620, 267, 48);
		back.setBounds(10,30,50,50);
		

		setSize(390,844);
		setLocationRelativeTo(null);		
		setVisible(true);
		
		
		 idText.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	             
	                if (idText.getText().equals("                                 아이디")) {
	                    idText.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	          
	                if (idText.getText().isEmpty()) {
	                    idText.setText("                                 아이디");
	                }
	            }
	        });
		 
		
		 pwText.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	            
	                if (pwText.getText().equals("                               비밀번호")) {
	                	pwText.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	               
	                if (pwText.getText().isEmpty()) {
	                	pwText.setText("                               비밀번호");
	                }
	            }
	        });
		 
		 
		 this.requestFocusInWindow();
		

		  //1.학습관리로 이동
	       back.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {       	
	            	  dispose();
	            	  StudentTeacher StudentTeacher = new StudentTeacher();
	              	  StudentTeacher.setLocationRelativeTo(null); 
	              	  StudentTeacher.setVisible(true);
	            }
	        });
		 
		 
		//로그인 버튼을 눌렀을때
		 loginBtn.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent e) {

		         String id = idText.getText().trim();
		         String pw = pwText.getText().trim();

		         if (id.length() == 0 || pw.length() == 0) {
		             JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력 하셔야 됩니다.", "아이디나 비번을 입력!", JOptionPane.DEFAULT_OPTION);
		             return;
		         }

		         // loginDatabase 클래스의 인스턴스 생성
		         loginDatabase db = new loginDatabase();

		         // logincheck 메서드 호출하여 로그인 시도
		         boolean loginSuccess = db.Tlogincheck(id, pw);

		         SwingUtilities.invokeLater(new Runnable() {
		             public void run() {
		                 if (loginSuccess) {
		                     JOptionPane.showMessageDialog(null, "로그인 성공", "로그인 확인!", JOptionPane.DEFAULT_OPTION);
		                     SessionManager.setLoggedInTeacherId(id);
		                     SessionManager.setTeacherLoggedIn(true);
		                     
		                     dispose();  // 현재 창 닫기
		                     TSwingCalendar calendar = new TSwingCalendar();
		                     calendar.setLocationRelativeTo(null); 
		                     calendar.setVisible(true);

		                    
		                 } else {
		                     JOptionPane.showMessageDialog(null, "로그인 실패", "계정 정보를 확인해 주세요.", JOptionPane.DEFAULT_OPTION);
		                 }
		             }
		         });
		     }
		 });

		
		//회원가입 버튼으로 이동
		memberbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				TSignUp signup = new TSignUp();
				signup.setVisible(true);
			}
		});
		
		
	}
	

	 public static void main(String[] args) {
		new TeacherLogin();		
	}
}