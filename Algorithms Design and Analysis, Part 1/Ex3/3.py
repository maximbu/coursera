import random
import copy

def change_edges(edges_to_update,rem_vert,merged_vert,edges):
    updated = []
    for edge_ind in edges_to_update:
        if edge_ind in edges:
            edge = edges[edge_ind]
            if edge[0] == rem_vert :
                edge[0] = merged_vert
            if edge[1] == rem_vert :
                edge[1] = merged_vert            
            if edge[0] == edge[1]:
                #print "deleting edge:",edge
                del edges[edge_ind]
            else:
                updated.append(edge_ind)
    return updated


def rand_mincut(vertices,edges):
    num_of_vert = len(vertices)
    while num_of_vert>2:
        edge = random.choice(edges.keys())
        #print "edge:",edge
        vert = edges.pop(edge)
        rem_vert = vert[1]
        #print "rem:",rem_vert
        merged_vert = vert[0]
        #print "merged",merged_vert
        edges_to_update = vertices.pop(rem_vert)
        #print "edges_to_update" , edges_to_update
        updated = change_edges(edges_to_update,rem_vert,merged_vert,edges)
        tmp_arr = vertices[merged_vert]
        tmp_arr.extend(updated)
        vertices[merged_vert]= tmp_arr
        num_of_vert-=1
        #print "vertices:",vertices
        #print "edges:",edges
    #print "final vertices:",vertices
    #print "final edges:",edges
    return len(edges)


def mincut(vertices,edges):
    min_val = 99999
    for i in range(1000):
        print "*****new run********" , i , "min_val" , min_val
        val = rand_mincut(copy.deepcopy(vertices),copy.deepcopy(edges))
        if min_val > val:
            min_val = val
            #print "min_val updted to:",min_val
    return min_val

    

path = "D:\Studies\Algo_new\Ex3\\kargerMinCut.txt"
#path = "D:\Studies\Algo_new\Ex3\\test5.txt"
f = open(path, 'r')
vertices = {}
edges = {}
edge_num = 0
for line in f:
    input = line.split()
    first_v = int(input[0])
    if(first_v not in vertices):
        vertices[first_v]= []
    i = 1
    while(i<len(input)):
        secound_v = int(input[i])
        if(first_v<secound_v):
            edges[edge_num]=[first_v,secound_v]
            vertices[first_v].append(edge_num)
            if(secound_v not in vertices):
                vertices[secound_v]= []
            vertices[secound_v].append(edge_num)
            edge_num+=1
        i+=1
#print "vertices:",vertices
#print "edges:",edges
print mincut(vertices,edges)

