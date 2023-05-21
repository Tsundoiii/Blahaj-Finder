# Blåhaj Finder
Blåhaj Finder is a program that finds the nearest IKEA store(s) based on provided location(s) and displays the availability of [shonks](https://www.ikea.com/us/en/p/blahaj-soft-toy-shark-90373590/) at those stores(s).

This program currently only searches IKEA stores in the US (although any location in the world can be passed in and the program can still find the nearest American IKEA store(s) to that location).

I made this as my final project in AP Computer Science A (that's why I had to use Java ;~;) and I will probably never update this again. I don't expect anyone to actually use this, but if you do somehow find this, I hope this helps you find a soft and cuddly emotional support blåhaj of your own <3
## Installation
You can either run the program in the browser or install the program to your local machine. To [build the program from source code](#building) or run it on your local machine, you will need the [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/).
### Browser
Fork [this repository](https://app.codingrooms.com/w/bYrxX4yc2GWI) to run it in the browser.
### Local
#### Download
Download the `blahaj-finder.jar` file from the latest (and probably only) release from the releases tab.
#### Building
First, clone the repository.
```
git clone https://github.com/Tsundoiii/blahaj-finder.git
cd blahaj-finder
```
Then, use the Gradle Wrapper to build the program as a JAR file.

Windows:
```
.\gradlew.bat jar
```

Linux/MacOS:
```
./gradlew jar
```

The `blahaj-finder.jar` file will be located in the `build/libs` directory.
## Running
### Browser
Run the `BlahajFinder.java` file in the `src/main/java/BlahajFinder` directory (ignore the `Main.java` file). To change the [Options](#options), edit the values of `"args"` in the `launch.json` file located in the `.vscode` directory.

### Local
Run the program using Java.
```
java -jar <path to JAR file> <options>
```
### Options
Options are passed as command line arguments. Although it is technically possible to run the program without options, you will probably want to pass options to the program.

There are two types of options: location and display.
#### Location
These options determine what location the program will use to search for  IKEA stores.
- `-c`/`--coordinates <coordinates>`: Coordinates to use to determine nearest IKEA store in demical degrees. Set to 0, 0 (0°N 0°E) if no coordinates are passed.
- `-s`/`--state <state(s)>`: Two-letter abbreviation of state(s) to search for stores in. Only IKEA stores located in specified state(s) will be displayed. IKEA stores in all states will be displayed if no state(s) are passed.

#### Display
These options determine what information the program will display.
- `-n`/`--number-of-stores <number>`: Number of stores to display. Set to 1 if no number is passed.
- `-a`/`--address`: Display address of store(s). Address will not be displayed if not passed.
- `-o`/`--opening-hours`: Display opening hours of store(s) and whether the store is current open or not. Opening hours will not be displayed if not passed.

See the [Examples](#Examples) for detailed examples of usage.
## Examples
### No Options
Running the program with no options gives this output (availability of shonks at the closest IKEA store to the default coordinates of 0°N 0°E)
![image](https://github.com/Tsundoiii/blahaj-finder/assets/91398247/19351e50-b626-4e84-b8e0-653b55e47f44)
If you don't live at 0°N, 0°E (which you probably don't considering it's in the middle of the ocean), you will probably want to pass some location options.
### Location Options
Let's say you're at the Georgia Aquarium and you want to know how many frens you can get the sharks there :>. The Georgia Aquarium is at 33°45'48.1"N 84°23'42.7"W, which in demical degrees is 33.763354, -84.395189. You can get decimal degrees by right-clicking on a place in Google Maps. You can pass the `-c` option to tell the program to search for the nearest IKEA store to those coordinates.
![image](https://github.com/Tsundoiii/blahaj-finder/assets/91398247/2e793d16-8b83-4775-af27-8f18afc3307e)
However, let's say you're going down to Florida (I'm not sure why you or anyone else would want to go there, especially if you're the type of person to like blåhajs, but whatever) and you want to know which IKEA store in Florida is closest to your current location. You can pass the `-s` option with the state's two-letter abbreviation (`FL`) to limit the result to only the closest store in Florida.
![image](https://github.com/Tsundoiii/blahaj-finder/assets/91398247/3c6956c5-c750-4f75-bd87-10391f780db6)
### Display Options
