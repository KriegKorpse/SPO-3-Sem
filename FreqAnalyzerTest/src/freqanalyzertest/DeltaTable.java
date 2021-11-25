package freqanalyzertest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

//-----------------------------------------------------------------------------
public class DeltaTable {
   
   public static ArrayList<DeltaTableLine> Create(ArrayList<FreqInfo> stdDist, ArrayList<FreqInfo> txtDist) {
      ArrayList<DeltaTableLine> deltaTable = new ArrayList<>();

      for(int i = 0; i < txtDist.size(); i++)
      {
         FreqInfo txtElem = txtDist.get(i);
         ArrayList<FreqDelta> values = new ArrayList<>();
         for(int j = 0; j < stdDist.size(); j++)
         {
            FreqInfo stdElem = stdDist.get(j);
            double d_freq = Math.abs(txtElem.freq - stdElem.freq);
            FreqDelta delta = new FreqDelta(stdElem.symb, d_freq);
            values.add(delta);
         }
         
         values.sort(FreqDelta.comparator);
         DeltaTableLine line = new DeltaTableLine(txtElem.symb, values);
         deltaTable.add(line);
      }
      return deltaTable;
   }

   private static HashMap<String, Substitution> GetBestColumnSubstitution(ArrayList<DeltaTableLine> deltaTable, int colNum, ArrayList<Substitution> disabled) {
      // Берем выбранный столбец deltaTable и заменяем дублирующиеся символы символом с минимальным d_freq
      HashMap<String, Substitution> toMap = new HashMap<>();
      for (DeltaTableLine line : deltaTable) {
         if(null == SubstitutionUtils.FindByFrom(line.GetSymb(), disabled))
         {
            FreqDelta col = line.GetValues().get(colNum);
            Substitution mapElem = toMap.get(col.symb);
            if(null != mapElem) {
               if(mapElem.d_freq > col.d_freq) {
                  mapElem.d_freq = col.d_freq;
                  mapElem.from = line.GetSymb();
               }
            }
            else
               toMap.put(col.symb, new Substitution(line.GetSymb(), col.symb, col.d_freq));
         }
      }
      return toMap;
   }

   public static ArrayList<Substitution> GetBestSubstitutionTable(ArrayList<DeltaTableLine> deltaTable, ArrayList<Substitution> apriorySubst) {
      ArrayList<Substitution> result;
      if(null == apriorySubst)
         result = new ArrayList<>();
      else
         result = (ArrayList<Substitution>) apriorySubst.clone();
      
      int totalCol = deltaTable.get(0).GetValues().size();
      int colNum = 0;
      boolean stop = false;
      while( !stop && (colNum < totalCol) ) {
         stop = result.size() == deltaTable.size();
         
         if(!stop) {
            HashMap<String, Substitution> colMap = GetBestColumnSubstitution(deltaTable, colNum, result);

            Iterator<Substitution> it = colMap.values().iterator();
            while(it.hasNext()) {
               Substitution mapElem = it.next();
               if(    (null == SubstitutionUtils.FindByFrom(mapElem.from, result)) 
                   && (null == SubstitutionUtils.FindByTo(mapElem.to, result)) )
                  result.add(mapElem);
            }
         }

         colNum++;
      }
      
      return result;      
   }

   public static DeltaTableLine FindLine(String symb, ArrayList<DeltaTableLine> deltaTable) {
      boolean found = false;
      int i = 0;
      DeltaTableLine line = null;
      while(!found && (i < deltaTable.size())) {
         line = deltaTable.get(i++);
         found = line.GetSymb().equals(symb);
      }
      
      if(found)
         return line;
      return null;
   }
   
   public static boolean IsSymbFixed(String symb, ArrayList<DeltaTableLine> deltaTable) {
      boolean found = false;
      int i = 0;
      while(!found && (i < deltaTable.size())) {
         DeltaTableLine line = deltaTable.get(i);
         String selected = line.GetSelectedValue().symb;
         if(line.fixed)
            found = line.fixed && selected.equals(symb);
         i++;
      }
      return found;
   }
}

//-----------------------------------------------------------------------------
class FreqDelta {
   String symb;
   double d_freq;
   
   public FreqDelta(String symb, double d_freq) {
      this.symb = symb;
      this.d_freq = d_freq;
   }

   public FreqDelta(String symb) {
      this.symb = symb;
      this.d_freq = 0.0;
   }
   
   @Override      
   public String toString() {
      return String.format("%s : %f", symb, d_freq); 
   }
   
   @Override
   public boolean equals(Object obj) {
      
      if (obj == this) {
          return true;
      }
      if (obj == null || obj.getClass() != this.getClass()) {
          return false;
      }
      FreqDelta other = (FreqDelta)obj;
      return symb.equals(other.symb);
   }   

   @Override
   public int hashCode() {
      int hash = 3;
      hash = 67 * hash + Objects.hashCode(this.symb);
      return hash;
   }

   static final Comparator<FreqDelta> comparator = new Comparator<FreqDelta>() {
      @Override
      public int compare(FreqDelta left, FreqDelta right) {
         // Сортировка в порядке возрастания d_freq
         if      (left.d_freq < right.d_freq) return -1;
         else if (left.d_freq > right.d_freq) return 1;
         return 0; 
      }
   };
}

//-----------------------------------------------------------------------------
class DeltaTableLine {
   private String symb;
   private ArrayList<FreqDelta> values;
   private int selected;
   public boolean fixed;
   
   public DeltaTableLine(String symb, ArrayList<FreqDelta> values) {
      this.fixed = false;
      this.symb = symb;
      this.values = values;
      if(values.size() > 0)
         selected = 0;
      else
         selected = -1;
   }
   
   public ArrayList<FreqDelta> GetValues() {
      return values;
   }

   public String GetSymb() {
      return symb;
   }

   public FreqDelta GetSelectedValue() {
      if(-1 == selected)
         return null;
      return values.get(selected);
   }

   public void SetSelectedValue(String symb) {
      int index = values.indexOf(new FreqDelta(symb)); 
      if(index >= 0)
         selected = index;
      else
         selected = 0;
   }

   public void SetSelectedIndex(int index) throws FreqAnalyzerException {
      if(selected >= values.size()-1)
         throw new FreqAnalyzerException("Невозможно сделать выделенным элемент №" + index);
      selected = index;
   }
   
   public FreqDelta SetNextSelectedValue() throws FreqAnalyzerException {
      if(selected >= values.size()-1)
         throw new FreqAnalyzerException("Невозможно сделать выделенным следующий элемент");
      selected++;
      return values.get(selected);
   }
}

//-----------------------------------------------------------------------------
class Substitution {
   public String from;
   public String to;
   public double d_freq;
   
   public Substitution(String from, String to, double d_freq) {
      this.from = from; 
      this.to = to; 
      this.d_freq = d_freq;
   }

   @Override      
   public String toString() {
      return String.format("%s -> %s : %f", from, to, d_freq); 
   }
   
}

class SubstitutionUtils {
   public static ArrayList<Substitution> GetTopElems(ArrayList<Substitution> arr, int nElems) {
      ArrayList<Substitution> result = new ArrayList<>(nElems);   
      if(nElems > arr.size())
         nElems = arr.size();
      for (int i = 0; i < nElems; i++)
         result.add(arr.get(i));
      return result;
   }
   
   public static ArrayList<Substitution> BgToChSubstitutions(ArrayList<Substitution> bgS) {
      ArrayList<Substitution> chS = new ArrayList<>();
      for (int i = 0; i < bgS.size(); i++) {
         Substitution bgSubst = bgS.get(i);
         Substitution chSubst1 = new Substitution(bgSubst.from.substring(0, 1), bgSubst.to.substring(0, 1), 0.0);
         Substitution chSubst2 = new Substitution(bgSubst.from.substring(1),    bgSubst.to.substring(1), 0.0);
         
         if( (null == FindByFrom(chSubst1.from, chS)) && (null == FindByTo(chSubst1.to, chS)) )
            chS.add(chSubst1);

         if( (null == FindByFrom(chSubst2.from, chS)) && (null == FindByTo(chSubst2.to, chS)) )
            chS.add(chSubst2);
      }
      return chS;
   }
   
   public static Substitution FindByFrom(String from, ArrayList<Substitution> arr) {
      boolean found = false;
      int i = 0;
      while(!found && (i < arr.size()))
         found = from.equals(arr.get(i++).from);
      i--;
      if(found)
         return arr.get(i);
      return null;
   }

   public static Substitution FindByTo(String to, ArrayList<Substitution> arr) {
      boolean found = false;
      int i = 0;
      while(!found && (i < arr.size()))
         found = to.equals(arr.get(i++).to);
      i--;
      if(found)
         return arr.get(i);
      return null;
   }
}

