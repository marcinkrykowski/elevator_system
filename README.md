# Elevator Control System

## Description
Design and implement an elevator control system. The system controls elevators that can stand still, go up or down.
There can be one, or many people going in different directions.Your system should be able to handle a few elevators — up to 16.

## Design
*Disclaimer: The implementation tend to be functional, but some parts of it are not expressed in a functional way e.g. mutable state.* 
The most important class is `MyElevatorSystem` that implements `ElevatorSystem` trait. That trait contains 4 methods: 
- `status` - returns current status of each elevator
- `step` - enables to move each elevator
- `pickup` - takes order of requested floor and moving direction
- `update` - executes an update for each elevator in the system

In the implementation `MyElevatorSystem` uses `Elevator` class that contains implementations for each method respectively and contains `ElevatorState` to keep that traction of state for each elevator.

## How to run application
It's a plain `sbt` application. All you need to do is run `Main` class or execute tests with `sbt test` command.

## Possible improvements
Couple of possible improvements:
- add more tests
- refactor some parts to domain objects e.g. requests instead of List of ints
- synchronise code, so it can be used in multi threading environment
- make design and implementation more functional e.g. remove mutable state
- change interface and remove unused arguments