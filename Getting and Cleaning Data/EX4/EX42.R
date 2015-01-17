install.packages("data.table")
library(data.table)
ta <- read.csv('getdata_data_GDP.csv',skip=3, nrows = 215)
remove_commas <- function(x){gsub(",","",x)}
t<-sapply(ta$US.dollars[2:191],remove_commas)
mean(as.numeric(t))