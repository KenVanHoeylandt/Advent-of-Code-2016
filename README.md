# Advent of Code 2016

## Introduction

This repository contains solutions for Advent of Code 2016.

All solutions are implemented in Kotlin with RxJava 2.x

Assignment data is cached within the project.
The Framework either retrieves the local data (if available) or otherwise retrieves the data from the server.
If you want to use the framework for your own assignments, make sure to delete all contents of the `data/` folder after forking.

## Commands

Building the jar file:

```
./gradlew jar
```

Running the jar file:

```
java -jar build/libs/AdventOfCode2016.jar [sessionToken] [day]
```
