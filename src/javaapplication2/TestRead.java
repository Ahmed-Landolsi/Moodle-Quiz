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
import javax.swing.JOptionPane;



public class TestRead {
    TestRead(String text, String output) {
            main(text, output);
        }
    public static void main(String file, String output) {
        String outputpath = output;
        Pattern patternQuestion;
        Pattern patternAnswer;
        Pattern patternAnswerText;
        Pattern patternQuestionText;
        Pattern patternAnswerTextEnd;
        Pattern patternIsMC;
        Pattern patternIsTF;
        Pattern patternIsSA;
        Pattern patternIsNUM;
        Pattern patternIsMAT;
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
        Matcher matcherIsMAT;
        Matcher matcherCategory;
        patternCategory = Pattern.compile("\\\\name\\|(.*)\\|");
        patternQuestion = Pattern.compile("\\\\question\\|(.*)\\|");
        patternAnswer = Pattern.compile("\\\\answer\\|(.*)\\|");
        patternQuestionText = Pattern.compile("[^\\w]subsection");
        patternAnswerText = Pattern.compile("[^\\w]begin\\{itemize\\}");
        patternAnswerTextEnd = Pattern.compile("[^\\w]end\\{itemize\\}");
        patternIsMC = Pattern.compile("\"multichoice\"");
        patternIsTF = Pattern.compile("\"truefalse\"");
        patternIsSA = Pattern.compile("\"shortanswer\"");
        patternIsNUM = Pattern.compile("\"numerical\"");
        patternIsMAT = Pattern.compile("\"matching\"");
        String category = "";
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            ArrayList<List<String>> questionParams;
            ArrayList<List<String>> question;
            ArrayList<List<String>> response;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {  //ISO-8859-15
                String strLine;
                category = "";
                int answerWriter = 0;
                int questionWriter = 0;
                boolean IsMcQuestion = false;
                boolean IsTFQuestion = false;
                boolean IsSAQuestion = false;
                boolean IsNUMQuestion = false;
                boolean IsMATQuestion = false;
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
                    matcherIsMAT = patternIsMAT.matcher(strLine);
                    matcherCategory = patternCategory.matcher(strLine);
                    if (questionWriter == 1) {
                        questionArray.add(strLine);
                    }
                    if (answerWriter == 1) {
                        answerArray.add(strLine);
                    }
                    if(matcherCategory.find()){
                        category = matcherCategory.group(1);
                    }
                    if(matcherQuestion.find()) { // findet frage: ....
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
                        if (matcherIsMAT.find()) { //findet "matching"
                            IsMATQuestion = true;
                            questionParamsArray.add(strLine);
                            questionParams.add(questionParamsArray);
                            questionParamsArray = new ArrayList<>();
                        }
                    }
                    
                    if (IsMcQuestion || IsTFQuestion || IsSAQuestion || IsNUMQuestion || IsMATQuestion) {
                        if (matcherQuestionText.find()) { //findet \subsection{}
                            questionWriter = 1;
                            questionArray.add(strLine); //working here
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
                            IsMATQuestion = false;
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
            questionParams.forEach((List<String> el) -> {
                el.stream().map((e) -> patternQuestion.matcher(e)).filter((matcherxy) -> (matcherxy.find())).map((matcherxy) -> matcherxy.group(1)).map((tmp) -> tmp.split(", ")).forEachOrdered((kk) -> {
                    // split question params with ","
                    myList.add(Arrays.toString(kk)); // add params array in one List of all questions params
                });
            });
            System.out.println("questParams final: " + myList);
            //OutprintObject(myList);
// List of String myList.get(0) --> string contains first question params
            System.out.println("questions final: " + question);
            //OutprintObject(question);
// List of List of String question.get(0) --> list of string contains first question text
            System.out.println("responses final: " + response);
            //OutprintObject(response);
// List of List of String response.get(0) --> list of string contains first question answers
            generalFetcher(outputpath, category, myList, question, response);
            
        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }  
    }
    
    public static void generalFetcher (String output, String category, List<String> myList, ArrayList<List<String>> question, ArrayList<List<String>> response){
            Quiz quiz = new Quiz();
            quiz.setCategory(category);
            System.out.println("\n----------Quiz Startet-------------\n");
            for (int i = 0; i < myList.size(); i++) {
                if (myList.get(i).contains("multichoice")){
                    try {
                        MultiChoiceQuestion MCQ = null;
                        MCQ = MultichoiceFetcher(myList.get(i),question.get(i),response.get(i));
                        quiz.setQuestions(MCQ);
                    } catch (IOException ex) {
                        Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (myList.get(i).contains("truefalse")){
                    try {
                        TrueFalseQuestion TFQ = null;
                        TFQ = TrueFalseFetcher(myList.get(i),question.get(i),response.get(i));
                        quiz.setQuestions(TFQ);
                    } catch (IOException ex) {
                        Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (myList.get(i).contains("shortanswer")){
                    try {
                        ShortAnswerQuestion SAQ = null;
                        SAQ = ShortAnswerFetcher(myList.get(i),question.get(i),response.get(i));
                        quiz.setQuestions(SAQ);
                    } catch (IOException ex) {
                        Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (myList.get(i).contains("numerical")){
                    try {
                        NumericalQuestion NUMQ = null;
                        NUMQ = NumericalFetcher(myList.get(i),question.get(i),response.get(i));
                        quiz.setQuestions(NUMQ);
                    } catch (IOException ex) {
                        Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (myList.get(i).contains("matching")){
                    try {
                        MatchingQuestion MATQ = null;
                        MATQ = MatchingFetcher(myList.get(i),question.get(i),response.get(i));
                        quiz.setQuestions(MATQ);
                    } catch (IOException ex) {
                        Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            OutputToXml(quiz, output);
    }
    
    public static MultiChoiceQuestion MultichoiceFetcher (String paramsList,  List<String> questionsList, List<String> answersList) throws IOException{
            Pattern pi;
            pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})"); //  detect {path}
            Pattern patternParamType = Pattern.compile("type=\"multichoice\"");
            MultiChoiceQuestion MCQ = null; 
            Matcher matcherParamType = patternParamType.matcher(paramsList);
            String[] split = paramsList.split(", ");
            String first = questionsList.get(0).replaceAll("\\\\subsection\\{", "").replaceAll("\\}", "");
            List sublist = questionsList.subList(1, questionsList.size());
            //String QuestionTextJoined = String.join("<br>", sublist);
            //String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            String QuestionTextJoinedProcessed = String.join("<br>", sublist).replace("\\begin{verbatim}","<pre><code>").replace("\\end{verbatim}","</code></pre>");
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join(" ", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("\\\\answer\\|(.*)\\|");
            if (matcherParamType.find()) {
                MCQ = new MultiChoiceQuestion();
                MCQ.setName(first);
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
                    String newstr = answer.replaceAll("\\\\answer\\|(.*)\\|", ""); ////"[^\\w]answer\\|.*?\\|"
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
            String first = questionsList.get(0).replaceAll("\\\\subsection\\{", "").replaceAll("\\}", "");
            List sublist = questionsList.subList(1, questionsList.size());
            //String QuestionTextJoined = String.join("<br>", sublist);
            //String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            String QuestionTextJoinedProcessed = String.join("<br>", sublist).replace("\\begin{verbatim}","<pre><code>").replace("\\end{verbatim}","</code></pre>");
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join(" ", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("\\\\answer\\|(.*)\\|");
            if (matcherParamType.find()) {
                TFQ = new TrueFalseQuestion();
                TFQ.setQuestiontext(QuestionTextJoinedProcessed);
                TFQ.setName(first);
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
                    if ("format".equals(parameter[0])) {
                        TFQ.setFormat(parameter[1]);
                    }
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
                    String newstr = answer.replaceAll("\\\\answer\\|(.*)\\|", "");
                    Answer answerObj = new Answer();
                    answerObj.setText(newstr.trim());
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
            String first = questionsList.get(0).replaceAll("\\\\subsection\\{", "").replaceAll("\\}", "");
            List sublist = questionsList.subList(1, questionsList.size());
            //String QuestionTextJoined = String.join("<br>", sublist);
            //String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            String QuestionTextJoinedProcessed = String.join("<br>", sublist).replace("\\begin{verbatim}","<pre><code>").replace("\\end{verbatim}","</code></pre>");
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join(" ", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("\\\\answer\\|(.*)\\|");
            if (matcherParamType.find()) {
                SAQ = new ShortAnswerQuestion();
                SAQ.setName(first);
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
                    if ("usecase".equals(parameter[0])) {
                        SAQ.setUsecase(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("format".equals(parameter[0])) {
                        SAQ.setFormat(parameter[1]);
                    }
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
                    String newstr = answer.replaceAll("\\\\answer\\|(.*)\\|", "");
                    Answer answerObj = new Answer();
                    answerObj.setText(newstr.trim());
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
            String first = questionsList.get(0).replaceAll("\\\\subsection\\{", "").replaceAll("\\}", "");
            List sublist = questionsList.subList(1, questionsList.size());
            //String QuestionTextJoined = String.join("<br>", sublist);
            //String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            String QuestionTextJoinedProcessed = String.join("<br>", sublist).replace("\\begin{verbatim}","<pre><code>").replace("\\end{verbatim}","</code></pre>");
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join(" ", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("\\\\answer\\|(.*)\\|");
            if (matcherParamType.find()) {
                NUMQ = new NumericalQuestion();
                NUMQ.setName(first);
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
                    if ("units".equals(parameter[0])) {
                        String[] strunit = new String[1];
                        if(parameter[1].contains("/")){
                            strunit = parameter[1].split("/");
                        }else{
                            strunit[0] = parameter[1];
                        }
                        for (String strunit1 : strunit) {
                            Unit unit = new Unit();
                            String[] str2 = strunit1.split(":");
                            unit.setUnit_name(str2[0].trim());
                            unit.setMultiplier(Integer.parseInt(str2[1]));
                            NUMQ.setUnits(unit);
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
                    String newstr = answer.replaceAll("\\\\answer\\|(.*)\\|", "");
                    AnswerNumerical answerObj = new AnswerNumerical();
                    answerObj.setText(newstr.trim());
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
    
    public static MatchingQuestion MatchingFetcher (String paramsList,  List<String> questionsList, List<String> answersList) throws IOException{
            Pattern pi;
            pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})"); //  detect {path}
            Pattern patternParamType = Pattern.compile("type=\"matching\"");
            MatchingQuestion MATQ = null; 
            Matcher matcherParamType = patternParamType.matcher(paramsList);
            String[] split = paramsList.split(", ");
            String first = questionsList.get(0).replaceAll("\\\\subsection\\{", "").replaceAll("\\}", "");
            List sublist = questionsList.subList(1, questionsList.size());
            //String QuestionTextJoined = String.join("<br>", sublist);
            //String QuestionTextJoinedProcessed = "<p style=\"display:inline;\">"+QuestionTextJoined+"<br></p>\n";
            String QuestionTextJoinedProcessed = String.join("<br>", sublist).replace("\\begin{verbatim}","<pre><code>").replace("\\end{verbatim}","</code></pre>");
            Matcher mi = pi.matcher(QuestionTextJoinedProcessed); 
            String AswerTextJoined = String.join(" ", answersList);
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("\\\\answer\\|(.*)\\|");
            if (matcherParamType.find()) {
                MATQ = new MatchingQuestion();
                MATQ.setQuestiontext(QuestionTextJoinedProcessed);
                MATQ.setName(first);
                if(mi.find()) {
                    List<String[]> imgs = imageAdapter(QuestionTextJoinedProcessed);
                    for (String[] img : imgs) {
                        Image image = new Image();
                        image.setName(img[0]);
                        image.setEncodedstring(img[1]);
                        MATQ.setQuestiontext(img[2]);
                        MATQ.setImages(image);
                    }
                }                
            }            
            for (String split1 : split) {
                String[] parameter = paramSplitter(split1);
                if (MATQ != null) {
                    if ("shuffle".equals(parameter[0])) {
                        MATQ.setShuffleanswers(Boolean.parseBoolean(parameter[1].trim()));
                    }
                    if ("format".equals(parameter[0])) {
                        MATQ.setFormat(parameter[1]);
                    }
                    if ("correctfeedback".equals(parameter[0])) {
                        MATQ.setCorrectfeedback(parameter[1]);
                    }
                    if ("partiallycorrectfeedback".equals(parameter[0])) {
                        MATQ.setPartiallycorrectfeedback(parameter[1]);
                    }
                    if ("incorrectfeedback".equals(parameter[0])) {
                        MATQ.setIncorrectfeedback(parameter[1]);
                    }
                    if ("generalfeedback".equals(parameter[0])) {
                        MATQ.setGeneralfeedback(parameter[1]);
                    }
                    if ("hidden".equals(parameter[0])) {
                        MATQ.setHidden(Integer.parseInt(parameter[1].trim()));
                    }
                    if ("defaultgrade".equals(parameter[0])) {
                        MATQ.setDefaultgrade(Float.parseFloat(parameter[1].trim()));
                    }
                    if ("penalty".equals(parameter[0])) {
                        MATQ.setPenalty(Float.parseFloat(parameter[1].trim()));
                    }
                }
            }
            for (String answer : parsedString) {
                if (!answer.isEmpty() && !isNull(answer) && !("<br>".equals(answer))) {
                    String newstr = answer.replaceAll("\\\\answer\\|(.*)\\|", "");
                    Subquestion subq = new Subquestion();
                    AnswerMatching ans = new AnswerMatching();
                    ans.setText(newstr.trim().replaceAll("<br>", ""));
                    subq.setAnswer(ans);
                    Matcher m1 = p.matcher(answer);
                    while (m1.find()) {
                        String[] answerParams = new String[1];
                        answerParams[0] = m1.group(1);
                        if (answerParams[0].contains(",")) {
                            answerParams = m1.group(1).split(", ");
                            System.out.println(Arrays.toString(answerParams));
                        }
                        for (String ap : answerParams) {
                            String[] parameterAnswer = paramSplitter(ap);
                            if ("text".equals(parameterAnswer[0])) {
                                subq.setText(parameterAnswer[1]);
                            }
                        }
                    }
                    if (MATQ != null) {
                        String format = MATQ.getFormat();
                        subq.setFormat(format);
                        MATQ.setSubquestions(subq);
                    }
                }
            }
            System.out.printf("\n--Begin question--\n");
            return MATQ;
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

    public static void OutputToXml(Quiz quiz, String output) {
        try {
            String out = output;
            File file = new File(out);
            JAXBContext jaxbContext = JAXBContext.newInstance(Quiz.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-15"); //"UTF-8"
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(quiz, file);
            JOptionPane.showMessageDialog(null, "Conversion done!   Please check your file on this path:\n"+file);            
        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Error in marshalling..."+e.toString());
        }  
    }
    
    public static List<String[]> imageAdapter(String str) throws IOException {
        List<String[]> list = new ArrayList<>();
        Pattern pi;
        pi = Pattern.compile("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})");
        Matcher mi = pi.matcher(str);
        while (mi.find()) {
            String[] imageAr = new String[3];
            String ImageParams = getImageParams(mi.group(2));
            String tmp1 = mi.group(3);
            String base46Image = encoder(tmp1);
            File f = new File(tmp1);
            imageAr[0] = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\") + 1);
            imageAr[1] = base46Image;
            str = str.replaceFirst("\\\\includegraphics\\[(([^]]+)\\]\\{([^}]+)\\})", "<p style=\"display:inline;\"><img src=\"@@PLUGINFILE@@/" + imageAr[0] + "\" role=\"presentation\" style=\"vertical-align:middle; margin:0.5em; " + ImageParams + "\" /></p>");
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
                        FinalString += " heigth:" + convertImageParams(parameterImage[1]);
                    }
                    if (tmp.contains("width")) {
                        FinalString += " width:" + convertImageParams(parameterImage[1]);
                    }
                }
            }
        }else{
            tab[0] = htmlParamString;
            String[] parameterImage = paramSplitter(tab[0]);
                    if (parameterImage[0].contains("scale")) {
                        FinalString += "transform:scale("+Float.parseFloat(parameterImage[1])+"); ";
                    } else if (!parameterImage[0].contains("scale")) {
                        FinalString += parameterImage[0].trim() + ":" + convertImageParams(parameterImage[1]);
                    }            
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
            Result += Integer.toString(finalint)+"px; ";
        }
        return Result;
    }

    
// function to display programm running infos in the Text area of the main form    
//      private void updateTextArea(final String text) { 
//      }

}
