CREATE SCHEMA CRM;

CREATE TABLE CRM.KLIJENT (
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	ime VARCHAR(40) NOT NULL,
	prezime VARCHAR(40) NOT NULL,
	adresa VARCHAR(40) NOT NULL,
	mobitel VARCHAR(40) NOT NULL,
	email VARCHAR(40) NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO CRM.KLIJENT (ime, prezime, adresa, mobitel, email) VALUES ('Tomislav', 'Bradaè', 'Jankomir 10', '0989000440', 'tomislavbradac@yahoo.com'); 
INSERT INTO CRM.KLIJENT (ime, prezime, adresa, mobitel, email) VALUES ('Aldo', 'Topiæ', 'Tratinska 15', '0917894561', 'atopic@tvz.hr'); 
INSERT INTO CRM.KLIJENT (ime, prezime, adresa, mobitel, email) VALUES ('Marko', 'Horvat', 'Ilica 258', '0955654872', 'mhorvat@gmail.com'); 

SELECT * FROM CRM.KLIJENT;

CREATE TABLE CRM.POPRAVAK ( 
	id INT NOT NULL GENERATED ALWAYS AS IDENTITY, 
	naziv VARCHAR(40) NOT NULL,
	opis VARCHAR(500) NOT NULL,
	vozilo VARCHAR(500) NOT NULL,
	cijena DOUBLE NOT NULL,
	idKlijent int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (idKlijent) REFERENCES CRM.KLIJENT(id)
); 

INSERT INTO CRM.POPRAVAK (naziv, opis, vozilo, cijena, idKlijent) VALUES ('Veliki servis', 'promjena svega', 'VW Golf 5', 3599.00, 2); 
INSERT INTO CRM.POPRAVAK (naziv, opis, vozilo, cijena, idKlijent) VALUES ('Promjena ulja', 'stavljeno novo ulje koje je klijent donio', 'Škoda Fabia 1.9 SDI', 299.99, 1); 
INSERT INTO CRM.POPRAVAK (naziv, opis, vozilo, cijena, idKlijent) VALUES ('Krpanje gume', 'èavao u prednjoj desnoj gumi', 'Ferrari LaFerrari FXX', 599.50, 3); 

SELECT * FROM CRM.POPRAVAK;



