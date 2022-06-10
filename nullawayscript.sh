bench_file=$(basename $1)
bench_name="${bench_file%.*}"
log_location=$2
echo "" > "$log_location/nullaway/$bench_name.log"
mvn compile -Dbenchmark="$1" -Dnullaway=true -P nullawayjava11 --log-file="$log_location/nullaway/$bench_name.log"
