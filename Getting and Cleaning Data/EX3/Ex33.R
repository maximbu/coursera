library(dplyr)
ta <- read.csv('getdata_data_GDP.csv',skip=3, nrows = 215)
ta_df = tbl_df(ta)
ta_df<-rename(ta_df,CountryCode=X)

tb <- read.csv('getdata_data_EDSTATS_Country.csv')
tb_df = tbl_df(tb)
d<-merge(ta_df,tb_df, all = TRUE, by = c("CountryCode"))

sum(!is.na(unique(d$Ranking)))

t_d = tbl_df(d)

sorted <- arrange(t_d, desc(Ranking))
sorted[13,1:4]


summarize(group_by(t_d,Income.Group),mean(Ranking,na.rm = TRUE))

install.packages("Hmisc")
library(Hmisc)
groups<- cut2(d$Ranking,g=5)
t<-table(d$Income.Group,d$groups)
t["Lower middle income",1]