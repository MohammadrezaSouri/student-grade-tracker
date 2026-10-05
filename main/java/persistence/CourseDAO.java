package persistence;

import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
        private ConnectionPool pool ;
        public CourseDAO (ConnectionPool pool) {
            this.pool = pool;
        }
        public void save (Course course) throws SQLException {
            Connection connection = pool.getConnection();
            try {
                String sql = "INSERT INTO courses(name , unit) VALUES (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, course.getName());
                preparedStatement.setInt(2, course.getUnit());

                preparedStatement.executeUpdate();
            }finally {
                pool.releaseConnection(connection);
            }
        }
        public List<Course> findAll()throws SQLException {
            Connection connection = pool.getConnection();
            List<Course> courseList = new ArrayList<>();
            try {
                String sql = "SELECT * FROM courses";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setName(resultSet.getString("name"));
                    course.setUnit(resultSet.getInt("unit"));
                    courseList.add(course);
                }
            }finally {
                pool.releaseConnection(connection);
            }
            return courseList;
        }
}
