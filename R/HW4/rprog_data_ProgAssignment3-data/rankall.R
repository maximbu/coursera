rankall <- function(outcome, num = "best") {
## Read outcome data
## Check that state and outcome are valid
## For each state, find the hospital of the given rank
## Return a data frame with the hospital names and the
## (abbreviated) state name
if (missing(outcome))
        stop("invalid outcome.")
rel_col<-0
if(outcome == 'heart attack')
{
	rel_col <- 11
}
else if(outcome == 'heart failure')
{
	rel_col <- 17
} else if(outcome == 'pneumonia')
{
	rel_col <- 23
}
else
	stop("invalid outcome.")

input <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
u <- unique(input$State)
states <- u[order(u)]
hospitals <- character(length(states))
for (i in 1:length(states))
{
state<-states[i]
indexes_state <- which(input$State == state)
dataset_state <- input[indexes_state,]
dataset_state[,rel_col]<-suppressWarnings(as.numeric(dataset_state[,rel_col]));
if(num == 'best')
{
	index <- which(dataset_state[,rel_col] == min(dataset_state[,rel_col], na.rm = TRUE))
	out<-dataset_state[index,2]
}
else if(num == 'worst')
{
	index <- which(dataset_state[,rel_col] == max(dataset_state[,rel_col], na.rm = TRUE))
	out<-dataset_state[index,2]
}
else
{
	ord<-order(dataset_state[,rel_col],dataset_state[,2],na.last=NA)
	out <- dataset_state[ord,2][num]
}
hospitals[i]<-out
}
df <- data.frame(hospitals, states, stringsAsFactors=FALSE)
names(df) <- c("hospital","state")
df
}