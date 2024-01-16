import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
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
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class studentnote extends JFrame {

    private ImageIcon logoimg = new ImageIcon("src/img/로고2.png");
    private ImageIcon yogaimg = new ImageIcon("src/img/요가사진.png");
    private JLabel logo = new JLabel(logoimg);
    private JLabel yoga = new JLabel(yogaimg);
    private JTable noteTable;
    private DefaultTableModel tableModel;
    JPanel myPanel1 = new JPanel();
    JPanel myPanel2 = new JPanel();
    JButton manageBtn = new JButton("학습관리");
    JButton classBtn = new JButton("수업예약");
    JButton logoutBtn = new JButton("로그아웃");
    JLabel classing = new JLabel("학습 기록");

    public studentnote() {

        if (!SessionManager.isStudentLoggedIn()) {
            JOptionPane.showMessageDialog(null, "먼저 로그인하세요.");
            System.exit(0);
        }

        setTitle("학습관리");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.WHITE);

     
        String[] columnNames = { "수업 아이디", "날짜", "사용자 입력" };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; 
            }

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
        updateStudentTable();

        noteTable.setBackground(Color.WHITE);

        c.add(classing);
        c.add(yoga);
        c.add(logo);
        c.add(myPanel1);
        c.add(myPanel2);
        c.add(noteTable);
        c.add(manageBtn);
        c.add(classBtn);
        c.add(logoutBtn);

        myPanel1.setBackground(new Color(228, 222, 222));
        myPanel2.setBackground(new Color(228, 222, 222));
        myPanel1.setBounds(75, 190, 1, 500);
        myPanel2.setBounds(40, 215, 300, 1);
        classing.setBounds(80, 160, 80, 80);
        manageBtn.setBounds(20, 750, 100, 50);
        classBtn.setBounds(135, 750, 100, 50);
        logoutBtn.setBounds(250, 750, 100, 50);
        yoga.setBounds(20, 100, 330, 80);
        manageBtn.setFont(new Font("고딕체", Font.BOLD, 14));
        classBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        logoutBtn.setFont(new Font("고딕체", Font.PLAIN, 14));
        classing.setFont(new Font("고딕체", Font.BOLD, 14));

        manageBtn.setBackground(Color.white);
        classBtn.setBackground(Color.white);
        logoutBtn.setBackground(Color.white);

        manageBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        classBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        logoutBtn.setBorder(new EmptyBorder(0, 0, 0, 0));

      
        logo.setBounds(110, 10, 150, 80);
        noteTable.setBounds(3, 220, 310, 400);
        noteTable.setBackground(Color.white);
        noteTable.setBorder(new EmptyBorder(0, 0, 0, 0));
        noteTable.setBackground(Color.WHITE);
        classing.setForeground(new Color(86, 77, 77));

       
        classBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                SSwingCalendar SSwingCalendar = new SSwingCalendar();
                SSwingCalendar.setLocationRelativeTo(null);
                SSwingCalendar.setVisible(true);
            }
        });

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
        
        noteTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                	  loginDatabase db = new loginDatabase();
                    int selectedRow = noteTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String classId = (String) noteTable.getValueAt(selectedRow, 0);
                        String feedback = (String) noteTable.getValueAt(selectedRow, 2);

                       
                        db.updateFeedback(classId, feedback);
                        //일단 콘솔로 출력해보고, 나중에 다이어로그로 보여주도록
                        System.out.println("피드백이 업데이트되었습니다: " + feedback);
                        JOptionPane.showMessageDialog(null, "수업에 대한 기록이 등록되었습니다.");
                    }
                }
            }
        });
        
        
        setSize(390, 844);
        setVisible(true);
    }

   
    private class PlaceholderRenderer extends DefaultTableCellRenderer {
        private String placeholder;

        public PlaceholderRenderer(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
           
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.LEFT);

            if (value == null || value.toString().isEmpty()) {
                label.setText(placeholder);
                label.setForeground(Color.GRAY);
            } else {
                label.setText(value.toString());
                label.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            }

            return label;
        }
    }
    private void updateStudentTable() {

        if (SessionManager.isStudentLoggedIn()) {
           
            String studentId = SessionManager.getLoggedInStudentId();

            loginDatabase db = new loginDatabase();

     
            ArrayList<Enrollment> enrollments = db.getEnrollmentsForStudent(studentId);

         
            tableModel.setRowCount(0);

        
            for (Enrollment enrollment : enrollments) {
                Object[] rowData = { enrollment.getClassId(), enrollment.getReservationDate(), enrollment.getFeedback() };
                tableModel.addRow(rowData);
            }
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
        columnModel.getColumn(2).setCellRenderer(new PlaceholderRenderer("  수업에 대한 기록을 남겨보세요"));

        columnModel.getColumn(0).setMinWidth(0);
        columnModel.getColumn(0).setMaxWidth(0);
        columnModel.getColumn(0).setWidth(0);
        columnModel.getColumn(1).setPreferredWidth(38);
        columnModel.getColumn(2).setPreferredWidth(200);
    }
    
    

    public static void main(String[] args) {
        new studentnote();
    }
}
