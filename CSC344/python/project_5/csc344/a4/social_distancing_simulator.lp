%Duncan Zaug
%806107062
%Project 4: Social Distancing Similator

% n1 is the length of the side
% n2 is the distance between the south edge and north edge
% startX is the X-coordinate of the starting position
% d is the required social distance
#const n1=25.
#const n2=25.
#const startX=13.
#const d=6. 

cell(0..n1,0..n2).
%p(1,2).
p(13,7). 
p(20,4). 
p(4,20).
person(startX,0).

%valid and invalid cells
invalid(X1,Y1) :- cell(X1,Y1), p(X2,Y2), (X1-X2)**2 + (Y1-Y2)**2 < d**2.
%X edge movement
edge(X1,Y1,X2,Y2) :- person(X1,Y1), not invalid(X1,Y1), not invalid(X2,Y2), |X1-X2| == 1, |Y1-Y2| == 0, cell(X1,Y1), cell(X2,Y2).
%Y edge movement 
edge(X1,Y1,X2,Y2) :- person(X1,Y1), not invalid(X1,Y1), not invalid(X2,Y2), |X1-X2| == 0, Y1-Y2 == -1, cell(X1,Y1), cell(X2,Y2).
%path time
%(n2+1)+(n2/3) 
{path(X1,Y1,X2,Y2): edge(X1,Y1,X2,Y2)}.
:- not path(startX,0,_,_).
:- path(_,_,startX,0).
:- path(X,Y,X1,Y1), path(X,Y,X2,Y2), X1 != X2, Y1 != Y2.
:- path(X,Y,X1,Y1), path(X,Y,X2,Y2), X1 != X2.
:- path(X,Y,X1,Y1), path(X,Y,X2,Y2), Y1 != Y2.
:- path(X1,Y1,X2,Y2), path(X2,Y2,X1,Y1).
%:- path(_,n2,_,_).
:- path(_,_,X1,Y1), path(_,_,X2,Y2), Y1 == n2, Y2 == n2, X1 != X2.
:- not path(_,_,_,n2).
%:- path(X1,Y1,X2,Y2), path(X3,Y3,X4,Y4).
%:- not path(X1,Y,X2,n2), path(X1,n2,X2,Y), path(X2,n2,X1,Y).
person(X,Y) :- path(_,_,X,Y).

%ignore this
%vertical
%path(X,Y1,X,Y2) :- path(X,Y1-1,X,Y2-1), Y2 > Y1.
%right
%path(X1,Y,X2,Y) :- path(X1-1,Y,X2-1,Y), X2 > X1.
%left
%path(X1,Y,X2,Y) :- path(X1-1,Y,X2-1,Y), X2 < X1.
%end
%path(X,Y1,X,n2) :- path(X,Y1-1,X,n2-1).

%show results
%#show invalid/2.
%#show edge/4.
#show path/4.
