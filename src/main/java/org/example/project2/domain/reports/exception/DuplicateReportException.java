package org.example.project2.domain.reports.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class DuplicateReportException extends ApplicationException {

    private static final ErrorCode ERROR_CODE = ErrorCode.DUPLICATE_REPORT;

    public DuplicateReportException() {

        super(ERROR_CODE);
    }
}
