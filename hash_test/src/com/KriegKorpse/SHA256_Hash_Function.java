package com.KriegKorpse;

import java.math.BigInteger;
import java.util.ArrayList;

class Chunk {
    String substr;
    ArrayList<Integer> int_chunk;

    public Chunk(String _substr, ArrayList<Integer> _int_chunk) {
        this.substr = _substr;
        this.int_chunk = _int_chunk;
    }

    void add_elem_Chunk(int elem){
        this.int_chunk.add(elem);
    }

    public void Print_chunk() {
        System.out.println("substr = " + "\"" + this.substr + "\"");
        System.out.println("int_chunk:");
        for (int i = 0; i < int_chunk.size(); i++) {
            String s = String.format("%08x ", int_chunk.get(i));
            System.out.print(s);
            if (0 == (i+1) % 2)
                System.out.println();
        }
    }
}

class Hash_values{
    ArrayList<Integer> h_v = new ArrayList();

    Hash_values(){
        this.h_v.add(0x6a09e667);
        this.h_v.add(0xbb67ae85);
        this.h_v.add(0x3c6ef372);
        this.h_v.add(0xa54ff53a);
        this.h_v.add(0x510e527f);
        this.h_v.add(0x9b05688c);
        this.h_v.add(0x1f83d9ab);
        this.h_v.add(0x5be0cd19);
    }

    void print_hash_values(){
        System.out.println("Hash values:");
        for(int i = 0; i < this.h_v.size(); i++){
            String s = String.format("%08x ", this.h_v.get(i));
            System.out.println("h" + i + " = " + s);
        }
        System.out.println();
    }

    int compare(Hash_values right) {
        Hash_values left = this;
        for(int i = 0; i < 8; i++) {
            int result = UInt.compare(left.h_v.get(i), right.h_v.get(i));
            if(0 != result)
                return result;
        }
        return 0;
    }

    public String toStringHex() {
        String result = "";
        for(int i = 0; i < this.h_v.size(); i++){
            result += String.format("%08x", this.h_v.get(i));
        }
        return result;
    }
}

class Hash_table{
    ArrayList<Integer> h_t = new ArrayList();

    Hash_table(){
        this.h_t.add(0x428a2f98); this.h_t.add(0x71374491); this.h_t.add(0xb5c0fbcf); this.h_t.add(0xe9b5dba5);
        this.h_t.add(0x3956c25b); this.h_t.add(0x59f111f1); this.h_t.add(0x923f82a4); this.h_t.add(0xab1c5ed5);
        this.h_t.add(0xd807aa98); this.h_t.add(0x12835b01); this.h_t.add(0x243185be); this.h_t.add(0x550c7dc3);
        this.h_t.add(0x72be5d74); this.h_t.add(0x80deb1fe); this.h_t.add(0x9bdc06a7); this.h_t.add(0xc19bf174);
        this.h_t.add(0xe49b69c1); this.h_t.add(0xefbe4786); this.h_t.add(0x0fc19dc6); this.h_t.add(0x240ca1cc);
        this.h_t.add(0x2de92c6f); this.h_t.add(0x4a7484aa); this.h_t.add(0x5cb0a9dc); this.h_t.add(0x76f988da);
        this.h_t.add(0x983e5152); this.h_t.add(0xa831c66d); this.h_t.add(0xb00327c8); this.h_t.add(0xbf597fc7);
        this.h_t.add(0xc6e00bf3); this.h_t.add(0xd5a79147); this.h_t.add(0x06ca6351); this.h_t.add(0x14292967);
        this.h_t.add(0x27b70a85); this.h_t.add(0x2e1b2138); this.h_t.add(0x4d2c6dfc); this.h_t.add(0x53380d13);
        this.h_t.add(0x650a7354); this.h_t.add(0x766a0abb); this.h_t.add(0x81c2c92e); this.h_t.add(0x92722c85);
        this.h_t.add(0xa2bfe8a1); this.h_t.add(0xa81a664b); this.h_t.add(0xc24b8b70); this.h_t.add(0xc76c51a3);
        this.h_t.add(0xd192e819); this.h_t.add(0xd6990624); this.h_t.add(0xf40e3585); this.h_t.add(0x106aa070);
        this.h_t.add(0x19a4c116); this.h_t.add(0x1e376c08); this.h_t.add(0x2748774c); this.h_t.add(0x34b0bcb5);
        this.h_t.add(0x391c0cb3); this.h_t.add(0x4ed8aa4a); this.h_t.add(0x5b9cca4f); this.h_t.add(0x682e6ff3);
        this.h_t.add(0x748f82ee); this.h_t.add(0x78a5636f); this.h_t.add(0x84c87814); this.h_t.add(0x8cc70208);
        this.h_t.add(0x90befffa); this.h_t.add(0xa4506ceb); this.h_t.add(0xbef9a3f7); this.h_t.add(0xc67178f2);
    }

    void add_in_Hash_table(int elem){
        this.h_t.add(elem);
    }

    void print_hash_values(){
        System.out.println("Hash table:");
        for(int i = 0; i < this.h_t.size(); i++){
            String s = String.format("%08x ", this.h_t.get(i));
            System.out.println("ht" + i + " = " + s);
        }
    }
}

class UInt {
    public static long cast_ulong(int a) {
        return (long)a & 0x00000000FFFFFFFFL;
    }

    public static int cast_uint(long a) {
        return (int) (a & 0x00000000FFFFFFFFL);
    }

    /*public static int mod(int a, int b) {
        long c = cast_ulong(a) % cast_ulong(b);
        return cast_uint(c);
    }*/

    public static int sum(int a, int b){
        long c = cast_ulong(a) + cast_ulong(b);
        return cast_uint(c);
    }

    /*public static int sum(int a, int b, int c){
        long d = cast_ulong(a) + cast_ulong(b) + cast_ulong(c);
        return cast_uint(d);
    }*/

    /*public static int sum(int a, int b, int c, int d){
        long e = cast_ulong(a) + cast_ulong(b) + cast_ulong(c) + cast_ulong(d);
        return cast_uint(e);
    }*/

    public static int sum(int a, int b, int c, int d, int e){
        long f = cast_ulong(a) + cast_ulong(b) + cast_ulong(c) + cast_ulong(d) + cast_ulong(e);
        return cast_uint(f);
    }

    public static int compare(int _a, int _b) {
        return Integer.compareUnsigned(_a, _b);
    }
}

public class SHA256_Hash_Function {

    Alphabet alphabet;

    public SHA256_Hash_Function(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    private String Byte2String(byte b) {
        String hex_str = String.format("%x", b);
        if (1 == hex_str.length())
            hex_str = "0" + hex_str;

        String bin_str = "";
        for (int i = 0; i < hex_str.length(); i++)
            switch (hex_str.charAt(i)) {
                case '0':
                    bin_str += "0000";
                    break;
                case '1':
                    bin_str += "0001";
                    break;
                case '2':
                    bin_str += "0010";
                    break;
                case '3':
                    bin_str += "0011";
                    break;
                case '4':
                    bin_str += "0100";
                    break;
                case '5':
                    bin_str += "0101";
                    break;
                case '6':
                    bin_str += "0110";
                    break;
                case '7':
                    bin_str += "0111";
                    break;
                case '8':
                    bin_str += "1000";
                    break;
                case '9':
                    bin_str += "1001";
                    break;
                case 'a':
                    bin_str += "1010";
                    break;
                case 'b':
                    bin_str += "1011";
                    break;
                case 'c':
                    bin_str += "1100";
                    break;
                case 'd':
                    bin_str += "1101";
                    break;
                case 'e':
                    bin_str += "1110";
                    break;
                case 'f':
                    bin_str += "1111";
                    break;
            }
        return bin_str;
    }

    private ArrayList<Byte> Int2ByteArr(int v) {
        ArrayList<Byte> reverse_arr = new ArrayList();
        for (int i = 0; i < 4; i++) {
            int rotated_v = (v >> (i * 8)) & 0x000000ff;
            reverse_arr.add(Integer.valueOf(rotated_v).byteValue());
        }
        return reverse_arr;
    }

    private int ByteConcat2Int(byte bt1, byte bt2, byte bt3, byte bt4) {
        int res = bt1;

        res = res << 8;
        int ibt2 = bt2 & 0xff;
        res = res | bt2;

        res = res << 8;
        int ibt3 = bt3 & 0xff;
        res = res | bt3;

        res = res << 8;
        int ibt4 = bt4 & 0xff;
        res = res | ibt4;

        return res;
    }

    private ArrayList<String> arr_54s(String txt) {
        ArrayList<String> arr_txt = new ArrayList();
        int beg_ind = 0;
        while (beg_ind < txt.length()) {
            int end_ind = 0;
            if ((txt.length() - beg_ind) < 54) {
                end_ind = txt.length() - 1;
            } else {
                end_ind = beg_ind + 53;
            }
            arr_txt.add(txt.substring(beg_ind, end_ind + 1));
            beg_ind = end_ind + 1;
        }
        return arr_txt;
    }

    public int rightrotate(int val, int count) {
        for (int i = count; i > 0; i--) {
            boolean is_one = 1 == (val & 0x00000001);
            val = val >> 1;
            if (is_one) {
                val = val | 0x80000000;
            } else {
                val = val & 0x7FFFFFFF;
            }
        }
        return val;
    }

    public BigInteger hash_func(String text) {
        Hash_values h_v = new Hash_values();
        Hash_table h_t = new Hash_table();
        ArrayList<Byte> arr = new ArrayList<>();
        ArrayList<String> arr_txt = arr_54s(text);
        ArrayList<Chunk> chunks = new ArrayList();

        int num = 0;
        while (chunks.size() != arr_txt.size()) {
            for (int i = 0; i < text.length(); i++) {
                int codeCh = alphabet.indexOf(text.charAt(i));
                if (-1 != codeCh)
                    arr.add(Integer.valueOf(codeCh).byteValue());
            }
            arr.add(Integer.valueOf(0x80).byteValue());
            while (arr.size() < 56) {
                arr.add(Integer.valueOf(0).byteValue());
            }

            //big endian
            for (int i = 0; i < 4; i++) {
                arr.add(Integer.valueOf(0).byteValue());
            }
            int end_value = text.length() * 8;
            ArrayList<Byte> arr_end_value = Int2ByteArr(end_value);
            for (int i = 3; i >= 0; i--) {
                arr.add(arr_end_value.get(i));
            }

            ArrayList<Integer> arr_32_bit = new ArrayList();
            for (int i = 0; i < 16; i++) {
                arr_32_bit.add(ByteConcat2Int(arr.get(i * 4), arr.get(i * 4 + 1), arr.get(i * 4 + 2), arr.get(i * 4 + 3)));
            }
            for (int i = 0; i < 48; i++) {
                arr_32_bit.add(0x00000000000000000000000000000000);
            }
            chunks.add(new Chunk(arr_txt.get(num), arr_32_bit));
            num++;

            for (int i = 16; i < 64; i++) {
                int f_s0 = rightrotate(arr_32_bit.get(i - 15), 7);
                int s_s0 = rightrotate(arr_32_bit.get(i - 15), 18);
                int t_s0 = rightrotate(arr_32_bit.get(i - 15), 3);
                int s0 = f_s0 ^ s_s0 ^ t_s0;

                int f_s1 = rightrotate(arr_32_bit.get(i - 2), 17);
                int s_s1 = rightrotate(arr_32_bit.get(i - 2), 11);
                int t_s1 = rightrotate(arr_32_bit.get(i - 2), 10);
                int s1 = f_s1 ^ s_s1 ^ t_s1;

                int curr = UInt.sum(arr_32_bit.get(i - 16), s0);
                curr = UInt.sum(curr, arr_32_bit.get(i - 7));
                curr = UInt.sum(curr, s1);
                arr_32_bit.set(i, curr);
            }

            int a = h_v.h_v.get(0);
            int b = h_v.h_v.get(1);
            int c = h_v.h_v.get(2);
            int d = h_v.h_v.get(3);
            int e = h_v.h_v.get(4);
            int f = h_v.h_v.get(5);
            int g = h_v.h_v.get(6);
            int h = h_v.h_v.get(7);

            for (int i = 0; i < 64; i++) {
                int s1 = rightrotate(e, 6) ^ rightrotate(e, 11) ^ rightrotate(e, 25);
                int ch = (e & f) ^ ((~e) & g);
                int temp1 = UInt.sum(h, s1, ch, h_t.h_t.get(i), arr_32_bit.get(i));
                int s0 = rightrotate(a, 2) ^ rightrotate(a, 13) ^ rightrotate(a, 22);
                int maj = (a & b) ^ (a & c) ^ (b & c);
                int temp2 = UInt.sum(s0, maj);
                h = g;
                g = f;
                f = e;
                e = UInt.sum(d, temp1);
                d = c;
                c = b;
                b = a;
                a = UInt.sum(temp1, temp2);
            }

            h_v.h_v.set(0, UInt.sum(h_v.h_v.get(0), a));
            h_v.h_v.set(1, UInt.sum(h_v.h_v.get(1), b));
            h_v.h_v.set(2, UInt.sum(h_v.h_v.get(2), c));
            h_v.h_v.set(3, UInt.sum(h_v.h_v.get(3), d));
            h_v.h_v.set(4, UInt.sum(h_v.h_v.get(4), e));
            h_v.h_v.set(5, UInt.sum(h_v.h_v.get(5), f));
            h_v.h_v.set(6, UInt.sum(h_v.h_v.get(6), g));
            h_v.h_v.set(7, UInt.sum(h_v.h_v.get(7), h));
        }

        return new BigInteger(h_v.toStringHex(), 16);
    }

}
