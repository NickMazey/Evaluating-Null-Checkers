bench_file=$(basename $1)
bench_name="${bench_file%.*}"
log_location=$2
echo "" > $log_location/infer/$bench_name.log
rm -r -f infer-out
#Finding infer location for use in maven
infer_location=`which infer`
start_seconds=$( date '+%s' )
start_nanos=$( date '+%N' )
#To fix a weird bug where it says "java_class_info should be present for Java classes" while still working fine
infer run --quiet --debug --no-default-checkers --eradicate --pulse  -- mvn compile -Dbenchmark=$1 -Djavaver=11 -Dinferlocation=$infer_location -Dcheckerframework=false --log-file $log_location/infer/$bench_name.log
end_seconds=$( date '+%s' )
end_nanos=$( date '+%N' )
./scripts/calctime.sh $start_seconds $start_nanos $end_seconds $end_nanos > $log_location/infer/$bench_name.time
#Suppresses output
sent=`cp -v infer-out/report.txt $log_location/infer/"$bench_name".inferreport`
