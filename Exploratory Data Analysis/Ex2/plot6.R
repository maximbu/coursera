library(ggplot2)
NEI <- readRDS("summarySCC_PM25.rds")
SCC <- readRDS("Source_Classification_Code.rds")

# calculate total Emissions
motor_scc <- SCC[grep("Vehicles", SCC$Short.Name),1]
baltimore_motor <- NEI[(NEI$SCC %in% motor_scc) & NEI$fips=='24510', ]
la_motor <- NEI[(NEI$SCC %in% motor_scc) & NEI$fips=='06037', ]
total_baltimore <- aggregate(Emissions ~ year, baltimore_motor, sum)
total_baltimore$city <- 'Baltimore'
total_la <- aggregate(Emissions ~ year, la_motor, sum)
total_la$city <- 'LA'
total<- rbind(total_baltimore, total_la)

qplot(year, Emissions, data = total, color = city, 
      geom = "line", ylab = "Total PM2.5 emission", 
      xlab = "Year", main = "Motor total PM2.5 emission")
# store png
dev.copy(png, file="plot6.png", width=480, height=480)
dev.off()