function [theta, J_history] = gradientDescentMulti(X, y, theta, alpha, num_iters)
%GRADIENTDESCENTMULTI Performs gradient descent to learn theta
%   theta = GRADIENTDESCENTMULTI(x, y, theta, alpha, num_iters) updates theta by
%   taking num_iters gradient steps with learning rate alpha

% Initialize some useful values
m = length(y); % number of training examples
J_history = zeros(num_iters, 1);

for iter = 1:num_iters

    % ====================== YOUR CODE HERE ======================
    % Instructions: Perform a single gradient step on the parameter vector
    %               theta. 
    %
    % Hint: While debugging, it can be useful to print out the values
    %       of the cost function (computeCostMulti) and gradient here.
    %


	totsum = zeros(size(X,2),1);
	
	%size(X(3,:))
	
	for i=1:m
	   %a = (theta(1)*X(i,1)+ theta(2)*X(i,2) - y(i));

	   totsum = totsum + ((X(i,:)*theta - y(i)).* X(i,:))' ; 

	   %sum0 = sum0 + a * X(i,1)
	   %sum1 = sum1 + a * X(i,2)
	end
	
    %theta = theta - alpha/m * [sum0;sum1];
	
	theta = theta - alpha/m * totsum;








    % ============================================================

    % Save the cost J in every iteration    
    J_history(iter) = computeCostMulti(X, y, theta);

end

end
