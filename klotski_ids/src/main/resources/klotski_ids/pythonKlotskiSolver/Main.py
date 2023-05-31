#!/usr/bin/env python

""" Main file of the Huarong Solver (aka Klotski as known to the western world)
"""

__author__ = 'Mingjing'
__copyright__ = "Copyright 2014, Stellari Studio"

from HRConsts import *
from HRException import InvalidMove
from HRBoard import HRBoard
from HRGame import HRGame

if __name__ == "__main__":

    game = HRGame('src/main/resources/klotski_ids/data/levelSolutions/DefaultLayout.txt')
    game.solve()
    game.solution.outputToFile("src/main/resources/klotski_ids/data/levelSolutions/Solutions.txt")
    game.solution.output(if_graphical=True, results_per_line=5)

