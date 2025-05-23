package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class EmailNotMatchException extends ApplicationException {
  private static final ErrorCode ERROR_CODE = ErrorCode.EMAIL_NOT_MATCH;

  public EmailNotMatchException(){super(ERROR_CODE);}
}
