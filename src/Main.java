import java.io.IOException;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws IOException {
		JFrame obj = new JFrame();
		GamePlay gameplay = new GamePlay();
		obj.setBounds(10, 10, 905, 700);
		obj.setResizable(false);
		obj.add(gameplay);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
