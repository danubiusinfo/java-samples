# Hibernate JSON datatype using Quarkus framework

This sample module shows how to store data in JSON structure in relational DB using Hibernate and Quarkus framework

Related
article: [How did we resolve a performance issue related to i18n?](https://danubius.io/how-did-we-resolve-a-performance-issue-related-to-i18n/)

## Running the sample

- First install dependencies: `mvn install`
- Move to the docker folder and run the db container: `docker-compose up`
- Run quarkus in dev mode: `mvn package quarkus:dev`

## Test the endpoints

### Save item

Post the following sample JSON to the `http://localhost:8080/item` endpoint

```json
{
  "languages": [
    {
      "content": "Item name (NL)",
      "language": "nl"
    },
    {
      "content": "Item name (EN)",
      "language": "en"
    },
    {
      "content": "Item name (FR)",
      "language": "fr"
    }
  ]
}
```

### List items

Open the `http://localhost:8080/item` endpoint

