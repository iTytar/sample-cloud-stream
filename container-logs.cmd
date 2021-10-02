@echo off
start "Producer" docker-compose logs -f producer
start "Processor" docker-compose logs -f processor
start "Consumer" docker-compose logs -f consumer
