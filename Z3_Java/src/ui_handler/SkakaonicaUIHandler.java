package ui_handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Skakaonica;
import service.SkakaonicaService;

public class SkakaonicaUIHandler {
	private static final SkakaonicaService skakaonicaService = new SkakaonicaService();
	
	public void handleSkakaonicaMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad skakaonicama:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jedne skakaonice");
			System.out.println("4 - Unos vise skakaonica");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja skakaonicama");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				showAll();
				break;
			case "2":
				showById();
				break;
			case "3":
				handleSingleInsert();
				break;
			case "4":
				handleMultipleInserts();
				break;
			case "5":
				handleUpdate();
				break;
			case "6":
				handleDelete();
				break;
			case "7":
				handleExistById();
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));
	}

	private void showAll() {
		System.out.println(Skakaonica.getFormattedHeader());

		try {
			for (Skakaonica a : skakaonicaService.findAll()) {
				System.out.println(a);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	//TODO koristi existsById
	private void showById() {
		System.out.println("Unesite ID skakaonice: ");
		String id = MainUIHandler.sc.nextLine();

		try {
			Skakaonica skakaonica = skakaonicaService.findById(id);
			System.out.println(Skakaonica.getFormattedHeader());
			System.out.println(skakaonica);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//TODO koristi existsById
	private void handleSingleInsert() {
		System.out.println("ID skakaonice: ");
		String id = MainUIHandler.sc.nextLine();

		System.out.println("Naziv: ");
		String name = MainUIHandler.sc.nextLine();

		System.out.println("Duzina: ");
		int duzina = Integer.parseInt(MainUIHandler.sc.nextLine());

		System.out.println("Tip(srednja/normalna/velika): ");
		String tip = MainUIHandler.sc.nextLine();

		System.out.println("Id drzave: ");
		String idDrzave = MainUIHandler.sc.nextLine();

		try {
			skakaonicaService.save(new Skakaonica(id, name, duzina, tip, idDrzave));
			System.out.println("Dodavanje uspesno.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//TODO koristi existsById
	private void handleMultipleInserts() {
		List<Skakaonica> skakaonicaList = new ArrayList<>();
		String answer;
		do {
			System.out.println("IDSKAKAONICE: ");
			String id = MainUIHandler.sc.nextLine();

			System.out.println("Naziv: ");
			String name = MainUIHandler.sc.nextLine();

			System.out.println("Duzina: ");
			int duzina = Integer.parseInt(MainUIHandler.sc.nextLine());

			System.out.println("Tip(srednja/normalna/velika): ");
			String tip = MainUIHandler.sc.nextLine();

			System.out.println("Id drzave: ");
			String idDrzave = MainUIHandler.sc.nextLine();

			skakaonicaList.add(new Skakaonica(id, name, duzina, tip, idDrzave));

			System.out.println("Prekinuti unos? X za potvrdu");
			answer = MainUIHandler.sc.nextLine();
		} while (!answer.equalsIgnoreCase("X"));

		try {
			int rowsSaved = skakaonicaService.saveAll(skakaonicaList);
			System.out.println("Uspesno ažurirano " + rowsSaved + " skakaonica.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//TODO koristi existsById
	private void handleUpdate() {
		System.out.println("ID skakaonice: ");
		String id = MainUIHandler.sc.nextLine();

		try {
			if (!skakaonicaService.existsById(id)) {
				System.out.println("Uneta vrednost ne postoji!");
				return;
			}

			System.out.println("Naziv: ");
			String name = MainUIHandler.sc.nextLine();

			System.out.println("Duzina: ");
			int duzina = Integer.parseInt(MainUIHandler.sc.nextLine());

			System.out.println("Tip(srednja/normalna/velika): ");
			String tip = MainUIHandler.sc.nextLine();

			System.out.println("Id drzave: ");
			String idDrzave = MainUIHandler.sc.nextLine();


			boolean success = skakaonicaService.save(new Skakaonica(id, name, duzina, tip, idDrzave));
			if (success) {
				System.out.println("Skakaonica uspesno izmenjena.");
			} else {
				System.out.println("Izmena skakaonice nije uspela.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void handleDelete() {
		System.out.println("ID skakaonice: ");
		String id = MainUIHandler.sc.nextLine();
	
		try {
			boolean success = skakaonicaService.deleteById(id);
			if (success) {
				System.out.println("Skakaonica uspesno obrisana.");
			} else {
				System.out.println("Brisanje skakaonice nije uspelo.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//TODO zasto ne radi za nove skakaonice!?
	private void handleExistById() {
		System.out.println("ID skakaonice: ");
		String id = MainUIHandler.sc.nextLine();
		
		try {
			boolean success = skakaonicaService.existsById(id);
			if (success) {
				System.out.println("Skakaonica postoji.");
			} else {
				System.out.println("Skakaonica ne postoji.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
