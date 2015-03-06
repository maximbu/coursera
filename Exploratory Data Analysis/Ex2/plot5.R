library(ggplot2)
NEI <- readRDS("summarySCC_PM25.rds")
SCC <- readRDS("Source_Classification_Code.rds")

# calculate total Emissions
motor_scc <- SCC[grep("Vehicles", SCC$Short.Name),1]
baltimore_motor <- NEI[(NEI$SCC %in% motor_scc) & NEI$fips=='24510', ]
total <- aggregate(Emissions ~ year, baltimore_motor, sum)

plot(total, type = "l", ylab = "Total PM2.5 emission", 
     xlab = "Year", main = "Baltimore motor vehicles total PM2.5 emission")

# store png
dev.copy(png, file="plot5.png", width=480, height=480)
dev.off()