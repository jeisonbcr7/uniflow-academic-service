package com.uniflow.academic.subject.application.ports.in;

public interface DeleteSubjectCommand {

    void execute(String subjectId, String studentId);
}
