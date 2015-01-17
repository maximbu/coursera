t<-read.fwf("getdata_wksst8110.for",skip=4,widths=c(-1,9,-5,4,-1,3,-5,4,-1,3,-5,4,-1,3,-5,4,-1,3))
sum(t$V4)