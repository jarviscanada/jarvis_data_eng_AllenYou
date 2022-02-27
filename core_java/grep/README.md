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
```aidl
docker pull allencyou/grep
docker run --rm -v `pwd`/data:/data -v `pwd`/out:/out allencyou/grep ${regex_pattern} ${src_dir} /out/${outfile}
```

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
As the application deals with progressively large files, the application outputs an OutOfMemoryError
exception if the file is larger than the heap of the JVM. The List data structure easily gets extremely
large if a file contains to many lines to process. The solution in the application was to use 
Stream APIs, rather than List datatype. Streams do not store data and allows for elements to be
computed on demand, allowing for memory to be saved in large sizes.

# Test


# Deployment
A Docker image was created and distributed to the Docker Hub for easy access. This can be viewed on publicly 
on https://hub.docker.com/r/allencyou/grep or using the command ``docker pull allencyou/grep.``


# Improvement
List three things you can improve in this project.