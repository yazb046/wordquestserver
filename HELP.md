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

[//]: # (get all words by a user by filter with pagination and sorting)
GET http://localhost:8080/api/words/searchBy?userId=1&filter=new&pageNo=0&pageSize=50&sortBy=word&direction=asc

[//]: # (get all texts by a user, a word and by filter with pagination and sorting)
GET http://localhost:8080/api/texts/searchBy?userId=1&filter=new&pageNo=0&pageSize=50&sortBy=word&direction=asc&word=abbiegen

# (save a new card to the user)
POST http://localhost:8080/api/cards?userId=1
{
"id":1,
"text":"text"
"contextTitle":"contexttitle"
}
curl -X POST http://localhost:8080/api/cards?userId=1 -H "Content-Type: application/json" -d '{"id":1, "text":"text","contextTitle":"contexttitle"}'
