# If required packages are not present then install them.
if("dplyr" %in% rownames(installed.packages()) == FALSE) {install.packages("dplyr")};library(dplyr)
if("tidyr" %in% rownames(installed.packages()) == FALSE) {install.packages("tidyr")};library(tidyr)

# If the dataset is not present in the current working directory stop

folder <- "UCI HAR Dataset"
stopifnot(file.exists(folder))

# Read the train data
train_x <- read.table(paste(folder,"train","X_train.txt",sep = "\\"))
train_y  <- read.table(paste(folder,"train","y_train.txt",sep = "\\"))
names(train_y)="activity"
train_subject  <- read.table(paste(folder,"train","subject_train.txt",sep = "\\"))
names(train_subject)="subject"
# Build full train data
train_data <- cbind(train_x, train_subject, train_y)

# Read the test data
test_x <- read.table(paste(folder,"test","X_test.txt",sep = "\\"))
test_y  <- read.table(paste(folder,"test","y_test.txt",sep = "\\"))
names(test_y)="activity"
test_subject <- read.table(paste(folder,"test","subject_test.txt",sep = "\\"))
names(test_subject)="subject"
# Build full test data
test_data <- cbind(test_x, test_subject, test_y)

# Combine all the data
data <- rbind(train_data, test_data)

# Read feature names
feature_names <- read.table(paste(folder,"features.txt",sep = "\\"))$V2
# Get only the relevant features
mean_and_std_features <- grep("mean\\(\\)|std\\(\\)", feature_names)
filter_features <- c(mean_and_std_features,length(feature_names)+1,length(feature_names)+2)
filtered_data = data[, filter_features]
# Build appropriate names vector
filtered_names <- feature_names[mean_and_std_features]
filtered_names <- gsub("mean", "Mean", filtered_names)
filtered_names <- gsub("std", "Std", filtered_names)
filtered_names <- gsub("Acc", "Acceleration", filtered_names)
filtered_names <- gsub("Mag", "Magnitude", filtered_names)
filtered_names <- gsub("BodyBody", "Body", filtered_names)
filtered_names <- gsub("-", "", filtered_names)
filtered_names <- gsub("\\(\\)", "", filtered_names)
filtered_names <- gsub("^t(.*)$", "Time\\1", filtered_names)
filtered_names <- gsub("^f(.*)$", "Frequency\\1", filtered_names)
# Assign names along with subject and activity
names(filtered_data) <- c(filtered_names,"subject","activity")

# Read activities and rename them approp.
activities <- read.table(paste(folder,"activity_labels.txt",sep = "\\"))
activities[, 2] <- tolower(activities[, 2])
activities[, 2] <- gsub("_(.)", "\\U\\1", activities[, 2],perl=TRUE)
# Reassign activity name instead of identifier
filtered_data$activity <- activities[filtered_data$activity, 2]
# Store currcnt dataset
write.table(file="full_tidy_dataset.txt")

# Use dplyr and tidyr to group by subject and activity , calculate mean , gather columns and store the final result
tbl_df(filtered_data) %>%
  group_by(subject, activity) %>%
  summarise_each(funs(mean)) %>%
  gather(measurement, mean, -activity, -subject)  %>%
  write.table(file="means_by_activity_and_subject.txt", row.name=FALSE)