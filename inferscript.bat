echo "" > log/infer.log
infer run --eradicate --pulse -- mvn clean compile -Djavaver=11 -Dcheckerframework=false --log-file log/infer.log