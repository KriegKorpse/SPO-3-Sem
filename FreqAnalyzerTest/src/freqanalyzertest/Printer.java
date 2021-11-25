package freqanalyzertest;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Printer {
   private final PrintStream stm;
   
   public Printer(PrintStream stm) {
      this.stm = stm;
   }
   
   public void println(String text) {
      stm.println(text);
   }
   
   public void PrintArr(ArrayList<?> arr) {
      for(int i = 0; i < arr.size(); i++)
         stm.println(arr.get(i).toString());
   }
   
   public void PrintArr2(ArrayList<?> arr1, ArrayList<?> arr2, int columnWidth, char columnDelim) {
      String fmt0 = "%-" + columnWidth + "s"; 
      String fmt = fmt0 + " %c " + fmt0;
      int size = Math.max(arr1.size(), arr2.size());
      for(int i = 0; i < size; i++) {
         String str1 = "";
         String str2 = "";
         
         if(i < arr1.size())
            str1 += arr1.get(i).toString();

         if(i < arr2.size())
            str2 += arr2.get(i).toString();

         String str = String.format(fmt, str1, columnDelim, str2);
         stm.println(str);
      }
   }
   
   public static void PrintDeltaTable(FreqAnalyzer analyzer, PrintStream stm) {
      Iterator<FreqInfo> freqs = analyzer.GetCryptedTextChars();
      while(freqs.hasNext()) {
         FreqInfo freq = freqs.next();

         String lineKey = freq.symb;         
         DeltaTableLine line = analyzer.GetDeltaTableLine(lineKey);
         ArrayList<FreqDelta> lineValues = line.GetValues();
         FreqDelta selectedValue = line.GetSelectedValue();
         stm.print(lineKey + " -> " + "(" + selectedValue.toString() + ") ");
         for(int i = 0; i < lineValues.size(); i++) {
            FreqDelta lineValue = lineValues.get(i);
            stm.print(lineValue.toString() + " ");
         }
         stm.println();
         stm.flush();
      }
   }
}
