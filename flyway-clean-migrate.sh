#!/usr/bin/env bash
./gradlew -i sample-account:flywayClean sample-account:flywayMigrate
./gradlew -i sample-backend:flywayClean sample-backend:flywayMigrate