NEI <- readRDS("summarySCC_PM25.rds")
SCC <- readRDS("Source_Classification_Code.rds")

# calculate total Emissions
baltimore <- subset(NEI, fips=='24510')
total <- aggregate(Emissions ~ year, baltimore, sum)
plot(total$year,total$Emissions, type="l", main = "Baltimore emission per year", col="red", xlab="years" ,ylab="Total PM2.5 emission")

# store png
dev.copy(png, file="plot2.png", width=480, height=480)
dev.off()