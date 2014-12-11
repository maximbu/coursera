pollutantmean <- function(directory, pollutant, id = 1:332) {
  ## 'directory' is a character vector of length 1 indicating
  ## the location of the CSV files
  
  ## 'pollutant' is a character vector of length 1 indicating
  ## the name of the pollutant for which we will calculate the
  ## mean; either "sulfate" or "nitrate".
  
  ## 'id' is an integer vector indicating the monitor ID numbers
  ## to be used
  ans <- 0
  all_val <- vector()
  files_full <- list.files(directory, full.names=TRUE)
  for (i in id)
  {
    data <- read.csv(files_full[i])
    to_calc <- if (pollutant == "sulfate")
    {
      data$sulfate
    }
    else
    {
      data$nitrate
    }
    all_val <- c(all_val, to_calc)
  }
  mean(all_val,na.rm=TRUE)
  
  
  ## Return the mean of the pollutant across all monitors list
  ## in the 'id' vector (ignoring NA values)
}