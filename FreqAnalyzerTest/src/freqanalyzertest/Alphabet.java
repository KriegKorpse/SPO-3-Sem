package freqanalyzertest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Alphabet {
   private static final char alphabet[] = {
      'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ъ','ь','ы','э','ю','я'
   };

   int length(){
      return alphabet.length;
   }
   
   public Iterator<Character> iterator() {
      ArrayList<Character> arr = new ArrayList<>();
      for(int i = 0; i < alphabet.length; i++)
         arr.add(alphabet[i]);
      return arr.iterator();
   }

   int indexOf(char c) {
      boolean found = false;
      int i = 0;
      while(!found && (i < alphabet.length))
         found = c == alphabet[i++];
      i--;
      if(!found)
         i = -1;
      return i;
   }

   char charOf(int i){
       return alphabet[i];
   }
   
   public boolean contain(char c) {
      return -1 != indexOf(c);
   }
   
}

