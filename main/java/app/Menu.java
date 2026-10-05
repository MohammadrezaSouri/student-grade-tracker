package app;

import model.Course;
import model.Grade;
import model.Student;
import persistence.CourseDAO;
import persistence.GradeDAO;
import persistence.StudentDAO;
import report.JsonExporter;
import service.GradeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Menu {
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
    private final GradeDAO gradeDAO;
    private final GradeService gradeService = new GradeService();

    public Menu(StudentDAO studentDAO, CourseDAO courseDAO, GradeDAO gradeDAO) {
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.gradeDAO = gradeDAO;
    }

    public void start() {
        while (true) {
            IO.println("\n=== GRADE TRACKER ===");
            IO.println("1. Add Student");
            IO.println("2. Add Course");
            IO.println("3. Add Grade");
            IO.println("4. Average Score Per Student");
            IO.println("5. Failed Grades");
            IO.println("6. Top Grade Per Course");
            IO.println("7. Overall Statistics");
            IO.println("8. Export to JSON");
            IO.println("9. Exit");
            IO.println("Choose: ");

            switch (IO.readln()) {
                case "1" -> handleAddStudent();
                case "2" -> handleAddCourse();
                case "3" -> handleAddGrade();
                case "4" -> handleAverageScore();
                case "5" -> handleFailedGrades();
                case "6" -> handleTopGrade();
                case "7" -> handleStatistics();
                case "8" -> handleExport();
                case "9" -> { return; }
                default -> IO.println("Invalid option.");
            }
        }
    }

    private void handleAddStudent() {
        IO.println("First Name: ");
        String firstName = IO.readln();
        IO.println("Last Name: ");
        String lastName = IO.readln();
        IO.println("Student Number: ");
        String studentNumber = IO.readln();

        Student student = new Student(0, firstName, lastName, studentNumber);
        try {
            studentDAO.save(student);
            IO.println("Student added.");
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private void handleAddCourse() {
        IO.println("Course Name: ");
        String name = IO.readln();
        IO.println("Units: ");
        int unit = Integer.parseInt(IO.readln());

        Course course = new Course(0, unit, name);
        try {
            courseDAO.save(course);
            IO.println("Course added.");
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private void handleAddGrade() {
        IO.println("Student ID: ");
        int studentId = Integer.parseInt(IO.readln());
        IO.println("Course ID: ");
        int courseId = Integer.parseInt(IO.readln());
        IO.println("Score (0-20): ");
        double score = Double.parseDouble(IO.readln());
        IO.println("Semester: ");
        String semester = IO.readln();

        Grade grade = new Grade(0, studentId, courseId, score, semester);
        try {
            gradeDAO.save(grade);
            IO.println("Grade added.");
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private void handleAverageScore() {
        try {
            List<Grade> grades = gradeDAO.findAll();
            Map<Integer, Double> averages = gradeService.averageScorePerStudent(grades);
            averages.forEach((studentId, avg) ->
                    IO.println("Student " + studentId + ": " + avg));
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private void handleFailedGrades() {
        try {
            List<Grade> grades = gradeDAO.findAll();
            List<Grade> failed = gradeService.getFailedGrades(grades);
            if (failed.isEmpty()) {
                IO.println("No failed grades.");
                return;
            }
            failed.forEach(g -> IO.println("Student " + g.getStudentId() +
                    " | Course " + g.getCourseId() +
                    " | Score: " + g.getScore()));
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private void handleTopGrade() {
        try {
            List<Grade> grades = gradeDAO.findAll();
            Map<Integer, Optional<Grade>> topGrades = gradeService.topGradeByStudentId(grades);
            topGrades.forEach((courseId, gradeOpt) ->
                    gradeOpt.ifPresent(g -> IO.println("Course " + courseId +
                            " | Best Score: " + g.getScore() +
                            " | Student: " + g.getStudentId())));
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private void handleStatistics() {
        try {
            List<Grade> grades = gradeDAO.findAll();
            DoubleSummaryStatistics stats = gradeService.overallStatistics(grades);
            IO.println("Count: " + stats.getCount());
            IO.println("Average: " + stats.getAverage());
            IO.println("Max: " + stats.getMax());
            IO.println("Min: " + stats.getMin());
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private void handleExport() {
        try {
            List<Grade> grades = gradeDAO.findAll();
            JsonExporter.export(grades, "grades.json");
            IO.println("Exported to grades.json");
        } catch (SQLException | IOException e) {
            IO.println("Error: " + e.getMessage());
        }
    }
}
