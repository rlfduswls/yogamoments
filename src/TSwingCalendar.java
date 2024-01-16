import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Container;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;
import java.awt.BasicStroke;
import javax.swing.border.*;
import java.awt.Graphics2D;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;


public class TSwingCalendar extends JFrame implements ActionListener {
	
	private int selectedYear;
	private int selectedMonth;
    private int selectedDate;
	private JTable classTable;
	private DefaultTableModel tableModel;
	private loginDatabase loginDb; 
	private ImageIcon logoimg = new ImageIcon("src/img/로고2.png");
	private JLabel logo = new JLabel(logoimg);
    JPanel calendar = new JPanel();
    JPanel topPane = new JPanel();
    JButton prevBtn = new JButton("     <     ");
    JButton nextBtn = new JButton("     >     ");
    JLabel yearLabel = new JLabel();
    JLabel monthLabel = new JLabel();
    JPanel titlePane = new JPanel(new GridLayout(1, 7));
    String titleStr[] = {"일", "월", "화", "수", "목", "금", "토"};
    JPanel datePane = new JPanel(new GridLayout(0, 7));
    JPanel  myPanel1 = new JPanel ();
    JPanel  myPanel2 = new JPanel ();
    JPanel  myPanel3 = new JPanel ();
    
    JPanel Class = new JPanel(new GridLayout(2, 2));
    JLabel classinfo =new JLabel("수업 정보");
    JLabel classname=new JLabel("수업");
    JLabel classnum=new JLabel("정원");
    JButton classSignupBtn = new JButton("수업등록하기");
    JButton manageBtn = new JButton("수강관리");
    JButton classBtn = new JButton("수업관리");
    JButton logoutBtn = new JButton("로그아웃");
    
    
    Calendar now;
    int year, month, date;
    
    public TSwingCalendar() {
    	 if (!SessionManager.isTeacherLoggedIn()) {
    	       
    	        JOptionPane.showMessageDialog(null, "먼저 로그인하세요.");
    	        System.exit(0);
    	        
    	    }
    	
        setTitle("수업");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);
       
        //수업정보불러오는 부분임
        loginDb = new loginDatabase();
        String[] columnNames = {"시간", "수업", "정원"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        classTable = new JTable(tableModel);
        classTable.setFillsViewportHeight(true);
        classTable.setCellSelectionEnabled(false);
        JTableHeader header = classTable.getTableHeader();
        header.setFont(new Font("고딕체", Font.BOLD, 13));  
        header.setUI(new BasicTableHeaderUI());
        header.setBackground(Color.WHITE);  
        header.setBorder(new LineBorder(Color.WHITE)); 
        header.setBorder(BorderFactory.createMatteBorder(1,1, 1, 1, Color.WHITE));
        classTable.setBorder(null); 
        classTable.setBackground(Color.WHITE);
        classTable.setGridColor(Color.WHITE);  
        classTable.setRowHeight(30);
        classTable.setOpaque(true);
        classTable.setBackground(Color.WHITE);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.WHITE);
        classTable.setDefaultRenderer(Object.class, renderer);
        classTable.setFillsViewportHeight(true);
        classTable.setCellSelectionEnabled(false);

        // JTableHeader 비활성화
        classTable.getTableHeader().setReorderingAllowed(false);
        classTable.getTableHeader().setResizingAllowed(false);
        
        TableColumnModel columnModel = classTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(70);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        classTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        classTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
       
        updateClassDisplay(); 
        JScrollPane scrollPane = new JScrollPane(classTable);
        scrollPane.setBackground(Color.WHITE); 
        
        
        now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        selectedYear = now.get(Calendar.YEAR);
        selectedMonth = now.get(Calendar.MONTH);
        selectedDate = now.get(Calendar.DATE);
        
        
        c.add(classSignupBtn);
        c.add(topPane);
        c.add(yearLabel);
        c.add(titlePane);
        c.add(datePane);
        c.add(logo);
        c.add(classinfo);
        c.add(classname);
        c.add(classnum);
        c.add(myPanel1);
        c.add(myPanel2);
        c.add(myPanel3);
        c.add(scrollPane);
        c.add(manageBtn);
        c.add(classBtn);
        c.add(logoutBtn);
        
        
        manageBtn.setBounds(20,750,100,50);
        classBtn.setBounds(135,750,100,50);
        logoutBtn.setBounds(250,750,100,50);

        manageBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        classBtn.setFont(new Font("고딕체", Font.BOLD, 14));
        logoutBtn.setFont(new Font("고딕체", Font.PLAIN, 14));


        manageBtn.setBackground(Color.white);
        classBtn.setBackground(Color.white);
        logoutBtn.setBackground(Color.white);      
      
        manageBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        classBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        logoutBtn.setBorder(new EmptyBorder(0, 0, 0, 0));    
        
        
        classSignupBtn.setBounds(230,720,80,20);
        logo.setBounds(110, 10, 150, 80);
        yearLabel.setBounds(168, 100, 100, 30);
        topPane.setBounds(110, 130, 150, 30);
        titlePane.setBounds(50, 180, 270, 30);
        datePane.setBounds(50, 220, 270, 180);
        classinfo.setBounds(150,440,95,20);
        myPanel1.setBounds(40, 450, 90, 1);
        myPanel2.setBounds(230, 450, 90, 1);
        myPanel3.setBounds(40, 500, 280, 1);
        scrollPane.setBounds(40, 480, 280, 200);
        monthLabel.setForeground(new Color(237,138,23));
      
        
        classname.setFont(new Font("고딕체", Font.BOLD, 13));
        classnum.setFont(new Font("고딕체",Font.BOLD, 13));
        classinfo.setFont(new Font("고딕체", Font.BOLD, 14));
        classSignupBtn.setFont(new Font("고딕체", Font.BOLD, 11));
        monthLabel.setFont(new Font("고딕체", Font.PLAIN, 15));
        prevBtn.setFont(new Font("고딕체", Font.PLAIN, 15));
        nextBtn.setFont(new Font("고딕체", Font.PLAIN, 15));
        yearLabel.setFont(new Font("고딕체", Font.PLAIN, 15));
   
        
        topPane.add(prevBtn);
        topPane.add(monthLabel);
        topPane.add(nextBtn);
        topPane.setBackground(Color.white);
        datePane.setBackground(Color.white);
        classSignupBtn.setBackground(Color.white);
        myPanel1.setBackground(new Color(228,222,222));
        myPanel2.setBackground(new Color(228,222,222));
        myPanel3.setBackground(new Color(228,222,222));
        classinfo.setBackground(new Color(86,77,77));
        classTable.setBackground(Color.white);
        scrollPane.setBackground(Color.white);
        
        
        classSignupBtn.setForeground(new Color(85,72,72));
        
        yearLabel.setText(String.valueOf(year));
        monthLabel.setText(String.valueOf(month));

        prevBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        nextBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        classTable.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        
        prevBtn.setBackground(Color.WHITE);
        nextBtn.setBackground(Color.WHITE);

        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        
        
        setCalendarLabels();
        
       

       
        titlePane.setBackground(Color.white);
        for(int i=0; i<titleStr.length; i++){
        	JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
        	lbl.setFont(new Font("고딕체", Font.PLAIN, 17));
        	titlePane.add(lbl);		}

        
        int arc = 3; 
   
        classSignupBtn.setBorder(new LineBorder(new Color(247, 192, 129), 1) {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(getLineColor());
                g2.setStroke(new BasicStroke(1));
                g2.draw(new RoundRectangle2D.Double(x + arc, y + arc, width - arc * 2, height - arc * 2+1, arc, arc));
               
            }
        });

        classSignupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	ClassSignup ClassSignup= new ClassSignup();
            	ClassSignup.setLocationRelativeTo(null); 
            	ClassSignup.setVisible(true); 
            	
            }
        });
        
        
        manageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	dispose();
            	studentManagement studentManagement = new studentManagement();
            	studentManagement.setLocationRelativeTo(null); 
            	studentManagement.setVisible(true);

            }
        });
 
        //로그아웃 누르면, 세션이 끊기고, 초기페이지로 이동 
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
              if (SessionManager.isTeacherLoggedIn()) {
            	  SessionManager.isTeacherLoggedIn = false;
            	  JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
            	  dispose();
                  StudentTeacher StudentTeacher = new StudentTeacher();
              	  StudentTeacher.setLocationRelativeTo(null); 
              	  StudentTeacher.setVisible(true);
              }
            	
            }
        });

        
        setSize(390, 844);
        setVisible(true);
        this.requestFocusInWindow();
    }


    public void actionPerformed(ActionEvent ae) {
        Object obj = ae.getSource();
        if (obj instanceof JButton) {
            JButton eventBtn = (JButton) obj;
            if (eventBtn.equals(prevBtn)) {
                // 전달
                if (month == 1) {
                    year--;
                    month = 12;
                } else {
                    month--;
                }
            } else if (eventBtn.equals(nextBtn)) {
                // 다음달
                if (month == 12) {
                    year++;
                    month = 1;
                } else {
                    month++;
                }
            }
            setCalendarLabels();
        }
    }

    public void setCalendarLabels() {
        yearLabel.setText(String.valueOf(year));
        monthLabel.setText(String.valueOf(month));
        datePane.setVisible(false);
        datePane.removeAll();
        dayPrint(year, month);
        datePane.setVisible(true);
    }
    
    
    private void updateClassDisplay() {
       
        String teacherId = SessionManager.getLoggedInTeacherId(); 
        ArrayList<ClassInfo> teacherClasses = loginDb.getClassInfoForTeacher(teacherId);
        
      
        tableModel.setRowCount(0);
        teacherClasses.sort((class1, class2) -> class1.getClassDate().compareTo(class2.getClassDate()));
        
        for (ClassInfo classInfo : teacherClasses) {
            String classDateTime = classInfo.getClassDate();
            String classTime = getClassTime(classDateTime);

            int currentEnrollment = classInfo.getCurrentEnrollment();
            int classCapacity = Integer.parseInt(classInfo.getClassCapacity());
      
            String enrollmentInfo = currentEnrollment + "/" + classCapacity;

            String[] rowData = {
                    classTime,
                    classInfo.getClassName(),
                    enrollmentInfo
            };

            int classDay = getClassDay(classDateTime);
            int classYear = getClassYear(classDateTime);
            int classMonth = getClassMonth(classDateTime);
            
            if (selectedDate == classDay && selectedYear == classYear && selectedMonth == classMonth) {
                
                tableModel.addRow(rowData);


                
            }


        }

    }



// 헬퍼 메서드: 전체 날짜-시간 문자열에서 년도, 월, 일, 시간을 추출
private int getClassYear(String classDateTime) {
    return Integer.parseInt(classDateTime.split(" ")[0].split("-")[0]);
}

private int getClassMonth(String classDateTime) {
    return Integer.parseInt(classDateTime.split(" ")[0].split("-")[1]) - 1;
}

private int getClassDay(String classDateTime) {
    return Integer.parseInt(classDateTime.split(" ")[0].split("-")[2]);
}

private String getClassTime(String classDateTime) {
    return classDateTime.split(" ")[1];
}

    

    public void dayPrint(int y, int m) {
    	 Calendar cal = Calendar.getInstance();
    	    int currentYear = cal.get(Calendar.YEAR);
    	    int currentMonth = cal.get(Calendar.MONTH);
    	    int currentDate = cal.get(Calendar.DATE);
    	    
        cal.set(y, m - 1, 1);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i < week; i++) {
            datePane.add(new JLabel(" "));
        }
        for (int i = 1; i <= lastDate; i++) {
            JLabel lbl = new JLabel(String.valueOf(i), JLabel.CENTER);
            cal.set(y, m - 1, i);
            int outWeek = cal.get(Calendar.DAY_OF_WEEK);
            lbl.setFont(new Font("고딕체", Font.PLAIN, 17));

            if (i == selectedDate && y == selectedYear && m - 1 == selectedMonth) {
                lbl.setBackground(new Color(255, 255, 153));
            } else {
                // 나머지 날짜는 흰색 배경
                lbl.setBackground(Color.WHITE);
            }

            lbl.setOpaque(true);
            lbl.addMouseListener(new DateLabelMouseListener(i));
            
            if (y < currentYear || (y == currentYear && m - 1 < currentMonth) || (y == currentYear && m - 1 == currentMonth && i < currentDate)) {
                lbl.setForeground(new Color(231,179,157)); 
                
            } else if (y > currentYear || (y == currentYear && m - 1 > currentMonth) || (y == currentYear && m - 1 == currentMonth && i > currentDate)) {
                lbl.setForeground(new Color(237,138,23));
            } else {
                lbl.setForeground(new Color(237,138,23));
            }
            
            datePane.add(lbl);
        }
        
    }
    private class DateLabelMouseListener extends MouseAdapter {
        private final int day;

        public DateLabelMouseListener(int day) {
            this.day = day;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            selectedDate = day;
            setCalendarLabels();
            updateClassDisplay();
        }
    }

    public static void main(String[] args) {
        new TSwingCalendar();
    }
}
