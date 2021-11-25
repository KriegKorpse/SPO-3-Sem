package freqanalyzertest;

import java.util.ArrayList;

public class Caesar {
   
   private final char alph[] = {'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я'};
      
   public String Crypt(int key, String text)
   {
      String result = "";
      char chars[] = text.toLowerCase().toCharArray();
      for(int i = 0; i < chars.length; i++)
      {
         int x = Ch2Num(chars[i]);
         if(x != -1)
         {
            int y = (x + key) % alph.length;
            result += Num2Ch(y);
         }
         else
            result += chars[i];
      }

      return result;
   }
   
   public String DeCrypt(int key, String text)
   {
      String result = "";

      char chars[] = text.toCharArray();
      for(int i = 0; i < chars.length; i++)
      {
         int y = Ch2Num(chars[i]);
         if(y != -1)
         {
            int x = (y - key) % alph.length;
            if(x < 0)
               x = alph.length + x;
            result += Num2Ch(x);
         }
         else
            result += chars[i];
      }
      
      return result;
   }
      
   private int Ch2Num(char ch)
   {
      boolean found = false;
      int i = 0;
      while(!found && (i < alph.length))
      {
         found = ch == alph[i];
         i++;
      }
      
      if(found)
         return i-1;
      else
         return -1;
   }
   
   private char Num2Ch(int num)
   {
      return alph[num];
   }
   
}
