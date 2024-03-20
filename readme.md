# Readme to explain the structure of the project and how to use it


* [Used technology](#tech)
* [Repository](#repo)


<a name="tech"></a>
## Used Technology

**Framework SpringBoot:** Hibernate, JPA, Validation, Web
**Dependency management:** Maven
**Other libraries:** Lombok, MapStruct and AWS SDK

<a name="repo"></a>
## Repository
Clone the repository
```bash
  git clone https://github.com/adreso/test-blossom.git
```
Install the dependencies
```bash
  ./mvnw clean install
```
Start the server
```bash
  ./mvnw spring-boot:run
```

## Domain-based structure

The Driven Domain Design (DDD) approach is applied.

**IMPORTANT:** The application and domain layers should not implement any class from the infrastructure layer; while the infrastructure layer can implement classes from the domain.

Project Structure
```bash
  ├── java
  │   └── co.com.blossom
  │       ├── configs                                # Configuration files for the application
  │       │   ├── annotations                        # Custom annotations especially for the exception handling
  │       │   ├── aspects                            # Aspect classes for the application to handle the exception
  │       │   ├── controller                         # Abstract controller class to standardize the responses
  │       │   ├── enumerators                        # Enum classes to cross the application
  │       │   ├── exceptions                         # Exceptions classes to capture the error through the aspects in the different part of the application (Infrastructure, Application, Domain)       
  │       │   ├── infraestructura                    # Properties files for the application
  │       │   ├── utils                              # Standar response, custom slice to paginate, error codes and environment properties
  │       ├── AppConfig                              # Define the confugurations that affect the entire application
  │       ├── service1                               # Group of folders containing the all the domains of the application
  │       │   ├── domain1                            # This is a group of folders that contains the structure of the domain
  │       │   │   ├── application                    # The application layer of the domain
  │       │   │   │   └── rest                       # In this case the application layer is a rest controller
  │       │   │   │       └── ...Controllers         # The rest controller class. Ex. TemplateController.java
  │       │   │   ├── domain                         # The domain layer of the domain
  │       │   │   │   ├── gateways                   # The interface of the domain to communicate with the infrastructure
  │       │   │   │   ├── model                      # The DTO and the projection of the domain
  │       │   │   │   └── services                   # The interface and implementation of the domain to communicate with the application
  │       │   │   └── infraestructure                # The infrastructure layer
  │       │   │       └── mysql                      # In this case the infrastructure layer is a mysql database
  │       │   │           ├── gateways               # The implementation of the domain to communicate with the infrastructure
  │       │   │           ├── mapper                 # The mapper to convert the DTO to the entity and vice versa
  │       │   │           └── model                  # The infrastructure entity to communicate with the database
  │       │   │           └── ...Repository          # The repository to communicate with the database
  │       │   └── domain1                            # Other domain of the application
              . . .
  │       ├── service2                               # Other group of folders containing the all the domains of the application
  │       ├── service3                               # Other group of folders containing the all the domains of the application
  
```
The test structure is the same as the main structure, but the test classes are in the same package as the class to be tested.

## Application YAML
In the application YAML file we have all the configurations and environment properties for the application. And this is ubicated in the resources folder.

```bash
  ├── resources
  │   └── application.yml
```
For this file in particular we are using a configuration based in profiles, specifically to work with aws parameters store. To work with multiple environments we have to create a profile in the application.yml file and create a file with the same name of the profile and the properties that we want to override. For example, if we have a profile called dev, we only need to add that profile and create the parameter store environment variables in the aws console.

## Dockerfile
In this file we have the configuration to build the application in a docker container. This file is ubicated in the root of the project.
```bash
  ├── Dockerfile
```
For this file in particular we are using layers to optimize the build of the application, but there this still can be more optimized.

## Buildspec file
In this file we have the configuration to build the application in AWS CodeBuild. This file is ubicated in the root of the project.

```bash
  ├── buildspec.yml
```
For this file in particular we are defining three phases: install, pre_build and build. 
    - In the install phase we just definite the runtime version
    - In the pre_build phase we are testing and installing the application to generate the jar file
    - In the build phase we are building the docker image and pushing it to the ECR repository
    - Finally we are generating the imagedefinitions.json and the appspec.yml files to deploy the application in AWS ECS.
``
For the tests in particular, we could have created a new phase called test and run the tests in that phase, but for this case we are running the tests in the pre_build phase, for simplicity.
``

## Appspect file
In this file we have the configuration to deploy the application in AWS ECS. This file is ubicated in the root of the project.

```bash
  ├── appspec.yml
```
In this case we are only defining the task definition and the container name to deploy the application in AWS ECS. This can be more dynamic, but for this case we are using a static configuration.

## Liquibase folder
In this folder we have the configuration to manage the database changes. This file is ubicated in the root of the project. More information about the structure of the liquibase folder can be found in the readme.md file in the liquibase folder.
