
# read the data
data <- read.table("household_power_consumption.txt", header=T, sep=";",na.strings = "?",stringsAsFactors=FALSE)
# filter relevant dates
df <- data[(data$Date=="1/2/2007") | (data$Date=="2/2/2007"),]
# modify date,time
df$DateTime <- as.POSIXct(strptime(paste(df$Date,df$Time), "%d/%m/%Y %H:%M:%S"))

#create plot1
hist(df$Global_active_power, main = "Global Active Power", col="red", xlab="Global Active Power (kilowatts)")

# store png
dev.copy(png, file="plot1.png", width=480, height=480)
dev.off()
