## utility function to support matrix inverse with cache
## usage sample: 
## mat<-makeCacheMatrix(matrix(c(4,3,3,2),nrow=2,ncol=2))
## cacheSolve(mm)
## cacheSolve(mm)

## utility function for matrix creation

makeCacheMatrix <- function(x = matrix()) {
  inv <- NULL
  set <- function(y) {
    x <<- y
    inv <<- NULL
  }
  get <- function() x
  getinverse <- function() inv
  setinverse <- function(inv_mat) inv <<- inv_mat
  list(set = set, get = get,
       setinverse = setinverse,
       getinverse = getinverse)
}


## utility function for storing inverse value

cacheSolve <- function(x, ...) {
        ## Return a matrix that is the inverse of 'x'
  inv <- x$getinverse()
  if(!is.null(inv)) {
    message("getting cached data")
    return(inv)
  }
  data <- x$get()
  inv <- solve(data)
  x$setinverse(inv)
  inv
}
