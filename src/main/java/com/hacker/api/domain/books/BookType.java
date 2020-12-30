package com.hacker.api.domain.books;

public enum BookType {
    AUDIO("Äänikirja"),
    PAPER("Paperiversio"),
    EBOOK("eBook");

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
        return null;
    }
}
