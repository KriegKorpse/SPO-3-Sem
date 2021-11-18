package com.KriegKorpse;

public class Alphabet {
   private static final char alphabet[] = {
      'А','Б','В','Г','Д','Е','Ё','Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ъ','Ь','Ы','Э','Ю','Я',
      'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ъ','ь','ы','э','ю','я'
   };
   
   private int indexOf(char c) {
      boolean found = false;
      int i = 0;
      while(!found && (i < alphabet.length))
         found = c == alphabet[i++];
      i--;
      if(!found)
         i = -1;
      return i;
   }
   
   public boolean contain(char c) {
      return -1 != indexOf(c);
   }
   
}

