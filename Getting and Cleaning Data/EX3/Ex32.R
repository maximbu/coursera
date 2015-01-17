install.packages("jpeg")
library(jpeg)
img <- readJPEG("getdata_jeff.jpg",native=TRUE)
quantile(img, probs = c(0.3, 0.8))