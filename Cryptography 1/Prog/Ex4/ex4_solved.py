import urllib2
import string

TARGET = 'http://crypto-class.appspot.com/po?er='
TARGET_CIPHERTEXT = 'f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4'
FAST_CHARLIST = ' etaonisrhldcupfmwybgvkqxjzETAONISRHLDCUPFMWYBGVKQXJZ,.!' #string.printable

#--------------------------------------------------------------
# padding oracle
#--------------------------------------------------------------
def checkprocess(counter,text):
    #    print counter, ''.join(text)
    return counter+1

def query(q):
    target = TARGET + urllib2.quote(q)    # Create query URL
    req = urllib2.Request(target)         # Send HTTP request to server
    try:
        f = urllib2.urlopen(req)          # Wait for response
    except urllib2.HTTPError, e:
        #print "We got: %d" % e.code       # Print response code
        if e.code == 404:
            return True # good padding
        return False # bad padding

def strxor(a, b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])

def decryptblock(prevblock,currentblock,pretext):
    pt0 = ['?' for i in range(16)]  # initial guess
    for i in range(15,-1,-1):       # loop 16 bytes, start from the last one
        pad_len=16-i
        counter = 0
        fullguess = pt0
        flag = False
        for guess in FAST_CHARLIST:
            fullguess[i] = guess
            counter=checkprocess(counter,fullguess)
            new_prev_block = strxor(strxor(prevblock,fullguess),chr(pad_len)*16)  # xor the previous block
            new_ct = ''.join(pretext)+new_prev_block+currentblock   # create the new cipher text
            if query(new_ct.encode('hex')):
                print 'found it: %s' % guess
                pt0[i] = guess
                flag = True
                break
        if flag == False:
            fullcharlist = [chr(j) for j in range(256)]
            for guess in fullcharlist:
                if guess in set(FAST_CHARLIST):
                    continue
                fullguess[i] = guess
                counter=checkprocess(counter,fullguess)
                new_prev_block = strxor(strxor(prevblock,fullguess),chr(pad_len)*16)
                new_ct = ''.join(pretext)+new_prev_block+currentblock
                if query(new_ct.encode('hex')):
                    print 'found it: %s' % guess
                    pt0[i] = guess
                    flag = True
                    break
        if flag == False:
            print "!Fail to decrypt!"
            break
    return ''.join(pt0);

def decrypt(ciphertext):
    blocks = []
    plaintext = []
    for i in range(0,len(ciphertext),16):
        blocks.append(ciphertext[i:i+16])
    n = len(blocks)
    # first block
    s=decryptblock(blocks[0],blocks[1],'')
    plaintext.append(s)
    # middle blocks
    for i in range(1,n-2):
        s=decryptblock(blocks[i],blocks[i+1],blocks[0:i])
        plaintext.append(s)
    # last block with padding
    s=decryptblock(blocks[n-2],blocks[n-1],blocks[:n-2])
    plaintext.append(s)
    print ''.join(plaintext)

if __name__ == "__main__":
    print decrypt(TARGET_CIPHERTEXT.decode('hex'))
