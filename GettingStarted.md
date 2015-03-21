# Using **Flint** - A Simple Example #

What **Flint** is trying to achieve is simplify is the task of loading data into Lucene. Although this task is not overly difficult without Flint, it can still be a steep learning curve depending on the complexity of the data.

What Flint provides is an easily understood abstraction and code that makes it easy to prepare and "pre-flight" data prior to indexing it. This helps developers to:

  * understand what the Lucene index will look like.
  * use XML validation and QA technology to check the data before indexing.
  * provides a logical point to make Lucene-specific improvements to the data before indexing.

Hopefully the following example will help developers to understand how Flint can add value and improve productivity when loading data into Apache's Lucene.

# Prerequisite #
Use a Subversion (SVN) client, such as the following, to checkout the example code:<br>
<ul><li><a href='http://subclipse.tigris.org/update_1.6.x'>Subclipse</a><br>
</li><li><a href='http://community.polarion.com/projects/subversive/download/eclipse/2.0/update-site/'>Subversive</a><br>
OR<br>
Download the <a href='http://code.google.com/p/wo-flint/downloads/list'>zip</a> file and import it into your development environment, say, Eclipse.</li></ul>

<h1>How to write data to a Lucene index</h1>
Flint depends on data being converted into an intermediary XML format, which we call Index XML or <b>IXML</b>. IXML is simply an XML representation of how records are stored into Lucene.<br>
Here is an example of source document<br>
<i><b>example.xml</b></i>
<pre><code>&lt;example&gt;<br>
  &lt;artists&gt;<br>
    &lt;artist&gt;<br>
      &lt;artist.id&gt;12625&lt;/artist.id&gt;<br>
      &lt;artist.about&gt;bio etc&lt;/artist.about&gt;<br>
      &lt;artist.URL&gt;MySpace URL&lt;/artist.URL&gt;<br>
      &lt;artist.name&gt;JO MEARES AND THE HONEYRIDERS&lt;/artist.name&gt;<br>
      &lt;artist.altname1&gt;JO MEARES&lt;/artist.altname1&gt;<br>
      &lt;artist.altid1&gt;5851&lt;/artist.altid1&gt;<br>
      &lt;artist.altname2&gt;JO MEARS&lt;/artist.altname2&gt;<br>
      &lt;artist.altid2&gt;5852&lt;/artist.altid2&gt;<br>
    &lt;/artist&gt;<br>
    &lt;artist&gt;<br>
      &lt;artist.id&gt;10642&lt;/artist.id&gt;<br>
      &lt;artist.about&gt;bio etc&lt;/artist.about&gt;<br>
      &lt;artist.URL&gt;MySpace URL&lt;/artist.URL&gt;<br>
      &lt;artist.name&gt;THE MULES&lt;/artist.name&gt;<br>
    &lt;/artist&gt;<br>
    &lt;artist&gt;<br>
      &lt;artist.id&gt;4937&lt;/artist.id&gt;<br>
      &lt;artist.about&gt;bio etc&lt;/artist.about&gt;<br>
      &lt;artist.URL&gt;MySpace URL&lt;/artist.URL&gt;<br>
      &lt;artist.name&gt;GOLDEN MEAN&lt;/artist.name&gt;<br>
      &lt;artist.rel1&gt;18023&lt;/artist.rel1&gt;<br>
      &lt;artist.rel1.desc&gt;bass player left Golden Mean and went to this band<br>
      &lt;/artist.rel1.desc&gt;<br>
    &lt;/artist&gt;<br>
  &lt;/artists&gt;<br>
  &lt;events&gt;<br>
    &lt;event&gt;<br>
      &lt;event.id&gt;27526&lt;/event.id&gt;<br>
      &lt;event.date&gt;12022009&lt;/event.date&gt;<br>
      &lt;event.venue&gt;493&lt;/event.venue&gt;<br>
      &lt;event.artist1&gt;12625&lt;/event.artist1&gt;<br>
      &lt;event.artist2&gt;10642&lt;/event.artist2&gt;<br>
      &lt;event.artist3&gt;4937&lt;/event.artist3&gt;<br>
      &lt;event.title&gt;&lt;/event.title&gt;<br>
      &lt;event.artwork&gt;&lt;/event.artwork&gt;<br>
    &lt;/event&gt;<br>
  &lt;/events&gt;<br>
  &lt;venues&gt;<br>
    &lt;venue&gt;<br>
      &lt;venue.id&gt;493&lt;/venue.id&gt;<br>
      &lt;venue.name&gt;Excelsior Hotel&lt;/venue.name&gt;<br>
      &lt;venue.www_url&gt;http://www.excelsiorhotel.com.au/&lt;/venue.www_url&gt;<br>
      &lt;venue.location_url&gt;http://maps.google.com.au/places/au/surry-hills/foveaux-st/64/-excelsior-hotel&lt;/venue.location_url&gt;<br>
      &lt;venue.contact&gt;&lt;/venue.contact&gt;<br>
      &lt;venue.street_addr&gt;&lt;/venue.street_addr&gt;<br>
      &lt;venue.postcode&gt;&lt;/venue.postcode&gt;<br>
    &lt;/venue&gt;<br>
  &lt;/venues&gt;<br>
&lt;/example&gt;<br>
</code></pre>

This is the same data transformed into IXML<br>
<i><b>example.xml</b></i> <code>[ixml format]</code>
<pre><code>&lt;?xml version="1.0" encoding="utf-8"?&gt;<br>
&lt;!DOCTYPE documents PUBLIC "-//Weborganic//DTD::Flint Index Document 1.0//EN" "http://www.weborganic.org/code/flint/schema/index-documents-1.0.dtd"&gt;<br>
&lt;documents&gt;<br>
  &lt;document&gt;<br>
    &lt;field name="id" store="yes" index="un-tokenized"&gt;ARTIST-12625&lt;/field&gt;<br>
    &lt;field name="type" store="yes" index="un-tokenized"&gt;ARTIST&lt;/field&gt;<br>
    &lt;field name="name" store="yes" index="un-tokenized"&gt;JO MEARES AND THE HONEYRIDERS&lt;/field&gt;<br>
    &lt;field name="url" store="yes" index="un-tokenized"&gt;MySpace URL&lt;/field&gt;<br>
    &lt;field name="about" store="yes" index="tokenized"&gt;bio etc&lt;/field&gt;<br>
    &lt;field name="fulltext" store="no" index="tokenized"&gt;JO MEARES AND THE HONEYRIDERSbio etc&lt;/field&gt;<br>
  &lt;/document&gt;<br>
  &lt;document&gt;<br>
    &lt;field name="id" store="yes" index="un-tokenized"&gt;ARTIST-10642&lt;/field&gt;<br>
    &lt;field name="type" store="yes" index="un-tokenized"&gt;ARTIST&lt;/field&gt;<br>
    &lt;field name="name" store="yes" index="un-tokenized"&gt;THE MULES&lt;/field&gt;<br>
    &lt;field name="url" store="yes" index="un-tokenized"&gt;MySpace URL&lt;/field&gt;<br>
    &lt;field name="about" store="yes" index="tokenized"&gt;bio etc&lt;/field&gt;<br>
    &lt;field name="fulltext" store="no" index="tokenized"&gt;THE MULESbio etc&lt;/field&gt;<br>
  &lt;/document&gt;<br>
  &lt;document&gt;<br>
    &lt;field name="id" store="yes" index="un-tokenized"&gt;ARTIST-4937&lt;/field&gt;<br>
    &lt;field name="type" store="yes" index="un-tokenized"&gt;ARTIST&lt;/field&gt;<br>
    &lt;field name="name" store="yes" index="un-tokenized"&gt;GOLDEN MEAN&lt;/field&gt;<br>
    &lt;field name="url" store="yes" index="un-tokenized"&gt;MySpace URL&lt;/field&gt;<br>
    &lt;field name="about" store="yes" index="tokenized"&gt;bio etc&lt;/field&gt;<br>
    &lt;field name="fulltext" store="no" index="tokenized"&gt;GOLDEN MEANbio etc&lt;/field&gt;<br>
  &lt;/document&gt;<br>
  &lt;document&gt;<br>
    &lt;field name="id" store="yes" index="un-tokenized"&gt;EVENT-27526&lt;/field&gt;<br>
    &lt;field name="type" store="yes" index="un-tokenized"&gt;EVENT&lt;/field&gt;<br>
    &lt;field name="name" store="yes" index="un-tokenized"/&gt;<br>
    &lt;field name="date" store="yes" index="un-tokenized" parse-date-as="yyyyMMdd" resolution="day"&gt;12022009&lt;/field&gt;<br>
    &lt;field name="artist" store="no" index="un-tokenized"&gt;12625&lt;/field&gt;<br>
    &lt;field name="artist" store="no" index="un-tokenized"&gt;10642&lt;/field&gt;<br>
    &lt;field name="artist" store="no" index="un-tokenized"&gt;4937&lt;/field&gt;<br>
    &lt;field name="fulltext" store="no" index="tokenized"/&gt;<br>
  &lt;/document&gt;<br>
  &lt;document&gt;<br>
    &lt;field name="id" store="yes" index="un-tokenized"&gt;VENUE-493&lt;/field&gt;<br>
    &lt;field name="type" store="yes" index="un-tokenized"&gt;VENUE&lt;/field&gt;<br>
    &lt;field name="name" store="yes" index="un-tokenized"&gt;Excelsior Hotel&lt;/field&gt;<br>
    &lt;field name="url" store="yes" index="un-tokenized"&gt;http://www.excelsiorhotel.com.au/&lt;/field&gt;<br>
    &lt;field name="location" store="yes" index="tokenized"&gt;http://maps.google.com.au/places/au/surry-hills/foveaux-st/64/-excelsior-hotel&lt;/field&gt;<br>
    &lt;field name="fulltext" store="no" index="tokenized"/&gt;<br>
  &lt;/document&gt;<br>
&lt;/documents&gt;<br>
</code></pre>

This transformation can be done with any standard XML processing tool, such as XSLT. The XSLT for this example can be found under the example project folder.<br>

<h2>Generate the index file</h2>
In order to generate the Lucene index file based on these IXML files, Flint has a Java class is written to accomplish this job. It can be triggered from the command line.<br>
<pre><code>java org.weborganic.flint.Indexer -index [Lucene index file output folder] -file [ixml files used to generate the index]<br>
</code></pre>

Note: You might need to specified class path in order to trigger from the command line.<br>
<br>
<pre><code>java -cp log4j-*.jar;lucene-core-*.jar;flint-*.jar <br>
org.weborganic.flint.Indexer -index [Lucene index file output folder] -file [ixml files used to generate the index]<br>
</code></pre>

<h2>Try It</h2>
An ANT script is written to make this process simpler to manage. The script simply convert the source xml into IXML format, and then calls the Indexer class to generate the Lucene index based on the IXML input.<br>
<br>
In order to run the process, just run the default ANT task named "index", the index files will be generated in the index folder, which can be specified in the command-line argument, see above for details.