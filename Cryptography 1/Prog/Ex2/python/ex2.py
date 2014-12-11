from Crypto.Cipher import AES
import binascii

def strxor(a, b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])
      
def xorhex(a, b):
    return strxor(a.decode('hex'), b.decode('hex'))

def decryptCBC(key,message):
    blocks = split_hex(message).split(" ")
    message=""
    for (i, item) in enumerate(blocks):
        if(i==0):
            IV = item
        else:
            cipher = AES.new(key.decode('hex'), AES.MODE_ECB)
            decryptedItem = cipher.decrypt(item)
            prevBlock = blocks[i-1]
            message = message + strxor(prevBlock,decryptedItem)
    return message


def decryptCTR(key,message):
    IV = message[0:16]
    message = message[16:]
    print "IV is:" + IV
    blocks = split_hex(message).split(" ")
    retVal=""
    for (i, item) in enumerate(blocks):
            cipher = AES.new(key.decode('hex'), AES.MODE_ECB)
            decVal = cipher.decrypt(IV)
            IV = addOne(IV)
            print "IV+1 is :" + IV
            retVal = retVal + strxor(item,decVal)
    return retVal


def addOne(IV):
    temp = hex(int(binascii.hexlify(IV), 16)+1)  #Change the IV back to a hex string, convert the hex string to an int and add 1, convert the int to a hex value
    tempStr = binascii.unhexlify(temp.encode('hex')) #Encode this value as a hex string, however it is in the format '0xdeadbeefL'
    return binascii.unhexlify(tempStr[2:-1])  #Slice off the '0x' and the 'L' so it is in the same format as the original IV
    
    


def split_hex(value):
    nonhex = value.decode('hex')
    return " ".join(nonhex[i:i+16] for i in range(0, len(nonhex), 16))

print decryptCBC("140b41b22a29beb4061bda66b6747e14" ,"4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81")
print decryptCBC("140b41b22a29beb4061bda66b6747e14" ,"5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48\
e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253")
print decryptCTR("36f18357be4dbd77f050515c73fcf9f2","69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc3\
88d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329")
print decryptCTR("36f18357be4dbd77f050515c73fcf9f2","770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa\
0e311bde9d4e01726d3184c34451")
raw_input("Press enter to continue")
