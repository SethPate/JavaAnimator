# JavaAnimator
A Java Animator built for an object-oriented design class at Northeastern University.

This Animator takes in a text file of animation instructions representing shapes, colors, and several kinds of transformations.
The Animator stores this information in an abstract format, using the IShape and ITransformation interfaces.
The Animator can output each animation as an SVG, a Swing graphic in a GUI, or a textual representation.
The user can use the command line to specify a speed (ticks per second), output format, and input file location.

This program was meant as an exercise to teach the MVC (Model, View, Controller) concept, as well as design patterns such as Factory and Builder.

Almost all of this code is my own work. However, some code in the TweenModelBuilder and AnimationFileReader classes were supplied by the course instructor, Vidoje Mihajlovic.
