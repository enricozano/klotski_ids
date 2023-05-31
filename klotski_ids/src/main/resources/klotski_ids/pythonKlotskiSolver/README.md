#Klotski Solver

A command-line solver for the Klotski game written in Python 3.4

Copyright 2014, Mingjing Zhang
Simon Fraser University

##How it works:
Run Main.py.
It reads the board specified in 'DefaultLayout.txt',
then output the steps in an easy-to-read formt

##Write your own layout file:
The layout file contains the coordinate of each block. It is organized as follows:
-- BEGINNING OF FILE --
TypeLetter
Coordinate
Coordinate
...
TypeLetter
Coordinate
Coordinate
...

...
-- END OF FILE --

'TypeLetter' is a single capital letter that specifies which type of piece the next few coordinates define,
until another TypeLetter is encountered. It can be either B(ig), H(orizontal), V(ertical) or S(mall).

'Coordinate' is a two number group in the order of 'X Y' (0 based). It represents the coordinate of the
LEFT-TOP block of a piece.

For example, the following file:

B
1 0
H
1 2
2 3

defines a 2x2 piece whose LEFT-TOP block is at (1,0),
and two 1x2 pieces whose LEFT-TOP blocks are at(1,2), and (2, 3) respectively.

So the resulting board would look (E = empty block):

E 0 0 E
E 0 0 E
E 1 1 E
E E 1 1
E E E E


##FAQ:
* Why the 'HR' prefix for all class files?

  HR is the acronym of the Chinese name of Klotski -- Hua-Rong Dao (The Path of Hua-Rong).
  In the Chinese version, the game reenacts a classical story where the warlord, Cao Cao,
  escaped to the path of Hua-Rong after defeated in the Battle of Red Cliff. There he was
  ambushed by the enemy general Guan Yu. Each piece is assigned with a name of a historical
  figure, with the 2x2 piece being Cao Cao, and the horizontal one Guan Yu.

##Default Output:
```language
Solution found in 5.627 secs. 11975 nodes checked

Solution Report:
========================================
Initial Layout:
----------------------------------------
1 0 0 2
1 0 0 2
3 5 5 4
3 6 7 4
8     9

Solution:
----------------------------------------
Step  1 to  5:
  #6 DOWN        #9 LEFT        #4 DOWN        #5 RIGHT       #3 RIGHT
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
  3 5 5 4  -->   3 5 5 4  -->   3 5 5    -->   3   5 5  -->     3 5 5  -->
  3   7 4        3   7 4        3   7 4        3   7 4          3 7 4
  8 6   9        8 6 9          8 6 9 4        8 6 9 4        8 6 9 4

Step  6 to 10:
  #8 UP          #6 LEFT        #3 DOWN        #5 LEFT LEFT   #7 UP RIGHT
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
    3 5 5  -->     3 5 5  -->       5 5  -->   5 5      -->   5 5   7  -->
  8 3 7 4        8 3 7 4        8 3 7 4        8 3 7 4        8 3   4
    6 9 4        6   9 4        6 3 9 4        6 3 9 4        6 3 9 4

Step 11 to 15:
  #9 UP UP       #3 RIGHT       #8 RIGHT DOWN  #5 DOWN        #9 LEFT LEFT
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
  5 5 9 7  -->   5 5 9 7  -->   5 5 9 7  -->       9 7  -->   9     7  -->
  8 3   4        8   3 4            3 4        5 5 3 4        5 5 3 4
  6 3   4        6   3 4        6 8 3 4        6 8 3 4        6 8 3 4

Step 16 to 20:
  #7 LEFT LEFT   #3 UP          #4 UP          #8 RIGHT RIGHT #6 RIGHT RIGHT
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2
  9 7      -->   9 7 3    -->   9 7 3 4  -->   9 7 3 4  -->   9 7 3 4  -->
  5 5 3 4        5 5 3 4        5 5 3 4        5 5 3 4        5 5 3 4
  6 8 3 4        6 8   4        6 8            6     8            6 8

Step 21 to 25:
  #5 DOWN        #7 DOWN LEFT   #3 LEFT        #4 LEFT        #2 DOWN DOWN
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0
  1 0 0 2        1 0 0 2        1 0 0 2        1 0 0 2        1 0 0
  9 7 3 4  -->   9   3 4  -->   9 3   4  -->   9 3 4    -->   9 3 4 2  -->
      3 4        7   3 4        7 3   4        7 3 4          7 3 4 2
  5 5 6 8        5 5 6 8        5 5 6 8        5 5 6 8        5 5 6 8

Step 26 to 30:
  #0 RIGHT       #1 RIGHT       #9 UP UP       #7 UP UP       #3 LEFT
  1   0 0          1 0 0        9 1 0 0        9 1 0 0        9 1 0 0
  1   0 0          1 0 0          1 0 0        7 1 0 0        7 1 0 0
  9 3 4 2  -->   9 3 4 2  -->     3 4 2  -->     3 4 2  -->   3   4 2  -->
  7 3 4 2        7 3 4 2        7 3 4 2          3 4 2        3   4 2
  5 5 6 8        5 5 6 8        5 5 6 8        5 5 6 8        5 5 6 8

Step 31 to 35:
  #1 DOWN DOWN   #0 LEFT        #2 UP UP       #4 RIGHT       #6 UP UP
  9   0 0        9 0 0          9 0 0 2        9 0 0 2        9 0 0 2
  7   0 0        7 0 0          7 0 0 2        7 0 0 2        7 0 0 2
  3 1 4 2  -->   3 1 4 2  -->   3 1 4    -->   3 1   4  -->   3 1 6 4  -->
  3 1 4 2        3 1 4 2        3 1 4          3 1   4        3 1   4
  5 5 6 8        5 5 6 8        5 5 6 8        5 5 6 8        5 5   8

Step 36 to 40:
  #8 LEFT UP     #5 RIGHT RIGHT #1 DOWN        #3 DOWN        #6 LEFT LEFT
  9 0 0 2        9 0 0 2        9 0 0 2        9 0 0 2        9 0 0 2
  7 0 0 2        7 0 0 2        7 0 0 2        7 0 0 2        7 0 0 2
  3 1 6 4  -->   3 1 6 4  -->   3   6 4  -->       6 4  -->   6     4  -->
  3 1 8 4        3 1 8 4        3 1 8 4        3 1 8 4        3 1 8 4
  5 5                5 5          1 5 5        3 1 5 5        3 1 5 5

Step 41 to 45:
  #0 DOWN        #9 RIGHT RIGHT #7 UP RIGHT    #6 UP UP       #3 UP UP
  9     2            9 2          7 9 2        6 7 9 2        6 7 9 2
  7 0 0 2        7 0 0 2          0 0 2          0 0 2        3 0 0 2
  6 0 0 4  -->   6 0 0 4  -->   6 0 0 4  -->     0 0 4  -->   3 0 0 4  -->
  3 1 8 4        3 1 8 4        3 1 8 4        3 1 8 4          1 8 4
  3 1 5 5        3 1 5 5        3 1 5 5        3 1 5 5          1 5 5

Step 46 to 50:
  #1 LEFT        #8 LEFT DOWN   #0 DOWN        #9 DOWN LEFT   #2 LEFT
  6 7 9 2        6 7 9 2        6 7 9 2        6 7   2        6 7 2
  3 0 0 2        3 0 0 2        3     2        3 9   2        3 9 2
  3 0 0 4  -->   3 0 0 4  -->   3 0 0 4  -->   3 0 0 4  -->   3 0 0 4  -->
  1   8 4        1     4        1 0 0 4        1 0 0 4        1 0 0 4
  1   5 5        1 8 5 5        1 8 5 5        1 8 5 5        1 8 5 5

Step 51 to 55:
  #4 UP UP       #0 RIGHT       #9 DOWN        #7 DOWN        #6 RIGHT
  6 7 2 4        6 7 2 4        6 7 2 4        6   2 4          6 2 4
  3 9 2 4        3 9 2 4        3   2 4        3 7 2 4        3 7 2 4
  3 0 0    -->   3   0 0  -->   3 9 0 0  -->   3 9 0 0  -->   3 9 0 0  -->
  1 0 0          1   0 0        1   0 0        1   0 0        1   0 0
  1 8 5 5        1 8 5 5        1 8 5 5        1 8 5 5        1 8 5 5

Step 56 to 60:
  #3 UP          #1 UP          #8 LEFT        #9 DOWN DOWN   #0 LEFT
  3 6 2 4        3 6 2 4        3 6 2 4        3 6 2 4        3 6 2 4
  3 7 2 4        3 7 2 4        3 7 2 4        3 7 2 4        3 7 2 4
    9 0 0  -->   1 9 0 0  -->   1 9 0 0  -->   1   0 0  -->   1 0 0    -->
  1   0 0        1   0 0        1   0 0        1   0 0        1 0 0
  1 8 5 5          8 5 5        8   5 5        8 9 5 5        8 9 5 5

Step 61 to 65:
  #4 DOWN DOWN   #2 RIGHT       #6 RIGHT       #7 RIGHT       #3 RIGHT
  3 6 2          3 6   2        3   6 2        3   6 2          3 6 2
  3 7 2          3 7   2        3 7   2        3   7 2          3 7 2
  1 0 0 4  -->   1 0 0 4  -->   1 0 0 4  -->   1 0 0 4  -->   1 0 0 4  -->
  1 0 0 4        1 0 0 4        1 0 0 4        1 0 0 4        1 0 0 4
  8 9 5 5        8 9 5 5        8 9 5 5        8 9 5 5        8 9 5 5

Step 66 to 70:
  #1 UP UP       #0 LEFT        #7 DOWN DOWN   #6 DOWN DOWN   #2 LEFT
  1 3 6 2        1 3 6 2        1 3 6 2        1 3   2        1 3 2
  1 3 7 2        1 3 7 2        1 3   2        1 3   2        1 3 2
    0 0 4  -->   0 0   4  -->   0 0   4  -->   0 0 6 4  -->   0 0 6 4  -->
    0 0 4        0 0   4        0 0 7 4        0 0 7 4        0 0 7 4
  8 9 5 5        8 9 5 5        8 9 5 5        8 9 5 5        8 9 5 5

Step 71 to 75:
  #4 UP UP       #7 RIGHT UP    #5 UP          #9 RIGHT RIGHT #8 RIGHT RIGHT
  1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4
  1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4
  0 0 6    -->   0 0 6 7  -->   0 0 6 7  -->   0 0 6 7  -->   0 0 6 7  -->
  0 0 7          0 0            0 0 5 5        0 0 5 5        0 0 5 5
  8 9 5 5        8 9 5 5        8 9            8     9            8 9

Step 76 to 80:
  #0 DOWN        #6 LEFT LEFT   #7 LEFT LEFT   #5 UP          #8 UP RIGHT
  1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4
  1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4        1 3 2 4
      6 7  -->   6     7  -->   6 7      -->   6 7 5 5  -->   6 7 5 5  -->
  0 0 5 5        0 0 5 5        0 0 5 5        0 0            0 0   8
  0 0 8 9        0 0 8 9        0 0 8 9        0 0 8 9        0 0   9

Step 81 to 81:
  #0 RIGHT
  1 3 2 4
  1 3 2 4
  6 7 5 5
    0 0 8
    0 0 9

----------------------------------------
Total steps: 81
============ END OF REPORT =============
```


