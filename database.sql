-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Czas generowania: 09 Lut 2022, 17:04
-- Wersja serwera: 8.0.26
-- Wersja PHP: 8.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `s401343`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `actual_rentals`
--

CREATE TABLE `actual_rentals` (
  `rid` int NOT NULL,
  `uid` int NOT NULL,
  `bid` int NOT NULL,
  `start_of_rental` date DEFAULT NULL,
  `end_of_rental` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Zrzut danych tabeli `actual_rentals`
--

INSERT INTO `actual_rentals` (`rid`, `uid`, `bid`, `start_of_rental`, `end_of_rental`) VALUES
(20, 3, 7, '2021-12-31', '2022-01-09'),
(22, 3, 2, '2022-01-01', '2022-01-10'),
(25, 1, 12, '2022-02-11', '2022-02-21'),
(26, 1, 8, '2022-02-10', '2022-02-23');

--
-- Wyzwalacze `actual_rentals`
--
DELIMITER $$
CREATE TRIGGER `check_max_rentals` BEFORE INSERT ON `actual_rentals` FOR EACH ROW BEGIN
        IF (SELECT count(uid) FROM actual_rentals WHERE uid = NEW.uid) >= 5 THEN
            SIGNAL SQLSTATE '02000' SET MESSAGE_TEXT = 'Rental limit (5)';
        END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `admin`
--

CREATE TABLE `admin` (
  `admin_id` int NOT NULL,
  `uid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Zrzut danych tabeli `admin`
--

INSERT INTO `admin` (`admin_id`, `uid`) VALUES
(1, 1),
(3, 2),
(4, 3),
(2, 4);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `books_list`
--

CREATE TABLE `books_list` (
  `bid` int NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `author` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `genre` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `target_group` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `year_of_publication` int NOT NULL,
  `number_of_copies` int NOT NULL,
  `number_of_pages` int NOT NULL,
  `publisher` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Zrzut danych tabeli `books_list`
--

INSERT INTO `books_list` (`bid`, `title`, `author`, `genre`, `target_group`, `year_of_publication`, `number_of_copies`, `number_of_pages`, `publisher`, `description`) VALUES
(1, 'Pan Tadeusz', 'Adam Mickiewicz', 'Epic', 'Everyone', 1834, 5, 296, 'Rea', 'Ta epopeja narodowa (z elementami gaw??dy szlacheckiej) powsta??a w latach 1832???1834 w Pary??u. Sk??ada si?? z dwunastu ksi??g pisanych wierszem, trzynastozg??oskowym aleksandrynem polskim. Czas akcji: pi???? dni z roku 1811 i jeden dzie?? z roku 1812. Epopeja jest sta???? pozycj?? na polskiej li??cie lektur szkolnych.'),
(2, 'Igrzyska ??mierci', 'Suzanne Collins', 'Dystopia', 'Teenagers', 2008, 3, 378, 'Media Rodzina', 'Na ruinach dawnej Ameryki P????nocnej rozci??ga si?? pa??stwo Panem, z imponuj??cym Kapitolem otoczonym przez dwana??cie dystrykt??w. Okrutne w??adze stolicy zmuszaj?? podleg??e sobie rejony do sk??adania upiornej daniny. Raz w roku ka??dy dystrykt musi dostarczy?? ch??opca i dziewczyn?? mi??dzy dwunastym a osiemnastym rokiem ??ycia, by wzi??li udzia?? w G??odowych Igrzyskach, turnieju na ??mier?? i ??ycie, transmitowanym na ??ywo przez telewizj??.'),
(4, 'Harry Potter i Kamie?? Filozoficzny', 'J.K. Rowling', 'Fantasy', 'Teenagers', 1997, 6, 320, 'Media Rodzina', 'W pierwszej cz????ci czytelnik dowiaduje si??, ??e r??wnolegle z naszym ??wiatem istnieje ??wiat alternatywny: ??wiat czarodziej??w. Niemagiczni ludzie (zwani mugolami) nie maj?? o nim poj??cia. Jednak ci, kt??rzy s?? obdarzeni talentem magicznym, ucz??szczaj?? do specjalnych szk????, a potem pracuj?? w magicznych zawodach. Nasz tytu??owy bohater Harry to sierota, od male??ko??ci wychowywany przez ciotk?? i wuja. W wieku 11 lat dowiaduje si??, ??e jego rodzice byli czarodziejami, a on sam ma niebawem podj???? nauk?? w najlepszej szkole dla czarodziej??w. W Hogwarcie Harry poznaje prawdziwych przyjaci????, ale zyskuje te?? wrog??w. Co najwa??niejsze ??? dowiaduje si??, ??e zab??jca jego rodzic??w, Lord Voldemort, wci???? ??yje i planuje powr??t. Poniewa?? ka??da cz?????? Harry???ego Pottera kryje w sobie zagadk??, w tomie pierwszym odkrywamy tajemnic?? pewnego profesora oraz jego zwi??zek z Voldemortem.'),
(5, 'Harry Potter i Komnata Tajemnic', 'J.K. Rowling', 'Fantasy', 'Teenagers', 1998, 4, 368, 'Media Rodzina', 'Ta cz?????? obejmuje drugi rok nauki Harry???ego w Hogwarcie. Cho?? mo??e si?? to nam wydawa?? niewiarygodne, Harry nie mo??e doczeka?? si?? ko??ca wakacji i powrotu do szko??y. T??skni za swoimi najlepszymi przyjaci????mi, Ronem Wesleyem i Hermion?? Granger. Hogwart jest jego prawdziwym domem. W drugiej z kolejno??ci cz????ci sagi dowiadujemy si?? wi??cej o pochodzeniu i dzieci??stwie Voldemorta, jego zwi??zku z w????ami i z legendarnym Slytherinem, jednym z czterech za??o??ycieli Hogwartu. Poniewa?? ka??dy tom cyklu kryje w sobie przygod?? i tajemnic??, to i druga cz?????? Harry???ego Pottera pe??na jest wartkiej akcji zmierzaj??cej do rozwik??ania zagadki z przesz??o??ci.'),
(6, 'Harry Potter i wi??zie?? Azkabanu', 'J.K. Rowling', 'Fantasy', 'Teenagers', 1999, 2, 448, 'Media Rodzina', 'To w trzecim tomie Harry poznaje swojego jedynego krewnego i zyskuje w nim opiekuna i przyjaciela. Na scen?? wkracza bowiem Syriusz Black, kt??ry uchodzi za s??ug?? Voldemorta, uciekinier z Azkabanu, czyli wi??zienia strze??onego przez zatrwa??aj??cych dementor??w.  Wszyscy w????cznie z Harrym s?? przekonani, ??e Black zdradzi?? rodzic??w Harry???ego. Tymczasem Syriusz opowiada ca??kiem inn?? histori??.'),
(7, 'Harry Potter i Czara Ognia', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2000, 2, 766, 'Media Rodzina', 'Czwarta z kolei cz?????? cyklu wysz??a drukiem w Anglii w  2000 roku, a w Polsce jesieni?? 2001. Czwarta cz?????? wyj??tkowo raduje fan??w Harry???ego Pottera, poniewa?? jest znacznie wi??ksza obj??to??ciowo, a poza tym rozpoczyna si?? imponuj??cym wydarzeniem sportowym: mistrzostwami ??wiata w quidditchu. Rok szkolny przynosi te?? wiele emocji, bo nasz bohater bierze udzia?? w niebezpiecznym Turnieju Tr??jmagicznym, do kt??rego w og??le nie powinien si?? zakwalifikowa??.  Najwyra??niej komu?? bardzo zale??y na tym, ??eby Harry podj???? si?? trzech zagra??aj??cych ??yciu zada??. Czwarta cz?????? sagi jest znacznie mroczniejsza od poprzednich, poniewa?? kto?? bliski Harry???emu pada ofiar?? turnieju. Ponadto najgro??niejszy przeciwnik Harry???ego, sam Lord Voldemort, odradza si?? w ca??ej pe??ni i odzyskuje moc. Po tej cz????ci nie spos??b ju?? nie przeczyta?? kolejnych.'),
(8, 'Harry Potter i Zakon Feniksa', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2003, 4, 960, 'Media Rodzina', 'Pi??t?? cz????ci?? ???Harry???ego Pottera??? po kolei jest ???Zakon Feniksa??? z 2003 roku, kt??ry w Polsce wyszed?? w roku 2004. W tym tomie Harry, Ron i Hermiona do????czaj?? do Zakonu Feniksa, aby wsp??lnie z doros??ymi czarodziejami pokona?? Voldemorta. Do tej organizacji niegdy?? nale??eli rodzice Harry???ego oraz sam Dumbledore. Potrzeba wskrzeszenia Zakonu jest wielka, gdy?? zagro??enie ze strony  Voldemorta i ??miercio??erc??w staje si?? namacalne. Z tej cz????ci Harry???ego Pottera pochodzi chyba najbardziej znienawidzona nauczycielka wszech czas??w ??? Dolores Umbridge. Zostaje ona Wielkim Inkwizytorem i zast??puje Dumbledore???a na stanowisku dyrektora.'),
(9, 'Harry Potter i Ksi?????? P????krwi', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2005, 2, 704, 'Media Rodzina', 'W sz??stej cz????ci cyklu Dumbledore anga??uje Harry???ego do poszukiwa?? horkruks??w. ??eby zbyt wiele nie zdradza??: s?? to magiczne przedmioty niezb??dne do pokonania Voldemorta. W sz??stej cz????ci Harry pos??uguje si?? podr??cznikiem do eliksir??w, kt??ry kiedy?? nale??a?? do niejakiego Ksi??cia P????krwi. Odr??czne notatki wskazuj??, ??e w??a??ciciel tej ksi????ki by?? geniuszem. Kto si?? kryje pod tym pseudonimem i jaka jest rola Ksi??cia P????krwi w historii Harry???ego?'),
(10, 'Harry Potter i Insygnia ??mierci', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2007, 3, 782, 'Media Rodzina', 'W tomie si??dmym  dochodzi do ostatecznego starcia Harry???ego z Voldemortem. Harry wie, ??e nie pokona Czarnego Pana, je??li wcze??niej nie zniszczy wszystkich horkruks??w, gdy?? to one stanowi?? o nie??miertelno??ci Czarnego Pana. Dlatego wraz z Ronem i Hermion?? opuszczaj?? Hogwart, gdzie rz??dz?? poplecznicy Voldemorta, i udaj?? si?? w misj?? odnalezienia horkruks??w. Ostatni?? cz?????? wie??czy wielka Bitwa o Hogwart. Niestety, bitwa przynosi ofiary.'),
(11, 'W pier??cieniu ognia', 'Suzanne Collins', 'Dystopia', 'Teenagers', 2009, 1, 360, 'Media Rodzina', 'Katniss i Peeta z racji wygrania ostatnich igrzysk s?? w trakcie obowi??zkowego Tournee Zwyci??zc??w. Wkr??tce okazuje si??, ??e wiele dystrykt??w zaczyna si?? buntowa?? przeciwko Kapitolowi. Prezydent Snow win?? za wszystko obarcza Katniss, kt??ra wraz z Peet?? z??ama??a zasady obowi??zuj??ce na arenie podczas igrzysk. Dla ludno??ci w dystryktach Katniss jest symbolem nadziei. Pragn??c j?? zniszczy??, prezydent Snow przygotowuje dla niej co?? specjalnego.'),
(12, 'Kosog??os', 'Suzanne Collins', 'Dystopia', 'Teenagers', 2010, 2, 372, 'Media Rodzina', 'Katniss, po ci????kich prze??yciach w ??wier??wieczu Poskromienia, zostaje przetransportowana do legendarnego 13 Dystryktu. Tam, mimo niech??ci, postanawia zosta?? Kosog??osem ??? symbolem rebelii. B??dzie musia??a uczestniczy?? w wojnie, kr??ci?? materia??y propagandowe i wreszcie rozprawi?? si?? z Kapitolem. B??dzie te?? musia??a wybiera?? mi??dzy dwiema mi??o??ciami: zagorza??ym zwolennikiem rewolucji Gale???em a opanowanym i romantycznym Peet??.'),
(13, 'Lew, czarownica i stara szafa', 'C.S. Lewis', 'Fantasy', 'Everyone', 1950, 6, 184, 'Media Rodzina', 'Jest oko??o 1940 roku, dopiero co rozpocz????a si?? druga wojna ??wiatowa, i czw??rka rodze??stwa Piotr, Zuzanna, Edmund i ??ucja ewakuuj?? si?? z bombardowanego Londynu. Zostaj?? wys??ani na wie?? do domu profesora Digory???ego Kirke, kt??ry mieszka tam wraz ze swoj?? gosposi??, Pani?? Macready. Pewnego deszczowego dnia, dzieci postanawiaj?? zbada?? dom. ??ucja, najm??odsza z rodze??stwa, zainteresowana szaf?? stoj??c?? w pustym pokoju odkrywa, ??e jest to portal do pokrytego ??niegiem lasu z latarni?? po??rodku. Spotyka tam fauna, kt??ry przedstawia si?? jako Tumnus i zaprasza j?? na herbat??. Opowiada jej, ??e owa kraina nazywa si?? Narni?? i jest rz??dzona przez bezwzgl??dn?? Bia???? Czarownic??, kt??ra sprawia, ??e panuje tu wieczna zima, ale nigdy nie ma ??wi??t.'),
(14, 'Ksi?????? Kaspian', 'C.S. Lewis', 'Fantasy', 'Everyone', 1951, 3, 224, 'Media Rodzina', 'Mija rok od wydarze?? opisanych w powie??ci Lew, czarownica i stara szafa. Piotr, Zuzanna, Edmund i ??ucja po sp??dzeniu bardzo mi??ych wakacji musz?? jecha?? do nowych szk???? ??? szk???? z internatem. Tym smutniejszy dla nich jest fakt, ??e bracia jad?? do innej szko??y, a siostry do innej, co oznacza, ??e rodze??stwo nie zobaczy si?? przez najbli??sze 10 miesi??cy. Nagle dzieci, siedz??c na ??awce, czuj??, ??e co?? ich k??uje w plecy. Po chwili jaka?? moc ci??gnie ich w nieznane. ??api?? si?? za r??ce i.... l??duj?? w Narnii. S?? uradowani. Trafili na wysp??, kt??rej pochodzenia nie mogli sobie przypomnie?? ??? wysp?? poro??ni??t?? puszcz??. Bawi?? si?? w wodzie, potem id?? dalej. Robi?? si?? g??odni, jedz?? kanapki, jakie przyszykowa??a im mama. Wreszcie syc?? g????d jab??kami z sadu, do kt??rego dziwnym trafem si?? dostali. Sad prowadzi ich do muru, kt??ry nale??y do ruin starego zamku. Dziwi?? si??, bowiem w Narnii nie by??o ??adnych ruin. Przekraczaj?? jednak ich mury.'),
(15, 'Podr???? W??drowca do ??witu', 'C.S. Lewis', 'Fantasy', 'Everyone', 1952, 2, 248, 'Media Rodzina', '??ucja, Edmund oraz ich niezno??ny kuzyn Eustachy za pomoc?? magicznego obrazu przenosz?? si?? na pok??ad ???W??drowca do ??witu???, gdzie ponownie spotykaj?? m??odego kr??la Narnii ??? Kaspiana. Musz?? pom??c mu w pr??bie odnalezienia dawno zaginionych przyjaci???? ojca, baron??w narnijskich (Revilian, Bern, Argoz, Mavramorn, Oktezjan, Restimar i Rup), kt??rych uzurpator Miraz wys??a?? na morze. W tym celu rozpoczynaj?? pe??n?? przyg??d podr???? a?? na ???ostateczny wsch??d???.'),
(16, 'Srebrne krzes??o', 'C.S. Lewis', 'Fantasy', 'Everyone', 1953, 1, 159, 'Media Rodzina', 'Eustachy Scrubb i jego szkolna kole??anka Julia Pole, uczniowie Eksperymentalnej Szko??y, zostaj?? wezwani przez Aslana do magicznej krainy Narnii z misj?? odnalezienia zaginionego od dziesi??ciu lat, kr??lewicza Narnii ??? Riliana. Jest on synem Kaspiana X, z kt??rym zaprzyja??ni?? si?? Eustachy podczas podr????y ???W??drowca do ??witu??? na koniec ??wiata.'),
(17, 'Ko?? i jego ch??opiec', 'C.S. Lewis', 'Fantasy', 'Everyone', 1954, 1, 148, 'Media Rodzina', 'Akcja ksi????ki rozgrywa si?? w Narnii, Archenlandii i Kalormenie za czas??w Piotra, Zuzanny, Edmunda i ??ucji. G????wnym bohaterem jest mieszkaj??cy w Kalormenie i przygarni??ty przez rybaka Arszisza Szasta. Pewnego dnia do ich chatki przybywa jeden z kalorme??skich wielmo????w, Tarkaan Anradin i chce kupi?? Szast?? od Arszisza. Ch??opiec przez przypadek pods??uchuje ich rozmow?? i postanawia uciec na koniu Anradina. Okazuje si?? i?? rumak o imieniu Bri jest m??wi??cym koniem i pochodzi z Narnii. Tam te?? obaj planuj?? uciec. Podczas ucieczki, uciekaj??c przed lwem, spotykaj?? innych uciekinier??w. S?? to: Tarkiina Arawis na swej m??wi??cej klaczy Hwin. Arawis, cho?? pochodzi z kalorme??skiej arystokracji, ucieka przera??ona czekaj??cym j?? ma????e??stwem ze starym wezyrem Ahoszt??.'),
(18, 'Siostrzeniec czarodzieja', 'C.S. Lewis', 'Fantasy', 'Everyone', 1955, 1, 132, 'Media Rodzina', 'Ksi????ka opisuje stworzenie Narnii przez Aslana, w czym uczestnicz?? Digory Kirke i Pola Plummer. Jest to pod wzgl??dem chronologii pierwsza cz?????? ???Narnii???. Dowiadujemy si??, jak powsta??a. Jak w poprzednich cz????ciach jest nawi??zanie do Biblii (Stworzenie Narnii przez Aslana, mo??emy por??wna?? do stworzenia ??wiata przez Boga).'),
(19, 'Ostatnia bitwa', 'C.S. Lewis', 'Fantasy', 'Everyone', 1956, 2, 135, 'Media Rodzina', 'Julia i Eustachy zostaj?? wezwani do Narnii by pom??c zaprowadzi?? pok??j kr??lowi Tirianowi, poniewa?? zacz????a tam rz??dzi?? si?? podst??pna ma??pa zwana Kr??taczem. Ubiera os??a ??amig????wka w sk??r?? lwa i ka??e mu udawa?? Aslana. Dla w??asnego zysku sprowadza Kalorme??czyk??w, pozwalaj??c na wyr??b m??wi??cych drzew i sprzedawanie mieszka??c??w Narnii jako niewolnik??w. Do tego stopnia m??ci wszystkim Narnijczykom w g??owach, i?? m??wi im, ??e Tasz (b??stwo Kalorme??czyk??w) i Aslan (w??adca Narnii) s?? jedn?? osob??.');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rental_history`
--

CREATE TABLE `rental_history` (
  `hid` int NOT NULL,
  `uid` int NOT NULL,
  `bid` int NOT NULL,
  `rental_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Zrzut danych tabeli `rental_history`
--

INSERT INTO `rental_history` (`hid`, `uid`, `bid`, `rental_date`) VALUES
(1, 3, 1, '2022-01-01'),
(2, 3, 1, '2022-01-20'),
(3, 3, 2, '2022-01-06'),
(4, 1, 11, '2022-01-05'),
(5, 3, 7, '2021-12-31'),
(6, 3, 17, '2022-01-15'),
(7, 3, 2, '2021-12-31'),
(8, 1, 8, '2022-01-26'),
(9, 1, 8, '2022-01-25'),
(10, 1, 12, '2022-02-11'),
(11, 1, 8, '2022-02-10');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `uid` int NOT NULL,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `class` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `salt` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_number` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`uid`, `name`, `surname`, `class`, `username`, `salt`, `password`, `email`, `phone_number`) VALUES
(1, 'Admin', 'Admin', NULL, 'admin', '[B@6242f5b3', 'fc4ca5f9602768d412735f049a110ee61fd0c04406cf47cf092638d07b6ed3d9', 'admin@admin.admin', 123456789),
(2, 'Anna', 'M??ynarczyk', '3F', 'amlynarczyk', '[B@177201c8', 'ec6faae2f8aa14b1a6430b8e83a1678c62987cba9ac810788e8e00c789e7eb1a', 'amlynarczyk@wp.pl', 555333999),
(3, 'Dawid', 'Kwapisz', '3C', 'dkwapisz', '[B@29fdae12', '869521ddf5844afbf28a325a3faa946ec6d920a02786e6b0cf5497da4d8b7dd7', 'dkwapisz@gmail.com', 555333444),
(4, 'Agnieszka', '??upnik', NULL, 'azupnik', '[B@4e203831', '6542ea25629ec736b2cd0df5626a11cae02c9d5503a991480a550ad1fa72d8c1', 'a@zupnik.pl', 123321123),
(5, 'Jan', 'Kowalski', '4D', 'jkowalski', '[B@563a744c', 'b55d3c88be2fe31451233cce8933004d2b5eecd104878aee581ad6d59efca15c', 'jan@wp.pl', 999222999),
(7, 'Pawe??', 'Nowak', '6D', 'pawelo_1', '[B@4ff67e4a', 'f4d872fe5949aa0bfd8a014da02792357b6ea326a2e5ec89e14fb01690fdde6f', 'pnowak@szkola.pl', 354724774),
(8, 'Magda', 'Kot', '2D', 'kot22', '[B@1081835c', 'c4f28202e411d2f6eb3346fae4a220fe3aff2ea85b80c2aa9c490e5529edfad6', 'magda321@gmail.com', 693555434),
(9, 'Aleksandra', '??ak', '4F', 'ola321', '[B@17586061', 'a76cc74e69752a146dc8afa8bebfb5bdfffca8a2c155689ad69557e0d8f3ae9b', 'ola321@wp.pl', 799531356),
(10, 'Micha??', 'Lewandowski', '1D', 'michalooo_pl', '[B@68b4ea50', '5d393b7472333dfb08464b31b53d91318c3b90aa9a9f3687197f0554be098c57', 'michalooo05@interia.pl', 742745643),
(11, 'Jaros??aw', 'Kowalski', '5A', 'jareczek1', '[B@4cddbfe2', 'c8431e11d9c2431b69070325b2d3e280a82b9ffce09f75f4fbcb952d157908eb', 'jaro99@gmail.com', 524111999),
(12, 'Anna', 'Kowalska', '3C', 'akowal', '[B@65499d94', 'cd67ef0dc881aed55bfa49450ef36147a6a90e7e8d0739957d18a0b0f18c3230', 'anna.kow@gmail.com', 312322552),
(13, 'Agata', 'Walczak', '7F', 'agata', '[B@25cf776a', '1ba9e117854f69f24afa91b41ba176dd82af58f7be07a4f876335fde6d52494b', 'aw@o2.pl', 777442567),
(14, 'Agnieszka', 'Krawczyk', '3D', 'akrawiec', '[B@7ae4095e', 'a1bdd16ba4a9002532f250196fb04283713ac914483c172d9356ebc8b5d200d8', 'akrawczyk233@gmail.com', 412666122),
(15, 'Maksymilian', 'Nowak', NULL, 'nowak321', '[B@66e1b1c9', '36a04a1bf718bc107d787ccbd77b645cfd8755dd7cb6927985d1df40a5d62504', 'maksnowak@wp.pl', 825111276),
(16, 'Tomasz', 'Pies', '1A', 'tomaszooo', '[B@26302380', '542cf0095d4a94018803748297a117cb2a044b399e6f03b42d37e6e22c3807d9', 'tomaszo@fb.pl', 506222354),
(17, 'Arkadiusz', 'Stolarczyk', '1F', 'arek99', '[B@3729a825', 'df7f05331a45aaf43e6363a76c2b3fdd5aeaaf3b9718f2301a18d558463607d3', 'arek99@gmail.com', 523666123),
(18, 'Piotr', 'Stankiewicz', '3G', 'pio_stankiewicz', '[B@516e1386', 'a51944d4816cf16e7961ce8a574b6d8bb7c342cf0115b005bb12a7f1e087e49f', 'pio.stankiewicz@gmail.com', 602888912),
(19, 'Marta', 'Lis', '2C', 'mlis', '[B@7be23912', '9128ef6182218ca5fc877b62a9f18c8f8b3747a0ef383140988cf7d9784bafce', 'mlis@o2.pl', 702420693),
(20, 'Halina', 'Torpeda', '2A', 'halina1', '[B@30a17cbc', '30e021cb73e3c48091969ff46585c75552436b41b56e166f8cf63273fc2deab0', 'halina@onet.pl', 432111890),
(21, 'Przemys??aw', 'Tyto??', '1E', 'p_tyton', '[B@508be8eb', '62244fda30e3ecc882241a138f6d75ebd7c45341f1c7d6db496c45f68720413a', 'ptyton@strona.com.pl', 321222613),
(22, 'Anna', 'Lis', '4D', 'anna_lis', '[B@527d5bae', 'caa5753c326ce71ce10698a05079fc022a256c7bdf6bcdb16f133e36fdbfa041', 'anna.lis@gmail.com', 502113731),
(23, 'Jan', 'Duda', '3C', 'jduda', '[B@20e63dd5', 'e68ce179d9b3da591264567c0680f5c9a6088f60a50afd36ffbe6f2d7f92311c', 'janek@gmail.com', 890331231),
(24, 'Zbigniew', 'Krawczyk', '6D', 'zbik21', '[B@199a53fd', '3e77b7c8b190a2c0df1a7c4c6535ef519abf5337ae8bafe70d99a179a4adda4a', 'zbigk@o2.pl', 538119440);

--
-- Indeksy dla zrzut??w tabel
--

--
-- Indeksy dla tabeli `actual_rentals`
--
ALTER TABLE `actual_rentals`
  ADD PRIMARY KEY (`rid`),
  ADD KEY `actual_rentals_fk0` (`uid`),
  ADD KEY `actual_rentals_fk1` (`bid`);

--
-- Indeksy dla tabeli `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `uid` (`uid`);

--
-- Indeksy dla tabeli `books_list`
--
ALTER TABLE `books_list`
  ADD PRIMARY KEY (`bid`);

--
-- Indeksy dla tabeli `rental_history`
--
ALTER TABLE `rental_history`
  ADD PRIMARY KEY (`hid`),
  ADD KEY `rental_history_fk0` (`uid`),
  ADD KEY `rental_history_fk1` (`bid`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone_number` (`phone_number`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `actual_rentals`
--
ALTER TABLE `actual_rentals`
  MODIFY `rid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT dla tabeli `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `books_list`
--
ALTER TABLE `books_list`
  MODIFY `bid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT dla tabeli `rental_history`
--
ALTER TABLE `rental_history`
  MODIFY `hid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `uid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Ograniczenia dla zrzut??w tabel
--

--
-- Ograniczenia dla tabeli `actual_rentals`
--
ALTER TABLE `actual_rentals`
  ADD CONSTRAINT `actual_rentals_fk0` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`),
  ADD CONSTRAINT `actual_rentals_fk1` FOREIGN KEY (`bid`) REFERENCES `books_list` (`bid`);

--
-- Ograniczenia dla tabeli `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_fk0` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE;

--
-- Ograniczenia dla tabeli `rental_history`
--
ALTER TABLE `rental_history`
  ADD CONSTRAINT `rental_history_fk0` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`),
  ADD CONSTRAINT `rental_history_fk1` FOREIGN KEY (`bid`) REFERENCES `books_list` (`bid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
