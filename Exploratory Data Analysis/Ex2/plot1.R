NEI <- readRDS("summarySCC_PM25.rds")
SCC <- readRDS("Source_Classification_Code.rds")

# calculate total Emissions
total <- aggregate(Emissions ~ year, NEI, sum)
plot(total$year,total$Emissions, type="l", main = "Emission per year", col="red", xlab="years" ,ylab="Total PM2.5 emission")

# store png
dev.copy(png, file="plot1.png", width=480, height=480)
dev.off()