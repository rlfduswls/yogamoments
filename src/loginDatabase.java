
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import javax.swing.JOptionPane;

public class loginDatabase {
	 private String teacherId;
	 
	Connection con = null;
	Statement stmt = null;
	String url = "jdbc:mysql://localhost/yogamoments?serverTimezone=Asia/Seoul";
	String user = "root";
	String passwd = "0000";		
	
	
	ArrayList<ClassInfo> getClassInfoForTeacher(String teacherId) {
	ArrayList<ClassInfo> classInfoList = new ArrayList<>();

	    try {
	        String query = "SELECT classid, classdate, classname, current_enrollment, classnum, teacherid FROM Class WHERE teacherid = ?";
	        PreparedStatement preparedStatement = con.prepareStatement(query);
	        preparedStatement.setString(1, teacherId);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	        	String classid=resultSet.getString("classid");
	            String classDate = resultSet.getString("classdate");
	            String className = resultSet.getString("classname");
	            int currentEnrollment = resultSet.getInt("current_enrollment");
	            String classCapacity = resultSet.getString("classnum");
	            String teacherIdFromDB = resultSet.getString("teacherid");

	            ClassInfo classInfo = new ClassInfo(classid,classDate, className, currentEnrollment, classCapacity, teacherIdFromDB);
	            classInfoList.add(classInfo);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return classInfoList;
	}

	String getClassIdFromClassName(String className) {
        String classId = null;

        try {
            String query = "SELECT classid FROM Class WHERE classname = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, className);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                classId = resultSet.getString("classid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classId;
    }
	
	
	loginDatabase() {	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			System.out.println("MySQL 서버 연동 성공");
		} catch(Exception e) {
			System.out.println("MySQL 서버 연동 실패 > " + e.toString());
		}
	}

	/* 로그인 정보를 확인 */
	boolean Slogincheck(String _i, String _p) {
	    boolean flag = false;

	    String id = _i;
	    String pw = _p;

	    try {
	        String checkingStr = "SELECT pwText FROM student WHERE idText='" + id + "'";
	        ResultSet result = stmt.executeQuery(checkingStr);

	        if (result.next() && pw.equals(result.getString("pwText"))) {
	            flag = true;
	            System.out.println("로그인 성공");
	        } else {
	            flag = false;
	            System.out.println("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다.");
	        }
	    } catch (SQLException e) {
	        flag = false;
	        System.out.println("로그인 실패: SQL 오류 - " + e.toString());
	    } catch (Exception e) {
	        flag = false;
	        System.out.println("로그인 실패: 예외 발생 - " + e.toString());
	    }

	    return flag;
	}
	
	boolean Tlogincheck(String _i, String _p) {
	    boolean flag = false;

	    String id = _i;
	    String pw = _p;

	    try {
	        String checkingStr = "SELECT pwText FROM teacher WHERE idText='" + id + "'";
	        ResultSet result = stmt.executeQuery(checkingStr);

	        if (result.next() && pw.equals(result.getString("pwText"))) {
	            flag = true;
	            teacherId = id; 
	            System.out.println("로그인 성공");
	        } else {
	            flag = false;
	            System.out.println("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다.");
	        }
	    } catch (SQLException e) {
	        flag = false;
	        System.out.println("로그인 실패: SQL 오류 - " + e.toString());
	    } catch (Exception e) {
	        flag = false;
	        System.out.println("로그인 실패: 예외 발생 - " + e.toString());
	    }

	    return flag;
	}
	boolean SjoinCheck(String _i, String _p, String _n, String _t) {
	    boolean flag = false;

	    String id = _i;
	    String pw = _p;
	    String name = _n;
	    String teacherid = _t;

	    try {
	      
	        String teacherCheckQuery = "SELECT COUNT(*) AS count FROM teacher WHERE idText='" + teacherid + "'";
	        ResultSet teacherCheckResult = stmt.executeQuery(teacherCheckQuery);

	        if (teacherCheckResult.next() && teacherCheckResult.getInt("count") > 0) {
	           
	            String insertStr = "INSERT INTO student VALUES('" + id + "', '" + pw + "','" + name + "','" + teacherid + "')";
	            System.out.println("Insert Query: " + insertStr);

	            int rowsAffected = stmt.executeUpdate(insertStr);

	            if (rowsAffected > 0) {
	                flag = true;
	                System.out.println("회원가입 성공");
	            } else {
	                flag = false;
	                System.out.println("회원가입 실패: No rows affected");
	            }
	        } else {
	           
	            flag = false;
	            JOptionPane.showMessageDialog(null, "선생님 이름이 알맞지 않습니다.", "선생님 이름 입력", JOptionPane.WARNING_MESSAGE);
	          
	        }
	        
	    }catch (SQLIntegrityConstraintViolationException e) {
            flag = false;
            JOptionPane.showMessageDialog(null, "아이디가 중복 되었습니다.", "아이디 중복", JOptionPane.WARNING_MESSAGE); 
	    } catch (SQLException e) {
	        flag = false;
	        e.printStackTrace();
	        System.out.println("회원가입 실패: SQL 오류 - " + e.toString());
	    } catch (Exception e) {
	        flag = false;
	        e.printStackTrace();
	        System.out.println("회원가입 실패: 예외 발생 - " + e.toString());
	    }

	    return flag;
	}


	boolean TjoinCheck(String _i, String _p, String _n) {
	    boolean flag = false;

	    String id = _i;
	    String pw = _p;
	    String name = _n;

	    try {
	        String insertStr = "INSERT INTO teacher VALUES('" + id + "', '" + pw + "','" + name + "')";
	        System.out.println("Insert Query: " + insertStr);  // Add this line for debugging
	        int rowsAffected = stmt.executeUpdate(insertStr);

	        if (rowsAffected > 0) {
	            flag = true;
	            teacherId = id;
	            System.out.println("회원가입 성공");
	        } else {
	            flag = false;
	            System.out.println("회원가입 실패: No rows affected");
	        }
	    }catch (SQLIntegrityConstraintViolationException e) {
            flag = false;
            JOptionPane.showMessageDialog(null, "아이디가 중복되었습니다.", "아이디 중복", JOptionPane.WARNING_MESSAGE); } 
	    catch (SQLException e) {
	        flag = false;
	        e.printStackTrace();
	        System.out.println("회원가입 실패: SQL 오류 - " + e.toString());
	    } catch (Exception e) {
	        flag = false;
	        e.printStackTrace();
	        System.out.println("회원가입 실패: 예외 발생 - " + e.toString());
	    }

	    return flag;
	}
	
	String getTeacherId() {
	    return teacherId;
	}

	boolean classSignup(String classid, String classname, String classdate, String classnum, String teacherId) {
	    boolean flag = false;

	    try {
	    	String teacherid = SessionManager.getLoggedInTeacherId();
	    	
	        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	        Date date = inputFormat.parse(classdate);

	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = outputFormat.format(date);

	        System.out.println("Formatted Date: " + formattedDate);

	        // 삽입 전에 중복 확인
	        String duplicateCheckQuery =  "SELECT COUNT(*) AS count FROM Class WHERE classdate='" + formattedDate + "' AND teacherid='" + teacherid + "'";
            
	        ResultSet duplicateCheckResult = stmt.executeQuery(duplicateCheckQuery);

	        System.out.println("Duplicate Check Query: " + duplicateCheckQuery);

	        if (duplicateCheckResult.next() && duplicateCheckResult.getInt("count") == 0) {
	            // 중복 없음, 삽입 진행
	            String insertClassStr = "INSERT INTO Class (classid, classname, classdate, classnum, teacherid) VALUES ('"
	                    + classid + "', '" + classname + "', '" + formattedDate + "', '" + classnum + "', '" + teacherid + "')";
	            System.out.println("Insert Class Query: " + insertClassStr);
	            int rowsAffected = stmt.executeUpdate(insertClassStr);

	            if (rowsAffected > 0) {
	                flag = true;
	                System.out.println("수업 등록 성공");
	            } else {
	                flag = false;
	                System.out.println("수업 등록 실패: No rows affected");
	            }
	        } else {
	            // 중복 발견
	            flag = false;
	            JOptionPane.showMessageDialog(null, "같은 날짜 및 시간에 수업이 있거나, 같은 아이디의 수업이 등록되어 있습니다.", "중복 수업", JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (SQLIntegrityConstraintViolationException e) {
	        flag = false;
	        JOptionPane.showMessageDialog(null,"같은 날짜 및 시간에 수업이 있거나, 같은 아이디의 수업이 등록되어 있습니다.", "중복 수업", JOptionPane.WARNING_MESSAGE);
	    } catch (SQLException e) {
	        flag = false;
	        e.printStackTrace();
	        System.out.println("수업 등록 실패: SQL 오류 - " + e.toString());
	        if (e instanceof SQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(null, "아이디가 중복 되었습니다.", "아이디 중복", JOptionPane.WARNING_MESSAGE);
            }
        } catch (ParseException e) {
            flag = false;
            JOptionPane.showMessageDialog(null, "시간 입력은 20000101 00:00:00 으로 해주세요.", "시간 입력 오류",
                    JOptionPane.WARNING_MESSAGE);
            
	    } catch (Exception e) {
	        flag = false;
	        e.printStackTrace();
	        System.out.println("수업 등록 실패: 예외 발생 - " + e.toString());
	    }
	    

	    return flag;
	}
	
	 boolean increaseEnrollment(String classId) {
	        boolean flag = false;

	        try {
	            // 현재 등록된 수업의 current_enrollment 값을 가져오기
	            String getCurrentEnrollmentQuery = "SELECT current_enrollment FROM Class WHERE classid = ?";
	            PreparedStatement getCurrentEnrollmentStatement = con.prepareStatement(getCurrentEnrollmentQuery);
	            getCurrentEnrollmentStatement.setString(1, classId);
	            ResultSet enrollmentResult = getCurrentEnrollmentStatement.executeQuery();

	            if (enrollmentResult.next()) {
	                int currentEnrollment = enrollmentResult.getInt("current_enrollment");

	                // current_enrollment 값을 1 증가시켜 업데이트
	                String updateEnrollmentQuery = "UPDATE Class SET current_enrollment = ? WHERE classid = ?";
	                PreparedStatement updateEnrollmentStatement = con.prepareStatement(updateEnrollmentQuery);
	                updateEnrollmentStatement.setInt(1, currentEnrollment + 1);
	                updateEnrollmentStatement.setString(2, classId);

	                int rowsAffected = updateEnrollmentStatement.executeUpdate();

	                if (rowsAffected > 0) {
	                    flag = true;
	                    System.out.println("수업 등록 및 current_enrollment 증가 성공");
	                } else {
	                    flag = false;
	                    System.out.println("수업 등록 실패 또는 current_enrollment 증가 실패: No rows affected");
	                }
	            } else {
	                flag = false;
	                System.out.println("수업 등록 및 current_enrollment 증가 실패: 해당 classid의 수업을 찾을 수 없습니다.");
	            }
	        } catch (SQLException e) {
	            flag = false;
	            e.printStackTrace();
	            System.out.println("수업 등록 및 current_enrollment 증가 실패: SQL 오류 - " + e.toString());
	        } catch (Exception e) {
	            flag = false;
	            e.printStackTrace();
	            System.out.println("수업 등록 및 current_enrollment 증가 실패: 예외 발생 - " + e.toString());
	        }

	        return flag;
	    }


	 
	 public ClassInfo getClassInfo(String classId) {
		    ClassInfo classInfo = null;

		    try {
		        String query = "SELECT classid, classdate, classname, current_enrollment, classnum, teacherid FROM Class WHERE classid = ?";
		        PreparedStatement preparedStatement = con.prepareStatement(query);
		        preparedStatement.setString(1, classId);

		        ResultSet resultSet = preparedStatement.executeQuery();

		        if (resultSet.next()) {
		            String retrievedClassId = resultSet.getString("classid");
		            String classDate = resultSet.getString("classdate");
		            String className = resultSet.getString("classname");
		            int currentEnrollment = resultSet.getInt("current_enrollment");
		            String classCapacity = resultSet.getString("classnum");
		            String teacherIdFromDB = resultSet.getString("teacherid");

		            // 여기서 retrievedClassId를 사용하여 ClassInfo 객체를 생성할 때 classId를 설정
		            classInfo = new ClassInfo(retrievedClassId, classDate, className, currentEnrollment, classCapacity, teacherIdFromDB);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return classInfo;
		}

	 
	 boolean studentReserveClass(String studentId, String classId) {
		    try {
		        if (isAlreadyReserved(studentId, classId)) {
		            JOptionPane.showMessageDialog(null, "이미 예약된 수업입니다.", "중복 예약", JOptionPane.WARNING_MESSAGE);
		            return false;
		        }

		        boolean flag = reserveClass(studentId, classId);

		        if (flag) {
		            System.out.println("예약 성공. Student ID: " + studentId + ", Class ID: " + classId);
		        } else {
		            System.out.println("예약 실패");
		        }

		        return flag;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle database-related errors
		        return false;
		    }
		}

		private boolean reserveClass(String studentId, String classId) throws SQLException {
		    try {
		        // Get class date from the Class table
		        String getClassDateQuery = "SELECT classdate FROM Class WHERE classid = ?";
		        try (PreparedStatement getClassDateStatement = con.prepareStatement(getClassDateQuery)) {
		            getClassDateStatement.setString(1, classId);
		            ResultSet classDateResult = getClassDateStatement.executeQuery();

		            if (classDateResult.next()) {
		                // Retrieve class date and insert into Enrollments table
		                String classDate = classDateResult.getString("classdate");
		                String reserveClassQuery = "INSERT INTO Enrollments (student_id, class_id, reservationDate) VALUES (?, ?, ?)";
		                try (PreparedStatement reserveClassStatement = con.prepareStatement(reserveClassQuery)) {
		                    reserveClassStatement.setString(1, studentId);
		                    reserveClassStatement.setString(2, classId);
		                    reserveClassStatement.setString(3, classDate);

		                    int rowsAffected = reserveClassStatement.executeUpdate();

		                    return rowsAffected > 0;
		                }
		            } else {
		                // Class date not found
		                return false;
		            }
		        }
		    } catch (SQLIntegrityConstraintViolationException e) {
		        // Duplicate reservation handling
		        return false;
		    }
		}


	 public boolean isAlreadyReserved(String studentId, String classId) {
		    try {
		        // con이 초기화되었는지 확인
		        if (con == null) {
		            System.out.println("Connection is not initialized.");
		            return false;
		        }

		        String checkReservationQuery = "SELECT * FROM Enrollments WHERE student_id = ? AND class_id = ?";
		        try (PreparedStatement checkReservationStatement = con.prepareStatement(checkReservationQuery)) {
		            checkReservationStatement.setString(1, studentId);
		            checkReservationStatement.setString(2, classId);

		            try (ResultSet resultSet = checkReservationStatement.executeQuery()) {
		                return resultSet.next();  // 결과가 있으면 이미 예약된 상태
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false; // 예외 발생 시 false 반환 또는 다른 적절한 처리
		    }
		}
	 
	 String getStudentIdFromStudentName(String studentName, String teacherId) {
	        String studentId = null;

	        try {
	            String query = "SELECT idText FROM student WHERE nameText = ? AND teacherid = ?";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, studentName);
	            preparedStatement.setString(2, teacherId);

	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                studentId = resultSet.getString("idText");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return studentId;
	    }

	 ArrayList<StudentInfo> getStudentInfoInTeacher(String teacherId) {
	        ArrayList<StudentInfo> studentInfoList = new ArrayList<>();

	        try {
	            String query = "SELECT idText, nameText FROM student WHERE teacherid = ?";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, teacherId);

	            ResultSet resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                String studentId = resultSet.getString("idText");
	                String studentName = resultSet.getString("nameText");

	                StudentInfo studentInfo = new StudentInfo(studentId, studentName);
	                studentInfoList.add(studentInfo);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return studentInfoList;
	    }
	 
	 
	 ArrayList<StudentInfo> getStudentsInTeacher(String teacherId) {
	        ArrayList<StudentInfo> students = new ArrayList<>();

	        try {
	            String query = "SELECT idText, nameText FROM student WHERE teacherid = ?";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, teacherId);

	            ResultSet resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                String studentId = resultSet.getString("idText");
	                String studentName = resultSet.getString("nameText");
	                
	                // Create a StudentInfo object and add it to the list
	                StudentInfo studentInfo = new StudentInfo(studentId, studentName);
	                students.add(studentInfo);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return students;
	    }

	    String getTeacherIdForStudent(String studentId) {
	        String teacherId = null;

	        try {
	            String query = "SELECT teacherid FROM student WHERE idText = ?";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, studentId);

	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                teacherId = resultSet.getString("teacherid");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return teacherId;
	    }
	

	  
	    ArrayList<Enrollment> getEnrollmentsForStudent(String studentId) {
	        ArrayList<Enrollment> enrollments = new ArrayList<>();

	        try {
	            String query = "SELECT class_id, reservationDate, feedback FROM enrollments WHERE student_id = ?";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, studentId);

	            ResultSet resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                String classId = resultSet.getString("class_id");
	                String reservationDate = resultSet.getString("reservationDate");
	                String feedback = resultSet.getString("feedback");

	                Enrollment enrollment = new Enrollment(studentId, classId, reservationDate, feedback);
	                enrollments.add(enrollment);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return enrollments;
	    }

	    public void updateFeedback(String classId, String feedback) {
	        try {
	            String query = "UPDATE enrollments SET feedback = ? WHERE class_id = ?";
	            PreparedStatement preparedStatement = con.prepareStatement(query);
	            preparedStatement.setString(1, feedback);
	            preparedStatement.setString(2, classId);

	            int affectedRows = preparedStatement.executeUpdate();

	            if (affectedRows > 0) {
	                System.out.println("피드백이 업데이트되었습니다.");
	            } else {
	                System.out.println("피드백 업데이트 실패: 해당하는 classId를 찾을 수 없습니다.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("피드백 업데이트 중 오류 발생: " + e.getMessage());
	        }
	    }


	 }
