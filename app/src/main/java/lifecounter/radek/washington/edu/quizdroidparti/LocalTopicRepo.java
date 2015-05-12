package lifecounter.radek.washington.edu.quizdroidparti;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 5/12/15.
 *
 * A hard-coded repository of locally stored topics and questions.
 */
public class LocalTopicRepo implements TopicRepository {

    // Titles for different topics.
    private String mathTitle = "Math";
    private String marvelTitle = "Marvel Superheroes";
    private String physicsTitle = "Physics";

    //Short descriptions.
    private String mathShortDesc = "Average Nightmare";
    private String marvelShortDesc = "Hallelujah!";
    private String physicsShortDesc = "Unforgettable and Insufferable Horror";

    //Long descriptions.
    private String mathLongDesc = "Something that you have to learn from 6 all the way to " +
            "your college graduation, at which point you decide you've learned way too much of it " +
            "to be actually useful. You then start looking at your academic life pathetically and " +
            "wondering how on earth did you spend all those time on something like this (True story).";

    private String physicsLongDesc = "A horrendous nightmare in which you have to live through your " +
            "college (if you are an engineering major, ofc). Sometimes you wonder why life is so cruel when " +
            "you look at a spinning wheel and have no darn clue how to make it stop by spinning yourself.";

    private String marvelLongDesc = "For all nerds (or quasi-nerds) alike (which I humbly assume " +
            "you are, given you spend a good chunk of your life like me sitting in front of a computer " +
            "and writing apps), this is a dream world too distant and beautiful to even think about but" +
            "so realistically represented that people just cannot stop talking about it.";

    //Lists of questions.
    private Topic math;
    private Topic physics;
    private Topic marvel;

    //Constructs a new LocalTopic repo
    public LocalTopicRepo() {

        //Construct a math topic with 2 questions.
        math = new Topic(mathTitle, mathShortDesc, mathLongDesc);
        List<String> mathOptions1 = new ArrayList<String>();
        mathOptions1.add("199");
        mathOptions1.add("169");
        mathOptions1.add("139");
        mathOptions1.add("Please use a calculator and hit yourself in the head!");

        List<String> mathOptions2 = new ArrayList<String>();
        mathOptions2.add("Yes");
        mathOptions2.add("No");
        mathOptions2.add("This is a philosophical question");
        mathOptions2.add("I admit my math sux");

        math.addQuestion(new Question("What's the answer of 13 * 13?", 1, mathOptions1));
        math.addQuestion(new Question("Is 2 a prime number?", 0, mathOptions2));

        //Construct a physics topic with 2 questions.
        physics = new Topic(physicsTitle, physicsShortDesc, physicsLongDesc);
        List<String> physicsOptions1 = new ArrayList<String>();
        physicsOptions1.add("3 * 10^8 m/s");
        physicsOptions1.add("343 m/s");
        physicsOptions1.add("1200 m/s");
        physicsOptions1.add("Admit it! You don't know!");

        List<String> physicsOptions2 = new ArrayList<String>();
        physicsOptions2.add("F = mg");
        physicsOptions2.add("V = D / t");
        physicsOptions2.add("F = ma");
        physicsOptions2.add("E = mc^2");

        physics.addQuestion(new Question("What's the speed of sound?", 1, physicsOptions1));
        physics.addQuestion(new Question("Second Newton's Law?", 2, physicsOptions2));

        //Construct a marvel topic with 2 questions.
        List<String> marvelOptions1 = new ArrayList<String>();
        marvelOptions1.add("Stark's dad");
        marvelOptions1.add("A narcissistic good-looking alien");
        marvelOptions1.add("A scientist who mutated");
        marvelOptions1.add("Superman's cousin");

        List<String> marvelOptions2 = new ArrayList<String>();
        marvelOptions2.add("Darth Vader is not Marvel-made");
        marvelOptions2.add("Vader rules!");
        marvelOptions2.add("LOL");
        marvelOptions2.add("I quit!");

        marvel.addQuestion(new Question("Who is Thor?", 1, marvelOptions1));
        marvel.addQuestion(new Question("IronMan vs Darth Vader?", 1, marvelOptions2));
    }

    // Returns a list of topics stored locally in memory.
    public List<Topic> getTopicList() {
        List<Topic> topicList = new ArrayList<Topic>();
        topicList.add(math);
        topicList.add(physics);
        topicList.add(marvel);
        return topicList;
    }
}
