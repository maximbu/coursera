# template for "Stopwatch: The Game"
import simplegui

# define global variables
time_passed = 0
num_of_stops = 0
times_stoped_on_round = 0
was_timer_running = False

# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    minutes = (t / 600)%10
    sec = (t/10) %60
    tens_of_sec = t % 10
    
    minutes_str = str(minutes)
    sec_str = str(sec)
    if(sec < 10):
       sec_str = "0"+sec_str
    tens_of_sec_str = str(tens_of_sec)
    
    return minutes_str+":"+sec_str+"."+tens_of_sec_str
    
# define event handlers for buttons; "Start", "Stop", "Reset"
def start():
    global was_timer_running
    timer.start()
    was_timer_running = True
    
def stop():
    global num_of_stops,times_stoped_on_round,was_timer_running
    timer.stop()
    if(was_timer_running):
        num_of_stops+=1
        was_timer_running = False
        if(time_passed%10==0):
            times_stoped_on_round+=1
    
def reset():
    global time_passed,num_of_stops,times_stoped_on_round,was_timer_running
    time_passed = 0 
    num_of_stops = 0
    times_stoped_on_round = 0
    was_timer_running = False
    timer.stop()  

def format_counters():
    return  str(times_stoped_on_round)+"/"+str(num_of_stops)

# define event handler for timer with 0.1 sec interval
def tick():
    global time_passed
    time_passed+=1

# define draw handler
def draw(canvas):
    canvas.draw_text(format(time_passed), [50,112], 48, "Red")
    canvas.draw_text(format_counters(), [220,30], 30, "White")

    
# create frame
frame = simplegui.create_frame("Stopwatch: The Game", 300, 200)
timer = simplegui.create_timer(100,tick)

# register event handlers
frame.set_draw_handler(draw)
frame.add_button("Start", start)
frame.add_button("Stop", stop)
frame.add_button("Reset", reset)

# start frame
frame.start()
#timer.start()

# Please remember to review the grading rubric
