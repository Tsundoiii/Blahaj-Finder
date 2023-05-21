# Blåhaj Finder
Blåhaj Finder is a program that finds the nearest IKEA store(s) based on provided location(s) and displays the availability of [shonks](https://www.ikea.com/us/en/p/blahaj-soft-toy-shark-90373590/) at those stores(s).

This program currently only searches IKEA stores in the US (although any location in the world can be passed in and the program can still find the nearest IKEA store(s) to that location).


I made this as my final project in AP Computer Science A (that's why I had to use Java ;~;) and I will probably never update this again. I don't expect anyone to actually use this, but if you do somehow find this, I hope this helps you find a soft and cuddly blåhaj of your own :3
## Installation
You can install the program by either downloading the `blahaj-finder.jar` file from the latest release or by [building it from source code](#building).

### Building
You will need the [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) to build the program.

First, clone the repository.
```
git clone https://github.com/Tsundoiii/blahaj-finder.git
cd blahaj-finder
```
Then, use the Gradle Wrapper to build the program into a JAR file.

Windows:
```
.\gradlew.bat jar
```

Linux/MacOS:
```
./gradlew jar
```

The `blahaj-finder.jar` file will be located in the `build/libs` directory
## Running
Run the program using Java.
```
java -jar <path to JAR file> <options>
```
### Options
Options are passed as command line arguments. Although it is technically possible to run the program without options, you will probably want to pass options to the program.

There are two types of options: location and information.
#### Location
These options determine what location the program will use to search for  IKEA stores.
- `-c`/`--coordinates <coordinates>`: Coordinates to use to determine nearest IKEA store in demical degrees. Set to (0, 0) if no coordinates are passed.
- `-s`/`--state <state(s)>`: State(s) to search for stores in. Only IKEA stores located in specified state(s) will be displayed. All IKEA stores will be displayed if no state(s) are passed.

#### Information
These options determine what information the program will display.
- `-n`/`--number-of-stores <number>`: Number of stores to display. Set to 1 if no number is passed.
- `-a`/`--address`: Display address of store(s). Address will not be displayed if not passed.
- `-o`/`--opening-hours`: Display opening hours of store(s) and whether the store is current open or not. Opening hours will not be displayed if not passed.

See the [Examples](#Examples) for detailed examples of usage.
## Examples