acs <- read.csv("getdata_data_ss06pid.csv")
t<-sqldf("select pwgtp1 from acs where AGEP < 50")
u1<-sqldf("select distinct AGEP from acs")