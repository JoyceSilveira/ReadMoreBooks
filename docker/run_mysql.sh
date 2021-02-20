#!/bin/bash
echo "Inicializando mysql"
docker start mysql-docker_db_1
echo "Inicializando phpMyAdmin"
docker start mysql-docker_app_1
