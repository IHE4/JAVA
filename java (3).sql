-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 15 mai 2024 à 01:29
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `java`
--

-- --------------------------------------------------------

--
-- Structure de la table `abonnements`
--

CREATE TABLE `abonnements` (
  `id_utilisateur` int(11) NOT NULL,
  `duree` date NOT NULL,
  `prix` decimal(4,2) NOT NULL,
  `reduction` decimal(4,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `abonnements`
--

INSERT INTO `abonnements` (`id_utilisateur`, `duree`, `prix`, `reduction`) VALUES
(0, '2024-12-31', 50.00, 0.30),
(1, '2024-12-31', 50.00, 0.90);

-- --------------------------------------------------------

--
-- Structure de la table `commentaires`
--

CREATE TABLE `commentaires` (
  `id` int(11) NOT NULL,
  `id_film` int(11) NOT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `commentaire` varchar(999) NOT NULL,
  `date` date NOT NULL,
  `note` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `commentaires`
--

INSERT INTO `commentaires` (`id`, `id_film`, `id_utilisateur`, `commentaire`, `date`, `note`) VALUES
(0, 2, 3, 'Je n\'ai pas accroché à ce film. Les chansons étaient ennuyeuses et l\'histoire manquait de substance.', '2024-05-03', 2),
(1, 0, 1, 'J\'ai adoré ce film ! Les effets spéciaux sont incroyables et l\'intrigue est captivante.', '2024-05-03', 9),
(2, 0, 2, 'Un film très divertissant avec des rebondissements passionnants. Je le recommande vivement !', '2024-05-03', 7),
(3, 0, 3, 'Je n\'ai pas aimé ce film. L\'intrigue était confuse et les personnages manquaient de profondeur.', '2024-05-03', 3),
(4, 1, 1, 'Un classique intemporel ! L\'histoire d\'amour entre les deux personnages principaux est émouvante.', '2024-05-03', 8),
(5, 1, 2, 'Ce film m\'a vraiment touché. Les acteurs étaient fantastiques et l\'histoire était poignante.', '2024-05-03', 7),
(6, 1, 3, 'Je ne recommanderais pas ce film. L\'intrigue était prévisible et les dialogues étaient peu naturels.', '2024-05-03', 1),
(7, 2, 1, 'Un chef-d\'œuvre musical ! Les numéros de danse étaient époustouflants et l\'alchimie entre les acteurs était palpable.', '2024-05-03', 7),
(8, 2, 2, 'Ce film m\'a transporté dans un autre monde. La musique et les performances étaient à couper le souffle.', '2024-05-03', 9);

-- --------------------------------------------------------

--
-- Structure de la table `films`
--

CREATE TABLE `films` (
  `titre` varchar(99) NOT NULL,
  `realisateur` varchar(99) NOT NULL,
  `date_publication` int(11) NOT NULL,
  `theme` varchar(99) NOT NULL,
  `producteur` varchar(99) NOT NULL,
  `resume` varchar(999) NOT NULL,
  `categorie_age` int(11) NOT NULL,
  `acteurs_principaux` varchar(99) NOT NULL,
  `activer_commentaire` bit(1) NOT NULL,
  `code` varchar(99) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `films`
--

INSERT INTO `films` (`titre`, `realisateur`, `date_publication`, `theme`, `producteur`, `resume`, `categorie_age`, `acteurs_principaux`, `activer_commentaire`, `code`, `id`) VALUES
('titanic', 'james cameron', 1997, 'drame', '20th century fox', 'l\'histoire epique de l\'amour entre jack et rose, deux passagers du titanic, alors que le navire coule tragiquement.', 13, 'leonardo dicaprio, kate winslet', b'0', 'ttn', 0),
('deadpool', 'tim miller', 2016, 'super-heros', '20th century fox', 'un ancien agent des forces speciales subit une experience qui le laisse avec des pouvoirs de guerison, adoptant l\'alter ego de deadpool.', 16, 'ryan reynolds, morena baccarin', b'0', 'ddp', 1),
('inception', 'christopher nolan', 2010, 'action', 'warner bros', 'un voleur experimente vole des secrets en utilisant une technologie d\'infiltration dans les reves.', 13, 'leonardo dicaprio, joseph gordon-levitt', b'1', 'inc', 2),
('star wars', 'george lucasfilm', 2003, 'fiction', 'century', 'blablabla', 18, 'anakin', b'1', 'starw', 3),
('la la land', 'damien chazelle', 2016, 'musical', 'lionsgate', 'un pianiste de jazz et une aspirante actrice tombent amoureux a los angeles tout en poursuivant leurs reves.', 13, 'ryan gosling, emma stone', b'1', 'lll', 4),
('interstellar', 'christopher nolan', 2014, 'science-fiction', 'paramount pictures', 'un groupe d\'explorateurs voyage a travers un trou de ver dans l\'espace afin de trouver une nouvelle planete habitable pour l\'humanite.', 13, 'matthew mcconaughey, anne hathaway', b'1', 'int', 5),
('the avengers', 'joss whedon', 2012, 'super-heros', 'marvel studios', 'une equipe de super-heros, dont iron man, thor, hulk et captain america, s\'unit pour sauver le monde des menaces extraterrestres.', 13, 'robert downey jr., chris evans, scarlett johansson', b'1', 'avn', 6),
('the hangover', 'todd phillips', 2009, 'comedie', 'warner bros', 'apres une nuit de debauche a las vegas, trois amis se reveillent sans souvenir de ce qui s\'est passe et tentent de retrouver le marie disparu.', 18, 'bradley cooper, ed helms, zach galifianakis', b'1', 'hng', 7),
('the notebook', 'nick cassavetes', 2004, 'romance', 'new line cinema', 'une histoire d\'amour passionnee entre un jeune couple confronte a des defis et a des saparations.', 18, 'ryan gosling, rachel mcadams', b'0', 'not', 8),
('the godfather', 'francis ford coppola', 1972, 'crime', 'paramount pictures', 'la saga d\'une famille mafieuse italo-americaine dirigee par le patriarche don vito corleone.', 18, 'marlon brando, al pacino', b'0', 'gdf', 9),
('the shawshank redemption', 'frank darabont', 1994, 'drame', 'columbia pictures', 'l\'histoire d\'andy dufresne, un banquier condamne a tort pour le meurtre de sa femme, et de son amitie avec un detenu nomme red.', 16, 'tim robbins, morgan freeman', b'1', 'tsr', 10);

-- --------------------------------------------------------

--
-- Structure de la table `historique_achat`
--

CREATE TABLE `historique_achat` (
  `id` int(11) NOT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `id_film` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `historique_achat`
--

INSERT INTO `historique_achat` (`id`, `id_utilisateur`, `id_film`) VALUES
(0, 1, 0),
(1, 0, 3),
(2, 0, 4),
(3, 0, 5);

-- --------------------------------------------------------

--
-- Structure de la table `paniers`
--

CREATE TABLE `paniers` (
  `id` int(11) NOT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `id_film` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `paniers`
--

INSERT INTO `paniers` (`id`, `id_utilisateur`, `id_film`) VALUES
(0, 0, 0),
(1, 0, 1),
(2, 0, 2),
(3, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(99) NOT NULL,
  `password` varchar(99) NOT NULL,
  `firstName` varchar(99) NOT NULL,
  `lastName` varchar(99) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `address` varchar(99) NOT NULL,
  `secretPhrase` varchar(99) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `money` decimal(5,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `firstName`, `lastName`, `dateOfBirth`, `address`, `secretPhrase`, `isAdmin`, `money`) VALUES
(0, 'aa@gmail.com', 'aa', 'Richy', 'Razafindandy', '2003-05-17', '1 rue de la paix', '2+2', 1, 999.9),
(1, 'bb@gmail.com', 'bb', 'Imane', 'Hammouche', '1994-12-31', '3 avenu francklin roosevelt', 'maroc', 1, 549.5),
(2, 'cc@gmail.com', 'cc', 'Berkehan', 'Uslu', '1991-06-16', '78 bis rue des turcs', 'branleur', 0, 458.2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `abonnements`
--
ALTER TABLE `abonnements`
  ADD PRIMARY KEY (`id_utilisateur`);

--
-- Index pour la table `commentaires`
--
ALTER TABLE `commentaires`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `films`
--
ALTER TABLE `films`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `historique_achat`
--
ALTER TABLE `historique_achat`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `paniers`
--
ALTER TABLE `paniers`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `commentaires`
--
ALTER TABLE `commentaires`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `historique_achat`
--
ALTER TABLE `historique_achat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `paniers`
--
ALTER TABLE `paniers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
