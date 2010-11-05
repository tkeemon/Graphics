import javax.media.opengl.GL2;

/**
 * Object to hold 3D coordinates
 * @author tj
 *
 */
class Point3d {
    private float x, y, z; 

    public Point3d() {
        this(0,0,0);
    }

    public Point3d(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /** 
     * getters and setters
     * 
     */
    public float getX() {return x;}
    public float getY() {return y;}
    public float getZ() {return z;}
    public void setX(float f) {x = f;}
    public void setY(float f) {y = f;}
    public void setZ(float f) {z = f; }

    /**
     * Draws point specified by (x,y,z). Assumes this occurs between glBegin(GL_POINTS)/glEnd()
     * @param gl
     */
 /*   public void draw(GL2 gl) {
        gl.glVertex3d(x,y,z);
    }
 */   
    /**
     * Info to show if object is output to a stream
     */
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}