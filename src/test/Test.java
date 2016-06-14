package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;

import javax.swing.*;

import softRender.game.Sr3Window;

public class Test extends JFrame {
	private myPanel p;

	public Test(String name) {
		super(); // 继承父类的构造方法
		setTitle(name); // 名字
		setBounds(0, 0, 800, 800); // 大小
		BorderLayout bl = new BorderLayout();
		bl.setHgap(20);
		bl.setVgap(20);
		getContentPane().setLayout(bl); // 布局管理
		p = new myPanel("jarvischu");
		p.setLocation(0, 0);
		p.setSize(800, 800);
		getContentPane().add(p, bl.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 设置默认关闭操作
		
	}
	
	public void start(){
		new Thread(p).start();
	}

	public static void main(String args[]) {
		Test frame = new Test("JarvisChu");
		frame.setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				frame.start();
			}
		});
	}
}

class myPanel extends JPanel implements Runnable {
	private String m_Name;
	private Sr3Window window;
	public myPanel(String name) {
		m_Name = name;
		window = new Sr3Window();
		window.addNode(new TestNode());
	}
	@Override
	public void setSize(int width ,int height){
		super.setSize(width, height);
		window.setSize(width , height);
	}
	
	public void paint(Graphics g) {
//		GradientPaint grdp = new GradientPaint(0, 0, Color.blue, 100, 50, Color.RED);
//		// 创建一个渐变填充的对象
//		g2d.setPaint(grdp); // 选中该Paint对象
//		g2d.fillRect(0, 0, 150, 150);
		window.paint(g);
	}
	
	public void update(){
		window.update(0.01f);
	}
	
	public void run(){
		boolean isRun  = true;
		while(isRun){
			try {
				Thread.sleep(10);
				update();
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isRun = false;
			}
		}
	}
	
	
}
