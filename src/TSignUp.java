import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TSignUp extends JDialog{
	private ImageIcon logoimg = new ImageIcon("src/img/로고2.png");
	private JLabel logo = new JLabel(logoimg);
	
	 private JTextField idText = new JTextField("아이디");
	 private JTextField pwText = new JTextField();
	 private JTextField pwCheckText = new JTextField();
	 private JTextField nameText = new JTextField("이름");
	 private JButton signUpbtn = new JButton("회원가입 완료");
	 private JLabel pwlabel = new JLabel("회원가입");
	 private JButton back = new JButton("<");
	  
	 private boolean membershipProgress = true;

	 public TSignUp() {
		setTitle("회원가입");
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.WHITE);
		
		c.add(logo);
		c.add(pwlabel);
		c.add(idText);
		c.add(pwText);
		c.add(pwCheckText);
		c.add(nameText);
		c.add(signUpbtn);
		c.add(back);
		
		
		idText.setText("                                 아이디");
		pwText.setText("                               비밀번호");
		pwCheckText.setText("                            비밀번호 확인");
		nameText.setText("                                   이름");
		
		
		idText.setFont(new Font("고딕체", Font.BOLD, 15));
		pwText.setFont(new Font("고딕채", Font.BOLD, 15));
		pwCheckText.setFont(new Font("고딕채", Font.BOLD, 15));
		nameText.setFont(new Font("고딕채", Font.BOLD, 15));
		signUpbtn.setFont(new Font("고딕채", Font.BOLD, 15));
		pwlabel.setFont(new Font("고딕채", Font.BOLD, 15));
		back.setFont(new Font("고딕체", Font.PLAIN, 20));
		
		idText.setForeground(new Color(186, 186, 186));
		pwText.setForeground(new Color(186, 186, 186));
		pwCheckText.setForeground(new Color(186, 186, 186));
		nameText.setForeground(new Color(186, 186, 186));
		signUpbtn.setForeground(new Color(255, 255, 255));
		back.setForeground(new Color(75,80,157));
		
		
		idText.setBorder(new EmptyBorder(0, 0, 0, 0));
		pwText.setBorder(new EmptyBorder(0, 0, 0, 0));
		pwCheckText.setBorder(new EmptyBorder(0, 0, 0, 0));
		nameText.setBorder(new EmptyBorder(0, 0, 0, 0));
		signUpbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		back.setBorder(new EmptyBorder(0,0,0,0));
		
		idText.setBounds(10, 250, 353, 50);
		pwText.setBounds(10, 330, 353, 50);
		pwCheckText.setBounds(10, 410, 353, 50);
	    nameText.setBounds(10, 490, 353, 50);
	    signUpbtn.setBounds(10, 650, 353, 50);
	    logo.setBounds(110, 10, 150, 80);
	    pwlabel.setBounds(145,100,100,100);
	    back.setBounds(10,30,50,50);
	    
	    idText.setBackground(new Color(244, 244, 244));
		pwText.setBackground(new Color(244, 244, 244));
		pwCheckText.setBackground(new Color(244, 244, 244));
		nameText.setBackground (new Color(244, 244, 244));
		signUpbtn.setBackground (new Color(190, 216, 255));
		back.setBackground(Color.WHITE);
		

		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {       	
            	  dispose();
            	  TeacherLogin  TeacherLogin = new  TeacherLogin();
            	  TeacherLogin.setLocationRelativeTo(null); 
            	  TeacherLogin.setVisible(true);
            }
        });

		
		setSize(390,844);
		setLocationRelativeTo(null);		
		setVisible(true);
		
		FocusEvent();
		checkValue();
		
		this.requestFocusInWindow();
	}
	 
	 
	 
	
	 private void FocusEvent() {
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
			
		 pwCheckText.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	               
	                if (pwCheckText.getText().equals("                            비밀번호 확인")) {
	                	pwCheckText.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	             
	                if (pwCheckText.getText().isEmpty()) {
	                	pwCheckText.setText("                            비밀번호 확인");
	                }
	            }
	        });
			
		 nameText.addFocusListener(new FocusListener() {
	            @Override
	            public void focusGained(FocusEvent e) {
	              
	                if (nameText.getText().equals("                                   이름")) {
	                	nameText.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	             
	                if (nameText.getText().isEmpty()) {
	                	nameText.setText("                                   이름");
	                }
	            }
	        });
			
			
	 }
	 

	 private void checkValue() {
		   System.out.println("Checking values...");
		    signUpbtn.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            System.out.println("Button clicked!");
		            try {
		                if (idText.getText().trim().length() == 0 || idText.getText().trim().equals("아이디")) {
		                    JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요.", "아이디 입력", JOptionPane.WARNING_MESSAGE);
		                    idText.grabFocus();
		                    return;
		                }

		                if (pwText.getText().trim().length() == 0) {
		                    JOptionPane.showMessageDialog(null, "비밀번호를 입력해 주세요.", "비밀번호 입력", JOptionPane.WARNING_MESSAGE);
		                    pwText.grabFocus();
		                    return;
		                }

		                if (pwCheckText.getText().trim().length() == 0) {
		                    JOptionPane.showMessageDialog(null, "비밀번호 확인을 입력해 주세요.", "비밀번호 확인 입력", JOptionPane.WARNING_MESSAGE);
		                    pwCheckText.grabFocus();
		                    return;
		                }

		                if (!(pwText.getText().trim().equals(pwCheckText.getText().trim()))) {
		                    JOptionPane.showMessageDialog(null, "비밀번호가 같지 않습니다.", "비밀번호 확인", JOptionPane.WARNING_MESSAGE);
		                    return;
		                }

		                if (nameText.getText().trim().length() == 0 || nameText.getText().trim().equals("이름")) {
		                    JOptionPane.showMessageDialog(null, "이름을 입력해 주세요.", "이름 입력", JOptionPane.WARNING_MESSAGE);
		                    nameText.grabFocus();
		                    return;
		                }

		                if (membershipProgress) {
		                    loginDatabase db = new loginDatabase();
		                    boolean joinSuccess = db.TjoinCheck(idText.getText().trim(), pwText.getText().trim(), nameText.getText().trim());
		                    if (joinSuccess) {
		                        JOptionPane.showMessageDialog(null, "회원가입 성공", "회원가입 확인", JOptionPane.DEFAULT_OPTION);
		                        
		                        dispose(); 
			                     TeacherLogin tlog= new TeacherLogin();
			                     tlog.setLocationRelativeTo(null); 
			                     tlog.setVisible(true);
		                    } else {
		                        JOptionPane.showMessageDialog(null, "회원가입 실패", "회원가입 확인", JOptionPane.DEFAULT_OPTION);
		                        idText.grabFocus();
			                    return;
		                    }

		                    setVisible(false);
		                }
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "오류가 발생했습니다. 자세한 내용은 콘솔을 확인하세요.", "오류", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    });
		}

	 
	 public static void main(String[] args) {
	      
	        new TSignUp();
	    }
	 
}