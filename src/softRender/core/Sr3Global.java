package softRender.core;

import softRender.math.Sr3Matrix;
import softRender.math.Sr3Vector;

public class Sr3Global {
	static Sr3Render render;
	
	static Sr3Triangle triangle;

	static Sr3Matrix cameraMatrix;
	
	public static void transPosToScreen(Sr3Vertex vertex, Sr3Matrix projectMatrix)
    {
		int width = render.getWidth();
		int height = render.getHeight();
		
    	Sr3Vector pos = vertex.position;
    	
    	Sr3Matrix matrix = new Sr3Matrix(projectMatrix.p);
    	
    	matrix.multiply(cameraMatrix);

    	matrix.transform(pos);


        pos.x = pos.x * width + width / 2;
        pos.y = -pos.y * height + height / 2;
        pos.z = pos.z * width +width / 2;
    }
}
