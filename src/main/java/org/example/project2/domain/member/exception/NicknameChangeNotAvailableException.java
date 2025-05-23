package org.example.project2.domain.member.exception;

import org.example.project2.global.exception.ApplicationException;
import org.example.project2.global.exception.ErrorCode;

public class NicknameChangeNotAvailableException extends ApplicationException {

  private static ErrorCode ERROR_CODE = ErrorCode.NICKNAME_CHANGE_NOT_AVAILABLE;

  public NicknameChangeNotAvailableException(){
    super(ERROR_CODE);
  }

}
