echo "" > log/default.log
mvn clean package -Djavaver=11 -Dcheckerframework=false --log-file log/default.log