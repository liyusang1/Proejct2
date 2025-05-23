package org.example.project2.domain.member.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class NewPasswordNotMatchException extends ApplicationException {
  private static final ErrorCode ERROR_CODE = ErrorCode.NEW_PASSWORD_NOT_MATCH;

  public NewPasswordNotMatchException(){super(ERROR_CODE);}

}
