import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClassSignup extends JDialog{
	private ImageIcon logoimg = new ImageIcon("src/img/로고2.png");
	private JLabel logo = new JLabel(logoimg);
	
	 private JTextField classid = new JTextField("수업 아이디");
	 private JTextField className = new JTextField();
	 private JTextField classDate = new JTextField();
	 private JTextField classnum = new JTextField("이름");
	 private JButton signUpbtn = new JButton("수업등록완료");
	 private JLabel pwlabel = new JLabel("수업 등록");
	 private boolean membershipProgress = true;
	 private String teacherid;
	 private JButton back = new JButton("<");

	 public ClassSignup() {
		setTitle("수업 등록");
		

	
		 if (!SessionManager.isTeacherLoggedIn()) {
  	       
 	        JOptionPane.showMessageDialog(null, "먼저 로그인하세요.");
 	        System.exit(0);
 	    }
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.WHITE);
		
		c.add(logo);
		c.add(pwlabel);
		c.add(classid);
		c.add(className);
		c.add(classDate);
		c.add(classnum);
		c.add(signUpbtn);
		c.add(back);
		
		
		
		classid.setText("                               수업 아이디");
		className.setText("                               수업 이름");
		classDate.setText("                            수업 날짜/시간");
		classnum.setText("                                수업 정원");
		
		
		classid.setFont(new Font("고딕체", Font.BOLD, 15));
		className.setFont(new Font("고딕채", Font.BOLD, 15));
		classDate.setFont(new Font("고딕채", Font.BOLD, 15));
		classnum.setFont(new Font("고딕채", Font.BOLD, 15));
		signUpbtn.setFont(new Font("고딕채", Font.BOLD, 15));
		pwlabel.setFont(new Font("고딕채", Font.BOLD, 15));
		back.setFont(new Font("고딕체", Font.PLAIN, 20));
		
		
		classid.setForeground(new Color(186, 186, 186));
		className.setForeground(new Color(186, 186, 186));
		classDate.setForeground(new Color(186, 186, 186));
		classnum.setForeground(new Color(186, 186, 186));
		signUpbtn.setForeground(new Color(255, 255, 255));
		back.setBackground(Color.WHITE);
		back.setForeground(new Color(75,80,157));
		
		
	
		classid.setBorder(new EmptyBorder(0, 0, 0, 0));
		className.setBorder(new EmptyBorder(0, 0, 0, 0));
		classDate.setBorder(new EmptyBorder(0, 0, 0, 0));
		classnum.setBorder(new EmptyBorder(0, 0, 0, 0));
		signUpbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		back.setBorder(new EmptyBorder(0,0,0,0));
		back.setBounds(10,30,50,50);
		
		
		classid.setBounds(10, 250, 353, 50);
		className.setBounds(10, 330, 353, 50);
		classDate.setBounds(10, 410, 353, 50);
		classnum.setBounds(10, 490, 353, 50);
	    signUpbtn.setBounds(10, 650, 353, 50);
	    logo.setBounds(110, 10, 150, 80);
	    pwlabel.setBounds(145,100,100,100);
	   
	    classid.setBackground(new Color(244, 244, 244));
	    className.setBackground(new Color(244, 244, 244));
		classDate.setBackground(new Color(244, 244, 244));
		classnum.setBackground (new Color(244, 244, 244));
		signUpbtn.setBackground (new Color(255, 201, 139));
		
		setSize(390,844);
		setLocationRelativeTo(null);		
		setVisible(true);
		
		FocusEvent();
		checkValue();
		
		this.requestFocusInWindow();
		
		//1.학습관리로 이동
	       back.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {       	
	            	  dispose();
	            	 TSwingCalendar TSwingCalendar = new TSwingCalendar();
	            	 TSwingCalendar.setLocationRelativeTo(null); 
	            	 TSwingCalendar.setVisible(true);
	            }
	        });
		 
	}
	 
	 
	 //텍스트 필드에 있는 값을 체크하고 지우기 위한 메소드
	 private void FocusEvent() {
		 classid.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                // 텍스트 필드에 포커스가 들어오면 기본값 지우기
	                if (classid.getText().equals("                               수업 아이디")) {
	                	classid.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                // 텍스트 필드에서 포커스가 나가면 기본값이 없으면 다시 설정
	                if (classid.getText().isEmpty()) {
	                	classid.setText("                               수업 아이디");
	                }
	            }
	        });
			
		 className.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                // 텍스트 필드에 포커스가 들어오면 기본값 지우기
	                if ( className.getText().equals("                               수업 이름")) {
	                	 className.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                // 텍스트 필드에서 포커스가 나가면 기본값이 없으면 다시 설정
	                if ( className.getText().isEmpty()) {
	                	 className.setText("                               수업 이름");
	                }
	            }
	        });
			
		 classDate.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                // 텍스트 필드에 포커스가 들어오면 기본값 지우기
	                if (classDate.getText().equals("                            수업 날짜/시간")) {
	                	classDate.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                // 텍스트 필드에서 포커스가 나가면 기본값이 없으면 다시 설정
	                if (classDate.getText().isEmpty()) {
	                	classDate.setText("                            수업 날짜/시간");
	                }
	            }
	        });
			
		 classnum.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                // 텍스트 필드에 포커스가 들어오면 기본값 지우기
	                if (classnum.getText().equals("                                수업 정원")) {
	                	classnum.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                // 텍스트 필드에서 포커스가 나가면 기본값이 없으면 다시 설정
	                if (classnum.getText().isEmpty()) {
	                	classnum.setText("                                수업 정원");
	                }
	            }
	        });
		 
		 
	 }
	 
	 
	 
	 private void showWarningMessage(String message) {
	        JOptionPane.showMessageDialog(null, message, "경고", JOptionPane.WARNING_MESSAGE);
	    }
	 
	 private boolean isClassnumValid(String classnum) {
	        try {
	            int num = Integer.parseInt(classnum);
	            return num > 0 && num <= 10;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	 
	 //수업 등록할때 모든 값이 입력되었는지 체크 메소드
	 private void checkValue() {
		   System.out.println("Checking values...");
		    signUpbtn.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	
		            System.out.println("Button clicked!");
		            try {
		                if (classid.getText().trim().length() == 0 || classid.getText().trim().equals("수업 아이디")) {
		                    JOptionPane.showMessageDialog(null, "수업 아이디를 입력해 주세요.", "수업 아이디 입력", JOptionPane.WARNING_MESSAGE);
		                    classid.grabFocus();
		                    return;
		                }

		                if (className.getText().trim().length() == 0 || classid.getText().trim().equals("수업 아이디")) {
		                    JOptionPane.showMessageDialog(null, "수업 이름을 입력해주세요.", "수업 이름 입력", JOptionPane.WARNING_MESSAGE);
		                    className.grabFocus();
		                    return;
		                }

		              

		                if (classDate.getText().trim().length() == 0 || classid.getText().trim().equals("수업 아이디")) {
		                    JOptionPane.showMessageDialog(null, "수업 날짜/시간 입력해주세요.", "수업 날짜/시간 입력", JOptionPane.WARNING_MESSAGE);
		                    classDate.grabFocus();
		                    return;
		                }


		                if (classnum.getText().trim().length() == 0 || classnum.getText().trim().equals("이름")) {
		                    JOptionPane.showMessageDialog(null, "수업 정원을 입력해 주세요.", "수업 정원", JOptionPane.WARNING_MESSAGE);
		                    classnum.grabFocus();
		                    return;
		                }
		                if (!isClassnumValid(classnum.getText())) {
	                        showWarningMessage("수업 정원은 1부터 10까지의 숫자여야 합니다.");
	                        classnum.grabFocus();
	                        return;
	                    }
		              
		                if (membershipProgress) {
		                    loginDatabase db = new loginDatabase();
		                    String teacherid = SessionManager.getLoggedInTeacherId();
		                    boolean classSignupSuccess = db.classSignup(classid.getText().trim(), className.getText().trim(),
		                            classDate.getText().trim(), classnum.getText().trim(), teacherid);
		                    
		                    if (teacherid == null) {
		                        // 선생님 아이디가 null인 경우에 대한 처리
		                        showWarningMessage("로그인 정보가 없습니다. 다시 로그인하세요.");
		                        // 필요에 따라 로그인 화면으로 이동하도록 처리
		                        return;
		                    }
		                  
		                    if (classSignupSuccess) {
		                        JOptionPane.showMessageDialog(null, "수업 등록 성공", "수업 등록 확인", JOptionPane.DEFAULT_OPTION);
		                    } else {
		                        JOptionPane.showMessageDialog(null, "수업 등록 실패", "수업 등록 확인", JOptionPane.DEFAULT_OPTION);
		                        classid.grabFocus();
			                    return;
		                       
		                    }

		                    dispose();
			            	 TSwingCalendar TSwingCalendar = new TSwingCalendar();
			            	 TSwingCalendar.setLocationRelativeTo(null); 
			            	 TSwingCalendar.setVisible(true);
		                }
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "오류가 발생했습니다. 자세한 내용은 콘솔을 확인하세요.", "오류", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    });
		    
		    
		}

	 
	 public static void main(String[] args) {
	      
	        new ClassSignup();
	    }
	 
}