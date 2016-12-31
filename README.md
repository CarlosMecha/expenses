# My Bank

## Requirements
- Java 1.8
- Postgres 9.5

## Run

```bash
java -jar bank-0.0.1-SNAPSHOT.jar > `date +%Y-%m-%d-%H-%M`.log
```

## Backups

### Cron

```cron
0 10 * * * pg_dump -T users -p $PGPORT -U $PGUSER --no-acl bank > `date +%Y-%m-%d-%H-%M`.bakup
```