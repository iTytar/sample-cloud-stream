@echo off
set CMD="echo '{\"id\":\"1ec39056-b7b7-46bf-b7a5-0e0df3c95f28\", \"personId\":\"2ec39056-b7b7-46bf-b7a5-0e0df3c95f28\", \"accountId\":\"3ec39056-b7b7-46bf-b7a5-0e0df3c95f28\"}' | /bin/kafka-console-producer --broker-list localhost:9092 --topic customers"
set CONTAINER=sample-cloud-stream_kafka_1
echo Execute in container '%CONTAINER%' %CMD%
docker exec -it %CONTAINER% sh -c %CMD%
