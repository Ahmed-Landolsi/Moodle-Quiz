 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 *
 * @author foufou
 */
@XmlRootElement(name="question")

@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(propOrder={"name", "questiontext", "generalfeedback", "defaultgrade", "penalty", "hidden"})

public class CR_Question {

    /**
     * @param subquestions the subquestions to set
     */

    private float penalty;
    private float defaultgrade;
    private final QuestionName name = new QuestionName();
    private final QuestionText questiontext = new QuestionText();
    private final Feedback generalfeedback = new Feedback();
    private int hidden = 0;
    private int prototypetype = 0;
    private int allornothing = 0;
    private String penaltyregime = "0";
    private int precheck = 0;
    private int showsource = 0;
    private int answerboxlines = 18;
    private int answerboxcolumns = 100;
    private int useace = 1;
    private String cputimelimitsecs;
    private String memlimitmb;
    private int validateonsave = 0;
    private String iscombinatortemplate;
    private String allowmultiplestdins;
    private String type = "coderunner";
    private String coderunnertype = "java_method";
    private String format = "html";
    private String language;
    private String acelang;
    private String sandbox;
    private String grader;
    private String template;
    private String answer;
    private String answerpreload;
    private String testsplitterre;
    private String sandboxparams;
    private String templateparams;
    private String resultcolumns;
    List<CR_Testcase> testcases;
    
    public static void main(String[] args){}
    public CR_Question() {
        this.testcases = new ArrayList<>();
    }


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
        this.generalfeedback.setFormat(format);
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

  

    /**
     * @return the type
     */
    @XmlAttribute
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the prototypetype
     */
    @XmlElement
    public int getPrototypetype() {
        return prototypetype;
    }

    /**
     * @param prototypetype the prototypetype to set
     */
    public void setPrototypetype(int prototypetype) {
        this.prototypetype = prototypetype;
    }

    /**
     * @return the allornothing
     */
    @XmlElement
    public int getAllornothing() {
        return allornothing;
    }

    /**
     * @param allornothing the allornothing to set
     */
    public void setAllornothing(int allornothing) {
        this.allornothing = allornothing;
    }

    /**
     * @return the penaltyregime
     */
    @XmlElement
    public String getPenaltyregime() {
        return penaltyregime;
    }

    /**
     * @param penaltyregime the penaltyregime to set
     */
    public void setPenaltyregime(String penaltyregime) {
        this.penaltyregime = penaltyregime;
    }

    /**
     * @return the precheck
     */
    @XmlElement
    public int getPrecheck() {
        return precheck;
    }

    /**
     * @param precheck the precheck to set
     */
    public void setPrecheck(int precheck) {
        this.precheck = precheck;
    }

    /**
     * @return the showsource
     */
    @XmlElement
    public int getShowsource() {
        return showsource;
    }

    /**
     * @param showsource the showsource to set
     */
    public void setShowsource(int showsource) {
        this.showsource = showsource;
    }

    /**
     * @return the answerboxlines
     */
    @XmlElement
    public int getAnswerboxlines() {
        return answerboxlines;
    }

    /**
     * @param answerboxlines the answerboxlines to set
     */
    public void setAnswerboxlines(int answerboxlines) {
        this.answerboxlines = answerboxlines;
    }

    /**
     * @return the answerboxcolumns
     */
    @XmlElement
    public int getAnswerboxcolumns() {
        return answerboxcolumns;
    }

    /**
     * @param answerboxcolumns the answerboxcolumns to set
     */
    public void setAnswerboxcolumns(int answerboxcolumns) {
        this.answerboxcolumns = answerboxcolumns;
    }

    /**
     * @return the useace
     */
    @XmlElement
    public int getUseace() {
        return useace;
    }

    /**
     * @param useace the useace to set
     */
    public void setUseace(int useace) {
        this.useace = useace;
    }

    /**
     * @return the cputimelimitsecs
     */
    @XmlElement
    public String getCputimelimitsecs() {
        return cputimelimitsecs;
    }

    /**
     * @param cputimelimitsecs the cputimelimitsecs to set
     */
    public void setCputimelimitsecs(String cputimelimitsecs) {
        this.cputimelimitsecs = cputimelimitsecs;
    }

    /**
     * @return the memlimitmb
     */
    @XmlElement
    public String getMemlimitmb() {
        return memlimitmb;
    }

    /**
     * @param memlimitmb the memlimitmb to set
     */
    public void setMemlimitmb(String memlimitmb) {
        this.memlimitmb = memlimitmb;
    }

    /**
     * @return the validateonsave
     */
    @XmlElement
    public int getValidateonsave() {
        return validateonsave;
    }

    /**
     * @param validateonsave the validateonsave to set
     */
    public void setValidateonsave(int validateonsave) {
        this.validateonsave = validateonsave;
    }

    /**
     * @return the iscombinatortemplate
     */
    @XmlElement
    public String getIscombinatortemplate() {
        return iscombinatortemplate;
    }

    /**
     * @param iscombinatortemplate the iscombinatortemplate to set
     */
    public void setIscombinatortemplate(String iscombinatortemplate) {
        this.iscombinatortemplate = iscombinatortemplate;
    }

    /**
     * @return the allowmultiplestdins
     */
    @XmlElement
    public String getAllowmultiplestdins() {
        return allowmultiplestdins;
    }

    /**
     * @param allowmultiplestdins the allowmultiplestdins to set
     */
    public void setAllowmultiplestdins(String allowmultiplestdins) {
        this.allowmultiplestdins = allowmultiplestdins;
    }

    /**
     * @return the coderunnertype
     */
    @XmlElement
    public String getCoderunnertype() {
        return coderunnertype;
    }

    /**
     * @param coderunnertype the coderunnertype to set
     */
    public void setCoderunnertype(String coderunnertype) {
        this.coderunnertype = coderunnertype;
    }

    /**
     * @return the language
     */
    @XmlElement
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the acelang
     */
    @XmlElement
    public String getAcelang() {
        return acelang;
    }

    /**
     * @param acelang the acelang to set
     */
    public void setAcelang(String acelang) {
        this.acelang = acelang;
    }

    /**
     * @return the sandbox
     */
    @XmlElement
    public String getSandbox() {
        return sandbox;
    }

    /**
     * @param sandbox the sandbox to set
     */
    public void setSandbox(String sandbox) {
        this.sandbox = sandbox;
    }

    /**
     * @return the grader
     */
    @XmlElement
    public String getGrader() {
        return grader;
    }

    /**
     * @param grader the grader to set
     */
    public void setGrader(String grader) {
        this.grader = grader;
    }

    /**
     * @return the template
     */
    @XmlElement
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return the answer
     */
    @XmlElement
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return the answerpreload
     */
    @XmlElement
    public String getAnswerpreload() {
        return answerpreload;
    }

    /**
     * @param answerpreload the answerpreload to set
     */
    public void setAnswerpreload(String answerpreload) {
        this.answerpreload = answerpreload;
    }

    /**
     * @return the testsplitterre
     */
    @XmlElement
    public String getTestsplitterre() {
        return testsplitterre;
    }

    /**
     * @param testsplitterre the testsplitterre to set
     */
    public void setTestsplitterre(String testsplitterre) {
        this.testsplitterre = testsplitterre;
    }

    /**
     * @return the sandboxparams
     */
    @XmlElement
    public String getSandboxparams() {
        return sandboxparams;
    }

    /**
     * @param sandboxparams the sandboxparams to set
     */
    public void setSandboxparams(String sandboxparams) {
        this.sandboxparams = sandboxparams;
    }

    /**
     * @return the templateparams
     */
    @XmlElement
    public String getTemplateparams() {
        return templateparams;
    }

    /**
     * @param templateparams the templateparams to set
     */
    public void setTemplateparams(String templateparams) {
        this.templateparams = templateparams;
    }

    /**
     * @return the resultcolumns
     */
    @XmlElement
    public String getResultcolumns() {
        return resultcolumns;
    }

    /**
     * @param resultcolumns the resultcolumns to set
     */
    public void setResultcolumns(String resultcolumns) {
        this.resultcolumns = resultcolumns;
    }

    /**
     * @return the testcases
     */
    @XmlElementWrapper(name="testcases")
    @XmlElement(name="testcase")
    public List<CR_Testcase> getTestcases() {
        return testcases;
    }

    /**
     * @param testcase the testcases to set
     */
    public void setTestcases(CR_Testcase testcase) {
        this.testcases.add(testcase);
    }
    
}