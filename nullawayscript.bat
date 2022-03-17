echo "" > log/nullaway.log
mvn clean package -Dnullaway=true -P nullawayjava8 --log-file=log/nullaway.log