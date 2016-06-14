package softRender.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Sr3TextureCache {
	private int m_curGenID;
	private Map<Integer , BufferedImage> m_mapTexture;
	private static Sr3TextureCache ince = null;
	private Sr3TextureCache(){
		m_curGenID = 0;
		m_mapTexture = new HashMap<Integer , BufferedImage>();
	}
	public static Sr3TextureCache getInstance(){
		if(null == ince){
			ince = new Sr3TextureCache();
		}
		return ince;
	}

	public void clear(){
		m_curGenID = 0;
		m_mapTexture.clear();
	}
	
	public int createTexture(String fileName){
		int result = 0;
		File f = new File(fileName);
		if(!f.exists()){
			return result;
		}
		
		try {
			BufferedImage bi = ImageIO.read(f);
			if(null != bi){
				++m_curGenID;
				m_mapTexture.put(m_curGenID, bi);
				result = m_curGenID;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public BufferedImage getTexture(int textureID){
		return m_mapTexture.get(textureID);
	}
}
