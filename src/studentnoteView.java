import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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


public class studentnoteView extends JFrame {

    private ImageIcon logoimg = new ImageIcon("src/img/로고2.png");
    private ImageIcon yogaimg = new ImageIcon("src/img/요가사진.png");
    private JLabel logo = new JLabel(logoimg);
    private JLabel yoga = new JLabel(yogaimg);
    private JTable noteTable;
    private DefaultTableModel tableModel;
    JPanel myPanel1 = new JPanel();
    JPanel myPanel2 = new JPanel();
    JButton manageBtn = new JButton("수강관리");
    JButton classBtn = new JButton("수업관리");
    JButton logoutBtn = new JButton("로그아웃");
    JLabel classing = new JLabel("학습 기록");
    private JButton back = new JButton("<");
    

    public studentnoteView(String studentId) {

    	if (!SessionManager.isTeacherLoggedIn()) {
            JOptionPane.showMessageDialog(null, "먼저 로그인하세요.");
            System.exit(0);
        }

        setTitle("학습 기록 보기");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);

      
        String[] columnNames = { "수업 아이디", "날짜", "사용자 입력" };

        tableModel = new DefaultNonEditableTableModel(columnNames, 0) {
            @Override
            public Object getValueAt(int row, int column) {
                if (column == 1) {
                 
                    String fullDate = (String) super.getValueAt(row, column);
                   
                    String dateOnly = fullDate.substring(0, 10);
                    return dateOnly;
                } else {
                    return super.getValueAt(row, column);
                }
            }
        };

        noteTable = new JTable(tableModel);
        noteTable.setFillsViewportHeight(true);
        noteTable.setCellSelectionEnabled(false);

    
        configureTableUI();
        updateNoteTable(studentId);

        noteTable.setBackground(Color.WHITE);

        c.add(back);
        c.add(classing);
        c.add(logo);
        c.add(myPanel1);
        c.add(myPanel2);
        c.add(noteTable);
        c.add(manageBtn);
        c.add(classBtn);
        c.add(logoutBtn);
        c.add(yoga);

        myPanel1.setBackground(new Color(228, 222, 222));
        myPanel2.setBackground(new Color(228, 222, 222));
        myPanel1.setBounds(75, 190, 1, 500);
        myPanel2.setBounds(40, 215, 300, 1);
        classing.setBounds(80, 160, 80, 80);
        manageBtn.setBounds(20, 750, 100, 50);
        classBtn.setBounds(135, 750, 100, 50);
        logoutBtn.setBounds(250, 750, 100, 50);
        manageBtn.setFont(new Font("고딕체", Font.BOLD, 14));
        classBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        logoutBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        classing.setFont(new Font("고딕체", Font.BOLD, 14));
        back.setFont(new Font("고딕체", Font.PLAIN, 20));
        yoga.setBounds(20, 100, 330, 80);
        manageBtn.setBackground(Color.white);
        classBtn.setBackground(Color.white);
        back.setBackground(Color.WHITE);
        logoutBtn.setBackground(Color.white);
        back.setForeground(new Color(75,80,157));

        manageBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        classBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        logoutBtn.setBorder(new EmptyBorder(0, 0, 0, 0));

        logo.setBounds(110, 10, 150, 80);
        noteTable.setBounds(3, 220, 310, 400);
        noteTable.setBackground(Color.white);
        noteTable.setBorder(new EmptyBorder(0, 0, 0, 0));
        back.setBorder(new EmptyBorder(0,0,0,0));
        noteTable.setBackground(Color.WHITE);
        classing.setForeground(new Color(86, 77, 77));
        back.setBounds(10,30,50,50);
		

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {       	
            	  dispose();
            	  studentManagement studentManagement = new studentManagement();
            	  studentManagement.setLocationRelativeTo(null); 
            	  studentManagement.setVisible(true);
            }
        });
	 
       
        classBtn.addActionListener(e -> {
            dispose();
            TSwingCalendar TSwingCalendar = new TSwingCalendar();
            TSwingCalendar.setLocationRelativeTo(null);
            TSwingCalendar.setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            if (SessionManager.isTeacherLoggedIn()) {
                SessionManager.isTeacherLoggedIn = false;
                JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
                dispose();
                StudentTeacher StudentTeacher = new StudentTeacher();
                StudentTeacher.setLocationRelativeTo(null);
                StudentTeacher.setVisible(true);
            }
        });
        
        this.requestFocusInWindow();
        setSize(390, 844);
        setVisible(true);
    }

    private void updateNoteTable(String studentId) {
        
        if (SessionManager.isTeacherLoggedIn()) {
            loginDatabase db = new loginDatabase();

          
            ArrayList<Enrollment> enrollments = db.getEnrollmentsForStudent(studentId);

            tableModel.setRowCount(0);

            for (Enrollment enrollment : enrollments) {
                Object[] rowData = { enrollment.getClassId(), enrollment.getReservationDate(), enrollment.getFeedback() };
                tableModel.addRow(rowData);
            }
        }
    }

    class DefaultNonEditableTableModel extends DefaultTableModel {
        public DefaultNonEditableTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private void configureTableUI() {
        JTableHeader header = noteTable.getTableHeader();
        header.setFont(new Font("고딕체", Font.BOLD, 13));
        header.setUI(new BasicTableHeaderUI());
        header.setBackground(Color.WHITE);
        header.setBorder(new LineBorder(Color.WHITE));
        header.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));

        noteTable.setBorder(null);
        noteTable.setBackground(Color.WHITE);
        noteTable.setGridColor(Color.WHITE);
        noteTable.setRowHeight(30);
        noteTable.setOpaque(true);
        noteTable.setBackground(Color.WHITE);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.WHITE);
        noteTable.setDefaultRenderer(Object.class, renderer);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) noteTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.LEFT);

        TableColumnModel columnModel = noteTable.getColumnModel();

        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(1).setPreferredWidth(38);
        columnModel.getColumn(2).setPreferredWidth(200);
    }

    public static void main(String[] args) {
        // 테스트 코드
        new studentnoteView("학생ID_테스트");
    }
}
