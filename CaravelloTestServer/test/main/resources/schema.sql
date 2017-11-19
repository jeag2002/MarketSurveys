create table MarketData(
marketid int not null primary key auto_increment,
providerid varchar(10),
gender varchar(2),
age integer,
currency varchar(3),
income integer,
country varchar(3),
frecuency varchar(10),
channel varchar(10)
);