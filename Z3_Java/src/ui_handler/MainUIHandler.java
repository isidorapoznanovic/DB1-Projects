package ui_handler;

import java.util.Scanner;

public class MainUIHandler {

	public static Scanner sc = new Scanner(System.in);

	private final SkakaonicaUIHandler skakaonicaUIHandler = new SkakaonicaUIHandler();
	private final ComplexQueryUIHandler complexQueryUIHandler = new ComplexQueryUIHandler();

	public void handleMainMenu() {

		String answer;
		do {
			System.out.println("\nOdaberite opciju:");
			System.out.println("1 - Rukovanje skakaonicama");
			System.out.println("2 - Kompleksni upiti");
			System.out.println("X - Izlazak iz programa");

			answer = sc.nextLine();

			switch (answer) {
			case "1":
				skakaonicaUIHandler.handleSkakaonicaMenu();
				break;
			case "2":
				complexQueryUIHandler.handleComplexQueryMenu();
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));

		sc.close();
	}

}
