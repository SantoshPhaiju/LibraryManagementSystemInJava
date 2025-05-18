package utils;

import java.util.Random;

public class BookIdGenerator {
    public static String generate(String bookName) {
        if (bookName.length() < 3) {
            throw new IllegalArgumentException("Book name must be at least 3 characters long.");
        }

        String prefix = bookName.substring(0, 3).toUpperCase();
        int randomNumber = 1000 + new Random().nextInt(9000);

        return prefix + randomNumber;
    }
}
