package com.uniflow.academic.subject.infrastructure.persistence.postgres;

import com.uniflow.academic.subject.domain.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectEntityMapper {

    public SubjectEntity toEntity(Subject subject) {
        return SubjectEntity.builder()
                .id(subject.getId())
                .name(subject.getName())
                .code(subject.getCode())
                .professor(subject.getProfessor())
                .credits(subject.getCredits())
                .color(subject.getColor())
                .periodId(subject.getPeriodId())
                .studentId(subject.getStudentId())
                .description(subject.getDescription())
                .schedule(subject.getSchedule())
                .createdAt(subject.getCreatedAt())
                .updatedAt(subject.getUpdatedAt())
                .build();
    }

    public Subject toDomain(SubjectEntity entity) {
        return Subject.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .professor(entity.getProfessor())
                .credits(entity.getCredits())
                .color(entity.getColor())
                .periodId(entity.getPeriodId())
                .studentId(entity.getStudentId())
                .description(entity.getDescription())
                .schedule(entity.getSchedule())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
