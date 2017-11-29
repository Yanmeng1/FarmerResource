package com.aaron.jmetal.nsga2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.solutionattribute.impl.NumberOfViolatedConstraints;
import org.uma.jmetal.util.solutionattribute.impl.OverallConstraintViolation;

import com.aaron.data.parameter.GUIParameter;
import com.aaron.data.parameter.Parameter;

public class FarmerProblem extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution> {
	
	// 用OverallConstraintsViolation和NuberOfViolatedContraints工具来为solution设置限制条件
	public OverallConstraintViolation<DoubleSolution> overallConstraintViolationDegree;
	public NumberOfViolatedConstraints<DoubleSolution> numberOfViolatedConstraints;
	
	// 用反序列化会对象Parameter里的参数设置
	GUIParameter guiParameter;
	
	/**
	 * 面向抽象调用的方法
	 */
	public FarmerProblem(){
		this(44);
	}
	public FarmerProblem(int numberOfVariables) {
		
		guiParameter = Parameter.DeserializeParameter();
		
		// 设置问题的目标函数数量、变量数量、限制数量 以及 设置问题的名称
		this.setNumberOfObjectives(guiParameter.getNumberOfObjectives());
		this.setNumberOfVariables(guiParameter.getNumberOfVariables());
		this.setNumberOfConstraints(guiParameter.getNumberOfConstraints());
		this.setName("FarmerProblem");
		
		// 设定变量定义域
		List<Double> lowerLimit = Arrays.asList(guiParameter.getMinLand());
		List<Double> upperLimit = Arrays.asList(guiParameter.getMaxLand());
		for ( int i=0; i < this.getNumberOfVariables(); i++) {
			this.setLowerLimit(lowerLimit);
			this.setUpperLimit(upperLimit);
		}
		
		// 创建限制对象的工具
		overallConstraintViolationDegree = new OverallConstraintViolation<DoubleSolution>();
		numberOfViolatedConstraints = new NumberOfViolatedConstraints<DoubleSolution>();
	}
	
	/**
	 * 目标函数设定 - 通过 solution 目标函数的设定传至全局
	 * @param solution
	 */
	@Override
	public void evaluate(DoubleSolution solution) {
		double[] Objectives = new double[this.getNumberOfObjectives()];
		double[] variables = new double[this.getNumberOfVariables()];
		// 取数变量准备好组合！
		for ( int i = 0; i < this.getNumberOfVariables(); i++ ){
			variables[i] = solution.getVariableValue(i);
		}
		
		// 添加四个目标函数 - 默认 min f(x)  利益0.7，成本0.15，化肥0.1，粮食0.05
		Objectives[0] = 0;	//利益最大化 -
		Objectives[1] = 0;	//成本最小化 +
		Objectives[2] = 0;	//化肥最小化 +
		double fertilizerRset = 0;
		Objectives[3] = 0;	//粮食产量最大化 -
		for ( int i=0; i<this.getNumberOfVariables(); i++) {
			Objectives[0] -= guiParameter.getIo().getMarginTarget()[i] * variables[i];
			Objectives[1] += guiParameter.getIo().getCostTarget()[i] * variables[i];
			Objectives[2] += guiParameter.getIo().getChemicalTarget()[i] * variables[i];
			fertilizerRset += guiParameter.getFertilizerRestrict()[i] * variables[i];
			Objectives[3] -= guiParameter.getIo().getOutputTarget()[i] * variables[i];
		}
		Objectives[2] -= fertilizerRset;
		
//		for (int i=0 ; i<this.getNumberOfObjectives(); i++) {
//			Objectives[i] = 0-Objectives[i];
//		}
		
		// 向方案solution 中添加目标函数
		for ( int i=0; i<this.getNumberOfObjectives(); i++ ) {
			solution.setObjective(i, guiParameter.getWeight()[i] * Objectives[i]);
		}
	}
	/**
	 * 限制条件设定 - 通过 solution 将限制设定至全局
	 * @param solution
	 */
	@Override
	public void evaluateConstraints(DoubleSolution solution) {
		// 限制
		double[] constraint = new double[this.getNumberOfConstraints()];
		double[] variables = new double[this.getNumberOfVariables()];
		// 取数变量准备好组合！
		for ( int i = 0; i < this.getNumberOfVariables(); i++ ){
			variables[i] = solution.getVariableValue(i);
		}
		
		// 添加刚性约束 土地限制、劳动力限制、农业机械限制、肥料限制
		constraint[0] = guiParameter.getTotalMaxLands();
		constraint[4] = 0;
		constraint[1] = guiParameter.getTotalMaxLabour();
		constraint[2] = guiParameter.getTotalMaxMachine();
		constraint[3] = 0;
		guiParameter.loadFertilizerRestrict();
		for (int i=0; i<this.getNumberOfVariables(); i++){
			// sum(lands) == maxLand
			constraint[0] -= guiParameter.getIo().getLandRestrict()[i] * variables[i];
			constraint[1] -= guiParameter.getIo().getLabourRestrict()[i] * variables[i];
			constraint[2] -= guiParameter.getIo().getMachineRestrcit()[i] * variables[i];
			constraint[3] += guiParameter.getFertilizerRestrict()[i] * variables[i];
		}
		constraint[4] = 0 - constraint[0];
		
		// 添加弹性约束
		if ( this.getNumberOfConstraints() > 5) {
			for (int i=5; i<this.getNumberOfConstraints(); i++) {
				constraint[i] = guiParameter.getInequalityConstraints().get(i-5)[this.getNumberOfVariables()];
				for (int j=0; j<this.getNumberOfVariables(); j++) {
					constraint[i] += (guiParameter.getInequalityConstraints().get(i-5)[j] * variables[j]);
				}
			}
		}
		
		// 记录该方案的约束情况
		double overallConstraintViolation = 0.0;
		int violatedConstraints = 0;
		for( int i=0; i<this.getNumberOfConstraints(); i++) {
			if ( constraint[i] < 0 ) {
				overallConstraintViolation += constraint[i];
				violatedConstraints++;
			}
		}
		
		// 为该方案solution记录其约束情况
		overallConstraintViolationDegree.setAttribute(solution, overallConstraintViolation);
		numberOfViolatedConstraints.setAttribute(solution, violatedConstraints);
	}

}
/**
 * DoubleSolution 在 AbstractDoubleProblem里进行注册
 * AbstractDoubleProblem override了createSolution()方法。
 * @Override  
 * public DoubleSolution createSolution() {  
 * 		return new DefaultDoubleSolution(this)  ;  
 * }  
 */

