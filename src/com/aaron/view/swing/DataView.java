package com.aaron.view.swing;
/**
 * 菜单 ： 数据面板
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.aaron.data.parameter.GUIParameter;
import com.aaron.data.parameter.Parameter;

public class DataView implements TableModelListener, ActionListener {
	
	private JFrame jFrame;
	// 刚性约束界面信息
	private JPanel rigidPanel = null;
	private JTable wightTable = null;
	private JTable restrictTable = null;
	// 单个变量界面约束信息
	private JPanel singlePanel = null;
	private JTable singleTable = null;
	// 其他弹性界面约束信息
	private JPanel elasticPanel = null;
	private JTable elasticTable = null;
	private DefaultTableModel defaultModel = null;
	
	public DataView ( JFrame jFrame ) {
		this.jFrame = jFrame;
	}
	/**
	 * 数据-刚性约束
	 * @param guiParameter
	 */
	public void rigidConstraint(GUIParameter guiParameter) {
		System.out.println("刚性约束");
		if(rigidPanel == null) {
		String[] names1 = {"目标函数","利润","投入成本","化肥","粮食产量"};
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
//		jFrame.setTitle("现代农场资源配置系统");
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	/**
	 * 数据-单个变量
	 * @param guiParameter
	 */
	public void singleConstraint(GUIParameter guiParameter) {
		System.out.println("单个变量");
		if ( singlePanel == null ) {
			String[] names = {"序号","种类","最小值","最大值"};
			Object[][] tableContents = new Object[guiParameter.getIo().getSpecies()][4];
			guiParameter.setTotalMaxLands(Double.parseDouble(restrictTable.getValueAt(0, 1).toString()));
			for (int row=0; row<guiParameter.getIo().getSpecies(); row++){
				tableContents[row][0] = guiParameter.getIo().getNo()[row]; 
				tableContents[row][1] = guiParameter.getIo().getSpeciesName()[row];
				tableContents[row][2] = new Double(0.0);
				tableContents[row][3] = guiParameter.getMaxLand()[row ];
			}
			singleTable = new MyTable(tableContents, names);
			singleTable.setPreferredScrollableViewportSize(new Dimension(400,400));
			JScrollPane JSP= new JScrollPane(singleTable);
			
			singlePanel = new JPanel();
			singlePanel.add(JSP);
		}
		jFrame.setContentPane(singlePanel);
//		jFrame.setTitle("现代农场资源配置系统");
		jFrame.pack();
		jFrame.setVisible(true);
	}
	/**
	 * 数据-其他约束
	 * @param guiParameter
	 */
	public void elasticConstraint(GUIParameter guiParameter) {
		System.out.println("其他约束");
		if( elasticPanel == null ) {
			String[] names = new String[ guiParameter.getIo().getSpecies() + 2 ];
			// 设置第一个和最后一个字段为约束类型和常数
			names[0] = "约束类型";
			names[names.length-1] = "常数";
//		    System.arraycopy(guiParameter.getIo().getSpeciesName(), 0, names, 1, guiParameter.getIo().getSpecies());
		    for(int i=1; i<names.length-1; i++) {
		    	names[i] = " "+i+" "+ guiParameter.getIo().getSpeciesName()[i-1];
		    }
			Object[][] tableContents = new Object[0][ guiParameter.getIo().getSpecies() + 2 ]; 
			defaultModel = new DefaultTableModel(tableContents, names);
			elasticTable = new JTable(defaultModel);
			// 设置表格列的宽度值
			TableAtrribute.setTableHeaderWidth(elasticTable);
			elasticTable.setPreferredScrollableViewportSize(new Dimension(500,300));
			JScrollPane jScrollpane = new JScrollPane(elasticTable);
			jScrollpane.setViewportView(elasticTable);
//			jScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//			jScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			JPanel buttonPanel = new JPanel();
			JButton addButton = new JButton("添加行");
			addButton.setActionCommand("addline");
			addButton.addActionListener(this);
			JButton deleteButton = new JButton("删除所选行");
			deleteButton.setActionCommand("deleteline");
			deleteButton.addActionListener(this);
			
			buttonPanel.add(addButton);
			buttonPanel.add(deleteButton);
			
			Container contentPane = jFrame.getContentPane();
			elasticPanel = new JPanel();
			elasticPanel.setLayout(new BorderLayout());
			elasticPanel.add(buttonPanel, BorderLayout.NORTH);
			elasticPanel.add(jScrollpane, BorderLayout.CENTER);
			contentPane.add(elasticPanel);
		}
		jFrame.setContentPane(elasticPanel);
//		jFrame.setTitle("现代农场资源配置系统");
		jFrame.pack();//自适应组件大小
		jFrame.setVisible(true);
	}

	/**
	 * 数据-保存参数
	 * @param guiParameter
	 */
	public void saveConstraint(GUIParameter guiParameter) {
		// TODO Auto-generated method stub
		System.out.println("保存参数");
		
		// 将file文件中的邮寄费约束添加到GUIParameter中
		guiParameter.loadFertilizerRestrict();
		
		// 将面板上的数据装载到GUIParameter中
		
		// 获取weightTable和restrict表格参数
		for ( int i = 0 ; i < guiParameter.getWeight().length; i++) {
			guiParameter.setWeight(i, Double.valueOf(this.wightTable.getValueAt(0, i+1).toString()));
		}
		guiParameter.setTotalMaxLands(Double.valueOf(this.restrictTable.getValueAt(0, 1).toString()));
		guiParameter.setTotalMaxLabour(Double.valueOf(this.restrictTable.getValueAt(0, 2).toString()));
		guiParameter.setTotalMaxMachine(Double.valueOf(this.restrictTable.getValueAt(0, 3).toString()));


		// 获取singleTable面板参数
		if (singleTable != null) {
			for ( int i=0; i<guiParameter.getNumberOfVariables(); i++) {
				guiParameter.setMinLandByNo(i, Double.valueOf(this.singleTable.getValueAt(i, 2).toString()));
				guiParameter.setMaxLandByNo(i, Double.valueOf(this.singleTable.getValueAt(i, 3).toString()));
			}
		}
		// 获取elasticalTable参数面板信息
		
		// 每次的点保存的时候置空 guiParameter中的约束，重新获取面板中的约束
		guiParameter.setInequalityConstraintsEmpty();
		guiParameter.setEqualityConstriantsEmpty();
		
		if ( elasticTable != null && elasticTable.getRowCount() > 0) {
			int rowNum = elasticTable.getRowCount();
			// 单行添加
			for ( int i=0; i<rowNum; i++ ) {
				double[] constraints = new double[guiParameter.getNumberOfVariables() + 1];
				for(int j=0; j<constraints.length; j++) 
				{
					constraints[j] = Double.valueOf(this.elasticTable.getValueAt(i, j+1).toString());
				}
//				System.out.println(elasticTable.getValueAt(i, 0).toString());
				if ( elasticTable.getValueAt(i, 0).toString().equals(" 0.0 < ")) {
					System.out.println(" 0.0 < 添加不等式约束");
					guiParameter.getInequalityConstraints().add(constraints);
				}
				if ( elasticTable.getValueAt(i, 0).toString().equals(" 0.0 = ")) {
					System.out.println(" 0.0 = 添加等式约束");
					guiParameter.getEqualityConstraints().add(constraints);
				}
			}
			// 将等式约束转换为不等式约束
			if( guiParameter.getNumberOfEqualityConstraints() > 0 ) {
				for (int i=0; i<guiParameter.getNumberOfEqualityConstraints(); i++)
				{
					double[] equalConstraint1 = new double[guiParameter.getNumberOfVariables()+1];
					double[] equalConstraint2 = new double[guiParameter.getNumberOfVariables()+1];
					for (int j=0; j<guiParameter.getNumberOfVariables()+1; j++) {
						equalConstraint1[j] = guiParameter.getEqualityConstraints().get(i)[j];
						if (equalConstraint1[j] == 0.0) equalConstraint2[j] = 0.0;
						else equalConstraint2[j] = -guiParameter.getEqualityConstraints().get(i)[j];
					}
					guiParameter.getInequalityConstraints().add(equalConstraint1);
					guiParameter.getInequalityConstraints().add(equalConstraint2);
				}
			}
		}
		// 将guiParameter序列化
		Parameter.SerializeGUIParameter(guiParameter);
//		this.jFrame.setTitle("现代农场资源配置系统");
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		this.singlePanel = null;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getActionCommand().equals("addline")) {
			System.out.println("添加行");
			// 在表格中添加JcomboBox
			String[] constraintType = new String[]{" 0.0 < "," 0.0 = "};
			JComboBox jcb = new JComboBox(constraintType);
			TableColumn typeColumn = elasticTable.getColumnModel().getColumn(0);
			typeColumn.setCellEditor(new DefaultCellEditor(jcb));
			
			Object[] rowContent = new Object[defaultModel.getColumnCount()];
			rowContent[0] = "点击选择";
			
			for (int i = 1; i<rowContent.length; i++) rowContent[i] = new Double(0.0);
			defaultModel.addRow(rowContent);
			
		}
		
		if( e.getActionCommand().equals("deleteline")) {
			System.out.println("删除行");
			int numrow = elasticTable.getSelectedRow();
			if (numrow == -1) JOptionPane.showMessageDialog(this.jFrame,"请选择要删除的行!");
			else defaultModel.removeRow(numrow);
		}
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