package softRender.core;

import java.awt.Color;

import softRender.math.Sr3Vector;

public class Sr3Vertex {
    public Sr3Vector position;
    public Sr3Vector normal;
    public float u;
    public float v;
    public Color color;

    public Sr3Vertex(Sr3Vector position, Sr3Vector normal, float u, float v, Color color){
    	this.position = position;
    	this.normal = normal;
    	this.u = u;
    	this.v = v;
    	this.color = color;
    }
    
    @Override
	public Sr3Vertex clone(){
        return new Sr3Vertex(this.position.clone() , this.normal.clone() ,this.u,this.v, new Color(this.color.getRGB()));
	}
    
   public void setUV(float u , float v){
	   this.u = u ; this.v = v;
   }
}
