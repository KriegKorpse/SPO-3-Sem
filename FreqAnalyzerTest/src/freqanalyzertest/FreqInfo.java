package freqanalyzertest;

import java.util.Comparator;

public class FreqInfo {
   String symb;
   double freq;
   int count;

   public FreqInfo(String symb, double freq) {
      this.symb = symb;
      this.freq = freq;
      this.count = 0;
   }

   public FreqInfo(String symb) {
      this.symb = symb;
      this.freq = 0.0;
      this.count = 1;
   }
   
   public void CalcFreq(int totalCount) {
      freq = 100.0 * count / totalCount;
   }

   @Override
   public String toString() {
      return String.format("%s : %f", symb, freq);
   }

   // Метод, обратный toString()
   public static FreqInfo fromString(String str) throws FreqAnalyzerException {
      String parsed_str[] = str.split(":");
      if(parsed_str.length < 2)
         throw new FreqAnalyzerException("Строка " + "\"" + str + "\"" + " не содержит данных FreqInfo");
      String symb = parsed_str[0].trim();
      double freq = Double.valueOf(parsed_str[1].replace(',', '.')); 
      return new FreqInfo(symb, freq);
   }

   static final Comparator<FreqInfo> freqComparator = new Comparator<FreqInfo>() {
      @Override
      public int compare(FreqInfo left, FreqInfo right) {
         // Сортировка в порядке убывания freq
         if      (left.freq > right.freq) return -1;
         else if (left.freq < right.freq) return 1;
         return 0;
      }
   };

   static final Comparator<FreqInfo> symbComparator = new Comparator<FreqInfo>() {
      @Override
      public int compare(FreqInfo left, FreqInfo right) {
         return left.symb.compareTo(right.symb);
      }
   };
}


