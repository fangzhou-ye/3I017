-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Feb 19, 2019 at 12:15 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `twister_YE_ZHANG`
--

-- --------------------------------------------------------

--
-- Table structure for table `Connection`
--

CREATE TABLE `Connection` (
  `conn_key` int(64) NOT NULL,
  `id_user` int(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Connection`
--

INSERT INTO `Connection` (`conn_key`, `id_user`) VALUES
(10001, 1),
(10002, 3);

-- --------------------------------------------------------

--
-- Table structure for table `Friendship`
--

CREATE TABLE `Friendship` (
  `id_user1` int(64) NOT NULL,
  `id_user2` int(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Friendship`
--

INSERT INTO `Friendship` (`id_user1`, `id_user2`) VALUES
(1, 3),
(3, 4),
(4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `id_user` int(32) NOT NULL,
  `login` varchar(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id_user`, `login`, `password`, `nom`, `prenom`) VALUES
(1, 'yef119', 'kd353535', 'YE', 'Fangzhou'),
(2, 'zhangs01', 'abc123', 'ZHANG', 'Shihao'),
(3, 'sao', 'hwq1996', 'HUANG', 'Weiqin'),
(4, 'qjhuang', '12345', 'HUANG', 'Qijia');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Connection`
--
ALTER TABLE `Connection`
  ADD PRIMARY KEY (`conn_key`,`id_user`),
  ADD KEY `user_id` (`id_user`);

--
-- Indexes for table `Friendship`
--
ALTER TABLE `Friendship`
  ADD PRIMARY KEY (`id_user1`,`id_user2`),
  ADD KEY `id_user1` (`id_user1`),
  ADD KEY `id_user2` (`id_user2`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Connection`
--
ALTER TABLE `Connection`
  MODIFY `conn_key` int(64) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10003;

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `id_user` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Connection`
--
ALTER TABLE `Connection`
  ADD CONSTRAINT `uid` FOREIGN KEY (`id_user`) REFERENCES `User` (`id_user`);

--
-- Constraints for table `Friendship`
--
ALTER TABLE `Friendship`
  ADD CONSTRAINT `uid1` FOREIGN KEY (`id_user1`) REFERENCES `User` (`id_user`),
  ADD CONSTRAINT `uid2` FOREIGN KEY (`id_user2`) REFERENCES `User` (`id_user`);
