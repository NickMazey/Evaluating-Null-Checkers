bench_file=$(basename $1)
bench_name="${bench_file%.*}"
echo "" > log/infer/$bench_name.log

rm -r infer-out
#Finding infer location for use in maven
infer_location=`which infer`
infer run --quiet --debug --eradicate --pulse  -- mvn compile -Dbenchmark=$1 -Djavaver=11 -Dinferlocation=$infer_location -Dcheckerframework=false --log-file log/infer/$bench_name.log
#Suppresses output
sent=`cp -v infer-out/report.txt log/infer/"$bench_name"_report.txt`