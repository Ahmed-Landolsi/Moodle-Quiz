/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 *
 * @author foufou
 */
@XmlRootElement(name="question")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder={"name", "questiontext" , "generalfeedback", "defaultgrade", "penalty", "hidden", "single", "shuffleanswers", "answernumbering", "correctfeedback", "partiallycorrectfeedback", "incorrectfeedback", "answers"})
public class MultiChoiceQuestion {
    private boolean single = true;
    private boolean shuffleanswers = true;
    private int hidden = 0;
    @XmlAttribute
    private final String type = "multichoice";
    private String format = "moodle_auto_format";
    private String answernumbering  = "123";
    private float penalty;
    private float defaultgrade;
    private final QuestionName name = new QuestionName();
    private final QuestionText questiontext = new QuestionText();
    private final Feedback correctfeedback = new Feedback();
    private final Feedback partiallycorrectfeedback = new Feedback();
    private final Feedback incorrectfeedback = new Feedback();
    private final Feedback generalfeedback = new Feedback();
    
    @XmlElement(name="answer")
    ArrayList<Answer> answers;     //Answer has : *fraction(int)(default:falseanswer=0 rightanswer=100)
 
    public static void main(String[] args){}
    public MultiChoiceQuestion() {
        this.answers = new ArrayList<>();
    }
    @Override
    public String toString() {
        return ("\nQuestionName:"+this.getName()+
                    "\nQuestion Numbering: "+ this.getAnswernumbering() +
                    "\nShuffle: "+ this.isShuffleanswers() +
                    "\nSingle : " + this.isSingle());
   }
    public void setAnswer(Answer answer) {
        Answer ans = answer;
        this.answers.add(ans);
    } 
//    public void answerData(String feedback, String  text, int fraction) {
//        Answer answer = new Answer();
//        answer.setFraction(fraction);  
//        answer.setText(text); 
//        answer.setFeedback(feedback); 
//        answers.add(answer);
//    }
    


    /**
     * @return the format
     */
    //@XmlAttribute(for all feedback elements)
    @XmlTransient
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
        this.questiontext.setFormat(format);
        this.correctfeedback.setFormat(format); 
        this.partiallycorrectfeedback.setFormat(format) ;
        this.incorrectfeedback.setFormat(format);
        this.generalfeedback.setFormat(format);
    }

    /**
     * @return the answernumbering
     */
    @XmlElement
    public String getAnswernumbering() {
        return answernumbering;
    }

    /**
     * @param answernumbering the answernumbering to set
     */
    public void setAnswernumbering(String answernumbering) {
        this.answernumbering = answernumbering;
    }

    /**
     * @return the single
     */
    @XmlElement
    public boolean isSingle() {
        return single;
    }

    /**
     * @param single the single to set
     */
    public void setSingle(boolean single) {
        this.single = single;
    }

    /**
     * @return the shuffleanswers
     */
    @XmlElement
    public boolean isShuffleanswers() {
        return shuffleanswers;
    }

    /**
     * @param shuffleanswers the shuffleanswers to set
     */
    public void setShuffleanswers(boolean shuffleanswers) {
        this.shuffleanswers = shuffleanswers;
    }

     /**
     * @return the incorrectfeedback
     */
    @XmlElement
    public Feedback getIncorrectfeedback() {
        return incorrectfeedback;
    }

    /**
     * @param incorrectfeedback the incorrectfeedback to set
     */
    public void setIncorrectfeedback(String incorrectfeedback) {
        this.incorrectfeedback.setText(incorrectfeedback);
    }

    /**
     * @return the partiallycorrectfeedback
     */
    @XmlElement
    public Feedback getPartiallycorrectfeedback() {
        return partiallycorrectfeedback;
    }

    /**
     * @param partiallycorrectfeedback the partiallycorrectfeedback to set
     */
    public void setPartiallycorrectfeedback(String partiallycorrectfeedback) {
        this.partiallycorrectfeedback.setText(partiallycorrectfeedback);
    }

    /**
     * @return the correctfeedback
     */
    @XmlElement
    public Feedback getCorrectfeedback() {
        return correctfeedback;
    }

    /**
     * @param correctfeedback the correctfeedback to set
     */
    public void setCorrectfeedback(String correctfeedback) {
        this.correctfeedback.setText(correctfeedback);
    }

      
    public void setName(String name)
    {
        this.name.setText(name);
    }
    @XmlElement
    public QuestionName getName()
    {
        return name;
    }
 
 
    public void setQuestiontext(String questiontext)
    {
        this.questiontext.setText(questiontext);
    }
    @XmlElement
    public QuestionText getQuestiontext() {
        return questiontext;
    }

    /**
     * @return the penalty
     */
    @XmlElement
    public float getPenalty() {
        return penalty;
    }

    /**
     * @param penalty the penalty to set
     */
    public void setPenalty(float penalty) {
        this.penalty = penalty;
    }

    /**
     * @return the generalfeedback
     */
    @XmlElement
    public Feedback getGeneralfeedback() {
        return generalfeedback;
    }

    /**
     * @param generalfeedback the generalfeedback to set
     */
    public void setGeneralfeedback(String generalfeedback) {
        this.generalfeedback.setText(generalfeedback);
    }

    /**
     * @return the defaultgrade
     */
    @XmlElement
    public float getDefaultgrade() {
        return defaultgrade;
    }

    /**
     * @param defaultgrade the defaultgrade to set
     */
    public void setDefaultgrade(float defaultgrade) {
        this.defaultgrade = defaultgrade;
    }

    /**
     * @return the hidden
     */
    @XmlElement
    public int getHidden() {
        return hidden;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(int hidden) {
        this.hidden = hidden;
    }


 
}