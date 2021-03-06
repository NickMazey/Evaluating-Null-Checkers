timestamp="$(date +%Y%m%dT%H%M%S)"
./run.sh -c allcheckers.txt -b allbenchmarks.txt -l log/log$timestamp
#This isn't proper but it works
silent="$(mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt)"
classpath="$(cat classpath.txt)"
loglocation="$(realpath log)"
silent="$(mvn clean compile -Dcompiletools=true)"
java -cp target/classes:$classpath nm.evaluatingnullcheckers.tools.CheckerOutputParser "$loglocation/log$timestamp" "$loglocation/log$timestamp/reports$timestamp.json"
java -cp target/classes:$classpath nm.evaluatingnullcheckers.tools.CheckerEvaluator "$loglocation/log$timestamp/reports$timestamp.json" "$loglocation/log$timestamp/results$timestamp.json"
echo "reports available at $loglocation/log$timestamp/reports$timestamp.json"
echo "results available at $loglocation/log$timestamp/results$timestamp.json"

rm classpath.txt

