library(ggplot2)
NEI <- readRDS("summarySCC_PM25.rds")
SCC <- readRDS("Source_Classification_Code.rds")

# calculate total Emissions
baltimore <- subset(NEI, fips=='24510')
total <- aggregate(Emissions ~ year+type, baltimore, sum)

qplot(year, Emissions, data = total, color = type, 
      geom = "line", ylab = "Total PM2.5 emission", 
      xlab = "Year", main = "Baltimore total PM2.5 emission")

# store png
dev.copy(png, file="plot3.png", width=480, height=480)
dev.off()