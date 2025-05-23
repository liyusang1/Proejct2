package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class IntroductionLengthExceededException extends ApplicationException {
  private static final ErrorCode ERROR_CODE = ErrorCode.INTRODUCTION_TOO_LONG;

  public IntroductionLengthExceededException(){super(ERROR_CODE);}

}
