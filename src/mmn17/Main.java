 

package mmn17;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

public class Main {
	static int WINDOW_HEIGHT = 600;
	static int WINDOW_WIDTH = 800;
	public static Frame frame;

	public static void main(String[] args) {
		Scene gRobot = new Scene();
		InitGlut(gRobot);
	}


	public static void InitGlut(Scene scene) {
		GLCanvas canvas = new GLCanvas();
		Frame frame = createFrame(canvas);
		RobotMenu robotMenu = new RobotMenu(frame, scene);
		robotMenu.showMenu();
		Main.frame = frame;

		Animator animator = createAnimator(frame, canvas);
		canvas.addGLEventListener(scene);
		animator.start();
	}

	private static Frame createFrame(GLCanvas canvas) {
		Frame frame = new Frame("MMN 17 Robot");

		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLayout(new BorderLayout());

		frame.add(canvas, BorderLayout.CENTER);
		frame.validate();

		frame.setVisible(true);

		return frame;
	}

	private static Animator createAnimator(Frame frame, GLCanvas canvas) {
		final Animator animator = new Animator();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {
					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});

		animator.add(canvas);
		return animator;
	}

}
