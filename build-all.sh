#!/bin/bash

cd prime-number-server && ./gradlew clean build && cd ..
cd prime-number-proxy-service && ./gradlew clean build && cd ..

LIGHT_GREEN='\033[1;32m'
NC='\033[0m' # No Color
echo -e "${LIGHT_GREEN}prime-number-server and proxy-service have been built successfully!${NC}"
