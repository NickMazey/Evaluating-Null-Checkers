echo "" > log/default.log
mvn clean compile -Djavaver=11 -Dcheckerframework=false --log-file log/default.log