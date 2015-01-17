input<-xmlTreeParse("getdata_data_restaurants.xml",useInternal = TRUE)
v<-xpathSApply(input,"//zipcode",xmlValue)
length(v[v=='21231'])