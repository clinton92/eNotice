-- phpMyAdmin SQL Dump
-- version 3.5.8.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 28, 2014 at 10:53 PM
-- Server version: 5.5.34-0ubuntu0.13.04.1
-- PHP Version: 5.4.9-4ubuntu2.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `enotice`
--

-- --------------------------------------------------------

--
-- Table structure for table `gcmuser`
--

CREATE TABLE IF NOT EXISTS `gcmuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regid` text,
  `first` varchar(25) DEFAULT NULL,
  `last` varchar(25) DEFAULT NULL,
  `user` varchar(25) DEFAULT NULL,
  `pass` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `gcmuser`
--

INSERT INTO `gcmuser` (`id`, `regid`, `first`, `last`, `user`, `pass`, `email`, `mobile`) VALUES
(1, 'APA91bHXVJC5EmLIZalxWj6UbXdcnIL_HhDwuQxK7lF-tzKWM_7w2mh2un-lcHDaZiwFkC_pO5mx-3Eis1DKMCrREbzXbdrPu5fYsv_rGDfyH73GQlb6P0ZHecE7g0QOtzCn7zGiVK8RxqjEOKOy6WVgeB-OP4FuZg', 'Priyanka', 'Kapoor', 'priyanka', 'pk', 'anjalicool.kapoor444@gmail.com', '9888903138'),
(3, 'APA91bHXVJC5EmLIZalxWj6UbXdcnIL_HhDwuQxK7lF-tzKWM_7w2mh2un-lcHDaZiwFkC_pO5mx-3Eis1DKMCrREbzXbdrPu5fYsv_rGDfyH73GQlb6P0ZHecE7g0QOtzCn7zGiVK8RxqjEOKOy6WVgeB-OP4FuZg', 'Akshay', 'Giridhar', 'admin', 'pass', 'akshay@gmail.com', '9876556789');

-- --------------------------------------------------------

--
-- Table structure for table `notices`
--

CREATE TABLE IF NOT EXISTS `notices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text,
  `description` text,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `filepath` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data for table `notices`
--

INSERT INTO `notices` (`id`, `title`, `description`, `date`, `filepath`) VALUES
(1, 'Fee Submission', 'All are requested to submit the fee by 5th of January. After that, fine of Rs. 1000 will be imposed for each day. Fee for this semester can be seen from college website.', '2014-05-25 18:23:49', NULL),
(2, 'Athletic Meet', 'College is going to have its 53rd Athletic Meet. No class will be there. All are requested to reach the ground at 9 am sharp. Firstly there will be recitation of National Anthem. After that, March Past will go for all the departments. There is a speech by our Guest, Mr. M. S. Pathania, Head of Sports Department PTU, Jalandhar.', '2014-05-25 18:24:17', NULL),
(3, 'Viva Voce', 'This is to inform all IT students that your viva for 6 month Industrial Training would be at 30th May. So be ready with your working project, report and dialy diary. Timings for each slot are displayed on college website.', '2014-05-25 18:24:26', NULL),
(4, 'Sessional Test', 'All are informed to get ready for sesional tests. it is going to conduct on 25th of May. The required datesheet is attached. Please have a look. As it is a proposed datesheet, we require you to please check again few days later.', '2014-05-25 18:24:31', NULL),
(5, 'Datesheet Updated', 'Datesheet have been updated. Note your datesheet as it is a final one now.', '2014-05-25 18:24:42', NULL),
(6, 'Staff Meeting', 'All staff member are here by informed that there is a meeting today at 4 pm. So all are humbly requested to be there on time in TCC Conference Hall. Its mandatory for all staff members.);\n', '2014-05-25 18:24:47', NULL),
(7, 'New Admissions', 'New admissions for B.Tech are going to start from 1st June. The form can be taken from PG Block. Fee for the same is Rs. 2500', '2014-05-25 18:24:52', NULL),
(8, 'Fee Refund', 'Students of final year are requested to have an account in Punjab and Sindh Bank in order to get back their security fee. No case regarding this will be entertained without having account in the bank.', '2014-05-25 18:28:31', NULL),
(9, 'Admit Card', 'Students are requested to collect their admit card from their respective advisors. No student will be allowed to sit in exam without admit card.', '2014-05-25 18:38:21', NULL),
(10, 'Genesis 2014', 'Genenis is going to held on 26 th of Feb. Jassi Gill is our special guest this time. Gating will be there on that day. So bring your admit cards with you.', '2014-05-25 18:49:12', NULL),
(11, '', '', '2014-05-25 19:14:39', NULL),
(12, 'Library Books', 'Book Bank Section is now open for all students. If anybody wants a book from library, he or she can come and take from book bank section.', '2014-05-25 19:18:39', NULL),
(13, 'Ethical Hacking Workshop', 'Today is workshop on Ethical Hacking by Mr. Parneet Chopra , Dean of Drona Institute. Its mandatory for all IT students. Be there at 4 pm.', '2014-05-25 19:25:31', NULL),
(14, 'LOST ID', 'This is to inform that we found an ID card in library of Avneesh. So he is advised to take it from librarian as soon as possible.', '2014-05-25 19:58:18', NULL),
(15, 'Akhand Paath', 'All students are informed that there will be Akhand Paath from friday upto three days. Students who want to do seva can give their names to Mukesh, Head boy, final year, IT.', '2014-05-25 20:22:10', NULL),
(16, 'Chess Competition', 'All are informed that trials of chess tournament are going on. If anybody interested in becoming a college player, he can come in trials.', '2014-05-26 02:30:40', NULL),
(17, 'Mock Interviews', 'SAE is organising mock interviews in collaboration with Training and Placement Cell in order to improve soft skills and confidence of students. Interviews will be taken by our respected TNP dean, Mr. K.S. Maan. Timings are 9 am to 1 pm in TNP Conference hall.', '2014-05-26 02:37:57', NULL),
(18, 'No Dues', 'All students are requested to clear all the balances of Mess and take no dues slips from warden. Admit cards will be given only after taking no dues slips from students.', '2014-05-26 02:53:42', NULL),
(19, 'Synopis Submission', 'All students who are at 6 month industrial training are required to submit their synopsis by 10th of March.', '2014-05-26 03:26:13', NULL),
(20, 'Offer Letter', ' All students who are placed in TCS are informed to submit their Offer Letter to Training and placement cell by 23rd of May', '2014-05-26 03:29:21', NULL),
(21, 'International Conference', 'All staff members are informed that there is international conference on Green Technology in our college campus. Anyone wants to attend, can register for the same online.', '2014-05-26 03:44:15', NULL),
(22, 'Internship Program', 'All first and second year students are informed to participate in Internship Program for their 6 week industrial training.', '2014-05-26 03:53:00', NULL),
(23, 'Lost Activa', 'Hello, I am loveen.  My activa has been lost from outside college gate. It was locked. Dont know where it is now. Its of black color with college sticker number 54. If anybody has seen it, please inform in PG block.', '2014-05-26 04:02:40', NULL),
(24, 'Industrial Trip', 'All the second and third year students are informed that Industrial trip is going to Mohali. Its compulsory for all. Its a one day trip only. Return time is most probably 6 pm', '2014-05-26 05:21:07', NULL),
(25, 'Farewell Party', 'All mechanical and civil branch students are informed that there is farewall party organised by college for them. Contri for each person is Rs. 500. Deposite the fee in admin section by 30 th of May.', '2014-05-26 05:35:59', NULL),
(26, 'Report Format', 'The report format of 6 month industrial training students is now on college website. Read the instructions carefully in it. Nothing should be different from it. You have to submit two copies of report along with CD and certificates.', '2014-05-26 15:18:52', 'http://192.168.43.165/uploads/adbb19c20b9741e927d215eb919a56a9.jpg'),
(27, 'Golden Jublie of First Batch', 'This is to inform all that our college is celebrating the Golden Jublie of our first Batch of our college. All staff members are invited. Along with this, class representatives of all classes are also invited. Come and enjoy the company of your seniors.', '2014-05-26 21:37:08', ''),
(28, 'yiobvn', 'cjkg', '2014-05-27 13:59:12', 'http://192.168.43.165/uploads/IMG00084-20140418-1425.jpg'),
(29, 'hello testing', 'hiii', '2014-05-27 14:01:18', NULL),
(30, 'Library Books', 'All students who are having any library book should return it by 30 th of May. After that double penalty will be imposed.', '2014-05-28 05:18:07', NULL),
(31, 'hiii', '', '2014-05-28 05:41:04', NULL),
(32, 'guhk', '', '2014-05-28 05:42:20', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'admin', 'pass'),
(2, 'priyanka', 'pk'),
(3, 'riya', 'riya'),
(4, 'anjali', 'anjali'),
(5, 'swati', 'swati'),
(6, 'mona', 'mona'),
(7, 'raman', 'raman'),
(8, 'rimpy', 'rimpy');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
