package com.KriegKorpse;

import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        //String text = "Ныне присно и вовеки веков, да станет могучий пламенем на ветру, да станет слабый ветром над пламенем, именем хаоса, Ныне присно и Вовеки веков";
        String text1 = "Ныне присно и вовеки веков";
        String text2 = "да станет могучий пламенем на ветру";
        String text3 = "да станет слабый ветром над пламенем";
        String text4 = "именем хаоса";
        String text5 = "Ныне присно и вовеки веков";
        Hash_values h_v1 = SHA256_Hash_Function.hash_func(text1);
        Hash_values h_v2 = SHA256_Hash_Function.hash_func(text2);
        Hash_values h_v3 = SHA256_Hash_Function.hash_func(text3);
        Hash_values h_v4 = SHA256_Hash_Function.hash_func(text4);
        Hash_values h_v5 = SHA256_Hash_Function.hash_func(text5);

        int compare = h_v1.compare(h_v2);
        System.out.println("h_v1 vs h_v2: " + compare);
        compare = h_v1.compare(h_v3);
        System.out.println("h_v1 vs h_v3: " + compare);
        compare = h_v1.compare(h_v4);
        System.out.println("h_v1 vs h_v4: " + compare);
        compare = h_v1.compare(h_v5);
        System.out.println("h_v1 vs h_v5: " + compare);
        compare = h_v2.compare(h_v3);
        System.out.println("h_v2 vs h_v3: " + compare);

        System.out.println();
        System.out.println("h_v1");
        h_v1.print_hash_values();
        System.out.println("h_v2");
        h_v2.print_hash_values();
        System.out.println("h_v3");
        h_v3.print_hash_values();
        System.out.println("h_v4");
        h_v4.print_hash_values();
        System.out.println("h_v5");
        h_v5.print_hash_values();

        ArrayList<TableElem> table = new ArrayList<>();
        table.add(new TableElem(text1, h_v1));
        table.add(new TableElem(text2, h_v2));
        table.add(new TableElem(text3, h_v3));
        table.add(new TableElem(text4, h_v4));
        table.add(new TableElem(text5, h_v5));

        table.sort(TableElem.hashComparator);
        Printer.PrintTable(table);

    }
}

class TableElem {
    public final String word;
    public final Hash_values hash;

    TableElem(String word, Hash_values hash) {
        this.word = word;
        this.hash = hash;
    }

    static final Comparator<TableElem> hashComparator = new Comparator<TableElem>() {
        @Override
        public int compare(TableElem left, TableElem right) {
            return left.hash.compare(right.hash);
        }
    };
}

class Printer {

    public static void PrintStringArr(ArrayList<String> arr) {
        for (int i = 0; i < arr.size(); i++)
            System.out.println("\"" + arr.get(i) + "\"");
    }

    static void PrintTable(ArrayList<TableElem> table) {
        System.out.println();
        for (int i = 0; i < table.size(); i++) {
            TableElem elem = table.get(i);
            System.out.println(String.format("%s  \"%s\"", elem.hash.toString(), elem.word));
        }
    }
}


