import java.util.ArrayList;

public class ClassInfo {
    private String classDate;
    private String className;
    private int currentEnrollment;
    private String classCapacity;
    private String teacherId;
    private String classId;
    
    public ClassInfo(String classId, String classDate, String className, int currentEnrollment, String classCapacity, String teacherId) {
        this.classId = classId;
        this.classDate = classDate;
        this.className = className;
        this.currentEnrollment = currentEnrollment;
        this.classCapacity = classCapacity;
        this.teacherId = teacherId;
    }

    public String getClassDate() {
        return classDate;
    }

    public String getClassName() {
        return className;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public String getClassCapacity() {
        return classCapacity;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getClassId() {
        return classId;
    }
    public String toString() {
        return "ClassInfo{" +
                "classDate='" + classDate + '\'' +
                ", className='" + className + '\'' +
                ", currentEnrollment=" + currentEnrollment +
                ", classCapacity='" + classCapacity + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}
