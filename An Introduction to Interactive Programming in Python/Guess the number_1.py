# template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console
import simplegui
import random
import math

secret_number = -1
high_range = 100
guesses_left = 0

# helper function to start and restart the game
def new_game():
    # initialize global variables used in your code here
    global secret_number, guesses_left
    secret_number = random.randrange(0,high_range)
    guesses_left = int(math.ceil(math.log(high_range,2)))
    print "Starting a new game in the range [0-"+str(high_range)+")"
    print "You have "+str(guesses_left)+" guesses left"

def start_game_with_range(range):
    global high_range 
    high_range = range   
    new_game()   

# define event handlers for control panel
def range100():
    # button that changes the range to [0,100) and starts a new game 
    start_game_with_range(100)

def range1000():
    # button that changes the range to [0,1000) and starts a new game     
    start_game_with_range(1000)
    
def input_guess(guess):
    # main game logic goes here	
    global guesses_left
    int_guess = int(guess)
    if(int_guess == secret_number):
        print "Correct!"
        new_game()
        return
    if (int_guess > secret_number):
        print "Lower"
    else:
        print "Higher"
    print "Guess was" , int_guess
    guesses_left -= 1
    if(guesses_left == 0):
        print "You used all your guesses and lost , better luck next time"
        new_game()
    else:
        print "You have "+str(guesses_left)+" guesses left"
    


    
# create frame
frame = simplegui.create_frame("Guess the number",200,200)

# register event handlers for control elements and start frame
frame.add_button("Range: 0 - 100",range100,200)
frame.add_button("Range: 0 - 1000",range1000,200)
frame.add_input("Enter guess:",input_guess,100)
frame.start()

# call new_game 
new_game()


# always remember to check your completed program against the grading rubric
