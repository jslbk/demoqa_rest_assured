package models.demoqa;

import lombok.Data;

@Data
public class DeleteBooksRequestModel {

    private String isbn;

    private String userId;

}
