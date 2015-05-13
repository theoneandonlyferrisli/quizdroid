package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AnswerFragment extends Fragment {

    /*private Bundle qna;
    private int current;
    private int numCorrect;
    private int correctAnswer;
    private int selected;
    private int total;*/


    public AnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the application object and all the info needed.
        QuizApp app = (QuizApp) getActivity().getApplication();
        final Topic currentTopic = app.getCurrentTopic();
        Question currentQuestion = currentTopic.getQuestions().get(currentTopic.getCurrentIndex());
        currentTopic.choose(currentTopic.getLastSelected());


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        TextView result1 = (TextView) view.findViewById(R.id.correct_answer);
        result1.setText("The correct choice is the no." + currentQuestion.getAnswer() +
                ", and you answered no." + currentTopic.getLastSelected());

        TextView result2 = (TextView) view.findViewById(R.id.num_correct);
        result2.setText("You have " + currentTopic.getNumCorrect() + " out of "
                + currentTopic.getQuestions().size() + " correct!");

        Button next = (Button) view.findViewById(R.id.next);
        if (currentTopic.isFinished())
            next.setText("FINISH");

        final QuestionFragment questionFragment = new QuestionFragment();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentTopic.isFinished()) {
                    getFragmentManager().beginTransaction()
                        .replace(R.id.container, questionFragment)
                        .commit();
                } else {
                    Intent finish = new Intent(getActivity(), TopicList.class);
                    startActivity(finish);
                }
            }
        });
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
