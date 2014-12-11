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
  
a1 = '290b6e3a39155d6f'.decode('hex')
b1 = 'd6f491c5b645c008'.decode('hex')
print  strxor(a1,b1).encode('hex')
a2='7b50baab07640c3d'.decode('hex')
b2= 'ac343a22cea46d60'.decode('hex')
print  strxor(a2,b2).encode('hex')
a3='7c2822ebfdc48bfb'.decode('hex')
b3= '325032a9c5e2364b'.decode('hex')
print  strxor(a3,b3).encode('hex')
a4='4af532671351e2e1'.decode('hex')
b4= '87a40cfa8dd39154'.decode('hex')
print  strxor(a4,b4).encode('hex')
raw_input("Press enter to continue")
