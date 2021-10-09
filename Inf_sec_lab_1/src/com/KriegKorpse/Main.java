package com.KriegKorpse;

public class Main {
    public static void main(String[] args) {
        //String text = "В Лысых Горах, имении князя Николая Андреевича Болконского, ожидали с каждым днем приезда молодого князя Андрея с княгиней; но ожидание не нарушило стройного порядка, по которому шла жизнь в доме старого князя.";
        File_manager file_manager = new File_manager();
        String text = "Text.txt";
        text = file_manager.read(text);
    	Caesar caesar = new Caesar();
        String cryptedText = caesar.Crypt_text(text, 7);
        String decryptedText = caesar.Decrypt_text(cryptedText, 7);

        System.out.println("Original:  " + text);
        System.out.println("Crypted:   " + cryptedText);
        System.out.println("Decrypted: " + decryptedText);
        file_manager.write(text, "Printed_text.txt", false, "Original");
        file_manager.write(cryptedText, "Printed_text.txt", true, "Crypted");
        file_manager.write(cryptedText, "Crypted_text.txt", false);
        file_manager.write(decryptedText, "Printed_text.txt", true, "Decrypted");

        String Crypted_line = file_manager.read_line("Crypted_text.txt");
        caesar.Brute_force_decrypting(Crypted_line);

    }
}

public class Main1 {

    final int q = 3;
    final int p = 14;

    public static void main(String[] args) {
        //String text = "В Лысых Горах, имении князя Николая Андреевича Болконского, ожидали с каждым днем приезда молодого князя Андрея с княгиней; но ожидание не нарушило стройного порядка, по которому шла жизнь в доме старого князя.";
        File_manager file_manager = new File_manager();


        // Сторона A
        int A_ = 0; // ...
        // Сторона B
        int B_ = 0; // ...

        // Сторона A
        /*private*/ int Ka = 0; // на основе B_...
        String text = "Text.txt";
        text = file_manager.read(text);
        Caesar caesar = new Caesar();
        String cryptedText = caesar.Crypt_text(text, Ka);

        // Сторона B
        /*private*/ int Kb = 0; // на основе A_...
        String decryptedText = caesar.Decrypt_text(cryptedText, Kb);

        System.out.println("Original:  " + text);
        System.out.println("Crypted:   " + cryptedText);
        System.out.println("Decrypted: " + decryptedText);
        file_manager.write(text, "Printed_text.txt", false, "Original");
        file_manager.write(cryptedText, "Printed_text.txt", true, "Crypted");
        file_manager.write(cryptedText, "Crypted_text.txt", false);
        file_manager.write(decryptedText, "Printed_text.txt", true, "Decrypted");

        String Crypted_line = file_manager.read_line("Crypted_text.txt");
        caesar.Brute_force_decrypting(Crypted_line);

    }
}

class Storona {
    private int prChislo = GeneratePrChislo();
    public int V_ = q^prChislo % p;

    private int K(int V__) { return V__^prChislo % p; };

    String Crypt(int V__) {
        String text = "Text.txt";
        text = file_manager.read(text);
        Caesar caesar = new Caesar();
        String cryptedText = caesar.Crypt_text(text, K(V__));
        return cryptedText;
    };

    String DeCrypt(int V__, String cryptedText) {
        Caesar caesar = new Caesar();
        String decryptedText = caesar.Decrypt_text(cryptedText, K(V__));
        return decryptedText;
    };
}

ublic class Main1 {

    final int q = 3;
    final int p = 14;

    public static void main(String[] args) {
        //String text = "В Лысых Горах, имении князя Николая Андреевича Болконского, ожидали с каждым днем приезда молодого князя Андрея с княгиней; но ожидание не нарушило стройного порядка, по которому шла жизнь в доме старого князя.";
        File_manager file_manager = new File_manager();

        Storona A = new Storona();
        Storona B = new Storona();

        Storona C = new Storona();

        String cryptedText = A.Crypt(B.V_);
        String decryptedText = B.DeCrypt(A.V_, cryptedText);

        String decryptedText1 = C.DeCrypt(A.V_, cryptedText);



    }
}