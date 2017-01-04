# My Bank

## Requirements
- Java 1.8
- Postgres 9.5

## Run

```bash
java -jar bank-<version>-SNAPSHOT.jar > `date +%Y-%m-%d-%H-%M`.log
```

### Docker database

```bash
docker build --rm -t carlosmecha/bank-devdb:latest .
docker run --name database -p '5432:5432' -e POSTGRES_PASSWORD=mypass -e POSTGRES_USER=banking -e POSTGRES_DB=bank carlosmecha/bank-devdb
```


## Backups
 
### Cron

```cron
0 10 * * * /bin/bash backup.sh
```
