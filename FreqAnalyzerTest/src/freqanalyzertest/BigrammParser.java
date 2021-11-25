package freqanalyzertest;

import java.util.ArrayList;

public class BigrammParser {
   
   public static ArrayList<String> ParseText(String text, int shift, Alphabet alph) {
      ArrayList<String> result = new ArrayList<>();
      
      ArrayList<String> temp = TextConvertor.ToWordsAndDelims(text, alph);
      for(int i = 0; i < temp.size(); i++) {
         String str = temp.get(i);
         if((str.length() > 1) && TextConvertor.IsWord(str, alph)) {
            if(1 == shift) {
               result.add(str.substring(0, 1));
               str = str.substring(1);
            }
            ArrayList<String> bigrams = TextConvertor.ToFixedParts(str, 2);
            result.addAll(bigrams);
         }
         else
            result.add(str);
      }
      return result;
   }
   
   public static ArrayList<String> GetBigrams(ArrayList<String> parsedText, Alphabet alph) {
      ArrayList<String> result = new ArrayList<>();
      for(int i = 0; i < parsedText.size(); i++) {
         String str = parsedText.get(i);
         if((2 == str.length()) && TextConvertor.IsWord(str, alph)) // это биграмма
            result.add(str);
      }   
      return result;
   }
   
}
