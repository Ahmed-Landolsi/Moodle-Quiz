# Moodle-Quiz


This is a Java Project that convert Latex File, wich has to be decorated
with special annotation, to an Xml file that can be used/imported as a 
quiz to Moddle e-Learning plattform.

The converter is actually able to convert just quiz containing multiple
choice question.

The "Latex-v.text" file is an example of a Latex file structure, the 
annotations that have to be added befor converting are the following:

1- %name= Quizname /%   <br>
---  used to set a name for the quiz  --- <br>
2- %frage= (param1="", param2="", ...) /% <br>
---  used befor the question text section to set the question parameters --- <br>
3- %antwort= (param1="", param2="", ...) /% <br>
---  used after the answer text item text section to set the question parameters --- <br>
