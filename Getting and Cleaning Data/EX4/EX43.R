ta <- read.csv('getdata_data_GDP.csv',skip=3, nrows = 215)
countryNames<-ta$Economy
grep("^United",countryNames,value=TRUE)