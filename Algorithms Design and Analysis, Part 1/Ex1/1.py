def mergesort(lst):
    '''Recursively divides list in halves to be sorted'''
    if len(lst) == 1:
        return lst, 0
    middle = len(lst)/2
    left, s1 = mergesort(lst[:middle])
    right, s2 = mergesort(lst[middle:])
    sortedlist, s3 = merge(left, right)
    return sortedlist, (s1+s2+s3)

def merge(left, right):
    '''Subroutine of mergesort to sort split lists.  Also returns number
    of split inversions (i.e., each occurence of a number from the sorted second
    half of the list appearing before a number from the sorted first half)'''
    i, j = 0, 0
    splits = 0
    result = []
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
            splits += len(left[i:])
    result += left[i:]
    result += right[j:]
    return result, splits

path = "D:\Studies\Algo_new\Ex1\IntegerArray.txt"
#path2 = "D:\Studies\Algo_new\Ex1\Test.txt"
f = open(path, 'r')
input = []
for line in f:
    input.append(int(line))
#print input
r ,s = mergesort(input)
print s