package com.aaron.view.swing;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ViewMenu {
	
	// JFrame和界面切换对象
	private JFrame jFrame = null;
	private ViewSwitch viewSwitch = null;
	
	// 菜单按钮
	private JMenuBar menuBar = null;
	private JMenu fileMenu = null;
	private JMenu dataMenu = null;
	private JMenu resultMenu = null;
	private JMenu resultViewMenu = null;
	private JMenu helpMenu = null;
	
	// 菜单按钮对应的Panel
	private JPanel loadDataPanel = null;
	
	public ViewMenu(JFrame jFrame, ViewSwitch viewSwitch){
		
		this.jFrame = jFrame;
		this.viewSwitch = viewSwitch;
		
		menuBar = new JMenuBar();
		fileMenu = createFileMenu();
		dataMenu = createDataMenu();
		resultMenu = createResultMenu();
		resultViewMenu = createResultViewMenu();
		helpMenu = createHelpMenu();
		
		menuBar.add(fileMenu);
		menuBar.add(dataMenu);
		menuBar.add(resultMenu);
		menuBar.add(resultViewMenu);
		menuBar.add(helpMenu);
		
		loadDataPanel = createLoadDataPanel();
		
		fileMenu.addActionListener(viewSwitch);
		fileMenu.setActionCommand("loadData");
		
		this.jFrame.setTitle("现代生态农场优化决策支持模拟系统");
		this.jFrame.setContentPane(loadDataPanel);
		this.jFrame.setJMenuBar(menuBar);
		this.jFrame.setVisible(true);
	}

	
	
	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("帮助");
		// TODO Auto-generated method stub
		return helpMenu;
	}



	private JMenu createResultViewMenu() {
		JMenu chart = new JMenu("图表");
		JMenuItem item1 = new JMenuItem("柱状图");
		JMenuItem item2 = new JMenuItem("饼图");
		chart.add(item1);
		chart.add(item2);
		return chart;
	}


	/**
	 * 运行求解
	 * @return
	 */
	private JMenu createResultMenu() {
		JMenu resultMenu = new JMenu("运行");
		JMenuItem item1 = new JMenuItem("运行数据");
		JMenuItem item2 = new JMenuItem("饼状图");
		JMenuItem item3 = new JMenuItem("柱状图");
		
		item1.addActionListener(viewSwitch);
		item1.setActionCommand("runData");
		item2.addActionListener(viewSwitch);
		item2.setActionCommand("pieData");
		item3.addActionListener(viewSwitch);
		item3.setActionCommand("barData");
		
		resultMenu.add(item1);
//		resultMenu.add(item2);
//		resultMenu.add(item3);
		return resultMenu;
	}



	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("文件");
		
		JMenuItem item1 = new JMenuItem("新建");
		JMenuItem item2 = new JMenuItem("打开");
		JMenuItem item3 = new JMenuItem("保存");
		JMenuItem item4 = new JMenuItem("另存为");
		JMenuItem item5 = new JMenuItem("打印");
		JMenuItem item6 = new JMenuItem("退出");
		
		item1.addActionListener(this.viewSwitch);
		item1.setActionCommand("newFile");
		item2.addActionListener(this.viewSwitch);
		item2.setActionCommand("openFile");
		item3.addActionListener(this.viewSwitch);
		item3.setActionCommand("saveFile");
		item4.addActionListener(this.viewSwitch);
		item4.setActionCommand("saveAsFile");
		item5.addActionListener(this.viewSwitch);
		item5.setActionCommand("printFile");
		item6.addActionListener(this.viewSwitch);
		item6.setActionCommand("exitFile");
		
		fileMenu.add(item1);
		fileMenu.add(item2);
		fileMenu.add(item3);
		fileMenu.add(item4);
		fileMenu.add(item5);
		fileMenu.add(item6);
		
		return fileMenu;
	}



	private JMenu createDataMenu() {
		JMenu dataMenu = new JMenu("数据");
		
		JMenuItem item1 = new JMenuItem("刚性约束");
		JMenuItem item2 = new JMenuItem("单个变量");
		JMenuItem item3 = new JMenuItem("其他约束");
		JMenuItem item4 = new JMenuItem("保存参数");
		
		item1.addActionListener(viewSwitch);
		item1.setActionCommand("rigidConstraint");
		item2.addActionListener(viewSwitch);
		item2.setActionCommand("singleConstraint");
		item3.addActionListener(viewSwitch);
		item3.setActionCommand("elasticConstraint");
		item4.addActionListener(viewSwitch);
		item4.setActionCommand("saveConstraint");
		
		dataMenu.add(item1);
		dataMenu.add(item2);
		dataMenu.add(item3);
		dataMenu.add(item4);
		
		return dataMenu;
	}



	private JPanel createLoadDataPanel() {
		// ====================== 创建
		JPanel loadData = new JPanel();
		Icon icon = new ImageIcon("./data/add.png");
		JLabel jLabel = new JLabel();
		// ====================== 设置布局
		loadData.setLayout(new BorderLayout());

		jLabel.setIcon(icon);
		jLabel.setHorizontalAlignment(JLabel.CENTER);
		
		loadData.add(new JLabel());
		loadData.add(jLabel);
		
		jLabel.addMouseListener(this.viewSwitch);
		
		return loadData;
	}
}  
