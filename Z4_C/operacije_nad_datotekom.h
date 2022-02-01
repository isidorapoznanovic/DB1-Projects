#ifndef OPERACIJE_NAD_DATOTEKOM_H
#define OPERACIJE_NAD_DATOTEKOM_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>

#include "definicije_struktura_podataka.h"

void kreirajDatoteku(char *filename);
void ispisiSlog(SLOG *slog);
void obrisiSlogLogicki(FILE *fajl, int sifraUverenja);

FILE *otvoriDatoteku(char *filename);
SLOG *pronadjiSlog(FILE *fajl, int sifraUverenja);
void dodajSlog(FILE *fajl, SLOG *slog);
void ispisiSveSlogove(FILE *fajl);
void obrisiSlogFizicki(FILE *fajl, int sifraUverenja);

float prosekUverenja2021(FILE *fajl);
float nadjiMaksimalnuCenu(FILE *fajl);
void logickiObrisiSlogoveSaCenom(FILE *fajl, float max);
void vlasniciNajskupljaUverenja(FILE *fajl);
float maksimalnaCenaVlasnika(FILE *fajl, char* vlasnik);
void uverenjeSaMaxCenom(FILE *fajl, float max);

#endif
