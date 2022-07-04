#For calculating execution time of checkers
start_seconds=($1)
start_nanos=($2)
end_seconds=($3)
end_nanos=($4)
time_seconds=$((end_seconds-start_seconds))
start_ms=${start_nanos:0:3}
end_ms=${end_nanos:0:3}
if (( 10#$end_ms < 10#$start_ms ))
then
	difference=$(( 10#$start_ms- 10#$end_ms ))
	time_seconds=$(( 10#$time_seconds - 10#1 ))
	time_ms=$(( 10#1000 - 10#$difference ))
else
	time_ms=$(( 10#$end_ms-10#$start_ms ))
fi
seconds_ms=$(( 10#$time_seconds * 10#1000))
time_ms=$(( 10#$time_ms + 10#$seconds_ms ))
echo $time_ms
