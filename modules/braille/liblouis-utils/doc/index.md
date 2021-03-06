<link rel="dp2:permalink" href="http://daisy.github.io/pipeline/Get-Help/User-Guide/Braille/Liblouis/">
<link rev="dp2:doc" href="../src/main/java/org/daisy/pipeline/braille/liblouis/impl/LiblouisTranslatorJnaImplProvider.java"/>
<link rel="rdf:type" href="http://www.daisy.org/ns/pipeline/userdoc"/>

# Liblouis based braille transcription

[Liblouis][] based
[translators](http://daisy.github.io/pipeline/Get-Help/User-Guide/Braille/#braille-transcription)
can be selected using a query that contains
`(translator:liblouis)(table:...)` or
`(liblouis-table:...)`. Recognized features are:

<!-- id: If present it must be the only feature. Will match a transformer with a unique ID. -->

`translator`
: Will only match if the value is "liblouis".

`hyphenator`
: A value "none" will disable hyphenation. "liblouis" will match only
  Liblouis translators that support hyphenation out-of-the-box. "auto"
  is the default and will match any Liblouis translator, whether it
  supports hyphenation out-of-the-box, with the help of an external
  [hyphenator](http://daisy.github.io/pipeline/Get-Help/User-Guide/Braille/#hyphenation),
  or not at all. A value not equal to "none", "liblouis" or "auto"
  will match every Liblouis translator that uses an external
  hyphenator that matches this feature. A translator will only use
  external hyphenators with the same locale as the translator itself.

`table`
`liblouis-table`
: A Liblouis table is a list of URIs that can be either a file name, a
  file path relative to a registered table path, an absolute file URI,
  or a fully qualified table identifier. The table path that contains
  the first "sub-table" in the list will be used as the base for
  resolving the subsequent sub-tables. The default Liblouis tables are
  available under the path
  [`http://www.liblouis.org/tables/`](../src/main/resources/default-tables/). See
  also [this overview of
  tables](https://github.com/liblouis/liblouis/blob/master/extra/generate-display-names/display-names)
  available in Liblouis. The `liblouis-table` feature is not
  compatible with other features except `translator`, `hyphenator` and
  `locale`.

`locale`
: Matches only Liblouis translators with this locale.

<!-- handle-non-standard-hyphenation
     : Specifies how non-standard hyphenation is handled in pre-translation
       mode. Can be "ignore", "defer" or "fail". -->

`dots-for-undefined-char`
: The fixed dot pattern to insert for unknown characters. Must be a
  Unicode braille string.

The remaining features are used for selecting a specific table using
Liblouis'
[`lou_findTable`](http://liblouis.org/documentation/liblouis.html#lou_005ffindTable)
function. Available features are:

- `type`
- `contraction`
- `grade`
- `dots`
- `system`
- `variant`

These are described in more detail
[here](https://github.com/liblouis/liblouis/wiki/Table-discovery-based-on-table-metadata#standard-metadata-tags).


[Liblouis]: http://liblouis.org/
