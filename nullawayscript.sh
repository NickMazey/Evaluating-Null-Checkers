bench_file=$(basename $1)
bench_name="${bench_file%.*}"
echo "" > log/nullaway/$bench_name.log
log_location=$2
mvn compile -Dbenchmark=$1 -Dnullaway=true -P nullawayjava11 --log-file=$log_location/nullaway/$bench_name.log
