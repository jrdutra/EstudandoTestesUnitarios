#!/bin/bash
while true; do 
	git add --all
	git commit -m "Testes Unitarios"
	git push
	read
	sleep 5
	clear
done

