package com.KriegKorpse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

class TxtFileException extends Exception {
   public TxtFileException(String text) {
      super(text);
   }
}
   
public class TxtFile {

   public static String Read(String fileName, String encoding) throws TxtFileException {
      String text = "";
      
      try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), encoding)) {
         // читаем посимвольно
         int c;
         while((c = reader.read()) != -1)
            text += (char)c;
         
      } catch (FileNotFoundException ex) {
         throw new TxtFileException("Ошибка " + TxtFile.class.getName() + ": " + ex.getMessage());
      } catch (UnsupportedEncodingException ex) {
         throw new TxtFileException("Ошибка " + TxtFile.class.getName() + ": " + ex.getMessage());
      } catch (IOException ex) {
         throw new TxtFileException("Ошибка " + TxtFile.class.getName() + ": " + ex.getMessage());
      }

      return text;
   }

   public static String Read(String fileName) throws TxtFileException {
      return Read(fileName, System.getProperty("file.encoding"));
   }

   public static void Write(String text, String fileName, String encoding) throws TxtFileException {
      
      try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName, false), encoding)) {
         writer.write(text);
         
      } catch (FileNotFoundException ex) {
         throw new TxtFileException("Ошибка " + TxtFile.class.getName() + ": " + ex.getMessage());
      } catch (UnsupportedEncodingException ex) {
         throw new TxtFileException("Ошибка " + TxtFile.class.getName() + ": " + ex.getMessage());
      } catch (IOException ex) {
         throw new TxtFileException("Ошибка " + TxtFile.class.getName() + ": " + ex.getMessage());
      }
   }
   
   public static void Write(String text, String fileName) throws TxtFileException {
      Write(text, fileName, System.getProperty("file.encoding"));
   }
}
