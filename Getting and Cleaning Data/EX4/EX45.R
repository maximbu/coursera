install.packages("quantmod")
library(quantmod)
amzn = getSymbols("AMZN",auto.assign=FALSE)
sampleTimes = index(amzn) 
sample_2012<-sampleTimes[which(year(sampleTimes)==2012)]
length(sample_2012)
length(sample_2012[which(weekdays(sample_2012)=="Monday")])