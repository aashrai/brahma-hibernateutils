# Bramha-HibernateUtils
An annotation processor which auto generates a list of all classes with ```@Entity``` annotation.

## About 
Simplify registration of entity classes in Dropwizard by auto generating a list of classes with ```@Entity``` annotation.

## Example
Here's two entity classes that need to be registered.
```java
package com.example.hibernateutils

import javax.persistence.Entity;

@Entity 
public class Vishnu {
    
}
```
```java
package com.example.hibernateutils

import javax.persistence.Entity;

@Entity 
public class Shiva {
    
}
```
Adding entity classes to hibernate bundle is as simple as passing the generated array
```java
 private final HibernateBundle<Config> hibernateBundle =
            new HibernateBundle<Config>(Brahma_HibernateUtils.entityAnnotatedClasses[0],
                    Brahma_HibernateUtils.entityAnnotatedClasses) {

                public DataSourceFactory getDataSourceFactory(Config configuration) {
                    return configuration.getDataSourceFactory();
                }
            };
```

### Generated Code
```java
package com.brahma.utils;

import com.example.HelloWorld;
import java.lang.Class;

public final class Brahma_HibernateUtils {
  public static final Class[] entityAnnotatedClasses = new Class[] {
    Vishnu.class,
    Shiva.class
  }
  ;
}
```
## Project Brahma
This repo is a part of project brahma, a suite of annotation processors built with :hearts: by folks at [Gozefo]: https://www.gozefo.com/ enginnering to remove boilerplate in our java projects.

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
