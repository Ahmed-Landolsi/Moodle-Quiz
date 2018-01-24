package javaapplication2;
import java.io.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;



public class TestRead {
        TestRead(String text) {
            main(text);
        }
    public static void main(String file) {
        Pattern patternQuestion;
        Pattern patternAnswer;
        Pattern patternAnswerText;
        Pattern patternQuestionText;
        Pattern patternAnswerTextEnd;
        Pattern patternIsMC;
        Pattern patternIsTF;
        Pattern patternIsSA;
        Pattern patternIsNUM;
        Pattern patternCategory;
        Matcher matcherQuestion;
        Matcher matcherAnswer;
        Matcher matcherQuestionText;
        Matcher matcherAnswerText;
        Matcher matcherAnswerTextEnd;
        Matcher matcherIsMc;
        Matcher matcherIsTF;
        Matcher matcherIsSA;
        Matcher matcherIsNUM;
        Matcher matcherCategory;
        patternCategory = Pattern.compile("^%name=");
        patternQuestion = Pattern.compile("^%frage=");
        patternAnswer = Pattern.compile("^%antwort=");
        patternQuestionText = Pattern.compile("[^\\w]subsection");
        patternAnswerText = Pattern.compile("[^\\w]begin\\{itemize\\}");
        patternAnswerTextEnd = Pattern.compile("[^\\w]end\\{itemize\\}");
        patternIsMC = Pattern.compile("\"multichoice\"");
        patternIsTF = Pattern.compile("\"truefalse\"");
        patternIsSA = Pattern.compile("\"shortanswer\"");
        patternIsNUM = Pattern.compile("\"numerical\"");

        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            String category;
            ArrayList<List<String>> questionParams;
            ArrayList<List<String>> question;
            ArrayList<List<String>> response;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String strLine;
                category = "";
                int answerWriter = 0;
                int questionWriter = 0;
                boolean IsMcQuestion = false;
                boolean IsTFQuestion = false;
                boolean IsSAQuestion = false;
                boolean IsNUMQuestion = false;
                List<String> questionParamsArray = new ArrayList<>();
                List<String> questionArray = new ArrayList<>();
                List<String> answerArray = new ArrayList<>();
                questionParams = new ArrayList<>();
                question = new ArrayList<>();
                response = new ArrayList<>();
                while ((strLine = br.readLine()) != null) {
                    matcherQuestion = patternQuestion.matcher(strLine);
                    matcherAnswer = patternAnswer.matcher(strLine);
                    matcherAnswerText = patternAnswerText.matcher(strLine);
                    matcherQuestionText = patternQuestionText.matcher(strLine);
                    matcherAnswerTextEnd = patternAnswerTextEnd.matcher(strLine);
                    matcherIsMc = patternIsMC.matcher(strLine);
                    matcherIsTF = patternIsTF.matcher(strLine);
                    matcherIsSA = patternIsSA.matcher(strLine);
                    matcherIsNUM = patternIsNUM.matcher(strLine);
                    matcherCategory = patternCategory.matcher(strLine);
                    
                    if (questionWriter == 1) {
                        questionArray.add(strLine);
                    }
                    if (answerWriter == 1) {
                        answerArray.add(strLine);
                    }
                    if(matcherCategory.find()){
                        category = strLine.replace("%name=", "");
                        category = category.replace("/%","");
                    }
                    if(matcherQuestion.find()) { // findet %frage: ....
                        if (matcherIsMc.find()) { //findet "multichoice"
                            IsMcQuestion = true;
                            questionParamsArray.add(strLine);
                            questionParams.add(questionParamsArray);
                            questionParamsArray = new ArrayList<>();
                        }
                        if (matcherIsTF.find()) { //findet "truefalse"
                            IsTFQuestion = true;
                            questionParamsArray.add(strLine);
                            questionParams.add(questionParamsArray);
                            questionParamsArray = new ArrayList<>();
                        }
                        if (matcherIsSA.find()) { //findet "shortanswer"
                            IsSAQuestion = true;
                            questionParamsArray.add(strLine);
                            questionParams.add(questionParamsArray);
                            questionParamsArray = new ArrayList<>();
                        }
                        if (matcherIsNUM.find()) { //findet "numerical"
                            IsNUMQuestion = true;
                            questionParamsArray.add(strLine);
                            questionParams.add(questionParamsArray);
                            questionParamsArray = new ArrayList<>();
                        }
                    }
                    
                    if (IsMcQuestion || IsTFQuestion || IsSAQuestion || IsNUMQuestion) {
                        if (matcherQuestionText.find()) { //findet \subsection{}
                            questionWriter = 1;
                        }
                        if (matcherAnswerText.find()) { //findet \begin{itemize}
                            questionWriter = 0;
                            answerWriter = 1;
                            if (!questionArray.isEmpty()) {
                                questionArray.remove(questionArray.size() - 1);
                                question.add(questionArray);
                                questionArray = new ArrayList<>();
                            }
                            if (!answerArray.isEmpty()) {
                                answerArray.remove(answerArray.size() - 1);
                                response.add(answerArray);
                                answerArray = new ArrayList<>();
                            }
                        }
                        if (matcherAnswerTextEnd.find()) { //findet \end{itemize}
                            answerWriter = 0;
                            IsMcQuestion = false;
                            IsTFQuestion = false;
                            IsSAQuestion = false;
                            IsNUMQuestion = false;
                            if (!answerArray.isEmpty()) {
                                answerArray.remove(answerArray.size() - 1);
                                response.add(answerArray);
                                answerArray = new ArrayList<>();
                            }
                        }
                    }
                    
                }
            }
            List<String> myList = new ArrayList<>();
            Pattern patternxy = Pattern.compile("\\(([^)]+)\\)"); //read params between () in %frage=(...)/% Line
            questionParams.forEach((List<String> el) -> {
                el.stream().map((e) -> patternxy.matcher(e)).filter((matcherxy) -> (matcherxy.find())).map((matcherxy) -> matcherxy.group(1)).map((tmp) -> tmp.split(", ")).forEachOrdered((kk) -> {
                    // split question params with ","
                    myList.add(Arrays.toString(kk)); // add params array in one List of all questions params
                });
            });
            System.out.println("questParams final: " + myList);  // List of String myList.get(0) --> string contains first question params
            System.out.println("questions final: " + question);  // List of List of String question.get(0) --> list of string contains first question text
            System.out.println("responses final: " + response);  // List of List of String response.get(0) --> list of string contains first question answers
            Quiz quiz = new Quiz();
            quiz.setCategory(category);
            System.out.println("\n----------Quiz Startet-------------\n");
            for (int i = 0; i < myList.size(); i++) {
                if (myList.get(i).contains("multichoice")){
                    MultiChoiceQuestion MCQ = null;     
                    MCQ = MultichoiceFetcher(myList.get(i),question.get(i),response.get(i));
                    quiz.setQuestions(MCQ);
                } else if (myList.get(i).contains("truefalse")){
                    TrueFalseQuestion TFQ = null;     
                    TFQ = TrueFalseFetcher(myList.get(i),question.get(i),response.get(i));
                    quiz.setQuestions(TFQ);
                } else if (myList.get(i).contains("shortanswer")){
                    ShortAnswerQuestion SAQ = null;     
                    SAQ = ShortAnswerFetcher(myList.get(i),question.get(i),response.get(i));
                    quiz.setQuestions(SAQ);
                } else if (myList.get(i).contains("numerical")){
                    NumericalQuestion NUMQ = null;     
                    NUMQ = NumericalFetcher(myList.get(i),question.get(i),response.get(i));
                    quiz.setQuestions(NUMQ);
                }
                
            }
            OutputToXml(quiz);
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
    }
   
    public static MultiChoiceQuestion MultichoiceFetcher (String paramsList,  List<String> questionsList, List<String> answersList) throws IOException{
            Pattern pi;
            pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})"); //  detect {path}
            Pattern patternParamType = Pattern.compile("type=\"multichoice\"");
            MultiChoiceQuestion MCQ = null; 
            Matcher matcherParamType = patternParamType.matcher(paramsList);
            String[] split = paramsList.split(", ");
            String QuestionTextJoined = String.join("<br>", questionsList);
            String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join("<br>", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("%antwort=(.*?)/%");
            if (matcherParamType.find()) {
                MCQ = new MultiChoiceQuestion();
                //MCQ.setType("multichoice");
                MCQ.setQuestiontext(QuestionTextJoinedProcessed);
                if(mi.find()) {
                    List<String[]> imgs = imageAdapter(QuestionTextJoinedProcessed);


                    for (String[] img : imgs) {
                        Image image = new Image();
                        image.setName(img[0]);
                        image.setEncodedstring(img[1]);
                        MCQ.setQuestiontext(img[2]);
                        MCQ.setImages(image);
                    }
                }
                
            }
            
            for (String split1 : split) {
                String[] parameter = paramSplitter(split1);
                if (MCQ != null) {
                    if ("name".equals(parameter[0])) {
                        MCQ.setName(parameter[1]);
                    }
                    if ("single".equals(parameter[0])) {
                        boolean bool = Boolean.parseBoolean(parameter[1].trim());
                        MCQ.setSingle(bool);
                    }
                    if ("shuffle".equals(parameter[0])) {
                        MCQ.setShuffleanswers(Boolean.parseBoolean(parameter[1].trim()));
                    }
                    if ("format".equals(parameter[0])) {
                            MCQ.setFormat(parameter[1]);
                    }
                    if ("answernumbering".equals(parameter[0])) {
                        MCQ.setAnswernumbering(parameter[1]);
                    }
                    if ("correctfeedback".equals(parameter[0])) {
                        MCQ.setCorrectfeedback(parameter[1]);
                    }
                    if ("partiallycorrectfeedback".equals(parameter[0])) {
                        MCQ.setPartiallycorrectfeedback(parameter[1]);
                    }
                    if ("incorrectfeedback".equals(parameter[0])) {
                        MCQ.setIncorrectfeedback(parameter[1]);
                    }
                    if ("generalfeedback".equals(parameter[0])) {
                        MCQ.setGeneralfeedback(parameter[1]);
                    }
                    if ("hidden".equals(parameter[0])) {
                        MCQ.setHidden(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("defaultgrade".equals(parameter[0])) {
                        MCQ.setDefaultgrade(Float.parseFloat(parameter[1].trim()));
                    }
                    if ("penalty".equals(parameter[0])) {
                        MCQ.setPenalty(Float.parseFloat(parameter[1].trim()));
                    }
                   

                }
            }
            
            for (String answer : parsedString) {
                if (!answer.isEmpty() && !isNull(answer)) {
                    String newstr = answer.replaceAll("(<br>%antwort)[^&]*(\\%<br>)", "");
                    Answer answerObj = new Answer();
                    Matcher mia = pi.matcher(newstr); 
                    answerObj.setText("<p>" + newstr + "<br></p>\n");
                    if (mia.find()) {
                        List<String[]> imgs = imageAdapter(newstr);
                        imgs.forEach((img) -> {
                            Image image = new Image();
                            image.setName(img[0]);
                            image.setEncodedstring(img[1]);
                            answerObj.setText(img[2]);
                            answerObj.setImages(image);
                        });
                    }
                    Matcher m1 = p.matcher(answer);
                    while (m1.find()) {
                        String[] answerParams = new String[1];
                        answerParams[0] = m1.group(1);
                        if (answerParams[0].contains(",")) {
                            answerParams = m1.group(1).split(", ");
                        }
                        for (String ap : answerParams) {
                            String[] parameterAnswer = paramSplitter(ap);
                            if ("fraction".equals(parameterAnswer[0])) {
                                answerObj.setFraction(Integer.parseInt(parameterAnswer[1].trim()));
                            }
                            if ("format".equals(parameterAnswer[0])) {
                                answerObj.setFormat(parameterAnswer[1]);
                            }
                            if ("feedback".equals(parameterAnswer[0])) {
                                answerObj.setFeedback(parameterAnswer[1]);
                            }
                        }
                    }
                    if (MCQ != null) {
                        MCQ.setAnswer(answerObj);
                    }
                }
            }

            System.out.printf("\n--Begin question--\n");
            return MCQ;
    }
    
    public static TrueFalseQuestion TrueFalseFetcher (String paramsList,  List<String> questionsList, List<String> answersList) throws IOException{
            Pattern pi;
            pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})"); //  detect {path}
            Pattern patternParamType = Pattern.compile("type=\"truefalse\"");
            TrueFalseQuestion TFQ = null; 
            Matcher matcherParamType = patternParamType.matcher(paramsList);
            String[] split = paramsList.split(", ");
            String QuestionTextJoined = String.join("<br>", questionsList);
            String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join(" ", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("%antwort=(.*?)/%");
            if (matcherParamType.find()) {
                TFQ = new TrueFalseQuestion();
                TFQ.setQuestiontext(QuestionTextJoinedProcessed);
                if(mi.find()) {
                    List<String[]> imgs = imageAdapter(QuestionTextJoinedProcessed);


                    for (String[] img : imgs) {
                        Image image = new Image();
                        image.setName(img[0]);
                        image.setEncodedstring(img[1]);
                        TFQ.setQuestiontext(img[2]);
                        TFQ.setImages(image);
                    }
                }
                
            }
            
            for (String split1 : split) {
                String[] parameter = paramSplitter(split1);
                if (TFQ != null) {
                    if ("name".equals(parameter[0])) {
                        TFQ.setName(parameter[1]);
                    }
//                    if ("single".equals(parameter[0])) {
//                        boolean bool = Boolean.parseBoolean(parameter[1].trim());
//                        TFQ.setSingle(bool);
//                    }
//                    if ("shuffle".equals(parameter[0])) {
//                        TFQ.setShuffleanswers(Boolean.parseBoolean(parameter[1].trim()));
//                    }
                    if ("format".equals(parameter[0])) {
                        TFQ.setFormat(parameter[1]);
                    }
//                    if ("answernumbering".equals(parameter[0])) {
//                        TFQ.setAnswernumbering(parameter[1]);
//                    }
                    if ("correctfeedback".equals(parameter[0])) {
                        TFQ.setCorrectfeedback(parameter[1]);
                    }
                    if ("partiallycorrectfeedback".equals(parameter[0])) {
                        TFQ.setPartiallycorrectfeedback(parameter[1]);
                    }
                    if ("incorrectfeedback".equals(parameter[0])) {
                        TFQ.setIncorrectfeedback(parameter[1]);
                    }
                    if ("generalfeedback".equals(parameter[0])) {
                        TFQ.setGeneralfeedback(parameter[1]);
                    }
                    if ("hidden".equals(parameter[0])) {
                        TFQ.setHidden(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("defaultgrade".equals(parameter[0])) {
                        TFQ.setDefaultgrade(Float.parseFloat(parameter[1].trim()));
                    }
                    if ("penalty".equals(parameter[0])) {
                        TFQ.setPenalty(Float.parseFloat(parameter[1].trim()));
                    }
                   

                }
            }
            
            for (String answer : parsedString) {
                if (!answer.isEmpty() && !isNull(answer)) {
                    //System.out.println("answer befor:"+answer);
                    String newstr = answer.replaceAll("(%antwort)[^&]*(\\%)", "");
                    //System.out.println("answer after:"+answer);
                    Answer answerObj = new Answer();
                    //Matcher mia = pi.matcher(newstr);
                    //newstr = answer.replaceAll("<br>","");
                    answerObj.setText(newstr.trim());
//                    if (mia.find()) {
//                        List<String[]> imgs = imageAdapter(newstr);
//                        imgs.forEach((img) -> {
//                            Image image = new Image();
//                            image.setName(img[0]);
//                            image.setEncodedstring(img[1]);
//                            answerObj.setText(img[2]);
//                            answerObj.setImages(image);
//                        });
//                    }
                    Matcher m1 = p.matcher(answer);
                    while (m1.find()) {
                        String[] answerParams = new String[1];
                        answerParams[0] = m1.group(1);
                        if (answerParams[0].contains(",")) {
                            answerParams = m1.group(1).split(", ");
                        }
                        for (String ap : answerParams) {
                            String[] parameterAnswer = paramSplitter(ap);
                            if ("fraction".equals(parameterAnswer[0])) {
                                answerObj.setFraction(Integer.parseInt(parameterAnswer[1].trim()));
                            }
                            if ("format".equals(parameterAnswer[0])) {
                                answerObj.setFormat(parameterAnswer[1]);
                            }
                            if ("feedback".equals(parameterAnswer[0])) {
                                answerObj.setFeedback(parameterAnswer[1]);
                            }
                        }
                    }
                    if (TFQ != null) {
                        TFQ.setAnswer(answerObj);
                    }
                }
            }

            System.out.printf("\n--Begin question--\n");
            return TFQ;
    }
    
    public static ShortAnswerQuestion ShortAnswerFetcher (String paramsList,  List<String> questionsList, List<String> answersList) throws IOException{
            Pattern pi;
            pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})"); //  detect {path}
            Pattern patternParamType = Pattern.compile("type=\"shortanswer\"");
            ShortAnswerQuestion SAQ = null; 
            Matcher matcherParamType = patternParamType.matcher(paramsList);
            String[] split = paramsList.split(", ");
            String QuestionTextJoined = String.join("<br>", questionsList);
            String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join("<br>", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("%antwort=(.*?)/%");
            if (matcherParamType.find()) {
                SAQ = new ShortAnswerQuestion();
                //MCQ.setType("multichoice");
                SAQ.setQuestiontext(QuestionTextJoinedProcessed);
                if(mi.find()) {
                    List<String[]> imgs = imageAdapter(QuestionTextJoinedProcessed);


                    for (String[] img : imgs) {
                        Image image = new Image();
                        image.setName(img[0]);
                        image.setEncodedstring(img[1]);
                        SAQ.setQuestiontext(img[2]);
                        SAQ.setImages(image);
                    }
                }
                
            }
            
            for (String split1 : split) {
                String[] parameter = paramSplitter(split1);
                if (SAQ != null) {
                    if ("name".equals(parameter[0])) {
                        SAQ.setName(parameter[1]);
                    }
                    if ("usecase".equals(parameter[0])) {
                        SAQ.setUsecase(Integer.parseInt(parameter[1].trim()));
                    }
//                    if ("single".equals(parameter[0])) {
//                        boolean bool = Boolean.parseBoolean(parameter[1].trim());
//                        SAQ.setSingle(bool);
//                    }
//                    if ("shuffle".equals(parameter[0])) {
//                        SAQ.setShuffleanswers(Boolean.parseBoolean(parameter[1].trim()));
//                    }
                    if ("format".equals(parameter[0])) {
                        SAQ.setFormat(parameter[1]);
                    }
//                    if ("answernumbering".equals(parameter[0])) {
//                        SAQ.setAnswernumbering(parameter[1]);
//                    }
                    if ("correctfeedback".equals(parameter[0])) {
                        SAQ.setCorrectfeedback(parameter[1]);
                    }
                    if ("partiallycorrectfeedback".equals(parameter[0])) {
                        SAQ.setPartiallycorrectfeedback(parameter[1]);
                    }
                    if ("incorrectfeedback".equals(parameter[0])) {
                        SAQ.setIncorrectfeedback(parameter[1]);
                    }
                    if ("generalfeedback".equals(parameter[0])) {
                        SAQ.setGeneralfeedback(parameter[1]);
                    }
                    if ("hidden".equals(parameter[0])) {
                        SAQ.setHidden(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("defaultgrade".equals(parameter[0])) {
                        SAQ.setDefaultgrade(Float.parseFloat(parameter[1].trim()));
                    }
                    if ("penalty".equals(parameter[0])) {
                        SAQ.setPenalty(Float.parseFloat(parameter[1].trim()));
                    }
                   

                }
            }
            
            for (String answer : parsedString) {
                if (!answer.isEmpty() && !isNull(answer)) {
                    //<br>%antwort=(fraction=100, feedback="t'es bete ou quoi??") /%<br>
                    String newstr = answer.replaceAll("(<br>%antwort)[^&]*(\\%<br>)", "");
                    Answer answerObj = new Answer();
                    //Matcher mia = pi.matcher(newstr);
                    //newstr = answer.replaceAll("<br>","");
                    
                    answerObj.setText(newstr.trim());
//                    if (mia.find()) {
//                        List<String[]> imgs = imageAdapter(newstr);
//                        imgs.forEach((img) -> {
//                            Image image = new Image();
//                            image.setName(img[0]);
//                            image.setEncodedstring(img[1]);
//                            answerObj.setText(img[2]);
//                            answerObj.setImages(image);
//                        });
//                    }
                    Matcher m1 = p.matcher(answer);
                    while (m1.find()) {
                        String[] answerParams = new String[1];
                        answerParams[0] = m1.group(1);
                        if (answerParams[0].contains(",")) {
                            answerParams = m1.group(1).split(", ");
                        }
                        for (String ap : answerParams) {
                            String[] parameterAnswer = paramSplitter(ap);
                            if ("fraction".equals(parameterAnswer[0])) {
                                answerObj.setFraction(Integer.parseInt(parameterAnswer[1].trim()));
                            }
                            if ("format".equals(parameterAnswer[0])) {
                                answerObj.setFormat(parameterAnswer[1]);
                            }
                            if ("feedback".equals(parameterAnswer[0])) {
                                answerObj.setFeedback(parameterAnswer[1]);
                            }
                        }
                    }
                    if (SAQ != null) {
                        SAQ.setAnswer(answerObj);
                    }
                }
            }

            System.out.printf("\n--Begin question--\n");
            return SAQ;
    }
    
    public static NumericalQuestion NumericalFetcher (String paramsList,  List<String> questionsList, List<String> answersList) throws IOException{
            Pattern pi;
            pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})"); //  detect {path}
            Pattern patternParamType = Pattern.compile("type=\"numerical\"");
            NumericalQuestion NUMQ = null; 
            Matcher matcherParamType = patternParamType.matcher(paramsList);
            String[] split = paramsList.split(", ");
            String QuestionTextJoined = String.join("<br>", questionsList);
            String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join("<br>", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("%antwort=(.*?)/%");
            if (matcherParamType.find()) {
                NUMQ = new NumericalQuestion();
                //MCQ.setType("multichoice");
                NUMQ.setQuestiontext(QuestionTextJoinedProcessed);
                if(mi.find()) {
                    List<String[]> imgs = imageAdapter(QuestionTextJoinedProcessed);


                    for (String[] img : imgs) {
                        Image image = new Image();
                        image.setName(img[0]);
                        image.setEncodedstring(img[1]);
                        NUMQ.setQuestiontext(img[2]);
                        NUMQ.setImages(image);
                    }
                }
                
            }
            
            for (String split1 : split) {
                String[] parameter = paramSplitter(split1);
                if (NUMQ != null) {
                    if ("name".equals(parameter[0])) {
                        NUMQ.setName(parameter[1]);
                    }
                    if ("units".equals(parameter[0])) {
                        String[] strunit = new String[1];
                        if(parameter[1].contains("/")){
                            strunit = parameter[1].split("/");
                        }else{
                            strunit[0] = parameter[1];
                        }
                        //Unit[] unitarray;
                        for (String strunit1 : strunit) {
                            Unit unit = new Unit();
                            String[] str2 = strunit1.split(":");
                            unit.setUnit_name(str2[0].trim());
                            unit.setMultiplier(Integer.parseInt(str2[1]));
                            //if(unit != null){
                            NUMQ.setUnits(unit);
                            //}
                        }
                    }
                    if ("unitgradingtype".equals(parameter[0])) {
                        NUMQ.setUnitgradingtype(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("unitpenalty".equals(parameter[0])) {
                        NUMQ.setUnitpenalty(Float.parseFloat(parameter[1].trim()));
                    }                  
                    if ("showunits".equals(parameter[0])) {
                        NUMQ.setShowunits(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("unitsleft".equals(parameter[0])) {
                        NUMQ.setUnitsleft(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("format".equals(parameter[0])) {
                        NUMQ.setFormat(parameter[1]);
                    }
                    if ("correctfeedback".equals(parameter[0])) {
                        NUMQ.setCorrectfeedback(parameter[1]);
                    }
                    if ("partiallycorrectfeedback".equals(parameter[0])) {
                        NUMQ.setPartiallycorrectfeedback(parameter[1]);
                    }
                    if ("incorrectfeedback".equals(parameter[0])) {
                        NUMQ.setIncorrectfeedback(parameter[1]);
                    }
                    if ("generalfeedback".equals(parameter[0])) {
                        NUMQ.setGeneralfeedback(parameter[1]);
                    }
                    if ("hidden".equals(parameter[0])) {
                        NUMQ.setHidden(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("defaultgrade".equals(parameter[0])) {
                        NUMQ.setDefaultgrade(Float.parseFloat(parameter[1].trim()));
                    }
                    if ("penalty".equals(parameter[0])) {
                        NUMQ.setPenalty(Float.parseFloat(parameter[1].trim()));
                    }
                   

                }
            }
            
            for (String answer : parsedString) {
                if (!answer.isEmpty() && !isNull(answer)) {
                    //<br>%antwort=(fraction=100, feedback="t'es bete ou quoi??") /%<br>
                    String newstr = answer.replaceAll("(<br>%antwort)[^&]*(\\%<br>)", "");
                    AnswerNumerical answerObj = new AnswerNumerical();
                    //Matcher mia = pi.matcher(newstr);
                    //newstr = answer.replaceAll("<br>","");
                    
                    answerObj.setText(newstr.trim());
//                    if (mia.find()) {
//                        List<String[]> imgs = imageAdapter(newstr);
//                        imgs.forEach((img) -> {
//                            Image image = new Image();
//                            image.setName(img[0]);
//                            image.setEncodedstring(img[1]);
//                            answerObj.setText(img[2]);
//                            answerObj.setImages(image);
//                        });
//                    }
                    Matcher m1 = p.matcher(answer);
                    while (m1.find()) {
                        String[] answerParams = new String[1];
                        answerParams[0] = m1.group(1);
                        if (answerParams[0].contains(",")) {
                            answerParams = m1.group(1).split(", ");
                        }
                        for (String ap : answerParams) {
                            String[] parameterAnswer = paramSplitter(ap);
                            if ("fraction".equals(parameterAnswer[0])) {
                                answerObj.setFraction(Integer.parseInt(parameterAnswer[1].trim()));
                            }
                            if ("format".equals(parameterAnswer[0])) {
                                answerObj.setFormat(parameterAnswer[1]);
                            }
                            if ("feedback".equals(parameterAnswer[0])) {
                                answerObj.setFeedback(parameterAnswer[1]);
                            }
                            if ("tolerance".equals(parameterAnswer[0])) {
                                answerObj.setTolerance(Integer.parseInt(parameterAnswer[1].trim()));
                            }
                        }
                    }
                    if (NUMQ != null) {
                        NUMQ.setAnswernum(answerObj);
                    }
                }
            }

            System.out.printf("\n--Begin question--\n");
            return NUMQ;
    }
    
    
    
    public static String[] paramSplitter(String str){
        String paramstring;
        paramstring = str;
        String replace = paramstring.replaceAll("\"|\\[|\\]|\\(|\\)", "");
        String[] params = replace.split("=");
        return params;
    }
    public static void OutprintObject(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.printf("%s: %s%n", name, value);
        }
    }
    public static String StringProcessed (String str){
        String st = "<text>"+str+"</text>";
        return st;
    }

    private static void OutputToXml(Quiz quiz) {
        try {
            File file = new File("C:\\Users\\foufou\\Desktop\\file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Quiz.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(quiz, file);
        } catch (JAXBException e) {
            System.err.println("Error in marshalling..."+e.toString());
        }  
    }

    
    private static List<String[]> imageAdapter(String str) throws IOException{
        List<String[]> list = new ArrayList<>();
        Pattern pi;
        pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})");
        Matcher mi = pi.matcher(str);
        while(mi.find()){
        String[] imageAr = new String[3];
        String ImageParams = getImageParams(mi.group(2));
        String tmp1 = mi.group(3);
        String base46Image = encoder(tmp1);
        File f = new File(tmp1);
        imageAr[0] = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1);
        imageAr[1] = base46Image;
        str = str.replaceFirst("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})", "<p style=\"display:inline;\"><img src=\"@@PLUGINFILE@@/"+imageAr[0]+"\" role=\"presentation\" style=\"vertical-align:middle; margin:0.5em;\" "+ImageParams+"/></p>"); 
        imageAr[2] = str;
        list.add(imageAr);
        }
        return list;       
    }
    
    public static String encoder(String imagePath) throws MalformedURLException, IOException {
	String base64Image = "";
        if(imagePath.contains("https:")|| imagePath.contains("http:")){
            URL imageURL = new URL(imagePath);
            System.out.println(imageURL);
            OutputStream out;
            try (InputStream in = new BufferedInputStream(imageURL.openStream())) {
                out = new BufferedOutputStream(new FileOutputStream("downloaded-image.png"));
                for (int i; (i = in.read()) != -1;) {
                    out.write(i);
                }
            }
            out.close();

            File file = new File("downloaded-image.png");
            try (FileInputStream imageInFile = new FileInputStream(file)) {
                byte imageData[] = new byte[(int) file.length()];
                imageInFile.read(imageData);
                base64Image = Base64.getEncoder().encodeToString(imageData);
            } catch (FileNotFoundException e) {
                System.out.println("Image not found" + e);
            } catch (IOException ioe) {
                System.out.println("Exception while reading the Image " + ioe);
            }
        
        } else {
            File file = new File(imagePath);
            try (FileInputStream imageInFile = new FileInputStream(file)) {
                byte imageData[] = new byte[(int) file.length()];
                imageInFile.read(imageData);
                base64Image = Base64.getEncoder().encodeToString(imageData);
            } catch (FileNotFoundException e) {
                System.out.println("Image not found" + e);
            } catch (IOException ioe) {
                System.out.println("Exception while reading the Image " + ioe);
            }

        }
	return base64Image;
    }
    
    public static String getImageParams (String paramString){
        String htmlParamString = paramString;
        String[] tab =  new String[1];
        String FinalString = "";
        if (htmlParamString.contains(",")) {
            tab = htmlParamString.split(",");
            for (String ip : tab) {
                String[] parameterImage = paramSplitter(ip);
                for (String tmp : parameterImage) {
                    if (tmp.contains("heigth")) {
                        FinalString += " heigth=" + convertImageParams(parameterImage[1]);
                    }
                    if (tmp.contains("width")) {
                        FinalString += " width=" + convertImageParams(parameterImage[1]);
                    }
                }
            }
        }else{
            tab[0] = htmlParamString;
            String[] parameterImage = paramSplitter(tab[0]);
            FinalString += parameterImage[0].trim() + "=" + convertImageParams(parameterImage[1]);
        }
        return FinalString;
    }
    
    public static String convertImageParams (String paramstring){
        Pattern p; 
        p = Pattern.compile("(\\d*)([A-z].*)");
        Matcher m = p.matcher(paramstring.trim());
        String Result = "";
        if (m.find()){
            Double f = 3.7795275590551 ;
            Double i = Double.parseDouble(m.group(1).trim());
            switch (m.group(2)) {
                case "mm":
                    f = ( 3.7795275590551 * i);
                    break;
                case "cm":
                    f = ( 37.795275590551 * i);
                    break;
                case "pt":
                    f = ( 1.328352755905505446 * i);
                    break;
                case "in":
                    f = ( 95.99999999999954 * i);
                    break;
                default:
                    break;
            }
            int finalint = (int) Math.round(f);
            Result += "\""+Integer.toString(finalint)+"\"";
        }
        return Result;
    }

//      private void updateTextArea(final String text) {

//      
//      }

}
