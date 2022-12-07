# CS4102 Spring 2022 -- Unit D Programming
#################################
# Collaboration Policy: You are encouraged to collaborate with up to 3 other
# students, but all work submitted must be your own independently written
# solution. List the computing ids of all of your collaborators in the comment
# at the top of your java or python file. Do not seek published or online
# solutions for any assignments. If you use any published or online resources
# (which may not include solutions) when completing this assignment, be sure to
# cite them. Do not submit a solution that you are unable to explain orally to a
# member of the course staff.
#################################
# Your Computing ID: wyz5rge
# Collaborators: N/A
# Sources: Introduction to Algorithms, Cormen
#################################

# originally started this assignment in Java, switched to Python because jgrapht is annoying to work with
# final submission, cleaned up some code

# having this package would be pretty useful...
import networkx

class TilingDino:
    def __init__(self):
        return

    # This is the method that should set off the computation
    # of tiling dino.  It takes as input a list lines of input
    # as strings.  You should parse that input, find a tiling,
    # and return a list of strings representing the tiling
    #
    # @return the list of strings representing the tiling
    def compute(self, lines):

        # just like in my java project, we're going to assigned tiled pixels in a checkerboard pattern,
        # then find a perfect bitartite matching based after generating the graph

        # this will be the array that the domino tilings will be added to
        solution = []

        # dimensions of picture
        rows = len(lines)
        columns = len(lines[0])

        g = networkx.DiGraph()

        # bipartite matching algorithm is solved by reducing to vertex-disjoint flow network
        g.add_node("source")
        g.add_node("sink")

        # count number of pixels that need to be tiled, and assign b/w based on position
        pixels = 0
        for r in range(rows):
            for c in range(columns):
                if lines[r][c] == "#":
                    pixels = pixels + 1

                    # very top left corner pixel will be "white"
                    # original implementation executed based on row and column modulus depending on even/oddness of row
                    # checking for equality serves the same purpose, but is implemented in half the number of lines
                    if r % 2 == c % 2:
                        # column number comes before row number to maintain consistency with the assignment description
                        pixelString = "w " + str(c) + " " + str(r)
                        g.add_node(pixelString)
                        g.add_edge("source", pixelString, capacity = 1)
                    else:
                        pixelString = "b " + str(c) + " " + str(r)
                        g.add_node(pixelString)
                        g.add_edge(pixelString, "sink", capacity = 1)

        # correctly counting number of pixels
        # print("pixels: " + pixels)

        # correctly returning impossible for odd nubmer of pixels
        if pixels % 2 == 1:
            solution.append("impossible")
            return solution

        # create edges that connect adjacent pixels
        # i tried to implement this in the nested for-loop above, but it's a little hard to create edges when certain vertices haven't been created yet
        for r in range(rows):
            for c in range(columns):
                if lines[r][c] == "#":
                    # check pixel below if curent pixel is not on bottom row
                    if c + 1 < columns and lines[r][c + 1] == "#":
                        if r % 2 == c % 2:
                            edgeSourceString = "w " + str(c) + " " + str(r)
                            edgeTargetString = "b " + str(c + 1) + " " + str(r)
                            g.add_edge(edgeSourceString, edgeTargetString, capacity = 1)
                            # print("edge added down white")
                        else:
                            edgeSourceString = "w " + str(c + 1) + " " + str(r)
                            edgeTargetString = "b " + str(c) + " " + str(r)
                            g.add_edge(edgeSourceString, edgeTargetString, capacity = 1)
                            # print("edge added down black")

                    # check pixel to the right if pixel is not on right border
                    if r + 1 < rows and lines[r + 1][c] == "#":
                        if r % 2 == c % 2:
                            edgeSourceString = "w " + str(c) + " " + str(r)
                            edgeTargetString = "b " + str(c) + " " + str(r + 1)
                            g.add_edge(edgeSourceString, edgeTargetString, capacity = 1)
                            # print("edge added right white")
                        else:
                            edgeSourceString = "w " + str(c) + " " + str(r + 1)
                            edgeTargetString = "b " + str(c) + " " + str(r)
                            g.add_edge(edgeSourceString, edgeTargetString, capacity = 1)
                            # print("edge added right black")

        maxFlow = networkx.maximum_flow(g, "source", "sink")
        maxFlow = list(maxFlow)

        # print("possible pairs: " + str(pixels / 2))
        # print("max flow: " + str(maxFlow[0]))

        # if networkx.maximum_flow_value(g, "source", "sink") == pixels / 2:
        if maxFlow[0] == pixels / 2:
            # print("printing solution")
            for source in maxFlow[1]:
                if source[0] == "w":
                    for target in maxFlow[1][source]:
                        if maxFlow[1][source][target] == 1:
                            sourceNode, c1, r1 = source.split(" ")
                            targetNote, c2, r2 = target.split(" ")
                            solution.append(c1 + " " + r1 + " " + c2 + " " + r2)
            return solution

        # originally just had this line return solution, but i realized that there's achance that a picture with an even number of pixels is still impossible to tile
        solution.append("impossible")
        return solution