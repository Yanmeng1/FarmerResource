package com.aaron.data.parameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GUIParameter implements Serializable{
	
	private static final long serialVersionUID = -1972643716683823956L;

	// 1.读入界面+修改参数 -> 通过界面修改参数存入到IOParameter对象中;
	  // 粪便产生量大于粪便使用量 -> io对象变化进行约束 FertilizerRestrict = output - input >= 0
	private IOParameter io;
	
/********************作为后台要传的参数*********************/
	// 2.刚性约束
	//四个目标的权重参数 -> 界面必须输入权重参数
	private double[] weight;
	//界面必须输入 -> io对象 + 输入 =》 前三个约束
	private double totalMaxLands;
	private double totalMaxLabour;
	private double totalMaxMachine;
	private double[] FertilizerRestrict;
	
	// 3.每个变量的约束：默认[0:totalMaxLand] -> 界面通过  添加单个变量限制窗口
	private Double[] maxLand, minLand;
	// 4.其他约束
	List<double[]> equalityConstraints; //比例-默认为0
	List<double[]> inequalityConstraints; //系数-默认为0
	
	// 5. 计数
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
		this.equalityConstraints = new ArrayList<double[]>();    // 将等式约束转换为不等式约束
		this.inequalityConstraints = new ArrayList<double[]>();
	}

	public double[] getWeight() {
		return weight;
	}

	/**
	 * 设置参数
	 * @param index
	 * @param value
	 */
	public void setWeight(int index, double value) {
		this.weight[index] = value;
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
	 * 设置最大耕种面积 后 直接对每个变量进行初始化限制[0,totalMaxLand] or [0,0]
	 * @param totalMaxLands
	 */
	public void setTotalMaxLands(double totalMaxLands) {
		this.totalMaxLands = totalMaxLands;
		for(int i=0; i < this.io.getSpecies(); i++){
			this.minLand[i] = 0.0;
			if ( this.io.getCostTarget()[i] > 0.0 ) this.maxLand[i] = totalMaxLands / this.getIo().getLandRestrict()[i];
			else this.maxLand[i] = 0.0;
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
	public void loadFertilizerRestrict() {
		for(int i=0; i<this.io.getSpecies(); i++){
			this.FertilizerRestrict[i] = this.io.getFertilizerOutput()[i] - this.io.getFertilizerInput()[i];
		}
	}
	public double[] getFertilizerRestrict() {
		return FertilizerRestrict;
	}

	public Double[] getMaxLand() {
		return maxLand;
	}


	public Double[] getMinLand() {
		return minLand;
	}

	
	
	public int getNumberOfVariables(){
		return numberOfVariables;
	}
	public int getNumberOfObjectives(){
		return numberOfObjectives;
	}
	
	public int getNumberOfEqualityConstraints(){
		if ( equalityConstraints == null || equalityConstraints.size() == 0 ) return 0;
		else return equalityConstraints.size();
	}
	
	public int getNumberOfInequalityConstraints() {
		if ( inequalityConstraints == null || inequalityConstraints.size() == 0 ) 
			return 0;
		else return inequalityConstraints.size();
	}
	/**
	 * 返回约束个数 四个基本约束 + 输入约束
	 * @return
	 */
	public int getNumberOfConstraints(){
		return 4 + getNumberOfInequalityConstraints();
	}
	
	
	public List<double[]> getInequalityConstraints() {
		return this.inequalityConstraints;
	}
	public List<double [] > getEqualityConstraints() {
		return this.equalityConstraints;
	}
	
	public void setInequalityConstraintsEmpty() {
		this.inequalityConstraints.clear();
	}
	public void setEqualityConstriantsEmpty() {
		this.equalityConstraints.clear();
	}
}
