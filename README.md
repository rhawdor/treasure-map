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

Call this endpoint and add the file content as a list of string in the request body

```http request 
POST http://localhost:8080/json
```

#### Exemple:
```JSON
[
  "C - 3 - 4",
  "M - 1 - 0",
  "M - 2 - 1",
  "T - 0 - 3 - 2",
  "T - 1 - 3 - 3",
  "A - Lara - 1 - 1 - S - AADADAGGA"
]
```

### From a file

Call this endpoint and add the file as a form-data parameter in the request body named "file"

```http request 
POST http://localhost:8080/file
```