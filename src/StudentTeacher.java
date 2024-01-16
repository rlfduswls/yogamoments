import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StudentTeacher extends JFrame{
	
	private ImageIcon logoimg = new ImageIcon("src/img/자산 2.png");
	private JLabel logo = new JLabel(logoimg);
	private JButton Studentbtn =new JButton("STUDENT");
	private JButton Teacherbtn =new JButton("TEACHER");

	public StudentTeacher() {
		setTitle("학생/선생님 선택");
		
		Container c=getContentPane();
		c.setLayout(null);
		c.setBackground(Color.WHITE);
		
		c.add(logo);
		c.add(Studentbtn);
		c.add(Teacherbtn);
		
		Studentbtn.setFont(new Font("고딕체", Font.BOLD, 17));
		Teacherbtn.setFont(new Font("고딕체", Font.BOLD, 17));
		
		Studentbtn.setForeground(new Color(126, 111, 111));
		Teacherbtn.setForeground(new Color(130, 123, 175));

		Studentbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		Teacherbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		Studentbtn.setBounds(50, 348, 121, 135);
		Teacherbtn.setBounds(212, 348, 121, 135);
		logo.setBounds(100, 200, 160, 80);

		Studentbtn.setBackground(new Color(244, 242, 206));
		Teacherbtn.setBackground(new Color(179, 190, 230));
		

		setSize(390,844);
		setLocationRelativeTo(null);		
		setVisible(true);
		this.requestFocusInWindow();
		
		
	
		Studentbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				StudentLogin studentlogin =new StudentLogin();
				studentlogin.setVisible(true);
			}
		});
		
	
		Teacherbtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						 dispose();
						 TeacherLogin  teacherlogin =new  TeacherLogin();
						 teacherlogin.setVisible(true);
					}
				});
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new StudentTeacher();

	}

}
