Compile(){
	silent="$(mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt)"
	classpath="$(cat classpath.txt)"
	loglocation="$(realpath log)"
	silent="$(mvn clean compile -Dcompiletools=true)"
	rm classpath.txt
}

timestamp="$(date +%Y%m%dT%H%M%S)"
Compile
java -cp target/classes:$classpath nm.evaluatingnullcheckers.tools.BenchmarkSpace q
./run.sh -c allcheckers.txt -b allbenchmarks.txt -l log/log$timestamp
#Checking the log directory exists
if [[ -d log/log${timestamp} ]]
then
Compile
java -cp target/classes:$classpath nm.evaluatingnullcheckers.tools.CheckerOutputParser "$loglocation/log$timestamp" "$loglocation/log$timestamp/reports$timestamp.json"
echo "reports available at $loglocation/log$timestamp/reports$timestamp.json"
if [[ -f ${loglocation}/log${timestamp}/reports${timestamp}.json ]]
then
java -cp target/classes:$classpath nm.evaluatingnullcheckers.tools.CheckerEvaluator "$loglocation/log$timestamp/reports$timestamp.json" "$loglocation/log$timestamp/results$timestamp.json"
echo "results available at $loglocation/log$timestamp/results$timestamp.json"
if [[ -f ${loglocation}/log${timestamp}/results${timestamp}.json ]]
then
java -cp target/classes:$classpath nm.evaluatingnullcheckers.tools.ResultsOutputHandler $loglocation/log$timestamp/results$timestamp.json $loglocation/log$timestamp/results$timestamp.xlsx xlsx
echo "formatted results available at $loglocation/log$timestamp/results$timestamp.xlsx"
fi
fi
fi

