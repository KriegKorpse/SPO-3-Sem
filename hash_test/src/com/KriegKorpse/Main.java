package com.KriegKorpse;

import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) throws TxtFileException {
        Alphabet alphabet = new Alphabet();
        String text = TxtFile.Read("test_text.txt", "cp1251");
        TextConvertor txt_con = new TextConvertor(alphabet);
        ArrayList<String> texts = txt_con.ToWords(text);
        ArrayList<TableElem> table = new ArrayList<>();
        SHA256_Hash_Function SHA256 = new SHA256_Hash_Function(alphabet);
        for(int i = 0; i < texts.size(); i++){
            BigInteger hash = SHA256.hash_func(texts.get(i));
            table.add(new TableElem(texts.get(i), hash));
        }

        table.sort(TableElem.comparator);
        Printer.PrintTable(table);

        TableAnalyzer ta = new TableAnalyzer(table);
        System.out.println("Анализ коллизий ...");
        ArrayList<TableElem> tableUniqueWords = ta.GetUniqueWordsTable();

        System.out.println("Коллизии:");
        HashMap<String, ArrayList<String>> collisionsTable = ta.GetCollisionsTable();
        Printer.PrintCollisionsTable(collisionsTable);
        System.out.println("Всего: " + collisionsTable.size() + " из " + tableUniqueWords.size());
        System.out.println("Используется hash-значений: " + ta.GetUniqueHashCount());
    }
}

class TableElem {
    public final String word;
    public final BigInteger hash;

    TableElem(String word, BigInteger hash) {
        this.word = word;
        this.hash = hash;
    }

    static final Comparator<TableElem> hashComparator = new Comparator<TableElem>() {
        @Override
        public int compare(TableElem left, TableElem right) {
            return left.hash.compareTo(right.hash);
        }
    };

    static final Comparator<TableElem> wordComparator = new Comparator<TableElem>() {
        @Override
        public int compare(TableElem left, TableElem right) {
            // Сортировка по алфавиту
            return left.word.compareTo(right.word);
        }
    };

    static final Comparator<TableElem> comparator = new Comparator<TableElem>() {
        @Override
        public int compare(TableElem left, TableElem right) {
            // Сортировка hash + word
            int res = hashComparator.compare(left, right);
            if(0 == res)
                res = wordComparator.compare(left, right);
            return res;
        }
    };

}

class TableAnalyzer {
    ArrayList<TableElem> table;

    public TableAnalyzer(ArrayList<TableElem> table) {
        this.table = (ArrayList<TableElem>) table.clone();
    }

    public HashMap<String, ArrayList<String>> GetCollisionsTable() {
        table.sort(TableElem.comparator);

        HashMap<String, ArrayList<String>> collisions = new HashMap<>();
        ArrayList<String> collisedWords = new ArrayList<>();
        TableElem lastElem = table.get(0);
        for(int i = 1; i < table.size(); i++) {
            TableElem elem = table.get(i);

            if (lastElem.hash == elem.hash) {
                if(!lastElem.word.equals(elem.word)) {
                    if(collisedWords.isEmpty())
                        collisedWords.add(lastElem.word);
                    if(!collisedWords.contains(elem.word))
                        collisedWords.add(elem.word);
                }
            }
            else {
                if(!collisedWords.isEmpty())
                    collisions.put(lastElem.hash.toString(), collisedWords);
                collisedWords = new ArrayList<>();
            }
            lastElem = elem;
        }
        if(!collisedWords.isEmpty())
            collisions.put(lastElem.hash.toString(), collisedWords);

        return collisions;
    }

    public ArrayList<TableElem> GetUniqueWordsTable() {
        table.sort(TableElem.wordComparator);
        ArrayList<TableElem> result = new ArrayList<>();
        TableElem elem = table.get(0);
        result.add(elem);
        String lastWord = elem.word;
        for(int i = 1; i < table.size(); i++) {
            elem = table.get(i);
            String word = elem.word;
            if(!lastWord.equals(word))
                result.add(elem);
            lastWord = word;
        }
        return result;
    }

    public int GetUniqueHashCount() {
        table.sort(TableElem.hashComparator);
        TableElem elem = table.get(0);
        int count = 1;
        BigInteger lastHash = elem.hash;
        for(int i = 1; i < table.size(); i++) {
            elem = table.get(i);
            if(0 != lastHash.compareTo(elem.hash))
                count++;
            lastHash = elem.hash;
        }
        return count;
    }
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

    static void PrintCollisionsTable(HashMap<String, ArrayList<String>> collisions) {
        Iterator it = collisions.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry elem = (Map.Entry) it.next();
            String hash = (String) elem.getKey();

            ArrayList<String> collisedWords = (ArrayList<String>) elem.getValue();
            System.out.print(hash.toString() + "  ");
            for(int i = 0; i < collisedWords.size(); i++)
                System.out.print("\"" + collisedWords.get(i) + "\"" + " ");
            System.out.println();
        }
    }

}


