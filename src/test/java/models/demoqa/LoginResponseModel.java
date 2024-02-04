package models.demoqa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseModel {

    String userId;

    String username;

    String password;

    String token;

    String expires;

    @JsonProperty("created_date")
    String createdDate;

    String isActive;
}
