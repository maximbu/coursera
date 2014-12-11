corr <- function(directory, threshold = 0) {
  ## 'directory' is a character vector of length 1 indicating
  ## the location of the CSV files
  
  ## 'threshold' is a numeric vector of length 1 indicating the
  ## number of completely observed observations (on all
  ## variables) required to compute the correlation between
  ## nitrate and sulfate; the default is 0
  
  ## Return a numeric vector of correlations
  
  full_data <- vector()
  for (file in list.files(directory, full.names=TRUE))
  {
    t<-subset(read.csv(file),!is.na(sulfate) & !is.na(nitrate))
    if(nrow(t) > threshold)
    {
      full_data <- c(full_data, cor(t$sulfate,t$nitrate))
    }
    
  }

  full_data
}