package api.karbonzeromovil.Exceptions;

import lombok.Getter;

public class ExceptionDuplicateData extends RuntimeException {
  @Getter
  private String duplicateData;

  public ExceptionDuplicateData(String message) {
    super(message);
  }
  public ExceptionDuplicateData(String message, String duplicateData) {
    super(message);
    this.duplicateData = duplicateData;
  }


}
