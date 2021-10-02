@echo off
set CMD="/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic persons --from-beginning"
set CONTAINER=sample-cloud-stream_kafka_1
echo Execute in container '%CONTAINER%' %CMD%
docker exec -it %CONTAINER% sh -c %CMD% 