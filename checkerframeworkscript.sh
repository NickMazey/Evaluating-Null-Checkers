bench_file=$(basename $1)
bench_name="${bench_file%.*}"
echo "" > log/checkerframework/$bench_name.log
mvn clean compile -Dbenchmark=$1 -Djavaver=11 -Dcheckerframework=true --log-file log/checkerframework/$bench_name.log -P java11compile,checkerframework