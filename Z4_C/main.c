#include "stdio.h"
#include "stdlib.h"

#include "operacije_nad_datotekom.h"

// gcc *.c && ./a.out

int main() {

	int running = 1;
	int userInput;

	FILE *fajl = NULL;

	while (running) {
		printf("Odaberite opciju:\n");
		printf("1 - Otvaranje datoteke\n");
		printf("2 - Formiranje datoteke\n");
		printf("3 - Trazenje u datoteci\n");
		printf("4 - Unos sloga\n");
		printf("5 - Ispis svih slogova\n");
		printf("7 - Brisanje sloga (logicko)\n");
		printf("8 - Brisanje sloga (fiziko)\n");
		printf("9 - Ispisati prosecnu cenu uverenja izdatih u 2021 godini\n");
		printf("10 - Logicki obrisati slog (ili slogove, ako ih je vise) sa najvecom cenom\n");
		printf("11 - Za svakog vlasnika vozila ispisati najskuplje uverenje njemu izdato\n");
		printf("0 - Izlaz\n");
		if (fajl == NULL) {
			printf("!!! PAZNJA: datoteka jos uvek nije otvorena !!!\n");
		}
		scanf("%d", &userInput);
		getc(stdin);

		switch(userInput) {
			case 1:
				{
					char filename[20];
					printf("Unesite ime datoteke za otvaranje: ");
					scanf("%s", &filename[0]);
					fajl = otvoriDatoteku(filename);
					printf("\n");
					break;
				}
			case 2:
				{
					char filename[20];
					printf("Unesite ime datoteke za kreiranje: ");
					scanf("%s", filename);
					kreirajDatoteku(filename);
					printf("\n");
					break;
				}
			case 3:
				{
					int sifraUverenja;
					printf("Unesite sifru uverenja trazenog prijema: ");
					scanf("%d", &sifraUverenja);
					SLOG *slog = pronadjiSlog(fajl, sifraUverenja);
					if (slog == NULL) {
                        printf("Nema tog sloga u datoteci.\n");
					} else {
                        printf("Sif.Uverenja   Prz.Mehanicara      Datum       Cena       Prz.Vlasnika Ozn.Vrste.Vozila\n");
                        ispisiSlog(slog);
                        printf("\n");
					}
					free(slog);
					printf("\n");
					break;
				}
			case 4:
				{
					SLOG slog;
					printf("Sifra uverenja (8 cifara): ");
					scanf("%d", &slog.sifraUverenja);
					printf("Prezime mehanicara (10 karaktera): ");
					scanf("%s", slog.prezimeMehanicara);
					printf("Datum (dd-mm-YYYY): ");
					scanf("%s",slog.datum);
					printf("Cena (8 cifara): ");
					scanf("%f", &slog.cena);
					printf("Prezime vlasnika (10 karaktera): ");
					scanf("%s", slog.prezimeVlasnika);
					printf("Oznaka vrste vozila (tacno 2 karaktera): ");
					scanf("%s", slog.oznakaVrsteVozila);
					slog.deleted = 0;
					dodajSlog(fajl, &slog);
					printf("\n");
					break;
				}
			case 5:
				{
					ispisiSveSlogove(fajl);
					printf("\n");
					break;
				}
			case 7:
				{
					int sifraUverenja;
					printf("Unesite sifru uverenja sloga za logicko brisanje: ");
					scanf("%d", &sifraUverenja);
					obrisiSlogLogicki(fajl, sifraUverenja);
					printf("\n");
					break;
				}
			case 8:
				{
					int sifraUverenja;
					printf("Unesite sifru uverenja sloga za FIZICKO brisanje: ");
					scanf("%d", &sifraUverenja);
					obrisiSlogFizicki(fajl, sifraUverenja);
					printf("\n");
					break;
				}
			case 9:
				{
					float prosek = prosekUverenja2021(fajl);
					prosek == -1 ? printf("Nema slogova izdatih u 2021 godini\n") : printf("Prosek je: %f\n", prosek);
					printf("\n");
					break;
				}
			case 10:
				{
					float max = nadjiMaksimalnuCenu(fajl);
					logickiObrisiSlogoveSaCenom(fajl, max);
					printf("\n");
					break;
				}
			case 11:
				{
					vlasniciNajskupljaUverenja(fajl);
					printf("\n");
					break;
				}
			case 0:
				{
					running = 0;
					if (fajl != NULL) {
						fclose(fajl);
					}
				}
		}
	}

	return 0;

}




