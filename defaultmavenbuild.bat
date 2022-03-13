echo "" > log/default.log
mvn clean package -Dcheckerframework=false --log-file log/default.log