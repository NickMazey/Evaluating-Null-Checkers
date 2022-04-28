#!/bin/bash
Help()
{
    echo "usage: run [-c checkerlistfile] [-b benchmarklistfile]"
}

#Runs checkers
RunChecker()
{
    shopt -s nocasematch
    if [[ $CHECKER == "checkerframework" ]]
    then
    mkdir -p log/checkerframework
    ./checkerframeworkscript.sh $BENCHMARK
    fi
    if [[ $CHECKER == "infer" ]]
    then
    mkdir -p log/infer
    ./inferscript.sh $BENCHMARK
    fi
    if [[ $CHECKER == "nullaway" ]]
    then
    mkdir -p log/nullaway
    ./nullawayscript.sh $BENCHMARK
    fi
}

while getopts ":c:b:" option; do
    case $option in
        c)
            CHECKERLISTFILE="$OPTARG"
            ;;
        b)
            BENCHMARKLISTFILE="$OPTARG"
            ;;
        \?)
            Help
    esac
done



if [ $OPTIND -eq 1 ]
then 
    Help
else
    if [ -z $CHECKERLISTFILE ]; then echo "No checker list specified, exiting"; exit 0; fi
    if [ -z $BENCHMARKLISTFILE ]; then echo "No benchmark list specified, exiting"; exit 0; fi
    CHECKERS=$(cat $CHECKERLISTFILE)
    BENCHMARKS=$(cat $BENCHMARKLISTFILE)
    for CHECKER in $CHECKERS
    do
    for BENCHMARK in $BENCHMARKS
    do
            RunChecker
    done
    done

fi