#!/usr/bin/env bash

folders=(
  authentication-service
)

for folder in ${folders[@]}
 do
    docker build --build-arg build_folder=${folder} -t "vishwaghimire/${folder}:latest"
done