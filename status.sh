#!/bin/bash

netstat -nltp | grep -E "8080|8081|8082|8083|8091|8092"
