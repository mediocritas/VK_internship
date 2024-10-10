package api.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {
    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("error_msg")
    private String errorMsg;

    @JsonProperty("error_data")
    private Object errorData;

    public static ErrorMessage createExpectedError(int errorCode, String errorMsg) {
        return ErrorMessage.builder()
                .errorCode(errorCode)
                .errorMsg(errorMsg)
                .errorData(null)
                .build();
    }
}