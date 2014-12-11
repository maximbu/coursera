import urllib2
import sys
import itertools

TARGET = 'http://crypto-class.appspot.com/po?er='
TARGET_CIPHERTEXT = 'f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4'
FAST_CHARLIST = ' etaonisrhldcupfmwybgvkqxjzETAONISRHLDCUPFMWYBGVKQXJZ,.!' #string.printable
FASTEST_CHARLIST = ' eiaonsurhldctpfmwybgvkqxjzWMTSO' #string.printable


#--------------------------------------------------------------
# padding oracle
#--------------------------------------------------------------
class PaddingOracle(object):
    def query(self, q):
        target = TARGET + urllib2.quote(q)    # Create query URL
        req = urllib2.Request(target)         # Send HTTP request to server
        try:
            f = urllib2.urlopen(req)          # Wait for response
            print "All Good"
        except urllib2.HTTPError, e:          
            # print "We got: %d" % e.code       # Print response code
            if e.code == 404:
                return True # good padding
            return False # bad padding

def strxor(a, b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])


def decryptchar(fullguess,index,prevblock,currentblock,pretext):
    for guess in FASTEST_CHARLIST:
        po = PaddingOracle()
        pad_len=16-index
        fullguess[index] = guess
        new_prev_block = strxor(strxor(prevblock,fullguess),chr(pad_len)*16)  # xor the previous block
        new_ct = ''.join(pretext)+new_prev_block+currentblock   # create the new cipher text
        if po.query(new_ct.encode('hex')):
           print 'found it: %s' % guess
           return guess

def decryptblock(prevblock,currentblock,pretext):   
    pt0 = ['?' for i in range(16)]  # initial guess
    for i in range(15,-1,-1):       # loop 16 bytes, start from the last one       
        pt0[i] = decryptchar(pt0,i,prevblock,currentblock,pretext)
    return ''.join(pt0);

def getBlocks():
    ciphertext = TARGET_CIPHERTEXT.decode('hex')
    blocks = []
    for i in range(0,len(ciphertext),16):
        blocks.append(ciphertext[i:i+16])
    return blocks
    
def decrypt():
    blocks = getBlocks()
    n = len(blocks)
    plaintext = []
    for i in range(0,n-1):
        if(i==n-1):
            # last block with padding
            s=decryptblock(blocks[i-1],blocks[i],blocks[:i-1])
        else:
            s=decryptblock(blocks[i],blocks[i+1],blocks[0:i])
        plaintext.append(s)
    print ''.join(plaintext)

if __name__ == "__main__":
    po = PaddingOracle()
    #po.query(sys.argv[1])       # Issue HTTP query with the given argument    
    decrypt()
