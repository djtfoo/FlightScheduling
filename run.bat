@echo off
title Example Class 4B: Flight Scheduling
java -Djava.compiler=NONE App "routedata/routeslargest.csv" "graphs/routes_short.csv" "finalgraphs/small/20.csv" "finalgraphs/small/40.csv" "finalgraphs/small/60.csv" "finalgraphs/small/80.csv" "finalgraphs/small/100.csv"
pause