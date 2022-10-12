bench_file=$(basename $1)
bench_name="${bench_file%.*}"
log_location=$2
echo "" > $log_location/checkerframework/$bench_name.log
mvn compile -Dbenchmark=$1 -Djavaver=11 -Dcheckerframework=true --log-file $log_location/checkerframework/$bench_name.log -P java11compile,checkerframework
