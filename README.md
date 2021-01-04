# Hacker API

Hacker API serves `Book` (/api/books) and `Hacker` (/api/hackers) objects by API endpoints.

Hacker API uses Google API Client for fetching data from Google Sheets, parses this data to `Book` and `Hacker` objects and sends a JSON response to caller.  

## In production

Hacker API and Hacker UI is running in Heroku:


## `/api/books`

### Google Sheet for `Book` objects

Books (both audio and regular) is parsed from Google sheets that contains book reviews.
Example spreadsheet can be found [here](https://docs.google.com/spreadsheets/d/1iken0UPCQ9jHCkJxeRzZRIA8T7PBVZ9Fo4FrclE0pVs/edit#gid=483319555)

### Example response

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
                    "created": [
                        2019,
                        6,
                        25,
                        7,
                        37,
                        17
                    ],
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
                    "created": [
                        2019,
                        8,
                        12,
                        8,
                        56,
                        22
                    ],
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
    
## `/api/hackers`

### Google Sheet for `Hacker` objects

Hackers is parsed from Google sheet that contains project information for every hacker (i.e. employee).
Example spreadsheet can be found [here](https://docs.google.com/spreadsheets/d/13hvpwT57SZ7MpnsTlNVLJmmSzfYbgntTwypWdcNLV34/edit#gid=0)

### Example response

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

## Requirements

Hacker API uses Google Sheets API which requires authentication. To use Hacker API you need to do the following

### Create credentials

Create new project in [Google Developer Console](https://console.developers.google.com/) and enable Google Sheets API to that.

Add service account credentials to you project and a new key. Upload credentials to you machine.

Save your service account credentials in `/resources/secrets.json`.
Class `GoogleAuthorizationUtil` reads credentials from that file and authenticates requests for Google Sheets API.

### Create spreadsheets

Create spreadsheets that corresponds to examples shown above.
Give viewing rights to spreadsheets for your service account.

### Update application-{environment}.properties files

Update your application properties files to match your newly created spreadsheets.

Properties ending `.spreadsheet` correspond to a spreadsheet id and properties ending `.sheet` to sheet name and range.

In development, you only have to update file `application-development.properties`.

## Available scripts

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