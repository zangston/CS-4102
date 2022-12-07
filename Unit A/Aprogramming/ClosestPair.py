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
class ClosestPair:
    def __init__(self):
        return

    # list we will use to track that smallest distances found during recursion
    # we will code a function later to manipulate this
    # should only have two values
    closest = []
    distances = []

    # This is the method that should set off the computation
    # of closest pair.  It takes as input a list containing lines of input
    # as strings.  You should parse that input and then call a
    # subroutine that you write to compute the closest pair distances
    # and return those values from this method
    #
    # @return the distances between the closest pair and second closest pair
    # with closest at position 0 and second at position 1 
    def compute(self, file_data):
        # given code
        #    closest = 0.0
        #    secondClosest = 0.1
        #    return [closest, secondClosest]

        # sort_and_call to "set off" the recursive part of the algorithm
        self.sort_and_call(file_data)
        # print(self.distances)
        return self.find_smallest(self.distances)  # this is what we really care about in terms of return values

    def find_smallest(self, distances):
        smallest = []
        #print(distances)
        sorted_dists = sorted(distances)
        # print(sorted_dists)
        smallest.append(sorted_dists[0])

        for i in range(len(sorted_dists)):
            if sorted_dists[i] != smallest[0]:
                smallest.append(sorted_dists[i])
                break

        return smallest

    # where it all starts
    # reads in file data line-by-line, splits lines into a list, appends list to a greater list
    # each inner list acts as a point "object", with first field being x-coordinate, second field being y
    # finishes off by calling the divide-and-conquer recursive function
    def sort_and_call(self, file_data):
        points = []
        for line in file_data:
            x, y = line.split()
            x = float(x)
            y = float(y)
            points.append([x, y])
        x_list = sorted(points, key=lambda i: [i[0], i[1]])
        # print functions to see if sorting is working
        # print("sorted x list")
        # print(x_list)

        y_list = pointlist = sorted(points, key=lambda i: i[1])
        # print("sorted y list")
        # print(y_list)
        # it works :)
        return self.dc_cpp(x_list, y_list)

    # dc_cpp = divide and conquer closest pair of points
    # this is the recursive function that does most of the work
    def dc_cpp(self, x_list, y_list):
        # base case: at most 3 points, use brute force to find the shortest distance between points
        if len(x_list) <= 3:
            bruteforce_min = self.bruteforce(x_list)
            self.track_closest(bruteforce_min)
            return bruteforce_min

        # recursion: divide points into two sub-problems, split down the middle by median x-coordinate
        midpoint = len(x_list) // 2
        middlePoint = x_list[midpoint]

        # create two new lists of points to pass into recursive calls on sub-problems
        left_list = x_list[:midpoint]
        right_list = x_list[midpoint:]

        # recursive calls on both sub-problems
        left_dist = self.dc_cpp(left_list, y_list)
        right_dist = self.dc_cpp(right_list, y_list)

        # find minimum distance on left and right sides
        # this is how we determine the width of our "runway"
        left_right_min = min(left_dist, right_dist)

        runway_points = []

        for i in range(len(x_list)):
            if y_list[i][0] >= middlePoint[0] - self.closest[1] and y_list[i][0] <= middlePoint[0] + self.closest[1]:
                runway_points.append(x_list[i])

        runway_points.sort(key=lambda n: n[1])

        runway_min = self.runway(runway_points, left_right_min, middlePoint)
        if runway_min != left_right_min:
            self.track_closest(runway_min)

        total_min = min(left_right_min, runway_min)
        # total_min = left_right_min
        return total_min

    def runway(self, points, delta, midpoint):
        min_dist = delta

        for i in range(len(points)):
            for j in range(i + 1, min(i + 8, len(points))):
                d = self.find_dist(points[i], points[j])

                if d < min_dist:
                    min_dist = d

                if (points[i][0] < midpoint[0] <= points[j][0]) or \
                        (points[i][0] >= midpoint[0] and points[j][0] < midpoint[0]) or \
                        ((points[i][0] == midpoint[0] and points[j][0] == midpoint[0]) and ((points[i][1] < midpoint[1] and points[j][1] >= midpoint[1])
                        or (points[i][1] >= midpoint[1] and points[j][1] < midpoint[1]))):
                    self.track_closest(d)

        return min_dist

    def bruteforce(self, points):
        min_dist = float('inf')

        for i in range(len(points)):
            for j in range(i + 1, len(points)):
                d = self.find_dist(points[i], points[j])
                if d < min_dist:
                    min_dist = d
        return min_dist

    def find_dist(self, point1, point2):
        xdist = point2[0] - point1[0]
        ydist = point2[1] - point1[1]
        return ((xdist ** 2) + (ydist ** 2)) ** 0.5

    def track_closest(self, dist):
        self.distances.append(dist)
        if len(self.closest) == 0:
            self.closest.append(dist)

        elif len(self.closest) == 1:
            if dist < self.closest[0]:
                self.closest.append(self.closest[0])
                self.closest[0] = dist
            else:
                self.closest.append(dist)

        elif dist < self.closest[0]:
            self.closest[1] = self.closest[0]
            self.closest[0] = dist

        elif dist < self.closest[1]:
            self.closest[1] = dist
