package com.aaron.view.swing;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;


public class ViewFrame extends JFrame {
	
	int width = 500;
	int height = 400;
	
	private JPanel viewPanel = null;
	private JLabel titleMessage = null;

	public ViewFrame(int x, int y) {
		
		this.viewPanel = (JPanel) this.getContentPane();
		
		this.titleMessage = new JLabel("现代农场资源配置系统", 0);
		this.titleMessage.setFont(new Font("宋体", 1, 35));
		
		this.viewPanel.add(this.titleMessage);
		this.setTitle("欢迎使用");
		
		this.setBounds(x, y, this.width, this.height);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ViewSwitch(this);
	}
	

	public JPanel getViewPanel() {
		return this.viewPanel;
	}

	public void setViewPanel(JPanel viewPanel) {
		this.viewPanel = viewPanel;
	}
	
}