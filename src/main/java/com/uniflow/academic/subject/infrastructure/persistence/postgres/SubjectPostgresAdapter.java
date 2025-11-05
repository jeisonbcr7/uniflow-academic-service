package com.uniflow.academic.subject.infrastructure.persistence.postgres;

import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.application.ports.out.dto.SubjectFilter;
import com.uniflow.academic.subject.application.ports.out.dto.SubjectStatisticsResponse;
import com.uniflow.academic.subject.domain.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubjectPostgresAdapter implements SubjectRepository {

    private final SubjectJpaRepository jpaRepository;
    private final SubjectEntityMapper mapper;

    @Override
    public Subject save(Subject subject) {
        log.debug("Saving subject {}", subject.getId());
        SubjectEntity entity = mapper.toEntity(subject);
        SubjectEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Subject update(Subject subject) {
        log.debug("Updating subject {}", subject.getId());
        SubjectEntity entity = mapper.toEntity(subject);
        SubjectEntity updated = jpaRepository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Optional<Subject> findById(String subjectId, String studentId) {
        return jpaRepository.findByIdAndStudentId(subjectId, studentId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Subject> findAll(String studentId, SubjectFilter filter) {
        String periodId = filter != null ? filter.getPeriodId() : null;
        String professor = filter != null ? filter.getProfessor() : null;
        Integer credits = filter != null ? filter.getCredits() : null;
        String search = filter != null ? filter.getSearch() : null;

        List<SubjectEntity> entities = jpaRepository.searchSubjects(
                studentId,
                normalize(periodId),
                normalize(professor),
                credits,
                normalize(search)
        );

        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subject> findByPeriodId(String periodId, String studentId) {
        return jpaRepository.findByStudentIdAndPeriodId(studentId, periodId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCode(String code, String periodId, String studentId, String excludeSubjectId) {
        if (excludeSubjectId != null) {
            return jpaRepository.existsByStudentIdAndPeriodIdAndCodeIgnoreCaseAndIdNot(
                    studentId,
                    periodId,
                    code,
                    excludeSubjectId
            );
        }
        return jpaRepository.existsByStudentIdAndPeriodIdAndCodeIgnoreCase(studentId, periodId, code);
    }

    @Override
    public void delete(String subjectId, String studentId) {
        jpaRepository.deleteById(subjectId);
    }

    @Override
    public boolean hasAssociatedTasks(String subjectId) {
        // Tasks module not implemented yet.
        return false;
    }

    @Override
    public SubjectStatisticsResponse getStatistics(String studentId) {
        List<SubjectEntity> entities = jpaRepository.findByStudentId(studentId);
        if (entities.isEmpty()) {
            return SubjectStatisticsResponse.builder()
                    .total(0)
                    .totalCredits(0)
                    .averageCredits(0.0)
                    .byProfessor(Collections.emptyMap())
                    .byCredits(Collections.emptyMap())
                    .byPeriod(Collections.emptyMap())
                    .build();
        }

        List<Subject> subjects = entities.stream()
                .map(mapper::toDomain)
                .toList();

        int total = subjects.size();
        int totalCredits = subjects.stream()
                .map(Subject::getCredits)
                .filter(credits -> credits != null)
                .mapToInt(Integer::intValue)
                .sum();

        double averageCredits = total == 0 ? 0.0 : (double) totalCredits / total;

        var byProfessor = subjects.stream()
                .collect(Collectors.groupingBy(
                        subject -> subject.getProfessor() != null ? subject.getProfessor() : "Unknown",
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        var byCredits = subjects.stream()
                .filter(subject -> subject.getCredits() != null)
                .collect(Collectors.groupingBy(
                        Subject::getCredits,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        var byPeriod = subjects.stream()
                .collect(Collectors.groupingBy(
                        Subject::getPeriodId,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        return SubjectStatisticsResponse.builder()
                .total(total)
                .totalCredits(totalCredits)
                .averageCredits(averageCredits)
                .byProfessor(byProfessor)
                .byCredits(byCredits)
                .byPeriod(byPeriod)
                .build();
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
