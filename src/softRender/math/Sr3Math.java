package softRender.math;

import java.awt.Color;

import softRender.core.Sr3Vertex;

public class Sr3Math {
	public static float floatEpsilon = 0.001f;
	
	public static boolean FloatEqual(float a , float b){
		return Math.abs(a-b)<floatEpsilon;
	}
	
	public static Color interpolateColor(Color start , Color end , float factor){
		int r = (int) (start.getRed() + (end.getRed() - start.getRed())*factor);
		int g = (int) (start.getGreen() + (end.getGreen() - start.getGreen())*factor);
		int b = (int) (start.getBlue() + (end.getBlue() - start.getBlue())*factor);
		int a = (int) (start.getAlpha() + (end.getAlpha() - start.getAlpha())*factor);
		
		return new Color(r,g,b,a);
	}
	
	public static Sr3Vector interpolateSr3Vector(Sr3Vector start , Sr3Vector end, float factor){
		Sr3Vector result = end.clone();
		result.minus(start).multiply(factor).plus(start);
		return result;
	};
	
	public static Sr3Vertex interpolateSr3Vertex(Sr3Vertex start , Sr3Vertex end, float factor){
	    Sr3Vector p = interpolateSr3Vector(start.position , end.position, factor);
	    Sr3Vector n = interpolateSr3Vector(start.normal , end.normal, factor);
	    float tu = start.u + (end.u - start.u) * factor;
	    float tv = start.v + (end.v - start.v) * factor;
	    Color c = interpolateColor(start.color,end.color, factor);
	    return new Sr3Vertex(p, n, tu, tv, c);
	};
}
