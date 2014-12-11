# Rock-paper-scissors-lizard-Spock template
import random

# The key idea of this program is to equate the strings
# "rock", "paper", "scissors", "lizard", "Spock" to numbers
# as follows:
#
# 0 - rock
# 1 - Spock
# 2 - paper
# 3 - lizard
# 4 - scissors

# helper functions

def name_to_number(name):
    # convert name to number using if/elif/else
    # don't forget to return the result!
    number = -1
    nameToLower = name.lower()
    if(nameToLower=="rock"):
        number = 0
    elif(nameToLower=="spock"):
        number = 1
    elif(nameToLower=="paper"):
        number = 2
    elif(nameToLower=="lizard"):
        number = 3
    elif(nameToLower=="scissors"):
        number = 4
    return number    
    


def number_to_name(number):
    # convert number to a name using if/elif/else
    # don't forget to return the result!
    name = "Unknown option was used"
    if(number==0):
        name = "rock"
    elif(number==1):
        name = "Spock"
    elif(number==2):
        name = "paper"
    elif(number==3):
        name = "lizard"
    elif(number==4):
        name = "scissors"
    return name
    
    

def rpsls(player_choice): 
    # delete the follwing pass statement and fill in your code below
    
    # print a blank line to separate consecutive games
    print ""

    # print out the message for the player's choice
    print "Player chooses " + player_choice

    # convert the player's choice to player_number using the function name_to_number()
    player_number = name_to_number(player_choice)
    if(player_number == -1) :
        print "Wrong option was selected by player , please try again"
        return
    
    # compute random guess for comp_number using random.randrange()
    comp_number = random.randrange(0,4)

    # convert comp_number to comp_choice using the function number_to_name()
    comp_choice = number_to_name(comp_number)
    
    # print out the message for computer's choice
    print "Computer  chooses " + comp_choice
    
    # compute difference of comp_number and player_number modulo five
    # let's keep it possitive :)
    difference = (comp_number - player_number + 5) % 5

    # use if/elif/else to determine winner, print winner message
    if difference == 0 :
        print "Player and computer tie!"
        return
    
    if difference < 3 :
        print "Computer wins!"
        return
    
    print "Player wins!"
        

    
# test your code - THESE CALLS MUST BE PRESENT IN YOUR SUBMITTED CODE
rpsls("rock")
rpsls("Spock")
rpsls("paper")
rpsls("lizard")
rpsls("scissors")

# always remember to check your completed program against the grading rubric


