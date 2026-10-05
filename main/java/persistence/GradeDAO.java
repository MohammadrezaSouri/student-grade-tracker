package persistence;

import model.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    private ConnectionPool pool;
    public  GradeDAO(ConnectionPool pool) {
        this.pool = pool;
    }
    public void save(Grade grade) throws SQLException {
        Connection connection = pool.getConnection();
        try {
            String sql = "INSERT INTO grades (student_id, course_id, score, semester) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,grade.getStudentId());
            preparedStatement.setInt(2, grade.getCourseId());
            preparedStatement.setDouble(3, grade.getScore());
            preparedStatement.setString(4, grade.getSemester());

            preparedStatement.executeUpdate();
        }finally {
            pool.releaseConnection(connection);
        }
    }

    public List<Grade> findAll() throws SQLException {
        Connection connection = pool.getConnection();
        List<Grade> grades = new ArrayList<>();
        try {
            String sql = "SELECT * FROM grades";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Grade grade = new Grade();
                grade.setId(resultSet.getInt("id"));
                grade.setStudentId(resultSet.getInt("student_id"));
                grade.setCourseId(resultSet.getInt("course_id"));
                grade.setScore(resultSet.getDouble("score"));
                grade.setSemester(resultSet.getString("semester"));
                grades.add(grade);
            }
        }finally {
            pool.releaseConnection(connection);
        }
        return grades;
    }
}
