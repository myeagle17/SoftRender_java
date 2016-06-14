package softRender.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import softRender.core.Sr3Api;

public class Sr3Window {
	public int m_width;
	public int m_height;
	public List<Sr3Node> m_nodeList;
	
	public Sr3Window(){
		m_nodeList = new ArrayList<Sr3Node>();
		
	}
	public void setSize(int width , int height){
		m_width = width;
		m_height = height;
		Sr3Api.InitRender(width, height);
		
	}

	public void paint(Graphics g){
		
		Sr3Api.Clear(new Color(0.0f,0.0f,0.0f));
		
		for (Sr3Node sr3Node : m_nodeList) {
			sr3Node.draw();
		}
		
		Sr3Api.UpdateToScreen(g);
	}
	
	public void update(float deltaTime){
		for (Sr3Node sr3Node : m_nodeList) {
			sr3Node.update(deltaTime);
		}
	}
	
	public void addNode(Sr3Node node){
		m_nodeList.add(node);
	}
	
}