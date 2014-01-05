<h1>Name Entity Recogniser for Java</h2>
<h3>Introduction</h3>
<p>This is a simple Name Entity Recognizer(NER) for Java. The objective of the code is to parse a given sentence and come up with all the possible combinations of the entities.</p>

<h3>Input</h3>
<p>Input can be a sentence of any size that contains named entities,say, <strong>Remember the Titans was a movie directed by Boaz Yakin</strong>.

<h3>Output</h3>
<p>The output of the NER would be all the possible combinations of the entities in the sentence:
<ul>
<li>{Remember the Titans,Movie} was {a movie,Movie} directed by {Boaz Yakin,director}</li>
<li>{Remember the Titans,Movie} was a movie directed by Boaz Yakin</li>
<li>{Remember the Titans,Movie} was {a movie,Movie} directed by Boaz Yakin</li>
<li>{Remember the Titans,Movie} was a movie directed by {Boaz Yakin,director}</li>
<li>Remember the Titans was {a movie,Movie} directed by Boaz Yakin</li>
<li>Remember the Titans was {a movie,Movie} directed by {Boaz Yakin,director}</li>
<li>Remember the Titans was a movie directed by {Boaz Yakin,director}</li>
<li>Remember the {the titans,Movie,Sports Team} was {a movie,Movie} directed by {Boaz Yakin,director}</li>
<li>Remember the {the titans,Movie,Sports Team} was a movie directed by Boaz Yakin</li>
<li>Remember the {the titans,Movie,Sports Team} was {a movie,Movie} directed by Boaz Yakin</li>
<li>Remember the {the titans,Movie,Sports Team} was a movie directed by {Boaz Yakin,director}</li>
</ul>

<h3>Solution Approaches</h3>
<p>When the project was kicked off one of the several different approaches that came up was to keep a lookup table for all the know connector words like articles and conjunctions, remove them from the words list after splitting the sentence on the basis of the spaces. This would leave out the Name Entities in the sentence.A lookup is then done for these identified entities on another lookup table that associates them to the entity type.</p>

The entity lookup table here would contain the following data:
<ul>
<li>Remember the Titans=>Movie</li>
<li>a movie=>Movie</li>
<li>Boaz Yakin=>director</li>
<li>the Titans=>Movie</li>
<li>the Titans=>Sports Team</li>
</ul>

<p>Another alternative logic that was put forward was to build a crude sentence tree that would contain the connector words in the lookup table as parent nodes and do a lookup in the entity table for the leaf node that might contain the entities.This is the current logic followed to implement the NER here.</p>

