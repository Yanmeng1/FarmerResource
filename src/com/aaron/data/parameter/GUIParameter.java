package com.aaron.data.parameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GUIParameter implements Serializable{
	
	private static final long serialVersionUID = -1972643716683823956L;

	// 1.四个目标的权重参数 -> 界面必须输入权重参数
	private double[] weight;
	
	// 2.读入界面+修改参数 -> 通过界面修改参数存入到IOParameter对象中
	private IOParameter io;
	
	// 3.界面必须输入 -> io对象 + 输入 =》 前三个约束
	private double totalMaxLands;
	private double totalMaxLabour;
	private double totalMaxMachine;
	// 4.粪便产生量大于粪便使用量 -> io对象变化进行约束 FertilizerRestrict = output - input >= 0
	private double[] FertilizerRestrict;
	// 6.其他 > 0 约束  -> 添加的时候需要指定两个值
	// 一个限制条件 包括other1中的一个double[speciesNum] 和 other2中的一个double[speciesNum] other1=1, other2=0 ，
	// 通过界面创建List
	List<double[]> other1; //比例-默认为0
	List<double[]> other2; //系数-默认为0
	
	// 5.每个变量的约束：默认[0:totalMaxLand] -> 界面通过  添加单个变量限制窗口
	private Double[] maxLand, minLand;

	
	// 7. 计数
	private int numberOfObjectives;
	private int numberOfVariables;
	private int numberOfConstraints;
	
	/**
	 * 创建GUIParameter对象，接收文件IO参数,引用传递所以后边对ioParameter的修改会作用到这个上面
	 * @param ioParameter
	 */
	public GUIParameter(IOParameter ioParameter){
		this.io = ioParameter;
		
		this.numberOfObjectives = 4;
		this.numberOfVariables = ioParameter.getSpecies();
		this.numberOfConstraints = 4;
		
		this.weight = new double[4];
		//利益最大化 -
		//成本最小化 +
		//化肥最小化 +
		//粮食产量最大化 
		this.FertilizerRestrict = new double[ioParameter.getSpecies()];
		this.maxLand = new Double[ioParameter.getSpecies()];
		this.minLand = new Double[ioParameter.getSpecies()];
		this.other1 = new ArrayList<double[]>();
		this.other2 = new ArrayList<double[]>();
	}

	public double[] getWeight() {
		return weight;
	}

	public void setWeight(double[] weight) {
		this.weight = weight;
	}

	public IOParameter getIo() {
		return io;
	}

	public void setIo(IOParameter io) {
		this.io = io;
	}

	public double getTotalMaxLands() {
		return totalMaxLands;
	}
	/**
	 * 设置最大耕种面积 后 直接对每个变量进行初始化限制[0,totalMaxLand]
	 * @param totalMaxLands
	 */
	public void setTotalMaxLands(double totalMaxLands) {
		this.totalMaxLands = totalMaxLands;
		for(int i=0; i < this.io.getSpecies(); i++){
			this.maxLand[i] = totalMaxLands;
			this.minLand[i] = 0.0;
		}
	}
	/**
	 * 根据序号No设置变量的最大值取值
	 * @Param No
	 * @param maxLandValue
	 */
	public void setMaxLandByNo(int No, double landValue) {
		this.maxLand[No] = landValue;
	}
	/**
	 * 根据序号No设置爱变量的最小取值
	 * @param No
	 * @param minLandValue
	 */
	public void setMinLandByNo(int No, double landValue) {
		this.minLand[No] = landValue;
	}

	public double getTotalMaxLabour() {
		return totalMaxLabour;
	}

	public void setTotalMaxLabour(double totalMaxLabour) {
		this.totalMaxLabour = totalMaxLabour;
	}

	public double getTotalMaxMachine() {
		return totalMaxMachine;
	}

	public void setTotalMaxMachine(double totalMaxMachine) {
		this.totalMaxMachine = totalMaxMachine;
	}
	/**
	 * 有机肥生产量 >= 有机肥使用量 fertilizer = output - input >= 0 
	 * @return double[] 限制条件 output - input
	 */
	public double[] getFertilizerRestrict() {
		for(int i=0; i<this.io.getSpecies(); i++){
			this.FertilizerRestrict[i] = this.io.getFertilizerOutput()[i] - this.io.getFertilizerInput()[i];
		}
		return FertilizerRestrict;
	}

	public Double[] getMaxLand() {
		return maxLand;
	}


	public Double[] getMinLand() {
		return minLand;
	}

	public List<double[]> getOther1() {
		return other1;
	}

	public void setOther1(List<double[]> other1) {
		this.other1 = other1;
	}

	public List<double[]> getOther2() {
		return other2;
	}

	public void setOther2(List<double[]> other2) {
		this.other2 = other2;
	}
	
	public int getNumberOfVariables(){
		return numberOfVariables;
	}
	public int getNumberOfObjectives(){
		return numberOfObjectives;
	}
	/**
	 * 获取约束数量
	 * @return int 4刚性约束 + 弹性约束
	 */
	public int getNumberOfConstraints(){
		return this.numberOfConstraints + this.other2.size();
	}
}
