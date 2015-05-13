package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class OverviewFragment extends Fragment {
    private Bundle qna;
    private Activity hostActivity;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qna = getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get the application object.
        QuizApp app = (QuizApp) getActivity().getApplication();
        Topic currentTopic = app.getCurrentTopic();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        //Set view controls for question overview.
        TextView topic = (TextView) view.findViewById(R.id.topic);
        topic.setText(currentTopic.getTitle());

        TextView description = (TextView) view.findViewById(R.id.topic_description);
        description.setText(currentTopic.getLongDesc());

        TextView numQuestions = (TextView) view.findViewById(R.id.number_of_questions);
        numQuestions.setText("Number of questions available: " + currentTopic.getQuestions().size());

        final QuestionFragment questionFragment = new QuestionFragment();

        Button begin = (Button) view.findViewById(R.id.BEGIN);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, questionFragment)
                        .commit();
            }
        });


        return view;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.hostActivity = activity;
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
