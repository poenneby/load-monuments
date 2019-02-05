# load-monuments

This script creates the *monumental* database with a schema and persists the data of the monuments from MERIMEE in [Datomic](https://www.datomic.com).

## Prerequisites

You need a [Datomic transactor](https://docs.datomic.com/on-prem/transactor.html) running.

Example:

```
> $DATOMIC_HOME/bin/transactor $DATOMIC_HOME/config/samples/dev-transactor-template.properties
```


Get the monuments from the MERIMEE database and put them in the resources directory:

```
cd resources
wget http://data.culture.fr/entrepot/MERIMEE/merimee-MH.json
```

## Usage

Run the script using Leiningen:

`lein run`

```
Creating database
Creating schema
Loaded 45285 monument(s)
```
