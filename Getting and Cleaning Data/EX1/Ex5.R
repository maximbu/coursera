DT <- fread("getdata_data_ss06pid.csv")
t1<-system.time(
for (i in 1:100)
{
  tapply(DT$pwgtp15,DT$SEX,mean)
}
)

t2<-system.time(
  for (i in 1:100)
  {
    mean(DT[DT$SEX==1,]$pwgtp15); mean(DT[DT$SEX==2,]$pwgtp15)
  }
)

t3<-system.time(
  for (i in 1:100)
  {
    DT[,mean(pwgtp15),by=SEX]
  }
)
t4<-system.time(
  for (i in 1:100)
  {
    sapply(split(DT$pwgtp15,DT$SEX),mean)
  }
)