package softRender.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import softRender.test.Sr3DepthTest;

public class Sr3Canvas {
    public int m_width;
    public int m_height;
	private BufferedImage m_bufferedImage;
	private Sr3DepthTest m_depthTest;
	public Sr3Canvas(int width, int height){
		m_width = width;
		m_height = height;
		m_bufferedImage = new BufferedImage(width, height , BufferedImage.TYPE_INT_ARGB);
		m_depthTest = new Sr3DepthTest(width, height);
	}
	
	public void clear(Color color){
		for(int i = 0 ; i<m_width; ++i){
			for(int m = 0 ;m<m_height ; ++m){
				m_bufferedImage.setRGB(i, m, color.getRGB());
			}
		}
		m_depthTest.clear();
	}
	
	public void pushPixel(int x ,int y , int z , Color color){
		if (x<0||y<0||x>= m_width || y >= m_height){	
			return ;
		}
	    
	    int index = (m_height-y)*m_width + x;
	    // 深度测试
	    if (!m_depthTest.setDepthAndGetResult(index, z)){
	        return;
	    }
	    
	    m_bufferedImage.setRGB(x, y, color.getRGB());
	}
	
	public void updateToScreen(Graphics g){
		g.drawImage(m_bufferedImage , 0 , 0 , m_width , m_height , null);
	}
}
