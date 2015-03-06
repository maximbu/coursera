library(ggplot2)
NEI <- readRDS("summarySCC_PM25.rds")
SCC <- readRDS("Source_Classification_Code.rds")

# calculate total Emissions
coal_scc <- SCC[grep("Coal", SCC$Short.Name),1]
coal_emissions <- NEI[(NEI$SCC %in% coal_scc), ]
total <- aggregate(Emissions ~ year, coal_emissions, sum)

plot(total, type = "l", ylab = "Total PM2.5 emission", 
     xlab = "Year", main = "Coal total PM2.5 emission")

# store png
dev.copy(png, file="plot4.png", width=480, height=480)
dev.off()