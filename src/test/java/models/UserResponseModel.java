package models;

import lombok.Data;
import models.support.SupportModel;

@Data
public class UserResponseModel {

    private UserResponseDataModel data;
    private SupportModel support;

}