# The Prompt [![Build Status](https://travis-ci.org/jasonckok/jasonckok-prompt.svg?branch=master)](https://travis-ci.org/jasonckok/jasonckok-prompt)

A console app that interacts with [Bing News Search](https://www.microsoft.com/en-us/bing/apis/bing-news-search-api) API and displays the 20 latest World News Headlines to the user.
The app also display a tally of the 20 latest headlines' news providers.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
Note: The subscription key will expire in 7 days (Azure trial account). To use your own key, please edit the file api_key.txt

### Setup

Clone the project:

```
$ git clone https://github.com/jasonckok/jasonckok-prompt.git
```

The project is already build so to run the project, just run this command in CLI:

```
java -cp target/jasonckok-prompt-0.0.1-SNAPSHOT.jar prompt
```

And you will get the results in the console (as of 2/7/2019 8:41 PM EST):

<img src="https://github.com/jasonckok/jasonckok-prompt/blob/master/screenshots/bing_results.PNG">

If you wish to build the project yourself such as after changing the subscription key, ensure you have [Maven](https://maven.apache.org/index.html) on your machine then run this command:

```
mvn package
```

Bunch of magic happens (maven will download all the dependencies, run the test scripts, and build the program)
You will see this at the end:

<img src="https://github.com/jasonckok/jasonckok-prompt/blob/master/screenshots/package_results.PNG">

### Execute Tests

Run the command:

```
mvn test
```

You should get the following output in the console:

<img src="https://github.com/jasonckok/jasonckok-prompt/blob/master/screenshots/test_results.PNG">

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Travis CI](https://travis-ci.org) - Continuous Integration/Delivery

## Tools Used

* [Git](https://git-scm.com/) - Git
* [Eclipse](https://www.eclipse.org/) - IDE
* [Postman](https://www.getpostman.com/) - API Development and Testing

## Author

* **Jason Kok** - [github.com/jasonckok](https://github.com/jasonckok)
