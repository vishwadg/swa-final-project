#!/usr/bin/env bash

folders=(
  authentication-service
)

for folder in ${folders[@]}
 do
#    docker build . --build-arg build_folder=${folder} -t "vishwaghimire/${folder}:latest"
     docker build -t "vishwaghimire/${folder}:latest" --build-arg build_folder=${folder} .
done