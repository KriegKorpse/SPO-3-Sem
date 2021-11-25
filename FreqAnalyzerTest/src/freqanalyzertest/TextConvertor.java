package freqanalyzertest;

import java.util.ArrayList;

class TextConvertor {

   public static ArrayList<String> ToWords(String text, Alphabet alph) {
      return ToWordsImpl(text, alph, false);
   }

   public static ArrayList<String> ToWordsAndDelims(String text, Alphabet alph) {
      return ToWordsImpl(text, alph, true);
   }
   
   public static boolean IsWord(String str, Alphabet alph) {
      if(str.isEmpty())
         return false;
      return alph.contain(str.charAt(0));
   }

   private static ArrayList<String> ToWordsImpl(String text, Alphabet alph, boolean addDelims) {
      ArrayList<String> arr = new ArrayList<>();
      if(text.isEmpty())
         return arr;

      String current = "";
      char ch = text.charAt(0);      
      boolean prevIsWord = alph.contain(ch);
      current += ch;
      for(int i = 1; i < text.length(); i++) {
         ch = text.charAt(i);
         boolean isWord = alph.contain(ch);
         if(prevIsWord != isWord) {
            if(prevIsWord || (!prevIsWord && addDelims))
               arr.add(current);
            current = "";
         }
         prevIsWord = isWord;
         current += ch;
      }
      return arr;
   }
   
   public static ArrayList<String> ToFixedParts(String str, int part_len) {
      ArrayList<String> parts = new ArrayList<>();
      int beg_ind = 0;
      int end_ind = beg_ind + part_len - 1;
      while(end_ind < str.length()-1) {
         parts.add(str.substring(beg_ind, end_ind + 1));
         beg_ind = end_ind + 1;
         end_ind = beg_ind + part_len - 1;
      }
      parts.add(str.substring(beg_ind, str.length()));
      return parts;      
   }
  
}
