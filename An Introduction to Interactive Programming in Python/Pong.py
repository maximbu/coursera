# Implementation of classic arcade game Pong

import simplegui
import random

# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600
HEIGHT = 400       
BALL_RADIUS = 20
PAD_WIDTH = 8
PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2
HALF_PAD_HEIGHT = PAD_HEIGHT / 2
LEFT = False
RIGHT = True

ball_pos = []
ball_vel = []
score1 = 0
score2 = 0
paddle1_pos = HEIGHT/2
paddle2_pos = HEIGHT/2
paddle1_vel = 0
paddle2_vel = 0

# initialize ball_pos and ball_vel for new bal in middle of table
# if direction is RIGHT, the ball's velocity is upper right, else upper left
def spawn_ball(direction):
    global ball_pos, ball_vel # these are vectors stored as lists
    ball_pos = [WIDTH / 2, HEIGHT / 2]
    x_vel=random.randrange(120, 240)/60.0
    y_vel=random.randrange(60, 180)/60.0
    if(direction == LEFT):
        x_vel *= -1
    ball_vel = [x_vel,-y_vel]

# define event handlers
def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are numbers
    global score1, score2  # these are ints
    score1 = 0
    score2 = 0
    spawn_ball(RIGHT)

def restart():
    new_game() 
    
def draw(canvas):
    global score1, score2, paddle1_pos, paddle2_pos, ball_pos, ball_vel
        
    # draw mid line and gutters
    canvas.draw_line([WIDTH / 2, 0],[WIDTH / 2, HEIGHT], 1, "White")
    canvas.draw_line([PAD_WIDTH, 0],[PAD_WIDTH, HEIGHT], 1, "White")
    canvas.draw_line([WIDTH - PAD_WIDTH, 0],[WIDTH - PAD_WIDTH, HEIGHT], 1, "White")
        
    # update ball
    ball_pos[0]+=ball_vel[0]
    ball_pos[1]+=ball_vel[1]
    
    #collisions handeling
    ball_y  = ball_pos[1]
    if(ball_y <= BALL_RADIUS or ball_y >= HEIGHT-1-BALL_RADIUS):
        ball_vel[1]*=-1   
    
    # update paddle's vertical position, keep paddle on the screen
    new_paddle1_pos = paddle1_pos + paddle1_vel
    if(new_paddle1_pos > HALF_PAD_HEIGHT and new_paddle1_pos<HEIGHT-1-HALF_PAD_HEIGHT):
        paddle1_pos = new_paddle1_pos
        
    new_paddle2_pos = paddle2_pos + paddle2_vel
    if(new_paddle2_pos > HALF_PAD_HEIGHT and new_paddle2_pos<HEIGHT-1-HALF_PAD_HEIGHT):
        paddle2_pos = new_paddle2_pos
   
    #check gutters collision (win)
    if(ball_pos[0]<=BALL_RADIUS+PAD_WIDTH):
        #if paddle was hit
        if(ball_pos[1]<=paddle1_pos+HALF_PAD_WIDTH+BALL_RADIUS and ball_pos[1]>=paddle1_pos-HALF_PAD_WIDTH-BALL_RADIUS):
            ball_vel[0]*=-1.1   #increase velocity and reflect
        else:
            score2+=1
            spawn_ball(RIGHT)
            return
        
    if(ball_pos[0]>=WIDTH-1-BALL_RADIUS-PAD_WIDTH):
        #if paddle was hit
        if(ball_pos[1]<=paddle2_pos+HALF_PAD_WIDTH+BALL_RADIUS and ball_pos[1]>=paddle2_pos-HALF_PAD_WIDTH-BALL_RADIUS):
            ball_vel[0]*=-1.1    #increase velocity and reflect
        else:
            score1+=1
            spawn_ball(LEFT)
            return 
    
     # draw ball
    canvas.draw_circle(ball_pos, BALL_RADIUS, 2, "Red", "White")
    
    # draw paddles
    canvas.draw_line([HALF_PAD_WIDTH,paddle1_pos-HALF_PAD_HEIGHT],[HALF_PAD_WIDTH,paddle1_pos+HALF_PAD_HEIGHT], PAD_WIDTH, "White")
    canvas.draw_line([WIDTH-HALF_PAD_WIDTH,paddle2_pos-HALF_PAD_HEIGHT],[WIDTH-HALF_PAD_WIDTH,paddle2_pos+HALF_PAD_HEIGHT], PAD_WIDTH, "White")
    
    # draw scores
    canvas.draw_text(str(score1), [WIDTH/3,HEIGHT/3], 60, "Red")
    canvas.draw_text(str(score2), [WIDTH*2/3,HEIGHT/3], 60, "Red")
        
def keydown(key):
    global paddle1_vel, paddle2_vel
    vel = 4
    if key == simplegui.KEY_MAP["down"]:
        paddle2_vel+=vel
    elif key == simplegui.KEY_MAP["up"]:
        paddle2_vel-=vel
    elif key == simplegui.KEY_MAP["w"]:
        paddle1_vel-=vel
    elif key == simplegui.KEY_MAP["s"]:
        paddle1_vel+=vel
    
   
def keyup(key):
    global paddle1_vel, paddle2_vel
    vel = 4
    if key == simplegui.KEY_MAP["up"]:
        paddle2_vel+=vel
    elif key == simplegui.KEY_MAP["down"]:
        paddle2_vel-=vel
    elif key == simplegui.KEY_MAP["s"]:
        paddle1_vel-=vel
    elif key == simplegui.KEY_MAP["w"]:
        paddle1_vel+=vel

# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.add_button("Restart", restart)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)


# start frame
new_game()
frame.start()
