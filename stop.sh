#!/bin/bash

OUR_PID=$(sudo netstat -nltp | grep 8080 | awk '{print $7}' | awk -F/ '{print $1}')
echo $OUR_PID
kill $OUR_PID

OUR_PID=$(sudo netstat -nltp | grep 8081 | awk '{print $7}' | awk -F/ '{print $1}')
echo $OUR_PID
kill $OUR_PID

OUR_PID=$(sudo netstat -nltp | grep 8082 | awk '{print $7}' | awk -F/ '{print $1}')
echo $OUR_PID
kill $OUR_PID

OUR_PID=$(sudo netstat -nltp | grep 8083 | awk '{print $7}' | awk -F/ '{print $1}')
echo $OUR_PID
kill $OUR_PID

OUR_PID=$(sudo netstat -nltp | grep 8091 | awk '{print $7}' | awk -F/ '{print $1}')
echo $OUR_PID
kill $OUR_PID

OUR_PID=$(sudo netstat -nltp | grep 8092 | awk '{print $7}' | awk -F/ '{print $1}')
echo $OUR_PID
kill $OUR_PID
