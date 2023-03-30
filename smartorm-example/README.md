## SmartORM Example

This is an example project for using SmartORM, a Java ORM framework that can automatically generate CRUD code at compile
time using GPT.

## Prerequisites

To run this example, you'll need the following:

- Java 1.8 or above
- An OpenAI API key
- Maven

## Getting Started

1. Open the `smartorm.properties` file located in `src/main/resources`. Replace `<your-openai-token>` with your actual
   OpenAI API key.
2. Build the project with Maven:

```shell
mvn compile
```

The generated CRUD code will be located in the` target/generated-sources/annotations` directory. You can use this code
to interact with your database.