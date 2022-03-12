echo "" > log/checkerframework.log
mvn clean package --log-file log/checkerframework.log -P java8compile,checkerframework