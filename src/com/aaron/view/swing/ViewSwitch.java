package com.aaron.view.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.aaron.data.parameter.GUIParameter;
import com.aaron.data.parameter.IOParameter;

public class ViewSwitch implements ActionListener, MouseListener, TableModelListener {
	private JFrame VF;
	private ViewMenu vm;
	private String path = null;
	// 文件 按钮
	private FileView fileView = null;
	private JTable tableInfo = null;
	private IOParameter ioParameter = null;
	private GUIParameter guiParameter = null;
	private boolean fileFlag = false;
	
	// 数据 按钮
	private DataView dataView = null;
	private boolean dataFlag = false;
	
	// 运行 按钮
	private ResultView resultView = null;
	
	public ViewSwitch( JFrame vf ){
		this.VF = vf;
		this.vm = new ViewMenu(vf, this);
		this.dataView = new DataView(vf);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// 文件菜单按钮
		if (e.getActionCommand().equals("newFile")) {
			newFile();
		} else if (e.getActionCommand().equals("openFile")) {
			openFile();
		} else if (e.getActionCommand().equals("saveFile")) {
			saveFile();
		} else if (e.getActionCommand().equals("saveAsFile")) {
			saveAsFile();
		} else if (e.getActionCommand().equals("printFile")) {
			printFile();
		} else if (e.getActionCommand().equals("exitFile")){
			exitFile();
		}
		
		// 数据菜单按钮 - 优先判断年数据按钮有没有准备好 fileFlag = true;
		if (e.getActionCommand().equals("rigidConstraint")) {
			if (fileFlag == false) {
				JOptionPane.showMessageDialog(this.VF, "请打开或新建文件后添加约束");
				return;
			}
			dataView.rigidConstraint(this.guiParameter);
			dataFlag = true;
		} else if (e.getActionCommand().equals("singleConstraint")) {
			if (dataFlag == false) {
				JOptionPane.showMessageDialog(this.VF, "请优先添加刚性约束");
				return;
			}
			dataView.singleConstraint(this.guiParameter);
		} else if (e.getActionCommand().equals("elasticConstraint")) {
			if (dataFlag == false) {
				JOptionPane.showMessageDialog(this.VF, "请优先添加刚性约束");
				return;
			}
			dataView.elasticConstraint(this.guiParameter);
		} else if (e.getActionCommand().equals("saveConstraint")) {
			if (dataFlag == false) {
				JOptionPane.showMessageDialog(this.VF, "请优先添加刚性约束");
				return;
			}
			dataView.saveConstraint(this.guiParameter);
		}
		
		// 运行按钮
		if (e.getActionCommand().equals("runData")) {
			resultView = new ResultView(VF);
		} else if (e.getActionCommand().equals("pieData")) {
			if (resultView == null) {
				JOptionPane.showMessageDialog(this.VF, "请运行数据后生成饼图");
				return;
			}
			resultView.showPie();
		} else if (e.getActionCommand().equals("barData")) {
			if (resultView == null ) {
				JOptionPane.showMessageDialog(this.VF, "请运行数据后生成柱状图");
				return;
			}
			resultView.showBar();
		}
		
		// 帮助
	}

	
	
	
	private void exitFile() {
		// TODO Auto-generated method stub
		System.out.println("退出文件");
	}

	private void printFile() {
		// TODO Auto-generated method stub
		System.out.println("打印文件");
	}

	private void saveAsFile() {
		// TODO Auto-generated method stub
		System.out.println("另存为文件");
	}

	private void saveFile() {
		System.out.println("保存文件");
		// 不覆盖原来原参数
		if ( this.tableInfo == null || this.ioParameter == null ) {
			int i = JOptionPane.showConfirmDialog(this.VF, "操作顺序有误，请选择新建或打开文件", "欢迎使用", 0);
			if( i == 0 ) return;
		} else {
			try{
				for (int row=0; row<this.ioParameter.getSpecies(); row++){
					this.ioParameter.setCostTarget(row, Double.valueOf(this.tableInfo.getValueAt(row, 2).toString()));
					this.ioParameter.setMarginTarget(row, Double.valueOf(this.tableInfo.getValueAt(row, 3).toString()));
					this.ioParameter.setOutputTarget(row, Double.valueOf(this.tableInfo.getValueAt(row, 4).toString()));
					this.ioParameter.setChemicalTarget(row, Double.valueOf(this.tableInfo.getValueAt(row, 5).toString()));
					this.ioParameter.setLabourRestrict(row, Double.valueOf(this.tableInfo.getValueAt(row, 6).toString()));
					this.ioParameter.setMachineRestrcit(row, Double.valueOf(this.tableInfo.getValueAt(row, 7).toString()));
					this.ioParameter.setLandRestrict(row, Double.valueOf(this.tableInfo.getValueAt(row, 8).toString()));
					this.ioParameter.setFertilizerInput(row, Double.valueOf(this.tableInfo.getValueAt(row, 9).toString()));
					this.ioParameter.setFertilizerOutput(row, Double.valueOf(this.tableInfo.getValueAt(row, 10).toString()));
				}
				
				fileView = new FileView(this.VF, this.ioParameter);
				tableInfo = fileView.getTable();
				tableInfo.setPreferredScrollableViewportSize(new Dimension(800, 400));
				// 将表格加入到滚动条组件中
				JScrollPane scrollPane = new JScrollPane(tableInfo);
				
				this.VF.setContentPane(scrollPane);
				
				// 再将滚动条组件添加到中间容器中
				this.VF.setTitle("现代生态农场优化决策支持模拟系统");
				this.VF.pack();
				this.VF.setVisible(true);
				// 合并有机肥
				this.guiParameter.loadFertilizerRestrict();
				this.fileFlag = true;
			} catch (Exception e) {
				int i = JOptionPane.showConfirmDialog(this.VF, "输入数据有误，请检查数据后保存！", "欢迎使用", 0);
			}
		}
	}

	private void newFile() {
		// TODO Auto-generated method stub
		System.out.println("新建文件");
	}

	private void openFile() {
		System.out.println("打开文件");
	     JFileChooser jfc=new JFileChooser();  
	     jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
	     jfc.showDialog(this.VF, "选择");  
	     File file=jfc.getSelectedFile();  
	     if(file.isDirectory()){  
				int i = JOptionPane.showConfirmDialog(this.VF, "请选择scv文件", "欢迎使用", 0);
				if( i == 0 ) openFile();
         }else {  
	        System.out.println("文件:"+file.getAbsolutePath()); 
	        path = file.getAbsolutePath();
	        if ( path == null ) return;
	        this.ioParameter = new IOParameter(path);
	        this.guiParameter = new GUIParameter(this.ioParameter);
	        
	        if ( this.ioParameter != null ) fileFlag = true;
	        
	        fileView = new FileView(this.VF, this.ioParameter);
	        
	        tableInfo = fileView.getTable();
		    //为表格添加监听器
		    TableModel tableModel = tableInfo.getModel();
		    tableModel.addTableModelListener(this);
	        
	        tableInfo.setPreferredScrollableViewportSize(new Dimension(500, 400));
	        TableAtrribute.setTableHeaderWidth(tableInfo);
	        
			// 将表格加入到滚动条组件中
		    JScrollPane scrollPane = new JScrollPane(tableInfo);
		    
		    this.VF.setContentPane(scrollPane);
		    
		    // 再将滚动条组件添加到中间容器中
		    this.VF.setTitle("现代生态农场优化决策支持模拟系统");
		    this.VF.pack();
		    this.VF.setVisible(true);
	     }  
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		openFile();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println(e.getFirstRow()+" : "+e.getColumn()+" = > "+"changed");
		this.VF.setTitle("现代生态农场优化决策支持模拟系统");
		this.fileFlag = false;
	}


}
