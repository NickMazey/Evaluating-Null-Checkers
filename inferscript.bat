bench_file=$(basename $1)
bench_name="${bench_file%.*}"
echo "" > log/infer/$bench_name.log
infer run --quiet --debug --eradicate --pulse -- mvn clean compile -Dbenchmark=$1 -Djavaver=11 -Dcheckerframework=false --log-file log/infer/$bench_name.log
cp -v infer-out/report.txt log/infer/"$bench_name"_report.txt