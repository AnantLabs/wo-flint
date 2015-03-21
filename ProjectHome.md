## Overview ##

**Flint** is an XML representation of Lucene's internal data model and a handful of java classes that make it easy to read and write data to/from a Lucene index. It also provides some simple classes to aggregate Lucene queries.

The objective of Flint is simply to provide another way of interacting with Lucene. For developers comfortable with XML processing models and transformations, Flint may feel more familiar and therefore be easier to learn.

The Flint model also provide a logical stage for making Lucene-specific improvements to source data.

## Dependencies ##

This project requires Lucene http://lucene.apache.org.

We are trying to minimise dependencies, but this project also requires:
  * SLF4J http://www.slf4j.org/.
  * Diffx http://weborganic.org/code/diffx/.

## We have upgraded Flint! ##

Flint now works with Lucene 3 API.

The old Lucene 2.x release is now deprecated and will no longer be maintained.

