package com.uniflow.academic.subject.application.services;

import com.uniflow.academic.period.application.ports.out.PeriodRepository;
import com.uniflow.academic.subject.application.ports.in.ValidateSubjectCodeQuery;
import com.uniflow.academic.subject.application.ports.out.SubjectRepository;
import com.uniflow.academic.subject.application.ports.out.dto.ValidateSubjectCodeResponse;
import com.uniflow.academic.subject.domain.exception.InvalidSubjectException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ValidateSubjectCodeService implements ValidateSubjectCodeQuery {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Za-z]{2,4}-?\\d{3,4}$");

    private final SubjectRepository subjectRepository;
    private final PeriodRepository periodRepository;

    @Override
    public ValidateSubjectCodeResponse execute(
            String code,
            String periodId,
            String studentId,
            String subjectId
    ) {
        log.info("Validating code {} for period {} and student {}", code, periodId, studentId);

        if (code == null || code.isBlank()) {
            throw new InvalidSubjectException("Subject code is required");
        }
        if (periodId == null || periodId.isBlank()) {
            throw new InvalidSubjectException("Period is required");
        }

        if (!periodRepository.existsByIdAndStudentId(periodId, studentId)) {
            throw new InvalidSubjectException("Period does not belong to the authenticated student");
        }

        boolean matchesPattern = CODE_PATTERN.matcher(code).matches();
        boolean exists = subjectRepository.existsByCode(code, periodId, studentId, subjectId);

        String message;
        if (!matchesPattern) {
            message = "Subject code format is invalid";
        } else if (exists) {
            message = "Subject code format is valid but already exists in this period";
        } else {
            message = "Subject code is available";
        }

        return ValidateSubjectCodeResponse.builder()
                .isValid(matchesPattern)
                .isAvailable(matchesPattern && !exists)
                .message(message)
                .build();
    }
}
