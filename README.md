# Bramha
creator and maintainer of zefo code

Code generation libraries for our java projects to remove boilerplate and repeated code and make coding in java and developer life more simple.

## Ideas
- [x] Simplify registering of entity classes by auto generating a list of all classes with ```@Entity``` annotation.
- [x] Simplify writing basic DAO classes by auto generating get, search and create methods as per the new JPA standards for all classes with ```@GenerateDao``` annotation, adding further functionality will be as simple as extending the generated class.
- [ ] Simplify RestClient generation by auto generating rest client methods from Res classes
- [ ] HIbernate style DAO for elastic search, to standardize elastic search queries in our codebase

## Dependencies
1. [JavaPoet](https://github.com/square/javapoet) - for java code generation
2. Dropwizard for having access to its annotations.
