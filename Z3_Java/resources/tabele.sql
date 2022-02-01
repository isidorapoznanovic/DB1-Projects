
drop table skok;
drop table skakac;
drop table skakaonica;
drop table drzava;

create table drzava (
    idd char(3) primary key,
    nazivd varchar2(35) not null
);

create table skakaonica (
    idsa char(6) primary key,
    nazivsa varchar2(35) not null,
    duzinasa integer,
	tipsa varchar(10) check (tipsa in ('srednja', 'normalna', 'velika')),
	idd char(3) references drzava not null
);

create table skakac (
    idsc int primary key,
    imesc varchar(20) not null,
    przsc varchar(20) not null,
    idd char(3) not null,
	titule int,
	pbsc float
);

create table skok (
	idsk char(4) primary key,
	idsc int references skakac,
	idsa char(6) references skakaonica,
    bduzina decimal(6,2),
    bstil decimal(6,2),
    bvetar decimal (6,2)
);
