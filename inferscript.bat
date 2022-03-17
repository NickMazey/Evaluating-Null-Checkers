echo "" > log/infer.log
infer run --eradicate-only -- mvn clean package -Djavaver=11 -Dcheckerframework=false --log-file log/infer.log