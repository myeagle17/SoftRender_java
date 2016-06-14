package test;

import java.awt.Color;

import softRender.core.Sr3Api;
import softRender.core.Sr3Global;
import softRender.core.Sr3TextureCache;
import softRender.core.Sr3Vertex;
import softRender.game.Sr3Node;
import softRender.math.Sr3Matrix;
import softRender.math.Sr3Vector;

public class TestNode extends Sr3Node {
	private int textureID1 = 0;
	private int textureID2 = 0;
	private int textureID3 = 0;
	private int textureID4 = 0;
	private int textureID5 = 0;
	private int textureID6 = 0;
	
	private float step11 = 0.0f;
	private float off = 0.0f;
	
	private Sr3Matrix m_matrix;
	public TestNode() {
		Color c1 = new Color(1, 0, 0, 0);
		System.out.println("c1 = " + c1.getRGB());
		Color c2 = new Color(0, 0, 0, 1);
		System.out.println("c2 = " + c2.getRGB());

		m_matrix = new Sr3Matrix();	
		
		textureID1 = Sr3TextureCache.getInstance().createTexture("res/1.png");
		textureID2 = Sr3TextureCache.getInstance().createTexture("res/2.png");
		textureID3 = Sr3TextureCache.getInstance().createTexture("res/3.png");
		textureID4 = Sr3TextureCache.getInstance().createTexture("res/1.png");
		textureID5 = Sr3TextureCache.getInstance().createTexture("res/2.png");
		textureID6 = Sr3TextureCache.getInstance().createTexture("res/3.png");
	}

	@Override
	public void update(float deltaTime) {
	    m_matrix.identity();
	    m_matrix.translation(new Sr3Vector(0,0,0));
	    m_matrix.scale(new Sr3Vector(0.5f,0.5f,0.5f));
	    m_matrix.rotationY((float) Math.toRadians(step11));
	    m_matrix.rotationX((float) Math.toRadians(step11));
	    step11 += 1.00f;
	    off += 0.01f;
	    //Sr3Global.cameraMatrix.translation(new Sr3Vector(0.0f,0.0f,50.0f));
	}

	@Override
	public void draw() {
		Sr3Api.DrawPoint(new Sr3Vector(20, 20, 20), new Color(1.0f, 0.0f, 0.0f));
		Sr3Api.DrawPoint(new Sr3Vector(100, 100, 20), new Color(1.0f, 0.0f, 0.0f));
		//testDrawLine();

		//Sr3Api.DrawTextureInScreen(textureID1);
		
		drawTri();
	}

	private void testDrawLine() {
		float width = 1.0f;
		Sr3Vertex vertex0 = new Sr3Vertex(new Sr3Vector(20, 20, 1), new Sr3Vector(0, 0, 0), 0, 0,
				new Color(0.0f, 1.0f, 0.0f));

		Sr3Vertex vertexLT = new Sr3Vertex(new Sr3Vector(100, 100, 1), new Sr3Vector(0, 0, 0), 0, 0,
				new Color(0.0f, 0.0f, 1.0f));
		// Sr3Vertex
		// vertexRT(Sr3Vector(0,-width,0),Sr3Vector(),0,0,new Color(1.0f,0.0f,0.0f));
		// Sr3Vertex
		// vertexRD(Sr3Vector(width,0,0),Sr3Vector(),0,0,new Color(1.0f,0.0f,0.0f));
		// Sr3Vertex
		// vertexLD(Sr3Vector(-width,0,0),Sr3Vector(),0,0,new Color(1.0f,0.0f,0.0f));

		Sr3Api.DrawLine(vertex0, vertexLT);
		// render->drawLine(vertex0,vertexRT);
		// render->drawLine(vertex0,vertexRD);
		// render->drawLine(vertex0,vertexLD);

	}

	private void drawTri(){
		    Sr3Api.Begin();
		    
		    Color red = new Color(1.0f,0.0f,0.0f);
		    Color green = new Color(0.0f,1.0f,0.0f);
		    Color yellow = new Color(1.0f,0.0f,1.0f);
		    Color pink = new Color(0.0f,1.0f,1.0f);
		    
		    
		    Sr3Vertex vertexLT = new Sr3Vertex(new Sr3Vector(-0.25f,0.25f,0.25f),new Sr3Vector(),0,0,new Color(1.0f,0.0f,0.0f));
		    Sr3Vertex vertexRT = new Sr3Vertex (new Sr3Vector(0.25f,0.25f,0.25f),new Sr3Vector(),1,0,new Color(1.0f,0.0f,0.0f));
		    Sr3Vertex vertexRD= new Sr3Vertex(new Sr3Vector(0.25f,-0.25f,0.25f),new Sr3Vector(),1,1,new Color(1.0f,0.0f,0.0f));
		    Sr3Vertex vertexLD= new Sr3Vertex(new Sr3Vector(-0.25f,-0.25f,0.25f),new Sr3Vector(),0,1,new Color(0.0f,1.0f,0.0f));
		    
		    Sr3Vertex vertexLTB= new Sr3Vertex(new Sr3Vector(-0.25f , 0.25f,-0.25f),new Sr3Vector(),0,0,new Color(1.0f,0.0f,0.0f));
		    Sr3Vertex vertexRTB= new Sr3Vertex(new Sr3Vector(0.25f , 0.25f,-0.25f),new Sr3Vector(),1,0,new Color(0.0f,0.0f,0.0f));
		    Sr3Vertex vertexRDB= new Sr3Vertex(new Sr3Vector(0.25f , -0.25f,-0.25f),new Sr3Vector(),1,1,new Color(0.0f,0.0f,0.0f));
		    Sr3Vertex vertexLDB= new Sr3Vertex(new Sr3Vector(-0.25f,-0.25f,-0.25f),new Sr3Vector(),0,1,new Color(0.0f,0.0f,0.0f));
		    
		    vertexLT.color = red;
		    vertexRT.color = yellow;
		    vertexRD.color = new Color(0.0f,0.0f,1.0f);
		    vertexLD.color = green;
		    vertexLT.setUV(0, 1);
		    vertexRT.setUV(1, 1);
		    vertexRD.setUV(1, 0);
		    vertexLD.setUV(0, 0);
		    Sr3Api.AddTri(vertexRD, vertexLT, vertexRT, textureID2);
		    Sr3Api.AddTri(vertexRD, vertexLD, vertexLT, textureID2);
		    
		    // left
		    vertexLT.color = green;
		    vertexLD.color = green;
		    vertexLTB.color = green;
		    vertexLDB.color = green;
		    vertexLT.setUV(0, 1);
		    vertexLD.setUV(0, 0);
		    vertexLTB.setUV(1, 1);
		    vertexLDB.setUV(1, 0);
		    Sr3Api.AddTri(vertexLT, vertexLD, vertexLDB, textureID2);
		    Sr3Api.AddTri(vertexLT, vertexLTB, vertexLDB, textureID2);
		//
		    // back
		    vertexLTB.color = yellow;
		    vertexRTB.color = yellow;
		    vertexRDB.color = yellow;
		    vertexLDB.color = yellow;
		    vertexLTB.setUV(0, 1);
		    vertexRTB.setUV(1, 1);
		    vertexRDB.setUV(1, 0);
		    vertexLDB.setUV(0, 0);
		    Sr3Api.AddTri(vertexLTB, vertexRTB, vertexRDB, textureID3);
		    Sr3Api.AddTri(vertexRDB, vertexLDB, vertexLTB, textureID3);
		//
		    // right
		    vertexRT.color = pink;
		    vertexRD.color = pink;
		    vertexRTB.color = pink;
		    vertexRDB.color = pink;
		    vertexRT.setUV(1, 1);
		    vertexRD.setUV(1, 0);
		    vertexRTB.setUV(0, 1);
		    vertexRDB.setUV(0, 0);
		    Sr3Api.AddTri(vertexRT, vertexRTB, vertexRDB, textureID4);
		    Sr3Api.AddTri(vertexRT, vertexRD, vertexRDB, textureID4);
		    
		    // up
		    vertexLT.color = red;
		    vertexRT.color = red;
		    vertexLTB.color = red;
		    vertexRTB.color = red;
		    vertexLT.setUV(0, 1);
		    vertexRT.setUV(1, 1);
		    vertexLTB.setUV(0, 0);
		    vertexRTB.setUV(1, 0);
		    Sr3Api.AddTri(vertexLT, vertexRT, vertexLTB, textureID5);
		    Sr3Api.AddTri(vertexRT, vertexRTB, vertexLTB, textureID5);
		    
		    // bottom
		    vertexLD.color = yellow;
		    vertexRD.color = yellow;
		    vertexRDB.color = yellow;
		    vertexLDB.color = yellow;
		    vertexLD.setUV(0, 0);
		    vertexRD.setUV(1, 0);
		    vertexLDB.setUV(0, 1);
		    vertexRDB.setUV(1, 1);
		    Sr3Api.AddTri(vertexLD, vertexRD, vertexLDB, textureID6);
		    Sr3Api.AddTri(vertexRDB, vertexRD, vertexLDB, textureID6);
		    
		    Sr3Api.End(m_matrix);
	}

}
