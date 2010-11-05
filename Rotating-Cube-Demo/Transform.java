
/**
 * Simple matrix manipulations for rotations, scales, and translations
 * @author tj
 *
 */
public class Transform
{
	private float[][] matrix; // holds current transformation
	
	/**
	 * Default constructor
	 */
	public Transform() {
	    matrix = new float[4][4];
	    identity();
	
	}
	
	public Transform(float[][] t2) {
		matrix = t2;
	}
	
	/**
	 * Change current matrix back to identity matrix
	 */
	public void identity() {
	    for (int i=0; i<4; ++i) {
	        for (int j=0; j<4; ++j) {
	            if (i==j) matrix[i][j] = 1f;
	            else      matrix[i][j] = 0f;
	        }
	    }
	}
	
	/**
	 * Creates and applies rotation matrix around X-axis to the current transformation matrix. 
	 * Rotates by 'angle' degrees.
	 * @param angle - number of degrees to rotate
	 */
	public void rotateX(float angle) {
	    float cosTh = (float) Math.cos(Math.toRadians(angle));
	    float sinTh = (float) Math.sin(Math.toRadians(angle));
	    
	    float[][] trans =  {{1,0,0,0},
							{0,cosTh,-sinTh,0},
							{0,sinTh,cosTh,0},
							{0,0,0,1}};
	    Transform t2 = new Transform(trans);
	    multiply(t2);
	}
	
	/**
	 * Creates and applies rotation matrix around Y-axis to the current transformation matrix. 
	 * Rotates by 'angle' degrees.
	 * @param angle - number of degrees to rotate
	 */
	public void rotateY(float angle) {
		float cosTh = (float) Math.cos(Math.toRadians(angle));
	    float sinTh = (float) Math.sin(Math.toRadians(angle));
	    
	    float[][] trans =  {{cosTh,0,sinTh,0},
							{0,1,0,0},
							{-sinTh,0,cosTh,0},
							{0,0,0,1}};
	    Transform t2 = new Transform(trans);
	    multiply(t2);
	}
	
	/**
	 * Creates and applies rotation matrix around Z-axis to the current transformation matrix. 
	 * Rotates by 'angle' degrees.
	 * @param angle - number of degrees to rotate
	 */
	public void rotateZ(float angle) {
		float cosTh = (float) Math.cos(Math.toRadians(angle));
	    float sinTh = (float) Math.sin(Math.toRadians(angle));
	    
	    float[][] trans =  {{cosTh,-sinTh,0,0},
							{sinTh,cosTh,0,0},
							{0,0,1,0},
							{0,0,0,1}};
	    Transform t2 = new Transform(trans);
	    multiply(t2);
	}
	
	/**
	 * Creates and applies scale matrix to the current transformation matrix based on (x,y,z)
	 * @param x
	 * @param y
	 * @param z
	 */
	public void scale(float x, float y, float z) {
		float[][] trans =  {{x,0,0,0},
							{0,y,0,0},
							{0,0,z,0},
							{0,0,0,1}};
		Transform t2 = new Transform(trans);
		multiply(t2);
	}
	
	/**
	 * Creates and applies translation matrix to the current transformation matrix based on (x,y,z)
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(float x, float y, float z) {
	    float[][] trans =  {{1,0,0,x},
	    					{0,1,0,y},
	    					{0,0,1,z},
	    					{0,0,0,1}};
		Transform t2 = new Transform(trans);
		multiply(t2);
	}
	
	//not used, but i think it worked
	public void perspectiveZ(float f)
	{
		float[][] trans =  {{1,0,0,0},
							{0,1,0,0},
							{0,0,0,0},
							{0,0,-1/f,1}};
		Transform t2 = new Transform(trans);
		multiply(t2);
	}
	
	/**
	 * Computes Mt2 where M is the current transformation matrix.
	 * @param t2 - the transform matrix being passed in
	 * 
	 */
	public void multiply(Transform t2) {
		float[][] matrix2 = new float[4][4];
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				matrix2[i][j] = (matrix[i][0] * t2.matrix[0][j]) + 
						(matrix[i][1] * t2.matrix[1][j]) +
						(matrix[i][2] * t2.matrix[2][j]) +
						(matrix[i][3] * t2.matrix[3][j]);
				
		matrix = matrix2;
	}
	
	/**
	 * Computes Mp where M is the current transformation matrix.
	 * @param p - 3d point 
	 * @return new point after multiplication
	 */
	public Point3d multiply(Point3d p) {
	    float[] vect = {p.getX(), p.getY(), p.getZ(), 1};
	    float[] vect2 = new float[4];
	    
	    //doing the matrix multiplication
	    for(int row = 0; row < 4; row++)
	    {
	    	float pt = 0;
	    	for(int col = 0; col < 4; col++)
	    	{//System.out.println("col: " + col + " " + pt + " " + matrix[row][col] + " " + vect[col]);
	    		pt += (matrix[row][col] * vect[col]);
	    	}
	    	vect2[row] = pt;
	    }
		
		//dividing all points by 'w' before creating Point3d object (w should = 1 most of the time)
	    Point3d ret = new Point3d(vect2[0]/vect2[3],vect2[1]/vect2[3],vect2[2]/vect2[3]);
	    return ret;
	}
	
	/**
	 * Nice display for a Transform object.
	 */
	public String toString() {
	    String result = "";
	    for (int i=0; i<4; ++i) {
	        result = result + "[";
	        for (int j=0; j<4; ++j) {
	            result = result + matrix[i][j] + ' ';
	        }
	        result = result + "]\n";
	    }
	    return result;
	}
}