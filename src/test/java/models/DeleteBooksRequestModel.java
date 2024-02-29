package models;

import lombok.Data;

@Data
public class DeleteBooksRequestModel {

    private String isbn, userId;

}
