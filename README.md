# TreasureMap exercise

William SCHOUVEY

## Build

```bash 
$ mvn clean install 
```

The build generates a jar file.

## Endpoints

There is two ways to get a result:

### From a JSON

Call this endpoint and add the file content in the request body

```http request 
POST http://localhost:8080/json
```

### From a file

Call this endpoint and add the file as a form-data parameter in the request body named "file"

```http request 
POST http://localhost:8080/file
```