complete <- function(directory, id = 1:332) {
  full_data <- vector()
  files_full <- list.files(directory, full.names=TRUE)
  for (i in id)
  {
    data <- read.csv(files_full[i])
    t<-subset(data,!is.na(sulfate) & !is.na(nitrate))
    calc_val <- nrow(t)
    full_data <- c(full_data, calc_val)
  }
  all_val <- data.frame(id,full_data)
  names(all_val) <- c("id","nobs")
  all_val
  ## 'directory' is a character vector of length 1 indicating
  ## the location of the CSV files
  
  ## 'id' is an integer vector indicating the monitor ID numbers
  ## to be used
  
  ## Return a data frame of the form:
  ## id nobs
  ## 1  117
  ## 2  1041
  ## ...
  ## where 'id' is the monitor ID number and 'nobs' is the
  ## number of complete cases
}