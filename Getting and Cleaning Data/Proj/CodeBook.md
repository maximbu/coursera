CodeBook for "Getting and Cleaning Data" Project
=================================

## Description

This CodeBook contains information about creation of means_by_activity_and_subject.txt
which is an output of tiding data of UCI HAR Dataset as described in Readme.md

## Creation of means_by_activity_and_subject.txt

Creation of the dataset was done as follows :
- Reading all the train data : X_train.txt , y_train.txt , subject_train.txt into one dataset
- Reading all the train data : X_test.txt , y_test.txt , subject_test.txt into one dataset
- Merging of the two datasets
- Replacing cloumn names of X_*.txt with appropriate names from features.txt , y_*.txt column to 
'activity' and subject_*.txt column to 'subject'
- Filtering all columns from x_*.txt that don't end with 'mean()' or 'std()' 
- Renaming in a more human readable fashion :
	- mean -> Mean
	- std -> Std
	- Acc -> Acceleration
	- Mag -> Magnitude
	- BodyBody -> Body
	- deleting "-"
	- deleting "()"
	- renaming t* to Time*
	- renaming f* to Frequency*
- Replacing the activity column enumeration field with appropriate activity name from activity_labels.txt
- Removing the "_" in activity names , capitalize the first letter after the original "_" 
and lowercase all other letters in activity name : WALKING_UPSTAIRS  -> walkingUpstairs .
- storing the current result into full_tidy_dataset.txt
- grouping by subject and activity 
- calculate mean for each group
- gather appropriate columns
- store final result as means_by_activity_and_subject.txt
	
## Content of means_by_activity_and_subject.txt
The means_by_activity_and_subject.txt dataset contains the following information :
For each of 30 subjects and each of the following activities : 
[1] "walking"          
[2] "walkingUpstairs"  
[3] "walkingDownstairs"
[4] "sitting"          
[5] "standing"         
[6] "laying" 
It contains the mean values for each of the following:
 [1] "TimeBodyAccelerationMeanX"                 
 [2] "TimeBodyAccelerationMeanY"                 
 [3] "TimeBodyAccelerationMeanZ"                 
 [4] "TimeBodyAccelerationStdX"                  
 [5] "TimeBodyAccelerationStdY"                  
 [6] "TimeBodyAccelerationStdZ"                  
 [7] "TimeGravityAccelerationMeanX"              
 [8] "TimeGravityAccelerationMeanY"              
 [9] "TimeGravityAccelerationMeanZ"              
[10] "TimeGravityAccelerationStdX"               
[11] "TimeGravityAccelerationStdY"               
[12] "TimeGravityAccelerationStdZ"               
[13] "TimeBodyAccelerationJerkMeanX"             
[14] "TimeBodyAccelerationJerkMeanY"             
[15] "TimeBodyAccelerationJerkMeanZ"             
[16] "TimeBodyAccelerationJerkStdX"              
[17] "TimeBodyAccelerationJerkStdY"              
[18] "TimeBodyAccelerationJerkStdZ"              
[19] "TimeBodyGyroMeanX"                         
[20] "TimeBodyGyroMeanY"                         
[21] "TimeBodyGyroMeanZ"                         
[22] "TimeBodyGyroStdX"                          
[23] "TimeBodyGyroStdY"                          
[24] "TimeBodyGyroStdZ"                          
[25] "TimeBodyGyroJerkMeanX"                     
[26] "TimeBodyGyroJerkMeanY"                     
[27] "TimeBodyGyroJerkMeanZ"                     
[28] "TimeBodyGyroJerkStdX"                      
[29] "TimeBodyGyroJerkStdY"                      
[30] "TimeBodyGyroJerkStdZ"                      
[31] "TimeBodyAccelerationMagnitudeMean"         
[32] "TimeBodyAccelerationMagnitudeStd"          
[33] "TimeGravityAccelerationMagnitudeMean"      
[34] "TimeGravityAccelerationMagnitudeStd"       
[35] "TimeBodyAccelerationJerkMagnitudeMean"     
[36] "TimeBodyAccelerationJerkMagnitudeStd"      
[37] "TimeBodyGyroMagnitudeMean"                 
[38] "TimeBodyGyroMagnitudeStd"                  
[39] "TimeBodyGyroJerkMagnitudeMean"             
[40] "TimeBodyGyroJerkMagnitudeStd"              
[41] "FrequencyBodyAccelerationMeanX"            
[42] "FrequencyBodyAccelerationMeanY"            
[43] "FrequencyBodyAccelerationMeanZ"            
[44] "FrequencyBodyAccelerationStdX"             
[45] "FrequencyBodyAccelerationStdY"             
[46] "FrequencyBodyAccelerationStdZ"             
[47] "FrequencyBodyAccelerationJerkMeanX"        
[48] "FrequencyBodyAccelerationJerkMeanY"        
[49] "FrequencyBodyAccelerationJerkMeanZ"        
[50] "FrequencyBodyAccelerationJerkStdX"         
[51] "FrequencyBodyAccelerationJerkStdY"         
[52] "FrequencyBodyAccelerationJerkStdZ"         
[53] "FrequencyBodyGyroMeanX"                    
[54] "FrequencyBodyGyroMeanY"                    
[55] "FrequencyBodyGyroMeanZ"                    
[56] "FrequencyBodyGyroStdX"                     
[57] "FrequencyBodyGyroStdY"                     
[58] "FrequencyBodyGyroStdZ"                     
[59] "FrequencyBodyAccelerationMagnitudeMean"    
[60] "FrequencyBodyAccelerationMagnitudeStd"     
[61] "FrequencyBodyAccelerationJerkMagnitudeMean"
[62] "FrequencyBodyAccelerationJerkMagnitudeStd" 
[63] "FrequencyBodyGyroMagnitudeMean"            
[64] "FrequencyBodyGyroMagnitudeStd"             
[65] "FrequencyBodyGyroJerkMagnitudeMean"        
[66] "FrequencyBodyGyroJerkMagnitudeStd" 
