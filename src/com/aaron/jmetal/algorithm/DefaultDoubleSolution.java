package com.aaron.jmetal.algorithm;

import java.util.HashMap;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.impl.AbstractGenericSolution;

public class DefaultDoubleSolution   
	extends AbstractGenericSolution<Double, DoubleProblem>  
	implements DoubleSolution {  

	/** Constructor */  
	public DefaultDoubleSolution(DoubleProblem problem) {  
			super(problem) ;//会在父类AbstractGenericSolution里设置决策变量和优化目标函数空间，这里便是30和2.  

			initializeDoubleVariables();//随机初始化决策变量  
			initializeObjectiveValues();//优化目标函数设为0,0  
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
