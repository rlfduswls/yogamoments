import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.JButton;

public class studentManagement extends JFrame {
	
    private ImageIcon logoimg = new ImageIcon("src/img/로고2.png");
    private JLabel logo = new JLabel(logoimg);
    private JTable studentTable;
    private DefaultTableModel tableModel;
    JPanel myPanel1 = new JPanel();
    JPanel myPanel2 = new JPanel();
    JButton manageBtn = new JButton("수강관리");
    JButton classBtn = new JButton("수업관리");
    JButton logoutBtn = new JButton("로그아웃");
    
    public studentManagement() {
    	
         if (!SessionManager.isTeacherLoggedIn()) {
         JOptionPane.showMessageDialog(null, "먼저 로그인하세요.");
         System.exit(0);
         }

        setTitle("캘린더");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);

        // 테이블 컬럼 설정
        String[] columnNames = { "수강생 관리","수강생 아이디" };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setFillsViewportHeight(true);
        studentTable.setCellSelectionEnabled(false);

        // 테이블 UI 설정
        configureTableUI();

        // 데이터베이스에서 회원 정보를 가져와 테이블에 추가
        updateStudentTable();

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBackground(Color.WHITE);

        
        c.add(logo);
        c.add(myPanel1);
        c.add(myPanel2);
        c.add(scrollPane);
        c.add(manageBtn);
        c.add(classBtn);
        c.add(logoutBtn);
        
        manageBtn.setBounds(20,750,100,50);
        classBtn.setBounds(135,750,100,50);
        logoutBtn.setBounds(250,750,100,50);

        manageBtn.setFont(new Font("고딕체", Font.BOLD, 14));
        classBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        logoutBtn.setFont(new Font("고딕체", Font.PLAIN, 14));


        manageBtn.setBackground(Color.white);
        classBtn.setBackground(Color.white);
        logoutBtn.setBackground(Color.white);      
      
        manageBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        classBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        logoutBtn.setBorder(new EmptyBorder(0, 0, 0, 0));    
        
        myPanel1.setBackground(new Color(228, 222, 222));
        myPanel2.setBackground(new Color(228, 222, 222));
        myPanel1.setBounds(50, 130, 1, 500);
        myPanel2.setBounds(30, 155, 300, 1);
        logo.setBounds(110, 10, 150, 80);
        scrollPane.setBounds(55, 133, 310, 500);
        scrollPane.setBackground(Color.white);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setBackground(Color.WHITE);

        classBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	dispose();
            	TSwingCalendar TSwingCalendar = new TSwingCalendar();
            	TSwingCalendar.setLocationRelativeTo(null); 
            	TSwingCalendar.setVisible(true);
            }
        });
 
        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               
                if (!e.getValueIsAdjusting()) {
                
                    int selectedRow = studentTable.getSelectedRow();

                   
                    if (selectedRow != -1) {
              
                        StudentInfo selectedStudent = getSelectedStudentInfo(selectedRow);

                        
                        if (SessionManager.getTeacherLoggedInStatus()) {
                            // 학생의 아이디를 가져와서 studentnoteView 클래스를 생성
                            String studentId = selectedStudent.getStudentId();
                            studentnoteView studentViewPage = new studentnoteView(studentId);
                            dispose();
                            studentViewPage .setLocationRelativeTo(null); 
                            studentViewPage .setVisible(true);
                         
                        }
                    }
                }
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
    }

    private StudentInfo getSelectedStudentInfo(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            String studentName = (String) studentTable.getValueAt(selectedRow, 0);
            String studentId = (String) studentTable.getValueAt(selectedRow, 1);

            return new StudentInfo(studentId, studentName);
        }

        return null;
    }

    private void configureTableUI() {
        JTableHeader header = studentTable.getTableHeader();
        header.setFont(new Font("고딕체", Font.BOLD, 13));
        header.setUI(new BasicTableHeaderUI());
        header.setBackground(Color.WHITE);
        header.setBorder(new LineBorder(Color.WHITE));
        header.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));

        studentTable.setBorder(null);
        studentTable.setBackground(Color.WHITE);
        studentTable.setGridColor(Color.WHITE);
        studentTable.setRowHeight(30);
        studentTable.setOpaque(true);
        studentTable.setBackground(Color.WHITE);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.WHITE);
        studentTable.setDefaultRenderer(Object.class, renderer);

        // 셀 내용을 왼쪽 정렬로 설정
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) studentTable.getTableHeader()
                .getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.LEFT);

        TableColumnModel columnModel = studentTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setMinWidth(0);
        columnModel.getColumn(1).setMaxWidth(0);
        columnModel.getColumn(1).setWidth(0);
    }

    private void updateStudentTable() {
        // 데이터베이스에서 회원 정보 가져오는 코드 작성
        ArrayList<StudentInfo> students = getStudentsFromDatabase();

        // 가져온 데이터를 테이블에 추가
        for (StudentInfo student : students) {
            String[] rowData = { student.getStudentName(), student.getStudentId() };
            tableModel.addRow(rowData);
        }
    }


    private ArrayList<StudentInfo> getStudentsFromDatabase() {
        ArrayList<StudentInfo> students = new ArrayList<>();

        // loginDatabase 객체 생성
        loginDatabase db = new loginDatabase();

        // 현재 로그인한 선생님의 아이디를 가져오기
        String loggedInTeacherId = SessionManager.getLoggedInTeacherId();

        if (loggedInTeacherId != null) {
            // 선생님 아이디를 기반으로 해당 선생님에게 속한 학생들의 정보를 가져오기
            ArrayList<StudentInfo> studentsInTeacher = db.getStudentsInTeacher(loggedInTeacherId);

            // 가져온 데이터를 반환
            return studentsInTeacher;
        }

        return students;
    }


    public static void main(String[] args) {
        new studentManagement();
    }
}
