# read the data
data <- read.table("household_power_consumption.txt", header=T, sep=";",na.strings = "?",stringsAsFactors=FALSE)
# filter relevant dates
df <- data[(data$Date=="1/2/2007") | (data$Date=="2/2/2007"),]
# modify date,time
df$DateTime <- as.POSIXct(strptime(paste(df$Date,df$Time), "%d/%m/%Y %H:%M:%S"))

#create plot3
plot(df$DateTime ,df$Sub_metering_1,xlab="", ylab="Energy sub metering", type="l")
lines(df$DateTime,df$Sub_metering_2,col="red")
lines(df$DateTime,df$Sub_metering_3,col="blue")
legend("topright", col=c("black","red","blue"), c("Sub_metering_1","Sub_metering_2", "Sub_metering_3"),lty=c(1,1))

# store png
dev.copy(png, file="plot3.png", width=480, height=480)
dev.off()