# Moodle-Quiz
@Autor: Landolsi Ahmed

This is a Java Project that convert Latex File, wich has to be decorated
with special annotation, to an Xml file that can be used/imported as a 
quiz to Moddle e-Learning plattform.

The converter is actually able to convert just quiz containing multiple
choice question.

The "Latex-v.text" file is an example of a Latex file structure, the 
annotations that have to be added befor converting are the following:

1- %name= Quizname /%   
  ---  used to set a name for the quiz  ---
2- %frage= (param1="", param2="", ...) /%   
      ---  used befor the question text section to set the question parameters ---
3- %antwort= (param1="", param2="", ...) /%   
      ---  used after the answer text item text section to set the question parameters ---
