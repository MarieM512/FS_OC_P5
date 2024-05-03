# Yoga App

Yoga app is an application that connects yoga teachers with their clients by informing them of upcoming sessions. In addition, clients inform them of their holdings.

## Prerequisites

This application use MySQL like database so be sure you have it, else download it (https://dev.mysql.com/downloads/installer/).

If you are not familiar with this, I recommend you install MySQL Workbench (https://dev.mysql.com/downloads/workbench/). I will explain how to launch this project with this software.

## Installation

### 1. Clone the repository
```
git clone https://github.com/MarieM512/FS_OC_P5.git
```

### 2. Create a new databse in MySQL

You can import the script `ressources/sql/script.sql`

If you don't know how to do it, please follow the instructions

- Open MySQL Workbench
- Connect to your MySQL server (port:3306 for exemple)
- Click on the fourth icon from the left
- In Schema Name enter a name for your database, be sure to retain it because we will need it after.
- Click on Apply
- Go to the "Server" > "Data Import"
- Select Import from Self-Contained File > Select `script.sql` from `ressources/sql`
- Select your database in Default Schema to be Imported To
- Click on "Start Import"

### 3. Update the application with your config

Update `application.properties` with your config
```yaml
spring.datasource.url=jdbc:mysql://localhost:3306/oc_yoga?allowPublicKeyRetrieval=true #replace oc_yoga with the name of your database
spring.datasource.username=test #username
spring.datasource.password=OpenClassrooms #password
```

## Run the back application

If you have Maven on your pc, do this:
```
cd back
```

```
mvn clean install
```

```
mvn spring-boot:run
```

Else:

- In Visual Studio Code install `Extension Pack for Java` extension
- Go to `src/main/java/com/mariemetay/ChatopapiApplication.java`
- Click on Run, juste above the main function

## Run the front application

If you have Maven on your pc, do this:
```
cd front
```

```
npm install
```

```
npm run start
```

## Demo application

By default the admin account is:
- login: yoga@studio.com
- password: test!1234

## Tests back-end

### Run
```
mvn clean test
```

### Coverage report

> [!IMPORTANT]
> Be sure to run test before

Go to `back/target/site/jacoco` and open `index.html` in web browser to see the coverage.

## Tests front-end

### Unitary test

#### Run
```
npm run test
```

#### Coverage report
```
npm run test:coverage
```

Go to `front/coverage/jest/lcov-report` and open `index.html` in web browser to see the coverage.

### End-to-end

#### Run
```
npm run e2e
```

#### Coverage report

> [!IMPORTANT]
> Be sure to run test before

```
npm run e2e:coverage
```

Go to `front/coverage/lcov-report` and open `index.html` in web browser to see the coverage.
