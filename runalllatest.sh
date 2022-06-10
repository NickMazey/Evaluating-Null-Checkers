tail -1 allbenchmarks.txt > latestbenchmark.txt
./run.sh -c allcheckers.txt -b latestbenchmark.txt -l log/log-"$(date +%Y%m%dT%H%M%S)"
