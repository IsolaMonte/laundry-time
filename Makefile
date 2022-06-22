#!/bin/sh

.PHONY: build
build:
	echo "Build app"
	rm -f data/db.mv.db && rm -f data/testdb.mv.db && ./gradlew build

.PHONY: run
run:
	echo "Running app locally on port 8080"
	rm -f data/db.mv.db && ./gradlew bootRun

.PHONY: test
test:
	echo "Running automated test suite"
	rm -f data/db.mv.db && rm -f data/testdb.mv.db && ./gradlew cleanTest test