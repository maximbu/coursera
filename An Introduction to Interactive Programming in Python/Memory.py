# implementation of card game - Memory

import simplegui
import random

RANGE = 8
desk = []
exposed = []
to_test = []
state = 0
turns = 0

# helper function to initialize globals
def new_game():
    global desk , exposed , turns, state , to_test
    desk = range(RANGE)+range(RANGE)
    random.shuffle(desk)
    exposed = [False for card in desk]
    label.set_text("Turns = 0")
    to_test = []
    state = 0
    turns = 0


def end_game():
    for card_exposed in exposed:
        if card_exposed == False :
            return False
    return True
     
# define event handlers
def mouseclick(pos):
    global state,to_test,turns
    # add game state logic here          
    card_ind = pos[0]/50
    if(exposed[card_ind]):
        return
    exposed[card_ind] = True
    if end_game() == True:
        turns+=1
        label.set_text("Turns = " +str(turns) )
        print "Game Ended ... Well Done !!!"
        return
        
    if state == 0:
        to_test = [card_ind]
        state = 1
    elif state == 1:        
        to_test.append(card_ind)
        state = 2
        turns+=1
        label.set_text("Turns = " +str(turns) )
    else:
        if desk[to_test[0]] == desk[to_test[1]] :
            print "match !"
        else :
            exposed[to_test[0]] = False
            exposed[to_test[1]] = False
        to_test = [card_ind]
        state = 1
    
                        
# cards are logically 50x100 pixels in size    
def draw(canvas):
    loc = 25
    for ind in range(RANGE*2):
        if exposed[ind]:
            canvas.draw_text(str(desk[ind]), (loc-5, 60), 30, 'Red')
        else:
            canvas.draw_polygon([[loc-25, 0], [loc+25, 0], [loc+25, 100], [loc-25, 100]], 8, 'Black','Green')
        loc += 50
    pass


# create frame and add a button and labels
frame = simplegui.create_frame("Memory", 800, 100)
frame.add_button("Reset", new_game)
label = frame.add_label("Turns = 0")

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
new_game()
frame.start()


# Always remember to review the grading rubric