package softRender.core;

import java.util.Vector;

import softRender.math.Sr3Matrix;

public class Sr3Triangle {
	private Vector<Sr3Vertex> m_vecVertex;
	private Vector<Integer> m_vecTextureID;
	
	 public Sr3Triangle(){
		 m_vecVertex = new Vector<Sr3Vertex>();
		 m_vecTextureID = new Vector<Integer>();
	 }
	 
	 public void addDrawTri(Sr3Vertex vertex1 , Sr3Vertex vertex2 , Sr3Vertex vertex3){
		 addDrawTri(vertex1, vertex2, vertex3, 0);
	 }
	 
	 public void addDrawTri(Sr3Vertex vertex1 , Sr3Vertex vertex2 , Sr3Vertex vertex3, int textureID){
		 m_vecVertex.add(vertex1.clone());
		 m_vecVertex.add(vertex2.clone());
		 m_vecVertex.add(vertex3.clone());
		 m_vecTextureID.add(textureID);
	 }
	 
	 public void draw(Sr3Matrix projectMatrix){
		    for(int i = 0 ; i<m_vecTextureID.size() ; ++i)
		    {
		        Sr3Vertex vertext0 = m_vecVertex.elementAt(i*3);
		        Sr3Vertex vertext1 = m_vecVertex.elementAt(i*3+1);
		        Sr3Vertex vertext2 = m_vecVertex.elementAt(i*3+2);
		        Sr3Global.transPosToScreen(vertext0, projectMatrix);
		        Sr3Global.transPosToScreen(vertext1, projectMatrix);
		        Sr3Global.transPosToScreen(vertext2, projectMatrix);
		        Sr3Global.render.drawTri(vertext0 , vertext1 , vertext2 , m_vecTextureID.elementAt(i));
		    }
	 }
	 
	 public void clear(){
		 m_vecVertex.clear();
		 m_vecTextureID.clear();
	 }

}
