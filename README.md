# Scala Console Program
## Use Scala to build an interactive shell application
  
This project is an example of using Scala to create an interactive command-line application.

To make it interesting, the application takes as its goal the requirement to generate an odd-dimensioned grid, 
populating it with an increasing sequence of consecutive integers, arranged in a pattern spiralling 
out from the center point, and to calculate distances between cells in this grid.

## App Requirements
1. Fill a two-dimensional NxN grid with NxN numbers (N is odd and the numbers are monotone increasing). 
2. Start in the middle/center  and walk counter-clockwise to the outside. The middle square starts with 1.
3. Now given a location (one of the cell-values), calculate the rectilinear distance to the center.
4. How many steps are required to go from location 368078 to the center?

### Prerequisites & bulding the example
The target language is Scala version 2.12, and uses the build tool sbt 1.2.1.
Clone this repository in a fresh directory:
```
% uit clone git@bitbucket.org:royp/inspiral.git
```
Compile the example with the following command:
```
% sbt clean compile
[info] Done compiling.
[success] Total time: 6 s, completed 12-Aug-2018 11:38:12
```
The only explicit library dependency outside of the Scala language environment is ScalaTest version 3.0.5

## Example design
The main elements of the example consist of:
### Command loop
An implementation of a classic Un*x interactive shell, that prompts for a command and executes it.
This is implemented as a finite state machine, to make it simple to add or change commands.
### The grid builder
Given a single dimension value, N, constructs and initializes a model of a NxN grid
### Next-location algorithm
Determines the coordinates for the next value in the series

### Assumptions
Although not specified, it seemed reasonable to limit the grid dimension to values of N to a maximum of 999, as 
processing c. 1 million data points should be enough for the purposes of this exercise.

### Correctness
The implementation takes a 'design by contract' approach to correctness: class invariants and method
preconditions (Scala's ```require``` clause) are used to specify correctness and the client is expected to guard against
incorrect inputs - the client in this case is the console program, which rejects user input that would violate these
conditions

### Domain model
To make the code more readable, a handful of domain classes were created, representing
some of the prime concepts in the problem domain, such as ```Point``` and ```Bounds```, representing
coordinates in the grid space.

In addition, the application resurrects the old Prolog 'turtle' model, whereby a (real or virtual)
turtle robot responded to programmatic commands, such as 'forward', 'turn left', 'turn right', etc., 
here represented by the ```Turtle``` trait.

##### Requirement [4]
```Point.distance(other: Point)``` calculates the Manhattan Distance between a given point and another.  This is done by 
summing the absolute differences of subtracting the 'x' and 'y' values of initial point from the other point.

### Creating the grid
##### Requirement [2]
This example is built on an implementation of the ```Turtle``` trait, programmed to generate the spiral
path required, ```SprialTurtle```; other implementations could be built, with different rules,
if required.

The ```Turtle``` responds to the command ```walk``` & ```turn``` by returning a copy of
itself with the requested location and direction data set.

As a requirement of the example is to calculate the distance between two values in the
grid, which will require mapping a cell value to its grid coordinates, the grid
is initially represented as a map, ```Int -> Point```, simplifying later lookup.
This map is created by the tail-recursive function in ```GridBuilder.pointMap```.
##### Requirement [3]
```GridBuilder.pointMap``` recurses over the sequence of values, and uses the ```Turtle.walk``` method 
to determine the next coordinate location to index with the current list head, creating a copy of the result
map with this tuple appended to it

The value ```GridBuilder.gridView``` is a displayable representation of the grid built from this map - this is not strictly necessary
to meet the requirements, but is useful to provide feedback to the user and visualize 
the results.

## Running the console program
The example contains a ```main``` class to exercise the functionality.
It initially prompts for the grid dimension, after which it displays a representation of the grid,
and prompts for the cell value whose distance from the center (cell 1) is to be calculated.
On input of this value, the distance is calculated and displayed.  
The program iterates over the input/calculate/display actions, until 'q' is entered.
```
% sbt run
Generate NxN grid
enter N (odd) dimension (or q): 33
1025 1024 1023 1022 1021 .... 
1026  901  900  899  898 .... 
....  ...  ...  ...  ... .... 
1057 1058 1059 1060 1061 ....  
Please enter number between 1 and 1089 (or q): 1
1 @ Point(17,17) -> 1 @ Point(17,17) distance = 0
Please enter number between 1 and 1089 (or q): 12
1 @ Point(17,17) -> 12 @ Point(16,19) distance = 3
Please enter number between 1 and 1089 (or q): 23
1 @ Point(17,17) -> 23 @ Point(19,17) distance = 2
Please enter number between 1 and 1089 (or q): 1024
1 @ Point(17,17) -> 1024 @ Point(1,2) distance = 31
Please enter number between 1 and 1089 (or q): q
```
### Answering the question
#### Requirement [5]
To answer the question, generate a 607x607 grid (big enough to encompass the specified value):
```
Generate NxN grid
enter N (odd) dimension (or q): 607
....
Please enter number between 1 and 368449 (or q): 368078
1 @ Point(304,304) -> 368078 @ Point(607,236) distance = 371
```
## Testing
### Running the tests
Run the test suite to verify correct behaviour.  From the command line:
```
% sbt test
```
### Test Coverage Report
[![Coverage Status](https://coveralls.io/repos/github/sothach/inspiral/badge.svg?branch=master)](https://coveralls.io/github/sothach/inspiral?branch=master)
To measure test coverage, this app uses the 'scoverage' SBT plugin.
To create the report, rom the command line:
```
% sbt coverage test coverageReport
```

## Author
* [Roy Phillips](mailto:phillips.roy@gmail.com)

## License
(c) 2018 This project is licensed under Creative Commons License

Attribution 4.0 International (CC BY 4.0)