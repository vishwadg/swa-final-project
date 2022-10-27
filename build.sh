#!/usr/bin/env bash

#authentication-service
#tutor-service
#student-service
#reservation-service
#tutor-requirement-search-service
#tutor-requirement-service
#notification-service

folders=(
  authentication-service
  tutor-service
  student-service
)

for folder in ${folders[@]}
 do
      docker build . --build-arg build_folder=${folder} -t "vishwaghimire/${folder}:latest"
done