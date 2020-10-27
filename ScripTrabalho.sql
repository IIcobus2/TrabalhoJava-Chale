CREATE DATABASE Chale;

USE Chale;


create table chale(
	idchale
		int
		PRIMARY KEY
		NOT NULL,
	localizacao
		varchar(50)
		NOT NULL,
	capacidade
		int
		NOT NULL,
	valoraltatemporada
		decimal(8,2)
		NOT NULL,
	valorbaixatemporada
		decimal(8,2)
		NOT NULL
);

INSERT INTO public.chale(
	idchale, localizacao, capacidade, valoraltatemporada, valorbaixatemporada)
	VALUES (1, 'Distrito Federal', 5, 206.40, 140.10);
	
INSERT INTO public.chale(
	idchale, localizacao, capacidade, valoraltatemporada, valorbaixatemporada)
	VALUES (2, 'Caldas Novas', 10, 2106.40, 940.10);	
	
	
create table cliente(
	idcliente
		int
		PRIMARY KEY
		NOT NULL,
	nomecliente
		varchar(50)
		NOT NULL,
	rgcliente
		varchar(50)
		NOT NULL,
	enderecocliente
		varchar(50)
		NOT NULL,
	bairrocliente
		varchar(50)
		NOT NULL,
	cidadecliente
		varchar(50)
		NOT NULL,
	estadocliente
		varchar(50)
		NOT NULL,
	cepcliente
		varchar(50)
		NOT NULL,
	nascimentocliente
		Date
		NOT NULL
);

INSERT INTO public.cliente(
	idcliente, nomecliente, rgcliente, enderecocliente, bairrocliente, cidadecliente, estadocliente, cepcliente, nascimentocliente)
	VALUES (1, 'Alexandre', '777777', 'QNN 03', 'Ceilandia Norte', 'Brasilia', 'DF', '7777777', '2001-03-14');
	
INSERT INTO public.cliente(
	idcliente, nomecliente, rgcliente, enderecocliente, bairrocliente, cidadecliente, estadocliente, cepcliente, nascimentocliente)
	VALUES (2, 'Arthur', '777777', 'OPJ 03', 'Corumba', 'Cidade de Corumba', 'GO', '7777777', '2000-03-14');

create table hospedagem(
	idhospedagem
		int
		PRIMARY KEY
		NOT NULL,
	idchale
		int,
	idcliente
		int,
	estado
		varchar(50)
		NOT NULL,
	datainicio
		date
		NOT NULL,
	datafim
		date
		NOT NULL,
	qtdpessoas
		int
		NOT NULL,
	desconto
		decimal(8,2)
		NOT NULL,
	valorfinal
		decimal(8,2)
		NOT NULL
);

INSERT INTO public.hospedagem(
	idhospedagem, idchale, estado, datainicio, datafim, qtdpessoas, desconto, valorfinal, idcliente)
	VALUES (1, 1, 'Ocupada', '2020-10-26', '2020-10-30', 5, 50.0 , 1000.0, 1);

INSERT INTO public.hospedagem(
	idhospedagem, idchale, estado, datainicio, datafim, qtdpessoas, desconto, valorfinal, idcliente)
	VALUES (2, 2, 'Reservada', '2020-10-26', '2020-10-30', 15, 50.0 , 1000.0, 2);