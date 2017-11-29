package com.aaron.view.swing;

import java.util.List;

import javax.swing.JFrame;

import org.uma.jmetal.solution.DoubleSolution;

import com.aaron.data.parameter.GUIParameter;
import com.aaron.data.parameter.Parameter;
import com.aaron.jmetal.nsga2.FarmerSolutionInterface;

public class ResultView {
	
	private JFrame jFrame = null;
	private GUIParameter guiParameter = null;
	
	private FarmerSolutionInterface farmerSolution = null;
	private DoubleSolution bestSolution = null;
	
	
	public ResultView(JFrame vF) {
		this.jFrame = vF;
		this.guiParameter = Parameter.DeserializeParameter();
		testGUIParameter();
		farmerSolution = new FarmerSolutionInterface();
		bestSolution = farmerSolution.getBestSolution();
		testResult();
	}

	public void showBar() {
		// TODO Auto-generated method stub
		System.out.println("生成柱状图");
	}

	public void showPie() {
		// TODO Auto-generated method stub
		System.out.println("生成饼状图");
	}
	
	public void testResult() {
//		DoubleSolution bestSolution = farmerSolution.getNonDominatedSolutions().get(0);
//		double bestSolutionValue = 0;
//		for (int j=0; j<bestSolution.getNumberOfObjectives(); j++) {
//			bestSolutionValue += bestSolution.getObjective(j);
//		}
//		
//		List<DoubleSolution> doubleSoltions = farmerSolution.getNonDominatedSolutions();
//		for (int i=0; i<doubleSoltions.size(); i++) {
//			double tempSolutionValue = 0;
//			for (int j=0; j<bestSolution.getNumberOfObjectives(); j++) {
//				tempSolutionValue += bestSolution.getObjective(j);
//			}
//			if (bestSolutionValue > tempSolutionValue) {
//				bestSolution = doubleSoltions.get(i);
//				bestSolutionValue = tempSolutionValue;
//			}
//		}
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
