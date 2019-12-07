# Getting Started
## Installation

### Automatic Installation
* Run Maven Goal 'clean' 'compile' 'package' <code>mvn clean compile package</code>
*  Launch file <code>[autorun.sh](autorun.sh)</code> | <code>[autorun.cmd](autorun.cmd)</code> for windows
```bash
abdoulaye@faldji:~$  ./autorun.sh 
```
```console
C:\Users\abdoulaye\Documents\ProgComp\Tp-abdoulaye> ./autorun.cmd
```
* spring Exposed at [localhost:9192/](http://localhost:9192/)

### Manual installation
* Run Maven Goal 'clean' 'compile' 'package' <code>mvn clean compile package</code>
* Create Docker network: <code>docker network create tp-network </code>
* Build and Run tp-database (Image, Container) : 
<code> 
docker build -f tp-abdoulaye.dockerFile -t tp-abdoulaye .
&& docker run
-p 9192:8080
--name tp-abdoulaye
--network tp-network
tp-abdoulaye </code>
* Build and Run tp-abdoulaye (Image, Container) : 
<code>docker build -f hsqldb.dockerFile -t tp-database .
      && docker run
      -p 9001:9001
      --name tp-database
      --network tp-network
      tp-database </code>
* spring Exposed at [localhost:9192/](http://localhost:9192/)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.BUILD-SNAPSHOT/maven-plugin/)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

