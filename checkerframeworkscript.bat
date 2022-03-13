echo "" > log/checkerframework.log
mvn clean package -Djavaver=11 -Dcheckerframework=true --log-file log/checkerframework.log -P java8compile,checkerframework