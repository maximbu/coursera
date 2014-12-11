
cnt_cmp = 0


def quicksort(lst,type): 
    if len(lst) <= 1:
        return lst
    pivot = choose_pivot(lst,type)
    #print pivot
    p_val = lst[pivot]
    smaller , bigger = partition(lst,pivot)
    #print "pivot :", p_val ," list:" , lst , " smaller :" , smaller , " bigger :" , bigger
    sm_sort = quicksort(smaller,type)
    b_sort = quicksort(bigger,type)
    pivots = [x for x in lst if x == lst[pivot]]
    return sm_sort+[p_val]+b_sort

def choose_pivot(lst,type):
	if type == 1:
		return 0
	if type == 2:
		return -1
	if type == 3:
		a = lst[0]
		b = lst[-1]
		c_ind = (len(lst)+1)/2 - 1
		#print c_ind , len(lst)
		c = lst[c_ind]
		#print a , b , c
		if(a >= b and b >= c) :
			return -1
		if(a >= c and c >= b) :
			return c_ind
		if(b >= a and a >= c) :
			return 0
		if(b >= c and c >= a) :
			return c_ind
		if(c >= a and a >= b) :
			return 0
		if(c >= b and b >= a) :
			return -1
		

	
def partition(lst,pivot):
    global cnt_cmp
    cnt_cmp += len(lst)-1
    i = 1
    j = 1
    #print "pivot index:" , pivot , "val :" , lst[pivot] , "in" , lst
    p_val = lst[pivot]
    lst[pivot],lst[0] = lst[0],lst[pivot]
    while j < len(lst):
        if (lst[j] < p_val):
            lst[i],lst[j] = lst[j],lst[i]
            i += 1
        j+= 1
    lst[0],lst[i-1] = lst[i-1],lst[0] 
    return lst[:i-1], lst[i:]

path = "D:\Studies\Algo_new\Ex2\\QuickSort.txt"
f = open(path, 'r')
input = []
for line in f:
    input.append(int(line))
#print input
cnt_cmp = 0
quicksort(input,1)
print cnt_cmp
cnt_cmp = 0
quicksort(input,2)
print cnt_cmp
cnt_cmp = 0
quicksort(input,3)
print cnt_cmp
