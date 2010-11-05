import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.*; 

/**
 * Rotating cube demo
 * @author tj
 *
 */
public class RotateCube implements GLEventListener
{
    private JLabel statusLine; // for misc messages at bottom of window
    private int framesDrawn;
    private GLU glu;
    private Cube cube;
    private Transform trans;
    
    /**
     * Initializes global variables and sets the translation matrix
     */
    public RotateCube() {
    	statusLine = new JLabel();
    	framesDrawn = 0;
    	glu = new GLU();
    	cube = new Cube(new Point3d(5,5,0),4);
    	
    	//setting values for transform matrix
    	trans = new Transform();
        trans.translate(5f,5f, 0);
        trans.rotateY(.18f);
        trans.rotateZ(.17f);
        trans.rotateX(.16f);
        trans.scale(.9999f, .9999f, .9999f);
        trans.translate(-5f, -5f, 0);
    }
    
    public void init(GLAutoDrawable drawable) {        
        GL2 gl = drawable.getGL().getGL2();
        gl.setSwapInterval(1); // for animation synchronized to refresh rate
        gl.glClearColor(0f,0f,0f,1f); 
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0f, 10f, 0f, 10f, -10f, 10f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl  = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        cube.drawCube(gl);
        
        //apply transformation matrix after displaying
        cube.updateCubeLocation(trans);
        
        // check for errors, at least once per frame
        int error = gl.glGetError();
        if (error != GL2.GL_NO_ERROR) {
            System.out.println("OpenGL Error: " + glu.gluErrorString(error));
            System.exit(1);
        }

        //System.out.println("display() done"); // for debugging
        statusLine.setText("Frames drawn: "  +  ++framesDrawn);  // normally do something like this
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public static void main(String[] args) {
        GLProfile.initSingleton(true);
        System.setProperty("sun.awt.noerasebackground", "true"); 

        JFrame frame = new JFrame("Rotating Cube");
        GLCanvas canvas = new GLCanvas();
        canvas.setPreferredSize(new Dimension(400,400));  // desired size, not guaranteed

        RotateCube renderer = new RotateCube();
        canvas.addGLEventListener(renderer);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.getContentPane().add(renderer.statusLine, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // make just big enough to hold objects inside
        frame.setVisible(true);
        
        // if continual automatic redraws are desired:
        FPSAnimator animator = new FPSAnimator(canvas, 30);
        animator.start();
    }
}