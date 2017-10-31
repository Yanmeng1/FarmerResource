package com.aaron.view.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.aaron.data.parameter.GUIParameter;

public class DataView implements TableModelListener {
	
	private JFrame jFrame;
	private JPanel rigidPanel = null;
	private JTable wightTable = null;
	private JTable restrictTable = null;
	
	private JPanel singlePanel = null;
	private JTable singleTable = null;
	
	public DataView ( JFrame jFrame ) {
		this.jFrame = jFrame;
	}
	/**
	 * 刚性约束
	 * @param guiParameter
	 */
	public void rigidConstraint(GUIParameter guiParameter) {
		System.out.println("刚性约束");
		if(rigidPanel == null) {
		String[] names1 = {"目标函数","利润","投入成本","环境","粮食产量"};
		Object[][] tableContent1 = {{" 权重", new Double(0.0), new Double(0.0), new Double(0.0), new Double(0.0)}};
		String[] names2 = {"约束","土地面积","劳动力","农业机械"};
		Object[][] tableContent2 = {{" 最大值", new Double(0.0), new Double(0.0), new Double(0.0)}};
		
		wightTable = new RigidTable(tableContent1, names1);
		restrictTable = new RigidTable(tableContent2, names2);
		TableModel tableModel = restrictTable.getModel();
		tableModel.addTableModelListener(this);
		
		wightTable.setPreferredScrollableViewportSize(new Dimension(400,80));
		restrictTable.setPreferredScrollableViewportSize(new Dimension(400,80));
		JScrollPane JSP1= new JScrollPane(wightTable);
		JScrollPane JSP2= new JScrollPane(restrictTable);
		
		GridLayout grid = new GridLayout(2,1);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(grid);
		jPanel.add(JSP1);
		jPanel.add(JSP2);
		
		rigidPanel = new JPanel();
		rigidPanel.setLayout(new BorderLayout());
		rigidPanel.add(new JPanel(), BorderLayout.NORTH);
		rigidPanel.add(jPanel, BorderLayout.CENTER);
		
		}
		
		jFrame.setContentPane(rigidPanel);
		jFrame.setTitle("添加刚性约束");
		jFrame.pack();
		jFrame.setVisible(true);
	}

	public void singleConstraint(GUIParameter guiParameter) {
		System.out.println("单个变量");
		if ( singlePanel == null ) {
			String[] names = {"序号","种类","最小值","最大值"};
			Object[][] tableContents = new Object[guiParameter.getIo().getSpecies()][4];
			for (int row=0; row<guiParameter.getIo().getSpecies(); row++){
				tableContents[row][0] = guiParameter.getIo().getNo()[row]; 
				tableContents[row][1] = guiParameter.getIo().getSpeciesName()[row];
				tableContents[row][2] = new Double(0.0);
				tableContents[row][3] = new Double(Double.parseDouble(restrictTable.getValueAt(0, 1).toString()));
			}
			singleTable = new MyTable(tableContents, names);
			singleTable.setPreferredScrollableViewportSize(new Dimension(400,400));
			JScrollPane JSP= new JScrollPane(singleTable);
			
			singlePanel = new JPanel();
			singlePanel.add(JSP);
		}
		jFrame.setContentPane(singlePanel);
		jFrame.setTitle("单个变量约束");
		jFrame.pack();
		jFrame.setVisible(true);
	}

	public void elasticConstraint(GUIParameter guiParameter) {
		// TODO Auto-generated method stub
		System.out.println("其他约束");
	}

	public void saveConstraint(GUIParameter guiParameter) {
		// TODO Auto-generated method stub
		System.out.println("保存约束");
	}
	@Override
	public void tableChanged(TableModelEvent e) {
		this.singlePanel = null;
	}


	
}

//自定表格设置不可编辑状态
class RigidTable extends JTable{
	
	public RigidTable(Object[][] tableContents, String[] names){
		super(tableContents, names);
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		if ( column == 0 ) return false;
		else return true;
	}
}