package app;

import persistence.ConnectionPool;
import persistence.CourseDAO;
import persistence.GradeDAO;
import persistence.StudentDAO;

import java.sql.SQLException;

public class Main {
    static void main() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        StudentDAO studentDAO = new StudentDAO(pool);
        CourseDAO courseDAO = new CourseDAO(pool);
        GradeDAO gradeDAO = new GradeDAO(pool);
        new Menu(studentDAO, courseDAO, gradeDAO).start();
    }
}
