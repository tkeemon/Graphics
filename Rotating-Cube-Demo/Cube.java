import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

/**
 * Class for generating a wireframe cube.
 * @author tj
 *
 */
public class Cube {
	private Point3d location;
	private float sideLength;
	private Point3d[][] corners;
	
	public Cube(Point3d loc, float len) {
		location = loc;
		sideLength = len;
		
		//holds the coordinates of the 8 vertexes of the cube
		corners = generateCornerLocations(loc, len);
	}
	
	/**
	 * Create cube with same parameters as one previously defined.
	 * @param c
	 */
	public Cube(Cube c) {
		this(c.location, c.sideLength);
	}
	
	/**
	 * Calculates cube verticies based on 
	 * @param loc
	 * @param len
	 * @return
	 */
	private Point3d[][] generateCornerLocations(Point3d loc, float len) {
		Point3d[][] ret = new Point3d[2][4];
		float startX = loc.getX();
		float startY = loc.getY();
		float startZ = loc.getZ();
		float half = len / 2;
		for(int i=0; i<2; i++) {
			float zDisp;
			if(i%2 == 0) zDisp = half;
			else zDisp = -half;
			
			ret[i][0] = new Point3d(startX+half,startY+half,startZ+zDisp);
			ret[i][1] = new Point3d(startX-half,startY+half,startZ+zDisp);
			ret[i][2] = new Point3d(startX-half,startY-half,startZ+zDisp);
			ret[i][3] = new Point3d(startX+half,startY-half,startZ+zDisp);
		}
		
		return ret;
	}
	
	/**
	 * Changes the 3d coordinates of the cube based on the transformation matrix 'trans'
	 * @param trans
	 */
	public void updateCubeLocation(Transform trans) {
		for(int i=0; i<2; i++)
			for(int j=0; j<4; j++)
				corners[i][j] = trans.multiply(corners[i][j]);
	}
	
	/**
	 * Draws cube onto screen
	 * @param gl
	 */
	public void drawCube(GL2 gl) {
		gl.glLineWidth(3f);
		for(int i = 0; i < 2; i++) {
        	gl.glBegin(GL2.GL_LINE_LOOP);
        		if(i == 1)
        			gl.glColor3f(0f, 1f, 0f);
        		else
        			gl.glColor3f(0f, 0f, 1f);
        		
        		for(int j = 0; j < 3; j++) {
        			gl.glVertex3f(corners[i][j].getX(), corners[i][j].getY(), corners[i][j].getZ());
        			gl.glVertex3f(corners[i][j+1].getX(), corners[i][j+1].getY(), corners[i][j+1].getZ());
        		}
        	gl.glEnd();
        }
	    
	    gl.glEnable(GL.GL_BLEND);
	    
	    //connecting the remaining vertexes
	    gl.glBegin(GL2.GL_LINES);
	    	for(int j = 0; j < 4; j++)
	    	{
	    		gl.glColor3f(0f,0f,1f);
	    		gl.glVertex3f(corners[0][j].getX(), corners[0][j].getY(), corners[0][j].getZ());
	    		gl.glColor3f(0f,1f,0f);
	    		gl.glVertex3f(corners[1][j].getX(), corners[1][j].getY(), corners[1][j].getZ());
	    	}
	    gl.glEnd();
	}

	/**
	 * Getter method 
	 * @return
	 */
	public Point3d[][] getCorners() {
		return corners;
	}
}
