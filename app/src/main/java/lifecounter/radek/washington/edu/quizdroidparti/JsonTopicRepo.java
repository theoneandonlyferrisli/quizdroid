package lifecounter.radek.washington.edu.quizdroidparti;

import java.util.List;

/**
 * Created by apple on 5/12/15.
 *
 * A repository that reads topics from the json file under assets directory.
 */

public class JsonTopicRepo implements TopicRepository{
    private List<Topic> topicList;

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {

        this.topicList = topicList;
    }
}
