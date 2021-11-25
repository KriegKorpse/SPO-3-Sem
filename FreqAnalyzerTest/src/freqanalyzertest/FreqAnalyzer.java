package freqanalyzertest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

//-----------------------------------------------------------------------------
public class FreqAnalyzer {
   private final Alphabet alph = new Alphabet();
   private final String cryptedText;
   private String decryptedText;
   private final ArrayList<FreqInfo> stdDist;
   private final ArrayList<FreqInfo> txtDist;
   private final ArrayList<DeltaTableLine> deltaTable;
   private final ArrayList<Substitution> apriorySubst;
   
   public FreqAnalyzer(String cryptedFName, String cryptedEncoding, String freqTableFName, String freqTableEncoding, ArrayList<Substitution> apriorySubst) throws TxtFileException, FreqAnalyzerException
   {
      try {
         
         this.apriorySubst = apriorySubst;
         
         stdDist = FreqTable.ReadFromFile(new TxtFile(freqTableFName, freqTableEncoding));

         cryptedText = TxtFile.Read(cryptedFName, cryptedEncoding).toLowerCase();
         decryptedText = cryptedText;
         
         if(FreqTable.IsTableBG(stdDist))
            txtDist = FreqTable.CreateBG(cryptedText, alph);
         else
            txtDist = FreqTable.CreateCh(cryptedText, alph);

         deltaTable = DeltaTable.Create(stdDist, txtDist);

         AutoSelect();

         String dumpBaseName = PrepareDumpFolder(cryptedFName);
         FreqTable.WriteToFile(txtDist, dumpBaseName + "_dump_freq.txt", freqTableEncoding);
         Printer.PrintDeltaTable(this, new PrintStream(dumpBaseName + "_dump_delta_table.txt", "cp1251"));
      } 
      catch (FileNotFoundException ex) {
         throw new FreqAnalyzerException("FileNotFoundException: " + ex.getMessage());
      }
      catch (UnsupportedEncodingException ex) {
         throw new FreqAnalyzerException("UnsupportedEncodingException: " + ex.getMessage());
      }
   }
/*
   private void AutoSelect() throws FreqAnalyzerException {
      for(int i = 0; i < deltaTable.size(); i++) {
         DeltaTableLine line = deltaTable.get(i);
         line.fixed = false;
         while(DeltaTable.IsSymbFixed(line.GetSelectedValue().symb, deltaTable))
            line.SetNextSelectedValue();
         line.fixed = true;
      }
   }
*/

   private void AutoSelect() throws FreqAnalyzerException {
      // Снимаем все fixed       
      for(int i = 0; i < deltaTable.size(); i++)
         deltaTable.get(i).fixed = false;
      
      // Фиксим "лучшие замены"
      int numFixed = 0;
      ArrayList<Substitution> bestSubstTable = DeltaTable.GetBestSubstitutionTable(deltaTable, apriorySubst);
      for(int i = 0; i < deltaTable.size(); i++) {
         DeltaTableLine line = deltaTable.get(i);
         if(!line.fixed) {
            Substitution bestSubst = SubstitutionUtils.FindByFrom(line.GetSymb(), bestSubstTable);
            if(null != bestSubst) {
               line.SetSelectedValue(bestSubst.to);
               line.fixed = true;
               numFixed++;
            }
         }
      }
      
      // Фиксим оставшихся, если они есть
      if(numFixed != deltaTable.size()) {
         for(int i = 0; i < deltaTable.size(); i++) {
            DeltaTableLine line = deltaTable.get(i);
            if(!line.fixed) {
               while(DeltaTable.IsSymbFixed(line.GetSelectedValue().symb, deltaTable))
                  line.SetNextSelectedValue();
               line.fixed = true;
            }
         }
      }
   }
   
   public String GetCryptedText(int beginPos, int numCh) {
      int endPos = beginPos + numCh - 1;
      if(endPos <= cryptedText.length())
         return cryptedText.substring(beginPos, endPos);
      return cryptedText.substring(beginPos);
   }

   public String GetDecryptedText(int beginPos, int numCh) {
      int endPos = beginPos + numCh - 1;
      if(endPos <= decryptedText.length())
         return decryptedText.substring(beginPos, endPos);
      return decryptedText.substring(beginPos);
   }

   public String GetDecryptedText() {
      return decryptedText;
   }
   
   public Iterator<FreqInfo> GetAlphabet() {
      return stdDist.iterator();
   }

   public Iterator<FreqInfo> GetCryptedTextChars() {
      return txtDist.iterator();
   }

   public DeltaTableLine GetDeltaTableLine(String symb) {
      return DeltaTable.FindLine(symb, deltaTable);
   }

   public ArrayList<Substitution> GetSubstitution() {
      ArrayList<Substitution> result = new ArrayList<>();
      for(int i = 0; i < txtDist.size(); i++)
      {
         String from = txtDist.get(i).symb;
         FreqDelta selected = DeltaTable.FindLine(from, deltaTable).GetSelectedValue();
         result.add(new Substitution(from, selected.symb, selected.d_freq));
      }
      return result;
   }
   
   public void Decrypt() {
      decryptedText = cryptedText;
      ArrayList<Substitution> subst = GetSubstitution();
      
      for(int i = 0; i < subst.size(); i++) {
         Substitution pair = subst.get(i);
         String to = pair.to.toUpperCase();
         decryptedText = decryptedText.replace(pair.from, to);
      }
      decryptedText = decryptedText.toLowerCase();
   }
    
   String PrepareDumpFolder(String cryptedFName) {
      Path path = Paths.get(cryptedFName);
      Path dumpFolderName = Paths.get(path.getParent().toString() + "\\dumps");
      File dumpFolder = dumpFolderName.toFile();
      if(!dumpFolder.exists())
         dumpFolder.mkdir();
      String dumpBaseName = dumpFolderName + "\\" + path.getFileName().toString();
      return dumpBaseName;
   }
   
}

