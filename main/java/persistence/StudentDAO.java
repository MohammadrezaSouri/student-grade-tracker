package persistence;

import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    private ConnectionPool pool;
    public StudentDAO(ConnectionPool pool) {
        this.pool = pool;
    }
    public void save(Student student)throws SQLException {
        Connection connection = pool.getConnection();
        try {
            String sql = "INSERT INTO students (first_name, last_name, student_number) VALUES (?, ?, ?) ON CONFLICT (student_number) DO NOTHING";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,student.getFirstName());
            preparedStatement.setString(2,student.getLastName());
            preparedStatement.setString(3,student.getStudentNumber());

            preparedStatement.executeUpdate();
        }finally {
            pool.releaseConnection(connection);
        }
    }

    public Student findByStudentNumber(String studentNumber) throws SQLException {
        Connection conn = pool.getConnection();
        try {
            String sql = "SELECT * FROM students WHERE student_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setStudentNumber(rs.getString("student_number"));

                return student;
            }
            return null;
        } finally {
            pool.releaseConnection(conn);
        }
    }
}
