public class Enrollment {
    private String studentId;
    private String classId;
    private String reservationDate;
    private String feedback;

    public Enrollment(String studentId, String classId, String reservationDate, String feedback) {
        this.studentId = studentId;
        this.classId = classId;
        this.reservationDate = reservationDate;
        this.feedback = feedback;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getClassId() {
        return classId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getFeedback() {
        return feedback;
    }
}
