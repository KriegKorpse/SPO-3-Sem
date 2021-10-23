package com.KriegKorpse;

import java.math.BigInteger;
import java.util.ArrayList;

class User {
    private int p, q, m, fi, e, d;
    private String name;
    // 										   { 0 ,  1 ,  2 ,  3 ,  4 ,  5 ,  6 ,  7 ,  8 ,  9 ,  10 , 11,  12 , 13 , 14 , 15 , 16 , 17 , 18 , 19 , 20 , 21 , 22 , 23 , 24 , 25 , 26 , 27 , 28 , 29 , 30 , 31 , 32 , 33 ,
    private static final char alphabet[] =     {'а' ,'A' ,'б' ,'Б' ,'в' ,'В' ,'г' ,'Г' ,'д' ,'Д' ,'е' ,'Е' ,'ё' ,'Ё' ,'ж' ,'Ж' ,'з' ,'З' ,'и' ,'И' ,'й' ,'Й' ,'к' ,'К' ,'л' ,'Л' ,'м' ,'М' ,'н' ,'Н' ,'о' ,'О' ,'п' ,'П' ,

            //				  					 34, 35 , 36 , 37 , 38 , 39 , 40 , 41 , 42 , 43 , 44 , 45 , 46 , 47 , 48 , 49 , 50 , 51 , 52 , 53 , 54 , 55 , 56 , 57 , 58 , 59 , 60 , 61 , 62 , 63 , 64 , 65 , 66};
                                                'р','Р' ,'с' ,'С' ,'т' ,'Т' ,'у' ,'У' ,'ф' ,'Ф' ,'х' ,'Х' ,'ц' ,'Ц' ,'ч' ,'Ч' ,'ш' ,'Ш' ,'щ' ,'Щ' ,'ъ' ,'ы' ,'Ы' ,'ь' ,'э' ,'Э' ,'ю' ,'Ю' ,'я' ,'Я' ,' ' ,',' ,'.'};

    public User(String _name) {
        p = 0;
        q = 0;
        m = 0;
        fi = 0;
        e = 0;
        d = 0;
        name = _name;
    }

    public void Generate_User_Parms(int digits){
        Prime_number_generator gen = new Prime_number_generator();
        ArrayList<Integer> Prime_arr_d = new ArrayList();
        gen.Generate_prime_array_of_digit(digits, Prime_arr_d);
        p = gen.Generate_prime_number(Prime_arr_d);
        boolean flag = false;
        while(!flag){
            q = gen.Generate_prime_number(Prime_arr_d);
            if(q != p){
                flag = true;
            }
        }

        m = p*q;

        fi = (p - 1) * (q - 1);

        ArrayList<Integer> Prime_arr_s = new ArrayList<Integer>();
        gen.Generate_prime_array_of_size(fi, Prime_arr_s);
        e = gen.Generate_prime_number(Prime_arr_s);


        //OpenKey open_key = new OpenKey(e, m);

        d_Num_generator d_num = new d_Num_generator();
        while(d_Num_generator.GCD(e,m) != 1) {
            e = gen.Generate_prime_number(Prime_arr_s);
        }
        OpenKey open_key = new OpenKey(e, m);

        d = d_num.Generate_d_evklid(e, fi);
        System.out.println("------------------------------------------------------");
        System.out.println("User " + name);
        System.out.println("p = " + p + "\t" + "q = " + q);
        System.out.println("m = " + m + "\t" + "fi = " + fi);
        System.out.println("e = " + e + "\t" + "d = " + d);
        System.out.println("------------------------------------------------------");

    }

    ArrayList<Integer> Crypt(OpenKey otherKey, String text) {
        ArrayList<Integer> num_text = new ArrayList();
        for(int i = 0; i < text.length(); i++){
            boolean flag = false;
            int j = 0;
            while((!flag) && (j < alphabet.length)) {
                if(text.charAt(i) == alphabet[j]) {
                    num_text.add(j);
                    flag = true;
                }
                j++;
            }
        }
        System.out.println("Original_num_text = " + num_text);

        ArrayList<Integer> crypt_text = new ArrayList();
        for(int i = 0; i < num_text.size(); i++) {
            BigInteger ch_num = BigInteger.valueOf(num_text.get(i));
            BigInteger bm = BigInteger.valueOf(otherKey.m);
            int res = ch_num.pow(otherKey.e).mod(bm).intValue();
            crypt_text.add(res);
        }
        return crypt_text;
    };

    String DeCrypt(ArrayList<Integer> crypt_text) {
        ArrayList<Integer> encrypt_text = new ArrayList();
        for(int i = 0; i < crypt_text.size(); i++) {
            BigInteger ch_num = BigInteger.valueOf(crypt_text.get(i));
            BigInteger bm = BigInteger.valueOf(m);
            int res = ch_num.pow(d).mod(bm).intValue();
            encrypt_text.add(res);
        }
        System.out.println("Encrypted num_text: " + encrypt_text);

        String text = new String();
        for(int i = 0; i < encrypt_text.size(); i++){
            text += alphabet[encrypt_text.get(i)];
        }
        return text;
    };

    OpenKey GetOpenKey() {
        return new OpenKey(e, m);
    }
};

class OpenKey {
    public int e = 0;
    public int m = 0;

    OpenKey(int e, int m){
        this.e = e;
        this.m = m;
    }
}

