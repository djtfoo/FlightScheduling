@echo off
title Example Class 4B: Flight Scheduling
java -Djava.compiler=NONE App "routedata/routeslargest.csv" "graphs/routes_short.csv" "vary_edges/14cities/20edges.csv" "vary_edges/14cities/54edges.csv" "vary_edges/14cities/91edges.csv" "vary_edges/30cities/45edges.csv" "vary_edges/30cities/130edges.csv" "vary_edges/30cities/215edges.csv" "vary_vertices/20vertices.csv" "vary_vertices/60vertices.csv" "vary_vertices/100vertices.csv"
pause