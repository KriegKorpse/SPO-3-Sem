package freqanalyzertest;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FreqAnalyzerTest {

   public static void main(String[] args) throws TxtFileException, FreqAnalyzerException, FileNotFoundException, UnsupportedEncodingException {
      testAnalyze();
      //GenerateFreqs("METRO_2033.txt", "cp1251");
      //CaesarCryptFile("Обитаемый_остров_Малыш_часть3.txt", "cp1251");
   }
   
   private static void testAnalyze() throws TxtFileException, FreqAnalyzerException, FileNotFoundException, UnsupportedEncodingException {
      //Printer printer = new Printer(System.out);
      Printer printer = new Printer(new PrintStream("output.txt", "utf-8"));

      String cryptedFName = toAbsolutePath("Обитаемый_остров_Малыш_часть3.txt_crypted.txt");
      String original = TxtFile.Read(cryptedFName, "cp1251");
      printer.println("------------------------------------------------------------");
      printer.println(original.substring(0, 1000));


      //FreqAnalyzer anCh0 = new FreqAnalyzer(cryptedFName, "cp1251", "Обитаемый_остров_Малыш.txt_freqsCh.txt", "utf-8", null);
      //anCh0.Decrypt();
      //printer.println("------------------------------------------------------------");
      //printer.println(anCh0.GetDecryptedText().substring(0, 1000));

      FreqAnalyzer anBG = new FreqAnalyzer(cryptedFName, "cp1251", "Обитаемый_остров_Малыш.txt_freqsBG.txt", "utf-8", null);
      //anBG.Decrypt();
      //printer.println("------------------------------------------------------------");
      //printer.println(anBG.GetDecryptedText().substring(0, 1000));
      ArrayList<Substitution> bgSubst = SubstitutionUtils.GetTopElems(anBG.GetSubstitution(), 7);
      ArrayList<Substitution> chSubst = SubstitutionUtils.BgToChSubstitutions(bgSubst);
      
      printer.println("------------------------------------------------------------");
      printer.PrintArr(bgSubst);
      printer.PrintArr(chSubst);
      
      FreqAnalyzer anCh1 = new FreqAnalyzer(cryptedFName, "cp1251", "Обитаемый_остров_Малыш.txt_freqsCh.txt", "utf-8", chSubst);
      anCh1.Decrypt();
      printer.println("------------------------------------------------------------");
      printer.println(anCh1.GetDecryptedText().substring(0, 1000));
   }
   
   private static void GenerateFreqs(String fName, String encoding) throws FreqAnalyzerException, TxtFileException {
      Alphabet alph = new Alphabet();
      String text = TxtFile.Read(fName, encoding).toLowerCase(); // при криптоанализе всё приводится к маленьким буквам.
      String fNameCh = fName + "_freqsCh.txt";
      String fNameBG = fName + "_freqsBG.txt";
      
      ArrayList<FreqInfo> tableCh = FreqTable.CreateCh(text, alph);
      FreqTable.WriteToFile(tableCh, fNameCh, "utf-8");
      ArrayList<FreqInfo> tableBG = FreqTable.CreateBG(text, alph);
      FreqTable.WriteToFile(tableBG, fNameBG, "utf-8");
      
      System.out.println("Созданы файлы частотностей: " + fNameCh + ", " + fNameBG);
   }
   
   private static void CaesarCryptFile(String fName, String encoding) throws TxtFileException {
      String text = TxtFile.Read(fName, encoding);
      Caesar caesar = new Caesar();
      String cryptedText = caesar.Crypt(15, text);
      String cryptedFName = fName + "_crypted.txt";
      TxtFile.Write(cryptedText, cryptedFName, encoding);
      System.out.println("Зашифрован файл: " + cryptedFName);
   }
   
   private static String toAbsolutePath(String fName) {
      return Paths.get(fName).toAbsolutePath().toString();
   }
   
}
