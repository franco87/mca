#!/bin/bash

aws dynamodb create-table --table-name EntriesTable \
--attribute-definitions AttributeName=_id,AttributeType=S \
--key-schema AttributeName=_id,KeyType=HASH \
--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
--endpoint-url http://localhost:8000