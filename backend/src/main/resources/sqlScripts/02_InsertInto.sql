--Test podaci
insert into "preduzece"("id","naziv","pib","sediste","opis")
values (-100,'Test proizvodjac',1999,'test','test');

insert into "obrazovanje"("id","naziv","stepen_strucne_spreme","opis")
values (-100,'test','test','test');

insert into "sektor"("id","naziv","oznaka","preduzece")
values (-100,'test','test',1);

insert into "radnik"("id","ime","prezime","broj_lk","obrazovanje","sektor")
values (-100,'test','test',657452,1,1);


--obrazovanje podaci

insert into "obrazovanje"("id","naziv","stepen_strucne_spreme","opis")
values (1,'Gimnazija','gimnazija','Teritorija Republike Srpske');

insert into "obrazovanje"("id","naziv","stepen_strucne_spreme","opis")
values (2,'Ekonomska skola','4.','Teritorija Republike Srpske');

insert into "obrazovanje"("id","naziv","stepen_strucne_spreme","opis")
values (3,'Medicinska skola','4.','teritorija RS');

insert into "obrazovanje"("id","naziv","stepen_strucne_spreme","opis")
values (4,'Frizerska skola','4.','teritorija RS');

--preduzece podaci
insert into "preduzece"("id","naziv","pib","sediste","opis")
values (1,'Bosch DOO',2203,'Simanovci','Prizvodnja i distribucija');

insert into "preduzece"("id","naziv","pib","sediste","opis")
values (2,'Hutchinson',0609,'Ruma','Aerospace servisi');

insert into "preduzece"("id","naziv","pib","sediste","opis")
values (3,'DevoTeam',1912,'Novi Sad', 'IT consulting');

insert into "preduzece"("id","naziv","pib","sediste","opis")
values (4,'NIS',4462,'Novi Sad', 'Naftna indnustrija');

--sektor podaci
insert into "sektor"("id","naziv","oznaka","preduzece")
values (1,'Logistika','L',1);
insert into "sektor"("id","naziv","oznaka","preduzece")
values (2,'Ljudski resursi','HR',2);
insert into "sektor"("id","naziv","oznaka","preduzece")
values (3,'Saelsforce','SF',3);
insert into "sektor"("id","naziv","oznaka","preduzece")
values (4,'Marketing','M',4);

--radnik podaci
insert into "radnik"("id","ime","prezime","broj_lk","obrazovanje","sektor")
values (10,'Nadja','Pohan',7642589,1,1);
insert into "radnik"("id","ime","prezime","broj_lk","obrazovanje","sektor")
values (2,'Neda','Zelenovic',2468751,1,2);
insert into "radnik"("id","ime","prezime","broj_lk","obrazovanje","sektor")
values (3,'Anja','Novakovic',7512368,2,3);
insert into "radnik"("id","ime","prezime","broj_lk","obrazovanje","sektor")
values (4,'Aleksandar','Brocic',5324985,3,4);
insert into "radnik"("id","ime","prezime","broj_lk","obrazovanje","sektor")
values (5,'Aleksandar','Lazovic',7612589,4,4);
