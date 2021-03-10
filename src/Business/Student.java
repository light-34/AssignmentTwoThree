package Business;

public class Student {
    //private int studentId;
    private String studentId;
    private Object program;
    private int semester;
    private String courses;

    /*
    public Student(int studentId, Object program, int semester, String courses) {
        this.studentId = studentId;
        this.program = program;
        this.semester = semester;
        this.courses = courses;
    }
    */
    
    public Student(String studentId, Object program, int semester, String courses) {
        this.studentId = studentId;
        this.program = program;
        this.semester = semester;
        this.courses = courses;
    }
    
    public Student() {	} //for use with buttons *Tim

    public String getStudentId() { // changed to string
        return studentId;
    }

    public void setStudentId(String studentId) { //changed to string 
        this.studentId = studentId;
    }

    public Object getProgram() {
        return program;
    }

    public void setProgram(Object program) {
        this.program = program;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }
}
