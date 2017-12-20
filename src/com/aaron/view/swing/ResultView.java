package com.aaron.view.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;
import org.uma.jmetal.solution.DoubleSolution;

import com.aaron.data.parameter.GUIParameter;
import com.aaron.data.parameter.Parameter;
import com.aaron.jmetal.nsga2.FarmerSolutionInterface;

public class ResultView {
	private GUIParameter guiParameter = null;
	private FarmerSolutionInterface farmerSolution = null;
	private DoubleSolution bestSolution = null;
	private boolean isRun = false;
	// 显示正在运行界面
	int width = 500;
	int height = 360;
	// 提取出的结果
	List<String> names = null;
	List<Double> contents = null;
	
	public ResultView() {
		this.guiParameter = Parameter.DeserializeParameter();
		testGUIParameter();
//        ImageIcon icon = new ImageIcon("./data/running.png");  
//        JLabel label = new JLabel(icon);
//        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
//        JPanel ImagePanel = new JPanel();
//        ImagePanel.add(label);
	}
	
	public void showresult(JFrame jFrame) {
		farmerSolution = new FarmerSolutionInterface();
		long maxTime = farmerSolution.getEvaluationTime();
		bestSolution = farmerSolution.getBestSolution();
		testResult();
		showResultTable(bestSolution, jFrame);
		isRun = true;
	}

	public void showWaitImage(JFrame jFrame) {
		// 正在运行图片
		JPanel RuningImage = new JPanel(){  
			private static final long serialVersionUID = -5430457722902501633L;
			protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon("./data/running.png");  
                Image img = icon.getImage(); 
                g.drawImage(img, 0, 0, width,  
                		height, icon.getImageObserver());  
//                this.setSize(icon.getIconWidth(), icon.getIconHeight()); 
            }  
        }; 
        jFrame.setContentPane(RuningImage);
        jFrame.setVisible(true);
        new Thread() {
        		public void run() {
        			showresult(jFrame);
        		}
        }.start();
	}

	private void showResultTable(DoubleSolution bestSolution, JFrame jFrame) {
		names = new ArrayList<String>();
		contents = new ArrayList<Double>();
		double temp = 1.0;
		names.add("小麦-玉米");
		contents.add(Math.ceil(bestSolution.getVariableValue(0) * temp));
		for (int i=2; i<bestSolution.getNumberOfVariables(); i++) {
			if(this.guiParameter.getIo().getLandRestrict()[i] < 1.0000) 
				temp = this.guiParameter.getIo().getLandRestrict()[i];
			if (bestSolution.getVariableValue(i)*temp > 10.0) {
				names.add(this.guiParameter.getIo().getSpeciesName()[i]);
				contents.add(Math.ceil(bestSolution.getVariableValue(i) * temp));
			}
		}
		
		Object[] tableNames = names.toArray();
		Object[][] tableContents = new Object[1][contents.size()];
		for (int j=0; j<contents.size(); j++) {
			tableContents[0][j] = contents.get(j);
		}
		
		JTable resultTable = new JTable(tableContents, tableNames);
		resultTable.setPreferredScrollableViewportSize(new Dimension(500, 320));
		
        TableAtrribute.setTableHeaderWidth(resultTable);
        
		// 将表格加入到滚动条组件中
	    JScrollPane scrollPane = new JScrollPane(resultTable);
	    
	    jFrame.setContentPane(scrollPane);
	    // 再将滚动条组件添加到中间容器中
	    jFrame.pack();
	    jFrame.setVisible(true);
	}

	public void showBar(JFrame jFrame) {
		if(isRun == false) { 
			JOptionPane.showMessageDialog(jFrame, "请运行数据后生成柱状图！");
			return;
		}
		// TODO Auto-generated method stub
		System.out.println("生成柱状图");
		   JFreeChart barchart = ChartFactory.createBarChart("作物占地面积", // chart title   
	                "种类", // domain axis label   
	                "面积", // range axis label   
	                createBarDataset(), // data   
	                PlotOrientation.VERTICAL, // 图标方向   
	                false, // 是否显示legend   
	                true, // 是否显示tooltips   
	                false // 是否显示URLs   
	        );   
	       // 创建主题样式       
	        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");       
	        // 设置标题字体       
	        standardChartTheme.setExtraLargeFont(new Font("宋书", Font.BOLD, 15));       
	        // 设置图例的字体       
	        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 10));       
	        // 设置轴向的字体       
	        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 10));       
	        // 应用主题样式       
	        ChartFactory.setChartTheme(standardChartTheme);
	        
	        CategoryPlot plot = (CategoryPlot) barchart.getPlot();
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// 倾斜45度
//		barchart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,  
//                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);  
        jFrame = new ChartFrame("BarChart", barchart);  
        jFrame.pack();  
        RefineryUtilities.centerFrameOnScreen(jFrame);  
        jFrame.setVisible(true);  
	}
	

	private DefaultCategoryDataset createBarDataset() {
		
		DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();
		
		for ( int i=0; i<names.size(); i++ ) {
			if (this.guiParameter.getIo().getLandRestrict()[i] < 1.0 && this.guiParameter.getIo().getLandRestrict()[i] > 0.0) 
			{
				barDataSet.addValue(contents.get(i), "" ,names.get(i));
			}
			else
			{
				barDataSet.addValue(contents.get(i), "" ,names.get(i));
			}
		}
		return barDataSet;
	}

	private DefaultPieDataset createPieDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset(); 
        Double sum = 0.0;
        Double other = 0.0;
        for (int j=0; j<names.size(); j++) sum += contents.get(j);
        for (int i=0; i<names.size(); i++) {
        		// 0.5%以下的全部设置为其他
        		if((contents.get(i)/sum) > 0.005) dataset.setValue(names.get(i), contents.get(i));
        		else other += contents.get(i);
        }
        if (other > 0.0) dataset.setValue("其他", other);
        return dataset;  
	}

	public void showPie(JFrame jFrame) {
		if(isRun == false) { 
			JOptionPane.showMessageDialog(jFrame, "请运行数据后生成饼图！");
			return;
		}
		// TODO Auto-generated method stub
		System.out.println("生成饼状图");
        JFreeChart pieChart = ChartFactory.createPieChart("作物占地比重", createPieDataset(), true, true, false);  
        // 设置标题字体  
        pieChart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));  
        // 设置图例类别字体  
        pieChart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 14));  
        
        
        PiePlot pieplot = (PiePlot) pieChart.getPlot(); //通过JFreeChart 对象获得  
        pieplot.setNoDataMessage("无数据可供显示！"); // 没有数据的时候显示的内容  
        DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题,表示小数点后保留两位。  
        NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象  
        StandardPieSectionLabelGenerator sp = new StandardPieSectionLabelGenerator(  
                "{0}:{2}", nf, df);//获得StandardPieSectionLabelGenerator对象,生成的格式，{0}表示section名，{1}表示section的值，{2}表示百分比。可以自定义  
        pieplot.setLabelGenerator(sp);//设置饼图显示百分比 
        
        jFrame = new ChartFrame("PieChart", pieChart);  
        jFrame.pack();  
        RefineryUtilities.centerFrameOnScreen(jFrame);  
        jFrame.setVisible(true);  
	}
	
	public void testResult() {

		System.out.println("---------------------Objectives---------------------");
		for (int i=0; i<bestSolution.getNumberOfObjectives(); i++) {
			double objective = bestSolution.getObjective(i) >= 0 ? bestSolution.getObjective(i) : -bestSolution.getObjective(i);
			objective = objective / guiParameter.getWeight()[i];
			System.out.println(" Objective "+ (i+1) + " Value " +objective);
		}
		System.out.println("---------------------variables---------------------");
		double sum = 0;
		for (int i =0; i<bestSolution.getNumberOfVariables(); i++) {
			double temp = Double.parseDouble(bestSolution.getVariableValueString(i)) * guiParameter.getIo().getLandRestrict()[i];
			System.out.println( " == variable "+ (i+1) + " Value " + temp);
			sum += temp;
		}
		System.out.println("==== sum "+ sum);
	}
	
	public void testGUIParameter() {
		System.out.println("number of objectives : " + guiParameter.getNumberOfObjectives());
		System.out.println("number of variables : " + guiParameter.getNumberOfVariables());
		System.out.println("number of constraints : " + guiParameter.getNumberOfConstraints());
		
		System.out.println("------------------lowerLimit-----------------------");
		for (int i=0; i<guiParameter.getNumberOfVariables(); i++) {
			System.out.print("  "+guiParameter.getMinLand()[i]);
		}
		System.out.println();
		System.out.println("-------------------upperLimit---------------------");
		for (int i=0; i<guiParameter.getNumberOfVariables(); i++) {
			System.out.print("  "+guiParameter.getMaxLand()[i]);
		}
		System.out.println();
		System.out.println("------------------------目标权重-----------------------------");
		for ( int i=0; i<guiParameter.getNumberOfObjectives(); i++) {
			System.out.print("  "+guiParameter.getWeight()[i]);
		}
		System.out.println();
		System.out.println("------------------------限制-----------------------------");
		System.out.println("-------total land "+guiParameter.getTotalMaxLands());
		System.out.println("-------total Labour "+guiParameter.getTotalMaxLabour());
		System.out.println("-------total michine "+guiParameter.getTotalMaxMachine());
		System.out.println(" ---------------有机肥限制 ----------------------");
		for (int i=0; i<guiParameter.getFertilizerRestrict().length; i++) {
			System.out.print(" "+guiParameter.getFertilizerRestrict()[i]);
		}
		System.out.println();
		System.out.println(" ---------------等式限制---------------------");
		for (int i=0; i<guiParameter.getEqualityConstraints().size(); i++) {
			for (int j=0; j<guiParameter.getEqualityConstraints().get(0).length; j++) {
				System.out.print("  "+guiParameter.getEqualityConstraints().get(i)[j]);
			}
			System.out.println("---");
		}
		System.out.println();
		System.out.println(" ---------------不等式限制--------------------");
		for (int i=0; i<guiParameter.getInequalityConstraints().size(); i++) {
			for (int j=0; j<guiParameter.getInequalityConstraints().get(0).length; j++) {
				System.out.print("  "+guiParameter.getInequalityConstraints().get(i)[j]);
			}
			System.out.println("---");
		}
		System.out.println("=============================配置参数完毕=====================================");
	}
}


