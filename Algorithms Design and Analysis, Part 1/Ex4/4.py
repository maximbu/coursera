import sys , threading

t = 0
s = None
    
def dfs(vertices,node,end_order,components):
    global s , t
    vertices[node][0] = True
    if(s not in components):
        components[s]= 0
    components[s] += 1
    for i in vertices[node][1:]:
        if(i in vertices):
            if vertices[i][0] == False:
                dfs(vertices,i,end_order,components)       
    #print vertices[node]
    t+=1
    #print "t=" , t
    end_order.append(node)

    
def dfs_iter(vertices,node,end_order,components):
    global s , t
    visited, stack = set(), [node]
    while stack:
        if(s not in components):
            components[s]= 0
        vertex = stack.pop()
        components[s] += 1
        if vertex in vertices and vertices[vertex][0] == False:
            vertices[vertex][0] = True
            stack.extend(vertices[vertex][1:])
        end_order.append(vertex)
        
    

def dfs_loop(vertices,order):
    global s
    new_order = []
    components = {}
    #print order
    for i in order:
        if(i in vertices):
            if(not vertices[i][0]):
                s = i
                if(s%10000 == 0):
                    print "s=" , s
                dfs(vertices,i,new_order,components)
        else:
           new_order.append(i)
           if(i not in components):
               components[i]= 0
           components[i] += 1
    return [new_order,components]
    

def scc(vertices,vertices_rev,h_v):
    order = range (1,h_v+1)
    print "before first dfs loop"
    [order,components] = dfs_loop(vertices_rev,order)
    print "after first dfs loop"
    #print [order,components]
    [order,components] = dfs_loop(vertices,order)
    print "after sec dfs loop"
    return sorted(components.values())

def main():
    path = "D:\Studies\Algo_new\Ex4\\SCC.txt"
    #path = "D:\Studies\Algo_new\Ex4\\test1.txt"
    f = open(path, 'r')
    vertices = {}
    vertices_rev = {}
    h_v = 0
    for line in f:
        input = line.split()
        first_v = int(input[0])
        sec_v = int(input[1])
        if(first_v not in vertices):
            vertices[first_v]= [False]
        vertices[first_v].append(sec_v)
        if(sec_v not in vertices_rev):
            vertices_rev[sec_v]= [False]
        vertices_rev[sec_v].append(first_v)
        if(h_v < sec_v):
            h_v = sec_v
        if(h_v < first_v):
            h_v = first_v
    print "done parsing"
    #print "vertices:",vertices
    #print "vertices_rev:",vertices_rev
    print scc(vertices ,vertices_rev,h_v)[-5:]


threading.stack_size(67108864) # 64MB stack
sys.setrecursionlimit(2 ** 20)  # approx 1 million recursions
thread = threading.Thread(target = main) # instantiate thread object
thread.start() # run program at target
