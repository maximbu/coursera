import gmpy2

p = 13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171
g = 11717829880366207009516117596335367088558084999998952205599979459063929499736583746670572176471460312928594829675428279466566527115212748467589894601965568
h = 3239475104050450443565264378728065788649097520952449527834792452971981976143292558073856937958553180532878928001494706097394108577585732452307673444020333
middleSize = 2**20

def calculateLeft(x1):
    denominv =  gmpy2.f_mod(pow(g, x1,p),p)
    denom = gmpy2.invert(denominv, p)
    tval = gmpy2.mul(h, denom)
    return gmpy2.f_mod(tval, p)

def calculateRight(x0):
    power = pow(g,gmpy2.mul(middleSize , x0),p)
    return gmpy2.f_mod(power,p)


def findLog():
    leftSide = {}
    for x1 in range(0,middleSize):
        leftSide[calculateLeft(x1)] = x1
    for x0 in range(0,middleSize):
        num = calculateRight(x0)
        if num in leftSide:
            print "x0: ", x0
            print "x1: ", leftSide[num]
            print "x: ", gmpy2.f_mod(gmpy2.mul(x0*middleSize)+leftSide[num],p)
            break

findLog()
