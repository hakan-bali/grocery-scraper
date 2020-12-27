## Grocery Scraper
This project has been developed due to the detailed explanation given below:
https://jsainsburyplc.github.io/serverside-test/

### Pre-requisites for the project
[Install](https://www.oracle.com/uk/java/technologies/javase/jdk11-archive-downloads.html) JDK version 11.0.7

[Install](https://gradle.org/install/) Gradle version 6.6

[Install](https://git-scm.com/downloads) Git version 2.24.3

### A fresh startup for the project
Go to any directory you'd like to create the project.
```
$ cd <ANY_DIRECTORY>
```
Clone the project from GitHub repository. Project files will be placed under <ANY_DIRECTORY>/grocery-scraper
```
$ git clone https://github.com/hakan-bali/grocery-scraper.git
```
Proceed to the project directory.
```
$ cd grocery-scraper
```
Gradle will download all required dependencies, build the project, and run the project with the command below:
```
$ ./gradlew run
```
### Clean build whole project from command line
```
$ ./gradlew clean build
```

### Check that it is running
```
$ ./gradlew run
```

### Running unit tests from command line
```
$ ./gradlew test
```

### Command line options

#### Usage:
```
$ ./gradlew run --args='[-cfhVx] [-n FILENAME]' 
```
#### Options:
```
-c              JSon output will be redirected to console. Can be used with the '-f' option.
```
```
-f              JSon output will be redirected to 'output.json' file. Can be used with the '-c' option.
```
```
-h, --help      Show this help message and exit.
```
```
-n  FILENAME    Given <FILENAME> is used instead of default 'output.json'.
```
```
-V, --version   Print version information and exit.
```
```
-x              Output file is removed before output is saved.
```
#### Examples:
JSon output will be redirected to console only.
```
$ ./gradlew run --args='-c' 
```
JSon output will be redirected to 'output.json' file only.
```
$ ./gradlew run --args='-f' 
```
JSon output will be redirected to both console and 'output.json' file. 'output.json' file is removed if already exists.
```
$ ./gradlew run --args='-cf' 
```
JSon output will be redirected to 'MyOut.JSon' file only.
```
$ ./gradlew run --args='-nMyOut.JSon' 
```
JSon output will be redirected to both console and 'GroceryOut.json' file. If 'GroceryOut.json' already exists, output will be redirected to console only.
```
$ ./gradlew run --args='-cfn GroceryOut.json' 
```
JSon output will be redirected to both console and 'GroceryOut.json' file. If 'GroceryOut.json' already exists, it is removed first.
```
$ ./gradlew run --args='-cfxn GroceryOut.json' 
```
