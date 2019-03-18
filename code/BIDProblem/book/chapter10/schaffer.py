from sys import *
from math import *

while True:
    # Read the next line from standard input
    try:
        line = raw_input()
    except EOFError:
        break

    # If line is empty, stop
    if line == "":
        break

    # Parse the decision variables from the input
    vars = map(float, line.split())

    # Evaluate the Schaffer problem
    objs = (vars[0]**2, (vars[0] - 2)**2)

    # Print objectives to standard output, flush to write immediately
    print "%f %f" % objs
    stdout.flush()
