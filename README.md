# ES-LETI-1Sem-2020-Grupo15

The goal of this project is to develop a Java application that allows the user to evaluate the quality (number of correct answers and errors in the classification of defects) of certain tools (iPlasma, PMD and rules created by the user) in the detection of design defects in software, from the data provided in the assignement and in an Excel file.

## Group Members

- 88737 David Fonseca
- 87807 Ecaterina Grusetcaia 
- 87665 Gonçalo Santos 
- 87568 Tiago Barbosa
  
### Requirements:

- For our code to work it is necessary to download this [Excel File](https://e-learning.iscte-iul.pt/bbcswebdav/pid-111513-dt-content-rid-857362_1/xid-857362_1).
- It's necessary to also install both [EclEmma](https://www.eclemma.org/installation.html) and [JDeodorant](https://marketplace.eclipse.org/content/jdeodorant).
- Don´t forget to add the dependencies to your pom.xml!

### Dependencies

- To add a dependency for the maven library use:

[Apache POI](https://mvnrepository.com/artifact/org.apache.poi/poi/4.1.2) 
```
<!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.1.2</version>
</dependency>
```

### Trello link

 - [Trello](https://trello.com/b/iymYlbzi/es-project)
 
 ### Advice in order to start using our project

- The constructor of the App class has an int type argument. This argument is used to simplify the FileChooser meaning that when the filechooser is necessary, the value ‘0’ is passed otherwise the value ‘1’ is used.
 
### Functions not implemented

- All features are implemented and working, we even added some extra features, for example, deleting all rules, deleting a specific rule and show all rules.
- The unitary tests have a converage of about 98% of the classes but we couldn't cover the java files itself.

### Perspective of the main interface when opened

![Imagem2](https://user-images.githubusercontent.com/73655499/101992631-bbef5d80-3cac-11eb-9204-0de4cb188738.jpeg)

### Perspective of the main interface with Excel open

![Imagem1](https://user-images.githubusercontent.com/73655499/101992619-a7ab6080-3cac-11eb-8c33-e3f6e726c9f9.jpeg)

### Code Smells Report

- We didn't have any code smells in our project's code as proven by the following:

- God Class
![God Class](https://user-images.githubusercontent.com/73655499/101993538-65d1e880-3cb3-11eb-8e71-423714bdae41.jpeg)

- Long Method
![Long Method](https://user-images.githubusercontent.com/73655499/101993554-76825e80-3cb3-11eb-93f4-19c76df9a7cb.jpeg)

- Type Checking
![Type Checking](https://user-images.githubusercontent.com/73655499/101993556-800bc680-3cb3-11eb-8edb-aaa147023421.jpeg)

- Feature Envy
![Feature Envy](https://user-images.githubusercontent.com/73655499/101993566-8b5ef200-3cb3-11eb-844c-17b62c400dc6.jpeg)
