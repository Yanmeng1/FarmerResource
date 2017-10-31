package com.aaron.data.parameter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * GUIParameter对象 序列化与反序列化 工具类
 * @author yanmeng
 */
public class Parameter {
	
	
	private static final String path = "./data/parameter_serialized.txt";

	/**
	 * 序列化GUI对象
	 * @param guiParameter
	 * @param path
	 */
	public static void SerializeGUIParameter(GUIParameter guiParameter, String path) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try { 
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(guiParameter);
		} catch ( Exception e) {
			e.printStackTrace();
		} finally {
			if ( oos != null )
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	/**
	 * 使用默认路径进行序列化
	 * @param guiParameter
	 */
	public static void SerializeGUIParameter(GUIParameter guiParameter){
		SerializeGUIParameter(guiParameter,path);
	}
	
	/**
	 * 反序列化 GUIParameter 对象
	 * @param path
	 * @return
	 */
	public static GUIParameter DeserializeParamer(String path){
		ObjectInputStream ois = null;
		GUIParameter guiParameter = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			guiParameter = (GUIParameter) ois.readObject();
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			if ( ois != null )
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return guiParameter;
	}
	/**
	 * 使用默认路径进行反序列化
	 * @return
	 */
	public static GUIParameter DeserializeParameter(){
		return DeserializeParamer(path);
	}
}
