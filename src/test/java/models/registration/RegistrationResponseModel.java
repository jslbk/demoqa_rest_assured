package models.registration;

import lombok.Data;

@Data
public class RegistrationResponseModel {

    int id;
    private String token, error;

}
