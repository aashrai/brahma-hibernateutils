# Bramha-HibernateUtils
An annotation processor which auto generates a list of all classes with ```@Entity``` annotation.

## About 
Simplify registering of entity classes with Hibernate by auto generating list of classes with ```@Entity``` annotation.
We don't need to add entity classes to hibernate bundle manually.

## Problem
With hibernate we need to add the entity classes to hibernate bundle. User has to maintain the list of entity classes and also has to add the classes manually. Here's an example of adding entities to hibernate bundle.

```
private final HibernateBundle<Config> hibernateBundle =
            new HibernateBundle<Config>(Brahma.class
                                        Creator.class,
                                        LifeUniverser.class,
                                        HelloWorld.class,
                                        Person.class,
                                        Car.class,
                                        House.class
                                        Vegetable.class
                          ) {

                public DataSourceFactory getDataSourceFactory(Config configuration) {
                    return configuration.getDataSourceFactory();
                }
            };
```
It becomes difficult for the user to maintain all the entities and also the code doesn't look clean. We offer a solution to this problem and which makes a developers life easy.


## Example

### Entity Class
Here's an entity class that needs to be registered.
```
package com.example.hibernateutils

import javax.persistence.Entity;

@Entity 
public class HelloWorld {
    
}
```
### Generated Code
The generated code looks like this:
```
package com.brahma.utils;

import com.example.HelloWorld;
import java.lang.Class;

public final class Brahma_HibernateUtils {
  public static final Class[] entityAnnotatedClasses = new Class[] {
    HelloWorld.class,
  }
  ;
}
```
The list will contain all the classes that are annotated with @Entity annotation. Now, their is no need to maintain the list of entity classes.

### How to add generated list to hibernate bundle
```
 private final HibernateBundle<Config> hibernateBundle =
            new HibernateBundle<Config>(Brahma_HibernateUtils.entityAnnotatedClasses[0],
                    Brahma_HibernateUtils.entityAnnotatedClasses) {

                public DataSourceFactory getDataSourceFactory(Config configuration) {
                    return configuration.getDataSourceFactory();
                }
            };
```
Adding the generated list to hibernate bundle.


## License
MIT License

Copyright (c) 2018 gozefo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
