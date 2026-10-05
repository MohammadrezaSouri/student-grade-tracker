package service;

import model.Grade;
import java.util.*;
import java.util.stream.Collectors;

public class GradeService {

    public Map<Integer, Double> averageScorePerStudent (List<Grade> grades) {
        return grades.stream()
                .collect(Collectors.groupingBy(Grade::getStudentId,
                        Collectors.averagingDouble(Grade::getScore)));
    }

    public Map<Integer, Optional<Grade>> topGradeByStudentId (List<Grade> grades) {
        return grades.stream()
                .collect(Collectors.groupingBy(
                        Grade::getCourseId,
                        Collectors.maxBy((g1, g2) ->
                                Double.compare(g1.getScore(), g2.getScore()))
                ));
    }

    public List<Grade> getFailedGrades(List<Grade> grades) {
        return grades.stream()
                .filter(grade -> grade.getScore() < 10)
                .collect(Collectors.toList());
    }

    public DoubleSummaryStatistics overallStatistics (List<Grade> grades) {
        return grades.stream()
                .collect(Collectors.summarizingDouble(Grade::getScore));
    }
}
