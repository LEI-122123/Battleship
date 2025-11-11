# Battleship

Basic academic version of Battleship game to build upon.

## Group elements:
- 66174 Rúben Rocha
- 106090 Tiago Alves
- 113239 Pedro Veloso
- 122123 Rodrigo Delaunay

# Java with Maven [workflow](https://github.com/LEI-122123/Battleship/blob/main/.github/workflows/maven.yml)

The included workflow sets up a Java 17 environment with Maven,  
performs a clean build and runs the included Class tests  
on any Push or Pull Request, on the branch which these events take place.

```yml
- name: Build and test with Maven
        run: mvn -B clean test
```

## Quick copy-paste
```txt
nova
caravela 0 0 s
galeao 0 3 o
barca 0 9 n
fragata 8 6 e
nau 9 0 e
caravela 5 2 o
nau 3 7 n
barca 3 1 s
barca 3 5 e
caravela 7 0 e
barca 7 4 o
```
