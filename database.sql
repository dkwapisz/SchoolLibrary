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
(1, 'Pan Tadeusz', 'Adam Mickiewicz', 'Epic', 'Everyone', 1834, 5, 296, 'Rea', 'Ta epopeja narodowa (z elementami gawędy szlacheckiej) powstała w latach 1832–1834 w Paryżu. Składa się z dwunastu ksiąg pisanych wierszem, trzynastozgłoskowym aleksandrynem polskim. Czas akcji: pięć dni z roku 1811 i jeden dzień z roku 1812. Epopeja jest stałą pozycją na polskiej liście lektur szkolnych.'),
(2, 'Igrzyska Śmierci', 'Suzanne Collins', 'Dystopia', 'Teenagers', 2008, 3, 378, 'Media Rodzina', 'Na ruinach dawnej Ameryki Północnej rozciąga się państwo Panem, z imponującym Kapitolem otoczonym przez dwanaście dystryktów. Okrutne władze stolicy zmuszają podległe sobie rejony do składania upiornej daniny. Raz w roku każdy dystrykt musi dostarczyć chłopca i dziewczynę między dwunastym a osiemnastym rokiem życia, by wzięli udział w Głodowych Igrzyskach, turnieju na śmierć i życie, transmitowanym na żywo przez telewizję.'),
(4, 'Harry Potter i Kamień Filozoficzny', 'J.K. Rowling', 'Fantasy', 'Teenagers', 1997, 6, 320, 'Media Rodzina', 'W pierwszej części czytelnik dowiaduje się, że równolegle z naszym światem istnieje świat alternatywny: świat czarodziejów. Niemagiczni ludzie (zwani mugolami) nie mają o nim pojęcia. Jednak ci, którzy są obdarzeni talentem magicznym, uczęszczają do specjalnych szkół, a potem pracują w magicznych zawodach. Nasz tytułowy bohater Harry to sierota, od maleńkości wychowywany przez ciotkę i wuja. W wieku 11 lat dowiaduje się, że jego rodzice byli czarodziejami, a on sam ma niebawem podjąć naukę w najlepszej szkole dla czarodziejów. W Hogwarcie Harry poznaje prawdziwych przyjaciół, ale zyskuje też wrogów. Co najważniejsze – dowiaduje się, że zabójca jego rodziców, Lord Voldemort, wciąż żyje i planuje powrót. Ponieważ każda część Harry’ego Pottera kryje w sobie zagadkę, w tomie pierwszym odkrywamy tajemnicę pewnego profesora oraz jego związek z Voldemortem.'),
(5, 'Harry Potter i Komnata Tajemnic', 'J.K. Rowling', 'Fantasy', 'Teenagers', 1998, 4, 368, 'Media Rodzina', 'Ta część obejmuje drugi rok nauki Harry’ego w Hogwarcie. Choć może się to nam wydawać niewiarygodne, Harry nie może doczekać się końca wakacji i powrotu do szkoły. Tęskni za swoimi najlepszymi przyjaciółmi, Ronem Wesleyem i Hermioną Granger. Hogwart jest jego prawdziwym domem. W drugiej z kolejności części sagi dowiadujemy się więcej o pochodzeniu i dzieciństwie Voldemorta, jego związku z wężami i z legendarnym Slytherinem, jednym z czterech założycieli Hogwartu. Ponieważ każdy tom cyklu kryje w sobie przygodę i tajemnicę, to i druga część Harry’ego Pottera pełna jest wartkiej akcji zmierzającej do rozwikłania zagadki z przeszłości.'),
(6, 'Harry Potter i więzień Azkabanu', 'J.K. Rowling', 'Fantasy', 'Teenagers', 1999, 2, 448, 'Media Rodzina', 'To w trzecim tomie Harry poznaje swojego jedynego krewnego i zyskuje w nim opiekuna i przyjaciela. Na scenę wkracza bowiem Syriusz Black, który uchodzi za sługę Voldemorta, uciekinier z Azkabanu, czyli więzienia strzeżonego przez zatrważających dementorów.  Wszyscy włącznie z Harrym są przekonani, że Black zdradził rodziców Harry’ego. Tymczasem Syriusz opowiada całkiem inną historię.'),
(7, 'Harry Potter i Czara Ognia', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2000, 2, 766, 'Media Rodzina', 'Czwarta z kolei część cyklu wyszła drukiem w Anglii w  2000 roku, a w Polsce jesienią 2001. Czwarta część wyjątkowo raduje fanów Harry’ego Pottera, ponieważ jest znacznie większa objętościowo, a poza tym rozpoczyna się imponującym wydarzeniem sportowym: mistrzostwami świata w quidditchu. Rok szkolny przynosi też wiele emocji, bo nasz bohater bierze udział w niebezpiecznym Turnieju Trójmagicznym, do którego w ogóle nie powinien się zakwalifikować.  Najwyraźniej komuś bardzo zależy na tym, żeby Harry podjął się trzech zagrażających życiu zadań. Czwarta część sagi jest znacznie mroczniejsza od poprzednich, ponieważ ktoś bliski Harry’emu pada ofiarą turnieju. Ponadto najgroźniejszy przeciwnik Harry’ego, sam Lord Voldemort, odradza się w całej pełni i odzyskuje moc. Po tej części nie sposób już nie przeczytać kolejnych.'),
(8, 'Harry Potter i Zakon Feniksa', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2003, 4, 960, 'Media Rodzina', 'Piątą częścią „Harry’ego Pottera” po kolei jest „Zakon Feniksa” z 2003 roku, który w Polsce wyszedł w roku 2004. W tym tomie Harry, Ron i Hermiona dołączają do Zakonu Feniksa, aby wspólnie z dorosłymi czarodziejami pokonać Voldemorta. Do tej organizacji niegdyś należeli rodzice Harry’ego oraz sam Dumbledore. Potrzeba wskrzeszenia Zakonu jest wielka, gdyż zagrożenie ze strony  Voldemorta i Śmierciożerców staje się namacalne. Z tej części Harry’ego Pottera pochodzi chyba najbardziej znienawidzona nauczycielka wszech czasów – Dolores Umbridge. Zostaje ona Wielkim Inkwizytorem i zastępuje Dumbledore’a na stanowisku dyrektora.'),
(9, 'Harry Potter i Książę Półkrwi', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2005, 2, 704, 'Media Rodzina', 'W szóstej części cyklu Dumbledore angażuje Harry’ego do poszukiwań horkruksów. Żeby zbyt wiele nie zdradzać: są to magiczne przedmioty niezbędne do pokonania Voldemorta. W szóstej części Harry posługuje się podręcznikiem do eliksirów, który kiedyś należał do niejakiego Księcia Półkrwi. Odręczne notatki wskazują, że właściciel tej książki był geniuszem. Kto się kryje pod tym pseudonimem i jaka jest rola Księcia Półkrwi w historii Harry’ego?'),
(10, 'Harry Potter i Insygnia Śmierci', 'J.K. Rowling', 'Fantasy', 'Teenagers', 2007, 3, 782, 'Media Rodzina', 'W tomie siódmym  dochodzi do ostatecznego starcia Harry’ego z Voldemortem. Harry wie, że nie pokona Czarnego Pana, jeśli wcześniej nie zniszczy wszystkich horkruksów, gdyż to one stanowią o nieśmiertelności Czarnego Pana. Dlatego wraz z Ronem i Hermioną opuszczają Hogwart, gdzie rządzą poplecznicy Voldemorta, i udają się w misję odnalezienia horkruksów. Ostatnią część wieńczy wielka Bitwa o Hogwart. Niestety, bitwa przynosi ofiary.'),
(11, 'W pierścieniu ognia', 'Suzanne Collins', 'Dystopia', 'Teenagers', 2009, 1, 360, 'Media Rodzina', 'Katniss i Peeta z racji wygrania ostatnich igrzysk są w trakcie obowiązkowego Tournee Zwycięzców. Wkrótce okazuje się, że wiele dystryktów zaczyna się buntować przeciwko Kapitolowi. Prezydent Snow winą za wszystko obarcza Katniss, która wraz z Peetą złamała zasady obowiązujące na arenie podczas igrzysk. Dla ludności w dystryktach Katniss jest symbolem nadziei. Pragnąc ją zniszczyć, prezydent Snow przygotowuje dla niej coś specjalnego.'),
(12, 'Kosogłos', 'Suzanne Collins', 'Dystopia', 'Teenagers', 2010, 2, 372, 'Media Rodzina', 'Katniss, po ciężkich przeżyciach w Ćwierćwieczu Poskromienia, zostaje przetransportowana do legendarnego 13 Dystryktu. Tam, mimo niechęci, postanawia zostać Kosogłosem – symbolem rebelii. Będzie musiała uczestniczyć w wojnie, kręcić materiały propagandowe i wreszcie rozprawić się z Kapitolem. Będzie też musiała wybierać między dwiema miłościami: zagorzałym zwolennikiem rewolucji Gale’em a opanowanym i romantycznym Peetą.'),
(13, 'Lew, czarownica i stara szafa', 'C.S. Lewis', 'Fantasy', 'Everyone', 1950, 6, 184, 'Media Rodzina', 'Jest około 1940 roku, dopiero co rozpoczęła się druga wojna światowa, i czwórka rodzeństwa Piotr, Zuzanna, Edmund i Łucja ewakuują się z bombardowanego Londynu. Zostają wysłani na wieś do domu profesora Digory’ego Kirke, który mieszka tam wraz ze swoją gosposią, Panią Macready. Pewnego deszczowego dnia, dzieci postanawiają zbadać dom. Łucja, najmłodsza z rodzeństwa, zainteresowana szafą stojącą w pustym pokoju odkrywa, że jest to portal do pokrytego śniegiem lasu z latarnią pośrodku. Spotyka tam fauna, który przedstawia się jako Tumnus i zaprasza ją na herbatę. Opowiada jej, że owa kraina nazywa się Narnią i jest rządzona przez bezwzględną Białą Czarownicę, która sprawia, że panuje tu wieczna zima, ale nigdy nie ma świąt.'),
(14, 'Książę Kaspian', 'C.S. Lewis', 'Fantasy', 'Everyone', 1951, 3, 224, 'Media Rodzina', 'Mija rok od wydarzeń opisanych w powieści Lew, czarownica i stara szafa. Piotr, Zuzanna, Edmund i Łucja po spędzeniu bardzo miłych wakacji muszą jechać do nowych szkół – szkół z internatem. Tym smutniejszy dla nich jest fakt, że bracia jadą do innej szkoły, a siostry do innej, co oznacza, że rodzeństwo nie zobaczy się przez najbliższe 10 miesięcy. Nagle dzieci, siedząc na ławce, czują, że coś ich kłuje w plecy. Po chwili jakaś moc ciągnie ich w nieznane. Łapią się za ręce i.... lądują w Narnii. Są uradowani. Trafili na wyspę, której pochodzenia nie mogli sobie przypomnieć – wyspę porośniętą puszczą. Bawią się w wodzie, potem idą dalej. Robią się głodni, jedzą kanapki, jakie przyszykowała im mama. Wreszcie sycą głód jabłkami z sadu, do którego dziwnym trafem się dostali. Sad prowadzi ich do muru, który należy do ruin starego zamku. Dziwią się, bowiem w Narnii nie było żadnych ruin. Przekraczają jednak ich mury.'),
(15, 'Podróż Wędrowca do Świtu', 'C.S. Lewis', 'Fantasy', 'Everyone', 1952, 2, 248, 'Media Rodzina', 'Łucja, Edmund oraz ich nieznośny kuzyn Eustachy za pomocą magicznego obrazu przenoszą się na pokład „Wędrowca do Świtu”, gdzie ponownie spotykają młodego króla Narnii – Kaspiana. Muszą pomóc mu w próbie odnalezienia dawno zaginionych przyjaciół ojca, baronów narnijskich (Revilian, Bern, Argoz, Mavramorn, Oktezjan, Restimar i Rup), których uzurpator Miraz wysłał na morze. W tym celu rozpoczynają pełną przygód podróż aż na „ostateczny wschód”.'),
(16, 'Srebrne krzesło', 'C.S. Lewis', 'Fantasy', 'Everyone', 1953, 1, 159, 'Media Rodzina', 'Eustachy Scrubb i jego szkolna koleżanka Julia Pole, uczniowie Eksperymentalnej Szkoły, zostają wezwani przez Aslana do magicznej krainy Narnii z misją odnalezienia zaginionego od dziesięciu lat, królewicza Narnii – Riliana. Jest on synem Kaspiana X, z którym zaprzyjaźnił się Eustachy podczas podróży „Wędrowca do Świtu” na koniec świata.'),
(17, 'Koń i jego chłopiec', 'C.S. Lewis', 'Fantasy', 'Everyone', 1954, 1, 148, 'Media Rodzina', 'Akcja książki rozgrywa się w Narnii, Archenlandii i Kalormenie za czasów Piotra, Zuzanny, Edmunda i Łucji. Głównym bohaterem jest mieszkający w Kalormenie i przygarnięty przez rybaka Arszisza Szasta. Pewnego dnia do ich chatki przybywa jeden z kalormeńskich wielmożów, Tarkaan Anradin i chce kupić Szastę od Arszisza. Chłopiec przez przypadek podsłuchuje ich rozmowę i postanawia uciec na koniu Anradina. Okazuje się iż rumak o imieniu Bri jest mówiącym koniem i pochodzi z Narnii. Tam też obaj planują uciec. Podczas ucieczki, uciekając przed lwem, spotykają innych uciekinierów. Są to: Tarkiina Arawis na swej mówiącej klaczy Hwin. Arawis, choć pochodzi z kalormeńskiej arystokracji, ucieka przerażona czekającym ją małżeństwem ze starym wezyrem Ahosztą.'),
(18, 'Siostrzeniec czarodzieja', 'C.S. Lewis', 'Fantasy', 'Everyone', 1955, 1, 132, 'Media Rodzina', 'Książka opisuje stworzenie Narnii przez Aslana, w czym uczestniczą Digory Kirke i Pola Plummer. Jest to pod względem chronologii pierwsza część „Narnii”. Dowiadujemy się, jak powstała. Jak w poprzednich częściach jest nawiązanie do Biblii (Stworzenie Narnii przez Aslana, możemy porównać do stworzenia Świata przez Boga).'),
(19, 'Ostatnia bitwa', 'C.S. Lewis', 'Fantasy', 'Everyone', 1956, 2, 135, 'Media Rodzina', 'Julia i Eustachy zostają wezwani do Narnii by pomóc zaprowadzić pokój królowi Tirianowi, ponieważ zaczęła tam rządzić się podstępna małpa zwana Krętaczem. Ubiera osła Łamigłówka w skórę lwa i każe mu udawać Aslana. Dla własnego zysku sprowadza Kalormeńczyków, pozwalając na wyręb mówiących drzew i sprzedawanie mieszkańców Narnii jako niewolników. Do tego stopnia mąci wszystkim Narnijczykom w głowach, iż mówi im, że Tasz (bóstwo Kalormeńczyków) i Aslan (władca Narnii) są jedną osobą.');

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
(2, 'Anna', 'Młynarczyk', '3F', 'amlynarczyk', '[B@177201c8', 'ec6faae2f8aa14b1a6430b8e83a1678c62987cba9ac810788e8e00c789e7eb1a', 'amlynarczyk@wp.pl', 555333999),
(3, 'Dawid', 'Kwapisz', '3C', 'dkwapisz', '[B@29fdae12', '869521ddf5844afbf28a325a3faa946ec6d920a02786e6b0cf5497da4d8b7dd7', 'dkwapisz@gmail.com', 555333444),
(4, 'Agnieszka', 'Żupnik', NULL, 'azupnik', '[B@4e203831', '6542ea25629ec736b2cd0df5626a11cae02c9d5503a991480a550ad1fa72d8c1', 'a@zupnik.pl', 123321123),
(5, 'Jan', 'Kowalski', '4D', 'jkowalski', '[B@563a744c', 'b55d3c88be2fe31451233cce8933004d2b5eecd104878aee581ad6d59efca15c', 'jan@wp.pl', 999222999),
(7, 'Paweł', 'Nowak', '6D', 'pawelo_1', '[B@4ff67e4a', 'f4d872fe5949aa0bfd8a014da02792357b6ea326a2e5ec89e14fb01690fdde6f', 'pnowak@szkola.pl', 354724774),
(8, 'Magda', 'Kot', '2D', 'kot22', '[B@1081835c', 'c4f28202e411d2f6eb3346fae4a220fe3aff2ea85b80c2aa9c490e5529edfad6', 'magda321@gmail.com', 693555434),
(9, 'Aleksandra', 'Żak', '4F', 'ola321', '[B@17586061', 'a76cc74e69752a146dc8afa8bebfb5bdfffca8a2c155689ad69557e0d8f3ae9b', 'ola321@wp.pl', 799531356),
(10, 'Michał', 'Lewandowski', '1D', 'michalooo_pl', '[B@68b4ea50', '5d393b7472333dfb08464b31b53d91318c3b90aa9a9f3687197f0554be098c57', 'michalooo05@interia.pl', 742745643),
(11, 'Jarosław', 'Kowalski', '5A', 'jareczek1', '[B@4cddbfe2', 'c8431e11d9c2431b69070325b2d3e280a82b9ffce09f75f4fbcb952d157908eb', 'jaro99@gmail.com', 524111999),
(12, 'Anna', 'Kowalska', '3C', 'akowal', '[B@65499d94', 'cd67ef0dc881aed55bfa49450ef36147a6a90e7e8d0739957d18a0b0f18c3230', 'anna.kow@gmail.com', 312322552),
(13, 'Agata', 'Walczak', '7F', 'agata', '[B@25cf776a', '1ba9e117854f69f24afa91b41ba176dd82af58f7be07a4f876335fde6d52494b', 'aw@o2.pl', 777442567),
(14, 'Agnieszka', 'Krawczyk', '3D', 'akrawiec', '[B@7ae4095e', 'a1bdd16ba4a9002532f250196fb04283713ac914483c172d9356ebc8b5d200d8', 'akrawczyk233@gmail.com', 412666122),
(15, 'Maksymilian', 'Nowak', NULL, 'nowak321', '[B@66e1b1c9', '36a04a1bf718bc107d787ccbd77b645cfd8755dd7cb6927985d1df40a5d62504', 'maksnowak@wp.pl', 825111276),
(16, 'Tomasz', 'Pies', '1A', 'tomaszooo', '[B@26302380', '542cf0095d4a94018803748297a117cb2a044b399e6f03b42d37e6e22c3807d9', 'tomaszo@fb.pl', 506222354),
(17, 'Arkadiusz', 'Stolarczyk', '1F', 'arek99', '[B@3729a825', 'df7f05331a45aaf43e6363a76c2b3fdd5aeaaf3b9718f2301a18d558463607d3', 'arek99@gmail.com', 523666123),
(18, 'Piotr', 'Stankiewicz', '3G', 'pio_stankiewicz', '[B@516e1386', 'a51944d4816cf16e7961ce8a574b6d8bb7c342cf0115b005bb12a7f1e087e49f', 'pio.stankiewicz@gmail.com', 602888912),
(19, 'Marta', 'Lis', '2C', 'mlis', '[B@7be23912', '9128ef6182218ca5fc877b62a9f18c8f8b3747a0ef383140988cf7d9784bafce', 'mlis@o2.pl', 702420693),
(20, 'Halina', 'Torpeda', '2A', 'halina1', '[B@30a17cbc', '30e021cb73e3c48091969ff46585c75552436b41b56e166f8cf63273fc2deab0', 'halina@onet.pl', 432111890),
(21, 'Przemysław', 'Tytoń', '1E', 'p_tyton', '[B@508be8eb', '62244fda30e3ecc882241a138f6d75ebd7c45341f1c7d6db496c45f68720413a', 'ptyton@strona.com.pl', 321222613),
(22, 'Anna', 'Lis', '4D', 'anna_lis', '[B@527d5bae', 'caa5753c326ce71ce10698a05079fc022a256c7bdf6bcdb16f133e36fdbfa041', 'anna.lis@gmail.com', 502113731),
(23, 'Jan', 'Duda', '3C', 'jduda', '[B@20e63dd5', 'e68ce179d9b3da591264567c0680f5c9a6088f60a50afd36ffbe6f2d7f92311c', 'janek@gmail.com', 890331231),
(24, 'Zbigniew', 'Krawczyk', '6D', 'zbik21', '[B@199a53fd', '3e77b7c8b190a2c0df1a7c4c6535ef519abf5337ae8bafe70d99a179a4adda4a', 'zbigk@o2.pl', 538119440);

--
-- Indeksy dla zrzutów tabel
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
-- Ograniczenia dla zrzutów tabel
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
