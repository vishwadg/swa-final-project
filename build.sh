#!/usr/bin/env bash

folders=(
  authentication-service
  notification-service
  reservation-service
  student-service
  tutor-requirement-search-service
  tutor-requirement-service
  tutor-service
)

for folder in ${folders[@]}
 do
    docker build . --build-arg build_folder=${folder} -t "vishwaghimire/${folder}:latest"
done