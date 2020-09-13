package graphit;

/* Runs the program through Java Swing. */
import javax.swing.*;

public class App {

	public static void main(String[] args) {

		// final NGraphitBackend backend = new NGraphitBackend(40, 200);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				new NGraphitDisplay();
			}
		});

	}

}