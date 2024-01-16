// StudentInfo 클래스 수정
public class StudentInfo {
    private String studentId;
    private String studentName;

    public StudentInfo(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
}
