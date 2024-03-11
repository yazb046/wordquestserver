postgres dumpt:
brew upgrade postgresql@15
brew link --overwrite --force postgresql@15
pg_dump -U user -h localhost -p 5432 -d postgres -F c -f /dumpfilelocation
createdb -U user -h localhost -p 5432 newdbname
pg_restore -U user -h localhost -p 5432 -d restoreddb /dumpfilelocation
pg_dump -U admin -h localhost -p 5432 -d postgres > ~/Desktop/psdb_dump.sql

API:
[//]: # (get all words as in dictionary)
GET http://localhost:8080/api/words

[//]: # (get all words by a user)
GET http://localhost:8080/api/words/user?userId=1

[//]: # (add a word to a user)
PUT http://localhost:8080/api/words/user?userId=1&wordId=1040
