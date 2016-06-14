package softRender.test;

public class Sr3DepthTest {
	private float[] m_buffer;
	private int m_bufferSize;
	public Sr3DepthTest(int width , int height){
		m_bufferSize = width * height;
		m_buffer =new float[m_bufferSize];
	}
	
	public void clear(){

		for(int i = 0 ; i< m_bufferSize ; ++i){
			m_buffer[i] = Float.MIN_VALUE;
		}
	}
	
	public boolean setDepthAndGetResult(int index , float z)
	{
	    if (index >= m_bufferSize || index<0) {
	        return false;
	    }
	   
	    if(m_buffer[index]<z){
	        m_buffer[index] = z;
	        return true;
	    }else{
	        return false;
	    }
	}
}

