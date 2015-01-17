input<-read.xlsx("getdata_data_DATA.gov_NGAP.xlsx", sheetIndex = 1, header = TRUE , startRow = 18 ,endRow=23)
dat<-input[,7:15]
sum(dat$Zip*dat$Ext,na.rm=T)