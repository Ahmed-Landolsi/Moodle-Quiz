package javaapplication2;
import java.io.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
        Pattern patternCategory;
        Matcher matcherQuestion;
        Matcher matcherAnswer;
        Matcher matcherQuestionText;
        Matcher matcherAnswerText;
        Matcher matcherAnswerTextEnd;
        Matcher matcherIsMc;
        Matcher matcherCategory;
        patternCategory = Pattern.compile("^%name=");
        patternQuestion = Pattern.compile("^%frage=");
        patternAnswer = Pattern.compile("^%antwort=");
        patternQuestionText = Pattern.compile("[^\\w]subsection");
        patternAnswerText = Pattern.compile("[^\\w]begin\\{itemize\\}");
        patternAnswerTextEnd = Pattern.compile("[^\\w]end\\{itemize\\}");
        patternIsMC = Pattern.compile("\"multichoice\"");

        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String category = "";
            int answerWriter = 0;
            int questionWriter = 0;
            boolean IsMcQuestion = false;
            List<String> questionParamsArray = new ArrayList<>();
            List<String> questionArray = new ArrayList<>();
            List<String> answerArray = new ArrayList<>();
            ArrayList<List<String>> questionParams = new ArrayList<>();
            ArrayList<List<String>> question = new ArrayList<>();
            ArrayList<List<String>> response = new ArrayList<>();
            while ((strLine = br.readLine()) != null) {
                matcherQuestion = patternQuestion.matcher(strLine);
                matcherAnswer = patternAnswer.matcher(strLine);
                matcherAnswerText = patternAnswerText.matcher(strLine);
                matcherQuestionText = patternQuestionText.matcher(strLine);
                matcherAnswerTextEnd = patternAnswerTextEnd.matcher(strLine);
                matcherIsMc = patternIsMC.matcher(strLine);
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
                }

                if (IsMcQuestion) {
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
                        if (!answerArray.isEmpty()) {
                            answerArray.remove(answerArray.size() - 1);
                            response.add(answerArray);
                            answerArray = new ArrayList<>();
                        }
                    }
                }

            }

            br.close();
            List<String> myList = new ArrayList<>();
            Pattern patternxy = Pattern.compile("\\(([^)]+)\\)"); //read params between () in %frage=(...)/% Line
            for (List<String> el : questionParams) {
                for (String e : el) {
                    Matcher matcherxy = patternxy.matcher(e);
                    if (matcherxy.find())
                    {
                        String tmp = matcherxy.group(1);
                        String[] kk = tmp.split(", "); // split question params with ","
                        myList.add(Arrays.toString(kk)); // add params array in one List of all questions params
                    }
                }
            }
            System.out.println("questParams final: " + myList); 
            System.out.println("questions final: " + question);
            System.out.println("responses final: " + response);
            
            MultichoiceFetcher(category,myList,question,response);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
    }
    
    public static void MultichoiceFetcher (String category, List<String> paramsList,  ArrayList<List<String>> questionsList, ArrayList<List<String>> answersList){
        Pattern patternParamType = Pattern.compile("type=\"multichoice\"");
        Quiz quiz = new Quiz();
        System.out.println("\n----------Quiz Startet-------------\n");
        quiz.setCategory(category);
        MultiChoiceQuestion MCQ = null;        
       
        for (int i = 0; i < paramsList.size(); i++) {
            Matcher matcherParamType = patternParamType.matcher(paramsList.get(i));
            String[] split = paramsList.get(i).split(", ");
            String QuestionTextJoined = String.join("<br>", questionsList.get(i));
            
            //String QuestionTextJoinedProcessed = "<text><![CDATA[\n<p>"+QuestionTextJoined+"<br></p>\n]]></text>";
            String QuestionTextJoinedProcessed = "<p>"+QuestionTextJoined+"<br></p>\n";
            
            String AswerTextJoined = String.join("<br>", answersList.get(i));
            String[] parsedString = AswerTextJoined.split("\\\\item");
            Pattern p = Pattern.compile("%antwort=(.*?)/%");

            if (matcherParamType.find()) {
              MCQ = new MultiChoiceQuestion();
              MCQ.setQuestiontext(QuestionTextJoinedProcessed);
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
                    String newstr = answer.replaceAll("(<br>%antwort)[^&]*(/%<br>)", "");
                    Answer answerObj = new Answer();
                    
                    //answerObj.setText("<![CDATA[\n<p>" + newstr + "<br></p>\n]]>");
                    answerObj.setText("<p>" + newstr + "<br></p>\n");
                    
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
//                        System.out.printf("\nfraction: "+ answerObj.getFraction());
//                        System.out.printf("\nformat: "+ answerObj.getFormat());
//                        System.out.printf("\nfeedback: "+ answerObj.getFeedback());
//                        System.out.printf("\ntext: "+ answerObj.getText());

                    }
                }
            }
            
            if (MCQ != null) {
                quiz.setQuestions(MCQ);
                System.out.printf("\n--Begin question--\n");
                OutputToXml(quiz);
                //correctXml(); 
                //OutprintObject(MCQ);
            }
        }
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
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
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
//            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
//            DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", DumbEscapeHandler.theInstance);
//            marshaller.marshal(quiz, dataWriter);
            marshaller.marshal(quiz, file);
        } catch (JAXBException e) {
            System.err.println("Error in marshalling..."+e.toString());
        }  
//        catch (IOException ex) {
//            Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }
//    private static void correctXml (){
//        File file = new File("C:\\Users\\foufou\\Desktop\\file.xml");
//        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//       
//        try {
//            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//            org.w3c.dom.Document doc = docBuilder.parse(file);
//            Node category = doc.getFirstChild();
//            NamedNodeMap categoryAttributes = category.getAttributes();
//            Attr type = doc.createAttribute("type");
//            type.setValue("category");
//            categoryAttributes.setNamedItem(type);
//            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            //Node canada = doc.createElement("country");
//            //canada.setTextContent("ca");
//            //earth.appendChild(canada);
//            //initialize StreamResult with File object to save to file
//            StreamResult result = new StreamResult(new StringWriter());
//            DOMSource source = new DOMSource(doc);
//            transformer.transform(source, result);
//            String xmlString = result.getWriter().toString();
//            System.out.println(xmlString);
//        } catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
//            Logger.getLogger(TestRead.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


}
