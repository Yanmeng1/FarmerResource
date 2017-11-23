package com.aaron.view.swing;

import java.awt.Insets;

import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class WelcomeView {
	public static void main(String[] args) {
//	    try
//	    {
//	    	 //设置本属性将改变窗口边框样式定义
//	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow	;
//	    	// 设置不可见
//	        UIManager.put("RootPane.setupButtonVisible", false);
//	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//	        UIManager.put("ToolBar.isPaintPlainBackground", Boolean.TRUE);
//	    }
//	    catch(Exception e)
//	    {
//	        e.printStackTrace();	    
//	    }
		new ViewFrame(40, 40);
	}
}
