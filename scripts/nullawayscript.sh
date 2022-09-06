bench_file=$(basename $1)
bench_name="${bench_file%.*}"
log_location=$2
echo "" > $log_location/nullaway/$bench_name.log
start_seconds=$( date '+%s' )
start_nanos=$( date '+%N' )
mvn compile -Dbenchmark=$1 -Dnullaway=true -P nullawayjava11 --log-file=$log_location/nullaway/$bench_name.log
end_seconds=$( date '+%s' )
end_nanos=$( date '+%N' )
./scripts/calctime.sh $start_seconds $start_nanos $end_seconds $end_nanos > $log_location/nullaway/$bench_name.time
