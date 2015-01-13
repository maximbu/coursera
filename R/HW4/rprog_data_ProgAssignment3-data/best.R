best <- function(state, outcome) {
## Read outcome data
## Check that state and outcome are valid
## Return hospital name in that state with lowest 30-day death
## rate
if (missing(outcome))
        stop("invalid outcome.")
if (missing(state))
        stop("invalid state.")
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
indexes_state <- which(input$State == state)
if(length(indexes_state) == 0)
{
	stop("invalid state.")
}
dataset_state <- input[indexes_state,]
t<-suppressWarnings(as.numeric(dataset_state[,rel_col]));
index_min <- which(t == min(t, na.rm = TRUE))
dataset_state[index_min,2]
}