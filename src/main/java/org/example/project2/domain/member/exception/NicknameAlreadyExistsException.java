package org.example.project2.domain.member.exception;


import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class NicknameAlreadyExistsException extends ApplicationException {

  private static final ErrorCode ERROR_CODE = ErrorCode.NICKNAME_ALREADY_EXISTS;

  public NicknameAlreadyExistsException(){super(ERROR_CODE);}
}
