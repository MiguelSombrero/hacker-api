package com.hacker.api.domain.studies;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BookType {
    AUDIO("Äänikirja"),
    PAPER("Paperiversio"),
    EBOOK("eBook"),
    UNDEFINED("undefined");

    private String name;

    BookType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static BookType getBookTypeByTextValue(String text) {
        for (BookType type : BookType.values()) {
            if (type.name.equals(text)) {
                return type;
            }
        }
        return UNDEFINED;
    }
}
