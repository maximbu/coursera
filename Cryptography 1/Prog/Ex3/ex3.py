from Crypto.Hash import SHA256
import hashlib

def readFileAsBytes(path):
    chunks= []
    with open(path, 'rb', 0) as f:
        while True:
            chunk = f.read(1024)
            if not chunk:
                # EOF reached, end loop
                return chunks
            # chunk is up to 1000 characters long
            chunks.append(chunk)
            print len(chunks)


def geth0(path):
    data = readFileAsBytes(path)
    data.reverse()
    prevHash = ""
    cnt=0
    for block in data:
        h = SHA256.new()
        newBlock = block+prevHash
        h.update(newBlock)
        cnt = cnt + 1
        print cnt
        #if(cnt == len(data)):
        #    toAppend = h.hexdigest()
        #else:
        prevHash = h.digest()
        if(cnt < 10):
            print prevHash.encode('hex')
        #    print "last !!!"
    return prevHash.encode('hex')

def test2(path):
    h = ''
    blocks = []
    data = 'foo'

    with open(path, 'rb') as f:
        while data != '':
            data = f.read(1024)
            if data != '':
                blocks.insert(0, data)

    for data in blocks:
        h = hashlib.sha256(data + h).digest()

    return  h.encode('hex')
        
        

#print geth0("Data\Test1.mp4")
print geth0("Data\Out.mp4")
#print test2("Data\Test1.mp4")
print test2("Data\Out.mp4")
raw_input("Press enter to continue")
