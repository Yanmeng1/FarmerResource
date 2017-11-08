package com.aaron.data.parameter;

import java.io.Serializable;
import java.util.List;

import arg.aaron.csvutils.CsvReader;

public class IOParameter implements Serializable{
	
	private static final long serialVersionUID = -2499109887043053125L;
	// 种类个数  
	private int speciesNum;
	private int colNum;
	// 标题名称
	private String colName[];
	// 序号
	private int[] No;
	// 种类名称
	private String[] speciesName;
	// 4 个目标函数
	private double[] costTarget;
	private double[] marginTarget;
	private double[] outputTarget;
	private double[] chemicalTarget;
	// 5 个约束
	private double[] labourRestrict;
	private double[] machineRestrcit;
	private double[] landRestrict;
	private double[] FertilizerInput;
	private double[] FertilizerOutput;
	
	public IOParameter(String path){
		CsvReader reader = new CsvReader(path,",","GBK");
		
		List<String[]> readLines = reader.readLines();
		if ( readLines == null || readLines.isEmpty() ) return;
		
		int rowLenght = readLines.size();
		int colLenght = readLines.get(0).length;
		colNum = colLenght;
		
		colName = new String[colLenght];
		System.arraycopy(readLines.get(0), 0, colName, 0, colLenght);
		
		speciesNum = rowLenght-1; 
		No = new int[speciesNum];
		speciesName = new String[speciesNum];
		
		costTarget = new double[speciesNum];
		marginTarget = new double[speciesNum];
		outputTarget = new double[speciesNum];
		chemicalTarget = new double[speciesNum];
		
		labourRestrict = new double[speciesNum];
		machineRestrcit = new double[speciesNum];
		landRestrict = new double[speciesNum];
		FertilizerInput = new double[speciesNum];
		FertilizerOutput = new double[speciesNum];
		
		for(int row=0; row<speciesNum; row++){
			No[row] = row+1;
			speciesName[row] = readLines.get(row+1)[1] == null ? "无" : readLines.get(row+1)[1];
			costTarget[row] = readLines.get(row+1)[2].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[2]);
			marginTarget[row] = readLines.get(row+1)[3].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[3]);
			outputTarget[row] = readLines.get(row+1)[4].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[4]);
			chemicalTarget[row] = readLines.get(row+1)[5].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[5]);
			labourRestrict[row] = readLines.get(row+1)[6].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[6]);
			machineRestrcit[row] = readLines.get(row+1)[7].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[7]);
			landRestrict[row] = readLines.get(row+1)[8].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[8]);
			FertilizerInput[row] = readLines.get(row+1)[9].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[9]);
			FertilizerOutput[row] = readLines.get(row+1)[10].equals("") ? 0.0 : Double.parseDouble(readLines.get(row+1)[10]);
		}
//		for(int row=0; row<species; row++){
//			System.out.println(No[row]+"="+speciesName[row]+"="+costTarget[row]+"="+marginTarget[row]+
//					"="+outputTarget[row]+"="+chemicalTarget[row]+"="+labourRestrict[row]+"="+machineRestrcit[row]+
//					"="+landRestrict[row]+"="+FertilizerInput[row]+"="+FertilizerOutput[row]);
//		}		
	}

	public int getSpecies() {
		return speciesNum;
	}

	public void setSpecies(int species) {
		this.speciesNum = species;
	}

	public String[] getColName() {
		return colName;
	}

	public void setColName(String[] colName) {
		this.colName = colName;
	}

	public int[] getNo() {
		return No;
	}

	public void setNo(int[] no) {
		No = no;
	}

	public String[] getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String[] speciesName) {
		this.speciesName = speciesName;
	}

	public double[] getCostTarget() {
		return costTarget;
	}

	public void setCostTarget(int index, Double costTarget) {
		this.costTarget[index] = costTarget;
	}

	public double[] getMarginTarget() {
		return marginTarget;
	}

	public void setMarginTarget(int index,Double marginTarget) {
		this.marginTarget[index] = marginTarget;
	}

	public double[] getOutputTarget() {
		return outputTarget;
	}

	public void setOutputTarget(int index, Double outputTarget) {
		this.outputTarget[index] = outputTarget;
	}

	public double[] getChemicalTarget() {
		return chemicalTarget;
	}

	public void setChemicalTarget(int index, Double chemicalTarget) {
		this.chemicalTarget[index] = chemicalTarget;
	}

	public double[] getLabourRestrict() {
		return labourRestrict;
	}

	public void setLabourRestrict(int index, Double labourRestrict) {
		this.labourRestrict[index] = labourRestrict;
	}

	public double[] getMachineRestrcit() {
		return machineRestrcit;
	}

	public void setMachineRestrcit(int index, Double machineRestrcit) {
		this.machineRestrcit[index] = machineRestrcit;
	}

	public double[] getLandRestrict() {
		return landRestrict;
	}

	public void setLandRestrict(int index, Double landRestrict) {
		this.landRestrict[index] = landRestrict;
	}

	public double[] getFertilizerInput() {
		return FertilizerInput;
	}

	public void setFertilizerInput(int index, Double fertilizerInput) {
		FertilizerInput[index] = fertilizerInput;
	}

	public double[] getFertilizerOutput() {
		return FertilizerOutput;
	}

	public void setFertilizerOutput(int index, Double fertilizerOutput) {
		FertilizerOutput[index] = fertilizerOutput;
	}
	
	public int getColNum(){
		return this.colNum;
	}
	
}
