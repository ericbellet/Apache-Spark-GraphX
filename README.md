# Apache Spark & GraphX [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

[![Build Status](https://img.shields.io/travis/KunalKapadia/express-mongoose-es6-rest-api/master.svg?style=flat-square)](https://travis-ci.org/KunalKapadia/express-mongoose-es6-rest-api)

# [![graphx](http://spark.apache.org/docs/latest/img/graphx_logo.png)](http://spark.apache.org/graphx/)
# [![cloudera](http://g33ktalk.com/wp-content/uploads/2013/07/cloudera.jpg)](https://www.cloudera.com/)
# [![scala](https://dnsta5v53r71w.cloudfront.net/images/why-scala/scala-logo.png)](http://www.scala-lang.org/)
## Table of contents

* [Resumen](#resumen)
* [Archivos que contiene el repositorio](#archivos-que-contiene-el-repositorio)
* [Características](#características)
* [Inicialización](#inicialización)
* [Creador](#creador)


### Resumen

El siguiente repositorio...

### Archivos que contiene el repositorio

El siguiente repositorio contiene las siguientes carpetas con los diversos archivos:

```
Apache-Spark-GraphX/
├── data/
│   ├── continent.csv
│   ├── facebook_combined.txt
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   └── 
├── doc/
│   ├── GraphX-Hands-On.pdf
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   └── 
├── lib/
│   ├── breeze_2.10-0.12.jar 
│   ├── breeze-viz_2.10-0.12.jar
│   ├── gs-core-1.2.jar
│   ├── gs-ui-1.2.jar
│   ├── jcommon-1.0.16.jar
│   ├── jfreechart-1.0.13.jar 
│   └── pherd-1.0.jar
├── src/
│   ├── Facebook.scala
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   ├── 
│   └── 
└── style/
    ├── stylesheet
    ├── stylesheet-simple
    ├── 
    ├── 
    └── 
```

### Características

| Feature                                | Summary                                                                                                                                                                                                                                                     |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ES6 via Babel                  	 	 | ES6 support using [Babel](https://babeljs.io/).  |
| Code Linting               			 | JavaScript code linting is done using [ESLint](http://eslint.org) - a pluggable linter tool for identifying and reporting on patterns in JavaScript. Uses ESLint with [eslint-config-airbnb](https://github.com/airbnb/javascript/tree/master/packages/eslint-config-airbnb), which tries to follow the Airbnb JavaScript style guide.                                                                                                |
| Auto server restart                  	 | Restart the server using [nodemon](https://github.com/remy/nodemon) in real-time anytime an edit is made, with babel compilation and eslint.                                                                                                                                                                            |
| ES6 Code Coverage via [istanbul](https://www.npmjs.com/package/istanbul)                  | Supports code coverage of ES6 code using istanbul and mocha. Code coverage reports are saved in `coverage/` directory post `npm test` execution. Open `lcov-report/index.html` to view coverage report. `npm test` also displays code coverage summary on console.                                                                                                                                                                            |
| Debugging via [debug](https://www.npmjs.com/package/debug)           | Instead of inserting and deleting console.log you can replace it with the debug function and just leave it there. You can then selectively debug portions of your code by setting DEBUG env variable. If DEBUG env variable is not set, nothing is displayed to the console.                       |
| Promisified Code via [bluebird](https://github.com/petkaantonov/bluebird)           | We love promise, don't we ? All our code is promisified and even so our tests via [supertest-as-promised](https://www.npmjs.com/package/supertest-as-promised).                       |
| API parameter validation via [express-validation](https://www.npmjs.com/package/express-validation)           | Validate body, params, query, headers and cookies of a request (via middleware) and return a response with errors; if any of the configured validation rules fail. You won't anymore need to make your route handler dirty with such validations. |

- CORS support via [cors](https://github.com/troygoode/node-cors)
- Uses [http-status](https://www.npmjs.com/package/http-status) to set http status code. It is recommended to use `httpStatus.INTERNAL_SERVER_ERROR` instead of directly using `500` when setting status code.

### Inicialización

Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
```
Inicia la consola de Spark:
```sh
spark-shell --jars lib/gs-core-1.2.jar,lib/gs-ui-1.2.jar,lib/jcommon-1.0.16.jar,lib/jfreechart-1.0.13.jar,lib/breeze_2.10-0.12.jar,lib/breeze-viz_2.10-0.12.jar,lib/pherd-1.0.jar -i Facebook.scala

```

**Eric Bellet**

* <https://ve.linkedin.com/in/belleteric>
