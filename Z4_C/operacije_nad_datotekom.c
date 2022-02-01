#include "operacije_nad_datotekom.h"
#include <float.h>
#include <stdbool.h>

#define formatBool(b) ((b) ? "true" : "false")

FILE *otvoriDatoteku(char *filename) {
	FILE *fajl = fopen(filename, "rb+");
	if (fajl == NULL) {
		printf("Doslo je do greske! Moguce da datoteka \"%s\" ne postoji.\n", filename);
	} else {
		printf("Datoteka \"%s\" otvorena.\n", filename);
	}
	return fajl;
}

void kreirajDatoteku(char *filename) {
	FILE *fajl = fopen(filename, "wb");
	if (fajl == NULL) {
		printf("Doslo je do greske prilikom kreiranja datoteke \"%s\"!\n", filename);
	} else {
		//upisi pocetni blok
		BLOK blok;
		blok.slogovi[0].sifraUverenja = OZNAKA_KRAJA_DATOTEKE;
		fwrite(&blok, sizeof(BLOK), 1, fajl);
		printf("Datoteka \"%s\" uspesno kreirana.\n", filename);
		fclose(fajl);
	}
}

SLOG *pronadjiSlog(FILE *fajl, int sifraUverenja) {
	if (fajl == NULL) {
		return NULL;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;

	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == -1) {
				//nema vise slogova
				return NULL;
			}

			if (blok.slogovi[i].sifraUverenja == sifraUverenja && !blok.slogovi[i].deleted) {
                //Ako se evidBroj poklapa i slog NIJE logicki obrisan
                //(logicki obrisane slogove tretiramo kao da i ne
                //postoje u datoteci).

				SLOG *slog = (SLOG *)malloc(sizeof(SLOG));
				memcpy(slog, &blok.slogovi[i], sizeof(SLOG));
				return slog;
			}
		}
	}

	return NULL;
}

void dodajSlog(FILE *fajl, SLOG *slog) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	SLOG *slogStari = pronadjiSlog(fajl, slog->sifraUverenja);
	if (slogStari != NULL) {
        //U datoteci vec postoji slog sa tim evid. brojem,
        //pa ne mozemo upisati novi slog.
        printf("Vec postoji slog sa tim evid brojem!\n");
        return;
    }

	BLOK blok;
	fseek(fajl, -sizeof(BLOK), SEEK_END); //u poslednji blok upisujemo novi slog
	fread(&blok, sizeof(BLOK), 1, fajl);

	int i;
	for (i = 0; i < FBLOKIRANJA; i++) {
		if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
            //Ovo je mesto gde se nalazi slog sa oznakom
            //kraja datoteke; tu treba upisati novi slog.
			memcpy(&blok.slogovi[i], slog, sizeof(SLOG));
			break;
		}
	}

	i++; //(na to mesto u bloku cemo upisati krajDatoteke)

	if (i < FBLOKIRANJA) {
        //Jos uvek ima mesta u ovom bloku, mozemo tu smestiti slog
        //sa oznakom kraja datoteke.
		blok.slogovi[i].sifraUverenja = OZNAKA_KRAJA_DATOTEKE;

		//Sada blok mozemo vratiti u datoteku.
		fseek(fajl, -sizeof(BLOK), SEEK_CUR);
		fwrite(&blok, sizeof(BLOK), 1, fajl);
		fflush(fajl);
	} else {
		//Nema vise mesta u tom bloku, tako da moramo
        //praviti novi blok u koji cemo upisati slog
        //sa oznakom kraja datoteke.

		//Prvo ipak moramo vratiti u datoteku blok
        //koji smo upravo popunili:
		fseek(fajl, -sizeof(BLOK), SEEK_CUR);
		fwrite(&blok, sizeof(BLOK), 1, fajl);
		//fflush(fajl);

		//Okej, sad pravimo novi blok:
		BLOK noviBlok;
		noviBlok.slogovi[0].sifraUverenja = OZNAKA_KRAJA_DATOTEKE;
		//(vec smo na kraju datoteke, nema potrebe za fseekom)
		fwrite(&noviBlok, sizeof(BLOK), 1, fajl);
		//fflush(fajl);
	}

	if (ferror(fajl)) {
		printf("Greska pri upisu u fajl!\n");
	} else {
		printf("Upis novog sloga zavrsen.\n");
	}
}

float prosekUverenja2021(FILE *fajl) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return -1;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	int n = 0;
	float suma = 0;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}

			if (!blok.slogovi[i].deleted) {
				if(strstr((&blok.slogovi[i])->datum, "2021") != NULL){
					suma += (&blok.slogovi[i])->cena;
					n++;
				}
            }
		}

		rbBloka++;
	}

	float prosek = (n == 0) ? -1 : suma/n;
	return prosek;
}

void ispisiSveSlogove(FILE *fajl) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	printf("BL SL Sif.Uverenja Prz.Mehanicara Datum      Cena        Prz.Vlasnika Ozn.Vrste.Vozila\n");
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
				printf("B%d S%d *\n", rbBloka, i);
                break; //citaj sledeci blok
			}


			if (!blok.slogovi[i].deleted) {
                printf("B%d S%d ", rbBloka, i);
                ispisiSlog(&blok.slogovi[i]);
                printf("\n");
            }
		}

		rbBloka++;
	}
}

void ispisiSlog(SLOG *slog) {
	printf("% 12d     %10s %10s %8.2f   %10s    %2s",
        slog->sifraUverenja,
		slog->prezimeMehanicara,
		slog->datum,
		slog->cena,
		slog->prezimeVlasnika,
		slog->oznakaVrsteVozila);
}

void vlasniciNajskupljaUverenja(FILE *fajl){
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	char a[100][11];
	int n = 0;
	bool postoji = false;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}

			if (!blok.slogovi[i].deleted) {
				for(int j = 0; j < n; j++){
            		//printf("%s, %s, %s, i = %d, blok = %d\n", &blok.slogovi[i].prezimeVlasnika, formatBool(postoji), &a[j], i, rbBloka);
					if(strcmp(blok.slogovi[i].prezimeVlasnika, a[j]) == 0){
						postoji = true;
					}
				}
				if(!postoji){
					long trenutnaPozicija = ftell(fajl);
					strcpy(a[n], blok.slogovi[i].prezimeVlasnika);
					maksimalnaCenaVlasnika(fajl, blok.slogovi[i].prezimeVlasnika);
					fseek(fajl, trenutnaPozicija, SEEK_SET);
					n++;
				}
            }
			postoji = false;
		}

		rbBloka++;
	}
}

float maksimalnaCenaVlasnika(FILE *fajl, char* vlasnik){
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return -1;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	float max = FLT_MIN;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}

			if (!blok.slogovi[i].deleted && strcmp(blok.slogovi[i].prezimeVlasnika, vlasnik) == 0) {
            	if((&blok.slogovi[i])->cena > max){
					max = (&blok.slogovi[i])->cena;
					//printf("%f\n", max);
				}
            }
		}

		rbBloka++;
	}
	printf("%s ima uverenje/a sa maksimalnom cenom %f: ", vlasnik, max);
	uverenjeSaMaxCenom(fajl, max);
	return max;
}

void uverenjeSaMaxCenom(FILE *fajl, float max){
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}

			if (!blok.slogovi[i].deleted && (&blok.slogovi[i])->cena == max) {
            	printf("%d ", (&blok.slogovi[i])->sifraUverenja);
            }
		}

		rbBloka++;
	}
	printf("\n");
}

float nadjiMaksimalnuCenu(FILE *fajl){
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return -1;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	float max = FLT_MIN;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}

			if (!blok.slogovi[i].deleted) {
            	if((&blok.slogovi[i])->cena > max){
					max = (&blok.slogovi[i])->cena;
				}
            }
		}

		rbBloka++;
	}
	return max;
}


void logickiObrisiSlogoveSaCenom(FILE *fajl, float max) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	int rbBloka = 0;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {
			if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
                break; //citaj sledeci blok
			}

			if (!blok.slogovi[i].deleted && (&blok.slogovi[i])->cena == max) {
				obrisiSlogLogicki(fajl, blok.slogovi[i].sifraUverenja);
            }
		}

		rbBloka++;
	}
}


void obrisiSlogLogicki(FILE *fajl, int sifraUverenja) {
	if (fajl == NULL) {
		printf("Datoteka nije otvorena!\n");
		return;
	}

	fseek(fajl, 0, SEEK_SET);
	BLOK blok;
	while (fread(&blok, sizeof(BLOK), 1, fajl)) {

		for (int i = 0; i < FBLOKIRANJA; i++) {

            if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {
                printf("Nema tog sloga u datoteci\n");
                return;
            }

			if (blok.slogovi[i].sifraUverenja == sifraUverenja && !blok.slogovi[i].deleted) {

				blok.slogovi[i].deleted = 1;
				fseek(fajl, -sizeof(BLOK), SEEK_CUR);
				fwrite(&blok, sizeof(BLOK), 1, fajl);
				fflush(fajl);

                printf("Slog je logicki obrisan.\n");
				return;
			}
		}
	}
}

void obrisiSlogFizicki(FILE *fajl, int sifraUverenja) {

    SLOG *slog = pronadjiSlog(fajl, sifraUverenja);
    if (slog == NULL) {
        printf("Slog koji zelite obrisati ne postoji!\n");
        return;
    }

    //Trazimo slog sa odgovarajucom vrednoscu kljuca, i potom sve
    //slogove ispred njega povlacimo jedno mesto unazad.

    BLOK blok, naredniBlok;
    int sifraUverenjaZaBrisanje;
    sifraUverenjaZaBrisanje = sifraUverenja;
    //char evidBrojZaBrisanje[8+1];
    //strcpy(evidBrojZaBrisanje, evidBroj);

    fseek(fajl, 0, SEEK_SET);
    while (fread(&blok, 1, sizeof(BLOK), fajl)) {
        for (int i = 0; i < FBLOKIRANJA; i++) {

            if (blok.slogovi[i].sifraUverenja == OZNAKA_KRAJA_DATOTEKE) {

                if (i == 0) {
                    //Ako je oznaka kraja bila prvi slog u poslednjem bloku,
                    //posle brisanja onog sloga, ovaj poslednji blok nam
                    //vise ne treba;
                    printf("(skracujem fajl...)\n");
                    fseek(fajl, -sizeof(BLOK), SEEK_END);
					long bytesToKeep = ftell(fajl);
					ftruncate(fileno(fajl), bytesToKeep);
					fflush(fajl); //(da bi se primenile promene usled poziva truncate)
                }

                printf("Slog je fizicki obrisan.\n");
                return;
            }

            if (blok.slogovi[i].sifraUverenja == sifraUverenjaZaBrisanje) {

                //Obrisemo taj slog iz bloka tako sto sve slogove ispred njega
                //povucemo jedno mesto unazad.
                for (int j = i+1; j < FBLOKIRANJA; j++) {
                    memcpy(&(blok.slogovi[j-1]), &(blok.slogovi[j]), sizeof(SLOG));
                }

                //Jos bi hteli na poslednju poziciju u tom bloku upisati prvi
                //slog iz narednog bloka, pa cemo zato ucitati naredni blok...
                int podatakaProcitano = fread(&naredniBlok, sizeof(BLOK), 1, fajl);

                //...i pod uslovom da uopste ima jos blokova posle trenutnog...
                if (podatakaProcitano) {

                    //(ako smo nesto procitali, poziciju u fajlu treba vratiti nazad)
                    fseek(fajl, -sizeof(BLOK), SEEK_CUR);

                    //...prepisati njegov prvi slog na mesto poslednjeg sloga u trenutnom bloku.
                    memcpy(&(blok.slogovi[FBLOKIRANJA-1]), &(naredniBlok.slogovi[0]), sizeof(SLOG));

                    //U narednoj iteraciji while loopa, brisemo prvi slog iz narednog bloka.
                    //strcpy(sifraUverenjaZaBrisanje, naredniBlok.slogovi[0].sifraUverenja);
                    sifraUverenjaZaBrisanje = naredniBlok.slogovi[0].sifraUverenja;
                }

                //Vratimo trenutni blok u fajl.
                fseek(fajl, -sizeof(BLOK), SEEK_CUR);
                fwrite(&blok, sizeof(BLOK), 1, fajl);
                fflush(fajl);

                if (!podatakaProcitano) {
                    //Ako nema vise blokova posle trentnog, mozemo prekinuti algoritam.
                    printf("Slog je fizicki obrisan.\n");
                    return;
                }

                //To je to, citaj sledeci blok
                break;
            }

        }
    }
}
