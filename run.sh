#!/bin/bash
Help()
{
    echo "usage: run [-c checkerlistfile] [-b benchmarklistfile] [-l logoutputfolder]"
}

#Runs checkers
RunChecker()
{
    mkdir -p $LOGLOCATION/$CHECKER
    ./scripts/${CHECKER}script.sh $BENCHMARK $LOGLOCATION
}

while getopts ":c:b:l:" option; do
    case $option in
        c)
            CHECKERLISTFILE="$OPTARG"
            ;;
        b)
            BENCHMARKLISTFILE="$OPTARG"
            ;;
        l)
       	    LOGLOCATION="$OPTARG"
       	    ;;
        \?)
            Help
    esac
done

IterateThroughCheckers(){
    for CHECKER in $CHECKERS
    do
    if [[ -f scripts/${CHECKER}script.sh ]]
    then
    mvn clean -q
    echo "($CHECKER)"
    BENCHINDEX=0
    for BENCHMARK in $BENCHMARKS
    do
    #Compiles benchmarks in parallel
    #From testing, it has no speed improvement and makes the reported maven compile times the same for every class
    #Will probably look into this more in the future, because it seems like a good way to speed up the benchmarking process
    #if [[ $CHECKER != "infer" ]]
    #then
    #RunChecker &
    #else
    #RunChecker
    #fi
    BENCHINDEX=$(($BENCHINDEX+1))
    echo -ne "Evaluating Benchmark $BENCHINDEX of $BENCHNUM ($((($BENCHINDEX*100)/($BENCHNUM)))%)\r"
    RunChecker
    
    done
    echo -ne "\n"
    else
    echo "Exec script not found for ${CHECKER}"
    fi
    wait
    done
}


if [ $OPTIND -eq 1 ]
then 
    Help
else
    if [ -z $CHECKERLISTFILE ]; then echo "No checker list specified, exiting"; exit 0; fi
    if [ -z $BENCHMARKLISTFILE ]; then echo "No benchmark list specified, exiting"; exit 0; fi
    if [ -z $LOGLOCATION ]; then echo "NO log location specified, exiting"; exit 0; fi
    CHECKERS=$(cat $CHECKERLISTFILE)
    BENCHMARKS=$(cat $BENCHMARKLISTFILE)
    BENCHNUM=0
    for BENCHMARK in $BENCHMARKS
    do
    BENCHNUM=$((BENCHNUM + 1))
    done
    OSVERSION=`uname -r`
    echo "Running on:"
    echo "$OSTYPE ($OSVERSION)"
    echo "Testing the checkers:"
    echo $CHECKERS
    echo "With the benchmarks in file:"
    echo $BENCHMARKLISTFILE
    IterateThroughCheckers 
fi
