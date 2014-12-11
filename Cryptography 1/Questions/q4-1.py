def strxor(a, b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])
      
def encrypt(key, msg):
    c = strxor(key, msg)
    print
    print c.encode('hex')
    return c
  
a1 = 'Pay Bob 100$'
b1 = 'Pay Bob 500$'
c1 = '20814804c1767293b99f1d9cab3bc3e7'.decode('hex')
print  a1
print a1.encode('hex')
print 'Pay Bob 100$ len='+str(len(a1.encode('hex')))
print  b1
print b1.encode('hex')
print  c1
print c1.encode('hex')
diff = strxor(a1,b1)
print diff
new = strxor(c1,diff)
print new.encode('hex')
print '20814804c1767293b99f1d9cab3bc3e7'
#print 'encryption len='+str(len(c1.encode('hex')))
raw_input("Press enter to continue")
