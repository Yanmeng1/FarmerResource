package com.aaron.view.swing;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
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
		
		JPanel GImage = new JPanel() {  
            protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon("./data/test.png");  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, width,  
                		height, icon.getImageObserver());  
//                this.setSize(icon.getIconWidth(), icon.getIconHeight()); 
            }  
        };  
        
		
//        this.viewPanel = (JPanel) this.getContentPane();
//		this.titleMessage = new JLabel("现代生态农场优化决策支持模拟系统", 0);
//		this.titleMessage.setFont(new Font("宋体", 1, 26));
//		
//		this.viewPanel.add(this.titleMessage);
        
        this.setContentPane(GImage);
		this.setTitle("现代生态农场优化决策支持模拟系统");
		
		this.setBounds(x, y, this.width, this.height);
	
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		try {
			Thread.sleep(8000);
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