# Introduction
The Grep application acts as the Linux command Grep. It is given a root directory and search 
all files in the folder and all sub-folders and search for matching strings. The application is tested 
using test cases on the main method and JUNIT testing, as well as deployment on docker. 

Technologies used:

Core Java, Maven, Stream API, Regex and Lambda


# Quick Start
The following arguments are required to run the application
* `regex_pattern`: the regex pattern to be searched
* `src_dir`: the root directory path
* `outfile`: the output file name

The Application can be run in two ways

1. Jar file
```
mvn clean package
java -jar target/grep-1.0-SNAPSHOT.jar ${regex_pattern} ${src_dir} ./out/${outfile}
```
2.Docker Image


#Implementation

## Pseudocode
```matchedLines = []
for file in listFilesRecursively(rootDir)
for line in readLines(file)
if containsPattern(line)
matchedLines.add(line)
writeToFile(matchedLines)
``` 

## Performance Issue
(30-60 words)
Discuss the memory issue and how would you fix it

# Test
How did you test your application manually? (e.g. prepare sample data, run some test cases manually, compare result)

# Deployment
How you dockerize your app for easier distribution?

# Improvement
List three things you can improve in this project.