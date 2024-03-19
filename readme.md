
# Plantilla de Colaboración Electrónica

Plantilla base para la implementación de microservicios en el contexto de colaboración electrónica de la empresa DISPAPELES


## Índice de contenidos
* [Tecnología utilizada](#tech)
* [Repositorio](#repo)
* [Estructura basada en el dominio](#struct)
* [En laboratorio](#lab)
* [Versión](#version)

<a name="tech"></a>
## Tecnología utilizada


**Framework SpringBoot:** Hibernate, JPA, Validation, Web

**Gestor de dependencias:** Maven

**Otras librerías:** Lombok, MapStruct, 
**Otras librerías:**Swagger Url:http://localhost:8081/v2/api-docs, http://localhost:8081/swagger-ui.html)

<a name="repo"></a>
## Repositorio

Clonar proyecto

```bash
  git clone https://git-codecommit.us-east-1.amazonaws.com/v1/repos/DPColaboracionElectronicaPlantillaRest
```

Instalar dependencias

```bash
  ./mvnw clean compile
```

Iniciar servidor

```bash
  ./mvnw spring-boot:run
```

<a name="struct"></a>
## Estructura basada en el dominio

Se aplica el enfoque Driven Domain Design (DDD).

**IMPORTANTE:** Las capas de application y domain no deben implementar ninguna clase de la capa de infraestructura; mientras que la capa de infraestructura si puede implementar clases de origen domain

```bash
  ├── java
  │   └── co.com.microservicio
  │       ├── template                              # Dominio general de trabajo
  │       │   ├── application                       # Capa de aplicación
  │       │   │   └── controllers                   # Controladores de la aplicación
  │       │   │       └── rest                      # Controladores tipo rest de la aplicación
  │       │   │           └── ...Controller         # Clases controladoras rest. Ej. TemplateController.java
  │       │   ├── domain                            # Capa de dominio
  │       │   │   ├── gateways                      # Repositorios relacionado con el dominio
  │       │   │   │   └── ...Repository             # Interfaz repository. Ej. TemplateRepository.java
  │       │   │   ├── model                         # Modelos del dominio
  │       │   │   │   ├── projections               # Interfaces de proyección
  │       │   │   │   │   └── ...Projection         # Clases proyecciones. Ej. TemplateLiteProjection.java
  │       │   │   │   └── ...DTO                    # Clases DTO. Ej. TemplateDTO.java
  │       │   │   └── services                      # Servicios relacionado con el dominio
  │       │   │       ├── ...Services               # Interfaz servicio. Ej. TemplateService.java
  │       │   │       └── ...ServicesImpl           # Clase servicio (Lógica de negocio). Ej. TemplateServiceImpl.java
  │       │   └── infraestructure                   # Capa de infraestructura
  │       │       └── drivenadapter                 # Conjunto de adaptadores de base de datos
  │       │           └── mysql                     # Adaptador mySQL 
  │       │               ├── gateways              # Repositorios relacionado con el dominio
  │       │               │   └── ...GatewayImpl    # Clase que implementa el repositorio del dominio. Ej. TemplateGatewayImpl.java
  │       │               ├── mapper                # Conjunto de mappers que facilitan la conversion entre DTO y Entity
  │       │               │   └── ...Mapper         # Interfaz mapper con la relacion DTO y Entity. Ej. TemplateMapper.java
  │       │               ├── model                 # Conjunto de entidades
  │       │               │   └── ...Mapper         # Clase Entity con la estructura de la tabla en la BD. Ej. TemplateEntity.java
  │       │               └── ...Repository         # Repositorios con la lógica transaccional con MySql
  │       ├── template2                             # Otros dominio de ejemplo
  │       ├── ...                                   # Otros dominio de ejemplo
  │       └── SpringBootApplication                 # Punto de inicio de la aplicación
  └── resources                                     # Recursos de la aplicación
          └── ...                                   # Properties y otros
```

<a name="lab"></a>
## En Laboratorio

Los paquete 'external' y 'configs' está pendiente por revisar con el equipo de manera conjunta y debatir su utilidad.


```bash
  ├── java
  │   └── co.com.microservicio
  │       ├── configs                               # Por revisar
  │       ├── external                              # Por revisar
  │       ├── template                              # Dominio general de trabajo
  │       └── ...  
  └── ...
```

<a name="version"></a>
## Versión
2.0.2
