library(dplyr)
ta <- read.csv('getdata_data_GDP.csv',skip=3, nrows = 215)
ta_df = tbl_df(ta)
ta_df<-rename(ta_df,CountryCode=X)

tb <- read.csv('getdata_data_EDSTATS_Country.csv')
tb_df = tbl_df(tb)
d<-merge(ta_df,tb_df, all = TRUE, by = c("CountryCode"))

notes<-tolower(d$Special.Notes)
length(grep("fiscal year end: june",notes))