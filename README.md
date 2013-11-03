Logical Delete
==============

This plugin allows you to do a logical deletion of the domain classes. 
The main intention of the plugin is to handle cases when certain entities cannot be physically removed from the database.

## How it works:

Most of the work is done using [AST transformations](http://groovy.codehaus.org/Compile-time+Metaprogramming+-+AST+Transformations).
For the desired class a new boolean property is added: deleted. 
The GORM method __delete__ is modified to avoid the physical removal and just change the value of the _deleted_ property.
In addition to the AST transformations, an Hibernate filter is added to make the exclusion of the deleted entities transparent.

## How to use:

To provide logical deletion to a domain class you just need to add the __@LogicalDelete__ annotation to it.

```groovy
@LogicalDelete
class User {
   String lastName
   String firstName
   String nickName
    ...
}
```

To make a logical removal you just need to use the GORM method _delete_.

```groovy
user.delete() 
```

If you want to force a physical deletion to an annotated class, you have to add the __physical__ parameter in _true_ to the _delete_ method:

```groovy
user.delete(physical: true) 
```
