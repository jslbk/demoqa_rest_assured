package models;

import lombok.Data;
import models.support.SupportModel;

@Data
public class UserResponseModel {

    private UserDataModel data;
    private SupportModel support;

}