# SmartORM

`SmartORM` is a Java ORM (Object-Relational Mapping) framework that enables you to automatically generate CRUD (Create,
Read, Update, Delete) code at compile time using GPT. With `SmartORM`, you can simply write DAO (
Data Access Object) interfaces and get complete CRUD operations implemented automatically, while remaining compatible
with the JPA (Java Persistence API) specification.

## Features

- Automatic generation of CRUD code at compile time
- JPA-compliant interface and entity annotations
- Supports multiple database types, such as MySQL, PostgreSQL, SQLite, etc.

## Installation

To use SmartORM in your Java project, you can add the following Maven dependency to your project's pom.xml file:

```xml

<dependency>
    <groupId>io.github.1994</groupId>
    <artifactId>smartorm-all</artifactId>
    <version>1.0.0.Alpha</version>
</dependency>
```

> Please note that this project is currently under development and is still in its early preview version.

## Getting Started

The `@SmartDAO` annotation is a core annotation in `SmartORM`, used to define a DAO (Data Access Object) interface and
its
associated entity class and database connection.

for example:

```java

@SmartDAO(datasource = "datasource", entity = PersonEntity.class)
public interface PersonDAO {

    int insert(PersonEntity entity);

    PersonEntity getById(Long id);

    int deleteById(Long id);

    List<PersonEntity> findByGreaterThanBirth(Long birth);
}

@Entity
@Table(name = "test_person")
@Data
public class PersonEntity {

    private Long id;

    private String name;

    private Long birth;

    private int status;

}

```

The `@SmartDAO` annotation takes two parameters:

- datasource: the name of the database connection to use
- entity: the class of the entity associated with the DAO interface

In this example, the PersonDAO interface is associated with the PersonEntity class and the datasource database
connection.

Once you've defined your DAO interface with the `@SmartDAO` annotation, `SmartORM` will automatically generate the
implementation code for you at compile time, providing basic CRUD operations (insert, get by ID, delete by ID) and any
additional methods you define in your DAO interface.

### Custom Methods

In addition to the basic CRUD operations, you can define custom methods in your DAO interface and SmartORM will generate
the implementation code for them as well. For instance, in the example above, we've defined a `findByGreaterThanBirth`
method that retrieves all `PersonEntity` instances whose birth date is greater than a given value.

```java
List<PersonEntity> findByGreaterThanBirth(Long birth);
```

This method will generate the SQL code to retrieve all matching rows from the person table and map them to PersonEntity
instances.

## Configuration File

`SmartORM` can be configured using a `smartorm.properties` file in the resources folder of your Maven project. Here's an
example configuration file:

```properties
smartorm.gpt.token=<your-openai-token>
## optional proxy
# smartorm.gpt.proxy=localhost:7890
## optional proxy type
# smartorm.gpt.proxy.type=http or socks
## optional gpt model default is gpt-3.5-turbo
# smartorm.gpt.model=gpt-3.5-turbo
## optional default is mysql
# smartorm.dialect=mysql
```

### GPT Settings

The following settings are related to GPT:

- `smartorm.gpt.token`: Your OpenAI API token, which is required for SmartORM to access the GPT API. You can obtain an API
  token by signing up for OpenAI's GPT service.
- `smartorm.gpt.proxy`: Optional proxy server for SmartORM to use when accessing the GPT API. If you need to use a proxy
  server, specify the proxy server hostname and port here.
- `smartorm.gpt.proxy`.type: Optional proxy server type for SmartORM to use when accessing the GPT API. If you're using a
  proxy server, specify the proxy server type as either http or socks.
- `smartorm.gpt.model`: Optional GPT model to use for generating code. The default is gpt-3.5-turbo, but you can specify
  any
  of the GPT models provided by OpenAI.

### Database Settings

The following settings are related to database access:

- `smartorm.dialect`: Optional database dialect for SmartORM to use. The default is mysql, but you can specify any of the
  supported database dialects, such as postgresql or sqlite.

