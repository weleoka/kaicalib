# LTU - Bibliotekssystemet Grupp 8 [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.png?v=103)](https://opensource.org/licenses/mit-license.php)
Educational project based on MVC architecture. Hibernate ORM to model to a RDBMS with Spring (Java EE) for client web application and backend services.

Current build engine is Gradle and with mavenCentral repositories for dependency sourcing.  

To see an operational deployment of this project please contact the developers for details on how to access the remote server simply using your browser!


## Developer environment
The project is probably best using MySQL or MariaDB when in production. The connection details are in `src/main/java/resources/application.yml`. Included with the repository is DDL and useradd SQL for initialising the database. These are found in `db_user.sql`, `schema.sql` and `data.sql` files in `/resources`. It also assumes Java version 1.8 ( a.k.a Java 8).  
The location of the sql files is implicitly found by Spring and will load by default the schema and the initial data when in a development environment or for initial deployment. The user creation scrip should be manually run.

So in brief:
- clone the repository
- Run the gradle target `build` of ./gradlew (or if on windows gradle.bat) e.g. `./gradlew build` or `gradle.bat build`

The project will compile and the tests will run. Gradle can display resulting output/feedback in a browser.  

For deployment use the Spring Gradle plugin default task bootJar, bootWar, and bootRun.


### Deployment for development
Spring plugin for gradle configures a default task called `bootRun`. Executing this task: `./gradlew bootRun` will compile the application and launch a web-server (Apache Tomcat). Test the repository standard route over port 8080 as follows: `localost:8080/`. This is called the embedded deployment method as Tomcat is compiled into an executable jar.

Spring plugin also creates a war run task `./gradlew bootWar`. You can then deploy this to tomcat, and changes to static files will be immediately be available on a browser refresh. This method is known as servlet deployment. It is useful for running many apps on one machine. This method is becoming less popular with the advance of cloud computing, where each app is running on its own machine. For running multiple apps with embedded containers on one machine it is required to work with a proxy service such as nginx.

For development and testing there is both the java based H2 or HSQL DBMS. These are useful as they require no SQL server database to be running, but also can offer slightly faster init when repeatedly building and running the application. The settings or these are also in `application.yml` where they can be configured to be in memory only or also with persistance to file on storage medium.

This project can be compiled into either to jar or war and has the servlet configuration on classpath.



### Deployment for production
This section is not completed but notes in progress.

- Set the database privileges necessary to make changes to only the data of the database and not the structure (schema). What this means is to not `GRANT ALL` to the bibsys_db_user.
- Modify the webapp static resources to be sourced from CDN.
- todo: configure actuator endpoints by including `spring-boot-starter-actuator`.
- Make sure the /console (for H2 database) mapping in Spring Security is disabled.
- `.csrf().disable()` change this as H2 database console is not required in production.
- `.headers().frameOptions().disable()` This is also to do with H2 db debugging.
- UUID's should be stored as Binary 16 universally. See code in Patron and User as example

### Notes
A few notes on issues discovered or other general points of interest.  

` GET "/css/bootstrap.min.css.map"` results in `"ERROR" dispatch, status 404`. The map files are part of a system allowing CSS preprocessors to live render the CSS. Just ignore the error.  


## Repository workflow
Git workflow to be discussed. Coud be good to decide upon:

- main branches vs. working branches
- branch naming conventions
- merging method (rebase->merge or squash-> merge --no-ff)
- tag releases
- production build releases


## Application requirements

Funktionalitetskrav - låntagare:
- Registrera en användare
- Logga in användare
- Söka efter objekt (bok / DVD-titel, författare / regissörsnamn, ISBN, ämnesord / film-genren)
- Göra ett lån (en till flera objekt) och få kvitto på lånet 
- Varje dag 

Funktionalitetskrav - bibliotikarie:
- Lägga till, ändra, och ta bort objekt
- Söka efter objekt (bok / DVD-titel, författare / regissörsnamn, ISBN, ämnesord / film-genren)
- Lägga till, ändra, och ta bort låntagare
- Visa lista på objekt som ej återlämnats i tid

Krav för kvitton:
- objektets titel
- objektets identifierare
- datum objektet lånades
- senast återlämning

Krav för objekt:
- attribut fysisk placering, författare / regissör, ISBN (endast för böcker), en klassificering i
  form av ämnesord (för böcker), eller genrer för filmer
- för filmer finns dessutom åldersbegränsning, produktionsland, skådespelare
- unika streckkoder som används för att identifiera varje enskilt fysiskt objekt

Krav för systemet:
- Hantera undantag (exceptions) på ett sätt som ger meningsfull återkoppling till
användaren
- automatiskt skicka påminnelsemail till alla låntagare som har överskriden lånetid för objekt
- begränsa antalet aktiva lån enligt låntagarens kategori (Student, Forskare, Övriga universitetsanställda, Allmänheten)
- olika object har olika låenetid. Kurslitteratur 14 dagar, Övriga böcker 1 månad, Film 1 vecka, Referenslitteratur får ej lånas ut, Tidskrift får ej ånas ut

Generella krav:
- Lagra data i en relationsdatabas
- Använda sig av arv och polymorfism i någon lämplig del av lösningen
- Tillhandahålla grafiska användargränssnitt


### Deluppgift A - Design
Rita UML-diagram som beskriver er lösning. En datamodell med tillhörande attribut och samband ska också finnas med i design-dokumentationen. Ni kan även bifoga bilder (screenshots) på design av planerade användargränssnitt om ni vill ha feedback, dock inget krav.

### Deluppgift B – Fungerande prototyp
Lämna in en rapport utformad enligt rapportmall för mindre rapport. Rapporten ska innehålla beskrivning av utvecklingsprocessen: vilka ändringar ni gjort från design till implementation, motivera eventuella avgränsningar eller utökningar från kravspecifikationen etc. Rapporten ska innehålla beskrivning av den utvecklade applikationen med ”screenshots” på centrala delar av användargränssnitt med tillhörande kod inklusive kortfattad beskrivning till varje del.

### Style guide
This project attempts to adhere to the following [style guide](https://github.com/weleoka/myJavaStyleGuide).


# Meta
Developers:

developer 1 - email: *removed* | developer 2 - email: *removed* | developer 3 - email: *removed*


# Dependencies
Please see `build.gradle` for full dependencies list.


Distributed under the MIT license. See [LICENSE.md](https://github.com/weleoka/bibliotekssystemet/LICENCE) for more information.
