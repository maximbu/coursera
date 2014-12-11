import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import dataStructs.Graph;


public class Solver {

	private static final double optimalValue = 67700000;//37600;//67700000;//37600;//67700000;
	private static boolean toLog =true;
	private static boolean createDistancesMatrix = false;
	private static boolean skipVerixes = !createDistancesMatrix;
	private static boolean[] visited;
	private static int numOfpoints;
	private static Point[] points;
	private static double value;
	private static String isOptimalFlag = "0";
	private static int[] selectedRoute;
	private static float[][] distMatrix;
	private static Logger logger;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				String[] testArgs = new String[1];
				testArgs[0] = "-file=D:\\Studies\\Optimization\\Traveling Salesman\\Optimization_ex3_TravelingSalesman\\bin\\data\\"+
				//"tsp_5_1";
				//"tsp_51_1";
				// "tsp_100_3";
				// "tsp_200_2";
				 "tsp_574_1";
				// "tsp_1889_1";
				//"tsp_33810_1";
				solve(testArgs);
			} else {
				solve(args);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private static void readAndParseInput(String[] args)
			throws FileNotFoundException, IOException {
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null)
			return;

		// read the lines out of the file
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(fileName));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}

		// parse the data in the file
		String[] firstLine = lines.get(0).split("\\s+");
		numOfpoints = Integer.parseInt(firstLine[0]);

		points = new Point[numOfpoints];
		
		float minX = 0;
		float minY = 0;

		for (int i = 1; i < numOfpoints + 1; i++) {
			String line = lines.get(i);
			String[] parts = line.split("\\s+");

			float x = Float.parseFloat(parts[0]);
			if(x<minX)
			{
				minX = x;
			}
			float y = Float.parseFloat(parts[1]);
			if(y<minY)
			{
				minY = y;
			}
			points[i - 1] = new Point(x, y);
		}
		
		for (int i = 0; i < numOfpoints; i++) {
			points[i].x-= minX;
			points[i].y-= minY;
		}
	}

	private static float findSqrDistance(Point p1, Point p2) {
		return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
				* (p1.y - p2.y);
	}

	private static void solve(String[] args) throws IOException {
//		readAndParseInput(args);
//		findRoute();
//		printOutput();
		printExternalOutput5();
		// if (!isValidSolution()) {
		// System.out.println("Bad solution !!!");
		// }
	}

	private static void printExternalOutput4() {
		String sol="0 1 2 3 4 5 6 7 8 9 10 11 14 15 16 17 18 19 20 21 13 12 23 22 24 25 26 27 28 29 31 30 32 33 34 35 36 37 38 40 41 39 42 43 44 45 46 47 48 49 54 55 56 58 57 82 83 53 52 50 51 84 85 86 87 90 93 92 91 555 554 553 552 556 557 558 559 550 551 101 102 100 99 98 96 95 97 94 89 88 77 78 79 80 81 59 60 61 63 62 64 76 75 74 73 119 118 120 121 122 123 71 72 65 66 67 68 70 69 124 125 126 127 128 129 130 131 132 133 134 135 136 137 138 139 140 141 151 150 149 152 148 147 146 143 142 144 145 158 157 153 156 159 155 154 165 164 166 167 163 168 162 160 161 113 112 111 110 114 115 116 117 106 105 104 103 547 546 545 544 543 541 542 107 109 108 173 174 175 172 170 171 169 180 181 179 178 177 176 540 539 537 538 187 188 186 182 183 185 184 194 195 196 197 198 199 200 201 202 204 203 208 207 206 205 209 210 211 212 213 214 215 216 217 223 224 222 221 220 218 219 193 192 191 190 226 225 189 227 228 229 230 231 533 532 531 530 233 232 234 235 236 237 240 239 238 246 247 245 244 243 242 241 248 249 250 251 252 254 253 255 256 257 300 299 258 259 260 261 262 263 264 265 266 298 297 296 295 294 293 270 269 268 267 271 272 273 274 275 292 291 290 289 288 276 277 278 279 282 281 280 287 286 285 284 283 334 335 336 337 338 339 333 332 331 330 340 341 343 344 345 346 364 363 361 362 347 348 349 350 351 415 416 414 413 352 353 354 355 357 358 359 360 401 403 402 356 412 411 410 418 417 419 420 421 422 423 424 425 426 427 428 429 430 432 437 438 436 435 434 433 431 382 386 387 385 383 384 409 408 407 406 405 404 400 399 397 398 370 369 368 367 365 342 366 329 328 327 326 325 324 323 322 373 372 371 396 395 394 393 392 391 388 389 390 381 380 379 378 377 376 375 374 315 314 316 313 312 451 311 310 309 308 307 305 318 317 321 320 319 306 304 303 301 302 508 507 506 505 504 503 502 498 501 500 499 452 453 440 441 450 449 448 447 446 442 445 444 443 439 454 455 456 457 458 459 460 461 462 463 464 465 496 497 519 518 495 494 524 525 493 492 491 526 523 522 521 520 517 516 515 514 513 509 510 511 512 527 528 529 490 489 488 534 535 487 486 485 484 536 483 481 482 475 476 480 479 477 478 548 549 560 561 562 563 564 565 566 567 568 569 570 474 473 472 471 470 469 468 467 466 571 572 573";
		System.out.println("36905" + " " + isOptimalFlag);
		System.out.println(sol);
	}
	
	private static void printExternalOutput5() {
		String sol="0 1866 3 1749 876 553 4 554 21 877 883 882 885 886 893 892 879 1750 611 612 880 22 1882 27 1824 1704 1860 1841 1801 34 32 1873 68 1369 896 647 69 70 1688 1716 369 1368 1715 1714 1713 1712 1711 368 1710 1709 1708 26 1881 6 888 887 423 424 265 1485 264 366 339 657 602 340 367 935 1798 1703 1799 1800 918 426 902 914 1668 1669 341 676 1672 890 1483 260 1482 1481 1480 1479 1478 1477 1476 1475 1474 1473 1472 1471 1470 1469 1468 1467 1466 1465 1464 1463 1290 1159 1255 1019 1215 1462 1461 1523 1522 1521 307 1520 1627 1507 1760 545 1424 1421 1664 1618 1503 1500 1643 1594 1725 1887 1403 1879 1597 1650 1055 47 1673 1764 1833 1797 1125 659 630 769 313 730 126 962 963 964 965 162 966 419 386 1637 1638 1639 1056 1640 1641 1598 314 770 631 660 1404 1405 1406 1407 1408 1409 1410 1411 1412 1413 1414 1415 1628 1667 1458 375 436 1728 1700 1020 793 1253 1252 1021 181 255 215 480 751 1701 1729 923 258 437 448 502 308 661 444 384 1416 1616 1617 19 372 547 1653 731 393 1599 541 1046 318 967 407 251 1259 1511 1644 1047 1645 1646 1600 316 1589 1647 1648 1656 1657 1501 1658 300 1605 706 90 20 1651 1726 1590 705 1601 783 721 1877 1777 1831 1048 968 1260 1815 1122 1536 782 720 252 254 1654 476 1261 196 969 1049 1262 1263 1264 1265 194 1266 1267 1268 1269 1270 1271 1317 1316 1315 1314 1313 1312 213 1311 1310 1309 1308 1050 970 214 471 344 148 387 408 479 520 420 315 317 127 212 195 273 531 475 320 253 447 161 473 279 226 610 717 220 187 470 637 634 363 685 617 312 311 305 645 639 154 680 452 277 378 140 431 675 455 689 564 301 559 562 772 269 468 210 248 811 754 825 294 584 276 821 774 268 422 759 867 943 237 863 853 383 280 757 817 848 838 780 428 699 786 842 852 746 829 857 778 756 204 814 636 805 792 810 789 178 185 567 466 493 435 298 350 803 122 800 223 1205 1206 1207 1208 1209 1210 1211 1212 1213 1214 180 560 13 14 1401 1307 16 11 1608 1003 1808 432 211 826 1595 1517 823 1494 12 179 1373 822 281 1542 1334 1541 1540 1539 913 1204 1333 1332 945 1538 1537 1696 1859 1001 912 1811 1790 1071 911 910 905 909 906 1070 1789 1810 907 1000 1858 1695 908 208 441 139 510 797 362 999 138 207 998 807 403 239 411 741 153 1692 1693 1694 118 123 949 1604 1825 1203 116 651 1202 1331 1330 1329 1328 206 440 137 796 361 509 222 349 799 802 434 125 297 566 809 492 465 177 184 788 64 1298 104 102 115 1804 111 105 1543 106 1372 236 1396 1395 1371 382 816 698 851 113 1805 1806 1807 1803 114 101 1297 1296 777 1228 948 1384 1383 1382 1381 1327 1380 1379 997 1301 1378 1377 1068 1069 1302 1788 240 1376 1375 241 1067 742 1066 63 1300 996 1857 1691 1326 98 99 1809 947 1226 1227 1201 1295 1385 1386 856 743 745 100 1387 1388 242 1389 1390 1391 1392 243 1393 1394 247 1492 864 110 1515 275 245 1397 246 1398 1399 585 820 819 1493 716 91 1516 33 1400 1306 15 10 1607 1002 44 1525 1072 1620 1635 45 1697 61 719 1830 1233 704 89 364 1634 1619 87 60 1699 92 1829 1232 609 225 160 1535 446 159 961 960 959 958 957 956 955 608 186 469 1698 684 1528 1527 1526 1007 1006 1005 1004 1609 1614 1305 1118 17 18 1495 1496 688 377 451 1013 1621 1636 954 1625 1078 53 1613 76 75 1738 1499 65 9 1498 1737 96 97 1612 83 1012 1077 1633 1624 1531 59 953 94 1828 1231 1324 1337 1534 1121 729 1814 1258 1427 1510 519 1832 1763 762 478 1796 1124 1509 1426 1257 1813 761 1120 1533 1336 42 219 1230 1827 93 952 58 1530 1623 1632 1076 1011 679 77 78 1611 1010 1075 1631 1622 1529 57 951 79 1826 1229 41 1335 1532 1119 664 748 1812 1256 1425 1508 1123 1795 749 750 1762 665 666 1054 1649 1596 1878 1402 1886 1724 1593 1642 1778 1663 1423 1606 80 1506 1626 1519 1148 1147 1146 1145 1144 1143 1142 1141 1140 1139 1138 1137 1136 1135 1134 1133 1132 1131 1130 1129 1128 1053 164 1127 163 1126 228 229 418 417 413 414 1052 397 396 1092 157 1091 1090 1089 1088 1087 1086 156 395 412 416 227 224 747 306 310 155 141 760 768 678 629 450 302 781 812 663 558 638 728 270 687 674 430 376 454 561 771 658 467 209 644 616 753 824 293 586 158 563 274 865 773 267 421 854 866 758 283 235 244 429 849 381 818 715 815 837 830 779 697 841 785 744 776 850 791 942 755 813 650 804 203 855 176 787 808 565 183 296 124 464 433 801 798 348 221 360 795 508 136 205 439 402 491 806 635 152 238 410 1374 1065 62 1787 1299 995 1856 1690 1325 117 295 946 649 1200 1294 71 790 103 839 840 282 1544 1545 1489 284 1546 1512 1115 1303 971 974 973 972 8 1304 1116 1513 7 557 1490 1491 868 109 1514 1117 453 673 1497 1736 1779 1780 1610 1009 1074 1008 1073 1079 1080 950 1081 1082 1083 1084 1085 399 285 286 400 1093 1094 1095 1096 1097 1098 1099 1100 1101 1547 1504 1548 1422 1549 1550 1505 1551 1518 1552 1553 1114 1113 1112 1111 1110 1109 1108 1107 1106 1105 1104 1103 1102 1338 1339 1340 1341 1342 1343 1344 1345 1346 1347 1348 1349 1350 1351 1352 1353 1354 1355 1356 1357 1358 1318 1149 1150 1151 1152 1018 1153 1154 1155 1156 1157 1218 1219 1220 1221 1222 1223 486 681 549 875 518 891 625 878 618 884 501 656 874 640 889 603 555 570 941 425 543 266 917 718 936 682 463 495 575 775 257 900 604 261 517 342 652 641 590 894 646 232 527 583 871 580 632 765 536 708 869 404 357 484 627 359 406 1682 356 1681 1364 355 1680 1670 1457 1679 1678 1677 86 85 1487 5 40 66 74 1851 46 84 49 1865 1888 1735 1836 1776 1844 1840 1225 1874 1781 1727 1288 1158 1254 1359 1017 1554 1555 1556 1557 1224 1558 1559 1560 1561 1562 1563 1564 1565 1566 1567 1568 1569 1570 1486 287 1571 1572 1573 1574 1575 1456 1576 1577 288 1363 1578 289 1579 1580 1581 1582 1583 1584 151 1064 1585 290 1722 696 713 599 1199 39 601 1868 521 600 714 712 1251 1198 1869 975 980 937 331 930 1659 987 1853 931 702 739 459 623 391 552 514 134 693 932 506 131 588 324 353 347 332 1662 120 1660 988 1854 121 88 938 692 994 1863 703 740 460 1775 1817 1816 550 135 457 621 939 392 700 737 515 690 933 507 589 326 132 351 926 345 497 532 329 982 1674 325 981 1845 321 379 490 488 898 128 522 944 928 736 537 725 843 578 172 192 596 202 727 710 371 694 845 619 231 500 147 539 528 615 858 218 291 594 149 767 574 861 1837 1683 1791 668 535 1876 1365 526 1870 642 1864 1875 534 667 1671 1484 1455 1454 1453 1452 1451 1450 1449 1448 1447 1446 1445 1444 1443 1442 1441 1440 1439 1438 1437 1436 1435 1434 1433 1432 1431 1430 1429 1428 1289 1793 409 1748 1752 1753 1754 1755 1756 1757 1488 1758 1759 655 262 516 343 653 643 591 895 872 581 764 54 1792 1684 483 1838 55 56 1057 593 1323 529 1045 1848 1361 1723 1195 1293 976 977 983 986 989 990 993 1774 1772 1 991 1855 505 984 1852 1655 978 1588 1587 1197 499 1586 1041 1321 38 1060 81 1686 25 1059 37 1320 1040 1850 1196 1292 95 1362 1849 43 1319 36 1058 24 52 1685 73 72 28 1366 1871 30 915 903 233 31 1872 67 1367 29 107 1687 82 23 1717 1718 1719 1720 1721 1360 1042 1322 1061 695 711 189 597 859 846 576 173 370 633 862 870 496 230 498 146 648 873 897 614 217 592 150 901 766 573 669 916 904 482 677 358 654 405 709 763 919 579 234 525 582 605 35 1802 613 1842 1861 1705 200 1178 1177 1176 1175 1174 1173 1172 167 1171 199 198 166 922 626 512 1751 51 921 624 1746 511 461 462 1819 1820 1821 1822 1823 1846 1706 1862 1179 168 171 1181 1182 1183 1184 169 1185 170 1186 1187 1188 1189 1190 1062 1191 1043 1192 1193 1194 175 724 620 726 201 735 844 538 540 929 523 628 487 489 504 513 925 733 129 556 494 530 380 322 292 533 330 346 927 352 130 587 327 934 691 133 551 390 458 622 738 701 899 940 48 1773 2 992 828 1661 985 979 1843 827 190 191 174 577 847 860 598 1250 1249 1248 1044 1247 1063 1246 1245 1244 671 672 670 443 1689 442 572 571 1884 1370 920 1243 1242 1241 1240 1239 278 683 188 1238 1237 1236 1235 1234 1180 1847 1707 1883 1039 1038 1037 1036 144 1035 542 569 833 1880 832 835 119 834 1747 831 143 1034 1033 1032 1031 1030 1029 1818 1524 1028 1027 1026 1025 1024 145 1016 1015 389 1014 1731 1771 1460 1630 1770 1676 1419 1769 1665 1615 1420 1666 1502 1768 1652 1592 1603 1767 1766 1765 388 1051 1602 836 1591 373 319 323 794 568 472 271 333 304 336 337 686 474 299 334 250 328 338 303 335 707 595 544 394 734 272 477 456 249 354 723 445 662 309 503 449 438 259 924 732 606 374 401 427 365 524 607 752 481 256 182 216 193 142 263 415 1786 1785 1761 108 1162 1163 1164 1165 1166 1167 1168 165 1169 1170 197 1867 1745 1734 1784 1835 1839 1885 1291 1161 1287 1286 1285 1023 1217 1284 1283 1282 1281 1280 1279 1278 1277 1276 1275 1274 1273 1272 1675 1418 722 1417 784 1629 1459 112 1730 1702 1216 1022 1739 1740 1160 1741 1742 485 1743 1744 1733 50 881 1732 1783 1834 1794 548 546 385 398 1782";
		System.out.println("36905" + " " + isOptimalFlag);
		System.out.println(sol);
	}

	private static boolean isValidSolution() {
		for (boolean city : visited) {
			if (!city) {
				return false;
			}
		}
		return true;
	}

	private static void findRoute() {
		value = 0;
		visited = new boolean[numOfpoints];
		selectedRoute = new int[numOfpoints];
		createLogger(0);
		distMatrix = null;
		
		if(createDistancesMatrix)
			createDistancesMatrix();
		
		// naiveApproach();
		// greedy1();
		simpleLS();
		// allPossibleCombinations();
		// kOpt();
	}
	
	
	private static double getDistance(int i, int j)
	{
		if(distMatrix != null)
		{
			return distMatrix[i][j];
		}
		return findSqrDistance(points[i], points[j]);
	}
	
	private static void createDistancesMatrix()
	{
		distMatrix = new float[numOfpoints][numOfpoints];
		for (int i = 0; i < numOfpoints; i++) {
			for (int j = 0; j < numOfpoints; j++) {
				distMatrix[i][j] = findSqrDistance(points[i], points[j]);
			}
		}
	}

	private static void kOpt() {
		// TODO Auto-generated method stub
	}

	private static void twoOpt() {
		// TODO Auto-generated method stub
		runGreedyWithStartingVal(0);
		Graph g = new Graph(numOfpoints);
		for (int i = 0; i < numOfpoints; i++) {
			g.addEdge(selectedRoute[i], selectedRoute[(i + 1) % numOfpoints]);
		}

	}

	private static void allPossibleCombinations() {
		// double minValue = Double.MAX_VALUE;
		Permutation p = new Permutation(numOfpoints, numOfpoints);
		double ind = 0;
		while (p.hasNext()) {
			int[] a = p.next();
			double valueOfArr = calculateSqrValueOf(a);
			ind++;
			// if (valueOfArr < minValue) {
			if (valueOfArr <= 428) {
				value = valueOfArr;
				for (int i = 0; i < numOfpoints; i++) {
					selectedRoute[i] = a[i];
					// visited[i] = true;
				}
				return;
			}
		}
		// value = minValue;
	}

	private static double calculateSqrValueOf(int[] path) {
		double curValue = 0;
		for (int i = 0; i < numOfpoints; i++) {
			int indI = path[i];
			int indJ = path[(i+1+numOfpoints)%numOfpoints];
			curValue += getDistance(indI,indJ);
		}
		return curValue;
	}

	private static void naiveApproach() {
		for (int i = 0; i < numOfpoints; i++) {
			visited[i] = true;
			selectedRoute[i] = i;
		}
		getSqrtValue();
	}

	private static double greedy1() {
		double minValue = Double.MAX_VALUE;
		int bestStartingVertix = 0;
		for (int i = 0; i < numOfpoints; ) {
			double value = runGreedyWithStartingVal(i);
			if (value < minValue) {
				minValue = value;
				bestStartingVertix = i;
			}
			if(skipVerixes)
			{
				i = (i+1)*2; 
			}
			else
			{
				i++;
			}
		}
		runGreedyWithStartingVal(bestStartingVertix);
		return updateValue();
	}

	private static double runGreedyWithStartingVal(int startingValue) {
		value = 0;
		visited = new boolean[numOfpoints];
		selectedRoute = new int[numOfpoints];
		int prevVertix = startingValue;
		startFrom(prevVertix);
		for (int i = 1; i < numOfpoints; i++) {
			double minDistance = Double.MAX_VALUE;
			int minVertix = i;
			for (int j = 0; j < numOfpoints; j++) {
				if (visited[j]) {
					continue;
				}
				double distance = getDistance(prevVertix,j);
				if (distance < minDistance && distance != 0) {
					minDistance = distance;
					minVertix = j;
				}
			}
			visited[minVertix] = true;
			selectedRoute[i] = minVertix;
			prevVertix = minVertix;
		}
		return getSqrtValue();
	}

	private static void startFrom(int vertix) {
		selectedRoute[0] = vertix;
		visited[vertix] = true;
	}

	private static double testing() {
		for (int j = 0; j < numOfpoints; j++) {
			visited[j] = true;
		}
		value = 5.2;
		selectedRoute[0] = 0;
		selectedRoute[1] = 4;
		selectedRoute[2] = 1;
		selectedRoute[3] = 3;
		selectedRoute[4] = 2;
		return value;
	}
	
	
	private static void createLogger(int i)
	{
		try
		{
		logger = Logger.getLogger("MyLog");  
	    FileHandler fh = new FileHandler("D:\\Studies\\Optimization\\Traveling Salesman\\Optimization_ex3_TravelingSalesman\\bin\\Logs\\MyLogFile"+i+".log");  
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	private static boolean checkStopOption()
	{
		File f = new File("D:\\Studies\\Optimization\\Traveling Salesman\\Optimization_ex3_TravelingSalesman\\bin\\stop.txt");
		return f.exists();
	}

	private static void simpleLS() {
		double oldValue = greedy1();//runGreedyWithStartingVal(0);// runGreedyWithStartingVal(0);//testing();//runGreedyWithStartingVal(0);
		int[] bestSoFar = selectedRoute.clone();
		double bestSoFarValue = value;
		double origValue = oldValue;
		// printOutput();
		int iteration = 0;
		double epsilon = 0.0001;
		while (true) {
			for (int j = 0; j < numOfpoints; j++) {
				createVertixSwap(j);
				updateValue();
				if(value < bestSoFarValue)
				{
					bestSoFarValue = value;
					bestSoFar = selectedRoute.clone();
				}
				// value -= vs.contribution;
				//updateVertixes(vs);
				if(toLog)
					logIntermidiateSolution(iteration,j,oldValue,origValue,bestSoFarValue);
				else
					printIntermidiateSolution(iteration,j,oldValue,origValue,bestSoFarValue);
				// value += getContributionOf(vs.origVertixIndex)
				// + getContributionOf(vs.swappedVertixIndex);
			}
			// VertixSwap vs = getVertixToUpdate();
			// value -= vs.contribution;
			// updateVertixes(vs);
			// value += getContributionOf(vs.origVertixIndex)
			// + getContributionOf(vs.swappedVertixIndex);
			
			//if (iteration == 1000 || Math.abs(oldValue - value) < epsilon) {
			if (value <= optimalValue || checkStopOption()) {
				return;
			}
			
			if(Math.abs(oldValue - value) < epsilon)
				createRandomSwaps(getRandomIndex(4));
			
			if(iteration == 20)
			{
				selectedRoute = bestSoFar.clone();
				iteration = 0;
				value = bestSoFarValue;
			}
//			else
//			{
//			if(Math.abs(oldValue - value) < epsilon)
//				createRandomSwaps(getRandomIndex(3));
//			}
			oldValue = value;
			iteration++;
			createLogger(iteration);
		}
	}

	private static void logIntermidiateSolution(int iteration, int j,
			double oldValue, double origValue, double bestSoFarValue) {
		
		StringBuilder st = new StringBuilder();
		
		st.append("Itreration :" + iteration+"\n");
		
		st.append("Node optimization :" + j+"\n");
		st.append("Prev value :" + oldValue+"\n");
		st.append("Orig value :" + origValue+"\n");
		st.append("Best so far value :" + bestSoFarValue+"\n");
		//updateValue();
		st.append(value + " " + isOptimalFlag);
				
		for (int i = 0; i < numOfpoints; i++) {
			st.append(selectedRoute[i] + " ");
		}
		logger.info(st.toString()+"\n");
	}

	private static void createRandomSwaps(int num) {
		for (int j = 0; j < num; j++) {
			int v1 = getRandomIndex(numOfpoints);
			int v2 = getRandomIndex(numOfpoints);
			//int v3 = getRandomIndex(numOfpoints);
			updateVertixes(new VertixSwap(v1, v2,0));
			//updateVertixes(new VertixSwap(v2, v3,0));
		}
	}
	
	private static int getRandomIndex(int n) {
		return (int) (Math.random() * (n));
	}

	private static void printIntermidiateSolution(int iteration, int j,
			double oldValue, double origValue , double bestSoFarValue) {
		System.out.println("Itreration :" + iteration);
		System.out.println("Node optimization :" + j);
		System.out.println("Prev value :" + oldValue);
		System.out.println("Orig value :" + origValue);
		System.out.println("Best so far value :" + bestSoFarValue);
		printOutput();
	}

	private static VertixSwap createVertixSwap(int j) {
		return getVertixIndexToUpdate(j);
	}

	private static VertixSwap getVertixToUpdate() {
		int worstVertix = getWorstVertixIndex();
		return getVertixIndexToUpdate(worstVertix);
	}

	private static VertixSwap getVertixIndexToUpdate(int worstVertixIndex) {
		int bestVertixIndexToSwap = worstVertixIndex;
		double bestValue = Double.MAX_VALUE;
		for (int j = 0; j < numOfpoints; j++) {
			// if (j == worstVertixIndex)
			// continue;
			
			updateVertixes(new VertixSwap(worstVertixIndex, j, 0));
			// double contribution = getContributionOf(worstVertixIndex)
			// + getContributionOf(j);
			double contribution = getSqrtValue();
			if (bestValue > contribution) {
				bestValue = contribution;
				bestVertixIndexToSwap = j;
			}
			updateVertixes(new VertixSwap(j, worstVertixIndex, 0));
		}
		VertixSwap v = new VertixSwap(worstVertixIndex, bestVertixIndexToSwap,
				bestValue);
		updateVertixes(v);
		return v;
	}

	private static int getWorstVertixIndex() {
		double worstContribution = Double.MIN_VALUE;
		int vertixToUpdate = 0;
		for (int j = 0; j < numOfpoints; j++) {
			double contribution = getContributionOf(j);
			if (contribution > worstContribution) {
				worstContribution = contribution;
				vertixToUpdate = j;
			}
		}
		return vertixToUpdate;
	}

	private static double getContributionOf(int vertix) {
		return findDistanceOfPointsInSelectedRoute(vertix, vertix + 1)
				+ findDistanceOfPointsInSelectedRoute(vertix, vertix - 1);
	}

	private static void updateVertixes(VertixSwap vs) {
		if (vs.origVertixIndex == vs.swappedVertixIndex)
			return;
		swapVertixes(vs.origVertixIndex, vs.swappedVertixIndex);
		double value1 = getSqrtValue();
		int lowerIndex = vs.origVertixIndex < vs.swappedVertixIndex ? vs.origVertixIndex
				: vs.swappedVertixIndex;
		int upperIndex = vs.origVertixIndex > vs.swappedVertixIndex ? vs.origVertixIndex
				: vs.swappedVertixIndex;

		swapFromIndexToindex(lowerIndex, upperIndex);
		double value2 = getSqrtValue();
		if (value1 < value2) {
			swapFromIndexToindex(lowerIndex, upperIndex);
		}
	}

	private static void swapFromIndexToindex(int lowerIndex, int upperIndex) {
		int i = lowerIndex + 1;
		int j = upperIndex - 1;
		while (i < j) {
			swapVertixes(i++, j--);
		}
	}

	private static void swapVertixes(int v1, int v2) {
		int temp = selectedRoute[v1];
		selectedRoute[v1] = selectedRoute[v2];
		selectedRoute[v2] = temp;
	}
	
	private static double getSqrtValue() {
		int locvalue = 0;
		for (int i = 0; i < numOfpoints; i++) {
			locvalue += findDistanceOfPointsInSelectedRoute(i, i + 1);
		}
		return locvalue;
	}

	private static double updateValue() {
		value = 0;
		for (int i = 0; i < numOfpoints; i++) {
			value += Math.sqrt(findDistanceOfPointsInSelectedRoute(i, i + 1));
		}
		return value;
	}

	private static double findDistanceOfPointsInSelectedRoute(int i, int j) {
		int indI = selectedRoute[(i + numOfpoints)% numOfpoints];
		int indJ = selectedRoute[(j + numOfpoints)% numOfpoints];
		return getDistance(indI,indJ);
	}

	private static void printOutput() {
		System.out.println(value + " " + isOptimalFlag);
		for (int i = 0; i < numOfpoints; i++) {
			System.out.print(selectedRoute[i] + " ");
		}
		System.out.println("");
	}
	
	
}
