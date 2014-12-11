# Mini-project #6 - Blackjack

import simplegui
import random

# load card sprite - 936x384 - source: jfitz.com
CARD_SIZE = (72, 96)
CARD_CENTER = (36, 48)
card_images = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/cards_jfitz.png")

CARD_BACK_SIZE = (72, 96)
CARD_BACK_CENTER = (36, 48)
card_back = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/card_jfitz_back.png")    

P_LOC = [200,400]
P_VAL_LOC = [250,550]
D_LOC = [200,200]
D_VAL_LOC = [250,150]
OUTCOME_LOC = [50,350]
SCORE_LOC = [250,50]

# initialize some useful global variables
in_play = False
outcome = ""
value_p = ""
value_d = ""
score = 0
desk = None
player = None
dealer = None

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}


# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def draw(self, canvas, pos):
        card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank), 
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, card_loc, CARD_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], CARD_SIZE)
        
# define hand class
class Hand:
    def __init__(self):
        self.cards = []	

    def __str__(self):
        ans = ""
        for i in range(len(self.cards)):
            ans += str(self.cards[i])+" "    	
        return "Hand contains " + ans + " Value " + str(self.get_value())

    def add_card(self, card):
        self.cards.append(card)	# add a card object to a hand

    def get_value(self):
        # count aces as 1, if the hand has an ace, then add 10 to hand value if it doesn't bust
        val = 0
        has_ace = False
        for i in range(len(self.cards)):
            card = self.cards[i]
            val+= VALUES[card.get_rank()]
            if(card.get_rank()=='A'):
                has_ace = True
        if has_ace and val<=11:
            val+=10
        return val
   
    def draw(self, canvas, pos):
        for i in range(len(self.cards)):
            card_pos = [pos[0]+i*(CARD_SIZE[0]+2),pos[1]]
            self.cards[i].draw(canvas,card_pos)
 
    
        
# define deck class 
class Deck:
    def __init__(self):
        self.shuffle()

    def shuffle(self):
        # shuffle the deck 
        self.cards = []
        for i in range(len(SUITS)):
            for j in range(len(RANKS)):
                self.cards.append(Card(SUITS[i],RANKS[j]))
        random.shuffle(self.cards)

    def deal_card(self):
        return self.cards.pop()
    
    def __str__(self):
        ans = ""
        for i in range(len(self.cards)):
            ans += str(self.cards[i])+" "    	
        return "Deck contains " + ans


#define event handlers for buttons
def deal():
    global outcome, in_play
    global desk ,player ,dealer , score

    if in_play:
        outcome = "You lost this round !"
        print "You lost this round due to pressing deal in a middle of the game"
        score -= 1
        in_play = False
    # your code goes here
    desk = Deck()
    player = Hand()
    dealer = Hand()
    player.add_card(desk.deal_card())
    dealer.add_card(desk.deal_card())
    player.add_card(desk.deal_card())
    dealer.add_card(desk.deal_card())
    
    #print "Player Hand :" , str(player)
    #print "Dealer Hand :" , str(dealer)
    outcome = "Hit or stand?"
    in_play = True

def hit():
    global outcome , in_play , score
    if not in_play:
        return
    # if the hand is in play, hit the player
    if player.get_value()<22:
        player.add_card(desk.deal_card())
    #print "Player Hand :" , str(player)
    if player.get_value()>21:
        outcome = "You have busted! New Deal?"
        in_play = False
        score-=1
    # if busted, assign a message to outcome, update in_play and score
       
def stand():
    global outcome , in_play , score
    in_play = False
    player_val = player.get_value()
    if player_val>21:
        outcome = "You have busted! New Deal?"
        score-=1
        return
    while dealer.get_value()<17:
        dealer.add_card(desk.deal_card())
        #print "Dealer Hand :" , str(dealer)
    
    dealer_val = dealer.get_value()
    if dealer_val >21:
        outcome = "You won! Dealer got busted ! New Deal?"
        score+=1
        return
    if player_val > dealer_val:
        outcome = "You won! You have :"+ str(player_val)+ " , dealer :"+str(dealer_val) + ". New Deal?"
        score+=1
    else:
        outcome = "You lost! You have :"+ str(player_val)+ " , dealer :"+str(dealer_val) + ". New Deal?"
        score-=1
    # if hand is in play, repeatedly hit dealer until his hand has value 17 or more

    # assign a message to outcome, update in_play and score

# draw handler    
def draw(canvas):
    global outcome , score
    # test to make sure that card.draw works, replace with your code below
    dealer.draw(canvas, D_LOC)
    player.draw(canvas, P_LOC)
    canvas.draw_text("Value:"+str(player.get_value()), P_VAL_LOC, 24, 'Blue')
    if in_play:
        canvas.draw_image(card_back, CARD_BACK_CENTER, CARD_BACK_SIZE, [D_LOC[0] + CARD_BACK_CENTER[0], D_LOC[1] + CARD_BACK_CENTER[1]], CARD_BACK_SIZE)
    else:
        canvas.draw_text("Value:"+str(dealer.get_value()), D_VAL_LOC, 24, 'Blue')
    
    canvas.draw_text(outcome, OUTCOME_LOC, 26, 'Red')
    canvas.draw_text("Score : " + str(score), SCORE_LOC, 40, 'Black')
    


# initialization frame
frame = simplegui.create_frame("Blackjack", 600, 600)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit",  hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)


# get things rolling
deal()
frame.start()


# remember to review the gradic rubric