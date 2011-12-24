package game.taquin;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TaquinObserver implements Runnable, Observer {

	private Shell shell;
	private int size;
	private Button[][] buttons;
	private Display display;

	public TaquinObserver(int size) {
		this.size = size;
		display = new Display();
		this.shell = new Shell(display);
		this.shell.setSize(40, 40);
		GridLayout layout = new GridLayout(3, false);
		this.shell.setLayout(layout);

		buttons = new Button[size][size];
		Thread thred = new Thread();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				buttons[i][j] = new Button(this.shell, SWT.PUSH);
				buttons[i][j].setSize(40, 40);
				buttons[i][j].setLayoutData(new GridData(SWT.LEFT, SWT.NORMAL,
						false, false, 1, 1));
				buttons[i][j].setText("");
			}
		}

		Button nextStep = new Button(shell, SWT.PUSH);
		nextStep.setText("Next step");

		shell.setSize(300, 300);
		shell.open();

	}

	@Override
	public void update(Observable o, Object arg) {
		int[][] game = (int[][]) arg;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int index = game[i][j];
				buttons[i][j].setText(index + "");

			}
		}
	}

	@Override
	public void run() {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
