echo "" > log/infer.log
infer run -a checkers --eradicate -- mvn clean package -Djavaver=11 -Dcheckerframework=false --log-file log/infer.log