--
-- Database: `crm`
--
CREATE DATABASE IF NOT EXISTS `crm` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `crm`;

-- --------------------------------------------------------

--
-- Table structure for table `klijent`
--

CREATE TABLE `klijent` (
  `id` int(11) NOT NULL,
  `ime` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `adresa` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `kontakt_broj` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `tstamp_registracije` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `klijent`
--

INSERT INTO `klijent` (`id`, `ime`, `prezime`, `adresa`, `kontakt_broj`, `email`, `tstamp_registracije`) VALUES
(1, 'Tomislav', 'Bradač', 'Jankomir 10', '0989000440', 'tbradac@tvz.hr', 1465394400),
(3, 'Marko', 'Marić', 'Neka Adresa 11', '0989888999', 'marko.maric@gmail.com', 1465565183),
(4, 'Ivo', 'Ivić', 'Neka Adresa 9', '091/69693334', 'ivo.ivic@gmail.com', 1465565441),
(5, 'Vanja', 'Vanjić', 'Neka Adresa 8', '09854632131', 'vanja.vanjic@gmail.com', 1460758380),
(6, 'Matija', 'Matić', 'Prva ulica 4', '09845322637', 'matija.matic@gmail.com', 1462977182),
(7, 'Ivona', 'Ivić', 'Druga ulica 1', '09885332543', 'ivona.ivic@gmail.com', 1457975483),
(8, 'Dijana', 'Novosel', 'Treća ulica 1', '09823325694', 'dijana.novosel@gmail.com', 1464519753),
(9, 'Monika', 'Pejičić', 'Peta ulica 8', '09745526325', 'monika.pejicic@gmail.com', 1463573532),
(10, 'Nikola', 'Nikolić', 'Šesta ulica 7', '09536653221', 'nikola.nikolic@gmail.com', 1464884589),
(11, 'Davor', 'Nikić', 'Sedma ulica 2', '09166599654', 'davor.nikic@gmail.com', 1462187297),
(12, 'Ivana', 'Perić', 'Osma cesta 3', '09952131654', 'ivana.peric@gmail.com', 1456089722),
(13, 'Mario', 'Matić', 'Deveta ulica 11', '09566455578', 'mario.matic@gmail.com', 1460733315),
(14, 'Tihomir', 'Varga', 'Deseta ulica 33', '09566543137', 'tihomir.varga@gmail.com', 1456847642);

-- --------------------------------------------------------

--
-- Table structure for table `popravak`
--

CREATE TABLE `popravak` (
  `id` int(11) NOT NULL,
  `naziv` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `opis` text COLLATE utf8_unicode_ci NOT NULL,
  `tip` int(11) NOT NULL,
  `vozilo` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `cijena` decimal(10,2) NOT NULL,
  `id_klijenta` int(11) NOT NULL,
  `tstamp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `popravak`
--

INSERT INTO `popravak` (`id`, `naziv`, `opis`, `tip`, `vozilo`, `cijena`, `id_klijenta`, `tstamp`) VALUES
(1, 'Filter ulja', 'Očišćen filter ulja', 4, 'Fiat Tipo', '99.99', 1, 1465473600),
(2, 'Pukla guma', 'Zamjena i balans nove gume', 1, 'Mercedes CLK', '50.00', 1, 1465566810),
(3, 'Alternator ne puni', 'Pregorio alternator', 4, 'Opel Astra', '60.00', 4, 1465755570),
(4, 'Popravak kartera', 'Karter pušta ulje', 3, 'Toyota Yaris', '250.44', 3, 1465775137),
(5, 'Lonca auspuha', 'Varenje lonca auspuha', 2, 'Fiat Stilo', '120.00', 14, 1465310706),
(6, 'Veliki servis', 'Zamjena svih filtera', 3, 'Opel Astra', '860.00', 13, 1463055133),
(7, 'Servis reduktora', 'Zamjena reduktora i kardana', 4, 'Porshe Cayenne GTS', '2430.00', 12, 1462808702),
(8, 'Pukla guma', 'Krpanje gume i špur kotača', 1, 'Škoda Fabia', '300.00', 11, 1464030453),
(9, 'Prednji kraj - sudar', 'Ravnanje lima', 3, 'VW Golf 2', '750.00', 10, 1461150427),
(10, 'Ravnanje praga', 'Ispravljanje lima na suvozačevom pragu', 3, 'Renault Clio', '260.00', 9, 1460886004),
(11, 'Puknut remen', 'Zamjena remena', 4, 'Opel Calibra', '420.00', 8, 1459759749),
(12, 'Zamjena ulja', 'Zamjena ulja u motoru', 4, 'VW Polo', '80.00', 7, 1457101203),
(13, 'Ventili', 'Namještanje zazora ventila', 4, 'Fiat Punto', '200.00', 6, 1457797923),
(14, 'Prazan akumulator', 'Punjenje akomulatora', 4, 'Yugo Koral 45', '40.00', 5, 1458144976),
(15, 'Zamjena svijećice', 'Auto ne pali, zamjena svijećice', 4, 'Audi A4', '220.00', 4, 1455188471);

-- --------------------------------------------------------

--
-- Table structure for table `tip_popravka`
--

CREATE TABLE `tip_popravka` (
  `id` int(11) NOT NULL,
  `tip` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tip_popravka`
--

INSERT INTO `tip_popravka` (`id`, `tip`) VALUES
(1, 'Guma'),
(2, 'Auspuh'),
(3, 'Karoserija'),
(4, 'Motor');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `klijent`
--
ALTER TABLE `klijent`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `popravak`
--
ALTER TABLE `popravak`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_klijenta` (`id_klijenta`),
  ADD KEY `tip` (`tip`);

--
-- Indexes for table `tip_popravka`
--
ALTER TABLE `tip_popravka`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `klijent`
--
ALTER TABLE `klijent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `popravak`
--
ALTER TABLE `popravak`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `tip_popravka`
--
ALTER TABLE `tip_popravka`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `popravak`
--
ALTER TABLE `popravak`
  ADD CONSTRAINT `popravak_ibfk_1` FOREIGN KEY (`id_klijenta`) REFERENCES `klijent` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `popravak_ibfk_2` FOREIGN KEY (`tip`) REFERENCES `tip_popravka` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

