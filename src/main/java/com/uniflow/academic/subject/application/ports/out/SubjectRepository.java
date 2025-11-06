package com.uniflow.academic.subject.application.ports.out;

import com.uniflow.academic.subject.application.ports.out.dto.SubjectFilter;
import com.uniflow.academic.subject.application.ports.out.dto.SubjectStatisticsResponse;
import com.uniflow.academic.subject.domain.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository {

    Subject save(Subject subject);

    Subject update(Subject subject);

    Optional<Subject> findById(String subjectId, String studentId);

    List<Subject> findAll(String studentId, SubjectFilter filter);

    List<Subject> findByPeriodId(String periodId, String studentId);

    boolean existsByCode(String code, String periodId, String studentId, String excludeSubjectId);

    void delete(String subjectId, String studentId);

    boolean hasAssociatedTasks(String subjectId);

    SubjectStatisticsResponse getStatistics(String studentId);
}
