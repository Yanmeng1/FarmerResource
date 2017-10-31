package com.aaron.jmetal.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.ConstrainedProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class ZDT1 extends AbstractDoubleProblem implements ConstrainedProblem<DoubleSolution>{
	
	  /** Constructor. Creates default instance of problem ZDT1 (30 decision variables) */  
	  public ZDT1() {  
	    this(30);  
	  } 
	  
	  /** 
	   * Creates a new instance of problem ZDT1. 
	   * 
	   * @param numberOfVariables Number of variables. 
	   */  
	  public ZDT1(Integer numberOfVariables) {  
	    setNumberOfVariables(numberOfVariables);//设定决策变量个数，30  
	    setNumberOfObjectives(2);//设定优化目标函数个数,2  
	    setName("ZDT1");//给这个问题起名,ZDT1.  
	  
	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;  
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;  
	  
	    //设置定义域  
	    for (int i = 0; i < getNumberOfVariables(); i++) {  
	      lowerLimit.add(0.0);  
	      upperLimit.add(1.0);  
	    }  
	  
	    setLowerLimit(lowerLimit);  
	    setUpperLimit(upperLimit);  
	  }  

	//这里就是优化目标函数的实现过程，Algorithm.evlataionPopulation()会调用这个方法  
	  /** Evaluate() method */  
	@Override
	public void evaluate(DoubleSolution solution) {
		// TODO Auto-generated method stub
	    double[] f = new double[getNumberOfObjectives()];  
	    
	    f[0] = solution.getVariableValue(0);  
	    double g = this.evalG(solution);  
	    double h = this.evalH(f[0], g);  
	    f[1] = h * g;  
	  
	    solution.setObjective(0, f[0]);  
	    solution.setObjective(1, f[1]);  
	}
	
	  /** 
	   * Returns the value of the ZDT1 function G. 
	   * 
	   * @param solution Solution 
	   */  
	  private double evalG(DoubleSolution solution) {  
	    double g = 0.0;  
	    for (int i = 1; i < solution.getNumberOfVariables(); i++) {  
	      g += solution.getVariableValue(i);  
	    }  
	    double constant = 9.0 / (solution.getNumberOfVariables() - 1);  
	    g = constant * g;  
	    g = g + 1.0;  
	    return g;  
	  }  
	
	  /** 
	   * Returns the value of the ZDT1 function H. 
	   * 
	   * @param f First argument of the function H. 
	   * @param g Second argument of the function H. 
	   */  
	  public double evalH(double f, double g) {  
	    double h ;  
	    h = 1.0 - Math.sqrt(f / g);  
	    return h;  
	  }

	@Override
	public void evaluateConstraints(DoubleSolution solution) {
		// TODO Auto-generated method stub
		
	}  
}
