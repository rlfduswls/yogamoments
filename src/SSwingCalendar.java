import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D; 
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.BasicTableHeaderUI; 
import java.util.*;

public class SSwingCalendar extends JFrame implements ActionListener {

	private Map<String, Set<Integer>> reservationsPerPersonPerDay = new HashMap<>();

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
    JButton classBookBtn = new JButton("예약하기");
    
    
    JButton manageBtn = new JButton("학습관리");
    JButton classBtn = new JButton("수업예약");
    JButton logoutBtn = new JButton("로그아웃");
  
    
    Calendar now;
    int year, month, date;

    public SSwingCalendar() {
    	
    	 if (!SessionManager.isStudentLoggedIn()) {
  	       
 	        JOptionPane.showMessageDialog(null, "먼저 로그인하세요.");
 	       System.exit(0);
 	    }

        setTitle("캘린더");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);

        now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        
        c.add(topPane);
        c.add(yearLabel);
        c.add(titlePane);
        c.add(datePane);
        c.add(logo);
        c.add(manageBtn);
        c.add(classBtn);
        c.add(logoutBtn);
        
        //수업정보불러오는 부분임
        loginDb = new loginDatabase();
        String[] columnNames = {"시간", "수업", "정원","예약", "classId"};
        
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
        int reserveColumnIndex = 3;
      
        classTable.getTableHeader().getColumnModel().getColumn(reserveColumnIndex).setHeaderRenderer(new TransparentHeaderRenderer());
        classTable.setBorder(null); 
        classTable.setBackground(Color.WHITE);
        classTable.setGridColor(Color.WHITE);  
        classTable.setRowHeight(30);
        classTable.setOpaque(true);
        classTable.setBackground(Color.WHITE);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.WHITE);
        classTable.setDefaultRenderer(Object.class, renderer);
     
        TableColumnModel columnModel = classTable.getColumnModel();
        
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(70);
        columnModel.getColumn(3).setPreferredWidth(60);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        classTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        classTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        classTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        classTable.addMouseListener(new MouseAdapter() {
        	 @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = classTable.getSelectedRow();

                if (selectedRow >= 0) {
                    TableModel model = classTable.getModel();
                    String classId = (String) model.getValueAt(selectedRow, 4);
                    System.out.println("Class ID: " + classId);
                }
            }
        });

     // 네 번째 열의 글자 색을 변경
        classTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // 네 번째 열에 대한 처리
                if (column == 3) {
                    // 네 번째 열의 글자 색을 변경 (여기서는 빨간색)
                    setForeground(new Color(76,60,175));
                    
                }

                return cellComponent;
            }
        });

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
        
        
        c.add( classBookBtn);
        c.add(topPane);
        c.add(yearLabel);
        c.add(titlePane);
        c.add(datePane);
        c.add(classinfo);
        c.add(classname);
        c.add(classnum);
        c.add(myPanel1);
        c.add(myPanel2);
        c.add(myPanel3);
        c.add(scrollPane);
        c.add(logo);
        
        manageBtn.setBounds(20,750,100,50);
        classBtn.setBounds(135,750,100,50);
        logoutBtn.setBounds(250,750,100,50);
      
        logo.setBounds(110, 10, 150, 80);
        yearLabel.setBounds(168, 100, 100, 30);
        topPane.setBounds(110, 130, 150, 30);
        titlePane.setBounds(50, 180, 270, 30);
        datePane.setBounds(50, 220, 270, 180);
        classinfo.setBounds(150,440,95,20);
        myPanel1.setBounds(40, 450, 90, 1);
        myPanel2.setBounds(230, 450, 90, 1);
        myPanel3.setBounds(40, 500, 280, 1);
        scrollPane.setBounds(40, 480, 310, 200);
        monthLabel.setForeground(new Color(75,83,157));
      
        
        classname.setFont(new Font("고딕체", Font.BOLD, 13));
        classnum.setFont(new Font("고딕체",Font.BOLD, 13));
        classinfo.setFont(new Font("고딕체", Font.BOLD, 14));
      
        monthLabel.setFont(new Font("고딕체", Font.PLAIN, 15));
        prevBtn.setFont(new Font("고딕체", Font.PLAIN, 15));
        nextBtn.setFont(new Font("고딕체", Font.PLAIN, 15));
        yearLabel.setFont(new Font("고딕체", Font.PLAIN, 15));
        
        manageBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        classBtn.setFont(new Font("고딕체", Font.BOLD, 14));
        logoutBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        
        topPane.add(prevBtn);
        topPane.add(monthLabel);
        topPane.add(nextBtn);
        topPane.setBackground(Color.white);
        datePane.setBackground(Color.white);
       
        myPanel1.setBackground(new Color(228,222,222));
        myPanel2.setBackground(new Color(228,222,222));
        myPanel3.setBackground(new Color(228,222,222));
        classinfo.setBackground(new Color(86,77,77));
        classTable.setBackground(Color.white);
        scrollPane.setBackground(Color.white);
        
        
        manageBtn.setBackground(Color.white);
        classBtn.setBackground(Color.white);
        logoutBtn.setBackground(Color.white);      
      
        
        yearLabel.setText(String.valueOf(year));
        monthLabel.setText(String.valueOf(month));

        prevBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        nextBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        classTable.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        manageBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        classBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        logoutBtn.setBorder(new EmptyBorder(0, 0, 0, 0));    
      
        
        
        prevBtn.setBackground(Color.WHITE);
        nextBtn.setBackground(Color.WHITE);

        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        
        
        setCalendarLabels();
        
        classTable.setBackground(Color.WHITE);
        classTable.getTableHeader().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);
        classTable.setFillsViewportHeight(true);
        classTable.setCellSelectionEnabled(false);
        
   
        // JTableHeader 비활성화
        classTable.getTableHeader().setReorderingAllowed(false);
        classTable.getTableHeader().setResizingAllowed(false);
               
        titlePane.setBackground(Color.white);
        for(int i=0; i<titleStr.length; i++){
        	JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
        	lbl.setFont(new Font("고딕체", Font.PLAIN, 17));
        	titlePane.add(lbl);		}

        
        int arc = 3; 
   
        classBookBtn.setBorder(new LineBorder(new Color(76,60,175), 1) {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(getLineColor());
                g2.setStroke(new BasicStroke(1));
                g2.draw(new RoundRectangle2D.Double(x + arc, y + arc, width - arc * 2, height - arc * 2+1, arc, arc));
               
            }
        });


        classTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = classTable.getSelectedRow();
                int buttonColumn = 3; // 예약하기 버튼이 위치한 열의 인덱스

                if (selectedRow >= 0 && classTable.columnAtPoint(e.getPoint()) == buttonColumn) {
                    System.out.println("예약하기 버튼 클릭됨 - 행: " + selectedRow + ", 열: " + buttonColumn);
                    handleReservation(selectedRow);
                } else {
                    System.out.println("테이블 셀 클릭됨 - 행: " + selectedRow + ", 열: " + classTable.columnAtPoint(e.getPoint()));
                }
            }
        });
     
        hideColumn(classTable, 4);
        
        //하단바 액션리스너 설정
        //1.학습관리로 이동
        manageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	dispose();
            	studentnote studentnote = new studentnote();
            	studentnote.setLocationRelativeTo(null); 
            	studentnote.setVisible(true);
            }
        });
        
     //로그아웃 누르면, 세션이 끊기고, 초기페이지로 이동 
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
              if (SessionManager.isStudentLoggedIn()) {
            	  SessionManager.isStudentLoggedIn = false;
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
    
    public void hideColumn(JTable table, int columnIndex) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setMaxWidth(0);
        column.setMinWidth(0);
        column.setPreferredWidth(0);
        column.setWidth(0);
        table.getTableHeader().getColumnModel().getColumn(columnIndex).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(columnIndex).setMinWidth(0);
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
        String studentId = SessionManager.getLoggedInStudentId();
        String teacherId = loginDb.getTeacherIdForStudent(studentId);
        ArrayList<ClassInfo> studentClasses = loginDb.getClassInfoForTeacher(teacherId);

        tableModel.setRowCount(0);
        studentClasses.sort((class1, class2) -> class1.getClassDate().compareTo(class2.getClassDate()));
        for (ClassInfo classInfo : studentClasses) {
            String classDateTime = classInfo.getClassDate();
            String classTime = getClassTime(classDateTime);
            int currentEnrollment = classInfo.getCurrentEnrollment();
            int classCapacity = Integer.parseInt(classInfo.getClassCapacity());

            String enrollmentInfo = currentEnrollment + "/" + classCapacity;

            JButton reserveButton = new JButton("예약하기");
            reserveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("예약하기 버튼 클릭됨");
                    // 버튼 클릭 이벤트 처리 (예약 로직)
                    int selectedRow = classTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        handleReservation(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "예약할 행을 선택하세요.");
                    }
                }
            });

            String[] rowData = {
                classTime,
                classInfo.getClassName(),
                enrollmentInfo,
                reserveButton.getText(),
                classInfo.getClassId() // classId 추가
            };

            String classId = classInfo.getClassId();
            reserveButton.setEnabled(isReservationPossible(classId));

            if (selectedDate == getClassDay(classDateTime) && selectedYear == getClassYear(classDateTime) && selectedMonth == getClassMonth(classDateTime)) {
                tableModel.addRow(rowData);

                System.out.println("Added row for class: " + classId + ", Enrollment: " + enrollmentInfo);
            }
        }
    }

    
    
    private void handleReservation(int selectedRow) {
        TableModel model = classTable.getModel();
        String classId = (String) model.getValueAt(selectedRow, 4);

        // 예약 가능 여부 확인
        if (isReservationPossible(classId)) {
         
            if (hasAlreadyReservedOnSelectedDay(selectedDate)) {
                JOptionPane.showMessageDialog(null, "하루에 한 번만 예약할 수 있습니다.");
            } else {
              
                if (!loginDb.isAlreadyReserved(SessionManager.getLoggedInStudentId(), classId)) {
                
                    boolean reservationSuccess = loginDb.increaseEnrollment(classId);

                    if (reservationSuccess) {
                     
                        updateReservations(selectedDate);

                        boolean studentReservationSuccess = loginDb.studentReserveClass(SessionManager.getLoggedInStudentId(), classId);

                        if (studentReservationSuccess) {
                           
                            JOptionPane.showMessageDialog(null, "예약이 완료되었습니다.");
                            model.setValueAt(updateEnrollmentInfo(classId), selectedRow, 2);
                            ((DefaultTableModel) model).fireTableDataChanged();
                        } else {
                            JOptionPane.showMessageDialog(null, "이미 예약된 수업입니다.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "예약에 실패했습니다."); 
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "이미 예약된 수업입니다.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "이미 예약되어 있거나 정원이 초과되었습니다.");
        }
    }



    
    private boolean hasAlreadyReservedOnSelectedDay(int selectedDay) {
        String studentId = SessionManager.getLoggedInStudentId();
        Set<Integer> reservations = reservationsPerPersonPerDay.getOrDefault(studentId, new HashSet<>());
        return reservations.contains(selectedDay);
    }

    private void updateReservations(int selectedDay) {
        String studentId = SessionManager.getLoggedInStudentId();
        reservationsPerPersonPerDay.computeIfAbsent(studentId, k -> new HashSet<>()).add(selectedDay);
    }
    
    private boolean isReservationPossible(String classId) {
        if (loginDb != null) {
            ClassInfo classInfo = loginDb.getClassInfo(classId);
            if (classInfo != null) {
                int currentEnrollment = classInfo.getCurrentEnrollment();
                int classCapacity = Integer.parseInt(classInfo.getClassCapacity());

                return currentEnrollment < classCapacity;
            }
        }
        return false;
    }


    private String updateEnrollmentInfo(String classId) {
        System.out.println("updateEnrollmentInfo called with classId: " + classId);

      
        ClassInfo classInfo = loginDb.getClassInfo(classId);

      
        System.out.println("Current enrollment: " + classInfo.getCurrentEnrollment());
        System.out.println("Class capacity: " + classInfo.getClassCapacity());

        return classInfo.getCurrentEnrollment() + "/" + classInfo.getClassCapacity();
    }

  
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
                
                lbl.setBackground(Color.WHITE);
            }

            lbl.setOpaque(true);
            lbl.addMouseListener(new DateLabelMouseListener(i));
            
            if (y < currentYear || (y == currentYear && m - 1 < currentMonth) || (y == currentYear && m - 1 == currentMonth && i < currentDate)) {
                lbl.setForeground(new Color(125,133,163)); 
            } else if (y > currentYear || (y == currentYear && m - 1 > currentMonth) || (y == currentYear && m - 1 == currentMonth && i > currentDate)) {
                lbl.setForeground(new Color(76,60,175));
            } else {
                lbl.setForeground(new Color(76,60,175));
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

    public class TransparentHeaderRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setForeground(Color.WHITE); // 헤더 텍스트 색상 설정
            label.setBackground(new Color(0, 0, 0, 0)); // 헤더 배경을 투명으로 설정
            return label;
        }
    }

    
    public static void main(String[] args) {
        new SSwingCalendar();
    }
}