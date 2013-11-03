---
layout: project
page: project
title: "NaN Labs - Logical Delete"
description: "Grails plugin that allows you to do a logical deletion of the domain classes."
home-text: Home
footer-title: Get in touch
logoImg: logo-195x120.png
section: project
header: "Logical Delete"
lead-text: "Grails plugin that allows you to do a logical deletion of the domain classes."
github-url: "https://github.com/nanlabs/logical-delete"
---

The main intention of the plugin is to handle cases when certain entities cannot be physically removed from the database.

<br/>

### How it works:

Most of the work is done using [AST transformations](http://groovy.codehaus.org/Compile-time+Metaprogramming+-+AST+Transformations).
For the desired class a new boolean property is added: deleted. 
The GORM method __delete__ is modified to avoid the physical removal and just change the value of the _deleted_ property.
In addition to the AST transformations, an Hibernate filter is added to make the exclusion of the deleted entities transparent.

<br/>

### How to use:

To provide logical deletion to a domain class you just need to add the __@LogicalDelete__ annotation to it.

<pre>
@LogicalDelete
class User {
   String lastName
   String firstName
   String nickName
    ...
}
</pre>

To make a logical removal you just need to use the GORM method _delete_.

<pre>
user.delete() 
</pre>

If you want to force a physical deletion to an annotated class, you have to add the __physical__ parameter in _true_ to the _delete_ method:

<pre>
user.delete(physical: true) 
</pre>