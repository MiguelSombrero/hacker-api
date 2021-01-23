package com.hacker.api.domain.studies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AudioBook.class, name = "AUDIO"),
        @JsonSubTypes.Type(value = VisualBook.class, name = "PAPER"),
        @JsonSubTypes.Type(value = VisualBook.class, name = "EBOOK")
})
public abstract class Book extends Rateable {
    private String authors;

    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private BookType type;
}
