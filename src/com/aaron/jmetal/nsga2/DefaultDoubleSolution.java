package com.aaron.jmetal.nsga2;

/**
 * Solution是个体定义的接口，每个Solution就是种群中一个个体，Solution需要做的是定义染色体编码方案，记录优化函数值，约束目标值，
 * 	其他信息等，默认提供了BinarySoluiton、DoubleSolution、IntegerSoluiton等。
 * 	同样的，如果需要定义新的Solution，你需要继承的是AbstractGenericSolution这个类。
 * 
 */

import java.util.HashMap;
import java.util.List;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;


public class DefaultDoubleSolution extends AbstractGenericSolution<Double, DoubleProblem>  
	implements DoubleSolution{
	
		/** Constructor */  
		public DefaultDoubleSolution(DoubleProblem problem) {  
				super(problem) ;	//会在父类AbstractGenericSolution里设置FarmerProblem信息 

				initializeDoubleVariables();	//随机初始化决策变量  
				initializeObjectiveValues();	//优化目标函数设为0,0  
		}  

		/** Copy constructor */  
		public DefaultDoubleSolution(DefaultDoubleSolution solution) {  
				super(solution.problem) ;  

				for (int i = 0; i < problem.getNumberOfVariables(); i++) {  
					setVariableValue(i, solution.getVariableValue(i));  
				}  

				for (int i = 0; i < problem.getNumberOfObjectives(); i++) {  
					setObjective(i, solution.getObjective(i)) ;  
				}  

				attributes = new HashMap<Object, Object>(solution.attributes) ;  
		}  

	@Override  
	public Double getUpperBound(int index) {  
		return problem.getUpperBound(index);  
	}  

	@Override  
	public Double getLowerBound(int index) {  
		return problem.getLowerBound(index) ;  
	}  

	@Override  
	public DefaultDoubleSolution copy() {  
		return new DefaultDoubleSolution(this);  
	}  

	@Override  
	public String getVariableValueString(int index) {  
		return getVariableValue(index).toString() ;  
	}  

	private void initializeDoubleVariables() {  
		
		for (int i = 0 ; i < problem.getNumberOfVariables(); i++) {  
			Double value = randomGenerator.nextDouble(getLowerBound(i), getUpperBound(i)) ;  
			setVariableValue(i, value) ;  
		}  
	}  

}
