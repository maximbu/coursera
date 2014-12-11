import Queue as queue # replace with 'import queue' if using Python 3

class MedianHeap:
    def __init__(self, numbers = None):
        self.median = None
        self.left = queue.PriorityQueue() # max-heap
        self.right = queue.PriorityQueue() # min-heap
        self.diff = 0 # difference in size between left and right
        
        if numbers:
            for n in numbers:
                self.put(n)

    def top(self):
        return self.median

    def put(self, n):
        if not self.median:
            self.median = n
        elif n <= self.median:
            self.left.put(-n)
            self.diff += 1
        else:
            self.right.put(n)
            self.diff -= 1

        if self.diff > 1:
            self.right.put(self.median)
            self.median = -self.left.get()
            self.diff = 0
        elif self.diff < -1:
            self.left.put(-self.median)
            self.median = self.right.get()
            self.diff = 0

    def get(self):
        median = self.median

        if self.diff > 0:
            self.median = -self.left.get()
            self.diff -= 1
        elif self.diff < 0:
            self.median = self.right.get()
            self.diff += 1
        elif not self.left.empty():
            self.median = -self.left.get()
            self.diff -= 1
        elif not self.right.empty():
            self.median = self.right.get()
            self.diff += 1
        else: # median was the only element
            self.median = None
        
        return median

    def p(self):
        print "diff",self.diff
        print "min",self.left
        print "max",self.right

path = "D:\Studies\Algo_new\Ex6\\test_3.txt"
    
if __name__ == '__main__':
    import sys
    f = open(path, 'r')
    s = 0
    m = MedianHeap()
    for line in f:
        m.put(int(line))
        m.p()
        s+= m.top()
    print "Median is ", m.get()
