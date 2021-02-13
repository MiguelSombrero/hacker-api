# Hacker API

![GitHub Actions](https://github.com/MiguelSombrero/hacker-api/workflows/Java%20CI%20with%20Maven/badge.svg)

Hacker API reads Google Sheets API, parses Java objects and return them as JSON response.

## Endpoints

### `GET /api/studies/books`

Finds all Books.

#### Google Sheet for `Book` objects

Books (both audio and regular) is parsed from Google sheets that contains book reviews.
Example spreadsheet can be <MISSING>

#### Example response

    [
        {
            "id": 783879955,
            "name": "Clean Code",
            "type": {
                "name": "Paperiversio"
            },
            "authors": "Robert, C. Martin",
            "rating": 4.5,
            "reviews": [
                {
                    "id": 915924271,
                    "created": "2019-09-12T08:46:07",
                    "review": "Very goog book",
                    "rating": 5,
                    "reviewer": {
                        "id": 1649854152,
                        "firstname": "Miika",
                        "lastname": "Somero",
                        "skills": [],
                        "projects": []
                    }
                },
                {
                    "id": 2024246544,
                    "created": "2019-09-12T08:46:07",
                    "review": "Awesome book!",
                    "rating": 4,
                    "reviewer": {
                        "id": 1368847203,
                        "firstname": "Kimi",
                        "lastname": "Raikkonen",
                        "skills": [],
                        "projects": []
                    }
                }
            ],
            "pages": 434
        }
    ]
    
### `GET /api/studies/books/reviews`

Finds all Book Reviews.

#### Google Sheet for `Review` objects

Same is with `Books` objects.

#### Example response

    [
        {
            "id": 2024715031,
            "created": "2021-01-03T11:59:07",
            "review": "Huippu kirja!",
            "rating": 4,
            "reviewer": {
                "id": 1754187377,
                "email": testi@testi.fi,
                "firstName": "Juho",
                "lastName": "Testinen",
                "skills": [],
                "projects": []
            },
            "book": {
                "id": 880471245,
                "rating": 0.0,
                "reviews": [],
                "authors": "Jari Litmanen",
                "name": "Litmanen 10",
                "type": {
                    "name": "Äänikirja"
                },
                "duration": 580
            },
            "course": null
        }    
    ]

### `POST /api/studies/books/reviews`

Creates new Book Review.

#### Example request body

    {
	    "review": "Tosi hyvä kirja - jippii",
	    "rating": 5,
	    "reviewer": {
		    "email": "miika.somero@testi.fi"
	    },
	    "book": {
		    "type": "Äänikirja",
		    "name": "Geenin Itsekkyys",
		    "duration": 620,
		    "authors": "Dawkins, Richard"
	    }
    }

### `GET /api/studies/courses`

Finds all Courses.

#### Google Sheet for `Course` objects

Same as with `Books` objects.

#### Example response

    [
        {
            "id": 2050048570,
            "rating": 5.0,
            "reviews": [
              {
                "id": 1499053504,
                "created": "2019-08-11T09:35:05",
                "review": "Hyvä kurssi",
                "rating": 5,
                "reviewer": {
                  "id": 980821762,
                  "firstname": "Kimmo",
                  "lastname": "Testinen",
                  "skills": [],
                  "projects": []
                },
                "book": null,
                "course": null
              }
            ],
            "duration": 4200,
            "name": "React 16 – The Complete Guide"
          },
    ]

### `GET /api/studies/courses/reviews`

Finds all Course Reviews.

#### Google Sheet for `Review` objects

Same as with `Books` objects.

#### Example response

    [
        {
            "id": 1923889721,
            "created": "2020-12-31T20:48:29",
            "review": "Huippu!",
            "rating": 4,
            "reviewer": {
              "id": 173153942,
              "email": testi@testi.fi,
              "firstName": "Henna",
              "lastName": "Testinen",
              "skills": [],
              "projects": []
            },
            "book": null,
            "course": {
              "id": 1786177975,
              "rating": 0.0,
              "reviews": [],
              "duration": 77,
              "name": "Microsoft Azure Developer: Develop Solutions With Blob Storage"
            }
          }
    ]

### `/api/projects/hackers`

Find all Hackers in projects.

#### Google Sheet for `Hacker` objects

Hackers is parsed from Google sheet that contains project information for every hacker (i.e. employee).
Example spreadsheet can be found <MISSING>

#### Example response

    [
        {
            "id": 1721384955,
            "firstname": "Miika",
            "lastname": "Somero",
            "skills": [
                {
                    "id": 85831,
                    "name": "SQL",
                    "knowHowMonths": 19
                },
                {
                    "id": 2304987,
                    "name": "Java",
                    "knowHowMonths": 54
                },
                {
                    "id": 2052351961,
                    "name": "Docker",
                    "knowHowMonths": 16
                }
            ],
            "projects": [
                {
                    "id": 649852067,
                    "name": "Verkkokauppa",
                    "client": "Risma",
                    "employer": "Alfame Systems Oy",
                    "role": {
                        "id": 836599805,
                        "name": "Sovelluskehittäjä",
                        "tasks": [
                            "Toteutus",
                            "Määritys"
                        ]
                    },
                    "start": "01.02.2020",
                    "end": "01.12.2020",
                    "description": "Toteutetaan verkkokauppa"
                },
                {
                    "id": 2119589892,
                    "name": "Eläkeuudistus 2017",
                    "client": "Eläkekassat- ja säätiöt",
                    "employer": "Testityöpaikka",
                    "role": {
                        "id": 1564909434,
                        "name": "Määrittelijä, Testaaja",
                        "tasks": [
                            "Määrittely",
                            "Testaus"
                        ]
                    },
                    "start": "01.05.2015",
                    "end": "01.04.2017",
                    "description": "Toteutetaan eläkelainsäädännön muutoksesta johtuvat muutokset eläkkeen laskentaan"
                }
            ]
        }
    ]

## Project requirements

### Google credentials

Hacker API uses Google Sheets API which requires authentication.
To use Hacker API you need Google service account, or some other way to authenticate.

Save your service account credentials in `/resources/google-credentials.json`.
Class `GoogleAuthorizationUtil` reads credentials from that file and authenticates requests for Google Sheets API.

### Google Spreadsheets

Create spreadsheets that corresponds to examples shown above.
Give writing rights to spreadsheets for your service account.

### Update application-{environment}.properties files

Update your application properties files to match your newly created spreadsheets.

Properties ending `.spreadsheet` correspond to a spreadsheet id and properties ending `.sheet` to sheet name and range.

In development, you only have to update file `application-development.properties`.

## Available commands

### Clone project

To clone application

    git clone git@github.com:MiguelSombrero/hacker-api.git

### Build project

    mvn install
    
### Run project in development mode

By default, application runs on development mode so no need to specify active profile

    mvn spring-boot:run

### Run project in test mode

To run application in test mode (needed for integration testing with hacker-UI)

    SPRING_PROFILES_ACTIVE=test mvn spring-boot:run
    
### Run project in production mode

To run application in production mode

    SPRING_PROFILES_ACTIVE=production mvn spring-boot:run

### Unit tests

    mvn test
