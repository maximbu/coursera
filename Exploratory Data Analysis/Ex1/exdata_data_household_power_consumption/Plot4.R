# read the data
data <- read.table("household_power_consumption.txt", header=T, sep=";",na.strings = "?",stringsAsFactors=FALSE)
# filter relevant dates
df <- data[(data$Date=="1/2/2007") | (data$Date=="2/2/2007"),]
# modify date,time
df$DateTime <- as.POSIXct(strptime(paste(df$Date,df$Time), "%d/%m/%Y %H:%M:%S"))

#create subplots 2x2
par(mfrow=c(2,2))

#subplot 1
plot(df$DateTime ,df$Global_active_power,xlab="", ylab="Global Active Power (kilowatts)", type="l")

#subplot 2
plot(df$DateTime ,df$Voltage,xlab="datetime",ylab="Voltage", type="l")

#subplot 3
plot(df$DateTime ,df$Sub_metering_1,xlab="", ylab="Energy sub metering", type="l")
lines(df$DateTime,df$Sub_metering_2,col="red")
lines(df$DateTime,df$Sub_metering_3,col="blue")
legend("topright", col=c("black","red","blue"), c("Sub_metering_1","Sub_metering_2", "Sub_metering_3"),lty=c(1,1,1,1)
       , bty="n", cex=.5)

#subplot 4
plot(df$DateTime ,df$Global_reactive_power,xlab="datetime",ylab="Global_reactive_power", type="l")

# store png 
dev.copy(png, file="plot4_enlarged.png", width=680, height=680)
#dev.copy(png, file="plot4.png", width=480, height=480)
dev.off()