package ui_handler;

import java.sql.SQLException;
import java.util.List;

import dto.complexquery1.SkakaonicaSaSkokovimaDTO;
import dto.complexquery2.DrzaveSaSkokovimaDTO;
import dto.complexquery3.TehnikalijeTransakcijeDTO;
import dto.complexquery4.SkakaonicaSaDrzavom;
import model.Drzava;
import model.Skakaonica;
import model.Skok;
import service.ComplexFuncionalityService;
//import service.SkokService;

public class ComplexQueryUIHandler {

	private static final ComplexFuncionalityService complexQueryService = new ComplexFuncionalityService();
	//private static final SkokService skokService = new SkokService();

	public void handleComplexQueryMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite funkcionalnost:");
			System.out.println(
					"\n1 - Uneti  IDSA  (identifikaciona  oznaka skakaonice) "
							+ "\n    Prikazati sve skokove koji su vrseni na skakaonici sa tim IDSA. Nakon liste skokova, prikazati i broj razlicitih skakaca koji su izvodili te skokove.");
			System.out.println(
					"\n2 - Prikazati podatke o svakoj drzavi. Za svaku drzavu  treba  prikazati  i  sve  skokove "
							+ "\n    koje  su  skakaci  iz  te  drzave  izvodili  na skakaonicama iz te iste drzave");
			System.out.println(
					"\n3 - Implementirati  funkciju  koja  ce  omoguciti  izmenu  vrednosti  obelezja BVETAR (korekcija  bodova  spram  vetra)  za  odabrani  skok.  "
							+ "\n    U  slucaju  da  nova vrednost ukupnog broja bodova za skok (dobijena po formuli BUKUPNO = BSTIL + BDUZINA  +  BVETAR)  "
							+ "\n    prevazilazi  vrednost  najboljeg  ostvarenog  broja  bodova skakaca  (obelezje  PBSC  u  tabeli  Skakac), "
							+ "\n    treba  azurirati  i  vrednost  najboljeg ostvarenog broja bodova (postaviti je na tu novu izracunatu vrednost)");
			System.out.println(
					"\n4 - Implementirati  izvestaj  koji  ce  za  unete  granice  duzine  skakaonice (minimalna  i  maksimalna  dužina)  prikazati"
							+ "\n    sve  skakaonice  cija  je  duzina  izmedju zadatih  granica.  Za  svaku  od  ovih  skakaonica  prikazati  i  "
							+ "\n    naziv  drzave  u  kojoj  se skakaonica nalazi.");
			
			System.out.println("\nX - Izlazak iz kompleksnih upita");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				zad3();
				break;
			case "2":
				zad4();
				break;
			case "3":
				zad5();
				break;	
			case "4":
				zad6();
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));
	}

	//TODO sredi ispis za nepostojecu stazu
	private void zad3() {
		System.out.println("Unesite ID skakaonice");
		String id = MainUIHandler.sc.nextLine();
		try {
			SkakaonicaSaSkokovimaDTO dto = complexQueryService.zad3(id);
			if(dto.getSkakaonica()!=null){
				System.out.println("-------------------------------SKAKAONICA-------------------------------");
				System.out.println();
				System.out.println(Skakaonica.getFormattedHeader());
				System.out.println(dto.getSkakaonica());
				System.out.println();
				System.out.println("--------------------------------SKOKOVI---------------------------------");
				System.out.println();
				System.out.print("                  ");
				System.out.println(Skok.getFormattedHeader());
				for(Skok s: dto.getSkokovi()){
					System.out.print("                  ");
					System.out.println(s);
				}
				System.out.println();
				System.out.println("------------------------BROJ RAZLICITIH SKAKACA-------------------------");
				System.out.println();
				System.out.print("                                    ");
				System.out.println(dto.getBrSkakaca());
				System.out.println();
				System.out.println("------------------------------------------------------------------------");
			}else{
				System.out.println("Uneli ste nepostojeci id skakaonice...");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void zad4() {
		try {
			List<DrzaveSaSkokovimaDTO> dtos = complexQueryService.zad4();
			for(DrzaveSaSkokovimaDTO dto : dtos){
				System.out.println("-------------------------------DRZAVA-------------------------------");
				System.out.println(Drzava.getFormattedHeader());
				System.out.println(dto.getDrzava());
				if(dto.getSkokovi().isEmpty()){
					System.out.println("Nema skakaca koji zadovoljavaju uslove pretrage");
				}else{
					System.out.println("------------------------------SKOKOVI-------------------------------");
					System.out.println(Skok.getFormattedHeader());
				
					for(Skok s : dto.getSkokovi()){
						System.out.println(s);
					}
				}
			}
			System.out.println("--------------------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//TODO ne daj idSkoka cf-servisu vec skok-servisu pa ispisi je l postoji pa napravi dto
	private void zad5(){
		System.out.println("Unesite ID skoka");
		String idSkok = MainUIHandler.sc.nextLine();
		System.out.println("Unesite bodove spram vetra");
		int bvetar = Integer.parseInt(MainUIHandler.sc.nextLine());
		try {
			TehnikalijeTransakcijeDTO dto = complexQueryService.zad5(idSkok, bvetar);
			if(dto.isPostojiSkok()){
				System.out.print("Izabrali ste skok: ");
				System.out.println(dto.getSkokStari());
				System.out.print("Izabrani skok je izvrsio: ");
				System.out.println(dto.getSkakac());
				System.out.println();
				System.out.println("STANJE NAKON TRANSAKCIJE");
				System.out.print("Izmenjeni skok: ");
				System.out.println(dto.getSkokIzmenjen());
				if(dto.isPromenjenIgrac()){
					System.out.print("Izmenjeni skakac: ");
					System.out.println(dto.getSkakacNovi());
				}else{
					System.out.println("Nije ostvaren novi rekord");
				}
			}else{
				System.out.println("Izabrali ste nepostojeci skok!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void zad6(){
		System.out.println("Unesite minimalnu duzinu staze");
		int a = Integer.parseInt(MainUIHandler.sc.nextLine());
		System.out.println("Unesite maksimalnu duzinu staze");
		int b = Integer.parseInt(MainUIHandler.sc.nextLine());

		try {
			List<SkakaonicaSaDrzavom> dtos = complexQueryService.zad6(a, b);
			if(!dtos.isEmpty()){
				System.out.println();
				System.out.println(Skakaonica.getFormattedHeader() + "   NAZIVDRZAVE ");
				System.out.println();
				for(SkakaonicaSaDrzavom dto: dtos){
					System.out.print(dto.getSkakaonica());
					System.out.println("   " + dto.getDrzava().getnaziv());
				}
			}else{
				System.out.println("Ne postoji skakaonica duzine iz datog opega!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
