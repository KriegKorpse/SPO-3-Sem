package freqanalyzertest;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FreqTable {
   
   public static ArrayList<FreqInfo> CreateBG(String text, Alphabet alph) throws FreqAnalyzerException {
      FreqTableBGCreator creator = new FreqTableBGCreator(text, alph);
      return creator.freqs;
   }
   
   public static ArrayList<FreqInfo> CreateCh(String text, Alphabet alph) throws FreqAnalyzerException {
      FreqTableChCreator creator = new FreqTableChCreator(text, alph);
      return creator.freqs;
   }

   public static ArrayList<FreqInfo> ReadFromFile(TxtFile txtFile) throws TxtFileException, FreqAnalyzerException {
      ArrayList<FreqInfo> result = new ArrayList<>();
      String str;
      while( null != (str = txtFile.ReadLine()) )
         result.add(FreqInfo.fromString(str));
      result.sort(FreqInfo.freqComparator);
      return result;
   }

   public static void WriteToFile(ArrayList<FreqInfo> freqs, String fName, String encoding) throws FreqAnalyzerException {
      try {
         freqs.sort(FreqInfo.freqComparator);
         Printer out = new Printer(new PrintStream(fName, encoding));
         out.PrintArr(freqs);
      } catch (FileNotFoundException ex) {
         throw new FreqAnalyzerException("FreqInfoException: " + ex.getMessage());
      } catch (UnsupportedEncodingException ex) {
         throw new FreqAnalyzerException("FreqInfoException: " + ex.getMessage());
      }
   }
   
   public static boolean IsTableBG(ArrayList<FreqInfo> table) throws FreqAnalyzerException {
      int symbLen = table.get(0).symb.length();
      switch(symbLen) {
         case 1:
            return false;
         case 2:
            return true;
         default:
            throw new FreqAnalyzerException("Таблица не содержит биграммы или символы");
      }
   }
   
   // Возвращает полную таблицу (с отсутствующими символами)
   public static ArrayList<FreqInfo> GetFullTableCh(ArrayList<FreqInfo> freqs, Alphabet alph) {
      ArrayList<FreqInfo> result = new ArrayList<>();

      HashMap<String, FreqInfo> freqsMap = new HashMap<>();
      for(int i = 0; i < freqs.size(); i++)
         freqsMap.put(freqs.get(i).symb, freqs.get(i));
      
      Iterator<Character> it1 = alph.iterator();
      while(it1.hasNext()) {
         String ch = it1.next().toString();
         if(null == freqsMap.get(ch))
            freqsMap.put(ch, new FreqInfo(ch, Double.NaN));
      }

      Iterator<FreqInfo> it = freqsMap.values().iterator();
      while(it.hasNext())
         result.add(it.next());
      
      result.sort(FreqInfo.symbComparator);
      return result;
   }
   
   // Возвращает полную таблицу (с отсутствующими биграммами)
   public static ArrayList<FreqInfo> GetFullTableBG(ArrayList<FreqInfo> freqs, Alphabet alph) {
      ArrayList<FreqInfo> result = new ArrayList<>();

      HashMap<String, FreqInfo> freqsMap = new HashMap<>();
      for(int i = 0; i < freqs.size(); i++)
         freqsMap.put(freqs.get(i).symb, freqs.get(i));
      
      Iterator<Character> it1 = alph.iterator();
      while(it1.hasNext()) {
         String ch1 = it1.next().toString();
         Iterator<Character> it2 = alph.iterator();
         while(it2.hasNext()) {
            String ch2 = it2.next().toString();
            String bg = ch1 + ch2;
            if(null == freqsMap.get(bg))
               freqsMap.put(bg, new FreqInfo(bg, Double.NaN));
         }
      }

      Iterator<FreqInfo> it = freqsMap.values().iterator();
      while(it.hasNext())
         result.add(it.next());
      
      result.sort(FreqInfo.symbComparator);
      return result;
   }
   
}

class FreqTableBGCreator {
   public ArrayList<FreqInfo> freqs = new ArrayList<>();

   public FreqTableBGCreator(String text, Alphabet alph) throws FreqAnalyzerException {
      ArrayList<String> parsed_shift0 = BigrammParser.ParseText(text, 0, alph);
      ArrayList<String> parsed_shift1 = BigrammParser.ParseText(text, 1, alph);
      
      HashMap<String, FreqInfo> bgMap = new HashMap<>();
      int total = 0;
      total += AddToMap(bgMap, BigrammParser.GetBigrams(parsed_shift0, alph));
      total += AddToMap(bgMap, BigrammParser.GetBigrams(parsed_shift1, alph));

      Iterator<FreqInfo> it = bgMap.values().iterator();
      while(it.hasNext()) {
         FreqInfo elem = it.next();
         elem.CalcFreq(total);
         freqs.add(elem);
      }
      freqs.sort(FreqInfo.freqComparator);
   }

   private static int AddToMap(HashMap<String, FreqInfo> bgMap, ArrayList<String> bigrams) throws FreqAnalyzerException {
      int total = 0;
      for(int i = 0; i < bigrams.size(); i++) {
         String bg = bigrams.get(i);
         if(2 == bg.length()) { // Это биграмма
            FreqInfo elem = bgMap.get(bg);
            if(null == elem)
               bgMap.put(bg, new FreqInfo(bg));
            else
               elem.count++;
            total++;
            if(Integer.MAX_VALUE == total)
               throw new FreqAnalyzerException("total превышает Integer.MAX_VALUE");
         }
      }
      return total;
   }
}

class FreqTableChCreator {
   public ArrayList<FreqInfo> freqs = new ArrayList<>();

   public FreqTableChCreator(String text, Alphabet alph) throws FreqAnalyzerException {
      HashMap<Character, FreqInfo> chMap = new HashMap<>();

      int total = 0;
      for(int i = 0; i < text.length(); i++) {
         char ch = text.charAt(i);
         if(alph.contain(ch)) {
            FreqInfo elem = chMap.get(ch);
            if(null == elem)
               chMap.put(ch, new FreqInfo(String.valueOf(ch)));
            else
               elem.count++;
            total++;
            if(Integer.MAX_VALUE == total)
               throw new FreqAnalyzerException("total превышает Integer.MAX_VALUE");
         }
      }

      Iterator<FreqInfo> it = chMap.values().iterator();
      while(it.hasNext()) {
         FreqInfo elem = it.next();
         elem.CalcFreq(total);
         freqs.add(elem);
      }
      freqs.sort(FreqInfo.freqComparator);
   }
}

