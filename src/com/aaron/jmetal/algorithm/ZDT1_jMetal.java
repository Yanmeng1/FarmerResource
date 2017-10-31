package com.aaron.jmetal.algorithm;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.SimpleRandomMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.io.FileNotFoundException;
import java.util.List;


/**
 * Solve ZDT1 with NSGA-II on jMetal
 */
public class ZDT1_jMetal {
    public static void main(String[] args) throws FileNotFoundException {
        Problem<DoubleSolution> problem;
        Algorithm<List<DoubleSolution>> algorithm;
        CrossoverOperator<DoubleSolution> crossover;
        MutationOperator<DoubleSolution> mutation;
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
        String referenceParetoFront = "";
        //定义优化问题
        problem = new ZDT1();
        //配置SBX交叉算子
        double crossoverProbability = 0.9;
        double crossoverDistributionIndex = 20.0;
        crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
        //配置变异算子
        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        //  double mutationDistributionIndex = 20.0 ;
        mutation = new SimpleRandomMutation(mutationProbability);
        //配置选择算子
        selection = new BinaryTournamentSelection<DoubleSolution>(
                new RankingAndCrowdingDistanceComparator<DoubleSolution>());
        //将组件注册到algorithm
        algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxEvaluations(25000)
                .setPopulationSize(100)
                .build();
/*       或者用这样的方法注册一个算法
          evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();
          algorithm = new NSGAII<DoubleSolution>(problem, 25000, 100, crossover,
          mutation, selection, evaluator);
*/
        //用AlgorithmRunner运行算法
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
        //获取结果集
        List<DoubleSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

//        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        
//        for(int i=0; i<population.size(); i++){
//        	for(int j=0; j<30; j++){
//        		System.out.println(population.get(i).getVariableValueString(j));
//        	}
//        }
        List<DoubleSolution> nondominatedSolutions = SolutionListUtils.getNondominatedSolutions(population);
        int count = 0;
        for(int i= 0; i<population.size(); i++){
        	for(int j=0; j<population.get(i).getNumberOfObjectives(); j++){
        		System.out.print(population.get(i).getObjective(j)+" ------ ");
        	}
        	System.out.println("");
        	count++;
        }
        System.out.println("===="+count);
//        CsvWriter writer = new CsvWriter("result.txt");
//        writer.writeRecord(content);
        
        // 将全部种群打印到文件中
//        printFinalSolutionSet(population);
//        if (!referenceParetoFront.equals("")) printQualityIndicators(population, referenceParetoFront);
    }

}
