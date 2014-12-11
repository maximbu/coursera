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
   
print 'attack at dawn'
print '6c73d5240a948c86981bc294814d'.decode('hex')
print strxor(strxor('attack at dawn','6c73d5240a948c86981bc294814d'.decode('hex')),'attack at dusk').encode('hex')
raw_input("Press Enter to continue...")
