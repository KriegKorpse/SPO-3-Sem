package com.KriegKorpse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

   public static void main(String[] args) throws TxtFileException {

      System.out.println("Чтение и преобразование файла ...");
      Alphabet alph = new Alphabet();
      String text = TxtFile.Read("test_text_2.txt", "cp1251");
      ArrayList<String> words = TextConvertor.ToWords(text, alph);

      System.out.println("Расчет hash ...");
      // 49157 - простое число между 2^15 и 2^16
      short M16 = (short) 49157;
      // 3221225470 = 0xBFFFFFF5 - простое число между 2^31 и 2^32
      int M32 = 0xBFFFFFF5;

      ArrayList<TableElem> table = new ArrayList<>();
      for(int i = 0; i < words.size(); i++) {
         String word = words.get(i);

         //int hash = SimpleHash.HashSumMod2(word);
         //int hash = SimpleHash.HashSum(word);
         //int hash = SimpleHash.HashSumTrans3ss(word);
         int hash = SimpleHash.HashModMSum(word, M32);
         //int hash = SimpleHash.Hash_0(word, M32);

         //hash = Unsigned.mod(hash, M);

         table.add(new TableElem(word, hash));
      }

      TableAnalyzer ta = new TableAnalyzer(table);

      System.out.println("Анализ коллизий ...");
      ArrayList<TableElem> tableUniqueWords = ta.GetUniqueWordsTable();

      //tableUniqueWords.sort(TableElem.wordComparator);
      tableUniqueWords.sort(TableElem.hashComparator);
      Printer.PrintTable(tableUniqueWords);

      System.out.println("Коллизии:");
      HashMap<Integer, ArrayList<String>> collisionsTable = ta.GetCollisionsTable();
      Printer.PrintCollisionsTable(collisionsTable);
      System.out.println("Всего: " + collisionsTable.size() + " из " + tableUniqueWords.size());
      System.out.println("Используется hash-значений: " + ta.GetUniqueHashCount());

   }

}

class TableElem {
   public final String word;
   public final int hash;

   TableElem(String word, int hash) {
      this.word = word;
      this.hash = hash;
   }

   static final Comparator<TableElem> hashComparator = new Comparator<TableElem>() {
      @Override
      public int compare(TableElem left, TableElem right) {
         // Сортировка в порядке возрастания беззнакового числа
         return UInt.compare(left.hash, right.hash);
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

   public HashMap<Integer, ArrayList<String>> GetCollisionsTable() {
      table.sort(TableElem.comparator);

      HashMap<Integer, ArrayList<String>> collisions = new HashMap<>();
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
               collisions.put(lastElem.hash, collisedWords);
            collisedWords = new ArrayList<>();
         }
         lastElem = elem;
      }
      if(!collisedWords.isEmpty())
         collisions.put(lastElem.hash, collisedWords);

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
      int lastHash = elem.hash;
      for(int i = 1; i < table.size(); i++) {
         elem = table.get(i);
         if(lastHash != elem.hash)
            count++;
         lastHash = elem.hash;
      }
      return count;
   }
}

class Printer {

   public static void PrintStringArr(ArrayList<String> arr) {
      for(int i = 0; i <arr.size(); i++)
         System.out.println("\"" + arr.get(i) + "\"");
   }

   public static void PrintShortArr(ArrayList<Short> arr) {
      for(int i = 0; i <arr.size(); i++)
         System.out.println(String.format("0x%04x", arr.get(i)));
   }

   public static void PrintIntegerArr(ArrayList<Integer> arr) {
      for(int i = 0; i <arr.size(); i++)
         System.out.println(String.format("0x%08x", arr.get(i)));
   }

   static void PrintTable(ArrayList<TableElem> table) {
      System.out.println();
      for(int i = 0; i < table.size(); i++) {
         TableElem elem = table.get(i);
         System.out.println(String.format("%08X  \"%s\"", elem.hash, elem.word));
      }
   }

   static void PrintCollisionsTable(HashMap<Integer, ArrayList<String>> collisions) {
      Iterator it = collisions.entrySet().iterator();
      while(it.hasNext()) {
         Map.Entry elem = (Map.Entry) it.next();
         int hash = (int) elem.getKey();

         ArrayList<String> collisedWords = (ArrayList<String>) elem.getValue();
         System.out.print(String.format("%08X", hash) + "  ");
         for(int i = 0; i < collisedWords.size(); i++)
            System.out.print("\"" + collisedWords.get(i) + "\"" + " ");
         System.out.println();
      }
   }
}
