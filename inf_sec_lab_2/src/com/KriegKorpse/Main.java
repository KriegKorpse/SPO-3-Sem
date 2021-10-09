package com.KriegKorpse;

import java.math.BigDecimal;
import java.util.ArrayList;


class User {

    private int prime_number;
    public int  V_;
    private int q, p, Key;

    public User(int q, int p) {
        this.q = q;
        this.p =p;
        ArrayList<Integer> Prime_arr = new ArrayList<Integer>();
        Prime_number_generator Prime_number = new Prime_number_generator();
        int size = 100000;
        Prime_number.Generate_prime_array(size, Prime_arr);
        prime_number = Prime_number.Generate_prime_number(Prime_arr);

        BigDecimal bq = BigDecimal.valueOf(q);
        BigDecimal L = bq.pow(prime_number);

        V_ = L.remainder(BigDecimal.valueOf(p)).intValue();
    }

    private int K(int V__) {
        BigDecimal bV__ = BigDecimal.valueOf(V__);
        BigDecimal L = bV__.pow(prime_number);
        Key =L.remainder(BigDecimal.valueOf(p)).intValue();;
        System.out.println();
        return Key;

    };

    String Crypt(int V__, String text) {
        //String text = "Text.txt";

        Caesar caesar = new Caesar();
        //int key = K(V__) % 33;
        int key = K(V__);
        String cryptedText = caesar.Crypt_text(text, key);
        return cryptedText;
    };

    String DeCrypt(int V__, String cryptedText) {
        Caesar caesar = new Caesar();
        int key = K(V__) % 33; //p
        String decryptedText = caesar.Decrypt_text(cryptedText, key);
        return decryptedText;
    };
};

public class Main {

    final static int q = 3;
    final static int p = 1400;

    public static void main(String[] args) {
        //String text = "В Лысых Горах, имении князя Николая Андреевича Болконского, ожидали с каждым днем приезда молодого князя Андрея с княгиней; но ожидание не нарушило стройного порядка, по которому шла жизнь в доме старого князя.";
        File_manager file_manager = new File_manager();
        String text = file_manager.read("Text.txt");

        User A = new User(q, p);
        User B = new User(q, p);

        User C = new User(q, p);


        String cryptedText = A.Crypt(B.V_, text);

        String decryptedText_B = B.DeCrypt(A.V_, cryptedText);
        String decryptedText_A = A.DeCrypt(B.V_, cryptedText);

        String decryptedText_C = C.DeCrypt(A.V_, cryptedText);



    }
}