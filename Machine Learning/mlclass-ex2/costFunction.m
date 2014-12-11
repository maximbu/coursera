function [J, grad] = costFunction(theta, X, y)
%COSTFUNCTION Compute cost and gradient for logistic regression
%   J = COSTFUNCTION(theta, X, y) computes the cost of using theta as the
%   parameter for logistic regression and the gradient of the cost
%   w.r.t. to the parameters.

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;
grad = zeros(size(theta));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta.
%               You should set J to the cost.
%               Compute the partial derivatives and set grad to the partial
%               derivatives of the cost w.r.t. each parameter in theta
%
% Note: grad should have the same dimensions as theta
%
sum =0;


for i=1:m
    hVal = sigmoid(X(i,:)*theta);
	sum = sum + (-y(i)*log(hVal)-(1-y(i))*(log(1-hVal)));
end

J = (1/m)*sum;

n = size(theta);

%for l=1:n
%	for i=1:m
%	   hVal = sigmoid(theta'*X(i));
%	   %grad(l) = grad(i) + (hVal - y(i)).* X(l))' ; 
%	end
%end

for i=1:m
	hVal = sigmoid(X(i,:)*theta);
	grad = grad + 1/m*((hVal - y(i)).* X(i,:))' ; 
end


% =============================================================

end
