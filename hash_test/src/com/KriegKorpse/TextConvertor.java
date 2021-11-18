package com.KriegKorpse;

import java.util.ArrayList;

class TextConvertor {

   Alphabet alphabet;

   public TextConvertor(Alphabet alphabet) {
      this.alphabet = alphabet;
   }


   public ArrayList<String> ToWords(String text) {
      final char delim = ' ';
      ArrayList<String> words = new ArrayList<>();
      String currentWord = "";
      for(int i = 0; i < text.length(); i++) {
         char ch = text.charAt(i);
         if(!alphabet.contain(ch))
            ch = delim;
         if(delim == ch) {
            if(!currentWord.isEmpty())
               words.add(currentWord);
            currentWord = "";
         }
         else
            currentWord += ch;
      }
      return words;
   }

   private String ReplaceUnknownCh(String str) {
      final char delim = ' ';
      String result = "";
      for(int i = 0; i < str.length(); i++) {
         char ch = str.charAt(i);
         if(!alphabet.contain(ch))
            ch = delim;
         result += ch;
      }
      return result;
   }
   
   public ArrayList<String> ToFixedParts(String str, int part_len) {
      ArrayList<String> parts = new ArrayList<>();
      int beg_ind = 0;
      int end_ind = beg_ind + part_len - 1;
      while(end_ind < str.length()-1) {
         parts.add(ReplaceUnknownCh(str.substring(beg_ind, end_ind + 1)));
         beg_ind = end_ind + 1;
         end_ind = beg_ind + part_len - 1;
      }
      parts.add(ReplaceUnknownCh(str.substring(beg_ind, str.length())));
      return parts;      
   }
  
}
