bench_file=$(basename $1)
bench_name="${bench_file%.*}"
log_location=$2
echo "" > $log_location/checkerframework/$bench_name.log
start_seconds=$( date '+%s' )
start_nanos=$( date '+%N' )
mvn compile -Dbenchmark=$1 -Djavaver=11 -Dcheckerframework=true --log-file $log_location/checkerframework/$bench_name.log -P java11compile,checkerframework
end_seconds=$( date '+%s' )
end_nanos=$( date '+%N' )
./calctime.sh $start_seconds $start_nanos $end_seconds $end_nanos > $log_location/checkerframework/$bench_name.time
