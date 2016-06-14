package softRender.math;

import softRender.core.Sr3Vertex;

public class Sr3Vector {
	public float x;
	public float y;
	public float z;
	
	public Sr3Vector(){
		this(0,0,0);
	}
	
	public Sr3Vector(float x ,float y ,float z){
		setVal(x,y,z);
	}
	
	public void setVal(float x , float y , float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
    @Override
	public Sr3Vector clone(){
        return new Sr3Vector(x,y,z); 
	}
    
	public float length(){
	    return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public Sr3Vector normalize() {
	    float factor = 0;
	    float length = length();
	    if(length > 0) {
	        factor = 1 / length;
	    }
	    setVal(x * factor, y * factor, z * factor);
	    return this;
	};

	public float dot(Sr3Vector v){
	    return x * v.x + y * v.y + z * v.z;
	};

	public Sr3Vector cross(Sr3Vector v){
	    float X = y * v.z - z * v.y;
	    float Y = z * v.x - x * v.z;
	    float Z = x * v.y - y * v.x;
	    setVal(X, Y, Z);
	    return this;
	};
	
	
	
	public Sr3Vector plus(Sr3Vector v){
		setVal(x + v.x , y + v.y , z + v.z);
		return this;
	}
	
	public Sr3Vector minus(Sr3Vector v){
		setVal(x - v.x , y - v.y , z - v.z);
		return this;
	}
	
	public Sr3Vector multiply(float value){
		setVal(x*value, y*value, z*value);
		return this;
	}	
}
