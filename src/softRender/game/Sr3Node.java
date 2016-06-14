package softRender.game;

import softRender.math.Sr3Matrix;

public abstract class Sr3Node {
	protected Sr3Matrix m_matrix;
	
	public Sr3Node(){
		m_matrix = new Sr3Matrix();
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void draw();
}
