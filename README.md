# employee-id-hunter

  - Studies with Spring Boot ```@Component``` and ```@Scheduled``` annotations, along with Java 8 ```Consumer<T>``` and ```Predicate<T>``` interfaces, playing around with a _SMS service_ with ```HttpURLConnection``` instances.
  
  ## To manually import the local JAR to your maven repository, go to the _resources_ folder, then run:
  
  ```
  mvn install:install-file \
  	-Dfile=service-smscep-1.0-SNAPSHOT.jar \
  	-DgroupId=com.byjg \
  	-DartifactId=service-smscep \
  	-Dversion=1.0-SNAPSHOT \
  	-Dpackaging=jar \
  	-DgeneratePom=true
  ```
