package softRender.math;

import java.util.Arrays;

public class Sr3Matrix {
	public float p[] = new float[16];
	public Sr3Matrix() {
	    identity();
	};

	public Sr3Matrix(float values[]) {
	    setValue(values);
	};
	
//	public boolean (const Sr3Matrix &m) const {
//	    bool equal = true;
//	    for (int i = 0; i < 16; i++) {
//	        equal = equal && floatEqual(p[i], m.p[i]);
//	    }
//	    return equal;
//	};

	public void identity() {
	    float values[] = {
	        1, 0, 0, 0,
	        0, 1, 0, 0,
	        0, 0, 1, 0,
	        0, 0, 0, 1,
	    };
	    setValue(values);
	};

	public void lookAtLH(Sr3Vector eye, Sr3Vector target, Sr3Vector up) {
	    Sr3Vector axisZ = (target.clone().minus(eye)).normalize();
	    Sr3Vector axisX = up.clone().cross(axisZ).normalize();
	    Sr3Vector axisY = axisZ.clone().cross(axisX).normalize();
	    
	    float eyeX = -axisX.dot(eye);
	    float eyeY = -axisY.dot(eye);
	    float eyeZ = -axisZ.dot(eye);
	    
	    float values[] = {
	        axisX.x, axisY.x, axisZ.x, 0,
	        axisX.y, axisY.y, axisZ.y, 0,
	        axisX.z, axisY.z, axisZ.z, 0,
	        eyeX,    eyeY,    eyeZ,    1,
	    };
	    
	    Sr3Matrix matrix = new Sr3Matrix(values);
	   multiply(matrix);
	};

	public void perspectiveFovLH(float fieldOfView, float aspect, float znear, float zfar) {
	    float height = 1 / (float)Math.tan(fieldOfView / 2);
	    float width = height / aspect;
	    float values[] = {
	        width,  0,      0,                                  0,
	        0,      height, 0,                                  0,
	        0,      0,      zfar / (zfar - znear),              1,
	        0,      0,      (znear * zfar) / (znear - zfar),    0,
	    };
	    Sr3Matrix matrix = new Sr3Matrix(values);
	   multiply(matrix);
	};

	public void perspectiveForLHNew(float fieldOfView, float fAspect, float fNear, float fFar)
	{
	    float xmin, xmax, ymin, ymax;       // Dimensions of near clipping plane
	    
	    // Do the Math for the near clipping plane
	    ymax = (float) (fNear * Math.tan(fieldOfView));
	    ymin = -ymax;
	    xmin = ymin * fAspect;
	    xmax = -xmin;
	    
	    // Construct the projection matrix
	    Sr3Matrix projMatrix = new Sr3Matrix();
	    projMatrix.p[0] = (2.0f * fNear)/(xmax - xmin);
	    projMatrix.p[5] = (2.0f * fNear)/(ymax - ymin);
	    projMatrix.p[8] = (xmax + xmin) / (xmax - xmin);
	    projMatrix.p[9] = (ymax + ymin) / (ymax - ymin);
	    projMatrix.p[10] = -((fFar + fNear)/(fFar - fNear));
	    projMatrix.p[11] = -1.0f;
	    projMatrix.p[14] = -((2.0f * fFar * fNear)/(fFar - fNear));
	    projMatrix.p[15] = 0.0f;
	    
	    multiply(projMatrix);
	}

	public boolean equals(Sr3Matrix obj) {
	    for (int i = 0; i < 16; i++) {
	       if(!Sr3Math.FloatEqual(p[i], obj.p[i])){
	    	   return false;
	       }
	    }
	    return true;
	}

	public void rotationX(float angle) {
	    float s = (float) Math.sin(angle);
	    float c = (float)Math.cos(angle);
	    float values[] = {
	        1, 0,  0, 0,
	        0, c,  s, 0,
	        0, -s, c, 0,
	        0, 0,  0, 1,
	    };
	    Sr3Matrix matrix = new Sr3Matrix(values);
	   multiply(matrix);
	}

	public void rotationY(float angle) {
	    float s = (float)Math.sin(angle);
	    float c = (float)Math.cos(angle);
	    float values[] = {
	        c, 0, -s, 0,
	        0, 1, 0,  0,
	        s, 0, c,  0,
	        0, 0, 0,  1,
	    };
	    Sr3Matrix matrix = new Sr3Matrix(values);
	   multiply(matrix);
	}

	public void rotationZ(float angle) {
	    float s =  (float)Math.sin(angle);
	    float c =  (float)Math.cos(angle);
	    float values[] = {
	        c,  s, 0, 0,
	        -s, c, 0, 0,
	        0,  0, 1, 0,
	        0,  0, 0, 1,
	    };
	    Sr3Matrix matrix = new Sr3Matrix(values);
	   multiply(matrix);
	}

	public void rotation(Sr3Vector r) {
	    rotationX(r.x);
	    rotationY(r.y);
	    rotationZ(r.z);
	}

	public void translation(Sr3Vector t) {
	    float values[] = {
	        1, 0, 0, 0,
	        0, 1, 0, 0,
	        0, 0, 1, 0,
	        t.x, t.y, t.z, 1,
	    };
	    Sr3Matrix matrix = new Sr3Matrix(values);
	   multiply(matrix);
	}

	public void scale(Sr3Vector s) {
	    float values[] = {
	        s.x, 0,   0,    0,
	        0,   s.y, 0,    0,
	        0,   0,   s.z,  0,
	        0,   0,   0,    1,
	    };
	    Sr3Matrix matrix = new Sr3Matrix(values);
	   multiply(matrix);
	}

	public void transform(Sr3Vector v){
	    float x = v.x * p[0*4 + 0] + v.y * p[1*4 + 0] + v.z * p[2*4 + 0] + p[3*4 + 0];
	    float y = v.x * p[0*4 + 1] + v.y * p[1*4 + 1] + v.z * p[2*4 + 1] + p[3*4 + 1];
	    float z = v.x * p[0*4 + 2] + v.y * p[1*4 + 2] + v.z * p[2*4 + 2] + p[3*4 + 2];
	    float w = v.x * p[0*4 + 3] + v.y * p[1*4 + 3] + v.z * p[2*4 + 3] + p[3*4 + 3];
	    
	    v.x = x/w;
	    v.y = y/w;
	    v.z = z/w;
	}

	public void multiply(Sr3Matrix m){
	    float tempP[] = new float[16];
	    for(int x = 0;  x< 4; ++x) {
	        for(int y = 0 ; y<4 ; ++y){
	            int index = y*4 + x;
	            tempP[index] = p[y*4] * m.p[x] + p[y*4 + 1] * m.p[1*4 + x] + p[y*4 + 2] * m.p[2*4 + x] + p[y*4 + 3] * m.p[3*4 + x];
	        }
	    }
	    setValue(tempP);
	};

	public void setValue(float values[]){
	    for(int i = 0; i < 16; i++) {
	        p[i] = values[i];
	    }
	}

}
