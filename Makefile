#!/bin/sh

build:
	echo "Build app"
	./gradlew build

run:
	echo "Running app locally on port 8080"
	rm -f data/db.mv.db && ./gradlew bootRun

test:
	echo "Running automated test suite"
	rm -f data/testdb.mv.db && ./gradlew test