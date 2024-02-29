package models;

import lombok.Data;

import java.util.List;

@Data
public class AddBooksModel {

    String userId;

    List<IsbnModel> collectionOfIsbns;

}