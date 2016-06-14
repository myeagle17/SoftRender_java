package softRender.core;

import java.awt.Color;
import java.awt.Graphics;

import softRender.math.Sr3Matrix;
import softRender.math.Sr3Vector;

public class Sr3Api {
	
	public static void InitRender(int width ,int height){
		Sr3Global.render = new Sr3Render(width , height);
		Sr3Global.triangle = new Sr3Triangle();
		InitCamera(width,height);
	}

	
	public static void UpdateToScreen(Graphics g){
		Sr3Global.render.updateToScreen(g);
	}
	
	public static void Clear(Color color){
		Sr3Global.render.clear(color);
	}
	
	 public static void DrawPoint(Sr3Vector position , Color color){
		 Sr3Global.render.drawPoint(position , color);
	 }
	 
	 public static void DrawLine(Sr3Vertex startVertex , Sr3Vertex endVertex){
		 Sr3Global.render.drawLine(startVertex, endVertex);
	 }
	 
	 public static void Begin(){
		 Sr3Global.triangle.clear();
	 }

	 public static void AddTri(Sr3Vertex vertex1 , Sr3Vertex vertex2 , Sr3Vertex vertex3 , int textureID){
		 Sr3Global.triangle.addDrawTri(vertex1, vertex2, vertex3, textureID);
	 }

	 public static void End(Sr3Matrix projectMatrix){
		 Sr3Global.triangle.draw(projectMatrix);
	 }
	 
	 public static void DrawTextureInScreen(int textureID){
		 Sr3Global.render.drawTextureTest(new Sr3Vector(100,100,100), textureID);
	 }
	 
	private static void InitCamera(int width , int height){
		Sr3Vector cameraPosition = new Sr3Vector(0 , 0 , 1);
		Sr3Vector cameraTarget = new Sr3Vector(0, 0, 0);
		Sr3Vector cameraUp = new Sr3Vector(0, 1, 0);
		Sr3Global.cameraMatrix = new Sr3Matrix();
		Sr3Global.cameraMatrix.lookAtLH(cameraPosition, cameraTarget, cameraUp);
		
		Sr3Global.cameraMatrix.perspectiveForLHNew((float)Math.toRadians(45), width/height, 1.0f, 100);
	}

}
