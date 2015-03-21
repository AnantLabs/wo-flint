# Implement Autocomplete Using Flint #

Flint is a lightweight yet powerful library that you can use to do many search related things, in this document, we'll show you how to use flint to implement the autocomplete for an input field of a HTML page.


## Prerequisite ##

In order to embed autocomplete function in a web application, the server-side scripts that provide the search results must first be implemented. This Flint code provides the functionality to return similar terms or terms with the same prefix as the user types. A copy of this Java code using Flint is available at:

```
http://code.google.com/p/wo-flint/source/browse/trunk/flaubert/src/org/weborganic/flaubert/AutoCompleteServlet.java
```

On the client side, the requirements are for [jQuery](http://jquery.com/), the jQuery autocomplete library and CSS file (available from [Pengoworks](http://www.pengoworks.com/workshop/jquery/autocomplete.htm), filename: `jquery.autocomplete.js` and `jquery.autocomplete.css`).

## Assumptions ##

It is assumed that any developer implementing this solution will know how to:
<ul>
<li>compile the java file and add it into the <code>classpath</code> of the webapp</li>
<li>configure the <code>web.xml</code> to make the URL mapping to the right servlet</li>
</ul>

## Overview ##

The autocomplete-dropdown has two portions of implementation: the client side JavaScript and the server side Servlet. When a user types something in the input field of a web page, the JavaScript will be activated and sends a request to the Servlet, the Servlet will do a wildcard search in the Lucene index using the predicate composed from the request as prefix, and then return all the possible values of the result starts with the prefix, and then the client side JavaScript will format the results and creates a inner HTML dropdown and display the results in it.

For example, if the user form has an input field like this: `<input name="artist-name">` When the user types in 'ste', the client-side JavaScript will create an URL for autocomplete: `http://[hostname]/autocomplete/artist-name=ste`, once the server-side Servlet receives this request, it will do a wildcard search using term 'ste**' in the field `'artist-name'` in the index, all possible values will be returned, such as: stefan, stefanie, steve, etc. Finally, the client-side JavaScript will do formatting the results, and placing them in the drop-down list.**

## How to use ##

Once you get the servlet running, you just need to include the above files (`*`.js & `*`.css) in the HTML, and embed a Jquery snippet (let's call it autocompleter) to invoke the autocomplete. For example,
```
  $(document).ready(function() {
    $("SELECTOR").autocomplete(URL [, OPTIONS]);
  });
```

_**SELECTOR**_: the input element you want the autocomplete to work on<br>
<i><b>URL</b></i>: the server-side script dealing with the request and return the result<br>
<i><b>OPTIONS</b></i>: a few options provided by the jquery autocomplete library, see <a href='http://www.pengoworks.com/workshop/jquery/autocomplete_docs.txt'>http://www.pengoworks.com/workshop/jquery/autocomplete_docs.txt</a> for details<br>

To customize the format of the result, add an option <code>formatItem</code> in the autocompleter and append the JavaScript to execute to the end of the option, separated by a colon.<br>
<br>
<pre><code>  $(document).ready(function() {<br>
    $("SELECTOR").autocomplete(URL, <br>
     {<br>
       formatItem:formatItem,<br>
     }<br>
    );<br>
  });<br>
<br>
  function formatItem() {<br>
    // do formatting<br>
  }<br>
</code></pre>

To avoid an excessively long result list, add this option: maxItemsToShow<br>
<br>
<pre><code>  $(document).ready(function() {<br>
    $("SELECTOR").autocomplete(URL, <br>
     {<br>
       // limit the result list to 10<br>
       maxItemsToShow:10,<br>
     }<br>
    );<br>
  });<br>
</code></pre>

To add extra parameters to the request, use the "extraParam" option:<br>
<br>
<pre><code>  $(document).ready(function() {<br>
    $("SELECTOR").autocomplete(URL, <br>
     {<br>
       // add extra params such as groupid=1798&amp;document_type=*control<br>
       extraParams:{groupid:1798,document_type:"*control"},<br>
     }<br>
    );<br>
  });<br>
</code></pre>