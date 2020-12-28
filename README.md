# Overview

This project has been developed with Java 1.7 source code compatibility due to the detailed explanation given below:
https://jsainsburyplc.github.io/serverside-test/
-----
## Pre-requisites for the project
[Install](https://www.oracle.com/uk/java/technologies/javase/javase8u211-later-archive-downloads.html) JDK version 1.8+

[Install](https://gradle.org/install/) Gradle version 6.6

[Install](https://git-scm.com/downloads) Git version 2.24.3

## A fresh startup for the project
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
---
### Clean build whole project from command line
```
$ ./gradlew clean build
```
---
### Running unit tests from command line
```
$ ./gradlew test
```
---
### Check that it is running
```
$ ./gradlew run
```
-----
### Command line options

#### Usage:
```
$ ./gradlew run --args='[-cfhVx] [-n FILENAME] [-u SOURCE_URL]' 
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
-u  SOURCE_URL  Given <SOURCE_URL> is used instead of default one given in the task description.
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
-----
### Requirements Compliance Matrix
| # | Functional Requirements | Compliance |
| --- | --- | --- |
| 1 | Build a Java console application | Done |
| 2 | Scrape the Sainsbury’s grocery site’s - Berries, Cherries, Currants page  | Done|
| 3 | Return a JSON array of all the products on the page | Done |
| 4 | Follow each product’s link to get the calories per 100g (in kcal). | Done |
| 5 | Follow each product’s link to get the long description. | Done |
| 6 | Each element in the JSON results array should contain "title", "unit_price", "kcal_per_100g" and "description" keys corresponding to items in the HTML. | Done |
| 7 | Do not include cross sell items already available with the product. | Done |
| 8 | Omit the kcal_per_100g field, if calories are unavailable. | Done |
| 9 | If the description is spread over multiple lines, scrape only the first line. | Done: All lines scraped |
| 10 | Show unit price and total up to 2 decimal places, representing pounds and whole pence. | Done |
| 11 | Additionally, include a "total" field following the results in the JSon. | Done |
| 12 | The "total" field contains two sub-fields: "gross" and "vat".  | Done |
| 13 | The "gross" field will be the total price of all the items on the page. | Done |
| 14 | The "vat" will be the VAT on the gross amount. | Done |
| 15 | Every item has 20% VAT which is already included in the price. | Done |

| # | Non-Functional Requirements | Compliance |
| --- | --- | --- |
| 1 | Code should be written in Java 1.7+ | Done |
| 2 | Application will be tested on a Unix-based terminal and ran in Java 1.8 | Done |
| 3 | The choice of tools, libraries and frameworks used to develop the application is left open. | Done |
-----

### Libraries Used to Develop the Application
| Library Name | Version |
| --- | --- |
| Jackson for JSon serialization | [2.12](https://github.com/FasterXML/jackson-core) |
| JSoup for web scraping | [1.13.1](https://jsoup.org/) |
| JUnit5 for unit testing | [5.7.0](https://junit.org/junit5/) |
| Lombok for boilerplate code generation | [1.18.16](https://projectlombok.org/) |
| Mockito for unit test mocking | [3.6.28](https://site.mockito.org/) |
| PicoCli for command line interface | [4.5.2](https://picocli.info/) |