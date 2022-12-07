# CS4102 Spring 2022 - Unit A Programming 
#################################
# Collaboration Policy: You are encouraged to collaborate with up to 3 other
# students, but all work submitted must be your own independently written
# solution. List the computing ids of all of your collaborators in the
# comments at the top of each submitted file. Do not share written notes,
# documents (including Google docs, Overleaf docs, discussion notes, PDFs), or
# code. Do not seek published or online solutions, including pseudocode, for
# this assignment. If you use any published or online resources (which may not
# include solutions) when completing this assignment, be sure to cite them. Do
# not submit a solution that you are unable to explain orally to a member of
# the course staff. Any solutions that share similar text/code will be
# considered in breach of this policy. Please refer to the syllabus for a
# complete description of the collaboration policy.
#################################
# Your Computing ID: wyz5rge
# Collaborators: jhs8cue, kd5eyn
# Sources: Introduction to Algorithms, Cormen
#################################
import math
import copy

# Point class to store x, y values
class Point():
    def __init__(self, x, y):
        self.x = x
        self.y = y

class ClosestPair:
    def __init__(self):
        return

    # Function that uses distance formula to compute distance between two points
    def distance(p1, p2):
        x_dist = p2.x - p1.x
        y_dist = p2.y - p1.y
        return math.sqrt(x_dist * x_dist + y_dist * y_dist)

    # This is the method that should set off the computation
    # of closest pair.  It takes as input a list containing lines of input
    # as strings.  You should parse that input and then call a
    # subroutine that you write to compute the closest pair distances
    # and return those values from this method
    #
    # @return the distances between the closest pair and second closest pair
    # with closest at position 0 and second at position 1 
    def compute(self, file_data):
        P = []
        for i in range(len(file_data)):
            P[i] = Point(file_data[i][1], file_data[i][2])

        P.sort(key=lambda point: point.x)
        Q = copy.deepcopy(P)
        Q.sort(key=lambda point: point.y)

        return self.recursiveClosest(P, Q, len(P))

        #closest = 0.0
        #secondClosest = 0.1
        #return [closest, secondClosest]

    # brute force distance for when there at most three points in a subproblem
    def bruteForce(P):
        min = float('inf')
        for i in range(len(P)):
            for j in range(i+1, len(P)):
                d = distance(P[i], P[j])
                if min > d:
                    min = d
        return min

    # find distance between closest points in runway
    # runway is a list of points inside of runway
    def runwayClosest(runway, size, delta):
        min = delta
        for i in range(size):
            j = i + 1
            while j < size and (runway[j].y - runway[i].y) < min:
                min = distance(runway[i], runway[j])
                j = j + 1
        return min

    def recursiveClosest(P, Q, n):
        if n <= 3:
            return bruteForce(P, n)

        midpoint = n // 2;
        middlePoint = p[midpoint]

        leftP = P[:midpoint]
        rightP = P[midpoint:]

        distanceLeft = recursiveClosest(leftP, Q, midpoint)
        distanceRight = recursiveClosest(rightP, Q, n - midpoint)

        minDist = min(distanceLeft, distanceRight)

        runwayP = []
        runwayQ = []
        leftRight = leftP + rightP
        for i in range(n):
            if abs(leftRight[i].x - middlePoint.x) < minDist:
                runwayP.append(leftRight[i])
            if abs(Q[i].x - middlePoint.x) < minDist:
                runwayQ.append(Q[i])

        runwayP.sort(key=lambda point: point.y)
        minA = min(minDist, runwayClosest(runwayP, len(runwayP), minDist))
        minB = min(minDist, runwayClosest(runwayQ, len(runwayQ), minDist))

        return min(minA, minB)