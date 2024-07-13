import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

public class Main {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static AtomicInteger lenghtThreeCharacters = new AtomicInteger(0);
    public static AtomicInteger lenghtFourCharacters = new AtomicInteger(0);
    public static AtomicInteger lenghtFiveCharacters = new AtomicInteger(0);

    public static void main(String[] args) {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));


        }

        Thread thread1 = new Thread(() -> checkBeautifulWords(texts, 3));
        Thread thread2 = new Thread(() -> checkBeautifulWords(texts, 4));
        Thread thread3 = new Thread(() -> checkBeautifulWords(texts, 5));
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Красивых слов с длиной" + ANSI_YELLOW + " три" + ANSI_RESET + ": " + lenghtThreeCharacters.get() + " шт.");
        System.out.println("Красивых слов с длиной" + ANSI_GREEN + " четыре" + ANSI_RESET + ": " + lenghtFourCharacters.get() + " шт.");
        System.out.println("Красивых слов с длиной" + ANSI_PURPLE + " пять" + ANSI_RESET + ": " + lenghtFiveCharacters.get() + " шт.");


    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void checkBeautifulWords(String[] texts, int lenght) {
        for (String text : texts) {
            if (isBeautifulWord(text, lenght)) {
                switch (lenght) {
                    case 3:
                        lenghtThreeCharacters.incrementAndGet();
                        break;
                    case 4:
                        lenghtFourCharacters.incrementAndGet();
                    case 5:
                        lenghtFiveCharacters.incrementAndGet();
                        break;
                }
            }
        }
    }

    public static boolean isBeautifulWord(String text, int lenght) {
        if (text.length() != lenght) {
            return false;
        }

        if (text.equals(new StringBuilder(text).reverse().toString())) {
            return true;
        }
        char currentChar = text.charAt(0);
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) < currentChar) {
                return false;
            }
            currentChar = text.charAt(i);
        }
        return false;
    }
}


