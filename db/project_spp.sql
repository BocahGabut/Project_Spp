-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 22 Mar 2019 pada 14.03
-- Versi Server: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project_spp`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `agama`
--

CREATE TABLE `agama` (
  `id` int(10) NOT NULL,
  `agama` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `agama`
--

INSERT INTO `agama` (`id`, `agama`) VALUES
(3, 'Budha'),
(1, 'Islam'),
(2, 'Kristen');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jurusan`
--

CREATE TABLE `jurusan` (
  `id` int(10) NOT NULL,
  `jurusan` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `jurusan`
--

INSERT INTO `jurusan` (`id`, `jurusan`) VALUES
(2, 'Rekayasa Perangkat Lunak'),
(1, 'Teknik Informatika'),
(3, 'Teknik Komputer Jaringan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `siswa`
--

CREATE TABLE `siswa` (
  `nis` int(15) NOT NULL,
  `nama` varchar(65) NOT NULL,
  `kelas` varchar(25) NOT NULL,
  `jurusan` varchar(45) NOT NULL,
  `tmp_lahir` varchar(25) NOT NULL,
  `tgl_lahir` varchar(15) NOT NULL,
  `jk` varchar(15) NOT NULL,
  `agama` varchar(25) NOT NULL,
  `no_hp` char(12) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `siswa`
--

INSERT INTO `siswa` (`nis`, `nama`, `kelas`, `jurusan`, `tmp_lahir`, `tgl_lahir`, `jk`, `agama`, `no_hp`, `alamat`) VALUES
(171830570, 'Ferguso', '10 TI 1', 'Teknik Informatika', 'Cirebon', 'Feb 19, 1997', 'L', 'Islam', '089517214711', 'krucuk'),
(171830571, 'Muhamad Reza', '11 RPL 1', 'Rekayasa Perangkat Lunak', 'Kuningan', 'Feb 21, 1991', 'L', 'Islam', '08534321212', 'perum'),
(171830572, 'Cristin', '12 RPL 3', 'Rekayasa Perangkat Lunak', 'Bandung', 'Feb 17, 1988', 'P', 'Kristen', '0821433234', 'Kesambi'),
(171830573, 'Adhe Purnomo', '12 RPL 2', 'Rekayasa Perangkat Lunak', 'Pluto', 'Feb 18, 1988', 'L', 'Islam', '08273165361', 'Bumi'),
(171830574, 'Firman Sutisna', '11 RPL 3', 'Rekayasa Perangkat Lunak', 'Jakarta', 'Feb 15, 1996', 'L', 'Islam', '08721872581', 'Jakarta'),
(171830575, 'Ahmad Syahreza', '10 TI 8', 'Teknik Informatika', 'Cirebon', 'Feb 15, 1990', 'L', 'Kristen', '08218761872', 'Kuningan'),
(171830576, 'Angela Cristin', '11 RPL 2', 'Rekayasa Perangkat Lunak', 'Cirebon', 'Feb 15, 1991', 'P', 'Kristen', '08216752712', 'Perum');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `no_trans` varchar(15) NOT NULL,
  `nis` int(15) NOT NULL,
  `nama` varchar(65) NOT NULL,
  `kelas` varchar(25) NOT NULL,
  `bulan` varchar(25) NOT NULL,
  `bayar` int(10) NOT NULL,
  `tgl_bayar` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`no_trans`, `nis`, `nama`, `kelas`, `bulan`, `bayar`, `tgl_bayar`) VALUES
('SBP0001', 171830575, 'Ahmad Syahreza', '10 TI 8', '01', 150000, '06 Maret 2019'),
('SBP0002', 171830570, 'Ferguso', '10 TI 1', '03', 150000, '19 Maret 2019');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(45) NOT NULL,
  `nama` varchar(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`username`, `password`, `nama`) VALUES
('admin', '827CCB0EEA8A706C4C34A16891F84E7B', 'Restu Maulana');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agama`
--
ALTER TABLE `agama`
  ADD PRIMARY KEY (`id`),
  ADD KEY `agama` (`agama`);

--
-- Indexes for table `jurusan`
--
ALTER TABLE `jurusan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jurusan` (`jurusan`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`nis`),
  ADD KEY `agama` (`agama`),
  ADD KEY `jurusan` (`jurusan`),
  ADD KEY `nama` (`nama`),
  ADD KEY `kelas` (`kelas`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`no_trans`),
  ADD KEY `nis` (`nis`),
  ADD KEY `nama` (`nama`),
  ADD KEY `kelas` (`kelas`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `nama_3` (`nama`),
  ADD KEY `nama` (`nama`),
  ADD KEY `nama_2` (`nama`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `agama`
--
ALTER TABLE `agama`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `jurusan`
--
ALTER TABLE `jurusan`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `siswa`
--
ALTER TABLE `siswa`
  ADD CONSTRAINT `siswa_ibfk_1` FOREIGN KEY (`jurusan`) REFERENCES `jurusan` (`jurusan`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `siswa_ibfk_2` FOREIGN KEY (`agama`) REFERENCES `agama` (`agama`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`nama`) REFERENCES `siswa` (`nama`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_ibfk_3` FOREIGN KEY (`kelas`) REFERENCES `siswa` (`kelas`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
