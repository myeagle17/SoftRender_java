package softRender.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import softRender.math.Sr3Math;
import softRender.math.Sr3Vector;

public class Sr3Render {
	private Sr3Canvas m_canvas;
	private boolean m_isDebugLine = true;
	public Sr3Render(int width ,int heigth){
		m_canvas = new Sr3Canvas(width, heigth);
	}
	
	public int getWidth(){
		return m_canvas.m_width;
	}
	
	public int getHeight(){
		return m_canvas.m_height;	
	}
	
	public void clear(Color color){
		m_canvas.clear(color);
	}
	
	public void updateToScreen(Graphics g){
		m_canvas.updateToScreen(g);
	}
	
    public void drawPoint(Sr3Vector position , Color color){
    	m_canvas.pushPixel((int)position.x, (int)position.y, (int)position.z, color);
    }
    
    public void drawLine(Sr3Vertex startVertex , Sr3Vertex endVertex){
    	Sr3Vector startVec = startVertex.position;
        Sr3Vector endVec = endVertex.position;
        float diffX = endVec.x - startVec.x;
        if(Math.abs(diffX)<= Sr3Math.floatEpsilon){
            float step = endVec.y>=startVec.y?1:-1;
            float lengthDiff = Math.abs(endVec.y - startVec.y);
            for(int a = 0 ; a<= lengthDiff ; ++a ){
                drawPoint(new Sr3Vector(startVec.x , startVec.y + a * step , startVec.z) , Sr3Math.interpolateColor(startVertex.color , endVertex.color, a/lengthDiff));
            }
        }else{
            float step = endVec.x>=startVec.x?1:-1;
            float lengthDiff = Math.abs(diffX);
            float yRatioX = (endVec.y - startVec.y)/diffX;
            for(int a = 0 ; a<=lengthDiff ; ++a){
                float drawX = startVec.x + a * step;
                float drawY = startVec.y + a * step * yRatioX;
                float drawZ = startVec.z;
                drawPoint(new Sr3Vector(drawX ,drawY , drawZ) ,Sr3Math.interpolateColor(startVertex.color , endVertex.color, a/lengthDiff));
            }
        }

    }
    private boolean isRateValid(Sr3Vertex vertex1 , Sr3Vertex vertex2 ){
        return !Sr3Math.FloatEqual(vertex1.position.y , vertex2.position.y);
    }

    // 由外部保证可以计算rate
    private float getLineRate(Sr3Vertex verUp , Sr3Vertex verBottom)
    {
        return (verUp.position.x - verBottom.position.x)/(verUp.position.y - verBottom.position.y);
    }

    private Sr3Vertex getVertexByY(float y , Sr3Vertex verUp , Sr3Vertex verDown){
        float rate = (verUp.position.y - y)/(verUp.position.y - verDown.position.y);
        return Sr3Math.interpolateSr3Vertex(verUp, verDown, rate);
    }

    
    // 绘制三角形
    public void drawTri(Sr3Vertex vertex1 , Sr3Vertex vertex2 , Sr3Vertex vertex3 , int textureID){
    	if (m_isDebugLine) {
            drawLine(vertex1, vertex2);
            drawLine(vertex2, vertex3);
            drawLine(vertex3, vertex1);
        }
        // 将顶点数组按照y轴坐标进行排序，1比3高
        Sr3Vertex verUp = vertex1;
        Sr3Vertex verMid = vertex2;
        Sr3Vertex verBottom  = vertex3;
        Sr3Vertex verTemp;
        if (verMid.position.y > verUp.position.y) {
            verTemp = verUp;
            verUp = verMid;
            verMid = verTemp;
        }
        if(verBottom.position.y > verMid.position.y){
            verTemp = verMid;
            verMid = verBottom;
            verBottom = verTemp;
        }
        if (verMid.position.y > verUp.position.y){
           verTemp = verUp;
           verUp = verMid;
           verMid = verTemp;
        }
        
        // 获取纹理
        BufferedImage img = Sr3TextureCache.getInstance().getTexture(textureID);
        // 从verBottom开始绘制起，直到verUp结束
        // 计算上下线的斜率
        boolean isRateUpBottomValid = isRateValid(verUp, verBottom);
        if(!isRateUpBottomValid) {
            System.out.println("tri vertex error:all y is the same");
            return;
        }

        for(float tempY = verBottom.position.y ; tempY <= verUp.position.y ; tempY += 1){
            if(tempY < verMid.position.y){
                Sr3Vertex verLeft = getVertexByY(tempY, verMid, verBottom);
                Sr3Vertex verRigth = getVertexByY(tempY , verUp , verBottom);
                flushRowLine(verLeft, verRigth, img);
            }else if(tempY == verMid.position.y){
                Sr3Vertex verRigth = getVertexByY(tempY , verUp , verBottom);
                flushRowLine(verMid, verRigth, img);
            }else{
                Sr3Vertex verLeft = getVertexByY(tempY, verMid, verUp);
                Sr3Vertex verRigth = getVertexByY(tempY , verUp , verBottom);
                flushRowLine(verLeft, verRigth, img);
            }
        }
    }
    
    public void drawTri(Sr3Vertex vertex1 , Sr3Vertex vertex2 , Sr3Vertex vertex3){
    	drawTri(vertex1, vertex2, vertex3 , 0);
    }
    
    // 画扫描线  平行的
    private void flushRowLine(Sr3Vertex vertext1 , Sr3Vertex vertext2 , BufferedImage img){
    	Sr3Vertex verLeft = vertext1;
        Sr3Vertex verRight = vertext2;
        Sr3Vertex verTemp;
        if (verLeft.position.x>verRight.position.x) {
            verTemp = verLeft;
            verLeft = verRight;
            verRight = verTemp;
        }
        float dis = verRight.position.x - verLeft.position.x;
        
        // 如果两点重合，则退出
        if(dis < Sr3Math.floatEpsilon) {
            flushPoint(vertext1, img);
            return;
        }
        
        // 绘制其他点
        for(float tempX = verLeft.position.x ; tempX <= verRight.position.x ; tempX += 0.5f){
            float factor = (verRight.position.x - tempX)/dis;
            flushPoint(Sr3Math.interpolateSr3Vertex(verRight, verLeft, factor), img);
        }
    }
    
    private Color getColorByImg(float u ,float v , BufferedImage img){
    	int width = img.getWidth();
    	int height = img.getHeight();
    	int x =(int)(width*u);
    	int y = (int)(height*v);
    	x = Math.min(x, width-1);
    	x = Math.max(x, 0);
    	y = Math.min(y, height-1);
    	y = Math.max(y, 0);
    	return new Color(img.getRGB(x, y));
    }
    
    // 画扫描点
    private void flushPoint(Sr3Vertex vertext , BufferedImage img){
    	Color color32 = null != img?getColorByImg(vertext.u , vertext.v , img):vertext.color;
    	Sr3Vector pos = vertext.position;
    	m_canvas.pushPixel((int)pos.x, (int)pos.y, (int)pos.z, color32);
    }
    
    // 绘制纹理仅用于测试
    public void drawTextureTest(Sr3Vector position , int textureID){
    	 BufferedImage pTexture = Sr3TextureCache.getInstance().getTexture(textureID);
    	    if(null == pTexture) return ;
    	    for (int w = 0 ; w<pTexture.getWidth() ; ++w){
    	        for(int h = 0; h<pTexture.getHeight() ; ++h){
    	        	m_canvas.pushPixel((int)position.x+w, (int)position.y + h, (int)position.z, new Color(pTexture.getRGB(w, h)));
    	        }
    	    }

    }

//    // 设置是否画线框
//    inline void setDebugLine(bool isDebugLine){m_isDebugLine = isDebugLine;}
//    



}
