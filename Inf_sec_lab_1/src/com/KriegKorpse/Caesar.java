package com.KriegKorpse;

import java.util.Locale;

public class Caesar {
    private final char alphabet_low[] = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    private final char alphabet_high[] = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};

    private char Crypt(char x, int key) {
        boolean state = false;
        for (int i = 0; i < alphabet_low.length; i++) {
            if (x == alphabet_low[i]) {
                return (alphabet_low[(i + key) % alphabet_low.length]);
            }
            if (x == alphabet_high[i]) {
                return (alphabet_high[(i + key) % alphabet_high.length]);
            }
        }
        return x;
    }

    ;

    private char Decrypt(char y, int key) {
        boolean state = false;
        for (int i = 0; i < alphabet_low.length; i++) {
            if (y == alphabet_low[i]) {
                if (i - key < 0) {
                    return (alphabet_low[(alphabet_low.length + (i - key))]);
                }
                return (alphabet_low[(i - key) % alphabet_low.length]);
            }
            if (y == alphabet_high[i]) {
                if (i - key < 0) {
                    return (alphabet_high[(alphabet_high.length + (i - key))]);
                }
                return (alphabet_high[(i - key) % alphabet_high.length]);
            }
        }
        return y;
    }

    ;

    public String Crypt_text(String text, int key) {
        char y = 0;
        String res = "";

        for (int i = 0; i < text.length(); i++) {
            char x = text.charAt(i);
            y = Crypt(x, key);
            res += y;
        }

        return res;
    }

    public String Decrypt_text(String text, int key) {
        char x = 0;
        String res = "";

        for (int i = 0; i < text.length(); i++) {
            char y = text.charAt(i);
            x = Decrypt(y, key);
            res += x;
        }

        return res;
    }

    public void Brute_force_decrypting(String text) {
        char x = 0;
        File_manager file_manager = new File_manager();
        System.out.println("\nPossible variants:\n");
        boolean flag = false;
        for(int key = 0; key < alphabet_low.length; key++) {
            String res = "";
            for (int i = 0; i < text.length(); i++) {
                char y = text.charAt(i);
                x = Decrypt(y, key);
                res += x;
            }
            String key_name = "key= " + key + " ";
            file_manager.write(key_name, "Possible_encryptions.txt", flag);
            file_manager.write("  " + res + "\n", "Possible_encryptions.txt", true);
            if(!flag) {
                flag = true;
            }
            System.out.println(key_name + ": " + res + "\n");
        }
    }
}


