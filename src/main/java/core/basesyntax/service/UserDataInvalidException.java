package core.basesyntax.service;

public class UserDataInvalidException extends RuntimeException {
  public UserDataInvalidException(String message) {
    super(message);
  }
}
