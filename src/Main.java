import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

public class Main {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static AtomicInteger lengthThreeCharacters = new AtomicInteger(0);
    public static AtomicInteger lengthFourCharacters = new AtomicInteger(0);
    public static AtomicInteger lengthFiveCharacters = new AtomicInteger(0);

    public static void main(String[] args) {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));


        }

        Thread thread1 = new Thread(() -> checkBeautifulWordsLengthThree(texts));
        Thread thread2 = new Thread(() -> checkBeautifulWordsLengthFour(texts));
        Thread thread3 = new Thread(() -> checkBeautifulWordsLengthFive(texts));
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

        System.out.println("Красивых слов с длиной" + ANSI_YELLOW + " три" + ANSI_RESET + ": " + lengthThreeCharacters.get() + " шт.");
        System.out.println("Красивых слов с длиной" + ANSI_GREEN + " четыре" + ANSI_RESET + ": " + lengthFourCharacters.get() + " шт.");
        System.out.println("Красивых слов с длиной" + ANSI_PURPLE + " пять" + ANSI_RESET + ": " + lengthFiveCharacters.get() + " шт.");


    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

//    public static void checkBeautifulWords(String[] texts, int lenght) {
//        for (String text : texts) {
//            if (isBeautifulWord(text, lenght)) {
//                switch (lenght) {
//                    case 3:
//                        lenghtThreeCharacters.incrementAndGet();
//                        break;
//                    case 4:
//                        lenghtFourCharacters.incrementAndGet();
//                    case 5:
//                        lenghtFiveCharacters.incrementAndGet();
//                        break;
//                }
//            }
//        }
//    }

    public static void checkBeautifulWordsLengthThree(String[] texts) {
        for (String text : texts) {
            if (isBeautifulWord(text, 3)) {
                lengthThreeCharacters.incrementAndGet();
            }
        }
    }

    public static void checkBeautifulWordsLengthFour(String[] texts) {
        for (String text : texts) {
            if (isBeautifulWord(text, 4)) {
                lengthFourCharacters.incrementAndGet();
            }
        }
    }

    public static void checkBeautifulWordsLengthFive(String[] texts) {
        for (String text : texts) {
            if (isBeautifulWord(text, 5)) {
                lengthFiveCharacters.incrementAndGet();
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


