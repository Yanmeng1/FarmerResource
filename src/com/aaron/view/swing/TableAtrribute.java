package com.aaron.view.swing;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableAtrribute {

	
	/**
	 * 设置列表头的宽度
	 * @param jTable
	 */
	public static void setTableHeaderWidth(JTable table) {
		for (int i=0; i<table.getColumnCount(); i++) {
			TableColumn tableColum = table.getColumn(table.getColumnName(i));
	        int width=getPreferredWidthForColumn(table, tableColum)+10;  
	        tableColum.setMinWidth(width);  
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
    /**获取一个列的推荐宽度，返回列名和列取值的最大宽度。*/  //2  
    private static int getPreferredWidthForColumn(JTable table, TableColumn col)  
    {  
      int hw=columnHeaderWidth(table, col),  
          cw=widestCellInColumn(table, col);  
      return hw>cw?hw:cw;  
    }  
  
    /**表头列的宽度*/  //3  
    private static int columnHeaderWidth(JTable table, TableColumn col)  
    {  
      TableCellRenderer renderer=col.getHeaderRenderer();  
      if(renderer==null)  
      {  
        renderer=table.getTableHeader().getDefaultRenderer();  
      }  
      if(renderer==null)  
          return 0;  
      Component comp=renderer.getTableCellRendererComponent(table,col.getHeaderValue(),false,false,0,0);  
      return comp.getPreferredSize().width;  
    }  
  
    /**值列的最大宽度*/  //4  
    private static int widestCellInColumn(JTable table, TableColumn col)  
    {  
      if(true) return 0;  
      int c=col.getModelIndex(),width=0,maxw=0;  
      for(int r=0;r<table.getRowCount();++r)  
      {  
        TableCellRenderer renderer=table.getCellRenderer(r,c);  
        Component comp=renderer.getTableCellRendererComponent(table,table.getValueAt(r,c),false,false,r,c);  
        width=comp.getPreferredSize().width;  
        maxw=width>maxw?width:maxw;  
      }  
      return maxw;  
    }  
}
