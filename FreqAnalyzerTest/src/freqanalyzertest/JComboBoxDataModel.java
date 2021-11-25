package freqanalyzertest;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;

public class JComboBoxDataModel extends DefaultListModel implements ComboBoxModel {
   
   FreqAnalyzer analyzer = null;
   String key_symb = "";
  
   public JComboBoxDataModel(FreqAnalyzer analyzer, String symb)
   {
      this.analyzer = analyzer;
      this.key_symb = symb;
   }
   
   @Override
   public void setSelectedItem(Object obj)
   {
      FreqDelta item = (FreqDelta)obj;
      GetTableLine().SetSelectedValue(item.symb);
      fireContentsChanged(this,-1,-1);
   }
   
   @Override
   public Object getSelectedItem()
   {
      FreqDelta item = GetTableLine().GetSelectedValue(); 
      return item;  // Выводится значение DeltaBg.toString();
   }
   
   @Override
   public int getSize()
   {
      return GetTableLine().GetValues().size();
   }
   
   @Override
   public Object getElementAt(int index)
   {
      return GetTableLine().GetValues().get(index); // Выводится значение DeltaBg.toString();
   }

   private DeltaTableLine GetTableLine() {
      return analyzer.GetDeltaTableLine(key_symb);
   }
}