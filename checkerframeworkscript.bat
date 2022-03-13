echo "" > log/checkerframework.log
mvn clean package -Dcheckerframework=true --log-file log/checkerframework.log -P java8compile,checkerframework