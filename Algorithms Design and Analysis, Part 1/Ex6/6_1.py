

#path = "D:\Studies\Algo_new\Ex6\\test_1.txt"
path = "D:\Studies\Algo_new\Ex6\\algo1_programming_prob_2sum.txt"
f = open(path, 'r')
pos_input = set()
neg_input = set()
nums_found = set()
for line in f:
    num = int(line)
    nums_to_check = range(-10000,10001)
    gen = (t for t in nums_to_check if t not in nums_found)
    if(num>0) :
        if(num not in pos_input):
            for t in gen:
             if(t-num) in neg_input:
                nums_found.add(t)
            pos_input.add(num)
    else:
      if(num not in neg_input):
            for t in gen:
             if(t-num) in pos_input:
                nums_found.add(t)
            neg_input.add(num)
    size = len(pos_input) + len(neg_input)
    if(size% 1000==0):
        print size, len(nums_found)
print len(nums_found)


