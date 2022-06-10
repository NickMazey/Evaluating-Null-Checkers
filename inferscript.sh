bench_file=$(basename $1)
bench_name="${bench_file%.*}"
echo "" > log/infer/$bench_name.log
log_location=$2
rm -r infer-out
#Finding infer location for use in maven
infer_location=`which infer`
#To fix a weird bug where it says "java_class_info should be present for Java classes" while still working fine
silence_infer=$(infer run --quiet --debug --eradicate --pulse  -- mvn compile -Dbenchmark=$1 -Djavaver=11 -Dinferlocation=$infer_location -Dcheckerframework=false --log-file $log_location/infer/$bench_name.log)
#Suppresses output
sent=`cp -v infer-out/report.txt $log_location/infer/"$bench_name".inferreport`
