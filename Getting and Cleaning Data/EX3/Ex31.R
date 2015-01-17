acs <- read.csv("getdata_data_ss06hid.csv")
agricultureLogical<-(acs$ACR == '3' & acs$AGS=='6')
t<-acs[which(agricultureLogical),]
head(t,3)
