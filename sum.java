/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author foufou
 */



 /*--------------------Tepmlate for CodeRunner Parameters--------------------

@cr_quiz(value="quiznamestring")
@cr_name(value="questionnamestring")
@cr_generalfeedback(value="feedbackstring")
@cr_defaultgrade(value=2.0)
@cr_penalty(value=0.0)
@cr_hidden(value=0)
@cr_coderunnertype(value="java_class") 
@cr_prototypetype(value=0)
@cr_allornothing(value=1)
@cr_penaltyregime(value="10, 20, ...")
@cr_precheck(value=0)
@cr_showsource(value=0)
@cr_answerboxlines(value=18)
@cr_answerboxcolumns(value=100)
@cr_useace(value=1)
@cr_language(value="")
@cr_acelang(value="")
@cr_sandbox(value="")
@cr_grader(value="EqualityGrader")
@cr_cputimelimitsecs(value= )
@cr_memlimitmb(value= )
@cr_validateonsave(value=0)
@cr_iscombinatortemplate(value= )
@cr_allowmultiplestdins(value= )
@cr_questiontext
...
text+Code+html
...
@cr_template
...
code
...
@cr_testsplitterre
...
json
...
@cr_sandboxparams
...
json
...
@cr_templateparams
...
json
...
@cr_resultcolumns
...
json
...
@cr_answerpreload
...
code
...
*-------------------------------------------------------------------*/

public class sum {
 private String str="";
    public static void main(String[] args) {
        sum sum = new sum();
        sum.setStr("Coderunner");
    }
    
    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
 
}
