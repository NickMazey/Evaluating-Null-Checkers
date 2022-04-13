echo "" > log/nullaway.log
mvn clean compile -Dnullaway=true -P nullawayjava8 --log-file=log/nullaway.log