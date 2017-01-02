# My Bank

## Requirements
- Java 1.8
- Postgres 9.5

## Run

```bash
java -jar bank-<version>-SNAPSHOT.jar > `date +%Y-%m-%d-%H-%M`.log
```

## Backups

### Cron

```cron
0 10 * * * /bin/bash backup.sh
```
