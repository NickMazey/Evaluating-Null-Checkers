bench_file=$(basename $1)
bench_name="${bench_file%.*}"
echo "" > log/nullaway/$bench_name.log
mvn clean compile -Dbenchmark=$1 -Dnullaway=true -P nullawayjava11 --log-file=log/nullaway/$bench_name.log