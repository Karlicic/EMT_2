package emt.finki.ukim.mk.demo.model.dto;

import emt.finki.ukim.mk.demo.model.Author;
import emt.finki.ukim.mk.demo.model.enumerations.Category;
import lombok.Data;

@Data
public class BookDto {

    private Long id;

    private String name;

    private Category category;

    private Long authorId;

    private Integer availableCopies;

    public BookDto() {}

    public BookDto(String name, Category category, Long authorId, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.authorId = authorId;
        this.availableCopies = availableCopies;
    }

}
