package demo.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RestExceptionResource {
    String message;
}
