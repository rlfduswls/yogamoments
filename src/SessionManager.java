public class SessionManager {
    private static String loggedInTeacherId;
    public static boolean isTeacherLoggedIn = false;

    private static String loggedInStudentId;
    public static boolean isStudentLoggedIn = false;

    public static void setLoggedInTeacherId(String teacherId) {
        loggedInTeacherId = teacherId;
    }

    public static String getLoggedInTeacherId() {
        return loggedInTeacherId;
    }

    public static boolean isTeacherLoggedIn() {
        return isTeacherLoggedIn;
    }

    public static void setTeacherLoggedIn(boolean loggedIn) {
        isTeacherLoggedIn = loggedIn;
    }

    public static void setLoggedInStudentId(String studentId) {
        loggedInStudentId = studentId;
    }

    public static String getLoggedInStudentId() {
        return loggedInStudentId;
    }

    public static boolean isStudentLoggedIn() {
        return isStudentLoggedIn;
    }

    public static void setStudentLoggedIn(boolean loggedIn) {
        isStudentLoggedIn = loggedIn;
    }
    
    public static boolean getTeacherLoggedInStatus() {
        return isTeacherLoggedIn;
    }
}
