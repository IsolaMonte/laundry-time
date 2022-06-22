# Laundry Booking Service

## Requirements
- 20 households
- 2 laundry rooms, located on Maple Street 3 and Maple Street 5
- Laundry rooms are bookable between 7 am and 10 pm daily = 5 three hours slots

## How to build/compile
```bash
make build
```

## How to run locally
```bash
make run
```

This will start the application locally with a TomCat webserver and a file based H2 database called `db.mv.db` located under `data/`.
The db is, for ease of use and testing purposes, pre-populated with some data, see `data.sql` under `src/main/resources`.

The Makefile simply wraps gradle and allows we to delete/meddle with the H2 db files before starting the app locally or running the test suite.

When running locally the REST API can be tested with `cURL`, example calls below:

Create a booking (if run twice will throw `TIMESLOT_UNAVAILABLE`)
```bash
curl -X POST --location "http://localhost:8080/bookings" -H "Content-Type: application/json" \
-d '{ "user": "kim", "date": "2022-10-12", "timeslot": "MORNING", "laundry_room": "MAPLE_STREET_3" }'
```

Fetch all bookings
```bash
curl -X GET --location "http://localhost:8080/bookings"
```

Fetch a booking by id
```bash
curl -X GET --location "http://localhost:8080/bookings/<id>"
```

## How to test
```bash
make test
```

This will run the automated test suites. The Makefile simply wraps gradle and allows we to delete/meddle with the H2 db files before starting the app locally or running the test suite.

As mentioned below the tests break when run more than once via ./gradlew or in IntelliJ, because of how the pre-population is done. If so, just delete `testdb.mv.db` and re-run the tests, or use the makefile command.


## Improvements
- Database should be a Dockerized replica of production (Postgres for example) instead of file based H2
(`docker-compose` would then be a pre-req for the persistence layer tests).
- With current DB setup the `data.sql` pre-population is run on each test invocation,
which breaks the `checkForBookingClash` test since the SQL injection circumvents the clash-check in the BookingService.
This is right now fixed by deleting the test db between test runs (ugly hack).
- In order to fix the db pre-population problem, create and persist new "random" bookings on each run of the test suite
(dynamic tests data FTW), i.e. create a good generator for the data class `Booking`.
- With good generators for the domain types we could also add property based testing.
- Users should be authenticated, i.e. instead of passing usernames as arg in the request body,
they should be fetched from auth context in the RestController.