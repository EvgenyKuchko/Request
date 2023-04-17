INSERT INTO statuses
VALUES ( 1, 'на рассмотрении' );
INSERT INTO statuses
VALUES ( 2, 'одобрено' );
INSERT INTO statuses
VALUES ( 3, 'не одобрено' );
INSERT INTO statuses
VALUES ( 4, 'частично одобрено' );

INSERT INTO districts
VALUES ( 1, 'центральный' );
INSERT INTO districts
VALUES ( 2, 'восточный' );
INSERT INTO districts
VALUES ( 3, 'западный' );
INSERT INTO districts
VALUES ( 4, 'южный' );
INSERT INTO districts
VALUES ( 5, 'северный' );

INSERT INTO hunting_order_types
VALUES ( 1, 'массовые виды' );
INSERT INTO hunting_order_types
VALUES ( 2, 'жеребьёвочные виды' );

INSERT INTO resources
VALUES ( 1, 'ласка', 1, 50, '01.05.2023', '30.05.2023');
INSERT INTO resources
VALUES ( 2, 'горностай', 1, 70, '01.05.2023', '30.05.2023' );
INSERT INTO resources
VALUES ( 3, 'белка', 1, 50, '01.05.2023', '30.05.2023' );
INSERT INTO resources
VALUES ( 4, 'бурундук', 1, 20, '01.05.2023', '30.05.2023' );
INSERT INTO resources
VALUES ( 5, 'суслик', 1, 40, '01.05.2023', '30.05.2023' );
INSERT INTO resources
VALUES ( 6, 'волк', 2, 10, '15.05.2023', '15.06.2023' );
INSERT INTO resources
VALUES ( 7, 'медведь', 2, 15, '15.05.2023', '15.06.2023' );
INSERT INTO resources
VALUES ( 8, 'кабан', 2, 20, '15.05.2023', '15.06.2023' );
INSERT INTO resources
VALUES ( 9, 'рысь', 2, 20, '15.05.2023', '15.06.2023' );
INSERT INTO resources
VALUES ( 10, 'лось', 2, 10, '15.05.2023', '15.06.2023' );

INSERT INTO hunting_licenses
VALUES ( 1, '10.12.2022', 1512, 'AA' );
INSERT INTO hunting_licenses
VALUES ( 2, '05.11.2022', 4879, 'НН' );
INSERT INTO hunting_licenses
VALUES ( 3, '10.03.2023', 3589, 'ВР' );
INSERT INTO hunting_licenses
VALUES ( 4, '11.12.2022', 9874, 'ВA' );
INSERT INTO hunting_licenses
VALUES ( 5, '17.09.2022', 1785, 'AЕ' );
INSERT INTO hunting_licenses
VALUES ( 6, '16.12.2022', 1479, 'ЕЕ' );
INSERT INTO hunting_licenses
VALUES ( 7, '10.10.2022', 1999, 'AМ' );
INSERT INTO hunting_licenses
VALUES ( 8, '04.12.2022', 1745, 'AН' );
INSERT INTO hunting_licenses
VALUES ( 9, '04.08.2022', 1742, 'ТТ' );
INSERT INTO hunting_licenses
VALUES ( 10, '10.10.2022', 1572, 'КН' );

INSERT INTO persons
VALUES ( 1, 'Иванов Иван Иванович', 1 );
INSERT INTO persons
VALUES ( 2, 'Павлов Павел Павлович', 2 );
INSERT INTO persons
VALUES ( 3, 'Петров Пётр Петрович', 3 );
INSERT INTO persons
VALUES ( 4, 'Григорьев Григорий Григорьевич', 4 );
INSERT INTO persons
VALUES ( 5, 'Сергеев Сергей Сергеевич', 5 );
INSERT INTO persons
VALUES ( 6, 'Фёдоров Фёдор Фёдорович', 6 );
INSERT INTO persons
VALUES ( 7, 'Владов Владислав Владиславович', 7 );
INSERT INTO persons
VALUES ( 8, 'Алексеев Алексей Алексеевич', 8 );
INSERT INTO persons
VALUES ( 9, 'Артёмов Артём Артёмович', 9 );
INSERT INTO persons
VALUES ( 10, 'Витальев Виталий Витальевич', 10 );