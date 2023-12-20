# Rubik's Cube Solver

This repository hosts a simple Rubik's Cube Solver implemented in Java using the A* algorithm.



### Cube Representation

The Rubik's Cube is represented as a 6x3x3 matrix. Each position corresponds to a face of the cube, and the 3 positions correspond to each row and column of the cube.

#### Assumptions

1. The cube coding works based on a 3D model.
2. Sides are numbered as follows:
   - G is the 1st face (0 in Java)
   - R is the 2nd face (1 in Java)
   - B is the 3rd face (2 in Java)
   - O is the 4th face (3 in Java)
   - Y is the 5th face (4 in Java)
   - W is the 6th face (5 in Java)
4. Changes in the cube after each move are made based on the 3D model.
5. Central cubes remain in their position throughout the solution.
7. Each cube has a defined face, line, and column.

#### Moves

- Possible moves: 18 (12 for moveUP/moveDOWN/moveLEFT/moveRIGHT and 6 for moveUP/moveDOWN on the 2nd face).
- Moves on central columns are coded as simultaneous movements of the sides.

#### Randomization

Randomizing the cube is done by starting from the final state and performing successive random moves.

#### Example Moves

1. **MoveRIGHT the middle line of the 1st face**


```
    BOO
    WWW
    BOO
YOY OGG WRW BBR
YOY OGG WRW BBR
YBY OGG WGW BBR
    GRR
    YYY
    GRR
    
 ```  

2. **MoveUP the 1st column of the 2nd face**

```
    BOO
    WWW
    WWW
YOO GGG GGG RRW 
YOO GGG RRW BBR
YBB OOO GGW BBR
    YYY
    YYY
    GRR

```
3. **MoveUP the 3rd column of the 2nd face**
   
```
    WWW
    WWW
    WWW
OOO GGG GGG RRR 
OOO GGG RRR BBB
BBB OOO GGG RRR
    YYY
    YYY
    YYY

```

5. **MoveLEFT the 3rd row in the 1st face**

```
    WWW
    WWW
    WWW
OOO GGG GGG RRR 
OOO GGG RRR BBB
OOO GGG RRR BBB
    YYY
    YYY
    YYY

 ```

#### Resolution

When solving, a `State` class is used to implement cube properties and those needed for the A* algorithm.

A* initializes a list and puts in the original state. Then, it produces its children, and based on the heuristic function, it chooses the one with the lowest cost (the sum of moves). The heuristic function used is the Manhattan distance for the sides and corners of the cube.

- `h1(n)`: Manhattan distance of sides divided by 4 (as each move moves 4 sides).
- `h2(n)`: Manhattan distance of corners divided by 4 (as each move moves 4 corners).
- `g(n)`: The cost from the root to n.
- `f(n)`: Maximum of `h1` and `h2` plus `g(n)`.

1. `h(n)` is an acceptable heuristic function since to solve the entire cube, all angles and all sides must first be solved. Hence, `h(n) = max{h1, h2} <= C(n)`.

3. `h(n)` is a consistent function since for every state, the heuristic estimate (i.e., the steps needed to solve either the angles or the sides) is always less than or equal to the actual distance from a node `n` to another neighboring `n'` plus the heuristic distance from `n'` to the final state.

*Note: Each natural Rubik's cube consists of 20 cubes that move and 6 central ones that do not move. The angles of the cube are 8 in total and consist of 3 stickers of different colors, and the sides are 12 in total and consist of 2 stickers of different colors.*


