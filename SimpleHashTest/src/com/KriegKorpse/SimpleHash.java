package com.KriegKorpse;

import java.nio.charset.Charset;
import java.util.ArrayList;

class HashUtils {
   
   public static ArrayList<Integer> ToIntegerArr(ArrayList<String> parts4ch) {
      // Дополняем до количества символов, кратного 4
      String last_part = parts4ch.get(parts4ch.size()-1);
      int length = last_part.length();
      if(length < 4) {
         parts4ch.remove(parts4ch.size()-1);
         while(length != 4) {
            last_part += "0";
            length++;
         }
         parts4ch.add(last_part);
      }
      
      // Превращаем в int по кодам ASCII
      ArrayList<Integer> arr = new ArrayList<>();
      for(int i = 0; i < parts4ch.size(); i++) {
         String part = parts4ch.get(i);
         byte[] b = Charset.forName("cp1251").encode(part).array();
         int m = 0;
         for(int j = 0; j < b.length; j++) {
            int m0 = ((int)b[j] & 0xFF);
            m = m << 8;
            m = m | m0;
         }
         arr.add(m);
      }
      return arr;
   }
   
   public static int TransformOver3ss(int val) {
      long val3ss = 0;
      long pow3 = 1; // три в степени 0
      for(int i = 0; i < 16; i++) {
         if(1 == (val & 0x0001))
            val3ss += pow3;
         val = val >> 1;
         pow3 *= 3; 
      }
      return UInt.cast_uint(val3ss);
   }
   
   
/*   
   public static ArrayList<String> CompleteLastChunk(ArrayList<String> chunks, int chunk_len) {
      String last_chunk = chunks.get(chunks.size()-1);
      if(last_chunk.length() < chunk_len) {
         chunks.remove(chunks.size()-1);
         last_chunk += "0";
         chunks.add(last_chunk);
      }
      else
         chunks.add("00");
      return chunks;
   }
*/   
}

public class SimpleHash {

   public static int HashSumMod2(String word) {
      ArrayList<String> parts4ch = TextConvertor.ToFixedParts(word, 4);
      ArrayList<Integer> arr = HashUtils.ToIntegerArr(parts4ch);

      int result = 0;
      for(int i = 0; i < arr.size(); i++) {
         result ^= arr.get(i);
      }
      return result;
   }
   
   public static int HashSum(String word) {
      ArrayList<String> parts4ch = TextConvertor.ToFixedParts(word, 4);
      ArrayList<Integer> arr = HashUtils.ToIntegerArr(parts4ch);

      int result = 0;
      for(int i = 0; i < arr.size(); i++) {
         result += arr.get(i);
      }
      return result;
   }

   public static int HashModMSum(String word, int M) {
      ArrayList<String> parts4ch = TextConvertor.ToFixedParts(word, 4);
      ArrayList<Integer> arr = HashUtils.ToIntegerArr(parts4ch);

      int result = 0;
      for(int i = 0; i < arr.size(); i++) {
         result += UInt.mod(arr.get(i), M);
      }
      return result;
   }
   
   public static int HashSumTrans3ss(String word) {
      return HashUtils.TransformOver3ss(HashSum(word));
   }
   
   public static int Hash_0(String word, int M) {
      ArrayList<String> parts4ch = TextConvertor.ToFixedParts(word, 4);
      ArrayList<Integer> arr = HashUtils.ToIntegerArr(parts4ch);
      
      long size = arr.size();
      int size0 = UInt.cast_uint(size);
      int size1 = UInt.cast_uint(size << 16);
      
      arr.add(0x80000000);
      arr.add(size0);
      arr.add(size1);
      
      int result = 0;
      for(int i = 0; i < arr.size(); i++) {
         result += UInt.mod(arr.get(i), M);
      }
      return result;
   }
}
