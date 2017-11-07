package com.aaron.view.swing;

import javax.swing.JFrame;
import javax.swing.JTable;

import com.aaron.data.parameter.IOParameter;

public class FileView {
	
	@SuppressWarnings("unused")
	private JFrame jFrame = null;
	private IOParameter ioParameter = null;
	private JTable table = null;
	
	public FileView ( JFrame jFrame, IOParameter ioParameter ) {
		this.jFrame = jFrame;
		this.ioParameter = ioParameter;
	}
	
	public JTable getTable(){
		
		String[] names = this.ioParameter.getColName();
		Object[][] tableContents = new Object[ioParameter.getSpecies()][ioParameter.getColNum()];
		for (int row=0; row<ioParameter.getSpecies(); row++){
			tableContents[row][0] = ioParameter.getNo()[row]; 
			tableContents[row][1] = ioParameter.getSpeciesName()[row];
			tableContents[row][2] = ioParameter.getCostTarget()[row];
			tableContents[row][3] = ioParameter.getMarginTarget()[row];
			tableContents[row][4] = ioParameter.getOutputTarget()[row];
			tableContents[row][5] = ioParameter.getEnvironmentalTarget()[row];
			tableContents[row][6] = ioParameter.getLabourRestrict()[row];
			tableContents[row][7] = ioParameter.getMachineRestrcit()[row];
			tableContents[row][8] = ioParameter.getLandRestrict()[row];
			tableContents[row][9] = ioParameter.getFertilizerInput()[row];
			tableContents[row][10] = ioParameter.getFertilizerOutput()[row];
		}
		table = new MyTable(tableContents, names);

		return table;
	}
}

// 自定表格设置不可编辑状态
class MyTable extends JTable{
	private static final long serialVersionUID = 1L;
	
	public MyTable(Object[][] tableContents, String[] names){
		super(tableContents, names);
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		if ( column == 0 || column == 1 ) return false;
		else return true;
	}
}
