## Script project for database change integration

This project contains the scripts that modify the relational databases for this project. 
It uses the free version of the tool [Liquibase](https://www.liquibase.org/) configured to be
used with Maven.

## Structure

The project is a modular Maven project, that is, there is a main pom and a module for each database. 
All modules internally have the same organization as described below:

```sh
resources
├── sprint(n)/
│   └── changelogs/
│       └── changelog-(user).sql
│   └──changelog-master.xml
├── liquibase_demo.properties
└── liquibase_dev.properties
└── liquibase_(environment).properties
```

- sprint(n): For each sprint a directory is created where (n) represents the sprint number
- changelog-(user).sql: Each developer creates a file with the required changes for the sprint
- liquibase_(environment).properties: Database configuration for a specific environment
- changelog-master.xml: General configuration of sprint changes.


## Synchronize database (Skip if log tables already exist)

To execute the scripts contained in this tool, the log tables required by liquibase must exist in the database, for this, use the command.

```sh
mvn liquibase:changelogSync -projects <project_name> -Denvironment=<profile>
```

## Execution

To apply or reverse the changes, the following Maven commands are available. In some cases, they apply parameters
specific to the Liquibase plugin and/or Maven parameters.

Apply the changes of all databases in a given environment. This command runs the scripts according to the
changelog-master.xml file configured in the pom of each module.

```sh
mvn liquibase:update -Denvironment=demo -Drelease=20
```

Apply the changes of a single database in a given environment for the delivery with identifier 20

```sh
mvn liquibase:update -projects core -Denvironment=demo -Drelease=20
```

Reverse the changes up to a tag for a given environment. The tags associated with the sprint are configured
in the changelog-master.xml file located in the directory of each sprint

```sh
mvn liquibase:rollback "-Dliquibase.rollbackTag=sp10" -Denvironment=demo -Drelease=20
```

Reverse the changes made on a date in the demo environment for the delivery with identifier 20

```sh
mvn liquibase:rollback "-Dliquibase.rollbackDate=2023-07-10" -Denvironment=demo -Drelease=20
```

Reverse the last n number of changes

```sh
mvn liquibase:rollback "-Dliquibase.rollbackCount=3" -Denvironment=demo -Drelease=20
```

To reverse the changes in a single database, include the '-projects' flag, for example
```sh
mvn liquibase:rollback "-Dliquibase.rollbackTag=sp10" -projects core -Denvironment=demo -Drelease=20
```